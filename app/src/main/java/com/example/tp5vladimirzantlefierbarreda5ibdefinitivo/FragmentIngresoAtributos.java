package com.example.tp5vladimirzantlefierbarreda5ibdefinitivo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class FragmentIngresoAtributos extends Fragment {
ImageView visualizadorImageView;
    public View onCreateView(LayoutInflater infladorDeLayouts2, ViewGroup grupoDeLaVista2, Bundle Datos2) {
        View vistaADevolver;
        vistaADevolver= infladorDeLayouts2.inflate(R.layout.layout_ingreso_atributos, grupoDeLaVista2,false);



        MainActivity mainActivity;
        mainActivity = (MainActivity) getActivity();


        visualizadorImageView = vistaADevolver.findViewById(R.id.idImageViewAtributo);
        visualizadorImageView.setImageBitmap(mainActivity.fotoElegida);




        return vistaADevolver;

    }
}
