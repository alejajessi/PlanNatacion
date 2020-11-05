package com.cyclemenu;

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

import com.e.periodizacionnatacion.R;

public class MenuMacroCiclo extends Fragment {

    private Button bttRegresar;
    private Button bttAddMacroCiclo;
    private Button bttViewMacroCiclo;
    private Button bttEditMacroCiclo;
    private Button bttDeleteMacroCilo;

    public MenuMacroCiclo() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_macro_ciclo, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarID(view);

        final NavController navController= Navigation.findNavController(view);
        funcionBttAddMacroCiclo(navController);
        funcionBttViewMacroCiclo(navController);
        funcionDeleteMacroCiclo(navController);
        funcionBttRegresar(navController);

    }

    public void inicializarID(View view){
        bttAddMacroCiclo = view.findViewById(R.id.bttadd_menu_macrociclo);
        bttEditMacroCiclo = view.findViewById(R.id.bttedit_menu_macrociclo);
        bttViewMacroCiclo = view.findViewById(R.id.bttview_menu_macrociclo);
        bttDeleteMacroCilo = view.findViewById(R.id.bttdelete_menu_macrociclo);
        bttRegresar = view.findViewById(R.id.retro_menu_macrociclo);
    }

    public void funcionBttAddMacroCiclo(NavController navigation){
        bttAddMacroCiclo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_addcyle);
            }
        });
    }

    public void funcionBttViewMacroCiclo(NavController navigation){
        bttViewMacroCiclo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_showcycle);
            }
        });
    }

    public void funcionBttRegresar(NavController navigation){
        bttRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });
    }

    public void funcionDeleteMacroCiclo(NavController navigation){
        bttDeleteMacroCilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });
    }
}
