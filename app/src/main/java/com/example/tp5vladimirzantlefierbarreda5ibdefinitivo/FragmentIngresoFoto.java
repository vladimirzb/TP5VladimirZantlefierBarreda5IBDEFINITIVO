package com.example.tp5vladimirzantlefierbarreda5ibdefinitivo;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class FragmentIngresoFoto extends Fragment implements  View.OnClickListener {
Button btnelegirFoto;
Button btnsacarFoto;
ImageView imgViewvisualziadorImgIngresada;
Button btnsiguienteFrgament;

    SharedPreferences preferences;
int codigoSacarUnaFoto=0;
int codigoElegirUnaFoto=1;
int codigoPedirPermiso=2;

    public View onCreateView(LayoutInflater infladorLayout,  ViewGroup GrupoDeLaVista, Bundle Datos) {
        View vistaAdevolver;
        vistaAdevolver = infladorLayout.inflate(R.layout.layout_ingreso_foto,GrupoDeLaVista, false);

        btnelegirFoto= vistaAdevolver.findViewById(R.id.buttonElegirFoto);
        btnelegirFoto.setOnClickListener(this);
        btnsacarFoto = vistaAdevolver.findViewById(R.id.buttonSacarFoto);
        btnsacarFoto.setOnClickListener(this);

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
                            }, codigoSacarUnaFoto
            );
        } else{
            Log.d("Inicio","TIene permiso, habilito el boton de tomar fotos");
            btnsacarFoto.setEnabled(true);
        }

        return vistaAdevolver;

    }

    @Override
    public void onRequestPermissionsResult(int codigoRespuesta, @NonNull String[] nombrePermisos, @NonNull int[] resultadosPermisos) {
        if (codigoRespuesta==codigoPedirPermiso)
        {
            Log.d("PermisosPedidos", "Permisos obtenidos" + nombrePermisos.length);
            for (int PunteroPermiso=0; PunteroPermiso<nombrePermisos.length;PunteroPermiso ++ )
            {
                Log.d("PermisosPedidos", "Permiso: "+ PunteroPermiso +
                        " - Nombre: "+nombrePermisos[PunteroPermiso]+" - "
                        +(resultadosPermisos[PunteroPermiso]==PackageManager.PERMISSION_GRANTED));
            }

            Log.d("PermisosPedidos", "Si obtuvo todos los permisos pedidos, habilito el boton de tomar foto");
            Boolean obtuvoTodosLosPermisos=true;
            for(int punteroPermiso=0; punteroPermiso<resultadosPermisos.length; punteroPermiso++)
            {
                if (resultadosPermisos[punteroPermiso]!=PackageManager.PERMISSION_GRANTED){
                    obtuvoTodosLosPermisos=false;
                }
            }
            if (obtuvoTodosLosPermisos){
                Log.d("PermisosPediso","Obtuvo todos los permisos, habilito el boton");
                btnsacarFoto.setEnabled(true);
            }else {
                Log.d("PermisosPedidos","NO obtuvo todos permisos, no habilito una goma");
            }
        }
    }

    public void onClick(View vistaRecibida) {

        switch (vistaRecibida.getId()) {

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
                Log.d("TomarFoto", "Voy a instanciar el Intent para acceder a la app de la camara");
                Intent intenttomarFoto;
                intenttomarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Log.d("TomarFoto", "Llamo a la app");
                startActivityForResult(intenttomarFoto, codigoSacarUnaFoto);
                break;


            case R.id.buttonElegirFoto:
                Log.d("ElegirFoto", "Voy a instanciar el Intent para elegir un contenido");
                Intent intentObtenerFoto;
                intentObtenerFoto = new Intent(Intent.ACTION_GET_CONTENT);

                Log.d("ElegirFoto", "Le digo que solo quiero imagenes");
                intentObtenerFoto.setType("image/*");

                Log.d("ElegirFoto", "Llamo a la activity");
                startActivityForResult(Intent.createChooser(intentObtenerFoto, "Seleccione foto"), codigoElegirUnaFoto);
                break;
        }
    }


        @Override
        public void onActivityResult(int requestCode, int resultCode,@Nullable Intent datosrecibidos) {
            super.onActivityResult(requestCode, resultCode, datosrecibidos);
            Log.d("FotoObtenida","Req:" + requestCode + "- Resul: " + resultCode);
            Log.d("FotoObtenida","Procesando...");

            if (requestCode== codigoSacarUnaFoto && resultCode== -1) //NOTA PARA ACORDARME BUSCAR CONTEXTO DE LA ACTIVITY
            {
                Log.d("FotoTomada","Foto tomada OK");
                Bitmap fotoElegida = (Bitmap) datosrecibidos.getExtras().get("data");
                imgViewvisualziadorImgIngresada.setImageBitmap(fotoElegida);
                Log.d("FotoTomada","Mando a procesar la foto");
                ///FUNCIONPROCESARIMGE-VA ACA/


            }
            if (requestCode ==  codigoElegirUnaFoto && resultCode== -1 ) {
               Log.d("FotoObtenida", "Comenzamos con la logica de la foto elegida");
                if (datosrecibidos!=null) {


                    Uri ubicacion = datosrecibidos.getData();
                    Log.d("FotoObtenida", "Ubicacion: " + ubicacion);
                    Bitmap imagenFOTOTRY = null;
                    try {
                        imagenFOTOTRY = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), ubicacion);
                        Log.d("FotoObtenida", "Foto obtenida OK");

                    } catch (Exception Error) {
                        Log.d("FotoObtenida", "Error en obtenion de foto" + Error.getMessage());
                    }
                    if (imagenFOTOTRY != null) {
                        Log.d("FotoObtenida", "Muestro la foto en la pantalla");
                        Bitmap fotoElegida = imagenFOTOTRY;
                        imgViewvisualziadorImgIngresada.setImageBitmap(fotoElegida);
                        Log.d("FotoObtenida", "Mando a procesar la foto");
                        ////FUNCIONPROCESARIMGE-VA ACA

                    }
                }
            }

        }

    }

