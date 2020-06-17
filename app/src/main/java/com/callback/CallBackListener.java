package com.callback;

import com.e.periodizacionnatacion.Clases.Cronograma;

import java.util.ArrayList;

public interface CallBackListener {
    void onCallBack(String fragmento, String dato1, String dato2, String dato3);
    void onCallBackArray(String fragmento, ArrayList<String> array);
    ArrayList<Cronograma> onCallBackVistaPrevia(String Fragmento);
}
