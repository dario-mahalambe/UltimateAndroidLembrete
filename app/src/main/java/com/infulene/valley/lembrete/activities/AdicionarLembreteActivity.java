package com.infulene.valley.lembrete.activities;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.infulene.valley.lembrete.DataUtils;
import com.infulene.valley.lembrete.backgroud_tasks.Lembrete_Receiver;
import com.infulene.valley.lembrete.R;

import java.util.Calendar;


public class AdicionarLembreteActivity extends AppCompatActivity {


    private static final String CHAVE_HORA = "hora";
    private static final String CHAVE_TITULO = "titulo";
    private static final String CHAVE_DETALHES = "detalhes";
    private LinearLayout btn_relogio;

    private Toolbar mToolbar;

    private TimePicker timePicker;

    private AlarmManager mAlarmManager;

    private TextView mTextView_horas;

    private Context context;

    private PendingIntent pendingIntent;

    private Button btn_gravar, btn_actualizar;

    private Button btn_disalarm;

    private EditText editText_titulo, editText_detalhes;

    private String hora_from_time_picker = "";

    private Calendar calendar;

    private Intent intent_lembrete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_lembrete);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_add_lembrete);

        btn_relogio = (LinearLayout) findViewById(R.id.btn_hora);

        mTextView_horas = (TextView) findViewById(R.id.tv_hora);

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        btn_disalarm = (Button) findViewById(R.id.btn_remover);

        btn_gravar = (Button) findViewById(R.id.btn_gravar);


        btn_actualizar = (Button) findViewById(R.id.btn_update);

        editText_titulo = (EditText) findViewById(R.id.et_titulo);

        editText_detalhes = (EditText) findViewById(R.id.et_detalhes);

        setSupportActionBar(mToolbar);

        this.context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        final Intent intent_editar = getIntent();

        editText_titulo.setText(intent_editar.getStringExtra(CHAVE_TITULO));
        mTextView_horas.setText(intent_editar.getStringExtra(CHAVE_HORA));
        editText_detalhes.setText(intent_editar.getStringExtra(CHAVE_DETALHES));

        btn_relogio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = getLayoutInflater();
                View dialogViewHora = inflater.inflate(R.layout.dialog_relogio, null);

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(AdicionarLembreteActivity.this, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Horas");
                builder.setView(dialogViewHora);

                timePicker = (TimePicker) dialogViewHora.findViewById(R.id.timePicker);

                builder.setPositiveButton("Aplicar", new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

//                                  =========== POSITIVE BUTTON DIALOG ======

                        calendar = Calendar.getInstance();

                        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                        calendar.set(Calendar.MINUTE, timePicker.getMinute());

                        hora_from_time_picker = DataUtils.horaTexto(timePicker.getHour(), timePicker.getMinute());
                        mTextView_horas.setText(hora_from_time_picker);

                        intent_lembrete = new Intent(getApplicationContext(), Lembrete_Receiver.class);

                        pendingIntent = PendingIntent.getBroadcast(AdicionarLembreteActivity.this,
                                0, intent_lembrete, PendingIntent.FLAG_UPDATE_CURRENT);


                        mAlarmManager.setInexactRepeating(
                                AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                    }
                });

                builder.setNegativeButton("Cancelar", null);
                builder.show();
            }
        });


        btn_gravar.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                //Botao responsavel por gravar na base de dados.

                addToLembretelist(); //
            }
        });


        btn_disalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Cancelar o alarme

                mAlarmManager.cancel(pendingIntent);
                Toast.makeText(context, "Alarme Cancelado", Toast.LENGTH_SHORT).show();
            }
        });

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              int id =   intent_editar.getIntExtra("id", 0);
              MainActivity.actualizar(id,editText_titulo.getText().toString(), hora_from_time_picker, editText_detalhes.getText().toString());

                MainActivity.mListAdapter.swapCursor(MainActivity.getAllLembretes());

                editText_detalhes.getText().clear();
                editText_titulo.getText().clear();
                mTextView_horas.setText("");
                Toast.makeText(context, "Actualizado", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static String msg_notification;

    public void addToLembretelist() {

        // Adicionar lembrete a base de dados
        MainActivity.addToLembretesList(editText_titulo.getText().toString(), hora_from_time_picker, editText_detalhes.getText().toString());

        MainActivity.mListAdapter.swapCursor(MainActivity.getAllLembretes());

        editText_detalhes.getText().clear();
        editText_titulo.getText().clear();
        mTextView_horas.setText("");
        msg_notification = editText_titulo.getText().toString();

        Toast.makeText(context, "Alarme Gravado!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
