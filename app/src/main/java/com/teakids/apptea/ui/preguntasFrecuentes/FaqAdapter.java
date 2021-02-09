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
import roomsqlite.entidades.Faq;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.FaqHolder> implements View.OnClickListener{

    private  View.OnClickListener listener;
    private final LayoutInflater mInflater;
    private List<Faq> faqList;
    private List<Faq> faqListFull;
    AdministarSesion idioma ;


    class FaqHolder extends RecyclerView.ViewHolder {
        private final TextView faqItemView;


        private FaqHolder(View itemView){
            super(itemView);
            faqItemView = itemView.findViewById((R.id.txt_faq));
        }
    }


   FaqAdapter(Context context){
        mInflater = LayoutInflater.from(context);
       idioma = new AdministarSesion(context);
    }

    @Override
    public FaqAdapter.FaqHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = mInflater.inflate(R.layout.recycle_item_faq,parent, false);

        itemview.setOnClickListener(this);

        return new FaqAdapter.FaqHolder(itemview);
    }

    @Override
    public void onBindViewHolder(FaqAdapter.FaqHolder holder, int position) {
        int mposition = position;
        if (faqList != null && position < faqList.size()) {
            Faq current = faqList.get(position);

            System.out.println("Idioma es  " + idioma.getIdioma());
            if(idioma.getIdioma()==1){
                holder.faqItemView.setText(current.getFaq_info());
            }else{
                holder.faqItemView.setText(current.getFaq_question());
            }
            holder.setIsRecyclable(false);
        }else{
            holder.faqItemView.setText("No existe ninguna categoria para habilidades cotidianas");
            holder.setIsRecyclable(false);
        }
    }

    void setFaq(List<Faq> faqs){

        faqList = faqs;
        notifyDataSetChanged();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        if(faqList != null)
            return faqList.size();
        else
            return 0;
    }


    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    @Override
    public void onViewRecycled(@NonNull FaqAdapter.FaqHolder holder) {
        super.onViewRecycled(holder);
        holder.faqItemView.setText(null);
        holder.setIsRecyclable(false);
    }
}
