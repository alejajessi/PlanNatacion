package com.e.periodizacionnatacion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.e.periodizacionnatacion.Clases.Usuario;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;


public class Login extends AppCompatActivity {

    public static final int RC_SIGN_IN = 9001;

    private GoogleSignInClient clienteGoogle;
    private FirebaseAuth fireAuth;
    private Usuario usuario;
    private Activity loginAct;

    private SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        clienteGoogle = GoogleSignIn.getClient(this, gso);

        fireAuth = FirebaseAuth.getInstance();
        loginAct = this;

        signInButton = findViewById(R.id.sesion_google);
        signInButton.setSize(SignInButton.SIZE_WIDE);

        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sesion_google:
                        signIn();
                        break;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);
        FirebaseUser currentUser = fireAuth.getCurrentUser();
        updateUI(currentUser);

    }

    //Change UI according to user data.
    public void  updateUI(FirebaseUser account){

        if (account != null) {
            Toast.makeText(this,"Sesión iniciada exitosamente",Toast.LENGTH_LONG).show();
            cambioAlMain();
        } else {
            Toast.makeText(this,"No se ha iniciado sesión",Toast.LENGTH_LONG).show();
        }
       // if(account != null){
       //     Toast.makeText(this,"Sesión iniciada exitosamente",Toast.LENGTH_LONG).show();
       //     cambioAlMain();
       // }else {
       //     Toast.makeText(this,"No se ha iniciado sesión",Toast.LENGTH_LONG).show();
       // }
    }

    private void signIn () {
        Intent signInIntent = clienteGoogle.getSignInIntent();
        startActivityForResult (signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            //updateUI(account);
            firebaseAuthWithGoogle(account);
            Log.e(">>>>>>>>", account.getEmail());
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(">>>>>>>>>>>>>>>>>>>", "signInResult:failed code=" + e.getStatusCode());
            Log.w(">>>>>>>>>>>>>>>>>>>", "Google sign in failed", e);
            updateUI(null);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        //showProgressDialog();
        // [END_EXCLUDE]
        if (acct != null) {
            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            Log.d("GOOGLE AUTH", "firebaseAuthWithGoogle:" + acct.getId());
            fireAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("GOOGLE AUTH", "signInWithCredential:success");
                                boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                if (isNew) {

                                    Log.e("GOOGLE AUTH", "I'm a new user.");
                                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(loginAct);

                                    String id = user.getUid();
                                    String nombre = acct.getDisplayName();
                                    String correo = acct.getEmail();
                                    String foto = acct.getPhotoUrl()+"";

                                    usuario = new Usuario(correo,foto,id,nombre);
                                    FirebaseDatabase.getInstance().getReference().child("Usuario").child(id).setValue(usuario);
                                    updateUI(user);

                                } else {

                                    Log.e("GOOGLE AUTH", "I'm an old user.");
                                    updateUI(user);

                                }

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("GOOGLE AUTH", "signInWithCredential:failure", task.getException());
                                Snackbar.make(signInButton, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                            // [START_EXCLUDE]
                            //hideProgressDialog();
                            // [END_EXCLUDE]
                        }
                    });
        }
    }

    public void cambioAlMain(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.putExtra("Usuario", usuario);
        startActivity(intent);
    }

}
