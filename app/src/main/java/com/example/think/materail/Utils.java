package com.example.think.materail;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.think.materail.Student.Stu_Profile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by komalchaurasia on 23-Jan-17.
 */

public class Utils {
    static Bitmap photoBitmap;
    public static String imagePath = "";
    public static String encodedbar = "encodedbar";
    public static String imageup = "";
    private final static String PREF_KEY = "My_Pref";
    public static String numone = "0";

    public static boolean savePreferences(Context c, String key, String value) {
        SharedPreferences sp = initializeSharedPreferences(c);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getSavedPreferences(Context c, String key, String defValue) {
        SharedPreferences sp = initializeSharedPreferences(c);
        return sp.getString(key, defValue);
    }

    private static SharedPreferences initializeSharedPreferences(Context c) {
        return c.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
    }

    public static boolean clearSavedPreferences(Context c, String key) {
        SharedPreferences sp = initializeSharedPreferences(c);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        return editor.commit();
    }

    public static void write(String msg) {
        try {

            System.out.println(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap resizeBitmap(Stu_Profile mainActivity, String stuimage, String path) {
        try {

            if (!TextUtils.isEmpty(path)) {
                imagePath = path;
            } else {

            }

            if (imagePath != null && imagePath.length() > 5) {
                Runtime.getRuntime().totalMemory();
                Runtime.getRuntime().freeMemory();
                System.gc();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;

                // File f = new File(path);
                ExifInterface exif = new ExifInterface(imagePath);
                int orientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);

                Matrix matrix = new Matrix();

                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        matrix.postRotate(90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        matrix.postRotate(180);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        matrix.postRotate(270);
                        break;
                    default:
                        write("======ROTAtion");

                        break;
                }
                if (imagePath != null) {
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = 0;

                    BitmapFactory.Options o = new BitmapFactory.Options();
                    o.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(new FileInputStream(new File(imagePath)), null, o);

                    // The new size we want to scale to
                    int REQUIRED_SIZE = 768;


                    int scale = 1;
                    while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                            o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                        scale *= 2;
                    }

                    // Decode with inSampleSize
                    BitmapFactory.Options o2 = new BitmapFactory.Options();
                    o2.inSampleSize = scale;
                    Bitmap bmt = BitmapFactory.decodeStream(new FileInputStream(new File(imagePath)), null, o2);
                    photoBitmap = Bitmap.createBitmap(bmt, 0, 0, bmt.getWidth(), bmt.getHeight(), matrix, true);


                    int idx = imagePath.lastIndexOf("/");
                    String name = imagePath.substring(idx + 1, imagePath.length());
                    File dest;

                    if (name.contains(".jpg") || name.contains(".JPG") || name.contains(".JPEG") || name.contains(".jpeg")) {

                        File dir = new File(Environment.getExternalStorageDirectory() + "/DCIM/");

                        dir.mkdir();
                        dest = new File(dir, "GivoCharity" + new Date().getTime() + ".jpg");
                        try {
                            FileOutputStream out;
                            out = new FileOutputStream(dest);
                            photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            out.flush();
                            out.close();
                            imagePath = dest.getAbsolutePath();
                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                    } else if (name.contains(".png") || name.contains(".PNG")) {

                        File dir = new File(Environment.getExternalStorageDirectory() + "/DCIM/");
                        dir.mkdir();
                        dest = new File(dir, "GivoCharity" + new Date().getTime() + ".png");
                        try {
                            FileOutputStream out;
                            out = new FileOutputStream(dest);
                            photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                            out.flush();
                            out.close();
                            imagePath = dest.getAbsolutePath();
                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                    }
                    return photoBitmap;
                } else {

                    if (photoBitmap != null) {
                        photoBitmap = Bitmap.createBitmap(photoBitmap, 0, 0, photoBitmap.getWidth(), photoBitmap.getWidth(), matrix, true);
                        int idx = imagePath.lastIndexOf("/");
                        String name = imagePath.substring(idx + 1, imagePath.length());

                        File dest;

                        if (name.contains(".jpg") || name.contains(".JPG") || name.contains(".JPEG") || name.contains(".jpeg")) {

                            File dir = new File(Environment.getExternalStorageDirectory() + "/DCIM/");

                            dir.mkdir();
                            dest = new File(dir, "GivoCharity" + new Date().getTime() + ".jpg");
                            try {
                                FileOutputStream out;
                                out = new FileOutputStream(dest);
                                photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                out.flush();
                                out.close();
                                imagePath = dest.getAbsolutePath();
                            } catch (IOException e) {

                                e.printStackTrace();
                            }
                        } else if (name.contains(".png") || name.contains(".PNG")) {

                            File dir = new File(Environment.getExternalStorageDirectory() + "/DCIM/");
                            dir.mkdir();
                            dest = new File(dir, "GivoCharity" + new Date().getTime() + ".png");
                            try {
                                FileOutputStream out;
                                out = new FileOutputStream(dest);
                                photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                                out.flush();
                                out.close();
                                imagePath = dest.getAbsolutePath();
                            } catch (IOException e) {

                                e.printStackTrace();
                            }
                        }
                        return photoBitmap;
                    }
                }
            }
        } catch (OutOfMemoryError e) {


            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            if (activity != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity
                        .getSystemService(Activity.INPUT_METHOD_SERVICE);
                View v = activity.getCurrentFocus();
                if (v != null) {
                    IBinder binder = activity.getCurrentFocus()
                            .getWindowToken();
                    if (binder != null) {
                        inputMethodManager.hideSoftInputFromWindow(binder, 0);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}