package com.deletecycle;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.Clases.DatoBasico;
import com.e.periodizacionnatacion.Clases.Integrante;
import com.e.periodizacionnatacion.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SelectCycleDelete extends Fragment {



    private ListView listCycle;
    private Button retroceder;

    private ArrayAdapter<String> adaptador;

    private ArrayList<String> macroCiclos;
    private ArrayList<DatoBasico> ciclos;

    private CallBackListener callback;
    private int posicion;
    private View view;


    public SelectCycleDelete() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_cycle_delete, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener) {
            callback = (CallBackListener) getActivity();
        }
        if (callback != null){
            ciclos = callback.onCallBackMostrarCiclo("SelectCycleDelete");
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

        listCycle = view.findViewById(R.id.list_select_cycle_delete);
        retroceder = view.findViewById(R.id.retro_select_cycle_delete);
        macroCiclos = new ArrayList<String>();
        adaptador = new ArrayAdapter<String>(getContext(),R.layout.mes_trabajo,macroCiclos);
        listCycle.setAdapter(adaptador);
        this.view = view;
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
                posicion = position;
                AlertDialog dialog= crearDialogoDelete();
                dialog.show();
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
                Navigation.findNavController(v).navigate(R.id.nav_menuMacroCiclo);
            }
        });

    }

    public AlertDialog crearDialogoDelete(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v= inflater.inflate(R.layout.dialog_delete_cycle, null);

        builder.setView(v);

        final TextView advertencia = v.findViewById(R.id.enun1_cycle_delete);

        advertencia.setText("Esta seguro de borrar el macrociclo "+macroCiclos.get(posicion));

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.onCallBackDeleteMacroCiclo(ciclos.get(posicion));
                irAHome();
            }
        });

        builder.setNegativeButton("Cancelar",null);

        return  builder.create();
    }

    private void irAHome() {
        Navigation.findNavController(view).navigate(R.id.nav_home);
    }
}
