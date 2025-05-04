package com.example.geowiki2;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ConsultarActivity extends AppCompatActivity {
    //private Button voltarButton;
    private Button magmaticasButton;
    private Button sedimentaresButton;
    private Button metamorficasButton;

    private Button voltarMainButton;
    private Button infoMagmaticasButton;
    private Button infoSedimentaresButton;
    private Button infoMetamorficasButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consultar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        magmaticasButton = findViewById(R.id.magmaticasButton);
        sedimentaresButton = findViewById(R.id.sedimentaresButton);
        metamorficasButton = findViewById(R.id.metamorficasButton);
        voltarMainButton = findViewById(R.id.voltarMainButton);

        infoMagmaticasButton = findViewById(R.id.infoMagmaticasButton);
        infoSedimentaresButton = findViewById(R.id.infoSedimentaresButton);
        infoMetamorficasButton = findViewById(R.id.infoMetamorficasButton);


        infoMagmaticasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialogMagmaticas = new Dialog(ConsultarActivity.this);
                dialogMagmaticas.setContentView(R.layout.dialog_info_mag);
                dialogMagmaticas.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                // Coleta os elementos do diálogo
                Button intrusivasButton = dialogMagmaticas.findViewById(R.id.intrusivasButton);
                Button extrusivasButton = dialogMagmaticas.findViewById(R.id.extrusivasButton);
                Button fecharMagButton = dialogMagmaticas.findViewById(R.id.fecharMagButton);

                intrusivasButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialogIntrusivas = new Dialog(ConsultarActivity.this);
                        dialogIntrusivas.setContentView(R.layout.dialog_info_mag_intrusivas);
                        dialogIntrusivas.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                        Button fecharIntrusivaButton = dialogIntrusivas.findViewById(R.id.fecharIntrusivaButton);

                        fecharIntrusivaButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogIntrusivas.dismiss();
                            }
                        });

                        dialogIntrusivas.show();
                    }
                });


                extrusivasButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialogExtrusivas = new Dialog(ConsultarActivity.this);
                        dialogExtrusivas.setContentView(R.layout.dialog_info_mag_extrusivas);
                        dialogExtrusivas.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                        Button fecharExtrusivaButton = dialogExtrusivas.findViewById(R.id.fecharExtrusivaButton);

                        fecharExtrusivaButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogExtrusivas.dismiss();
                            }
                        });

                        dialogExtrusivas.show();

                    }
                });

                fecharMagButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogMagmaticas.dismiss(); // Fecha o dialog
                    }
                });

                dialogMagmaticas.show();
            }
        });

        infoSedimentaresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialogSedimentares = new Dialog(ConsultarActivity.this);
                dialogSedimentares.setContentView(R.layout.dialog_info_sed);
                dialogSedimentares.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Button fecharSedButton = dialogSedimentares.findViewById(R.id.fecharSedButton);

                fecharSedButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSedimentares.dismiss(); // Fecha o dialog
                    }
                });

                dialogSedimentares.show();

            }
        });

        infoMetamorficasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialogMetamorficas = new Dialog(ConsultarActivity.this);
                dialogMetamorficas.setContentView(R.layout.dialog_info_met);
                dialogMetamorficas.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Button fecharMetButton = dialogMetamorficas.findViewById(R.id.fecharMetButton);

                fecharMetButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogMetamorficas.dismiss(); // Fecha o dialog
                    }
                });

                dialogMetamorficas.show();
            }
        });

        voltarMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        magmaticasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = "Magmáticas";
                //Intent que leva para a classe ListaActivity, onde terá o rv
                Intent intentMag = new Intent(getApplicationContext(), ListaActivity.class);
                //Leva o nome do butão para ser aplicado no titulo da tela e no filtro do rv
                intentMag.putExtra("nome", nome);
                startActivity(intentMag);
            }
        });

        sedimentaresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String nome = "Sedimentares";
               Intent intentSed = new Intent(getApplicationContext(), ListaActivity.class);
               intentSed.putExtra("nome", nome);
               startActivity(intentSed);
            }
        });

        metamorficasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = "Metamórficas";
                Intent intentMeta = new Intent(getApplicationContext(), ListaActivity.class);
                intentMeta.putExtra("nome", nome);
                startActivity(intentMeta);
            }
        });
        /*voltarButton = findViewById(R.id.voltarButton);

        voltarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });*/
    }
}