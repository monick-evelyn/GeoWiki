package com.example.geowiki2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.geowiki2.db.AppDataBase;
import com.example.geowiki2.objetos.Rocha;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button telaCadastrarButton;
    private Button telaVisualizarButton;
    private Button sobreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        new Thread(
                () -> {
                    MobileAds.initialize(this, initializationStatus -> {});
                })
                .start();


        telaCadastrarButton = findViewById(R.id.telaCadastrarButton);
        telaVisualizarButton = findViewById(R.id.telaVisualizarButton);
        sobreButton = findViewById(R.id.sobreButton);

        telaCadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdicionarActivity.class);
                startActivity(intent);
            }
        });

        telaVisualizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ConsultarActivity.class);
                startActivity(intent1);
            }
        });

        sobreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(), SobreActivity.class);
                startActivity(intent3);
            }
        });

    }

}