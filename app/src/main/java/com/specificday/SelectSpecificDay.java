package com.specificday;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.Clases.Cronograma;
import com.e.periodizacionnatacion.Clases.Dia;
import com.e.periodizacionnatacion.Clases.MacroCiclo;
import com.e.periodizacionnatacion.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class SelectSpecificDay extends Fragment {

    private TextView txDate;
    private ImageButton bttDate;
    private Button retroceder;
    private Button avanzar;
    private Calendar cal;
    private DatePickerDialog pickerDialog;
    private CallBackListener callback;
    private MacroCiclo macro;

    public SelectSpecificDay() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener) {
            callback = (CallBackListener) getActivity();
        }
        if (callback != null){
            macro = callback.onCallBackInfoCycle("SelectSpecificDay");
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if (macro != null){
                        cancel();
                    }else{
                        macro = callback.onCallBackInfoCycle("SelectSpecificDay");
                    }
                }
            }, 1000,1000);

        }else{
            Log.e("Problemas con el CallBack", "El callback es null en AddTestNotification");
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_specific_day, container, false);
    }

    public void inicializarID(View view){

        txDate = view.findViewById(R.id.tx_fecha_specific_day);
        bttDate = view.findViewById(R.id.date_specific_dy);
        retroceder = view.findViewById(R.id.retro_specificDay);
        avanzar = (Button)view.findViewById(R.id.avan_specificDay);
        actualizacionFecha();
    }

    public void actualizacionFecha(){

        cal=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(cal.getTime());
        txDate.setText(formattedDate);

    }

    public void funcionBttDate() {

        bttDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] fecha = txDate.getText().toString().trim().split("-");
                int day= Integer.parseInt(fecha[0]);
                int month=Integer.parseInt(fecha[1])-1;
                int year=Integer.parseInt(fecha[2]);
                pickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mMonth, int mDay) {

                        txDate.setText(mDay+"-"+(mMonth+1)+"-"+myear);
                    }
                }, year, month, day);
                pickerDialog.show();
            }
        });
    }

    public void funcionRetroceder(NavController nav) {
        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_select_cycle_specific_day);
            }
        });
    }

    public void funcionAvanzar(NavController nav) {
        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean tiempo=validarTiempo();

                if (tiempo == true ){
                    callback.onCallBack("SelectSpecificDay", txDate.getText().toString().trim(),null,null);
                    Navigation.findNavController(v).navigate(R.id.nav_show_specific_day);
                }
            }
        });
    }

    public boolean validarTiempo(){

        boolean valido = false;

        if (macro != null){
            String[] inicioMacro = macro.getInicio().split("-");
            String[] finMacro = macro.getFin().split("-");
            String[] test = txDate.getText().toString().trim().split("-");

            Calendar inicio = Calendar.getInstance();
            inicio.set(Integer.parseInt(inicioMacro[2]),Integer.parseInt(inicioMacro[1])-1,Integer.parseInt(inicioMacro[0]));

            Calendar fin = Calendar.getInstance();
            fin.set(Integer.parseInt(finMacro[2]),Integer.parseInt(finMacro[1])-1,Integer.parseInt(finMacro[0]));

            Calendar fechaTest = Calendar.getInstance();
            fechaTest.set(Integer.parseInt(test[2]),Integer.parseInt(test[1])-1,Integer.parseInt(test[0]));

            if (!fechaTest.before(inicio) && !fechaTest.after(fin)){
                valido = true;
            }else{
                Toast.makeText(getContext(),"La fecha debe estar entre "+macro.getInicio()
                        +" y "+macro.getFin(),Toast.LENGTH_LONG).show();
            }
        }else{
            Log.e("Problemas con el Macro ciclo", "El Macro ciclo es null");
        }

        return valido;
    }

}
