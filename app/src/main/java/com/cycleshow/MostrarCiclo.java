package com.cycleshow;

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
import android.widget.ListView;
import android.widget.Toast;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.Clases.DatoBasico;
import com.e.periodizacionnatacion.R;

import java.util.ArrayList;

public class MostrarCiclo extends Fragment {

    /**
     * Componente gráfico del xml fragment_mostrar_ciclo tipo LisView
     */
    private ListView listCycle;

    /**
     * Componente gráfico del xml fragment_mostrar_ciclo tipo Button
     */
    private Button retroceder;

    /**
     * Objeto tipo ArrayAdapter para modificación del ListView
     */
    private ArrayAdapter<String> adaptador;

    /**
     * Objeto tipo ArrayList para  asignar información de la lista del ListView
     */
    private ArrayList<String> macroCiclos;

    /**
     * Objeto tipo ArrayList del objeto DatoBasico
     */
    private ArrayList<DatoBasico> ciclos;

    /**
     * Objeto tipo CallBackListener
     */
    private CallBackListener callback;

    /**
     * Atributo tipo int que define la posición de la lista seleccionado
     */
    private int posicion;

    /**
     * Constructor de la clase MostrarCiclo
     */
    public MostrarCiclo() {
        // Required empty public constructor
    }

    /**
     *Método OnCreateView heredado de Fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mostrar_ciclo, container, false);
    }

    /**
     * Método OnActivityCreated
     * @param savedInstanceState
     */
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

    /**
     * Método OnViewCreated
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarID(view);

        final NavController navController= Navigation.findNavController(view);

        funcionListView(navController);
        funcionBttRetroceder(navController);
    }

    /**
     * Inicializa todos los atributos de la clase MostrarCiclo
     * @param view vista
     */
    public void inicializarID(View view){

        listCycle = view.findViewById(R.id.list_show);
        retroceder = view.findViewById(R.id.retro_showCyclee);
        macroCiclos = new ArrayList<String>();
        adaptador = new ArrayAdapter<String>(getContext(),R.layout.mes_trabajo,macroCiclos);
        listCycle.setAdapter(adaptador);
    }

    /**
     * Método agregarMacroCiclos: Encargado de añadir los macro ciclos a la lista y poder ser visualizados por el usuario
     */
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

    /**
     * Método funcionListView: Encargado de habilitar la visualización del ListView
     * @param nav
     */
    public void funcionListView(NavController nav){

        listCycle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callback.onCallBack("MostrarMacroCiclo",ciclos.get(position).getDato2(),null,null);
                Log.e("Mostrar MacroCiclos", "InfoCycle");
               // while (callback.onCallBackCambio()){
               //     Log.e("Mostrar MacroCiclos", "Imuere");
                    Toast.makeText(getContext(),"Cargando MacroCiclo...",Toast.LENGTH_SHORT).show();
               // }
                Navigation.findNavController(view).navigate(R.id.nav_showCycles);
            }
        });
    }

    /**
     * Método funcionBttAvanzar: Encargado de realizar el movimiento al fragment anterior: nav_home
     * @param navController control de navegación de la clase
     */
    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });

    }
}
