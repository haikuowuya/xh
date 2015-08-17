 package com.xinheng.util;

import android.app.Activity;
import android.view.ViewGroup;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

 public class ToastUtils
 {
     /**显示自定义的Toast*/
     public static void showCrouton(Activity activity, CharSequence text, ViewGroup viewGroup )
     {
         Style style = new Style.Builder().setBackgroundColorValue(0x88000000).build();
         //style = Style.ALERT;
         Crouton crouton = Crouton.makeText(activity, text,style, viewGroup);
         crouton.show();

     }
     /**显示自定义的Toast*/
     public static void showCrouton(Activity activity, CharSequence text,Style style,  ViewGroup viewGroup )
     {
         Crouton crouton = Crouton.makeText(activity, text, style, viewGroup);
         crouton.show();

     }
     public static void showCrouton(com.xinheng.base.BaseActivity activity , CharSequence text)
     {
         Crouton crouton = Crouton.makeText(activity, text,  Style.ALERT, activity.getContentViewGroup());
         crouton.show();
     }
 }
