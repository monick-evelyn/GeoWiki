package com.example.geowiki2.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.geowiki2.objetos.Rocha;

import java.util.List;

@Dao
public interface RochaDao {
    //Insere rochas conforme o objeto do parametro
    @Insert
    public void inserir(Rocha... rocha);

    //Atualiza registros conforme o objeto do parametro
    /*@Update
    public void atualizar(Rocha... rocha);

    //Apaga registros conforme o objeto do parametro
    @Delete
    public void apagar(Rocha... rocha);*/

    //Retorna todas as rochas
    @Query("SELECT * FROM rocha")
    public List<Rocha> getTodasAsRochas();

    //Pesquisa a rocha pelo id
    @Query("SELECT * FROM rocha WHERE id_rocha = :idRocha")
    public Rocha getRochaPorId(int idRocha);

    //Pesquisa as rochas pelo tipo - vai ser utilizado em consultar
    @Query("SELECT * FROM rocha WHERE tipo = :tipo ORDER BY nome ASC")
    public List<Rocha> getRochasPorTipo(String tipo);

    //Pesquisa a rocha pelo nome digitado - vai ser utilizado no Shearch View
    @Query("SELECT * FROM rocha WHERE nome = :nome")
    public Rocha getRochasPorNome(String nome);

    //Apaga todos os registros do BD
    /*@Query("DELETE FROM rocha")
    public void apagarTodas();*/
}
