package com.cycleadd;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.Clases.Cronograma;
import com.e.periodizacionnatacion.Clases.Dato;
import com.e.periodizacionnatacion.R;

import java.util.ArrayList;


public class AddVistaPrevia extends Fragment {

    private TextView enun1txt;
    private TextView enun2txt;
    private TextView enun3txt;
    private ListView lview1;
    private ListView lview2;
    private Button avanzar;
    private Button retroceder;

    private ArrayAdapter<String> adaptadorAgua;
    private ArrayList<String> diasAgua;

    private ArrayAdapter<String> adaptadorTierra;
    private ArrayList<String> diasTierra;

    private CallBackListener callback;

    public AddVistaPrevia() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vista_previa, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener) {
            callback = (CallBackListener) getActivity();
        }

        if (callback != null){
            ArrayList<Cronograma> cronogramas = callback.onCallBackVistaPrevia("AddVistaPrevia");
            AgregarCronogramas(cronogramas.get(0),cronogramas.get(1));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarID(view);

        final NavController navController= Navigation.findNavController(view);

        funcionListView1();

        funcionListView2();

        funcionBttAvanzar(navController);

        funcionBttRetroceder(navController);

    }

    public void inicializarID(View view){

        enun1txt = view.findViewById(R.id.enun1_vprevia);
        enun2txt = view.findViewById(R.id.enun2_vprevia);
        enun3txt = view.findViewById(R.id.enun3_vprevia);
        lview1 = view.findViewById(R.id.list1_vprevia);
        lview2 = view.findViewById(R.id.list2_vprevia);
        avanzar = view.findViewById(R.id.avan_vprevia);
        retroceder = view.findViewById(R.id.retro_vprevia);

        diasAgua = new ArrayList<String>();
        adaptadorAgua = new ArrayAdapter<String>(getContext(),R.layout.mes_trabajo,diasAgua);
        lview2.setAdapter(adaptadorAgua);

        diasTierra = new ArrayList<String>();
        adaptadorTierra = new ArrayAdapter<String>(getContext(),R.layout.mes_trabajo,diasTierra);
        lview1.setAdapter(adaptadorTierra);
    }

    public void funcionListView1(){

        lview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public void funcionListView2(){

        lview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    //Método funcionBttAvanzar: Encargado de realizar el movimiento al fragment siguiente: AddVolumenCycle, a tráves del NavController
    public void funcionBttAvanzar(NavController navController){

        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_addinteg);
            }
        });
    }

    //Método funcionBttRetroceder: Encargado de realizar el movimiento del fragment anterior: Home, a tráves del NavController
    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_volumen);
            }
        });

    }

    public void AgregarCronogramas(Cronograma agua, Cronograma tierra) {

        llenarArray(agua.getPeriodo1(),agua.getPeriodo2(),agua.getPeriodo3(), diasAgua);
        adaptadorAgua.notifyDataSetChanged();

        llenarArray(tierra.getPeriodo1(),tierra.getPeriodo2(),tierra.getPeriodo3(), diasTierra);
        adaptadorTierra.notifyDataSetChanged();
    }

    public void llenarArray (Dato periodo1, Dato periodo2, Dato periodo3, ArrayList<String> dia){
        String dato = "";
        int numMes = 1;

        //Periodo 1
        ArrayList<Dato> mesesPeriodo = periodo1.getFecha();
        for (int i=0;i<mesesPeriodo.size();i++){
            dato = " Mes"+numMes+": "+mesesPeriodo.get(i).getVolumen();
            dia.add(dato);
            numMes++;
        }

        //Periodo 2
        mesesPeriodo = periodo2.getFecha();
        for (int i=0;i<mesesPeriodo.size();i++){
            dato = " Mes"+numMes+": "+mesesPeriodo.get(i).getVolumen();
            dia.add(dato);
            numMes++;
        }

        //Periodo 3
        mesesPeriodo = periodo3.getFecha();
        for (int i=0;i<mesesPeriodo.size();i++){
            dato = " Mes"+numMes+": "+mesesPeriodo.get(i).getVolumen();
            dia.add(dato);
            numMes++;
        }
    }
}
