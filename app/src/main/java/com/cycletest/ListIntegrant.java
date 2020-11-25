package com.cycletest;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.Clases.Integrante;
import com.e.periodizacionnatacion.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListIntegrant} factory method to
 * create an instance of this fragment.
 */
public class ListIntegrant extends Fragment {

    private ListView lista;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> nombres;
    private ArrayList<Integrante> integrantes;
    private Button guardar;
    private Button retroceder;
    private CallBackListener callback;
    private TextView tipoFechaPrueba;
    private int posicion;

    public ListIntegrant() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener) {
            callback = (CallBackListener) getActivity();
        }
        if (callback != null){
            integrantes = callback.onCallBackIntegrantes("ListIntegrant");
            String tipo = callback.onCallBackMostrar("ListIntegrant1");
            String fecha = callback.onCallBackMostrar("ListIntegrant2");
            tipoFechaPrueba.setText("Tipo de prueba: "+tipo+"\nFecha de Prueba: "+fecha);
        }
        agregarIntegrantes();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarID(view);

        NavController navController= Navigation.findNavController(view);

        funcionListView();
        funcionAvanzar(navController);
        funcionRetroceder(navController);
    }

    public void inicializarID(View view){

        guardar = view.findViewById(R.id.avan_listinteg);
        retroceder = view.findViewById(R.id.retro_listintegrant);
        lista = view.findViewById(R.id.integrantes_listprueba);
        tipoFechaPrueba = view.findViewById(R.id.tx_tipo_fecha_prueba);
        nombres = new ArrayList<String>();
        integrantes = new ArrayList<Integrante>();
        adaptador = new ArrayAdapter<String>(getContext(),R.layout.integrante_txv,nombres);
        lista.setAdapter(adaptador);

    }

    public void funcionListView(){

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posicion = position;
                AlertDialog dialog= crearDialogoPrincipal();
                dialog.show();
            }
        });
    }

    public void funcionAvanzar(NavController navController){
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> tiempos = new ArrayList<String>();
                int tam = nombres.size();
                String[] datos = null;
                for (int i=0;i<tam;i++){
                    datos = nombres.get(i).split("-");
                    tiempos.add(datos[1]);
                }

                callback.onCallBackAgregarPruebas(integrantes,tiempos);
                Navigation.findNavController(v).navigate(R.id.nav_home);

            }
        });
    }

    public void funcionRetroceder (NavController navController){
        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_info_test);
            }
        });
    }

    public AlertDialog crearDialogoPrincipal(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v= inflater.inflate(R.layout.dialog_info_prueba, null);

        builder.setView(v);

        final EditText tiempo = v.findViewById(R.id.ed1_infoprueba);
        final String[] datos = nombres.get(posicion).split("-");
        if (datos.length>1){
            tiempo.setText(datos[1]);
        }
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String time = tiempo.getText().toString().trim();
                String valor = "";
                if (time.isEmpty()){
                    valor = datos[0]+" - 0";
                }else{
                    valor = datos[0]+" - "+time;
                }
                nombres.set(posicion,valor);
                adaptador.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancelar",null);

        return  builder.create();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_integrant, container, false);
    }

    public void agregarIntegrantes(){
        if (integrantes != null){
            if (!nombres.isEmpty()){
                nombres.clear();
            }
            String nombre="";
            int tam = integrantes.size();
            for (int i=0;i<tam;i++){
                nombre = integrantes.get(i).getNombre()+" - 0";
                nombres.add(nombre);
            }
            adaptador.notifyDataSetChanged();
        }else{
            Toast.makeText(getContext(),"No tiene Integrantes para esta prueba",Toast.LENGTH_LONG).show();
        }
    }

}