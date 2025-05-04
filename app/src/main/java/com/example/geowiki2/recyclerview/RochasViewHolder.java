package com.example.geowiki2.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geowiki2.R;

public class RochasViewHolder extends RecyclerView.ViewHolder {
    private TextView nomeRochaTextView;
    private TextView classificacaoMineTextView;
    private ImageView rochaImageViewItem;
    //private CardView cardRocha;

    public RochasViewHolder(View itemView) {
        super(itemView);
        nomeRochaTextView = itemView.findViewById(R.id.nomeRochaTextView);
        classificacaoMineTextView = itemView.findViewById(R.id.classificacaoMineTextView);
        rochaImageViewItem = itemView.findViewById(R.id.rochaImageViewItem);
        //cardRocha = itemView.findViewById(R.id.cardRocha);
    }

    public TextView getNomeRochaTextView() {
        return nomeRochaTextView;
    }
    public void setNomeRochaTextView(TextView nomeRochaTextView) {
        this.nomeRochaTextView = nomeRochaTextView;
    }

    public TextView getClassificacaoMineTextView() {
        return classificacaoMineTextView;
    }
    public void setClassificacaoMineTextView(TextView classificacaoMineTextView) {
        this.classificacaoMineTextView = classificacaoMineTextView;
    }

    public ImageView getRochaImageViewItem() {
        return rochaImageViewItem;
    }
    public void setRochaImageViewItem(ImageView rochaImageViewItem) {
        this.rochaImageViewItem = rochaImageViewItem;
    }

    /*public CardView getCardView() {
        return cardRocha;
    }*/
}
