package com.example.suraj.notes;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by achara on 8/6/2017.
 */

public class NotesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notes, container, false);



        //Handling Back Click listener
        if (this.getView() != null) {
            this.getView().setFocusableInTouchMode(true);
            this.getView().requestFocus();
            this.getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    return keyCode == KeyEvent.KEYCODE_BACK;

                }
            });
        }

        Calendar calendar=Calendar.getInstance();
       /* String currentDate=calendar.get(Calendar.DAY_OF_MONTH)+
                "-"+calendar.get(Calendar.MONTH)+
                "-"+calendar.get(Calendar.YEAR);*/

        SimpleDateFormat df = new SimpleDateFormat("HH:mm a",Locale.US);
        String  currentTime = df.format(calendar.getTime());
        getActivity().setTitle(currentTime);

       /* try {
            SimpleDateFormat outputFormat=new SimpleDateFormat("dd MMM yyyy", Locale.US);
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
            Date date = inputFormat.parse(currentDate);
            getActivity().setTitle(currentDate+"  "+currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        return view;
    }


}
