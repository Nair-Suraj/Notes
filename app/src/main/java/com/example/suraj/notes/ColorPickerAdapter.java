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

import java.util.ArrayList;

/**
 * Created by BBIM1040 on 24/08/17.
 */

public class ColorPickerAdapter extends BaseAdapter {
   ArrayList<Integer> colorList;
    Context context;
    public ColorPickerAdapter(ArrayList<Integer> colorList,Context context) {
        this.colorList = colorList;
        this.context=context;
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

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.color_item,viewGroup,false);
        final Button colorPicker=(Button) v.findViewById(R.id.colorPickerButton);
        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable drawable=context.getResources().getDrawable(R.drawable.ic_color_picker,null);
                PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(Color.parseColor("#FFF000"),
                        PorterDuff.Mode.SRC_ATOP);
                drawable.setColorFilter(porterDuffColorFilter);

            }
        });
        GradientDrawable drawable=(GradientDrawable) colorPicker.getBackground();
        drawable.setColor(colorList.get(i));
        return v;
    }
}
