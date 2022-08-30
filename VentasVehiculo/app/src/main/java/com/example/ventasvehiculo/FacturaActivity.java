package com.example.ventasvehiculo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class FacturaActivity extends AppCompatActivity {

    EditText jetplaca,jetmarca,jetmodelo,jetvalor,jetcod_factura,jetfecha;
    CheckBox jcbactivo;
    ClsOpenHelper admin=new ClsOpenHelper(this,"Concesionario.db",null,1);
    String placa,marca,modelo,valor;
    long resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);
        jetplaca=findViewById(R.id.etplaca);
        jetmarca=findViewById(R.id.etmarca);
        jetmodelo=findViewById(R.id.etmodelo);
        jetvalor=findViewById(R.id.etvalor);
        jcbactivo=findViewById(R.id.cbactivo);
        //jetcod_factura=findViewById(R.id.etcodfactura);
        jetfecha=findViewById(R.id.etfecha);
    }
    public void Consultar(View view){
        placa=jetplaca.getText().toString();
        if (placa.isEmpty()){
            Toast.makeText(this,"La placa no existe",Toast.LENGTH_SHORT).show();
            jetplaca.requestFocus();
        }
        else{
            SQLiteDatabase db=admin.getReadableDatabase();
            Cursor filafac= db.rawQuery("select * from TBLFactura where placa='"+placa+"'",null);
            if(filafac.moveToNext()){
                Cursor fila= db.rawQuery("select * from TblLVehiculo where placa='"+placa+"'",null);
                //sw=1;
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
}