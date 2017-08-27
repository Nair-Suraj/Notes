package com.example.suraj.notes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.suraj.notes.notes_database.Notes;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.Locale;

/**
 * Created by achara on 8/6/2017.
 */

public class NotesFragment extends Fragment {
    private static final int PICK_PHOTO = 0;
    private EditText editText, editTextHeader;
    private Bitmap bmp;
    private int id;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
            switch (item.getItemId()) {
                case R.id.save:
                    if (editText != null && editText.getText().toString().length() > 0) {
                        Notes notes = new Notes();
                        notes.setNoteText(editText.getText().toString());
                        notes.setNoteTextHeader(editTextHeader.getText().toString());
                        notes.setId(id);
                        notes.save();
                        getActivity().getFragmentManager().popBackStackImmediate();
                    }
                    break;
                case R.id.color:
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.color_grid, null, false);
                    GridView gridView = (GridView) view.findViewById(R.id.grid);
                    gridView.setAdapter(new ColorPickerAdapter(getColorList(), getActivity()));
                    builder.setView(view);
                    builder.setTitle("Choose Color");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    break;
                case R.id.camera:
                    requestPermission();
                    break;
            }
            return false;
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(getActivity(), "Errorr loading image", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                bmp = BitmapFactory.decodeStream(bufferedInputStream);

                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append(editText.getText());
                int selStart = editText.getSelectionStart();
                String imgId = "[img=1]";
                Drawable d = new BitmapDrawable(getResources(), bmp);
                builder.replace(editText.getSelectionStart(), editText.getSelectionEnd(), imgId);
                d.setBounds(0, 0, 300, 300);
                ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
                builder.setSpan(span, selStart, selStart + imgId.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                editText.getText().clear();
                editText.setText(builder);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

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


        try {
            Calendar calendar=Calendar.getInstance();
            String currentDate=calendar.get(Calendar.DAY_OF_MONTH)+
                    "-"+calendar.get(Calendar.MONTH)+
                    "-"+calendar.get(Calendar.YEAR);

            SimpleDateFormat df = new SimpleDateFormat("HH:mm a", Locale.US);
            String currentTime = df.format(calendar.getTime());
            getActivity().setTitle(currentTime);

            DateFormat outputFormat=new SimpleDateFormat("dd MMM yyyy", Locale.US);
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
            Date date = inputFormat.parse(currentDate);
            getActivity().setTitle(outputFormat.format(date)+"  "+currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }




        //BottomBarViewClicks

        editText = (EditText) view.findViewById(R.id.userNotes);
        editTextHeader = (EditText) view.findViewById(R.id.editTextHeader);
        editText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        if (getArguments() != null) {
            editText.setText(getArguments().getString("TEXT"));
            editTextHeader.setText(getArguments().getString("TEXT_HEADER"));
            id = getArguments().getInt("ID");
        }
        BottomNavigationView bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.design_bottom_sheet);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);


        return view;
    }

    ArrayList<Integer> getColorList() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(Color.parseColor("#FF0000"));
        list.add(Color.parseColor("#FFC0CB"));
        list.add(Color.parseColor("#800080"));
        list.add(Color.parseColor("#0000ff"));
        list.add(Color.parseColor("#008080"));
        list.add(Color.parseColor("#008000"));
        list.add(Color.parseColor("#00FF00"));
        list.add(Color.parseColor("#FFA500"));
        list.add(Color.parseColor("#FF8C00"));
        list.add(Color.parseColor("#FFEBCD"));
        list.add(Color.parseColor("#757575"));
        list.add(Color.parseColor("#6699cc"));

        return list;

    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            openFilePicker();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openFilePicker();
        }
    }

    public void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO);
    }

}
