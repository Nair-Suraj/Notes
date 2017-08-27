package com.example.suraj.notes;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by BBIM1040 on 24/08/17.
 */

public class ColorPickerAdapter extends BaseAdapter {
    ArrayList<Integer> colorList;
    Context context;

    public ColorPickerAdapter(ArrayList<Integer> colorList, Context context) {
        this.colorList = colorList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return colorList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder {

        private TextView colorPicker;

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder mViewHolder;
        if (view == null) {
            mViewHolder = new ViewHolder();
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.color_item, viewGroup, false);
            mViewHolder.colorPicker = (TextView) view.findViewById(R.id.colorPickerButton);
            view.setTag(mViewHolder);

        } else
            mViewHolder = (ViewHolder) view.getTag();

        GradientDrawable drawable = (GradientDrawable) mViewHolder.colorPicker.getBackground();
        drawable.setColor(colorList.get(i));
        return view;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }
}
