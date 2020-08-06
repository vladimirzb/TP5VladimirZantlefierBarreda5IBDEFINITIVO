package com.example.tp5vladimirzantlefierbarreda5ibdefinitivo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentEstadistica1 extends Fragment
{

    TextView txtviewAEstadiBarba;
    TextView txtviewEstadiSonrisa;
    TextView txtviewEstadiEstadoDeAnimo;
    public View onCreateView(LayoutInflater infladorDeLayouts2, ViewGroup grupoDeLaVista2, Bundle Datos2) {
        View vistaADevolver;
        vistaADevolver= infladorDeLayouts2.inflate(R.layout.layout_estadistica1, grupoDeLaVista2,false);



        MainActivity mainActivity;
        mainActivity = (MainActivity) getActivity();

        txtviewAEstadiBarba = vistaADevolver.findViewById(R.id.textviewbarbaAtributoEstadistica);
        txtviewEstadiEstadoDeAnimo = vistaADevolver.findViewById(R.id.textviewEstadoAnimeEstadis);
        txtviewEstadiSonrisa = vistaADevolver.findViewById(R.id.textviewSonrisaEstadis1);

        txtviewAEstadiBarba.setVisibility(View.INVISIBLE);
        txtviewEstadiEstadoDeAnimo.setVisibility(View.INVISIBLE);
        txtviewEstadiSonrisa.setVisibility(View.INVISIBLE);

        if (mainActivity.atributoSonrisa==true)
        {
            txtviewEstadiSonrisa.setVisibility(View.VISIBLE);

        }

        if (mainActivity.atributoEstadoAnimo==true)
        {
            txtviewEstadiEstadoDeAnimo.setVisibility(View.VISIBLE);


        }

        if (mainActivity.atributoBarba==true)
        {
            txtviewAEstadiBarba.setVisibility(View.VISIBLE);

        }









        return vistaADevolver;

    }

}
