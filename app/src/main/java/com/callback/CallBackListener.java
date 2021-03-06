package com.callback;

import com.e.periodizacionnatacion.Clases.Cronograma;
import com.e.periodizacionnatacion.Clases.DatoBasico;
import com.e.periodizacionnatacion.Clases.Dia;
import com.e.periodizacionnatacion.Clases.Integrante;
import com.e.periodizacionnatacion.Clases.MacroCiclo;
import com.e.periodizacionnatacion.Clases.Usuario;

import java.util.ArrayList;

public interface CallBackListener {
    void onCallBack(String fragmento, String dato1, String dato2, String dato3);
    void onCallBackExtendido(String fragmento, String dato1, String dato2, String dato3, String dato4, String dato5);
    void onCallBackAgregarIntegrante(String fragmento, ArrayList<Integrante> array);
    ArrayList<Cronograma> onCallBackCronograma(String fragmento);
    ArrayList<DatoBasico> onCallBackMostrarCiclo(String fragmento);
    MacroCiclo onCallBackInfoCycle(String fragmento);
    void onCallBackDeleteMacroCiclo(DatoBasico macroCiclo);
    String onCallBackMostrar(String fragmento);
    Dia onCallBackMostrarDia(String fragmento);
    boolean onCallBackCambioFragment();
    void onCallBackNotification(String fragmento, String nombre, String fecha, String macrociclo);
    ArrayList<Integrante> onCallBackIntegrantes(String fragmento);
    void onCallBackAgregarPruebas(ArrayList<Integrante> integ, ArrayList<String> tiempos);
    void onCallBackLogout();
    ArrayList<String> onCallBackFechasDePrueba(String fragmento);
}
