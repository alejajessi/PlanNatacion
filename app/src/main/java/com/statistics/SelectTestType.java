package com.statistics;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.Clases.MacroCiclo;
import com.e.periodizacionnatacion.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class SelectTestType extends Fragment {

    private TextView et_tipoPrueba;
    private CallBackListener callback;
    private Button avanzar;
    private Button retroceder;

    public SelectTestType() {
        // Required empty public constructor
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

        NavController navController= Navigation.findNavController(view);

        funcionet_tipoPrueba();
        funcionAvanzar(navController);
        funcionRetroceder(navController);
    }

    public void inicializarID(View view){
        et_tipoPrueba =  view.findViewById(R.id.tx_info_select_type);
        avanzar = (Button)view.findViewById(R.id.avan_select_test_type);
        retroceder = view.findViewById(R.id.retro_select_test_type);
    }


    public void funcionet_tipoPrueba(){
        et_tipoPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog= crearDialogoTipo();
                dialog.show();
            }
        });
    }

    public void funcionAvanzar(NavController nav) {
        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                boolean campos=verificarCampos();

                if (campos == true){
                    callback.onCallBack("SelectTestType", et_tipoPrueba.getText().toString().trim(), null,null);
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (callback.onCallBackCambioFragment()){
                                Navigation.findNavController(v).navigate(R.id.nav_show_statistic);
                                cancel();
                            }
                        }
                    }, 1000);
                }else{
                    Toast.makeText(getContext(),"Recuerda seleccionar el tipo de prueba",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void funcionRetroceder(NavController nav) {
        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_select_cycle_statistic);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_test_type, container, false);
    }

    private boolean verificarCampos() {
        if (!et_tipoPrueba.getText().toString().trim().isEmpty()){
            return true;
        }else{
            return false;
        }
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
                    et_tipoPrueba.setText(itemSelected.get(0));

                } else{
                    Toast.makeText(getContext(),"Debe seleccionar una (1) opción",Toast.LENGTH_SHORT).show();

                }

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
