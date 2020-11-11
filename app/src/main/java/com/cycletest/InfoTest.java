package com.cycletest;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;


public class InfoTest extends Fragment {

    private EditText et_nombre;
    private Calendar cal;
    private Timer time;
    private DatePickerDialog pickerDialog;
    private CallBackListener callback;
    private Button avanzar;
    private Button retroceder;
    private ImageButton bttDate;
    private EditText edtDate;

    public InfoTest() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener) {
            callback = (CallBackListener) getActivity();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         inicializarID(view);

        NavController navController= Navigation.findNavController(view);

         funcionBttDate();
         funcionAvanzar(navController);
         funcionRetroceder(navController);
    }

    public void inicializarID(View view){
        et_nombre =  view.findViewById(R.id.resp1_infotest);
        edtDate = view.findViewById(R.id.resp2_infotest);
        bttDate = view.findViewById(R.id.date_infotest);
        avanzar = view.findViewById(R.id.btt_avan_infotest);
        retroceder = view.findViewById(R.id.btt_retro_infotest);
        actualizacionFecha();
    }

    public void actualizacionFecha(){

        cal=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(cal.getTime());
        edtDate.setText(formattedDate);

    }

    public void funcionBttDate() {

        bttDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] fecha = edtDate.getText().toString().trim().split("-");
                int day= Integer.parseInt(fecha[0]);
                int month=Integer.parseInt(fecha[1])-1;
                int year=Integer.parseInt(fecha[2]);
                pickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mMonth, int mDay) {

                        edtDate.setText(mDay+"-"+(mMonth+1)+"-"+myear);
                    }
                }, year, month, day);
                pickerDialog.show();
            }
        });
    }

    public void funcionAvanzar(NavController nav) {
        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_list_integrant);
            }
        });
    }

    public void funcionRetroceder(NavController nav) {
        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_select_cycle_test);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_test, container, false);
    }
}