package com.example.geowiki2.recyclerview;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geowiki2.ConsultarActivity;
import com.example.geowiki2.ListaActivity;
import com.example.geowiki2.R;
import com.example.geowiki2.db.AppDataBase;
import com.example.geowiki2.objetos.Rocha;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class RochasAdapter extends RecyclerView.Adapter<RochasViewHolder> {

    private List<Rocha> listaDeRochas;
    private int posicaoSelecionada = RecyclerView.NO_POSITION; // **Novo campo**

    public RochasAdapter( List<Rocha> listaDeRochas) {
        this.listaDeRochas = listaDeRochas;
    }

    @NonNull
    @Override
    public RochasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rochas, parent, false);
        RochasViewHolder rochasViewHolder = new RochasViewHolder(view);
        return rochasViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RochasViewHolder holder, int position) {
        //obtém o obj Rocha da lista
        Rocha rocha = listaDeRochas.get(position);
        String nome = rocha.getNome();
        String classificacaoMinerologica = rocha.getClassificacaoMinerologica();
        String textura = rocha.getTextura();
        String cor = rocha.getCor();
        String tipo = rocha.getTipo();

        int dureza = rocha.getDureza();
        double densidade = rocha.getDensidade();

        String durezaString = String.valueOf(dureza);
        String densidadeString = String.valueOf(densidade); //fazer format

        String fotoString = rocha.getImgRocha();

        //muda o holder
        holder.getNomeRochaTextView().setText(nome);
        //holder.getClassificacaoMineTextView().setText(classificacaoMinerologica);
        //---------------------Destransforma img de str para bitmap-------------------------

        if (fotoString != null){
            if (!fotoString.equals("")) {
                byte[] imageInBytes = null;
                try {
                    imageInBytes = Base64.getDecoder().decode(fotoString);
                    Bitmap imagemModificada = BitmapFactory.decodeByteArray(imageInBytes, 0, imageInBytes.length);
                    holder.getRochaImageViewItem().setImageBitmap(imagemModificada);
                } catch (Exception e) {
                    holder.getRochaImageViewItem().setImageBitmap(null);
                    Log.d("RochasAdapter", "Array de bytes: " + imageInBytes);
                }
            }
        }
        //---------------------Destransforma img de str para bitmap-------------------------

        // **Novo código: Altera o fundo do item com base na posição selecionada**
        /*holder.getCardView().setCardBackgroundColor(posicaoSelecionada == position ?
                holder.itemView.getContext().getResources().getColor(R.color.marromescuro) :
                holder.itemView.getContext().getResources().getColor(android.R.color.transparent));*/
        holder.itemView.setBackgroundColor(posicaoSelecionada == position ?
                holder.itemView.getContext().getResources().getColor(R.color.marromescuro) :
                holder.itemView.getContext().getResources().getColor(android.R.color.transparent));

        Log.d("ListaActivity", "posicaoSelecionada: " + posicaoSelecionada);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Atualiza a posição selecionada
                int previaDePosicao = posicaoSelecionada;
                posicaoSelecionada = holder.getAdapterPosition();

                // Notifica a mudança no item anterior e no atual
                notifyItemChanged(previaDePosicao);
                notifyItemChanged(posicaoSelecionada);

                Log.d("ListaActivcity", "posicaoSelecionada: " + posicaoSelecionada);

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                View dialogView = LayoutInflater.from(holder.itemView.getContext())
                        .inflate(R.layout.dialog_info_rocha_item, null);
                builder.setView(dialogView);

                Dialog dialog = builder.create();
                //dialog.setContentView(R.layout.dialog_info_rocha_item);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                TextView nomeTxt = dialogView.findViewById(R.id.nomeTextViewDialog);
                TextView tipoTxt = dialogView.findViewById(R.id.tipoTextViewDialog);
                TextView corTxt = dialogView.findViewById(R.id.corTextViewDialog);
                TextView texturaTxt  = dialogView.findViewById(R.id.texturaTextViewDialog);
                TextView durezaTxt  = dialogView.findViewById(R.id.durezaTextViewDialog); //converter pra str
                TextView densidadeTxt  = dialogView.findViewById(R.id.densidadeTextViewDialog); //converter pra str

                Button fecharButton = dialogView.findViewById(R.id.fecharSedButton);
                Button classificacaoButton = dialogView.findViewById(R.id.classificacaoButtonDialog);
                Button siteRochasButton = dialogView.findViewById(R.id.siteRochasButton);

                //passar img view para o dialog??
                ImageView dialogImageView = dialogView.findViewById(R.id.rochaImageViewDialog);
                Log.d("ListaActivity", "imageView: " + dialogImageView);

                nomeTxt.setText(nome);
                tipoTxt.setText("Tipo: " + tipo);
                corTxt.setText("Cor: " + cor);
                texturaTxt.setText("Textura: " + textura);
                durezaTxt.setText("Dureza: " + durezaString + " mohs");
                densidadeTxt.setText("Densidade: " + densidadeString + "g/cm³");

                // Configura a imagem no dialog - pega fotoString e converte para bitmap para mostrar no dialog
                if (fotoString != null && !fotoString.isEmpty()) {
                    try {
                        byte[] imageInBytes = Base64.getDecoder().decode(fotoString);
                        Bitmap imagemModificada = BitmapFactory.decodeByteArray(imageInBytes, 0, imageInBytes.length);
                        dialogImageView.setImageBitmap(imagemModificada); //muda a ImgView com a imgBitmap
                    } catch (Exception e) {
                        Log.e("RochasAdapter", "Erro ao carregar imagem no dialog", e);
                        dialogImageView.setImageResource(R.drawable.camera); // Imagem padrão
                    }
                } else {
                    dialogImageView.setImageResource(R.drawable.camera); // Imagem padrão
                }


                fecharButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                classificacaoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialogClassificacao = new Dialog(holder.itemView.getContext());
                        dialogClassificacao.setContentView(R.layout.dialog_info_rocha_classificacao_minerologica);
                        dialogClassificacao.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                        TextView classificacaoTxtView = dialogClassificacao.findViewById(R.id.classificacaoTextViewDialog);
                        Button fecharClassificacaoButton = dialogClassificacao.findViewById(R.id.fecharClassificacaoButton);

                        fecharClassificacaoButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogClassificacao.dismiss();
                            }
                        });

                        classificacaoTxtView.setText(classificacaoMinerologica);

                        dialogClassificacao.show();
                    }
                });

                siteRochasButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tipoSite = "";
                        switch (tipo) {
                            case "Magmática":
                                tipoSite = "igneas";
                                break;
                            case "Sedimentar":
                                tipoSite = "sedimentares";
                                break;
                            case "Metamórfica":
                                tipoSite = "metamorficas";
                                break;
                            default:
                                tipoSite = "";
                                break;
                        }

                        Uri uri = Uri.parse("https://didatico.igc.usp.br/rochas/"+ tipoSite + "/"+ nome +"/");
                        Intent intentSite = new Intent(Intent.ACTION_VIEW, uri);
                        holder.itemView.getContext().startActivity(intentSite);
                    }
                });

                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        //return listaDeRochas.size();
        return listaDeRochas != null ? listaDeRochas.size() : 0;
    }

    //mudar o rv de acordo com o que for filtrando nba pesquisa
    public void filtrarLista(ArrayList<Rocha> filtroLista) {
        listaDeRochas = filtroLista;
        notifyDataSetChanged();
    }
}
