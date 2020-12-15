package com.showprevioustest;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.e.periodizacionnatacion.Clases.Dato;
import com.e.periodizacionnatacion.Clases.DatoBasico;
import com.e.periodizacionnatacion.Clases.Integrante;
import com.e.periodizacionnatacion.R;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowPreviousTest extends Fragment {

    private ListView lista;
    private TextView tv_integrante;
    private TextView tv_tipoPrueba;
    private Button retroceder;
    private Button salir;


    private ArrayAdapter<String> adaptador;
    private ArrayList<String> resultados;
    private ArrayList<Integrante> integrantes;
    private CallBackListener callback;
    private String nombrePrueba;
    private ArrayList<String> fechasPrueba;
    private AlertDialog dialog;


    public ShowPreviousTest() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_previous_test, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener) {
            callback = (CallBackListener) getActivity();
        }
        if (callback != null){
            integrantes = callback.onCallBackIntegrantes("ShowPreviousTest");
            nombrePrueba = callback.onCallBackMostrar("ShowPreviousTest");
            fechasPrueba = callback.onCallBackFechasDePrueba("ShowPreviousTest");

            if (fechasPrueba != null){
                tv_tipoPrueba.setText("con la prueba: "+nombrePrueba);
            }else{
                Toast.makeText(getContext(),"Nunca se ha realizado la prueba: "+nombrePrueba,Toast.LENGTH_SHORT).show();
                //Devolverse al fragment anterior
                //Navigation.findNavController(getView()).navigate(R.id.nav_select_cycle_statistic);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarID(view);

        final NavController navController= Navigation.findNavController(view);

        funciontv_integrante();
        funcionBttRetroceder(navController);
        funcionBttSalir(navController);
    }

    public void inicializarID(View view){

        lista = view.findViewById(R.id.list_show_previous_test);
        tv_tipoPrueba = view.findViewById(R.id.tx_enun_prueba_show_previous_test);
        tv_integrante = view.findViewById(R.id.tx_info_show_previous_test);
        retroceder = view.findViewById(R.id.retro_show_previous_test);
        salir = view.findViewById(R.id.avan_show_previous_test);
        resultados = new ArrayList<String>();
        integrantes = new ArrayList<Integrante>();
        adaptador = new ArrayAdapter<String>(getContext(),R.layout.integrante_txv,resultados);
        lista.setAdapter(adaptador);
    }

    public void funciontv_integrante(){
        tv_integrante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = crearDialogoIntegrantes();
                dialog.show();
            }
        });
    }

    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_select_previous_type);
            }
        });

    }

    private void funcionBttSalir(NavController navController) {

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });
    }

    public void agregarResultados(Integrante integrante){
        if (integrante != null){
            if (!resultados.isEmpty()){
                resultados.clear();
            }

            int tam = integrante.getPruebas().size();
            ArrayList<DatoBasico> resultadosInt = new ArrayList<DatoBasico>();
            for (int i=0;i<tam;i++){
                if (integrante.getPruebas().get(i).getNombre().equals(nombrePrueba)){
                    resultadosInt = integrante.getPruebas().get(i).getResultadosPruebas();
                    break;
                }
            }

            tam = resultadosInt.size();
            String resultado = "";
            for (int i=0;i<tam;i++){
                 resultado = "tiempo: "+resultadosInt.get(i).getDato1()+" - fecha: "+resultadosInt.get(i).getDato2();
                resultados.add(resultado);
            }
            adaptador.notifyDataSetChanged();
        }else{
            Toast.makeText(getContext(),"No tiene Integrantes para esta prueba",Toast.LENGTH_LONG).show();
        }
    }
    public AlertDialog crearDialogoIntegrantes(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View v= inflater.inflate(R.layout.dialog_integrantes_previous_test, null);

        builder.setView(v);

        ArrayList<String> nombres = new ArrayList<String>();
        ListView listaInt = v.findViewById(R.id.list_dialog_previous_test);
        ArrayAdapter<String> adaptadorInt = new ArrayAdapter<String>(getContext(),R.layout.integrante_txv,nombres);
        listaInt.setAdapter(adaptadorInt);

        agregarIntegrantes(nombres, adaptadorInt);
        funcionListViewIntegrantes(listaInt);

        builder.setNegativeButton("Cancelar",null);

        return  builder.create();
    }

    public void agregarIntegrantes(ArrayList<String> nombres, ArrayAdapter<String> adaptadorInt){
        if (integrantes != null){
            if (!nombres.isEmpty()){
                nombres.clear();
            }
            String nombre="";
            int tam = integrantes.size();
            for (int i=0;i<tam;i++){
                nombre = integrantes.get(i).getNombre();
                nombres.add(nombre);
            }
            adaptadorInt.notifyDataSetChanged();
        }else{
            Toast.makeText(getContext(),"No tiene Integrantes para esta prueba",Toast.LENGTH_LONG).show();
        }
    }

    public void funcionListViewIntegrantes(ListView listaIt){

        listaIt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_integrante.setText(integrantes.get(position).getNombre());
                agregarResultados(integrantes.get(position));
                dialog.dismiss();
            }
        });
    }

}
