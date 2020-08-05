package com.example.tp5vladimirzantlefierbarreda5ibdefinitivo;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class FragmentIngresoFoto extends Fragment implements  View.OnClickListener {
Button btnelegirFoto;
Button btnsacarFoto;
ImageView imgViewvisualziadorImgIngresada;
Button btnsiguienteFrgament;
    public View onCreateView(LayoutInflater infladorLayout,  ViewGroup GrupoDeLaVista, Bundle Datos) {
        View vistaAdevolver;
        vistaAdevolver = infladorLayout.inflate(R.layout.layout_ingreso_foto,GrupoDeLaVista, false);

        btnelegirFoto= vistaAdevolver.findViewById(R.id.buttonSacarFoto);

        btnsacarFoto = vistaAdevolver.findViewById(R.id.buttonElegirFoto);

        btnsiguienteFrgament = vistaAdevolver.findViewById(R.id.buttonSiguiente);
        btnsiguienteFrgament.setOnClickListener(this);
        imgViewvisualziadorImgIngresada = vistaAdevolver.findViewById(R.id.imageViewFotoSacada);

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
