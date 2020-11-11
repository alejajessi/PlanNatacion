package com.cyclenotification;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.callback.CallBackListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class Fcm extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e("token", "mi token es: " + s);
        guardarToken(s);
    }

    public void guardarToken (String s){
        Log.e("Jess","JESSICA PASO POR AQUÍ");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Usuario");
        if(user == null){
            Log.e("Error",">>>>>> Problemas en autenticación <<<<<<");
        }
        if(ref == null){
            Log.e("Error",">>>>>> Problemas en base de datos <<<<<<");
        }
        ref.child(user.getUid()).child("token").setValue(s);
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
