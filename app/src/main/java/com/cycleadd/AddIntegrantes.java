package com.cycleadd;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.Clases.Integrante;
import com.e.periodizacionnatacion.R;

import java.util.ArrayList;

public class AddIntegrantes extends Fragment {

    /**
     * Componente gráfico del xml fragment_add_integrantes tipo Button
     */
    private Button bttadd;

    /**
     * Componente gráfico del xml fragment_add_integrantes tipo Button
     */
    private Button crear;

    /**
     * Componente gráfico del xml fragment_add_integrantes tipo Button
     */
    private Button retroceder;

    /**
     * Componente gráfico del xml fragment_add_integrantes tipo ListView
     */
    private ListView lista;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> nombres;

    private ArrayList<Integrante> integrantes;

    private ArrayList<String> testType;

    private String cualBoton;

    private CallBackListener callback;

    private boolean agregar;
    private Integrante integrante;
    private int posicion;

    public AddIntegrantes() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_integrantes, container, false);
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

        final NavController navController= Navigation.findNavController(view);

        funcionBttAdd();

        funcionListView();

        funcionBttCrear(navController);

        funcionBttRetroceder(navController);

    }

    public void inicializarID(View view){

        bttadd = view.findViewById(R.id.bttmore_addinteg);
        crear = view.findViewById(R.id.avan_addinteg);
        retroceder = view.findViewById(R.id.retro_addinteg);
        lista = view.findViewById(R.id.integrantes_lista);
        nombres = new ArrayList<String>();
        integrantes = new ArrayList<Integrante>();
        adaptador = new ArrayAdapter<String>(getContext(),R.layout.integrante_txv,nombres);
        lista.setAdapter(adaptador);
        testType = new ArrayList<String>();

    }

    public void funcionBttAdd(){

        bttadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregar = true;

                AlertDialog dialog= crearDialogoPrincipal();
                dialog.show();
            }
        });
    }

    public void funcionListView(){

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                agregar = false;
                posicion = position;
                AlertDialog dialog= crearDialogoPrincipal();
                dialog.show();
            }
        });
    }

    public AlertDialog crearDialogoPrincipal(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v= inflater.inflate(R.layout.dialog_integrante, null);

        builder.setView(v);

        final EditText  nombre = v.findViewById(R.id.nom_diainteg);
        final EditText descrip = v.findViewById(R.id.infor_diainteg);

        builder.setPositiveButton("Siguiente", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                cualBoton = "Prueba";
                integrante = new Integrante();
                String name = nombre.getText().toString();
                Log.e("nombre", name);
                integrante.setNombre(name);
                String desc = descrip.getText().toString();
                integrante.setDescripcion(desc);
                AlertDialog dialogo= crearDialogoPruebas();
                dialogo.show();
            }
        });

        builder.setNegativeButton("Cancelar",null);

        return  builder.create();
    }

    public AlertDialog crearDialogoPruebas(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v= inflater.inflate(R.layout.dialog_int_pruebas, null);

        builder.setView(v);

        final ArrayList<CheckBox> pruebas = organizacionPruebas(v);
        final ArrayList<String> itemSelected = new ArrayList<String>();

        builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < pruebas.size(); i++) {
                    if (pruebas.get(i).isChecked()) {
                        itemSelected.add(pruebas.get(i).getText().toString().trim());
                    }
                }
                testType = itemSelected;
                integrante.setTiposPruebas(testType);
                cualBoton = "";

                if (agregar){
                    agregarIntegrante();
                }else{
                    modificarIntegrante();
                }
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

        if (cualBoton.equals("Pruebas") && !testType.isEmpty()){
            for (int i=0;i<pruebas.size();i++){
                for (int j=0;j<testType.size();j++){
                    if (pruebas.get(i).getText().toString().trim().equals(testType.get(j))){
                        pruebas.get(i).setChecked(true);
                        break;
                    }
                }
            }
        }

        return pruebas;
    }

    public void agregarIntegrante(){
        String nombre = integrante.getNombre();
        if(!nombre.isEmpty()) {
            String x=nombre;
            nombres.add(x);
            integrantes.add(integrante);
            adaptador.notifyDataSetChanged();
        }else {
            Toast.makeText(getContext(),"Recuerde ingresar un nombre",Toast.LENGTH_LONG).show();
        }
    }

    public void modificarIntegrante(){

        String nombre = integrante.getNombre();
        if(!nombre.isEmpty()) {
            String x=nombre;
            nombres.set(posicion,x);
            integrantes.set(posicion, integrante);
            adaptador.notifyDataSetChanged();
        }else {
            Toast.makeText(getContext(),"Recuerde ingresar un nombre",Toast.LENGTH_LONG).show();
        }
    }

    //Método funcionBttAvanzar: Encargado de realizar el movimiento al fragment siguiente: AddVolumenCycle, a tráves del NavController
    public void funcionBttCrear(NavController navController){

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null){
                    callback.onCallBackIntegrante("AddIntegrantes", integrantes);
                }
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });
    }

    //Método funcionBttRetroceder: Encargado de realizar el movimiento del fragment anterior: Home, a tráves del NavController
    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_vistaprevia);
            }
        });

    }
}
