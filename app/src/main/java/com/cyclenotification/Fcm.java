package com.cyclenotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.e.periodizacionnatacion.MainActivity;
import com.e.periodizacionnatacion.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Fcm extends FirebaseMessagingService {

    FirebaseUser user;
    DatabaseReference ref;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e("token", "mi token es: " + s);
        guardarToken(s);
    }

    public void guardarToken (final String s){
        Log.e("Jess","JESSICA PASO POR AQUÍ");
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref= FirebaseDatabase.getInstance().getReference().child("Usuario");
        if(user == null){
            Log.e("Error",">>>>>> Problemas en autenticación <<<<<<");
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if (user != null){
                        ref.child(user.getUid()).child("token").setValue(s);
                        cancel();
                    }else{
                        user = FirebaseAuth.getInstance().getCurrentUser();
                    }
                }
            }, 1000);
        }
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getData().size() >0 ){
            String nombre = remoteMessage.getData().get("nombre");
            String fecha = remoteMessage.getData().get("fecha");
            String macro = remoteMessage.getData().get("macrociclo");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //Consultar la fecha antes de entrar al metodo
                if (validarTiempo(fecha)){
                    Log.e("Muestra", "El Nombre es:" + nombre);
                    Log.e("Muestra", "La Fecha es:" + fecha);
                    Log.e("Muestra", "El Macrociclo es:" + macro);
                    mayorqueoreo(nombre,fecha,macro);
                }else{
                    Log.e("No muestra", "El Nombre es:" + nombre);
                    Log.e("No muestra", "La Fecha es:" + fecha);
                    Log.e("No muestra", "El Macrociclo es:" + macro);
                }
            }
        }
    }

    private void mayorqueoreo(String nombre, String fecha, String macro) {
        String id = "Mensaje";

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(id, "nuevo", NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            nm.createNotificationChannel(nc);
        }

        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Macrociclo: "+macro)
                .setSmallIcon(R.mipmap.logo_round)
                .setContentText("Test: "+nombre+" / Fecha: "+fecha)
                .setContentIntent(clicknotification())
                .setContentInfo("nuevo");

        Random rm = new Random();
        int idNotify = rm.nextInt(8000);

        assert nm != null;
        nm.notify(idNotify,builder.build());

    }

    public PendingIntent clicknotification(){
        Intent nf = new Intent(getApplicationContext(), MainActivity.class);
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this,0,nf,0);
    }

    public boolean validarTiempo(String fecha){

        boolean valido = false;

        String[] notTest = fecha.split("-");

        Calendar actual = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String[] act = df.format(actual.getTime()).split("-");

        //Log.e("Actual Fcm",df.format(actual.getTime()));
        if (Integer.parseInt(notTest[0]) == (Integer.parseInt(act[0]))
                && Integer.parseInt(notTest[1]) == (Integer.parseInt(act[1]))
                && Integer.parseInt(notTest[2]) == (Integer.parseInt(act[2]))){
            valido = true;
        }

        return valido;
    }
}
