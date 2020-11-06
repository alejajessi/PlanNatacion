package com.cyclenotification;

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

public class AddTestNotification extends Fragment {


    private ImageButton bttDate1;
    private EditText edtNom;
    private EditText edtDate1;
    private Calendar cal;
    private DatePickerDialog pickerDialog;
    private CallBackListener callback;
    private Button avanzar;
    private Button retroceder;


    public AddTestNotification() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_test_notification, container, false);
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

        funcionBttDate1();

        final NavController navController= Navigation.findNavController(view);

        funcionBttAvanzar(navController);

        funcionBttRetroceder(navController);

    }

    public void inicializarID(View view){

        edtNom = view.findViewById(R.id.et_nomb_addtest);
        edtDate1 = view.findViewById(R.id.ed_date1);
        bttDate1 = view.findViewById(R.id.btt_cal1_addcycle);
        avanzar = view.findViewById(R.id.avan_addcycle);
        retroceder = view.findViewById(R.id.retro_addcycle);
        actualizacionFecha();

    }

    public void actualizacionFecha(){

        cal=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(cal.getTime());
        edtDate1.setText(formattedDate);

    }

    public void funcionBttDate1(){

        bttDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] fecha = edtDate1.getText().toString().trim().split("-");
                int day= Integer.parseInt(fecha[0]);
                int month=Integer.parseInt(fecha[1])-1;
                int year=Integer.parseInt(fecha[2]);
                pickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mMonth, int mDay) {

                        edtDate1.setText(mDay+"-"+(mMonth+1)+"-"+myear);
                        if (validarFechaActual(mDay,mMonth,myear)){
                            Toast.makeText(getContext(),"La fecha de inicio debe ser mayor o igual a la fecha actual",Toast.LENGTH_LONG).show();
                        }
                    }
                }, year, month, day);
                pickerDialog.show();
            }
        });
    }

    //PENDIENTE OPCIÓN HORA

}
