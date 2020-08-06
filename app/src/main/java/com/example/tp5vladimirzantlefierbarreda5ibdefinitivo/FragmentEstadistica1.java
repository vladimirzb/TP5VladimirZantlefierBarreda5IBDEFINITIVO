package com.example.tp5vladimirzantlefierbarreda5ibdefinitivo;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


//AZURE
import com.microsoft.projectoxford.face.*;
import com.microsoft.projectoxford.face.contract.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class FragmentEstadistica1 extends Fragment
{

    TextView txtviewAEstadiBarba;
    TextView txtviewEstadiSonrisa;
    TextView txtviewEstadiEstadoDeAnimo;

    Bitmap fotoElegidaEstadis;
    ProgressDialog  dialogoDeProgreso;

    // Azure
    FaceServiceRestClient servicioPreocesamientoImagenes;

    public View onCreateView(LayoutInflater infladorDeLayouts2, ViewGroup grupoDeLaVista2, Bundle Datos2) {
        View vistaADevolver;
        vistaADevolver= infladorDeLayouts2.inflate(R.layout.layout_estadistica1, grupoDeLaVista2,false);

        dialogoDeProgreso = new ProgressDialog(this.getActivity());

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


        Log.d("Azure","Credenciales API");
        String apiEndpoint = "https://brazilsouth.api.cognitive.microsoft.com/";
        String subscriptionKey = "994f54a94c9a48b9a81ce887943b9c08";

        try {
            Log.d("Azure","Instancio el servicio");
            servicioPreocesamientoImagenes = new FaceServiceRestClient(apiEndpoint,subscriptionKey);
            Log.d("Azure", "Funciono bien");
        } catch (Exception error)
        {
            Log.d("Azure", "Error api al principio: "  + error.getMessage());
        }


        fotoElegidaEstadis = mainActivity.fotoElegida; //La foto que eligio el user
        procesarImagenObtenida(fotoElegidaEstadis);



        return vistaADevolver;

    }


    public void procesarImagenObtenida(final Bitmap imagenAprocesar)
    {
        ByteArrayOutputStream streamSalida = new ByteArrayOutputStream();
        imagenAprocesar.compress(Bitmap.CompressFormat.JPEG,100,streamSalida);
        ByteArrayInputStream streamEntrada = new ByteArrayInputStream(streamSalida.toByteArray());

        class procesarImagen extends AsyncTask<InputStream, String, Face[]>
        {
            @Override
            protected Face[] doInBackground(InputStream... inputStreams) {
                return new Face[0];
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialogoDeProgreso.show();
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
                dialogoDeProgreso.setMessage(values[0]);
            }

            @Override
            protected void onPostExecute(Face[] faces) {
                super.onPostExecute(faces);
            }

        }

        procesarImagen miTarea = new procesarImagen();
        miTarea.execute(streamEntrada);
    }

}
