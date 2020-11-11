package com.cycletest;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.Clases.DatoBasico;
import com.e.periodizacionnatacion.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectCycleTest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectCycleTest extends Fragment {

    private ListView listCycle;
    private Button retroceder;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> macroCiclos;
    private ArrayList<DatoBasico> ciclos;
    private CallBackListener callback;
    private int posicion;
    private ArrayList<String> testType;

    public SelectCycleTest() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener) {
            callback = (CallBackListener) getActivity();
        }
        if (callback != null){
            ciclos = callback.onCallBackMostrarCiclo("MostrarMacroCiclo");
        }
        agregarMacroCiclos();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController= Navigation.findNavController(view);
        inicializarID(view);
        funcionListView(navController, view);
        funcionBttRetroceder(navController);
    }

    public void inicializarID(View view){

        listCycle = view.findViewById(R.id.list_select_cycle_test);
        retroceder = view.findViewById(R.id.retro_selectCycleTest);
        macroCiclos = new ArrayList<String>();
        adaptador = new ArrayAdapter<String>(getContext(),R.layout.mes_trabajo,macroCiclos);
        listCycle.setAdapter(adaptador);




    }

    public void agregarMacroCiclos(){
        if (ciclos != null){
            if (!macroCiclos.isEmpty()){
                macroCiclos.clear();
            }
            String nombre="";
            for (int i=0;i<ciclos.size();i++){
                nombre = ciclos.get(i).getDato1();
                macroCiclos.add(nombre);
            }
            adaptador.notifyDataSetChanged();
        }else{
            Toast.makeText(getContext(),"No tiene MacroCiclos asociados",Toast.LENGTH_LONG).show();
        }
    }

    public void funcionListView(final NavController nav, View view){

        listCycle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //callback.onCallBack("MostrarMacroCiclo",ciclos.get(position).getDato2(),null,null);
                //Log.e("Mostrar MacroCiclos", "InfoCycle");
               // Toast.makeText(getContext(),"Cargando MacroCiclo...",Toast.LENGTH_SHORT).show();
                AlertDialog dialog= crearDialogoTipo(nav, view);
                dialog.show();
            }
        });
    }

    public AlertDialog crearDialogoTipo(NavController nav,  final View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View v= inflater.inflate(R.layout.dialog_int_pruebas, null);

        builder.setView(v);

        final ArrayList<CheckBox> pruebas = organizacionPruebas(v);
        final ArrayList<String> itemSelected = new ArrayList<String>();

        builder.setPositiveButton("Avanzar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < pruebas.size(); i++) {
                    if (pruebas.get(i).isChecked()) {
                        itemSelected.add(pruebas.get(i).getText().toString().trim());
                    }
                }
                if(itemSelected.size() == 1){
                    testType = itemSelected;
                    Log.e("UNO", "Entre al if final");
                    Navigation.findNavController(view).navigate(R.id.nav_info_test);
                } else{
                    Toast.makeText(getContext(),"Debe seleccionar una (1) opción",Toast.LENGTH_SHORT).show();

                }
                //cualBoton = "";
                //Mirar problema y función de Cuál Botón

            }
        });

        builder.setNegativeButton("Cancelar",null);

        return  builder.create();
    }

    public ArrayList<CheckBox> organizacionPruebas(View v){

        CheckBox aire = v.findViewById(R.id.pru_airelibre);
        CheckBox combin = v.findViewById(R.id.pru_combin);
        CheckBox espalda = v.findViewById(R.id.pru_espal);
        CheckBox mariposa = v.findViewById(R.id.pru_mari);
        CheckBox pecho = v.findViewById(R.id.pru_pecho);

        final ArrayList<CheckBox> pruebas = new ArrayList<CheckBox>();

        pruebas.add(aire);
        pruebas.add(combin);
        pruebas.add(espalda);
        pruebas.add(mariposa);
        pruebas.add(pecho);

        //Falta método que chequeé que sólo seleccionó 1 checkbox

        return pruebas;
    }

    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_menuPruebas);
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_cycle_test, container, false);
    }
}