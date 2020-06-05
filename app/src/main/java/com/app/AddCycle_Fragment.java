package com.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.e.periodizacionnatacion.R;

import java.util.Calendar;

public class AddCycle_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btt_date1;
    private EditText edt_date1;
    private  Button btt_date2;
    private EditText edt_date2;

    private Calendar cal;
    private DatePickerDialog pickerDialog;

    public AddCycle_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment com.app.AddEarthCycle.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCycle_Fragment newInstance(String param1, String param2) {
        AddCycle_Fragment fragment = new AddCycle_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_add_cycle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edt_date1 = view.findViewById(R.id.ed_date1);
        edt_date2 = view.findViewById(R.id.ed_date2);
        btt_date1 = view.findViewById(R.id.btt_cal1_addcycle);
        btt_date2 = view.findViewById(R.id.calendar2_addcycle);

        btt_date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cal=Calendar.getInstance();
                int day= cal.get(Calendar.DAY_OF_MONTH);
                int month=cal.get(Calendar.MONTH);
                int year=cal.get(Calendar.YEAR);

                pickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mMonth, int mDay) {

                        edt_date1.setText(mDay+"/"+(mMonth+1)+"/"+myear);
                    }
                }, day, month, year);
                pickerDialog.show();
            }
        });
        btt_date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cal=Calendar.getInstance();
                int day=  cal.get(Calendar.DAY_OF_MONTH);
                int month=cal.get(Calendar.MONTH);
                int year=cal.get(Calendar.YEAR);

                pickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mMonth, int mDay) {

                        edt_date1.setText(mDay+"/"+(mMonth+1)+"/"+myear);
                    }
                }, day, month, year);
                pickerDialog.show();
            }
        });
    }

}