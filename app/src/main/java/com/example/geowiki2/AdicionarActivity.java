package com.example.geowiki2;

import android.content.Intent;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.geowiki2.db.AppDataBase;
import com.example.geowiki2.objetos.Rocha;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.Base64;

public class AdicionarActivity extends AppCompatActivity {
    // Inicialização de variáveis
    private EditText nomeEditText;
    private Spinner tipoSpinner;
    private EditText classificacaoMineEditText;
    private EditText corEditText;
    private EditText texturaEditText;
    private EditText durezaEditText;
    private EditText densidadeEditText;
    private ImageView rochaImageView;

    private Button selecionarImgButton;
    private Button adicionarButton;
    private Button voltarButton;

    private Uri uriSelecionada;
    private String rochaImgString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adicionar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ligando variáveis aos elementos do layout
        nomeEditText = findViewById(R.id.nomeEditText);
        tipoSpinner = findViewById(R.id.tipoSpinner);
        classificacaoMineEditText = findViewById(R.id.classificacaoMineEditText);
        corEditText = findViewById(R.id.corEditText);
        texturaEditText = findViewById(R.id.texturaEditText);
        durezaEditText = findViewById(R.id.durezaEditText);
        densidadeEditText = findViewById(R.id.densidadeEditText);
        rochaImageView = findViewById(R.id.rochaImageView);
        adicionarButton = findViewById(R.id.adicionarButton);
        selecionarImgButton = findViewById(R.id.selecionarImgButton);
        voltarButton = findViewById(R.id.voltarButton);

        // Acessa os dados do banco de dados
        AppDataBase instance = AppDataBase.getInstance(getApplicationContext());

        // Botão para selecionar imagem da galeria
        selecionarImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarFoto(); // Método para abrir a galeria
            }
        });

        // Botão para adicionar os dados
        adicionarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("AdicionarActivity", "Coletando dados");

                // Coleta de dados digitados nos campos
                String nome = nomeEditText.getText().toString().trim();
                String tipo = tipoSpinner.getSelectedItem().toString().trim();
                String classificacaoMinerologica = classificacaoMineEditText.getText().toString().trim();
                String cor = corEditText.getText().toString().trim();
                String textura = texturaEditText.getText().toString().trim();
                String densidadeString = densidadeEditText.getText().toString().trim();
                String durezaString = durezaEditText.getText().toString().trim();

                // Obter o drawable da ImageView
                Drawable drawable = rochaImageView.getDrawable();
                //Log.d("AdicionarActivity", "Drawable atual: " + (drawable != null ? "OK" : "NULO"));

                //validação dos campos em branco
                if (nome.isEmpty() || tipo.isEmpty() || classificacaoMinerologica.isEmpty() || cor.isEmpty() ||
                        textura.isEmpty() || densidadeString.isEmpty() || durezaString.isEmpty() || drawable == null) {
                    Toast.makeText(getApplicationContext(), "Preencha os campos obrigatórios!", Toast.LENGTH_SHORT).show();
                    return;
                    //valida a img
                } else if (!(drawable instanceof android.graphics.drawable.BitmapDrawable)) {
                    Toast.makeText(getApplicationContext(), "Imagem inválida!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // Log para validar o tamanho do array de bytes
                    //Log.d("AdicionarActivity", "Tamanho do array de bytes: " + (rochaImg != null ? rochaImg.length : "NULO"));

                    try {
                        // Conversão de string para int e para double
                        int dureza = Integer.parseInt(durezaString);
                        double densidade = Double.parseDouble(densidadeString);

                        if (dureza < 0 || dureza > 10) {
                            Toast.makeText(getApplicationContext(), "Dureza inválida! A escala Mohs varia de 0 a 10", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        else {
                            // Atribui os dados ao objeto Rocha
                            Rocha rocha = new Rocha(rochaImgString, nome, tipo, classificacaoMinerologica, cor, textura, dureza, densidade);
                            // Insere os dados do objeto no banco de dados
                            instance.getRochaDao().inserir(rocha);

                            //Log.d("AdicionarActivity", "Imagem no objeto: " + rocha.getImgRocha());

                            Toast.makeText(getApplicationContext(), "Rocha adicionada com sucesso!", Toast.LENGTH_LONG).show();

                            // Limpa os campos
                            nomeEditText.setText("");
                            classificacaoMineEditText.setText("");
                            corEditText.setText("");
                            texturaEditText.setText("");
                            durezaEditText.setText("");
                            densidadeEditText.setText("");
                            rochaImageView.setImageDrawable(null);
                        }
                    } catch(Exception e){
                            Toast.makeText(getApplicationContext(), "Erro ao salvar a rocha!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }

        });

        // Botão para voltar para a tela inicial
        voltarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // Método para abrir a galeria
    public void selecionarFoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*"); //aceita somente imagens
        startActivityForResult(intent, 2);
    }

    private Bitmap redimensionarImagem(Bitmap original, int larguraMax, int alturaMax) {
        int largura = original.getWidth();
        int altura = original.getHeight();

        float proporcao = Math.min((float) larguraMax / largura, (float) alturaMax / altura);

        int novaLargura = Math.round(largura * proporcao);
        int novaAltura = Math.round(altura * proporcao);

        return Bitmap.createScaledBitmap(original, novaLargura, novaAltura, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent dados) {
        super.onActivityResult(requestCode, resultCode, dados);
        if (requestCode == 2) {
            uriSelecionada = dados.getData(); // Coleta a URI da imagem selecionada
            //Log.d("AdicionarActivity", "URI selecionada: " + uriSelecionada);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriSelecionada);
                //Log.d("AdicionarActivity", "Bitmap carregado: largura=" + bitmap.getWidth() + ", altura=" + bitmap.getHeight());
                // Redimensiona a imagem para um tamanho adequado (exemplo: 1024x1024)
                bitmap = redimensionarImagem(bitmap, 1024, 1024);
                rochaImageView.setImageBitmap(bitmap); // Exibe a imagem no ImageView

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //comprimi a img
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream); //MUDEI O TAMANHHO
                byte[] rochaImgArray = stream.toByteArray();
                //transfere de array pra string
                rochaImgString = Base64.getEncoder().encodeToString(rochaImgArray);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Erro ao carregar a imagem", Toast.LENGTH_SHORT).show();
                //Log.e("AdicionarActivity", "Erro ao carregar a imagem: ", e);
            }
        }
    }
}