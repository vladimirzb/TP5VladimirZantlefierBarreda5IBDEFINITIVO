package com.example.tp5vladimirzantlefierbarreda5ibdefinitivo;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    ImageView imgvwResultadoEstadis;

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
        imgvwResultadoEstadis = vistaADevolver.findViewById(R.id.imageViewResultadoEstadis);

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
        String apiEndpoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0";
        String subscriptionKey = "072b3cadee714e05833025819eecc37b";

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
            protected Face[] doInBackground(InputStream... imagenAprocesar) {
                publishProgress("Detetactando caras");
                Face[] resultado = null;
                Log.d("ProcesarImagen", " Defino que atributos quiero procesar");
                FaceServiceClient.FaceAttributeType[] atributos;
                atributos = new FaceServiceClient.FaceAttributeType[]
                        {
                                FaceServiceClient.FaceAttributeType.Age,
                                FaceServiceClient.FaceAttributeType.Glasses,
                                FaceServiceClient.FaceAttributeType.Smile,
                                FaceServiceClient.FaceAttributeType.FacialHair,
                                FaceServiceClient.FaceAttributeType.Gender
                        };
                try {
                    Log.d("ProcesarImagen", "Llamo al procesamiento de la imagen: "+imagenAprocesar[0]);
                    resultado = servicioPreocesamientoImagenes.detect(imagenAprocesar[0],true,false,atributos);
                    Log.d("ProcesarImagen", "Volvi al procesamiento de la imagen");
                }
                catch (Exception error)
                {
                    Log.d("ProcesarImagen","Error "+error.getMessage());
                }


                return resultado ;
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
            protected void onPostExecute(Face[] resultado) {
                super.onPostExecute(resultado);
                dialogoDeProgreso.dismiss();

                if (resultado==null)
                {

                    //Error en procesamiento(mostrarlo en textview?)
                } else {
                    if (resultado.length>0)
                    {
                        Log.d("ProcesarImagen", "Mando a recuadrar las caras");
                        recuadrarCaras(imagenAprocesar,resultado);

                        Log.d("ProcesarImagen","Mando a procsar los resultados de cada cara");
                        procesareusltadosDecaras(resultado);
                    }else {
                        Log.d("ProcesarImagen","No se detecto ninguna cara");
                        //Mostrarlo en textview?
                    }
                }
            }

        }

        procesarImagen miTarea = new procesarImagen();
        miTarea.execute(streamEntrada);
    }

    void recuadrarCaras(Bitmap imagenOriginal, Face [] carasARecuadrar){
        Bitmap imagenADibujar;
        imagenADibujar= imagenOriginal.copy(Bitmap.Config.ARGB_8888,true);

        Log.d("RecuadraraCaras", "Armo el canvas y el pincel");
        Canvas lienzo;
        lienzo= new Canvas(imagenADibujar);
        Paint pincel;
        pincel= new Paint();

        pincel.setAntiAlias(true);
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setColor(Color.RED);
        pincel.setStrokeWidth(5);

        Log.d("RecuadrarCaras","Para cada cara recibida dibuo su rectangulo");
        for (Face unaCara:carasARecuadrar)
        {
            FaceRectangle rectanguloUnaCara;
            rectanguloUnaCara = unaCara.faceRectangle;

            lienzo.drawRect(rectanguloUnaCara.left,rectanguloUnaCara.top,rectanguloUnaCara.left + rectanguloUnaCara.width,
            rectanguloUnaCara.top+rectanguloUnaCara.height,pincel);
        }
        Log.d("RecuadrarCaras", "Pongo la imagen resultante en el ImageView");
        imgvwResultadoEstadis.setImageBitmap(imagenADibujar);



    }

    void procesareusltadosDecaras (Face[] carasAprocesar){
    int cantidadHombre =0;
        int cantidadMujeres=0;

    Log.d("ProcesarImagen", "Armo el mensaje con informacion");
    String mensaje;
    mensaje="";

        for (int punteroCara=0 ; punteroCara<carasAprocesar.length; punteroCara++)
        {
            mensaje+= "Edad: " + carasAprocesar[punteroCara].faceAttributes.age;
            mensaje+= " - Sonrisa: " + carasAprocesar[punteroCara].faceAttributes.smile;
            mensaje+=" - Barba: " +  carasAprocesar[punteroCara].faceAttributes.facialHair.beard;
            mensaje+= " - Genero: " + carasAprocesar[punteroCara].faceAttributes.gender;
            mensaje+=" - Anteojos: " + carasAprocesar[punteroCara].faceAttributes.glasses;

            if (carasAprocesar[punteroCara].faceAttributes.gender.equals("male"))
            {
                cantidadHombre++;
            }else {
                cantidadMujeres++;
            }
            if (punteroCara<carasAprocesar.length-1){
                mensaje+="\n";
            }

            mensaje+= " - H: " +cantidadHombre +  " - M:" + cantidadMujeres;
            ///text.settext

        }





    }

}
