package com.example.pratik.imagepuzzle;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Created by pratik on 11/3/2017.
 */

class GrideViewImageAdaptor extends BaseAdapter {
    HashMap<Integer,Bitmap> bitMapBundle;
    DisplayMetrics displayMetrics;
    int screenHeight , screenWidth;
     MainActivity mainActivity;

    public GrideViewImageAdaptor(MainActivity mainActivity, HashMap<Integer, Bitmap> bitMapBundle) {
        this.bitMapBundle=bitMapBundle;
        this.mainActivity = mainActivity;
    }
    @Override
    public int getCount() {

        return bitMapBundle.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        bitMapBundle =new HashMap<Integer, Bitmap>();
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(this.mainActivity);
            try {
                displayMetrics = new DisplayMetrics();
                mainActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                screenWidth = displayMetrics.widthPixels;
                screenHeight = displayMetrics.heightPixels;
                Toast.makeText(mainActivity, screenWidth, Toast.LENGTH_SHORT).show();
                Toast.makeText(mainActivity,screenWidth, Toast.LENGTH_SHORT).show();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            imageView.setLayoutParams(new GridView.LayoutParams(screenWidth/3,screenWidth/3));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else {
            imageView = (ImageView) convertView;
        }
//
//        Iterator myVeryOwnIterator = bitMapBundle.keySet().iterator();
//        int key=(int)myVeryOwnIterator.next();
//        Bitmap value=(Bitmap) bitMapBundle.get(key);
//        Log.e("Value of Key",""+String.valueOf(key));
        imageView.setImageBitmap(bitMapBundle.get(4));
        return imageView;
    }
}

