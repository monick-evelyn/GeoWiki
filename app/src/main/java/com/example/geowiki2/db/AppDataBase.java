package com.example.geowiki2.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.geowiki2.objetos.Rocha;

@Database(entities = {Rocha.class}, version =  12)
public abstract class AppDataBase extends RoomDatabase{
    private static volatile AppDataBase INSTANCE;

    public static AppDataBase getInstance(Context context) {
        if (INSTANCE == null){
            synchronized (AppDataBase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDataBase.class, "rocha_br")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public  abstract  RochaDao getRochaDao();
}