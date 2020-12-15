package com.showprevioustest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.Clases.DatoBasico;
import com.e.periodizacionnatacion.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SelectCyclePrevious extends Fragment {


    private ListView listCycle;
    private Button retroceder;

    private ArrayAdapter<String> adaptador;

    private ArrayList<String> macroCiclos;
    private ArrayList<DatoBasico> ciclos;

    private CallBackListener callback;


    public SelectCyclePrevious() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_cycle_previous, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener) {
            callback = (CallBackListener) getActivity();
        }
        if (callback != null){
            ciclos = callback.onCallBackMostrarCiclo("SelectCyclePrevious");
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

        listCycle = view.findViewById(R.id.list_select_cycle_previous);
        retroceder = view.findViewById(R.id.retro_select_cycle_previous);
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
            int tam = ciclos.size();
            for (int i=0;i<tam;i++){
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
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                callback.onCallBack("SelectCyclePrevious",ciclos.get(position).getDato2(),null,null);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (callback.onCallBackCambioFragment()){
                            if (callback.onCallBackIntegrantes("SelectCyclePrevious").size()!=0){
                                Navigation.findNavController(view).navigate(R.id.nav_select_previous_type);
                            }
                            cancel();
                        }
                    }
                }, 1000,1000);
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
                Navigation.findNavController(v).navigate(R.id.nav_menuPruebas);
            }
        });

    }
}
