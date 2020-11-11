package com.cycletest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.Clases.Integrante;
import com.e.periodizacionnatacion.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListIntegrant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListIntegrant extends Fragment {

    private ListView lista;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> nombres;
    private ArrayList<Integrante> integrantes;
    private ArrayList<String> testType;
    private Button avanzar;
    private Button retroceder;

    public ListIntegrant() {
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_integrant, container, false);
    }
}