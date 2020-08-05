package com.example.tp5vladimirzantlefierbarreda5ibdefinitivo;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class FragmentIngresoFoto extends Fragment implements  View.OnClickListener {
Button btnelegirFoto;
Button btnsacarFoto;
ImageView imgViewvisualziadorImgIngresada;
Button btnsiguienteFrgament;

    SharedPreferences preferences;

int codigoPedirPermiso=1;
    public View onCreateView(LayoutInflater infladorLayout,  ViewGroup GrupoDeLaVista, Bundle Datos) {
        View vistaAdevolver;
        vistaAdevolver = infladorLayout.inflate(R.layout.layout_ingreso_foto,GrupoDeLaVista, false);

        btnelegirFoto= vistaAdevolver.findViewById(R.id.buttonElegirFoto);

        btnsacarFoto = vistaAdevolver.findViewById(R.id.buttonSacarFoto);

        btnsiguienteFrgament = vistaAdevolver.findViewById(R.id.buttonSiguiente);
        btnsiguienteFrgament.setOnClickListener(this);
        imgViewvisualziadorImgIngresada = vistaAdevolver.findViewById(R.id.imageViewFotoSacada);

        Log.d("Inicio","Inicializo el SharedPreferences");
        preferences  = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);//Buscamos la activity para encontrar el contexto

        Log.d("Inicio","Me fijo si la app tiene permisos");
        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            Log.d("Inicio", "Me fijo si la App tiene permiso para usar la camara");
            btnsacarFoto.setEnabled(false);
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]
                            { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }, codigoPedirPermiso
            );
        } else{
            Log.d("Inicio","TIene permiso, habilito el boton de tomar fotos");
            btnsacarFoto.setEnabled(true);
        }

        return vistaAdevolver;

    }


    public void onClick(View vistaRecibida) {

        switch(vistaRecibida.getId()){

            case R.id.buttonSiguiente:

                MainActivity mainact;
                mainact = (MainActivity) getActivity();

                //Conseguimos la foto del imageview asignada por el ususario para pasarla al main activity
//                imgViewvisualziadorImgIngresada.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) imgViewvisualziadorImgIngresada.getDrawable();
                Bitmap fotoAmandar = drawable.getBitmap();
                mainact.procesarFotoIngresada(fotoAmandar);

                break;

            case R.id.buttonSacarFoto:

                break;


            case R.id.buttonElegirFoto:

                break;
        }

    }
}
