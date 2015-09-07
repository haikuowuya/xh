package com.xinheng.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.xinheng.base.BaseActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * 图片选择工具类
 */
public class PhotoUtils
{
    /**
     * 最大上传图片的宽度和高度
     */
    public static final int W_H = 600;
    /***
     * 从相册文件中选取图片时的请求码
     */
    public static final int REQUEST_FROM_PHOTO = 1111;
    /***
     * 从拍照中选取图片时的请求码
     */
    public static final int REQUEST_FROM_CAMERA = 2222;

    /**
     * 按原比例缩放图片
     *
     * @param path      图片的URI地址
     * @param maxWidth  缩放后的宽度
     * @param maxHeight 缩放后的高度
     * @return
     */
    public static Bitmap scale(String path, int maxWidth, int maxHeight)
    {
        String tag = "SCALE";
        try
        {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            InputStream input = new FileInputStream(path);
            // BitmapFactory.decodeStream(input, null, options);
            int sourceWidth = options.outWidth;
            int sourceHeight = options.outHeight;
            input.close();
            float rate = Math.max(sourceWidth / (float) maxWidth, sourceHeight / (float) maxHeight);
            options.inJustDecodeBounds = false;
            options.inSampleSize = (int) rate;
            input = new FileInputStream(path);
            Bitmap bitmap = BitmapFactory.decodeStream(input, null, options);
            int w0 = bitmap.getWidth();
            int h0 = bitmap.getHeight();
            float scaleWidth = maxWidth / (float) w0;
            float scaleHeight = maxHeight / (float) h0;
            float maxScale = Math.min(scaleWidth, scaleHeight);
            Matrix matrix = new Matrix();
            matrix.reset();
            if (maxScale < 1)
            {
                matrix.postScale(maxScale, maxScale);
            }
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, w0, h0, matrix, true);

            input.close();
            // bitmap.recycle();
            return resizedBitmap;

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从相册中选择图片
     *
     * @param activity
     */
    public static void selectPicFromSD(BaseActivity activity)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        Intent localIntent2 = Intent.createChooser(intent, "选择图片");
        activity.startActivityForResult(localIntent2, PhotoUtils.REQUEST_FROM_PHOTO);
    }

    /***
     * 拍照选择图片
     *
     * @param activity
     * @return 保存的拍照图片路径
     */
    public static String selectPicFromCamera(BaseActivity activity)
    {
        String imageFilePath = null;
        if (!Environment.getExternalStorageState().equals(MEDIA_MOUNTED))
        {
            activity.showCroutonToast("SD卡不可用");
        }
        try
        {
            imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            imageFilePath += "/tmp.jpg";     //设置图片的保存路径
            File imageFile = new File(imageFilePath);                            //通过路径创建保存文件
            if (imageFile.exists())
            {
                imageFile.delete();
            }
            Uri photoUri = Uri.fromFile(imageFile);                                    //获取文件的Uri
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);            //跳转到相机Activity
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);    //告诉相机拍摄完毕输出图片到指定的Uri
            activity.startActivityForResult(intent, PhotoUtils.REQUEST_FROM_CAMERA);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return imageFilePath;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    private  static int readPictureDegree(String path)
    {
        int degree = 0;
        try
        {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation)
            {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return degree;
    }

    /***
     * 将图片旋转后返回bitmap ,用于选择图片时图片旋转90度的问题
     * @return
     */
    public static Bitmap rotateBitmap(String imagePath,Bitmap bitmap )
    {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(readPictureDegree(imagePath));
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }
}
