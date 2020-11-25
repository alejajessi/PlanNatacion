package com.e.periodizacionnatacion.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.e.periodizacionnatacion.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Button bttAddCycle;
    private Button bttShowCycle;
    private Button bttModiCycle;
    private Button bttStadistics;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarID(view);

        final NavController navController = Navigation.findNavController(view);

        funcionBttAddCycle(navController);
        funcionBttModiCycle(navController);
        funcionBttShowCycle(navController);
        funcionBttStadistics(navController);

    }

    public void inicializarID(View view){

        bttAddCycle = view.findViewById(R.id.btt_addCycle);
        bttModiCycle = view.findViewById(R.id.bttEditMCycle);
        bttShowCycle = view.findViewById(R.id.bttViwMCycle);
        bttStadistics = view.findViewById(R.id.btt_view_stadistics);

    }


    public void funcionBttAddCycle(NavController navController){

        bttAddCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.nav_addcyle);

            }
        });

    }

    public void funcionBttModiCycle(NavController navController){

        bttModiCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public  void funcionBttShowCycle(NavController navController){

        bttShowCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.nav_showcycle);
            }
        });

    }

    public void funcionBttStadistics(NavController navController){

        bttStadistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_select_cycle_statistic);
            }
        });

    }
}
