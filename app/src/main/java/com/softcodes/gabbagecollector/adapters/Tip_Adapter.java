package com.softcodes.gabbagecollector.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.softcodes.gabbagecollector.R;
import com.softcodes.gabbagecollector.database.Tips_Model;


import java.util.List;

public class Tip_Adapter extends RecyclerView.Adapter<Tip_Adapter.TipsViewHolder> {

    private Context mCtx;
    private List<Tips_Model> product_dbList;


    private OnItemClickListenerTips mListener;

    public void setOnClickListenerTips(OnItemClickListenerTips listener) {
        mListener = listener;
    }
    public interface OnItemClickListenerTips {

        void onshareTip(int position);
    }
    @NonNull
    @Override
    public TipsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.tips_list, null);
        return new TipsViewHolder(view);
    }


    public Tip_Adapter(Context mCtx, List<Tips_Model> product_dbList) {
        this.mCtx = mCtx;
        this.product_dbList = product_dbList;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TipsViewHolder holder, int position) {
        Tips_Model s = product_dbList.get(position);

        Glide.with(mCtx)
                .load(s.getTimage())
                .centerCrop()
                .placeholder(R.drawable.logo)
                .into(holder.TImage);
        holder.TName.setText(s.getTname());
        holder.Tdescription.setText(s.getTdescription());
        holder.Tdoctor.setText(s.getTdrname());


    }

  


    @Override
    public int getItemCount() {
        return product_dbList.size();
    }
    public  class TipsViewHolder extends RecyclerView.ViewHolder {
        TextView TName,Tdescription,Tdoctor;
        ImageView TImage,share;

        public TipsViewHolder(@NonNull View itemView) {
            super(itemView);
            TName = itemView.findViewById(R.id.tipd_name);
            Tdescription = itemView.findViewById(R.id.tipd_description);
            TImage = itemView.findViewById(R.id.tips_image);
            Tdoctor = itemView.findViewById(R.id.posted_by);
            share = itemView.findViewById(R.id.share_tip);

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onshareTip(position);
                        }
                    }
                }
            });

        }

    }

}
