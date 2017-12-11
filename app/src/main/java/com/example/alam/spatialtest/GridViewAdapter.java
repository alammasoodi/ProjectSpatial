package com.example.alam.spatialtest;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by alam on 1/11/17.
 */

public class GridViewAdapter extends ArrayAdapter<GridViewItem> {

    // private LinkedList<highlightitem> items;
    private Activity activity;
    private int resourceLayoutId;
    private List items;
    private static final String TAG = GridViewAdapter.class.getSimpleName();

    public GridViewAdapter(Activity activity, int resourceLayout,
                           List<GridViewItem> items) {
        super(activity, 0, items);
        this.activity = activity;
        this.items = items;
        this.resourceLayoutId = resourceLayout;
        Log.i(TAG, "initial adapter");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(resourceLayoutId, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GridViewItem item = (GridViewItem) items.get(position);

        if (item.isHighLight()) {
            //holder.background.setBackgroundColor(Color.BLUE);
            holder.image.setImageResource(R.drawable.image_disp);
        } else if(item.isWrongHighLight()) {
            holder.image.setImageResource(R.drawable.image_disp_wrong);

        }
        else if(item.isCorrectHighLight()) {
            holder.image.setImageResource(R.drawable.image_disp);

        }
        else {
            //holder.background.setBackgroundColor(Color.TRANSPARENT);
            holder.image.setImageResource(item.getImage());

        }

        return convertView;
    }

    private static class ViewHolder {
        private ImageView image;
        private ViewGroup background;

        public ViewHolder(View v) {
            image = (ImageView) v.findViewById(R.id.item_image);
            background = (ViewGroup) v.findViewById(R.id.row_layout);
        }
    }

}

