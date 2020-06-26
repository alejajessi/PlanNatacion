package com.callback;

import com.e.periodizacionnatacion.Clases.Cronograma;
import com.e.periodizacionnatacion.Clases.DatoBasico;
import com.e.periodizacionnatacion.Clases.Integrante;
import com.e.periodizacionnatacion.Clases.MacroCiclo;

import java.util.ArrayList;

public interface CallBackListener {
    void onCallBack(String fragmento, String dato1, String dato2, String dato3);
    void onCallBackIntegrante(String fragmento, ArrayList<Integrante> array);
    ArrayList<Cronograma> onCallBackVistaPrevia(String fragmento);
    ArrayList<DatoBasico> onCallBackMostrarCiclo(String fragmento);
    MacroCiclo onCallBackInfoCycle(String fragmento);
}
