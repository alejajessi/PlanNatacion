package com.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
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
import java.util.Date;

public class AddCycle_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText edtNom;
    private Button bttDate1;
    private EditText edtDate1;
    private Button bttDate2;
    private EditText edtDate2;
    private Button avanzar;
    private Button retroceder;

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

        inicializarID(view);

        funcionBttDate1();

        funcionBttDate2();

        //actividad = (Activity)getActivity();

        final NavController navController= Navigation.findNavController(view);

        funcionBttAvanzar(navController);

        funcionBttRetroceder(navController);

    }


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


    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });

    }

    public void crearMacroCiclo(MainActivity actividad){

        String nombreCiclo = edtNom.getText().toString().trim();
        String inicioCiclo = edtDate1.getText().toString().trim();
        String finCiclo = edtDate2.getText().toString().trim();

        actividad.inicializarMacrociclo(nombreCiclo, inicioCiclo, finCiclo);

    }

}