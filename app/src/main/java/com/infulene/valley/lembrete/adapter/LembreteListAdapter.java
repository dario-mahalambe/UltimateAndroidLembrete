package com.infulene.valley.lembrete.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.infulene.valley.lembrete.Data.LembreteContract;
import com.infulene.valley.lembrete.R;


/**
 * Created by user on 24-Oct-17.
 */

public class LembreteListAdapter extends RecyclerView.Adapter<LembreteListAdapter.MyViewHolder> {

    private Cursor mCursor;

    private RecyclerItemClickListner mRecyclerItemClickListner;

    private Context context;

    public LembreteListAdapter( Context context, Cursor cursor) {
        this.mCursor = cursor;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_lembrete_list,parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (!mCursor.moveToPosition(position))
            return;

        long id = mCursor.getLong(mCursor.getColumnIndex(LembreteContract.LembreteEntry._ID));

        String titulo = mCursor.getString(mCursor.getColumnIndex(LembreteContract.LembreteEntry.COLUMN_TITULO));

        String hora = mCursor.getString(mCursor.getColumnIndex(LembreteContract.LembreteEntry.COLUMN_HORA));

        holder.tv_hora.setText(hora);
        holder.tv_titulo.setText(titulo);
        holder.itemView.setTag(id);
        holder.iv_item.setImageResource(R.drawable.ic_access_time_blue_24dp);


    }

    public void setmRecyclerItemClickListner(RecyclerItemClickListner mRecyclerItemClickListner) {
        this.mRecyclerItemClickListner = mRecyclerItemClickListner;
    }

    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    public void addItem(int position) {
//        mItems.add(position, insertData);
        notifyItemInserted(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

       private TextView tv_titulo;
       private TextView tv_hora;
        private ImageView iv_item;


        public MyViewHolder(View itemView) {
            super(itemView);

            tv_titulo = (TextView) itemView.findViewById(R.id.tv_titulo_item);
            tv_hora = (TextView) itemView.findViewById(R.id.tv_hora_item);
            iv_item = (ImageView) itemView.findViewById(R.id.iv_item);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (mRecyclerItemClickListner != null) {

                mRecyclerItemClickListner.onClickItemListner(view, getAdapterPosition());
            }
        }
    }
}
