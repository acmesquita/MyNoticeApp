package com.catharina.mynoticeapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.catharina.mynoticeapp.R;
import com.catharina.mynoticeapp.modelo.Notice;

import java.util.List;

/**
 * Created by catharina on 17/09/16.
 */
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    private Context context;
    private List<Notice> notices;

    public NoticeAdapter(Context context, List<Notice> notices) {
        this.context = context;
        this.notices = notices;
    }

    public List<Notice> getNotices() {
        return notices;
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
    }

    @Override
    public NoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_notice, parent, false);
        NoticeViewHolder noticeViewHolder = new NoticeViewHolder(v);
        return noticeViewHolder;
    }

    @Override
    public void onBindViewHolder(NoticeViewHolder holder, int position) {
        Notice notice = notices.get(position);

        holder.titulo.setText(notice.getTitulo());
        holder.dataCriacao.setText(notice.getDate());
        holder.corpo.setText(notice.getCorpo());
    }

    @Override
    public int getItemCount() {
        return notices.size();
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder{

        TextView titulo;
        TextView dataCriacao;
        TextView corpo;

        public NoticeViewHolder(View itemView) {
            super(itemView);

            titulo = (TextView) itemView.findViewById(R.id.titulo);
            dataCriacao = (TextView) itemView.findViewById(R.id.data_criacao);
            corpo = (TextView) itemView.findViewById(R.id.corpo);
        }

    }
}
