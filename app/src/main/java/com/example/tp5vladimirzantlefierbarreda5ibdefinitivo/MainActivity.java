package com.example.tp5vladimirzantlefierbarreda5ibdefinitivo;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;

public class MainActivity extends Activity {
    FragmentManager adminFragment;
    FragmentTransaction transacFragments;

    Bitmap fotoElegida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Poner fragment
        adminFragment = getFragmentManager();

        Fragment frgIngresoFoto;
        frgIngresoFoto = new FragmentIngresoFoto();

        transacFragments= adminFragment.beginTransaction();
        transacFragments.replace(R.id.AlojadorDeFragment, frgIngresoFoto);
        transacFragments.commit();


    }

    public void procesarFotoIngresada(Bitmap fotoelegi)
    {
        fotoElegida=fotoelegi;

        Fragment frgIngresoAtributos;
        frgIngresoAtributos = new FragmentIngresoAtributos();

        transacFragments=adminFragment.beginTransaction();
        transacFragments.replace(R.id.AlojadorDeFragment, frgIngresoAtributos);
        transacFragments.commit();
    }
}