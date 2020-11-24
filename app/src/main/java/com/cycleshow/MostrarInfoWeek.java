package com.cycleshow;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.Clases.Cronograma;
import com.e.periodizacionnatacion.Clases.Dato;
import com.e.periodizacionnatacion.Clases.Dia;
import com.e.periodizacionnatacion.Clases.Integrante;
import com.e.periodizacionnatacion.R;

import java.util.ArrayList;

public class MostrarInfoWeek extends Fragment {

    /**
     * Componente gráfico del xml fragment_mostrar_info_week tipo TextView
     */
    private TextView enun1txt;

    /**
     * Componente gráfico del xml fragment_mostrar_info_week tipo TextView
     */
    private TextView enun2txt;

    /**
     * Componente gráfico del xml fragment_mostrar_info_week tipo ListView
     */
    private ListView lview1;

    /**
     * Componente gráfico del xml fragment_mostrar_info_week tipo Button
     */
    private Button retroceder;

    /**
     * Componente gráfico del xml fragment_mostrar_info_week tipo Button
     */
    private Button salir;

    /**
     * Componente gráfico del xml fragment_mostrar_info_week tipo Button
     */
    private Button modificar;

    /**
     * Objeto tipo ArrayAdapter para modificación del ListView 2
     */
    private ArrayAdapter<String> adaptador;

    /**
     * Objeto tipo ArrayList para  asignar información de la lista del ListView 2
     */
    private ArrayList<String> infoList;

    /**
     * Objeto tipo String que indica si se encuentra visualizando días o semanas
     */
    private String estado;

    //Dice que informacion se esta mostrando
    private String mostrando;

    ArrayList<Cronograma> cronogramas;

    private CallBackListener callback;

    /**
     * Constructor de la clase MostrarInfoWeek
     */
    public MostrarInfoWeek() {
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
        return inflater.inflate(R.layout.fragment_mostrar_info_week, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener) {
            callback = (CallBackListener) getActivity();
        }
        if (callback != null){
            cronogramas = callback.onCallBackCronograma("MostrarInfoWeek");
            mostrando = callback.onCallBackMostrar("MostrarInfoWeek");
        }
        if (mostrando.split("-").length<4){
            estado= "semanas";
        }else{
            estado = "dias";
        }
        AgregarCronogramas();

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
        funcionBttSalir(navController);
        funcionBttRetroceder(navController);
    }

    public void funcionBttPorcentajes() {
        AlertDialog dialog= crearDialogoModificar();
        dialog.show();
    }

    public AlertDialog  crearDialogoModificar(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v= inflater.inflate(R.layout.dialog_modporc, null);

        Spinner spSeleccion = v.findViewById(R.id.periodo_spinner);

        EditText edPeriodo1 = v.findViewById(R.id.ed1_p1_modporc);
        TextView txPeriodo1 = v.findViewById(R.id.tx2_p1_modporc);

        EditText edPeriodo2 = v.findViewById(R.id.ed2_p2_modporc);
        TextView txPeriodo2 = v.findViewById(R.id.tx4_p2_modporc);

        EditText edPeriodo3 = v.findViewById(R.id.ed3_p3_modporc);
        TextView txPeriodo3 = v.findViewById(R.id.tx6_p3_modporc);

        TextView txTotal1 = v.findViewById(R.id.tx8_t_modporc);
        TextView txTotal2 = v.findViewById(R.id.tx9_t_modporc);

        builder.setView(v);

        return  builder.create();
    }

    /**
     * Inicializa todos los atributos de la clase MostrarInfoWeek
     * @param view vista
     */
    public void inicializarID(View view){

        enun1txt = view.findViewById(R.id.enun1_minfoWeek);
        lview1 = view.findViewById(R.id.list1_minfoWeek);
        salir = view.findViewById(R.id.avan_minfoWeek);
        retroceder = view.findViewById(R.id.retro_minfoWeek);
        modificar = view.findViewById((R.id.opc_minfoWeek));
        enun2txt = view.findViewById(R.id.enun2_minfoWeek);
        infoList = new ArrayList<String>();
        adaptador = new ArrayAdapter<String>(getContext(),R.layout.mes_trabajo, infoList);
        lview1.setAdapter(adaptador);
    }

    public void funcionBttModificar(){
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog= crearDialogoModificar();
                dialog.show();
            }
        });
    }

    /**
     * Método funcionBttRetroceder: Encargado de realizar el movimiento al fragment anterior: nav_showMonths
     * @param navController control de navegación de la clase
     */
    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(estado.equals("semanas")) {
                    Navigation.findNavController(v).navigate(R.id.nav_showMonths);
                } else if(estado.equals("dias")){
                    estado="semanas";
                    int largo = mostrando.length() - 2;
                    mostrando = mostrando.substring(0,largo);
                    callback.onCallBack("MostrarInfoWeek",mostrando,"",null);
                    mostrando = callback.onCallBackMostrar("MostrarInfoWeek");
                    AgregarCronogramas();

                }
            }
        });

    }

    /**
     * Método funcionBttSalir: Encargado de realizar el movimiento al fragment principal: nav_home
     * @param navController control de navegación de la clase
     */
    public void funcionBttSalir(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });

    }

    public void AgregarCronogramas() {

        String[] datos = mostrando.split("-");

        //Actualizo el TextView con la información actual
        actualizarTextView();
        Cronograma cronograma = null;

        if (datos[0].equals("Agua")){
            cronograma = cronogramas.get(0);
        }else if (datos[0].equals("Tierra")){
            cronograma = cronogramas.get(1);
        }

        int mes= Integer.parseInt(datos[2]);
        int semana = -1;
        if (estado.equals("dias")){
            semana = Integer.parseInt(datos[3]);
        }

        if (datos[1].equals("Periodo1")){

            if (estado.equals("semanas")){
                llenarArray(cronograma.getPeriodo1().getFecha().get(mes));
            }else if (estado.equals("dias")){
                llenarArray(cronograma.getPeriodo1().getFecha().get(mes).getFecha().get(semana));
            }
            adaptador.notifyDataSetChanged();

        }else if (datos[1].equals("Periodo2")){

            if (estado.equals("semanas")){
                llenarArray(cronograma.getPeriodo2().getFecha().get(mes));
            }else if (estado.equals("dias")){
                llenarArray(cronograma.getPeriodo1().getFecha().get(mes).getFecha().get(semana));
            }
            adaptador.notifyDataSetChanged();

        }else if (datos[1].equals("Periodo3")){

            if (estado.equals("semanas")){
                llenarArray(cronograma.getPeriodo3().getFecha().get(mes));
            }else if (estado.equals("dias")){
                llenarArray(cronograma.getPeriodo1().getFecha().get(mes).getFecha().get(semana));
            }
            adaptador.notifyDataSetChanged();
        }
    }

    public void llenarArray (Dato periodo) {
        String dato = "";
        int num = 1;
        actualizarTextView();
        infoList.clear();
        ArrayList<Dato> datosPeriodo = periodo.getFecha();
        ArrayList<Dia> dias = new ArrayList<Dia>();

        int maximoTiempo = 0;
        if (estado.equals("dias")){
            dias = periodo.getDias();
            maximoTiempo = dias.size();
        }else{
            maximoTiempo = datosPeriodo.size();
        }

        for (int i = 0; i < maximoTiempo; i++) {
            if (estado.equals("semanas")){
                dato = "Semana" + num + ": " + datosPeriodo.get(i).getVolumen() + " (" + datosPeriodo.get(i).getPorcentaje() + "%)";
            }else{
                dato = "Día" + num + " ("+dias.get(i).getDiaDeSemana()+"): " + dias.get(i).getVolumen();
            }
            infoList.add(dato);
            num++;
        }
    }

    /**
     * Método funcionListView: Encargado de habilitar la visualización del ListView
     * @param nav
     */
    public void funcionListView(NavController nav){

        lview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(">>>>>>","Antes del callback");
                if (callback != null){
                   callback.onCallBack("MostrarInfoWeek",mostrando,"-"+position,null);
                    mostrando = callback.onCallBackMostrar("MostrarInfoWeek");

                    Toast.makeText(getContext(),mostrando+"",Toast.LENGTH_SHORT).show();
                    if (estado.equals("semanas")){
                        estado = "dias";
                        AgregarCronogramas();
                    }else{
                        Navigation.findNavController(view).navigate(R.id.nav_showDays);
                    }
                }
            }
        });

    }

    //Listo XD (Se coloca a actualizar en el metodo AgregarCronogramas)
    public void actualizarTextView(){
        String[] datos = mostrando.split("-");

        String info = "";

        //Día Agua o Tierra
        if (datos[0].equals("Agua")){
            info = "Días Agua - ";
        }else if (datos[0].equals("Tierra")){
            info = "Días Tierra - ";
        }

        //Periodo
        info = info+datos[1]+" - ";

        //Mes
        int mes= Integer.parseInt(datos[2]);
        info = info+"Mes"+(mes+1)+" - ";

        //Semana si se estan mostrando los días
        if (estado.equals("dias")){
            int semana= Integer.parseInt(datos[3]);
            info = info+"Semana"+(semana+1);
        }

        //Agrego el texto
        enun2txt.setText(info);
    }
}
