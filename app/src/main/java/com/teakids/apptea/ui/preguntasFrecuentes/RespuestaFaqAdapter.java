package com.teakids.apptea.ui.preguntasFrecuentes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teakids.apptea.R;
import com.teakids.apptea.utilidades.AdministarSesion;


import java.util.List;

import roomsqlite.entidades.RespuestaFaq;

public class RespuestaFaqAdapter extends RecyclerView.Adapter<RespuestaFaqAdapter.RespuestaFaqHolder> implements View.OnClickListener{

    private OnRespuestaListener mOnRespuestaListener;
    private final LayoutInflater mInflater;
    private List<RespuestaFaq> respuestaFaqList;
    AdministarSesion idioma ;

    @Override
    public void onClick(View v) {

    }

    class RespuestaFaqHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView respuestaItemView;
        OnRespuestaListener onRespuestaListener;

        public RespuestaFaqHolder(View itemView,OnRespuestaListener onRespuestaListener){
            super(itemView);
                respuestaItemView = itemView.findViewById(R.id.txt_respuesta_faq);
                this.onRespuestaListener = onRespuestaListener;
                itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }


    public RespuestaFaqAdapter(Context context, RespuestaFaqAdapter.OnRespuestaListener onRespuestaListener){
        mInflater = LayoutInflater.from(context);
        idioma = new AdministarSesion(context);
        this.mOnRespuestaListener = onRespuestaListener;
    }


    @Override
    public RespuestaFaqAdapter.RespuestaFaqHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = mInflater.inflate(R.layout.recycle_item_respuesta_faq,parent, false);

        return new RespuestaFaqHolder(itemview, mOnRespuestaListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RespuestaFaqHolder holder, int position) {

        if(respuestaFaqList !=null){
            RespuestaFaq current = respuestaFaqList.get(position);
            System.out.println("Idioma es  " + idioma.getIdioma());
            if(idioma.getIdioma()==1){
                holder.respuestaItemView.setText(current.getRespuesta());
            }else{
                holder.respuestaItemView.setText(current.getAnswer());
            }

            holder.setIsRecyclable(false);
        }else{
            holder.respuestaItemView.setText("No existe ninguna categoria para habilidades cotidianas");
        }

    }

    @Override
    public int getItemCount() {
        if(respuestaFaqList!= null)
            return respuestaFaqList.size();
        else
            return 0;
    }

    void setRespuesta(List<RespuestaFaq> respuestaFaqs){
        respuestaFaqList = respuestaFaqs;
        notifyDataSetChanged();
    }


    public interface OnRespuestaListener{
        void onRespuestaClick(RespuestaFaq posicion);
    }

    @Override
    public void onViewRecycled(@NonNull RespuestaFaqAdapter.RespuestaFaqHolder holder) {
        super.onViewRecycled(holder);
        holder.respuestaItemView.setText(null);
        holder.setIsRecyclable(false);
    }
}
