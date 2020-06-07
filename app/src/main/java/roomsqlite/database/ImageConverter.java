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
import android.graphics.drawable.BitmapDrawable;

import androidx.core.content.ContextCompat;

import com.example.apptea.R;

import java.io.ByteArrayOutputStream;

public class ImageConverter {

    public static byte[] convertirImagenAByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,0,stream);
        return stream.toByteArray();
    }

    public static Bitmap convertirByteArrayAImagen(byte[] array){
        return BitmapFactory.decodeByteArray(array,0,array.length);
    }


}
