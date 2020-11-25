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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.Clases.MacroCiclo;
import com.e.periodizacionnatacion.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class InfoTest extends Fragment {

    private TextView et_nombre;
    private Calendar cal;
    private DatePickerDialog pickerDialog;
    private CallBackListener callback;
    private Button avanzar;
    private Button retroceder;
    private ImageButton bttDate;
    private TextView edtDate;
    private MacroCiclo macro;

    public InfoTest() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener) {
            callback = (CallBackListener) getActivity();
        }
        if (callback != null){
            macro = callback.onCallBackInfoCycle("InfoTest");

        }else{
            Log.e("Problemas con el CallBack", "El callback es null en AddTestNotification");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         inicializarID(view);

        NavController navController= Navigation.findNavController(view);

        funcionEt_nombre();
        funcionBttDate();
        funcionAvanzar(navController);
        funcionRetroceder(navController);
    }

    public void inicializarID(View view){
        et_nombre =  view.findViewById(R.id.resp1_infotest);
        edtDate = view.findViewById(R.id.resp2_infotest);
        bttDate = view.findViewById(R.id.date_infotest);
        avanzar = (Button)view.findViewById(R.id.avan_infotest);
        retroceder = view.findViewById(R.id.retro_infotest);
        actualizacionFecha();
    }

    public void actualizacionFecha(){

        cal=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(cal.getTime());
        edtDate.setText(formattedDate);

    }

    public void funcionEt_nombre(){
        et_nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog= crearDialogoTipo();
                dialog.show();
            }
        });
    }

    public void funcionBttDate() {

        bttDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] fecha = edtDate.getText().toString().trim().split("-");
                int day= Integer.parseInt(fecha[0]);
                int month=Integer.parseInt(fecha[1])-1;
                int year=Integer.parseInt(fecha[2]);
                pickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mMonth, int mDay) {

                        edtDate.setText(mDay+"-"+(mMonth+1)+"-"+myear);
                    }
                }, year, month, day);
                pickerDialog.show();
            }
        });
    }

    public void funcionAvanzar(NavController nav) {
        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean campos=verificarCampos();
                boolean tiempo=validarTiempo();

                if (campos == true && tiempo == true ){
                    callback.onCallBack("InfoTest", et_nombre.getText().toString().trim(),edtDate.getText().toString().trim(),null);
                    Navigation.findNavController(v).navigate(R.id.nav_list_integrant);
                }else if(campos==false){
                    Toast.makeText(getContext(),"Recuerda asignar un nombre a la prueba",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void funcionRetroceder(NavController nav) {
        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_select_cycle_test);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_test, container, false);
    }

    private boolean verificarCampos() {
        if (!et_nombre.getText().toString().trim().isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public boolean validarTiempo(){

        boolean valido = false;

        if (macro != null){
            String[] inicioMacro = macro.getInicio().split("-");
            String[] finMacro = macro.getFin().split("-");
            String[] test = edtDate.getText().toString().trim().split("-");

            Calendar inicio = Calendar.getInstance();
            inicio.set(Integer.parseInt(inicioMacro[2]),Integer.parseInt(inicioMacro[1])-1,Integer.parseInt(inicioMacro[0]));

            Calendar fin = Calendar.getInstance();
            fin.set(Integer.parseInt(finMacro[2]),Integer.parseInt(finMacro[1])-1,Integer.parseInt(finMacro[0]));

            Calendar fechaTest = Calendar.getInstance();
            fechaTest.set(Integer.parseInt(test[2]),Integer.parseInt(test[1])-1,Integer.parseInt(test[0]));

            if (!fechaTest.before(inicio) && !fechaTest.after(fin)){
                valido = true;
            }else{
                Toast.makeText(getContext(),"La fecha del test debe estar entre "+macro.getInicio()
                        +" y "+macro.getFin(),Toast.LENGTH_LONG).show();
            }
        }else{
            Log.e("Problemas con el Macro ciclo", "El Macro ciclo es null");
        }

        return valido;
    }

    public AlertDialog crearDialogoTipo(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View v= inflater.inflate(R.layout.dialog_int_pruebas, null);

        builder.setView(v);

        final ArrayList<CheckBox> pruebas = organizacionPruebas(v);
        final ArrayList<String> itemSelected = new ArrayList<String>();

        builder.setPositiveButton("Avanzar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < pruebas.size(); i++) {
                    if (pruebas.get(i).isChecked()) {
                        itemSelected.add(pruebas.get(i).getText().toString().trim());
                    }
                }
                if(itemSelected.size() == 1){
                    et_nombre.setText(itemSelected.get(0));

                } else{
                    Toast.makeText(getContext(),"Debe seleccionar una (1) opción",Toast.LENGTH_SHORT).show();

                }
                //cualBoton = "";
                //Mirar problema y función de Cuál Botón

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

        //Falta método que chequeé que sólo seleccionó 1 checkbox

        return pruebas;
    }
}