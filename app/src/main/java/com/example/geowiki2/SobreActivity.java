package com.example.geowiki2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.net.URL;

public class SobreActivity extends AppCompatActivity {
    private Button voltarSobreButton;
    private Button saibaMaisButton;

    //private URL urlLinkedin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sobre);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        voltarSobreButton = findViewById(R.id.voltarSobreButton);
        saibaMaisButton = findViewById(R.id.saibaMaisButton);

        voltarSobreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        saibaMaisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirLinkedin(view);
            }
        });
    }

    public void abrirLinkedin(View view) {
        Uri uri = Uri.parse("https://www.linkedin.com/in/monick-evelyn-36b544339/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}