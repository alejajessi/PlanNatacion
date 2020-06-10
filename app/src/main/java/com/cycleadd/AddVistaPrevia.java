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
import android.widget.ListView;
import android.widget.TextView;

import com.e.periodizacionnatacion.R;


public class AddVistaPrevia extends Fragment {

    private TextView enun1txt;
    private TextView enun2txt;
    private TextView enun3txt;
    private ListView lview1;
    private ListView lview2;
    private Button avanzar;
    private Button retroceder;

    public AddVistaPrevia() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vista_previa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarID(view);

        final NavController navController= Navigation.findNavController(view);

        funcionBttAvanzar(navController);

        funcionBttRetroceder(navController);

    }

    public void inicializarID(View view){

        enun1txt = view.findViewById(R.id.enun1_vprevia);
        enun2txt = view.findViewById(R.id.enun2_vprevia);
        enun3txt = view.findViewById(R.id.enun3_vprevia);
        lview1 = view.findViewById(R.id.list1_vprevia);
        lview2 = view.findViewById(R.id.list2_vprevia);
        avanzar = view.findViewById(R.id.avan_vprevia);
        retroceder = view.findViewById(R.id.retro_vprevia);

    }

    //Método funcionBttAvanzar: Encargado de realizar el movimiento al fragment siguiente: AddVolumenCycle, a tráves del NavController
    public void funcionBttAvanzar(NavController navController){

        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //crearMacroCiclo(actividad);
                Navigation.findNavController(v).navigate(R.id.nav_addinteg);
            }
        });
    }

    //Método funcionBttRetroceder: Encargado de realizar el movimiento del fragment anterior: Home, a tráves del NavController
    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_volumen);
            }
        });

    }
}
