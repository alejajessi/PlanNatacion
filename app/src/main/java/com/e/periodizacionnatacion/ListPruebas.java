package com.e.periodizacionnatacion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ListPruebas extends Fragment {

    private ArrayList<String> nombres;
    private ListView listaFiltro;
    private EditText edFiltro;
    private ArrayAdapter<String> adapter;

    public ListPruebas() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_pruebas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inicializarID(view);
    }

    public void inicializarID(View v){
        nombres = new ArrayList<>();
        listaFiltro = v.findViewById(R.id.list_listp);
        edFiltro = v.findViewById(R.id.filt_listp);
        nombresdePrueba();
        adapter = new ArrayAdapter<String>(getContext(),R.layout.mes_trabajo, nombres); //Cambiar el R.layout.mes_trabajo
        listaFiltro.setAdapter(adapter);
    }

    public void nombresdePrueba(){
        nombres.add("Prueba 1");
        nombres.add("Prueba 2");
        nombres.add("Elraro");
        nombres.add("Competencia 4");
    }
}
