package com.example.alam.spatialtest;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by alam on 1/11/17.
 */

public class ImageAdapter extends BaseAdapter
{
    private int selectedPosition=-1;

    private Context mContext;
    private final int[] Imageid;
    private final String[] web;
    CountDownTimer mCountDownTimer;
    public ImageAdapter(Context c,String[] web,int[] Imageid )
    {
        mContext = c;
        this.Imageid = Imageid;
        this.web=web;
    }

    @Override
    public int getCount()
    {
        return Imageid.length;
    }
    @Override
    public Object getItem(int position)
    {
        return position;
    }
    @Override
    public long getItemId(int position)
    {
        return 0;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup
            parent)
    {
        AnimationDrawable animation = new AnimationDrawable();
        animation.addFrame(mContext.getResources().getDrawable(R.drawable.image_disp), 1000);
        animation.setOneShot(false);
        final ImageView mImageView;


        if (convertView == null) {
            mImageView = new ImageView(mContext);
            mImageView.setLayoutParams(new GridView.LayoutParams(130, 130));
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageView.setPadding(16, 16, 16, 16);
        } else {
            mImageView = (ImageView) convertView;
        }
       mImageView.setImageResource(Imageid[position]);
//        mImageView.setBackgroundDrawable(animation);
//        animation.start();

        return mImageView;
    }
    private void setSelectedPosition(int position)
    {
        selectedPosition=position;
    }
}
