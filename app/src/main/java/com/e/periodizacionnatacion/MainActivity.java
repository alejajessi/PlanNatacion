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
import com.e.periodizacionnatacion.Clases.Dato;
import com.e.periodizacionnatacion.Clases.DatoBasico;
import com.e.periodizacionnatacion.Clases.Dia;
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

    /**
     * Atributo para el control de la AppBar
     */
    private AppBarConfiguration mAppBarConfiguration;

    /**
     * Atributo que permite la visualización de la imagen de perfil del usuario en el menú
     */
    private ImageView imagenUsuario;

    /**
     * Componente del xml para modificar en el menú el nombre del usuario tipo TextView
     */
    private TextView nombreUsuario;

    /**
     * Componente del xml para modificar en el menú el correo del usuario tipo TextView
     */
    private TextView correoUsuario;

    /**
     * Objeto tipo Usuario
     */
    private Usuario usuario;

    /**
     * Objeto MacroCilo para funcionalidad de añadir y mostrar macro ciclo
     */
    private MacroCiclo MacroCiclo;

    /**
     *ArrayList de integrantes pertenecientes al macrociclo tipo: Integrantes
     */
    private ArrayList<Integrante> integrantes;
    
    /**
     * ArrayList de cronogramas para las clasificaciones tierra y agua tipo:Cronograma
     */
    private ArrayList<Cronograma> cronogramas;

    /**
     * Valor booleano para el control del generador de ID
     */
    private boolean existe;

    private boolean cambioFrag;

    private DialogCargando carga;

    private String mostrando;

    /**
     * Método OnCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_menuMacroCiclo, R.id.nav_menuPruebas,
                R.id.nav_canceltiempo, R.id.nav_deletecycle,
                R.id.nav_legalinfo).setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        imagenUsuario = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.ImagenUser);
        nombreUsuario = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nombreUser);
        correoUsuario = (TextView) navigationView.getHeaderView(0).findViewById(R.id.correoUser);

        carga = new DialogCargando(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(user);
        
    }

    /**
     * Método updateUI : Encargado de cambiar o actualizar la información del usuario haciendo uso de la base de datos
     * @param user usuario de FireBase
     */
    public void  updateUI(FirebaseUser user){
        carga.iniciar();
        FirebaseDatabase.getInstance().getReference().child("Usuario").child(user.getUid())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuario = dataSnapshot.getValue(Usuario.class);
                if (usuario != null){
                    Log.e(">>>>>>", usuario.toString());
                    carga.detener();
                }
                cambiarValorHeader();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                carga.detener();
            }
        });
    }

    /**
     * Método cambiarValorHeader  : Encargado de actualizar los datos del header de acuerdo al usuario actual
     */
    public void cambiarValorHeader(){
        if (usuario !=null){
            nombreUsuario.setText(usuario.getNombre());
            correoUsuario.setText(usuario.getCorreo());
            Glide.with(this).load(usuario.getFoto()).into(imagenUsuario);
        }else{
            cambioAlLog();
        }
    }

    /**
     * Método cambioAlLog : Realiza el movimiento de la actividad Main a la actividad login
     */
    public void cambioAlLog(){
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Método onSupportNavigateUp : Encargado de la funcionalidad del Navigation Controller
     * @return valor booleano
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Método onCallBack : Encargado del direccionamiento de funcionalidades entre el Main y los fragmentos que correspondan
     * @param fragmento fragmento correspondiente ( nombre del fragmento en el que se encuentra )
     * @param dato1 dato para enviar - puede ser null
     * @param dato2 dato para enviar - puede ser null
     * @param dato3 dato para enviar - puede ser null
     */
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
                break;
            case "MostrarMacroCiclo":
                pedirMacroCiclo(dato1);
                break;
            case "MostrarInfoCycle":
                cronogramas = new ArrayList<Cronograma>();
                cronogramas.add(new Cronograma());
                cronogramas.add(new Cronograma());
                pedirCronograma(MacroCiclo.getDiasAgua().getCronograma(), 0);
                pedirCronograma(MacroCiclo.getDiasTierra().getCronograma(), 1);
                break;
            case "MostrarInfoMonths":
                mostrando = dato1+dato2+dato3+"";
                Log.e("Mostrando",">>>>>>> "+mostrando);
                break;
            case "MostrarInfoWeek":
                mostrando = dato1+dato2+"";
                Log.e("Mostrando",">>>>>>> "+mostrando);
                break;
            case "MostrarInfoDay":
                mostrando = dato1+dato2+"";
                Log.e("Mostrando",">>>>>>> "+mostrando);
                break;
        }
    }

    /**
     * Método inicializarMacroCiclo : Encargado de crear el macro ciclo
     * @param nombre nombre del macro ciclo tipo : String
     * @param inicio fecha de inicio del macro ciclo tipo : String
     * @param fin fecha de fin del macro ciclo tipo : String
     */
    public void inicializarMacrociclo(String nombre, String inicio, String fin){

        MacroCiclo = new MacroCiclo(nombre,"id",inicio,fin);
    }

    /**
     * Método agregarDiasAgua : Encargado de añadir al macro ciclo los días a trabajar cada una de las habilidades de agua
     * @param resistencia Cadena de caracteres con los días a trabajar la habilidad de resistencia
     * @param tecnica Cadena de caracteres con los días a trabajar la habilidad de ténica
     * @param velocidad Cadena de caracteres con los días a trabajar la habilidad de velocidad
     */
    public void agregarDiasAgua(String resistencia, String tecnica, String velocidad){

        MacroCiclo.getDiasAgua().getTrabajo1().setDato1(resistencia.substring(0,resistencia.length()-1));
        MacroCiclo.getDiasAgua().getTrabajo2().setDato1(tecnica.substring(0,tecnica.length()-1));
        MacroCiclo.getDiasAgua().getTrabajo3().setDato1(velocidad.substring(0,velocidad.length()-1));

    }

    /**
     * Método agregarDiasTierra : Encargado de añadir al macro ciclo los días a trabajar cada una de las habilidades de tierra
     * @param construccion Cadena de caracteres con los días a trabajar la habilidad de construcción
     * @param conversion Cadena de caracteres con los días a trabajar la habilidad de conversión
     * @param maximo Cadena de caracteres con los días a trabajar la habilidad de máximo
     */
    public void agregarDiasTierra(String construccion, String conversion, String maximo){

        MacroCiclo.getDiasTierra().getTrabajo1().setDato1(construccion.substring(0,construccion.length()-1));
        MacroCiclo.getDiasTierra().getTrabajo2().setDato1(conversion.substring(0,conversion.length()-1));
        MacroCiclo.getDiasTierra().getTrabajo3().setDato1(maximo.substring(0,maximo.length()-1));

    }

    /**
     * Método agregarVolumen : Encargado de distribuir los valores de volumenes ingresados por el usuario en el cronograma correspondiente (agua/tierra)
     * @param volumenAgua Cadena de caracteres con valor númerico de volumen de agua a trabajar en el macro ciclo
     * @param volumenTierra Cadena de caracteres con valor númerico de volumen de tierra a trabajar en el macro ciclo
     */
    public void agregarVolumen(String volumenAgua, String volumenTierra){
        MacroCiclo.getDiasAgua().setVolumen(volumenAgua);
        MacroCiclo.getDiasTierra().setVolumen(volumenTierra);

        //calcular el cronograma
        cronogramas = new ArrayList<Cronograma>();
        cronogramas = MacroCiclo.GenerarCronogramasDeEntrenamiento();

        //Generar y verificar si existen las ID de cronograma
        //Se genera un ID para el cronograma y se verifica si existe en la BD
        carga.iniciar();
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
        carga.iniciar();
        id =  UUID.randomUUID()+"";
        verificarSiExisteId("Cronograma", id);
        if (id.equals( MacroCiclo.getDiasAgua().getCronograma())){
            existe = true;
            carga.iniciar();
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

    /**
     * Método onCallBackCronograma : Encargado de pasar el cronograma al fragmento
     * @param fragmento Cadena de caracteres que indica el fragmento al que debe ser enviado el cronograma
     * @return Arraylist <Cronograma>
     */
    @Override
    public ArrayList<Cronograma> onCallBackCronograma(String fragmento) {
        return cronogramas;
    }

    /**
     * Método onCallBackIntegrante : Encargado de enviar el ArrayList de Integrantes al agregarIntegrantes desde el fragmento AddIntegrantes
     * @param fragmento Cadena de caracteres que indica el fragmento del que viene el array
     * @param array ArrayList de Integrantes
     */
    @Override
    public void onCallBackIntegrante(String fragmento, ArrayList<Integrante> array) {
        switch (fragmento){
            case "AddIntegrantes":
                agregarIntegrantes(array);
                break;
        }
    }

    /**
     * Método agregarIntegrantes : Encargado de añadir los integrantes a la base de datos y al macro ciclo
     * @param integrantes
     */
    public void agregarIntegrantes(ArrayList<Integrante> integrantes){
        this.integrantes = new ArrayList<Integrante>();
        DatoBasico datos =  new DatoBasico();

        for (int i = 0; i < integrantes.size(); i++){

            //Se genera un ID para el integrante y se verifica si existe en la BD
            carga.iniciar();
            String id =  UUID.randomUUID()+"";
            verificarSiExisteId("Integrantes", id);
            while (existe){
                id =  UUID.randomUUID()+"";
                verificarSiExisteId("Integrantes", id);
            }
            Log.e("ID", id);
            //Asigna el ID generado
            integrantes.get(i).setID(id);
            datos = new DatoBasico(integrantes.get(i).getNombre(),integrantes.get(i).getID());
            MacroCiclo.getIntegrantes().add(datos);
            this.integrantes.add(integrantes.get(i));
            //Agregar Integrantes a la base de datos
            FirebaseDatabase.getInstance().getReference().child("Integrantes").child(integrantes.get(i).getID()).setValue(integrantes.get(i));
        }
        //Agrega el Macrociclo a la base de datos
        agregarMacrocicloBD();
    }

    /**
     * Método agregarMacrocicloBD : Encargado de añadir el macro ciclo a la base de datos incluye Usuario, MacroCiclo y Cronogramas
     */
    public void agregarMacrocicloBD(){

        //Genera una ID para el MacroCiclo y verifica que no exista
        carga.iniciar();
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

    /**
     * Método verificarSiExisteId : Encargado de validar si existe un ID igual al del parametro en la base de datos según la rama que le corresponda
     * @param rama Cadena de caracteres que indica dónde debe buscar en la base de datos
     * @param id Cadena de caracteres que representa un ID
     */
    public void verificarSiExisteId(String rama, final String id){

        Query query = FirebaseDatabase.getInstance().getReference().child(rama);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                existe = dataSnapshot.hasChild(id);
                if (!existe){
                    carga.detener();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                carga.detener();
            }
        });
    }

    /**
     * Método onCallBackMostrarCiclo : Encargado de enviar el ArrayList de los MacroCiclos ligados al usuario actual
     * @param fragmento Cadena de caracteres que define el fragmento actual
     * @return ArrayList de DatoBasico
     */
    @Override
    public ArrayList<DatoBasico> onCallBackMostrarCiclo(String fragmento) {
        ArrayList<DatoBasico> ciclos = new ArrayList<DatoBasico>();
        if (usuario.getMacroCiclos().isEmpty() ||usuario.getMacroCiclos() == null){
            return null;
        }
        return usuario.getMacroCiclos();
    }

    /**
     * Método pedirMacroCiclo : Encargado de extraer de la base de datos el macrociclo seleccionado
     * @param id Cadena de caracteres que indica el id del macro ciclo seleccionado
     */
    public void pedirMacroCiclo(String id){

        cambioFrag = false;
        if (MacroCiclo != null && MacroCiclo.getID().equals(id)){
            cambioFrag = true;
            return;
        }
        carga.iniciar();
        Query query = FirebaseDatabase.getInstance().getReference().child("MacroCiclo").child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MacroCiclo = dataSnapshot.getValue(MacroCiclo.class);
                cambioFrag=true;
                carga.detener();
                Log.e("Main","MacroCiclo LISTO");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                carga.detener();
            }
        });
    }

    /**
     * Método pedirCronograma : Encargado de pedir el cronograma de la base de dato
     * @param id Cadena de caracteres que representa el id del cronograma
     * @param posicion Cadena de caracteres que representa un valor númerico que indica si es tierra ó agua
     */
    public void pedirCronograma(String id, final int posicion){
        if (!cronogramas.isEmpty() && cronogramas.size()>posicion
                && cronogramas.get(posicion).getID().equals(id)){
           // cambioDeFragment = false;
            return;
        }
        if (posicion==0){
            carga.iniciar();
        }
        Query query = FirebaseDatabase.getInstance().getReference().child("Cronograma").child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Cronograma cronograma = dataSnapshot.getValue(Cronograma.class);
                cronogramas.set(posicion,cronograma);
                if (posicion==1){
                    carga.detener();
                }
            //    cambioDeFragment = false;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                carga.detener();
            }
        });
    }

    /**
     *  Método onCallBackInfoCycle : Encargado de solicitar la información del macro ciclo
     * @param fragmento Cadena de caracteres que identifica el fragment actual
     * @return MacroCiclo
     */
    @Override
    public com.e.periodizacionnatacion.Clases.MacroCiclo onCallBackInfoCycle(String fragmento) {
        if (MacroCiclo != null){
            return MacroCiclo;
        }
        return null;
    }

    /**
     * Método onCallBackDeleteMacroCiclo : Encargado de solicitar la información del macro ciclo a eliminar
     * @param macroCiclo Macro ciclo a eliminar
     */
    @Override
    public void onCallBackDeleteMacroCiclo(com.e.periodizacionnatacion.Clases.MacroCiclo macroCiclo) {
      deleteMacroCiclo(macroCiclo);
    }

    @Override
    public String onCallBackMostrar(String fragmento) {
        return mostrando;
    }

    //No sé como borrar desde aquí jijij holi
    public void deleteMacroCiclo ( MacroCiclo macroCiclo){
    }

    @Override
    public Dia onCallBackMostrarDia(String fragmento) {
        Dia dia = null;
        switch (fragmento){
            case "MostrarInfoDay":
                dia = darDia();
                break;
        }
        return dia;
    }

    public Dia darDia(){

        Dia diaSeleccionado = new Dia();
        String[] datos = mostrando.split("-");

        //Día Agua o Tierra
        int cualDia = 0;
        if (datos[0].equals("Tierra")){
            cualDia = 1;
        }

        //Periodo
        Dato periodo = null;
        if (datos[1].equals("Periodo1")){

            periodo = cronogramas.get(cualDia).getPeriodo1();

        }else if (datos[1].equals("Periodo2")){

            periodo = cronogramas.get(cualDia).getPeriodo2();

        }else if (datos[1].equals("Periodo3")){

            periodo = cronogramas.get(cualDia).getPeriodo3();

        }

        //Mes - semana - dia
        int mes= Integer.parseInt(datos[2]);
        int semana= Integer.parseInt(datos[3]);
        int dia= Integer.parseInt(datos[4]);

        diaSeleccionado = periodo.getFecha().get(mes).getFecha().get(semana).getDias().get(dia);
        return diaSeleccionado;
    }

    @Override
    public boolean onCallBackCambioFragment() {
        return cambioFrag;
    }
}
