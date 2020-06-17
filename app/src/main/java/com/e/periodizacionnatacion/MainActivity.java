package com.e.periodizacionnatacion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.callback.CallBackListener;
import com.bumptech.glide.Glide;
import com.e.periodizacionnatacion.Clases.Cronograma;
import com.e.periodizacionnatacion.Clases.DatoBasico;
import com.e.periodizacionnatacion.Clases.Integrante;
import com.e.periodizacionnatacion.Clases.MacroCiclo;
import com.e.periodizacionnatacion.Clases.Usuario;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements CallBackListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ImageView imagenUsuario;
    private TextView nombreUsuario;
    private TextView correoUsuario;

    private Usuario usuario;
    private MacroCiclo MacroCiclo;
    private ArrayList<Integrante> Integrantes;
    ArrayList<Cronograma> cronogramas;
    private boolean existe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_addcyle, R.id.nav_addprueba).setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        imagenUsuario = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.ImagenUser);
        nombreUsuario = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nombreUser);
        correoUsuario = (TextView) navigationView.getHeaderView(0).findViewById(R.id.correoUser);

        //usuario =  (Usuario) getIntent().getSerializableExtra("Usuario");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(user);
    }

    //Change UI according to user data.
    public void  updateUI(FirebaseUser user){
        Query query = FirebaseDatabase.getInstance().getReference().child("Usuario").child(user.getUid());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuario = dataSnapshot.getValue(Usuario.class);
                Log.e(">>>>>>", usuario.getNombre());
                cambiarValorHeader();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void cambiarValorHeader(){
        nombreUsuario.setText(usuario.getNombre());
        correoUsuario.setText(usuario.getCorreo());
        Glide.with(this).load(usuario.getFoto()).into(imagenUsuario);
    }

    public void cambioAlLog(){
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onCallBack(String fragmento, String dato1, String dato2, String dato3) {
        switch (fragmento){
            case "AddCycle":
                inicializarMacrociclo(dato1,dato2,dato3);
                break;
            case "AddWaterCycle":
                agregarDiasAgua(dato1,dato2,dato3);
                break;
            case "AddEarthCycle":
                agregarDiasTierra(dato1,dato2,dato3);
                break;
            case "AddVolumenCycle":
                agregarVolumen(dato1,dato2);
        }
    }

    public void inicializarMacrociclo(String nombre, String inicio, String fin){

        MacroCiclo = new MacroCiclo(nombre,"id",inicio,fin);
    }

    public void agregarDiasAgua(String resistencia, String tecnica, String velocidad){

        MacroCiclo.getDiasAgua().getTrabajo1().setDato1(resistencia.substring(0,resistencia.length()-1));
        MacroCiclo.getDiasAgua().getTrabajo2().setDato1(tecnica.substring(0,tecnica.length()-1));
        MacroCiclo.getDiasAgua().getTrabajo3().setDato1(velocidad.substring(0,velocidad.length()-1));

    }

    public void agregarDiasTierra(String construccion, String conversion, String maximo){

        MacroCiclo.getDiasTierra().getTrabajo1().setDato1(construccion.substring(0,construccion.length()-1));
        MacroCiclo.getDiasTierra().getTrabajo2().setDato1(conversion.substring(0,conversion.length()-1));
        MacroCiclo.getDiasTierra().getTrabajo3().setDato1(maximo.substring(0,maximo.length()-1));

    }

    public void agregarVolumen(String volumenAgua, String volumenTierra){
        MacroCiclo.getDiasAgua().setVolumen(volumenAgua);
        MacroCiclo.getDiasTierra().setVolumen(volumenTierra);

        //calcular el cronograma
        cronogramas = new ArrayList<Cronograma>();
        cronogramas = MacroCiclo.GenerarCronogramasDeEntrenamiento();

        //Generar y verificar si existen las ID de cronograma
        //Se genera un ID para el cronograma y se verifica si existe en la BD
        String id =  UUID.randomUUID()+"";
        verificarSiExisteId("Cronograma", id);
        while (existe){
            id =  UUID.randomUUID()+"";
            verificarSiExisteId("Cronograma", id);
        }

        //Agregar ID a DiasAgua y al cronograma respectivo
        MacroCiclo.getDiasAgua().setCronograma(id);
        cronogramas.get(0).setID(id);

        //Se genera un ID para el cronograma y se verifica si existe en la BD
        id =  UUID.randomUUID()+"";
        verificarSiExisteId("Cronograma", id);
        if (id.equals( MacroCiclo.getDiasAgua().getCronograma())){
            existe = true;
        }
        while (existe){
            id =  UUID.randomUUID()+"";
            verificarSiExisteId("Cronograma", id);
            if (id.equals( MacroCiclo.getDiasAgua().getCronograma())){
                existe = true;
            }
        }

        //Agregar ID a DiasTierra y al cronograma respectivo
        MacroCiclo.getDiasTierra().setCronograma(id);
        cronogramas.get(1).setID(id);
    }

    @Override
    public ArrayList<Cronograma> onCallBackVistaPrevia(String Fragmento) {
        return cronogramas;
    }

    @Override
    public void onCallBackArray(String fragmento, ArrayList<String> array) {
        switch (fragmento){
            case "AddIntegrantes":
                agregarIntegrantes(array);
                break;
        }
    }

    public void agregarIntegrantes(ArrayList<String> nombresIntegrante){

        Integrantes = new ArrayList<Integrante>();
        Integrante integrante = new Integrante();
        DatoBasico datos =  new DatoBasico();

        for (int i=0; i<nombresIntegrante.size(); i++){
            //Se pide el nombre
            String nombre = nombresIntegrante.get(i);
            //Se genera un ID para el integrante y se verifica si existe en la BD
            String id =  UUID.randomUUID()+"";
            verificarSiExisteId("Integrantes", id);
            while (existe){
                id =  UUID.randomUUID()+"";
                verificarSiExisteId("Integrantes", id);
            }
            //Se crea el integrante
            integrante = new Integrante(nombre,id);
            datos = new DatoBasico(nombre,id);
            MacroCiclo.getIntegrantes().add(datos);
            Integrantes.add(integrante);
            //Agregar Integrantes a la base de datos
            FirebaseDatabase.getInstance().getReference().child("Integrantes").child(integrante.getID()).setValue(integrante);
        }
        //Agrega el Macrociclo a la base de datos
        agregarMacrocicloBD();
    }

    public void agregarMacrocicloBD(){

        //Genera una ID para el MacroCiclo y verifica que no exista
        String id =  UUID.randomUUID()+"";
        verificarSiExisteId("MacroCiclo", id);
        while (existe){
            id =  UUID.randomUUID()+"";
            verificarSiExisteId("MacroCiclo", id);
        }

        //Asigna la ID al MacroCiclo y en la referencia del Usuario
        MacroCiclo.setID(id);
        usuario.getMacroCiclos().add(new DatoBasico(MacroCiclo.getNombre(), MacroCiclo.getID()));

        //Agrega el MacroCiclo y los Cronogramas a la base de datos. Tambien refresca la informacion del Usuario
        FirebaseDatabase.getInstance().getReference().child("Usuario").child(usuario.getID()).setValue(usuario);
        FirebaseDatabase.getInstance().getReference().child("MacroCiclo").child(MacroCiclo.getID()).setValue(MacroCiclo);
        FirebaseDatabase.getInstance().getReference().child("Cronograma").child(cronogramas.get(0).getID()).setValue(cronogramas.get(0));
        FirebaseDatabase.getInstance().getReference().child("Cronograma").child(cronogramas.get(1).getID()).setValue(cronogramas.get(1));

        Toast.makeText(this,"MacroCiclo Agregado",Toast.LENGTH_LONG).show();
    }

    public void verificarSiExisteId(String rama, final String id){

        Query query = FirebaseDatabase.getInstance().getReference().child(rama);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                existe = dataSnapshot.hasChild(id);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
