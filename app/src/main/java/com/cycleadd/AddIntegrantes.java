package com.cycleadd;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.R;

import java.util.ArrayList;

public class AddIntegrantes extends Fragment {

    private Button bttadd;
    private Button crear;
    private ListView lista;
    private Button retroceder;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> nombres;

    private CallBackListener callback;

    private boolean agregar;
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
        adaptador = new ArrayAdapter<String>(getContext(),R.layout.integrante_txv,nombres);
        lista.setAdapter(adaptador);

    }

    public void funcionBttAdd(){

        bttadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregar = true;
                AlertDialog dialog= crearDialogo();
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
                AlertDialog dialog= crearDialogo();
                dialog.show();
            }
        });
    }

    public AlertDialog crearDialogo(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v= inflater.inflate(R.layout.dialog_integrante, null);

        builder.setView(v);

        final EditText  nombre= v.findViewById(R.id.nom_diainteg);

        builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (agregar){
                    agregarIntegrante(nombre);
                }else{
                    modificarIntegrante(nombre);
                }
            }
        });

        builder.setNegativeButton("Cancelar",null);

        return  builder.create();
    }

    public void agregarIntegrante(EditText nombre){

        if(!nombre.toString().trim().isEmpty()) {
            String x=nombre.getText().toString().trim();
            nombres.add(x);
            adaptador.notifyDataSetChanged();
        }else {
            Toast.makeText(getContext(),"Recuerde ingresar un nombre",Toast.LENGTH_LONG).show();
        }
    }

    public void modificarIntegrante(EditText nombre){

        if(!nombre.toString().trim().isEmpty()) {
            String x=nombre.getText().toString().trim();
            nombres.set(posicion,x);
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
                    callback.onCallBackArray("AddIntegrantes", nombres);
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
