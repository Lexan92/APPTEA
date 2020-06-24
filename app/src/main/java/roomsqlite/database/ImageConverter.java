/*
 *
 * Nombre del Autor
 * 18/05/2020
 * Esta actividad hace el llamado a la lista de roles
 * /
 *
 *
 */

package roomsqlite.database;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;

import androidx.core.content.ContextCompat;

import com.example.apptea.R;

import java.io.ByteArrayOutputStream;

public class ImageConverter {

    public static byte[] convertirImagenAByteArray(Bitmap bitmap){

        //el bitmap es  redimensionado a 64x64 pixeles
        bitmap=redimensionarImagen(bitmap,250,250);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //el metodo comprime el bitmap en formato JPEG a una calidad del 75%
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

        return stream.toByteArray();
    }

    private static Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {
      int ancho=bitmap.getWidth();
      int alto=bitmap.getHeight();

        if(ancho>anchoNuevo || alto>altoNuevo){
            float escalaAncho=anchoNuevo/ancho;
            float escalaAlto= altoNuevo/alto;

            Matrix matrix=new Matrix();
            matrix.postScale(escalaAncho,escalaAlto);

            return Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);

        }else{
            return bitmap;
        }
    }

    public static Bitmap convertirByteArrayAImagen(byte[] array){
        return BitmapFactory.decodeByteArray(array,0,array.length);
    }


}
