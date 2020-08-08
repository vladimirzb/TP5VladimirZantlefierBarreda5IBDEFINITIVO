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
import java.util.ArrayList;

public class FragmentEstadistica1 extends Fragment
{

    TextView txtviewAEstadiBarba;
    TextView txtviewEstadiSonrisa;
    TextView txtviewEstadiEstadoDeAnimo;
    TextView txtviewResultadoGeneral;
    TextView txtviewResultadoSonrisa;
    TextView txtviewResultadoBarba;
    TextView txtviewResultadoEnojo;
    TextView txtviewTituloAtributos;
    ImageView imgvwResultadoEstadis;

    Bitmap fotoElegidaEstadis;
    ProgressDialog  dialogoDeProgreso;

    Boolean sonrisaBool;
    Boolean enojoBool;
    Boolean barbaBool;

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
        txtviewResultadoGeneral = vistaADevolver.findViewById(R.id.textViewEstadisResultadoGeneral);
        txtviewResultadoSonrisa = vistaADevolver.findViewById(R.id.textViewEstadisResultadoSonrisa);
        txtviewResultadoBarba = vistaADevolver.findViewById(R.id.textViewEstadisResultadoBarba);
        txtviewResultadoEnojo =  vistaADevolver.findViewById(R.id.textViewEstadisResultadoEnojo);
        txtviewTituloAtributos = vistaADevolver.findViewById(R.id.tituloatributos);
        imgvwResultadoEstadis = vistaADevolver.findViewById(R.id.imageViewResultadoEstadis);

        txtviewAEstadiBarba.setVisibility(View.INVISIBLE);
        txtviewEstadiEstadoDeAnimo.setVisibility(View.INVISIBLE);
        txtviewEstadiSonrisa.setVisibility(View.INVISIBLE);


        sonrisaBool = mainActivity.atributoSonrisa;
        barbaBool = mainActivity.atributoBarba;
        enojoBool=mainActivity.atributoEnojo;
        if (mainActivity.atributoSonrisa==true)
        {
            txtviewEstadiSonrisa.setVisibility(View.VISIBLE);

        }

        if (mainActivity.atributoEnojo==true)
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
//                FaceServiceClient.FaceAttributeType[] atributos;
//                atributos = new FaceServiceClient.FaceAttributeType[]
//                        {
//                                FaceServiceClient.FaceAttributeType.Age,
//                                FaceServiceClient.FaceAttributeType.Glasses,
//                                FaceServiceClient.FaceAttributeType.Smile,
//                                FaceServiceClient.FaceAttributeType.FacialHair,
//                                FaceServiceClient.FaceAttributeType.Gender
//                        };

                ArrayList<FaceServiceClient.FaceAttributeType> atributosList = new ArrayList<FaceServiceClient.FaceAttributeType>(); // Create an ArrayList object
                atributosList.add(FaceServiceClient.FaceAttributeType.Age);
                atributosList.add(FaceServiceClient.FaceAttributeType.Glasses);
                if (sonrisaBool==true) {
                    atributosList.add(FaceServiceClient.FaceAttributeType.Smile);
                }

                if (barbaBool==true) {
                    atributosList.add(FaceServiceClient.FaceAttributeType.FacialHair);
                }

                if (enojoBool==true) {
                    atributosList.add(FaceServiceClient.FaceAttributeType.Emotion);
                }
                atributosList.add(FaceServiceClient.FaceAttributeType.Gender);





                FaceServiceClient.FaceAttributeType[] atributos = atributosList.toArray(new FaceServiceClient.FaceAttributeType[0]); //Pasamos la lista a un array

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

    int cantidadPersonasSonriendo=0;
    int cantidadPersonasBarba=0;
    int cantidadPersonasEnojadas=0;
    int cantidadPersonasAnteojos=0;

    Log.d("ProcesarImagen", "Armo el mensaje con informacion");
    String mensaje;
    mensaje="";

        for (int punteroCara=0 ; punteroCara<carasAprocesar.length; punteroCara++)
        {
//            mensaje+= "Edad: " + carasAprocesar[punteroCara].faceAttributes.age;
            if (sonrisaBool==true) //Si eligio que analicemos la sonrisa
            {
                mensaje += " - Sonrisa: " + carasAprocesar[punteroCara].faceAttributes.smile;
                if (carasAprocesar[punteroCara].faceAttributes.smile > 0.5)
                {
                    cantidadPersonasSonriendo++;
                }
            }
//
            if (barbaBool==true)//Si eligio que analicemos la barba
            {
                mensaje += " - Barba: " + carasAprocesar[punteroCara].faceAttributes.facialHair.beard;
                if (carasAprocesar[punteroCara].faceAttributes.facialHair.beard>0.5)
                {
                    cantidadPersonasBarba++;
                }
            }

            if (enojoBool==true)//Si eligio que analicemos el enojo
            {
                mensaje += " - Enojo: " + carasAprocesar[punteroCara].faceAttributes.emotion.anger;
                if (carasAprocesar[punteroCara].faceAttributes.emotion.anger>0.5)
                {
                    cantidadPersonasEnojadas++;
                }
            }

//            mensaje+= " - Genero: " + carasAprocesar[punteroCara].faceAttributes.gender;
//            mensaje+=" - Anteojos: " + carasAprocesar[punteroCara].faceAttributes.glasses;

            //Logica anteojos para leer
            mensaje += " - Anteojos: " + carasAprocesar[punteroCara].faceAttributes.glasses;
            if (carasAprocesar[punteroCara].faceAttributes.glasses.toString() == "ReadingGlasses" )
            {
                cantidadPersonasAnteojos++;
            }


            if (carasAprocesar[punteroCara].faceAttributes.gender.equals("male"))
            {
                cantidadHombre++;
            }else {
                cantidadMujeres++;
            }
            if (punteroCara<carasAprocesar.length-1){
                mensaje+="\n";
            }
//Mensaje sirve para debuggear
            mensaje+= " - H: " +cantidadHombre +  " - M:" + cantidadMujeres;
            //BORRAR ESTO DE ABAJO
//            txtviewResultadoEnojo.setText(mensaje);
//            txtviewResultadoGeneral.setText(mensaje);



        }

        int carasTotales= carasAprocesar.length;


        //Logica atributos predeterminados
        float porcentajeGenteMujer = (float) cantidadMujeres / carasTotales;
        porcentajeGenteMujer *= 100;

        float porcentajeGenteHombre = (float) cantidadHombre / carasTotales;
        porcentajeGenteHombre *= 100;

        float porcentajeGenteAnteojos = (float) cantidadPersonasAnteojos / carasTotales;
        porcentajeGenteAnteojos *= 100;

        txtviewResultadoGeneral.setText("De todas las personas un " + porcentajeGenteHombre + "% son hombres" + " y un " +
                porcentajeGenteMujer + "% son mujeres. De los cuales un " + porcentajeGenteAnteojos + "% de todas las personas en la foto estan usando anteojos para leer" );








        //Logica estadistica sonrisa
        if(sonrisaBool==true) {
            float porcentajeGenteSonriendo = (float) cantidadPersonasSonriendo / carasTotales;
            porcentajeGenteSonriendo *= 100;


            txtviewResultadoSonrisa.setText("De todas las personas en la foto solamemnte un " + porcentajeGenteSonriendo + "%" + " estan sonriendo con una sonrisa promedio o mayor");
        }


        if(barbaBool==true) {

            float porcentajeGenteBarba = (float) cantidadPersonasBarba / carasTotales;
            porcentajeGenteBarba *= 100;
            txtviewResultadoBarba.setText("De todas las personas en la foto solamemnte un " + porcentajeGenteBarba + "%" + " tienen una barba promedio o mayor");
        }

        if(enojoBool==true) {

            float porcentajeGenteEnojada = (float) cantidadPersonasEnojadas / carasTotales;
            porcentajeGenteEnojada *= 100;
            txtviewResultadoEnojo.setText("De todas las personas en la foto solamemnte un " + porcentajeGenteEnojada + "%" + " estan enojadas");
        }



        
    }



}
