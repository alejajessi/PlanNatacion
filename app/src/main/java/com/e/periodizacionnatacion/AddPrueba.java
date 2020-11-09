package com.e.periodizacionnatacion;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class AddPrueba extends Fragment {

    private EditText nombrePrueba;
    private CheckBox cbComp;
    private CheckBox cbTest;
    private Button retroceder;
    private Button anhadir;


    public AddPrueba() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_prueba, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inicializarID(view);
        NavController navController = Navigation.findNavController(view);
        funcionAnhadir(navController, view);
        funcionBttRetroceder(navController);
    }

    public void inicializarID(View v){

        nombrePrueba = v.findViewById(R.id.ed_nombre_addp);
        cbComp = v.findViewById(R.id.cb_addp1);
        cbTest = v.findViewById(R.id.cb_addp2);
        retroceder = v.findViewById(R.id.retro_sp);
        anhadir = v.findViewById(R.id.anadir_sp);

    }

    public void funcionAnhadir(NavController navController, final View view){

        anhadir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean valido = verificarCampos();

                if(valido == false){

                    Toast.makeText(getContext(),"Recuerde llenar los campos",Toast.LENGTH_LONG).show();

                }else{

                    boolean campo=verificarSoloUno();

                    if(verificarSoloUno() == false){
                        Toast.makeText(getContext(),"Sólo debe seleccionar un tipo de prueba",Toast.LENGTH_LONG).show();
                    }else{

                        Navigation.findNavController(view).navigate(R.id.nav_mostrarP);

                        String nombre = nombrePrueba.toString();
                        String tipo="";
                        String fecha="";

                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                        fecha = df.format(c);

                        if(cbComp.isChecked()){

                            tipo="Competencia fundamental";

                        }else{

                            tipo="Test";

                        }
                    }
                }
            }
        });
    }

    public boolean verificarSoloUno(){
        if(cbComp.isChecked() && cbTest.isChecked()){
            return false;
        }else{
            return true;
        }
    }

    public boolean verificarCampos(){

            if(!nombrePrueba.toString().trim().isEmpty() && (cbTest.isChecked() || cbComp.isChecked())){
                return true;
            }else{
                return false;
            }
    }

    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });

    }
}
