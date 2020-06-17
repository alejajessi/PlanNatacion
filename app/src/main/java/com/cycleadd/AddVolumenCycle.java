package com.cycleadd;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddVolumenCycle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddVolumenCycle extends Fragment {

    private TextView txenunciado1;
    private EditText edvolagua;
    private TextView txenunciado2;
    private EditText edvoltierra;
    private Button avanzar;
    private Button retroceder;
    private CallBackListener callback;


    public AddVolumenCycle() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_volumen_cycle, container, false);
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

        final NavController navController = Navigation.findNavController(view);

        funcionBttAvanzar(navController);

        funcionBttRetroceder(navController);

    }

    public void inicializarID(View view){

        txenunciado1 = view.findViewById(R.id.tx_enun1_volcycle);
        txenunciado2 = view.findViewById(R.id.tx_enun2_volcycle);
        edvolagua = view.findViewById(R.id.ed_resp1_volcycle);
        edvoltierra = view.findViewById(R.id.ed_resp2_volcycle);
        avanzar = view.findViewById(R.id.btt_avan_volcycle);
        retroceder = view.findViewById(R.id.btt_retro_volcycle);

    }

    //Método funcionBttAvanzar: Encargado de realizar el movimiento al fragment siguiente: AddEarthCycle, a tráves del NavController
    public void funcionBttAvanzar(NavController navController){

        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean campos=verificarCampos();
                if (campos == true){

                    agregarVolumen();
                    Navigation.findNavController(v).navigate(R.id.nav_vistaprevia);

                }else {
                    Toast.makeText(getContext(),"Recuerda llenar todos los campos",Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    //Método funcionBttRetroceder: Encargado de realizar el movimiento del fragment anterior: AddCycle, a tráves del NavController
    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.nav_earthcycle);

            }
        });
    }

    public boolean verificarCampos(){

        if (!edvolagua.getText().toString().trim().isEmpty() &&
                !edvoltierra.getText().toString().trim().isEmpty()){
            return true;
        }else{
            return false;
        }

    }

    public void agregarVolumen(){

        String volumenAgua = edvolagua.getText().toString().trim();
        String volumenTierra = edvoltierra.getText().toString().trim();
        if (callback != null){
            callback.onCallBack("AddVolumenCycle",volumenAgua, volumenTierra, "");
        }
    }
}
