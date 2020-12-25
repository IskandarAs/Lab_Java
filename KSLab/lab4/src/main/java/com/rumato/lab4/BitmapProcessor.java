package com.rumato.lab4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BitmapProcessor {
    private final Context context;

    public BitmapProcessor(Context context) {
        this.context = context;
    }

    public File createTempFile() {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            return File.createTempFile(name, ".JPEG", storageDirectory);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bitmap getScaledBitmap(String filePath, int viewWidth, int viewHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        int photoWidth = options.outWidth;
        int photoHeight = options.outHeight;

        float relativeWidth = photoHeight / (float) viewHeight;
        float relativeHeight = photoWidth / (float) viewWidth;

        float scaleFactor = Math.max(relativeHeight, relativeWidth);
        scaleFactor = scaleFactor < 1? 1 : scaleFactor;

        options.inJustDecodeBounds = false;
        options.inSampleSize = (int) scaleFactor;

        return BitmapFactory.decodeFile(filePath, options);
    }

    public void saveBitmapToFile(Bitmap bitmap, String path) throws IOException {
        File file = new File(path);
        OutputStream os = null;

        try {
            os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }
}
