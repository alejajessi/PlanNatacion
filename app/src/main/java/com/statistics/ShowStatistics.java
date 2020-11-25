package com.statistics;

import android.graphics.Color;
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
import com.e.periodizacionnatacion.Clases.Integrante;
import com.e.periodizacionnatacion.Clases.Prueba;
import com.e.periodizacionnatacion.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ShowStatistics extends Fragment {

    private LineChart grafica;
    private Button retroceder;
    private Button salir;

    private ArrayList<Integrante> integrantes;
    private CallBackListener callback;
    private String nombrePrueba;
    private ArrayList<String> fechasPrueba;
    HashMap<String,Float>  labelEjeX;


    public ShowStatistics() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener) {
            callback = (CallBackListener) getActivity();
        }
        if (callback != null){
            integrantes = callback.onCallBackIntegrantes("ShowStatistics");
            nombrePrueba = callback.onCallBackMostrar("ShowStatistics");
            fechasPrueba = callback.onCallBackFechasDePrueba("ShowStatistics");
            if (fechasPrueba != null){
                agregarLabel();
            }else{
                Toast.makeText(getContext(),"Nunca se ha realizado la prueba: "+nombrePrueba,Toast.LENGTH_SHORT).show();
                //Devolverse al fragment anterior
                Navigation.findNavController(getView()).navigate(R.id.nav_select_cycle_statistic);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarID(view);

        final NavController navController= Navigation.findNavController(view);

        funcionBttRetroceder(navController);
        funcionBttSalir(navController);
    }

    public void inicializarID(View view){

        grafica = view.findViewById(R.id.linechat_statistics);
        retroceder = view.findViewById(R.id.retro_statistics);
        salir = view.findViewById(R.id.avan_statistics);
        integrantes = new ArrayList<Integrante>();
    }

    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_select_cycle_statistic);
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

    public void agregarLabel(){
        labelEjeX = new HashMap<String,Float>();
        int tam = fechasPrueba.size();
        for (int i=0;i<tam;i++){
            labelEjeX.put(fechasPrueba.get(i),(float)i);
        }
            agregarGrafica();
    }

    private void agregarGrafica() {

        ArrayList<ArrayList<Entry>> valores = new ArrayList<>();
        int tam = integrantes.size();

        //Recorro los integrantes
        for (int i=0;i<tam;i++){
            ArrayList<Entry> valIntegrante = new ArrayList<Entry>();
            int tamPruebas = integrantes.get(i).getPruebas().size();

            //Recorro las pruebas que ha hecho cada integrante
            for (int j=0;j<tamPruebas;j++){
                Prueba prueba = integrantes.get(i).getPruebas().get(j);

                //Verifico cual es el array de resultados de la prueba seleccionada
                if (prueba.getNombre().equals(nombrePrueba)){
                    int tamResultados = prueba.getResultadosPruebas().size();

                    //Recorro los resultados de la prueba
                    for (int k=0;k<tamResultados;k++){
                        DatoBasico resultado = prueba.getResultadosPruebas().get(k);
                        Entry ent = new Entry(labelEjeX.get(resultado.getDato2()),Float.parseFloat(resultado.getDato1()));

                        //Agrego los resultados al array de valIntegrante
                        valIntegrante.add(ent);
                    }
                    //Termino el for de pruebas
                    break;
                }
            }
            //Agrego el array de valIntegrante al array de valores para mostrar en el grafico
            valores.add(valIntegrante);
        }

        //Agrego a los valores a LineDataSet
        ArrayList<LineDataSet> arrayDataSets = new ArrayList<LineDataSet>();
        Random rnd = new Random();
        int red = rnd.nextInt(256);
        int green = rnd.nextInt(256);
        int blue = rnd.nextInt(256);
        for (int i=0;i<tam;i++){

            //Agrego los resultados de las pruebas de cada integrante con la etiqueta de su nombre
            LineDataSet dataSet = new LineDataSet(valores.get(i),integrantes.get(i).getNombre());
            dataSet.setColor(Color.argb(255, red, green, blue));
            arrayDataSets.add(dataSet);
        }

        LineData datos = new LineData();
        for (int i=0;i<tam;i++){

            //Agrego los DataSet
            datos.addDataSet(arrayDataSets.get(i));
        }

        //Agrego el LineData al grafico
        grafica.getXAxis().setValueFormatter(new IndexAxisValueFormatter(fechasPrueba));
        grafica.setData(datos);
        grafica.getDescription().setText(nombrePrueba);
        grafica.animateX(1000);
    }

}
