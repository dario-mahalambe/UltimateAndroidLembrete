package com.infulene.valley.lembrete.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.infulene.valley.lembrete.Data.LembreteContract;
import com.infulene.valley.lembrete.Data.LembreteDbHelper;
import com.infulene.valley.lembrete.R;
import com.infulene.valley.lembrete.adapter.LembreteListAdapter;
import com.infulene.valley.lembrete.adapter.RecyclerItemClickListner;

public class MainActivity extends AppCompatActivity implements RecyclerItemClickListner {

    private static SQLiteOpenHelper dbHelper;
    private FloatingActionButton fab_add_lembrete;

    private Toolbar mToolbar;

    private RecyclerView mRecyclerView_lembretes;

    public static LembreteListAdapter mListAdapter;

    private LinearLayout linearLayout_lembrete_vazio;

    private Intent intent_edit_alarm;

    private static SQLiteDatabase mDb;
    private static final String CHAVE_HORA = "hora";
    private static final String CHAVE_TITULO = "titulo";
    private static final String CHAVE_DETALHES = "detalhes";


    Cursor mCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);

        mRecyclerView_lembretes = (RecyclerView) findViewById(R.id.recycler_view_lembretes);

        linearLayout_lembrete_vazio = (LinearLayout) findViewById(R.id.linear_vazio);

        setSupportActionBar(mToolbar);

        fab_add_lembrete = (FloatingActionButton) findViewById(R.id.fab_adiconar_lembrete);

        LembreteDbHelper dbHelper = new LembreteDbHelper(this);
        mDb = dbHelper.getWritableDatabase();


         mCursor = getAllLembretes();


        linearLayout_lembrete_vazio.setVisibility(View.INVISIBLE);

        mRecyclerView_lembretes.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        mListAdapter = new LembreteListAdapter(getApplicationContext(), mCursor);

        mListAdapter.setmRecyclerItemClickListner(this);

        mRecyclerView_lembretes.setAdapter(mListAdapter);


        fab_add_lembrete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent_add_lembrete = new Intent(getApplicationContext(), AdicionarLembreteActivity.class);
                startActivity(intent_add_lembrete);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }


            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                long id = (long) viewHolder.itemView.getTag();

                removeLembrete(id);

                mListAdapter.swapCursor(getAllLembretes());
            }
        }).attachToRecyclerView(mRecyclerView_lembretes);

    }

    public static Cursor getAllLembretes() {
        return mDb.query(
                LembreteContract.LembreteEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                LembreteContract.LembreteEntry.COLUMN_TIMESTAMP
        );
    }


    @Override
    protected void onResume() {
        super.onResume();
        mListAdapter.notifyDataSetChanged();
    }

    public static long addToLembretesList(String titulo, String hora, String detalhes) {

        // Adicionar alarme a base de dados SQLITE

        ContentValues cv = new ContentValues();

        cv.put(LembreteContract.LembreteEntry.COLUMN_TITULO, titulo);
        cv.put(LembreteContract.LembreteEntry.COLUMN_HORA, hora);
        cv.put(LembreteContract.LembreteEntry.COLUMN_DETALHES, detalhes);

        return mDb.insert(LembreteContract.LembreteEntry.TABLE_NAME, null, cv);

    }

    public boolean removeLembrete(long id) {

        // Remover Alarme da lista e da base de dados
        return mDb.delete(LembreteContract.LembreteEntry.TABLE_NAME,
                LembreteContract.LembreteEntry._ID + "=" + id, null) > 0;

    }




    public static long actualizar(int id,String titulo, String hora, String detalhes)
    {

        try
        {
            ContentValues cv=new ContentValues();
            cv.put(LembreteContract.LembreteEntry.COLUMN_TITULO, titulo);
            cv.put(LembreteContract.LembreteEntry.COLUMN_HORA, hora);
            cv.put(LembreteContract.LembreteEntry.COLUMN_DETALHES, detalhes);

            return mDb.update(LembreteContract.LembreteEntry.TABLE_NAME,cv, LembreteContract.LembreteEntry._ID+" =?",new String[]{String.valueOf(id)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public void onClickItemListner(View view, int clicledItemIndex) {

        intent_edit_alarm = new Intent(getApplicationContext(), AdicionarLembreteActivity.class);
//

        LembreteDbHelper dbHelper = new LembreteDbHelper(this);
        mDb = dbHelper.getWritableDatabase();


        if (!mCursor.moveToPosition(clicledItemIndex))
            return;


        String titulo = mCursor.getString(mCursor.getColumnIndex(LembreteContract.LembreteEntry.COLUMN_TITULO));

        String detalhes = mCursor.getString(mCursor.getColumnIndex(LembreteContract.LembreteEntry.COLUMN_DETALHES));

        String hora = mCursor.getString(mCursor.getColumnIndex(LembreteContract.LembreteEntry.COLUMN_HORA));

         int id = mCursor.getInt(mCursor.getColumnIndex(LembreteContract.LembreteEntry._ID));




        intent_edit_alarm.putExtra(CHAVE_TITULO, titulo);
        intent_edit_alarm.putExtra(CHAVE_HORA, hora);
        intent_edit_alarm.putExtra(CHAVE_DETALHES, detalhes);
        intent_edit_alarm.putExtra("id", id);
        startActivity(intent_edit_alarm);


//        Toast.makeText(this, titulo+ " " + clicledItemIndex + " " + detalhes, Toast.LENGTH_SHORT).show();



    }
}
