package com.cycleAdd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.e.periodizacionnatacion.MainActivity;
import com.e.periodizacionnatacion.R;

import java.util.Calendar;

public class AddCycle extends Fragment {

    //Componente gráfico del xml fragment_add_cycle tipo EditText
    private EditText edtNom;

    //Componente gráfico del xml fragment_add_cycle tipo Button
    private Button bttDate1;

    //Componente gráfico del xml fragment_add_cycle tipo EditText
    private EditText edtDate1;

    //Componente gráfico del xml fragment_add_cycle tipo Button
    private Button bttDate2;

    //Componente gráfico del xml fragment_add_cycle tipo EditText
    private EditText edtDate2;

    //Componente gráfico del xml fragment_add_cycle tipo Button
    private Button avanzar;

    //Componente gráfico del xml fragment_add_cycle tipo Button
    private Button retroceder;

    //Componente para generar función de selección de fecha
    private Calendar cal;

    //Componente para emerger un dialogo de fecha
    private DatePickerDialog pickerDialog;

    //Constructor de la clase AddCycle
    public AddCycle() {
        // Required empty public constructor
    }

    //Método onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_cycle, container, false);
    }

    //Método OnViewCreated: maneja varias funcionalidades de los componentes xml
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarID(view);

        funcionBttDate1();

        funcionBttDate2();

        //actividad = (Activity)getActivity();

        final NavController navController= Navigation.findNavController(view);

        funcionBttAvanzar(navController);

        funcionBttRetroceder(navController);

    }

    //Método inicializar ID, se encarga de definir el ID correspondiente para cada componente xml con los declarados en la clase.
    public void inicializarID(View view){

        edtNom = view.findViewById(R.id.et_nomb_addcycle);
        edtDate1 = view.findViewById(R.id.ed_date1);
        edtDate1.setText("HOY jaja");
        edtDate2 = view.findViewById(R.id.ed_date2);
        edtDate2.setText("Acuerdame esto");
        bttDate1 = view.findViewById(R.id.btt_cal1_addcycle);
        bttDate2 = view.findViewById(R.id.calendar2_addcycle);
        avanzar = view.findViewById(R.id.avan_addcycle);
        retroceder = view.findViewById(R.id.retro_addcycle);

    }

    //Método funcionBttDate1: Encargado de emerger el dialogo para seleccionar la fecha de inicio del macro ciclo
    public void funcionBttDate1(){

        bttDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cal=Calendar.getInstance();
                int day= cal.get(Calendar.DAY_OF_MONTH);
                int month=cal.get(Calendar.MONTH);
                int year=cal.get(Calendar.YEAR);

                pickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mMonth, int mDay) {

                        edtDate1.setText(mDay+"/"+(mMonth+1)+"/"+myear);
                    }
                }, day, month, year);
                pickerDialog.show();
            }
        });
    }

    //Método funcionBttDate2: Encargado de emerger el dialogo para seleccionar la fecha de inicio del macro ciclo
    public void funcionBttDate2(){

        bttDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cal=Calendar.getInstance();
                int day=  cal.get(Calendar.DAY_OF_MONTH);
                int month=cal.get(Calendar.MONTH);
                int year=cal.get(Calendar.YEAR);

                pickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mMonth, int mDay) {

                        edtDate2.setText(mDay+"/"+(mMonth+1)+"/"+myear);
                    }
                }, day, month, year);
                pickerDialog.show();
            }
        });
    }

    //Método funcionBttAvanzar: Encargado de realizar el movimiento al fragment siguiente: AddWaterCycle, a tráves del NavController
    public void funcionBttAvanzar(NavController navController){

        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtDate1.getText().toString().trim().equals("Fecha") &&
                        !edtDate2.getText().toString().trim().equals("Fecha") &&
                        !edtNom.getText().toString().trim().isEmpty()){

                    //crearMacroCiclo(actividad);
                    Navigation.findNavController(v).navigate(R.id.nav_addwater);

                }else {
                    Toast.makeText(getContext(),"Recuerda llenar todos los campos",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Método funcionBttRetroceder: Encargado de realizar el movimiento del fragment anterior: Home, a tráves del NavController
    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });

    }

    //Método crearMacroCiclo:
    public void crearMacroCiclo(MainActivity actividad){

        String nombreCiclo = edtNom.getText().toString().trim();
        String inicioCiclo = edtDate1.getText().toString().trim();
        String finCiclo = edtDate2.getText().toString().trim();

        actividad.inicializarMacrociclo(nombreCiclo, inicioCiclo, finCiclo);

    }

}