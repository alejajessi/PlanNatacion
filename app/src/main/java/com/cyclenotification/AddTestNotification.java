package com.cyclenotification;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.callback.CallBackListener;
import com.e.periodizacionnatacion.Clases.MacroCiclo;
import com.e.periodizacionnatacion.Clases.Usuario;
import com.e.periodizacionnatacion.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class AddTestNotification extends Fragment {


    private ImageButton bttDate1;
    private EditText edtNom;
    private EditText edtDate1;
    private Calendar cal;
    private DatePickerDialog pickerDialog;
    private CallBackListener callback;
    private Button avanzar;
    private Button retroceder;
    private  MacroCiclo macro;


    public AddTestNotification() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_test_notification, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallBackListener) {
            callback = (CallBackListener) getActivity();
        }
        if (callback != null){
            macro = callback.onCallBackInfoCycle("AddTestNotification");

        }else{
            Log.e("Problemas con el CallBack", "El callback es null en AddTestNotification");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarID(view);

        funcionBttDate1();

        final NavController navController= Navigation.findNavController(view);

        funcionBttAvanzar(navController);

        funcionBttRetroceder(navController);

    }

    public void inicializarID(View view){

        edtNom = view.findViewById(R.id.et_nomb_addtest);
        edtDate1 = view.findViewById(R.id.ed_date1_test);
        bttDate1 = view.findViewById(R.id.btt_cal1_test);
        avanzar = view.findViewById(R.id.avan_addtest);
        retroceder = view.findViewById(R.id.retro_addtest);
        actualizacionFecha();

    }

    public void actualizacionFecha(){

        cal=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(cal.getTime());
        edtDate1.setText(formattedDate);

    }

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
                    }
                }, year, month, day);
                pickerDialog.show();
            }
        });
    }

    //Método funcionBttAvanzar: Encargado de realizar el movimiento al fragment siguiente:
    public void funcionBttAvanzar(NavController navController){

        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean campos=verificarCampos();
                boolean tiempo=validarTiempo();

                if (campos == true && tiempo == true ){

                    if (callback != null){
                        String nombre = edtNom.getText().toString().trim();
                        String fecha = edtDate1.getText().toString().trim();
                        callback.onCallBackNotification("AddTestNotification",nombre,fecha,macro.getNombre());
                    }
                    Navigation.findNavController(v).navigate(R.id.nav_home);

                }else if(campos==false){
                    Toast.makeText(getContext(),"Recuerda asignar un nombre al test",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Método funcionBttRetroceder: Encargado de realizar el movimiento del fragment anterior:
    public void funcionBttRetroceder(NavController navController){

        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Navigation.findNavController(v).navigate(R.id.nav_select_Cycle);
            }
        });

    }

    private boolean verificarCampos() {
        if (!edtNom.getText().toString().trim().isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public boolean validarTiempo(){

        boolean valido = false;

        if (macro != null){
            String[] inicioMacro = macro.getInicio().split("-");
            String[] finMacro = macro.getFin().split("-");
            String[] test = edtDate1.getText().toString().trim().split("-");

            Calendar inicio = Calendar.getInstance();
            inicio.set(Integer.parseInt(inicioMacro[2]),Integer.parseInt(inicioMacro[1])-1,Integer.parseInt(inicioMacro[0]));

            Calendar fin = Calendar.getInstance();
            fin.set(Integer.parseInt(finMacro[2]),Integer.parseInt(finMacro[1])-1,Integer.parseInt(finMacro[0]));

            Calendar fechaTest = Calendar.getInstance();
            fechaTest.set(Integer.parseInt(test[2]),Integer.parseInt(test[1])-1,Integer.parseInt(test[0]));

            if (!fechaTest.before(inicio) && !fechaTest.after(fin)){
                valido = true;
            }else{
                Toast.makeText(getContext(),"La fecha del test debe estar entre "+macro.getInicio()
                        +" y "+macro.getFin(),Toast.LENGTH_LONG).show();
            }
        }else{
            Log.e("Problemas con el Macro ciclo", "El Macro ciclo es null");
        }

        return valido;
    }


}
