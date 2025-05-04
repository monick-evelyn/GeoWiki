package com.example.geowiki2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geowiki2.db.AppDataBase;
import com.example.geowiki2.objetos.Rocha;
import com.example.geowiki2.recyclerview.RochasAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {
    private TextView nomeTextView;
    private SearchView pesquisarSearchView;
    private RecyclerView rochasRecyclerView;
    private Button voltarConsultarButton;    //lista de array

    //variaveis da classe adapter
    private RochasAdapter rochasAdapter;

    private List<Rocha> listaDeRochas;

    //array filtrada
    private ArrayList<Rocha> rochasArrayListFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //conexão de variaveis aos elementos do activity
        nomeTextView = findViewById(R.id.nomeTextView);
        pesquisarSearchView = findViewById(R.id.pesquisarSearchView);
        voltarConsultarButton = findViewById(R.id.voltarConsultarButton);
        rochasRecyclerView = findViewById(R.id.rochasRecyclerView);

        //para mudar o nome conforme a seleção
        // pega o tipo indicado no botão da AcitivityColsultar
        Intent intent = getIntent();
        String nome = (String) intent.getStringExtra("nome");
        nomeTextView.setText(nome);

        AppDataBase instance = AppDataBase.getInstance(getApplicationContext());

        String tipo = "";
        switch (nome) {
            case "Magmáticas":
                tipo = "Magmática";
                break;
            case "Sedimentares":
                tipo = "Sedimentar";
                break;
            case "Metamórficas":
                tipo = "Metamórfica";
                break;
            default:
                tipo = "";
                break;
        }

        //acessa o BD
        //AppDataBase instance = AppDataBase.getInstance(getApplicationContext());
        //pega a lista de rochas conforme o tipo selecionado
        if (!tipo.equals("")) {
            listaDeRochas = instance.getRochaDao().getRochasPorTipo(tipo);
            //Log.d("ListaActivity", "Lista de rochas recuperada: " + listaDeRochas);
            rochasRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            rochasAdapter = new RochasAdapter(listaDeRochas);
            rochasRecyclerView.setAdapter(rochasAdapter);

        } else {
            listaDeRochas = new ArrayList<>(); //lista vazia
            nomeTextView.setText("Nenhuma rocha encontrada");
        }


        voltarConsultarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ConsultarActivity.class);
                startActivity(intent);
            }
        });

        pesquisarSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String nomePesquisa) {
                filtro(nomePesquisa);
                return false;
            }

            //filtra conforme for escrevendo
            @Override
            public boolean onQueryTextChange(String nomePesquisa) {
                filtro(nomePesquisa);
                return false;
            }
        });
    }

    public void filtro(String texto) {
        texto = texto.trim();
        // criar um novo array para filtrar os dados
        ArrayList<Rocha> listaFiltrada = new ArrayList<>();
        // faz um for para comparar os elementos da lista do rv conforme texto
        for (Rocha item : listaDeRochas) {
            // checar se o item percorrido no rv tem o texto digitado
            if (item.getNome().toLowerCase().contains(texto.toLowerCase())) {
                // adicionar o ite na lista filtrada
                listaFiltrada.add(item);
            }
        }

        if (listaFiltrada.isEmpty()) { //se for vazia
            Toast.makeText(this, "Rocha não encontrada!", Toast.LENGTH_SHORT).show();
            //rochasAdapter.filtrarLista(listaFiltrada);
        } else {
            // passar a lista filtrada no rv
            rochasAdapter.filtrarLista(listaFiltrada);
        }
    }
}