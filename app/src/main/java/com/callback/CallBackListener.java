package com.callback;

import com.e.periodizacionnatacion.Clases.Cronograma;
import com.e.periodizacionnatacion.Clases.DatoBasico;
import com.e.periodizacionnatacion.Clases.Dia;
import com.e.periodizacionnatacion.Clases.Integrante;
import com.e.periodizacionnatacion.Clases.MacroCiclo;

import java.util.ArrayList;

public interface CallBackListener {
    void onCallBack(String fragmento, String dato1, String dato2, String dato3);
    void onCallBackExtendido(String fragmento, String dato1, String dato2, String dato3, String dato4, String dato5);
    void onCallBackIntegrante(String fragmento, ArrayList<Integrante> array);
    ArrayList<Cronograma> onCallBackCronograma(String fragmento);
    ArrayList<DatoBasico> onCallBackMostrarCiclo(String fragmento);
    MacroCiclo onCallBackInfoCycle(String fragmento);
    void onCallBackDeleteMacroCiclo(MacroCiclo macroCiclo);
    String onCallBackMostrar(String fragmento);
    Dia onCallBackMostrarDia(String fragmento);
    boolean onCallBackCambioFragment();
}
