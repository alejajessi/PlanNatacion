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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.Clases.Cronograma;
import com.e.periodizacionnatacion.Clases.Dato;
import com.e.periodizacionnatacion.R;

import java.util.ArrayList;

public class MostrarInfoMonths extends Fragment {

    /**
     * Componente gráfico del xml fragment_mostrar_info_months tipo TextView
     */
    private TextView enun1txt;

    /**
     * Componente gráfico del xml fragment_mostrar_info_months tipo TextView
     */
    private TextView enun2txt;

    /**
     * Componente gráfico del xml fragment_mostrar_info_months tipo TextView
     */
    private TextView enun3txt;

    /**
     * Componente gráfico del xml fragment_mostrar_info_months tipo ListView
     */
    private ListView lview1;

    /**
     * Componente gráfico del xml fragment_mostrar_info_months tipo ListView
     */
    private ListView lview2;

    /**
     * Componente gráfico del xml fragment_mostrar_info_months tipo ListView
     */
    private ListView lview3;

    /**
     * Componente gráfico del xml fragment_mostrar_info_months tipo Button
     */
    private Button salir;

    /**
     * Componente gráfico del xml fragment_mostrar_info_months tipo Button
     */
    private Button retroceder;

    /**
     * Componente gráfico del xml fragment_mostrar_info_day tipo Button
     */
    private Button porcentajes;

    /**
     * Componente gráfico del xml fragment_mostrar_info_day tipo Button
     */
    private Button bttDiaTierra;

    /**
     * Componente gráfico del xml fragment_mostrar_info_day tipo Button
     */
    private Button bttDiaAgua;

    /**
     * Objeto tipo ArrayAdapter para modificación del ListView 1
     */
    private ArrayAdapter<String> adaptadorPUno;

    /**
     * Objeto tipo ArrayList para  asignar información de la lista del ListView 1
     */
    private ArrayList<String> mesPuno;

    /**
     * Objeto tipo ArrayAdapter para modificación del ListView 2
     */
    private ArrayAdapter<String> adaptadorPDos;

    /**
     * Objeto tipo ArrayList para  asignar información de la lista del ListView 2
     */
    private ArrayList<String> mesPdos;

    /**
     * Objeto tipo ArrayAdapter para modificación del ListView 2
     */
    private ArrayAdapter<String> adaptadorPTres;

    /**
     * Objeto tipo ArrayList para  asignar información de la lista del ListView 2
     */
    private ArrayList<String> mesPtres;

    //Guarda el dia que esta seleccionado
    private String seleccionado;

    private CallBackListener callback;

    /**
     * Constructor de la clase MostrarInfoMonths
     */
    public MostrarInfoMonths() {
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
        return inflater.inflate(R.layout.fragment_mostrar_info_months, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener) {
            callback = (CallBackListener) getActivity();
        }
        if (callback != null){
            ArrayList<Cronograma> cronogramas = callback.onCallBackCronograma("MostrarInfoMonths");
            AgregarCronogramas(cronogramas.get(0));

            //Cambiar background de los botones
            Resources res = getResources();
            Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.borderedondo_banner, null);
            bttDiaAgua.setBackground(drawable);
            drawable = ResourcesCompat.getDrawable(res, R.drawable.botonredondo_second,null);
            bttDiaTierra.setBackground(drawable);

            seleccionado ="Agua";
        }

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

        funcionDiaAgua();
        funcionDiaTierra();
        funcionBttPorcentajes();

    }

    /**
     * Inicializa todos los atributos de la clase MostrarInfoMonths
     * @param view vista
     */
    public void inicializarID(View view){

        enun1txt = view.findViewById(R.id.enun1_minfoMonth);
        enun2txt = view.findViewById(R.id.enun2_minfoMonth);
        enun3txt = view.findViewById(R.id.enun3_minfoMonth);
        lview1 = view.findViewById(R.id.list1_minfoMonth);
        lview2 = view.findViewById(R.id.list2_minfoMonth);
        lview3 = view.findViewById(R.id.list3_minfoMonth);
        salir = view.findViewById(R.id.avan_minfoMonth);
        retroceder = view.findViewById(R.id.retro_minfoMonth);
        porcentajes = view.findViewById(R.id.opc_porinfoMonth);
        bttDiaAgua = view.findViewById(R.id.DA_infoMonth);
        bttDiaTierra = view.findViewById(R.id.DT_infoMonth);

        mesPuno = new ArrayList<String>();
        adaptadorPUno = new ArrayAdapter<String>(getContext(),R.layout.mes_trabajo, mesPuno);
        lview1.setAdapter(adaptadorPUno);

        mesPdos = new ArrayList<String>();
        adaptadorPDos = new ArrayAdapter<String>(getContext(),R.layout.mes_trabajo, mesPdos);
        lview2.setAdapter(adaptadorPDos);

        mesPtres = new ArrayList<String>();
        adaptadorPTres = new ArrayAdapter<String>(getContext(),R.layout.mes_trabajo, mesPtres);
        lview3.setAdapter(adaptadorPTres);
    }

    /**
     * Método funcionBttRetroceder: Encargado de realizar el movimiento al fragment anterior: showCycles
     * @param navController control de navegación de la clase
     */
    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_showCycles);
            }
        });

    }

    /**
     * Método funcionBttSalir: Encargado de realizar el movimiento al fragment principal: nav_home
     * @param navController control de navegación de la clase
     */
    public void funcionBttSalir(NavController navController){

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });

    }

    public void funcionBttPorcentajes() {
        porcentajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog= crearDialogoModificar();
                dialog.show();
            }
        });
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

        builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancelar",null);
        return  builder.create();
    }

    public void funcionDiaAgua(){

        bttDiaAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res = getResources();
                Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.borderedondo_banner, null);
                bttDiaAgua.setBackground(drawable);
                drawable = ResourcesCompat.getDrawable(res, R.drawable.botonredondo_second,null);
                bttDiaTierra.setBackground(drawable);
                if (callback != null){
                    ArrayList<Cronograma> cronogramas = callback.onCallBackCronograma("MostrarInfoMonths");
                    AgregarCronogramas(cronogramas.get(0));
                }
                seleccionado = "Agua";
            }
        });
    }

    public void funcionDiaTierra(){

        bttDiaTierra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res = getResources();
                Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.borderedondo_banner, null);
                bttDiaTierra.setBackground(drawable);
                drawable = ResourcesCompat.getDrawable(res, R.drawable.botonredondo_second, null);
                bttDiaAgua.setBackground(drawable);
                if (callback != null){
                    ArrayList<Cronograma> cronogramas = callback.onCallBackCronograma("MostrarInfoMonths");
                    AgregarCronogramas(cronogramas.get(1));
                }
                seleccionado = "Tierra";
            }
        });

    }

    public void AgregarCronogramas(Cronograma cronograma) {

        llenarArray(cronograma.getPeriodo1(), mesPuno);
        adaptadorPUno.notifyDataSetChanged();

        llenarArray(cronograma.getPeriodo2(), mesPdos);
        adaptadorPDos.notifyDataSetChanged();

        llenarArray(cronograma.getPeriodo3(), mesPtres);
        adaptadorPTres.notifyDataSetChanged();
    }

    public void llenarArray (Dato periodo, ArrayList<String> dia) {
        String dato = "";
        int numMes = 1;
        dia.clear();
        ArrayList<Dato> mesesPeriodo = periodo.getFecha();
        for (int i = 0; i < mesesPeriodo.size(); i++) {
            dato = "Mes" + numMes + ": " + mesesPeriodo.get(i).getVolumen() + " (" + mesesPeriodo.get(i).getPorcentaje() + "%)";
            dia.add(dato);
            numMes++;
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
                if (callback != null){
                    callback.onCallBack("MostrarInfoMonths",seleccionado,"-Periodo1","-"+position);
                }
                Toast.makeText(getContext(),seleccionado+"-Periodo1-"+position,Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.nav_showWeeks);
            }
        });

        lview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (callback != null){
                    callback.onCallBack("MostrarInfoMonths",seleccionado,"-Periodo2","-"+position);
                }
                Toast.makeText(getContext(),seleccionado+"-Periodo2-"+position,Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.nav_showWeeks);
            }
        });

        lview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (callback != null){
                    callback.onCallBack("MostrarInfoMonths",seleccionado,"-Periodo3","-"+position);
                }
                Toast.makeText(getContext(),seleccionado+"-Periodo3-"+position,Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.nav_showWeeks);
            }
        });
    }
}
