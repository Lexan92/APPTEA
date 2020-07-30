package com.example.apptea.ui.habilidadCotidiana;


import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;

import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.bumptech.glide.Glide;
import com.example.apptea.R;
import com.example.apptea.ui.Utilities.UtilCamara;
import com.example.apptea.ui.usuario.ValidarCodigo;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import roomsqlite.database.ImageConverter;
import roomsqlite.entidades.HabilidadCotidiana;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class NuevaHabilidadCotidianaDialog extends AppCompatActivity{

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;
    MaterialButton botonRegistrar, botonSeleccionar, botonCancelar;
    ImageView imgFoto;
    EditText nombreHabilidad;
    boolean imagen = false;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MaterialComponents_Light_Dialog_MinWidth);
        setContentView(R.layout.activity_nueva_hab_cotidiana);

        //Se declaran los componentes de la vista
        imgFoto = findViewById(R.id.img_nueva_habilidad);
        nombreHabilidad = findViewById(R.id.edit_nombre_habilidad);

        botonRegistrar = findViewById(R.id.button_guardar_imagen);
        botonSeleccionar = findViewById(R.id.button_seleccionar_imagen);
        botonCancelar = findViewById(R.id.button_cancelar_guardar_imagen);

        //se obteiene el ID de categoria habilidad cotidiana
        int keyCategoria =getIntent().getIntExtra("llaveCatHabilidad",0);
        HabilidadCotidianaViewModel habilidadCotidianaViewModel;
        habilidadCotidianaViewModel= new ViewModelProvider(this).get(HabilidadCotidianaViewModel.class);

         if (validaPermisos()) {
            botonSeleccionar.setEnabled(true);
        } else {
            botonSeleccionar.setEnabled(false);
        }

        botonSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inicia metodo para cargar imagenes
                cargarImagen();
            }
        });

        botonSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inicia metodo para cargar imagenes
                cargarImagen();
            }
        });

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inicia guardado de pictogramas
                //se valida si los campos estan vacios
                if (nombreHabilidad.getText().toString().isEmpty() && !imagen) {
                    nombreHabilidad.setError("Campo Requerido");
                    Toast.makeText(getApplicationContext(), "Debe agregar una nombre antes de guardar", Toast.LENGTH_LONG).show();
                } else {
                    if (!imagen) {
                        Toast.makeText(getApplicationContext(), "Debe agregar una imagen  antes de guardar", Toast.LENGTH_LONG).show();
                    } else {
                        if (nombreHabilidad.getText().toString().isEmpty()) {
                            nombreHabilidad.setError("Campo Requerido");
                        } else {
                            HabilidadCotidiana habilidadCotidiana = new HabilidadCotidiana();
                            habilidadCotidiana.setCat_hab_cotidiana_id(keyCategoria);
                            habilidadCotidiana.setHabilidad_cotidiana_nombre(nombreHabilidad.getText().toString());
                            habilidadCotidiana.setHab_predeterminado(false);
                            habilidadCotidianaViewModel.insert(habilidadCotidiana);
                            Toast.makeText(getApplicationContext(), "Habilidad Cotidiana Guardada", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), AgregarSecuenciaDialog.class);
                            intent.putExtra("hab_cotidiana_id", habilidadCotidiana.getHabilidad_cotidiana_id());
                            startActivity(intent);
                            finish();

                        }
                    }


                }
            }

        });


        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private boolean validaPermisos() {
        //se validan permisos para versiones de android menores a Marshmellow
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if ((checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        }

        if ((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))) {
            cargarDialogoRecomendacion();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
        }

        return false;
    }

    private void cargarDialogoRecomendacion() {

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(NuevaHabilidadCotidianaDialog.this);
        builder.setTitle("Permisos Desactivados");
        builder.setMessage("Deberá aceptar los permisos para el correcto funcionamiento de la aplicación");
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
                }
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                botonSeleccionar.setEnabled(true);
            } else {
                solicitarPermisosManual();
            }
        }
    }

    private void solicitarPermisosManual() {

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(NuevaHabilidadCotidianaDialog.this);
        builder.setTitle("¿Desea configurar los permisos de forma manual?");
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                Snackbar.make(botonSeleccionar, "Los permisos no fueron aceptados", Snackbar.LENGTH_LONG);
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.show();
    }


    private void cargarImagen() {


        final CharSequence[] opciones = {"Tomar Foto", "Cargar Imagen", "Cancelar"};
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(NuevaHabilidadCotidianaDialog.this);
        builder.setTitle("Seleccione una opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (opciones[i].equals("Tomar Foto")) {
                    tomarFotografia();
                } else {
                    if (opciones[i].equals("Cargar Imagen")) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"), COD_SELECCIONA);
                    } else {
                        dialog.dismiss();
                    }
                }


            }
        });
        builder.show();
    }

    private void tomarFotografia() {
        Intent intent = new Intent(NuevaHabilidadCotidianaDialog.this, UtilCamara.class);
        startActivityForResult(intent, COD_FOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case COD_SELECCIONA:
                    Uri miPath = data.getData();
                    Bitmap bitmap = null;
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.P){
                        try {
                            bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(),miPath));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),miPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    imgFoto.setImageBitmap(bitmap);
                    imagen = true;
                    break;

                case COD_FOTO:
                    ContentResolver cr = this.getContentResolver();

                    Bitmap bit = BitmapFactory.decodeByteArray((byte[])data.getExtras().get("data"), 0, ((byte[])data.getExtras().get("data")).length, null);
                    int rotation = (int) data.getExtras().get("rotation");
                    System.out.println("rotation "+rotation);
                    try {
                        Matrix matrix = new Matrix();
                        matrix.postRotate(rotation);
                        bit = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);
                        bit = Bitmap.createScaledBitmap(bit, 250, 250, true);
                        //imgFoto.setImageBitmap(bit);
                        Glide.with(this).load(bit).thumbnail(0.5f).into(imgFoto);
                        imagen=true;

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        Toast.makeText(NuevaHabilidadCotidianaDialog.this, (String) (e.toString()),
                                Toast.LENGTH_LONG).show();
                        Log.e("ioexception",e.toString());
                    }

                    break;
            }


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle","onResume Habilidad Cotidiana");
        Runtime.getRuntime().gc();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle","onPause Habilidad Cotidiana");
        Runtime.getRuntime().gc();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle","onDestroy Habilidad Cotidiana");
        Runtime.getRuntime().gc();

    }


}
