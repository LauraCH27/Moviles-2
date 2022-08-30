package com.example.ventasvehiculo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ClsOpenHelper extends SQLiteOpenHelper {
    public ClsOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table TblLVehiculo(placa text primary key," +
                "marca text not null,modelo text not null,valor intiger not null," +
                "activo text not null default'si' )");

        sqLiteDatabase.execSQL("create table TBLFactura(cod_factura AUTO_INCREMENT primary key," +
                "fecha text not null,placa text not null,activo text not  null default 'si',estado text not null default'HABILITADA'," +
                "constraint pk_factura foreign key(placa) references TblVehiculos(placa))") ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(("DROP TABLE TblFactura"));{
            onCreate(sqLiteDatabase);
        }

        sqLiteDatabase.execSQL(("DROP TABLE TblVehiculo"));{
            onCreate(sqLiteDatabase);
        }
    }
}

