package com.cyclenotification;

import android.util.Log;

import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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

        String from = remoteMessage.getFrom();
        Log.e("TAG","Mensaje de recibido" + from);

        if(remoteMessage.getNotification() != null){
            Log.e("TAG", "El título es:"+ remoteMessage.getNotification().getTitle());
            Log.e("TAG", "El detalle es:"+ remoteMessage.getNotification().getBody());
        }

        if(remoteMessage.getData().size() >0 ){
            Log.e("TAG", "Título es:" + remoteMessage.getData().get("título"));
            Log.e("TAG", "detalle es:" + remoteMessage.getData().get("detalle"));
            Log.e("TAG", "el color es:" + remoteMessage.getData().get("título"));
        }
    }
}
