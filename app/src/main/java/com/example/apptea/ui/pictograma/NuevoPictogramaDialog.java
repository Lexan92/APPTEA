


package com.example.apptea.ui.pictograma;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.apptea.R;
import com.example.apptea.ui.Utilities.UtilCamara;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;

import roomsqlite.database.ImageConverter;
import roomsqlite.entidades.Pictograma;

public class NuevoPictogramaDialog extends AppCompatActivity {


    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;
    MaterialButton botonRegistrar, botonSeleccionar, botonCancelar;
    ImageView imgFoto;
    EditText nombrePictograma;
    boolean imagen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MaterialComponents_Light_Dialog_MinWidth);
        setContentView(R.layout.activity_nuevo_pictograma_dialog);
        imgFoto = findViewById(R.id.img_nuevo_pictograma);
        nombrePictograma = findViewById(R.id.edit_nombre_pictograma);

        botonRegistrar = findViewById(R.id.button_guardar_imagen);
        botonSeleccionar = findViewById(R.id.button_seleccionar_imagen);
        botonCancelar = findViewById(R.id.button_cancelar_guardar_imagen);
        //se obteiene el ID de categoria pictograma
        int keyCategoria = getIntent().getIntExtra("llaveCategoria", 0);
        PictogramaViewModel pictogramaViewModel;
        pictogramaViewModel = new ViewModelProvider(this).get(PictogramaViewModel.class);


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
                if (nombrePictograma.getText().toString().isEmpty() && !imagen) {
                    nombrePictograma.setError("Campo Requerido");
                    Toast.makeText(getApplicationContext(), "Debe agregar una imagen  antes de guardar", Toast.LENGTH_LONG).show();
                } else {
                    if (!imagen) {
                        Toast.makeText(getApplicationContext(), "Debe agregar una imagen  antes de guardar", Toast.LENGTH_LONG).show();
                    } else {
                        if (nombrePictograma.getText().toString().isEmpty()) {
                            nombrePictograma.setError("Campo Requerido");
                        } else {
                            Pictograma pictograma = new Pictograma();
                            pictograma.setCat_pictograma_id(keyCategoria);
                            pictograma.setPictograma_nombre(nombrePictograma.getText().toString());
                            pictograma.setPictograma_imagen(ImageConverter.convertirImagenAByteArray(((BitmapDrawable) imgFoto.getDrawable()).getBitmap()));
                            pictogramaViewModel.insert(pictograma);
                            Toast.makeText(getApplicationContext(), "Pictograma Guardado", Toast.LENGTH_LONG).show();
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



    private void cargarImagen() {
        final CharSequence[] opciones = {"Tomar Foto", "Cargar Imagen", "Cancelar"};
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(NuevoPictogramaDialog.this);
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
        Intent intent = new Intent(NuevoPictogramaDialog.this, UtilCamara.class);
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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        try {
                            bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), miPath));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), miPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    imgFoto.setImageBitmap(bitmap);
                    imagen = true;
                    break;

                case COD_FOTO:

                    Bitmap bit = BitmapFactory.decodeByteArray((byte[]) data.getExtras().get("data"), 0, ((byte[]) data.getExtras().get("data")).length, null);
                    int rotation = (int) data.getExtras().get("rotation");
                    System.out.println("rotation " + rotation);
                    try {
                        Matrix matrix = new Matrix();
                        matrix.postRotate(rotation);
                        bit = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);
                        bit = Bitmap.createScaledBitmap(bit, 250, 250, true);
                        //imgFoto.setImageBitmap(bit);
                        Glide.with(this).load(bit).thumbnail(0.5f).into(imgFoto);
                        imagen = true;

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        Toast.makeText(NuevoPictogramaDialog.this, (String) (e.toString()),
                                Toast.LENGTH_LONG).show();
                        Log.e("ioexception", e.toString());
                    }

                    break;
            }


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle", "onResume Pictograma");
        Runtime.getRuntime().gc();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle", "onPause Pictograma");
        Runtime.getRuntime().gc();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle", "onDestroy Pictograma");
        Runtime.getRuntime().gc();

    }
}
