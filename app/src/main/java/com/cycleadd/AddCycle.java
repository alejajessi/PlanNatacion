package com.cycleadd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.callback.CallBackListener;
import com.e.periodizacionnatacion.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddCycle extends Fragment {

    //Componente gráfico del xml fragment_add_cycle tipo EditText
    private EditText edtNom;

    //Componente gráfico del xml fragment_add_cycle tipo Button
    private ImageButton bttDate1;

    //Componente gráfico del xml fragment_add_cycle tipo EditText
    private EditText edtDate1;

    //Componente gráfico del xml fragment_add_cycle tipo Button
    private ImageButton bttDate2;

    //Componente gráfico del xml fragment_add_cycle tipo EditText
    private EditText edtDate2;

    //Componente gráfico del xml fragment_add_cycle tipo Button
    private Button avanzar;

    //Componente gráfico del xml fragment_add_cycle tipo Button
    private Button retroceder;

    //Componente para generar función de selección de fecha
    private Calendar cal;

    //Componente para emerger un dialogo de fecha
    private DatePickerDialog pickerDialog;

    private CallBackListener callback;

    private boolean errorFechaActual;

    //Constructor de la clase AddCycle
    public AddCycle() {
        // Required empty public constructor
    }

    //Método onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_cycle, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener) {
            callback = (CallBackListener) getActivity();
        }
    }

    //Método OnViewCreated: maneja varias funcionalidades de los componentes xml
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarID(view);

        funcionBttDate1();

        funcionBttDate2();

        final NavController navController= Navigation.findNavController(view);

        funcionBttAvanzar(navController);

        funcionBttRetroceder(navController);

    }

    //Método inicializar ID, se encarga de definir el ID correspondiente para cada componente xml con los declarados en la clase.
    public void inicializarID(View view){

        edtNom = view.findViewById(R.id.et_nomb_addcycle);
        edtDate1 = view.findViewById(R.id.ed_date1);
        edtDate2 = view.findViewById(R.id.ed_date2);
        bttDate1 = view.findViewById(R.id.btt_cal1_addcycle);
        bttDate2 = view.findViewById(R.id.calendar2_addcycle);
        avanzar = view.findViewById(R.id.avan_addcycle);
        retroceder = view.findViewById(R.id.retro_addcycle);
        actualizacionFecha();

    }

    public void actualizacionFecha(){

        cal=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(cal.getTime());
        edtDate1.setText(formattedDate);
        edtDate2.setText(formattedDate);

    }

    //Método funcionBttDate1: Encargado de emerger el dialogo para seleccionar la fecha de inicio del macro ciclo
    public void funcionBttDate1(){

        bttDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] fecha = edtDate1.getText().toString().trim().split("-");
                int day= Integer.parseInt(fecha[0]);
                int month=Integer.parseInt(fecha[1])-1;
                int year=Integer.parseInt(fecha[2]);
                pickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mMonth, int mDay) {

                        edtDate1.setText(mDay+"-"+(mMonth+1)+"-"+myear);
                        if (validarFechaActual(mDay,mMonth,myear)){
                            Toast.makeText(getContext(),"La fecha de inicio debe ser mayor o igual a la fecha actual",Toast.LENGTH_LONG).show();
                        }
                    }
                }, year, month, day);
                pickerDialog.show();
            }
        });
    }

    //Método funcionBttDate2: Encargado de emerger el dialogo para seleccionar la fecha de inicio del macro ciclo
    public void funcionBttDate2(){

        bttDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] fecha = edtDate2.getText().toString().trim().split("-");
                int day= Integer.parseInt(fecha[0]);
                int month=Integer.parseInt(fecha[1])-1;
                int year=Integer.parseInt(fecha[2]);

                pickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mMonth, int mDay) {

                        edtDate2.setText(mDay+"-"+(mMonth+1)+"-"+myear);
                        if (validarFechaActual(mDay,mMonth,myear)){
                            Toast.makeText(getContext(),"La fecha de fin debe ser mayor a la fecha actual",Toast.LENGTH_LONG).show();
                        }
                    }
                }, year, month, day);
                pickerDialog.show();
            }
        });
    }

    //Método funcionBttAvanzar: Encargado de realizar el movimiento al fragment siguiente: AddWaterCycle, a tráves del NavController
    public void funcionBttAvanzar(NavController navController){

        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean campos=verificarCampos();
                boolean tiempo=validarTiempo();

                if (campos == true && tiempo == true ){

                    crearMacroCiclo();
                    Navigation.findNavController(v).navigate(R.id.nav_addwater);

                }else if(campos==false){
                    Toast.makeText(getContext(),"Recuerda llenar todos los campos",Toast.LENGTH_LONG).show();
                }else if (errorFechaActual){
                    Toast.makeText(getContext(),"El MacroCiclo debe durar como mínimo 3 meses",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Método funcionBttRetroceder: Encargado de realizar el movimiento del fragment anterior: Home, a tráves del NavController
    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });

    }

    public boolean verificarCampos(){

        if (!edtDate1.getText().toString().trim().equals("Fecha") &&
                !edtDate2.getText().toString().trim().equals("Fecha") &&
                !edtNom.getText().toString().trim().isEmpty()){
            return true;
        }else{
            return false;
        }

    }

    public boolean validarTiempo(){

        boolean valido = true;
        String[] inicio = edtDate1.getText().toString().trim().split("-");
        String[] fin = edtDate2.getText().toString().trim().split("-");

        Calendar fecha1 = Calendar.getInstance();
        fecha1.set(Integer.parseInt(inicio[2]),Integer.parseInt(inicio[1])-1,Integer.parseInt(inicio[0]));

        Calendar fecha2 = Calendar.getInstance();
        fecha2.set(Integer.parseInt(fin[2]),Integer.parseInt(fin[1])-1,Integer.parseInt(fin[0]));

        if (validarFechaActual(fecha1.get(Calendar.DATE), fecha1.get(Calendar.MONTH), fecha1.get(Calendar.YEAR))){
            errorFechaActual = false;
            Toast.makeText(getContext(),"La fecha de inicio debe ser mayor igual a la fecha actual",Toast.LENGTH_LONG).show();
            return false;
        }

        if (validarFechaActual(fecha2.get(Calendar.DATE), fecha2.get(Calendar.MONTH), fecha2.get(Calendar.YEAR))){
            errorFechaActual = false;
            Toast.makeText(getContext(),"La fecha de fin debe ser mayor a la fecha actual",Toast.LENGTH_LONG).show();
            return false;
        }

        int meses = calcularMes(fecha1, fecha2);
        if (meses < 3){
            errorFechaActual = true;
            return false;
        }
        return valido;
    }

    public boolean validarFechaActual(int day, int month, int year){

        Calendar fecha = Calendar.getInstance();
        fecha.set(year,month,day);

        int meses = calcularMes(cal,fecha);
        int anho=cal.get(Calendar.YEAR);


        if (year >= anho && meses >= 0){

            if (month == cal.get(Calendar.MONTH) && (day-cal.get(Calendar.DATE))<0 && year == anho ){
                return true;
            }else {
                return false;
            }
        }
        return true;
    }

    public int calcularMes(Calendar inicio, Calendar fin){
        int diferenciaAnios = calcularAnio(inicio,fin);
        int diferencia = (diferenciaAnios*12)+(fin.get(Calendar.MONTH)-inicio.get(Calendar.MONTH));
        if (fin.get(Calendar.DATE) < inicio.get(Calendar.DATE)){
            diferencia--;
        }
        return diferencia;
    }

    public int calcularAnio(Calendar inicio, Calendar fin){
        int diferencia = fin.get(Calendar.YEAR) - inicio.get(Calendar.YEAR);
        if (fin.get(Calendar.DAY_OF_YEAR) > inicio.get(Calendar.DAY_OF_YEAR) && diferencia != 0){
            diferencia--;
        }
        return diferencia;
    }

    //Método crearMacroCiclo:
    public void crearMacroCiclo(){

        String nombreCiclo = edtNom.getText().toString().trim();
        String inicioCiclo = edtDate1.getText().toString().trim();
        String finCiclo = edtDate2.getText().toString().trim();

        if (callback != null){
            callback.onCallBack("AddCycle",nombreCiclo,inicioCiclo,finCiclo);
        }

    }

}