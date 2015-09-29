package com.xinheng.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;

import java.io.File;

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

    /***
     * 上传头像
     */
    public static void showSelectDialog(final BaseActivity activity)
    {
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_dialog_modify_photo, null);
        LinearLayout linearCameraContainer = (LinearLayout) view.findViewById(R.id.linear_camera_container);
        LinearLayout linearGalleryContainer = (LinearLayout) view.findViewById(R.id.linear_gallery_container);
        final AlertDialog alertDialog = new AlertDialog.Builder(activity).setView(view).create();
        int width = DensityUtils.getScreenWidthInPx(activity) - DensityUtils.dpToPx(activity, 40);
        alertDialog.getWindow().setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
        alertDialog.show();
        linearCameraContainer.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        PhotoUtils.selectPicFromCamera(activity);
                        alertDialog.dismiss();
                    }
                });
        linearGalleryContainer.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        PhotoUtils.selectPicFromSD(activity);
                        alertDialog.dismiss();
                    }
                });
    }
    /**
     * 从相册中选择图片
     *
     * @param activity
     */
    private static void selectPicFromSD(BaseActivity activity)
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
    private static String selectPicFromCamera(BaseActivity activity)
    {
        String imageFilePath = null;
        if (!Environment.getExternalStorageState().equals(MEDIA_MOUNTED))
        {
            activity.showCroutonToast("SD卡不可用");
        }
        try
        {
            System.out.println("activity.getExternalCacheDir() = " + activity.getExternalCacheDir());
            File imageFile = new File(StorageUtils.getCacheDir(activity), System.currentTimeMillis() + ".png");  //通过路径创建保存文件
            if (imageFile.exists())
            {
                imageFile.delete();
            }
            Uri photoUri = Uri.fromFile(imageFile);                                    //获取文件的Uri
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);            //跳转到相机Activity
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);    //告诉相机拍摄完毕输出图片到指定的Uri
            activity.startActivityForResult(intent, PhotoUtils.REQUEST_FROM_CAMERA);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return imageFilePath;
    }


}
