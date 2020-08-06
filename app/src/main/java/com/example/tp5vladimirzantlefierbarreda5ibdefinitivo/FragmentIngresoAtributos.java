package com.example.tp5vladimirzantlefierbarreda5ibdefinitivo;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;


public class FragmentIngresoAtributos extends Fragment implements  View.OnClickListener {
ImageView visualizadorImageView;

Button btnSiguienteAtributo;
CheckBox chkbxBarba;
Boolean checkedBarba=false;
CheckBox chkbxSonrisa;
Boolean checkedSonrisa=false;
CheckBox chkbxEstadoDeAnimo;
Boolean checkedEstadoAnime=true;

    public View onCreateView(LayoutInflater infladorDeLayouts2, ViewGroup grupoDeLaVista2, Bundle Datos2) {
        View vistaADevolver;
        vistaADevolver= infladorDeLayouts2.inflate(R.layout.layout_ingreso_atributos, grupoDeLaVista2,false);



        MainActivity mainActivity;
        mainActivity = (MainActivity) getActivity();


        visualizadorImageView = vistaADevolver.findViewById(R.id.idImageViewAtributo);
        visualizadorImageView.setImageBitmap(mainActivity.fotoElegida);

        btnSiguienteAtributo = vistaADevolver.findViewById(R.id.buttonSiguienAtributos);
        btnSiguienteAtributo.setOnClickListener(this);

        chkbxBarba = vistaADevolver.findViewById(R.id.checkboxBarba);
        chkbxBarba.setOnClickListener(this);

        chkbxSonrisa = vistaADevolver.findViewById(R.id.checkboxSonrisa);
        chkbxSonrisa.setOnClickListener(this);

        chkbxEstadoDeAnimo = vistaADevolver.findViewById(R.id.checkboxEstadoAnimo);
        chkbxEstadoDeAnimo.setOnClickListener(this);






        return vistaADevolver;

    }


    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonSiguienAtributos:

                break;

            case R.id.checkboxBarba:
                if (checkedBarba==false){
                    checkedBarba=true;
                }
                else
                {
                    checkedBarba=false;
                }
                break;


            case R.id.checkboxSonrisa:

                if (checkedSonrisa==false)
                {
                    checkedSonrisa=true;
                }
                else
                {
                    checkedSonrisa=false;
                }

                break;


            case R.id.checkboxEstadoAnimo:
                if (checkedEstadoAnime==false)
                {
                    checkedEstadoAnime=true;
                }
                else
                {
                    checkedEstadoAnime=false;
                }

                break;



        }
    }

}
