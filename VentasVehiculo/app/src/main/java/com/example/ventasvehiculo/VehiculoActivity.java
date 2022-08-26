package com.example.ventasvehiculo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class VehiculoActivity extends AppCompatActivity {

    EditText jetplaca,jetmarca,jetmodelo,jetvalor;
    CheckBox jcbactivo;
    ClsOpenHelper admin=new ClsOpenHelper(this,"Concesionario.db",null,1);
    String placa,marca,modelo,valor;
    Long resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);
    //Ocultar titulo por defeccto y asociar objetos Java con Xml
        getSupportActionBar().hide();
        jetplaca=findViewById(R.id.etplaca);
        jetmarca=findViewById(R.id.etmarca);
        jetmodelo=findViewById(R.id.etmodelo);
        jetvalor=findViewById(R.id.etvalor);
        jcbactivo=findViewById(R.id.cbactivo);
    }
    public void Guardar(View view){
        placa=jetplaca.getText().toString();
        marca=jetmarca.getText().toString();
        modelo =jetmodelo.getText().toString();
        valor=jetvalor.getText().toString();
        if (placa.isEmpty()||marca.isEmpty()||modelo.isEmpty()||valor.isEmpty()){
            Toast.makeText(this, "Todos los datos son requeridos", Toast.LENGTH_SHORT).show();
            jetplaca.requestFocus();
        }
        else {
            SQLiteDatabase db=admin.getWritableDatabase();
            ContentValues registro=new ContentValues();
            registro.put("placa",placa);
            registro.put("marca",marca);
            registro.put("modelo",modelo);
            registro.put("valor", Integer.parseInt(valor));
            resp=db.insert("TblLVehiculo",null,registro);
            if (resp>0){
                Toast.makeText(this,"Registro guardado",Toast.LENGTH_SHORT).show();
                Limpiar_campos();
            }
            else Toast.makeText(this,"Error en registro",Toast.LENGTH_SHORT).show();
            db.close();
        }
    }
    public void Consultar(View view){
        placa=jetplaca.getText().toString();
        if (placa.isEmpty()){
            Toast.makeText(this,"La placa no existe",Toast.LENGTH_SHORT).show();
            jetplaca.requestFocus();
        }
        else{
            SQLiteDatabase db=admin.getReadableDatabase();
            Cursor fila= db.rawQuery("select * from TblLVehiculo where placa='"+placa+"'",null);
            if(fila.moveToNext()){
                jetmarca.setText(fila.getString(1));
                jetmodelo.setText(fila.getString(2));
                jetvalor.setText(fila.getString(3));
                if(fila.getString(4).equals("si"))
                  jcbactivo.setChecked(true);
                else
                    jcbactivo.setChecked(false);
            }
            else Toast.makeText(this,"Vehiculo no registrado",Toast.LENGTH_SHORT).show();
            db.close();
        }

    }


    private void Limpiar_campos(){
        jetplaca.setText("");
        jetvalor.setText("");
        jetmodelo.setText("");
        jetmarca.setText("");
        jcbactivo.setChecked(false);
        jetplaca.requestFocus();
    }

    }
