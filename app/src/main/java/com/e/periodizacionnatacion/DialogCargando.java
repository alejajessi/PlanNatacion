package com.e.periodizacionnatacion;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class DialogCargando {

    private Activity principal;
    private AlertDialog dialog;

    public DialogCargando(Activity actividad){
        this.principal = actividad;

    }

    public void iniciar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(principal);

        LayoutInflater inflater = principal.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_cargando,null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void detener(){
        dialog.dismiss();
    }
}