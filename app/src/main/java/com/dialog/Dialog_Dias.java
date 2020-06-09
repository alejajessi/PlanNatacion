package com.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.e.periodizacionnatacion.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEarthCycle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dialog_Dias extends DialogFragment {

    Activity actividad;

    public Dialog_Dias(Activity actividad) {
        // Required empty public constructor
        this.actividad = actividad;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return selectDaysOfWeek() ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_semana, container, false);
    }

    public AlertDialog selectDaysOfWeek(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(actividad.getBaseContext());

        LayoutInflater inflater = actividad.getLayoutInflater();

        View v= inflater.inflate(R.layout.dialog_semana, null);

        builder.setView(v);

        Button accept = v.findViewById(R.id.btt_accept_dialog);
        Button decline = v.findViewById(R.id.btt_decline_dialog);
        CheckBox lunes = v.findViewById(R.id.lunes_dialogo);
        CheckBox martes = v.findViewById(R.id.martes_dialogo);
        CheckBox miercoles = v.findViewById(R.id.mierco_dialogo);
        CheckBox jueves = v.findViewById(R.id.jueves_dialogo);
        CheckBox viernes = v.findViewById(R.id.viernes_dialogo);
        CheckBox sabado = v.findViewById(R.id.sabado_dialogo);
        CheckBox domingo = v.findViewById(R.id.domingo_dialogo);

        final ArrayList<CheckBox> semana = new ArrayList<>();
        final ArrayList itemSelected = new ArrayList();

        semana.add(lunes);
        semana.add(martes);
        semana.add(miercoles);
        semana.add(jueves);
        semana.add(viernes);
        semana.add(sabado);
        semana.add(domingo);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < semana.size(); i++) {
                    if (semana.get(i).hasSelection()) {
                        itemSelected.add(semana.get(i).getText());
                    }
                }
            }
        });


        decline.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dismiss();
            }
        });

        return  builder.create();
    }

}
