package com.e.periodizacionnatacion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.callback.CallBackListener;
import com.bumptech.glide.Glide;
import com.cyclelogin.Login;
import com.e.periodizacionnatacion.Clases.Cronograma;
import com.e.periodizacionnatacion.Clases.Dato;
import com.e.periodizacionnatacion.Clases.DatoBasico;
import com.e.periodizacionnatacion.Clases.Dia;
import com.e.periodizacionnatacion.Clases.Integrante;
import com.e.periodizacionnatacion.Clases.MacroCiclo;
import com.e.periodizacionnatacion.Clases.Notificacion;
import com.e.periodizacionnatacion.Clases.Prueba;
import com.e.periodizacionnatacion.Clases.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lowagie.text.pdf.BidiOrder;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
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

    private  String tipoPrueba;

    private String fechaPrueba;

    private String fechaDiaEspecifico;

    private ArrayList<String> fechasDePrueba;


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
                R.id.nav_home, R.id.nav_menuMacroCiclo, R.id.nav_menuPruebas, R.id.nav_deletecycle,
                R.id.nav_select_cycle_specific_day, R.id.nav_legalinfo).setDrawerLayout(drawer)
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
                    suscripcionTopic();
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

    private void suscripcionTopic() {

        FirebaseMessaging.getInstance().subscribeToTopic(usuario.getID()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.w("Suscripción a topic "+usuario.getID(),"Suscripción exitosa");
            }
        });

        Query query = FirebaseDatabase.getInstance().getReference().child("Notificaciones").child(usuario.getID());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Notificacion not = postSnapshot.getValue(Notificacion.class);
                    if (validarTiempoNotificacion(not.getFecha())){
                        onCallBackNotification("MainActivity",not.getNombre(),not.getFecha(),not.getMacrociclo());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

    @Override
    public void onCallBackLogout() {
        carga.iniciar();
        FirebaseAuth.getInstance().signOut();
        carga.detener();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
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
            case "AddVolumenCycle":
                agregarVolumen(dato1,dato2);
                break;
            case "MostrarMacroCiclo":
            case "SelectCycleStatistics":
            case "SelectCycle":
            case "SelectCycleTest":
            case "SelectCycleSpecificDay":
                pedirMacroCiclo(dato1);
                break;
            case "MostrarInfoCycle":
            case "MostrarSpecificDay":
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
            case "MostrarInfoDay":
                mostrando = dato1+dato2+"";
                Log.e("Mostrando",">>>>>>> "+mostrando);
                break;
            case "InfoTest":
                tipoPrueba = dato1;
                fechaPrueba = dato2;
                break;
            case "SelectSpecificDay":
                fechaDiaEspecifico = dato1;
                break;
            case "SelectTestType":
                tipoPrueba = dato1;
                pedirFechasdePrueba();
                break;
        }
    }

    @Override
    public void onCallBackExtendido(String fragmento, String dato1, String dato2, String dato3, String dato4, String dato5) {
        switch (fragmento){
            case "AddEarthCycle":
                agregarDiasTierra(dato1,dato2,dato3,dato4,dato5);
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
    public void agregarDiasTierra(String construccion, String conversion, String maximo, String coordinacion, String flexibilidad){

        MacroCiclo.getDiasTierra().getTrabajo1().setDato1(construccion.substring(0,construccion.length()-1));
        MacroCiclo.getDiasTierra().getTrabajo2().setDato1(conversion.substring(0,conversion.length()-1));
        MacroCiclo.getDiasTierra().getTrabajo3().setDato1(maximo.substring(0,maximo.length()-1));
        MacroCiclo.getDiasTierra().getTrabajo4().setDato1(coordinacion.substring(0,coordinacion.length()-1));
        MacroCiclo.getDiasTierra().getTrabajo5().setDato1(flexibilidad.substring(0,flexibilidad.length()-1));

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
        id =  UUID.randomUUID()+"";
        verificarSiExisteId("Cronograma", id);
        while (existe){
            id =  UUID.randomUUID()+"";
            verificarSiExisteId("Cronograma", id);
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
    public void onCallBackAgregarIntegrante(String fragmento, ArrayList<Integrante> array) {
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
    public void verificarSiExisteId(final String rama, final String id){

        //Verificar si la id generada para el cronograma de dias tierra es diferente a la del
        //cronograma de dias agua cuando generan los cronogramas
        if (rama.equals("Cronograma") && !MacroCiclo.getDiasAgua().getCronograma().isEmpty()
                && MacroCiclo.getDiasTierra().getCronograma().isEmpty()
                && id.equals( MacroCiclo.getDiasAgua().getCronograma())){
            existe = true;
            return;
        }

        Query query = FirebaseDatabase.getInstance().getReference().child(rama);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                existe = dataSnapshot.hasChild(id);
                if (rama.equals("Cronograma") && !MacroCiclo.getDiasAgua().getCronograma().isEmpty() && !existe){
                    carga.detener();
                }else if (!rama.equals("Cronograma") && !existe){
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
                pedirIntegrantes();
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
        cambioFrag = false;
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
                    cambioFrag = true;
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
    public MacroCiclo onCallBackInfoCycle(String fragmento) {
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
        if (fragmento.equals("MostrarSpecificDay")){
            return fechaDiaEspecifico;

        }else if (fragmento.equals("ShowStatistics") || fragmento.equals("ListIntegrant1")) {
            return tipoPrueba;

        }else if (fragmento.equals("ListIntegrant2")){
            return fechaPrueba;

        }else{

            return mostrando;
        }
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

    @Override
    public void onCallBackNotification(String fragmento, String nombre, String fecha, String macrociclo) {
        RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();

        try {

            json.put("to", "/topics/"+usuario.getID());
            JSONObject notificacion = new JSONObject();
            notificacion.put("nombre",nombre);
            notificacion.put("fecha",fecha);
            notificacion.put("macrociclo",macrociclo);

            json.put("data",notificacion);

            String URL = "https://fcm.googleapis.com/fcm/send";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, json,null,null){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String,String> header = new HashMap<>();

                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAAw4k346s:APA91bHYlJ9US9NCtbNyFVMXMiXhTSyPrjBGds6mw66Ca9ZckHUh8JkHlBDK4sdOi-wcfju8u8ftDLRNdZNDh7kCWZ5mOay-OpPM2id673iWFgCy0xPa5oYOjtTvKbExMmrWM5wT2Nqq");
                    return header;
                }
            };

            myrequest.add(request);

            if (fragmento.equals("AddTestNotification")){
                String id =  UUID.randomUUID()+"";
                Notificacion not = new Notificacion(nombre,fecha,macrociclo,false);
                FirebaseDatabase.getInstance().getReference().child("Notificaciones").child(usuario.getID()).child(id).setValue(not);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public boolean validarTiempoNotificacion(String fecha){

        boolean valido = false;

        String[] notTest = fecha.split("-");

        Calendar actual = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String[] act = df.format(actual.getTime()).split("-");

        //Log.e("Actual main",df.format(actual.getTime()));
        if (Integer.parseInt(notTest[0]) == (Integer.parseInt(act[0]))
            && Integer.parseInt(notTest[1]) == (Integer.parseInt(act[1]))
            && Integer.parseInt(notTest[2]) == (Integer.parseInt(act[2]))){
            valido = true;
        }

        return valido;
    }


    public void pedirIntegrantes(){
        ArrayList<DatoBasico> integrantesMacro = MacroCiclo.getIntegrantes();
        integrantes = new ArrayList<Integrante>();
        final int tamIntMacro = integrantesMacro.size();

        if (tamIntMacro>0){
            for (int i = 0; i<integrantesMacro.size();i++){
                String id = integrantesMacro.get(i).getDato2();
                Query query = FirebaseDatabase.getInstance().getReference().child("Integrantes").child(id);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Integrante integrante = dataSnapshot.getValue(Integrante.class);
                        integrantes.add(integrante);
                        if (tamIntMacro == integrantes.size()){
                            cambioFrag = true;
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
        }else {
            Toast.makeText(this,"No hay integrantes asociados a este macrociclo",Toast.LENGTH_LONG).show();
            cambioFrag = true;
            carga.detener();
        }
    }

    @Override
    public ArrayList<Integrante> onCallBackIntegrantes(String fragmento) {

        if (fragmento.equals("ListIntegrant")){
            ArrayList<Integrante> integrantesPrueba = new ArrayList<Integrante>();
            int tamInt = integrantes.size();
            for (int i=0; i<tamInt;i++){
                int tamTip = integrantes.get(i).getTiposPruebas().size();
                for (int j=0;j<tamTip;j++){
                    if (integrantes.get(i).getTiposPruebas().get(j).equals(tipoPrueba)){
                        integrantesPrueba.add(integrantes.get(i));
                        break;
                    }
                }
            }
            if (integrantesPrueba.size()==0){
                return null;
            }
            pedirFechasdePrueba();
            return integrantesPrueba;
        }

        return integrantes;
    }

    @Override
    public void onCallBackAgregarPruebas(ArrayList<Integrante> integ, ArrayList<String> tiempos) {
        Prueba pruebaT = null;
        DatoBasico dato = null;

        int tamP = integ.size();
        int tamI = integrantes.size();
        for (int i=0;i<tamP;i++){

            pruebaT = new Prueba();
            pruebaT.setNombre(tipoPrueba);

            dato = new DatoBasico();
            dato.setDato2(fechaPrueba);

            Integrante intPrueba = integ.get(i);
            for (int j=0;j<tamI;j++){

                Integrante intMacro = integrantes.get(j);
                //Preguntar si los integrantes son los mismos
                if (intMacro.getID().equals(intPrueba.getID())){

                    int tamPruebas = intMacro.getPruebas().size();
                    //Preguntar si tiene pruebas guardadas
                    if (tamPruebas>0){

                        int pos = -1;
                        for (int k=0;k<tamPruebas;k++){

                            //Preguntar si alguna de las pruebas guardadas es igual a la prueba actual
                            if (intMacro.getPruebas().get(k).getNombre().equals(tipoPrueba)){
                                pos = k;
                                break;
                            }
                        }

                        //Pregunta si no hay pruebas del tipo actual
                        if (pos == -1){
                            dato.setDato1(tiempos.get(i));
                            pruebaT.getResultadosPruebas().add(dato);
                            intMacro.getPruebas().add(pruebaT);
                            intMacro.ordenarPrueba(tipoPrueba);
                        }else{
                            dato.setDato1(tiempos.get(i));
                            intMacro.getPruebas().get(pos).getResultadosPruebas().add(dato);
                            intMacro.ordenarPrueba(tipoPrueba);
                        }
                    }else{
                        dato.setDato1(tiempos.get(i));
                        pruebaT.getResultadosPruebas().add(dato);
                        intMacro.getPruebas().add(pruebaT);
                        intMacro.ordenarPrueba(tipoPrueba);
                    }
                    break;
                }
            }
        }
        //Actualizar la informacion de los integrantes en la base de datos
        actualizarIntegrantesDB();
        actualizarFechaPruebas();
    }

    public void actualizarIntegrantesDB(){
        int tam = integrantes.size();
        for (int i=0;i<tam;i++){
            FirebaseDatabase.getInstance().getReference().child("Integrantes").child(integrantes.get(i).getID()).setValue(integrantes.get(i));
        }
    }

    private void actualizarFechaPruebas() {
        int tam = fechasDePrueba.size();
        boolean agregar = true;
        for (int i=0;i<tam;i++){
            if (fechasDePrueba.get(i).equals(fechaPrueba)){
                agregar = false;
                break;
            }
        }
        if (agregar){
            fechasDePrueba.add(fechaPrueba);
            FirebaseDatabase.getInstance().getReference().child("FechasPruebas").child(MacroCiclo.getID()).child(tipoPrueba).setValue(fechasDePrueba);
        }
    }

    private void pedirFechasdePrueba() {
        carga.iniciar();
        cambioFrag = false;
        fechasDePrueba = new ArrayList<String>();
        Query query = FirebaseDatabase.getInstance().getReference().child("FechasPruebas").child(MacroCiclo.getID()).child(tipoPrueba);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        fechasDePrueba.add(postSnapshot.getValue(String.class));
                    }
                    ordenarFechasPruebas();
                }
                cambioFrag = true;
                carga.detener();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public ArrayList<String> onCallBackFechasDePrueba(String fragmento) {
        if (fechasDePrueba.size() != 0){
            return  fechasDePrueba;
        }
        return null;
    }

    public void ordenarFechasPruebas() {
        if (fechasDePrueba.size()>1){
            sort(fechasDePrueba,0,(fechasDePrueba.size()-1));
        }
    }

    public void sort(ArrayList<String> fecha, int inicio, int size) {
        if(inicio < size){
            //Encuentra el punto medio del vector.
            int middle = (inicio + size) / 2;

            //Divide la primera y segunda mitad (llamada recursiva).
            sort(fecha, inicio, middle);
            sort(fecha, middle+1, size);

            //Une las mitades.
            merge(fecha, inicio, middle, size);
        }
    }

    public void merge(ArrayList<String> fecha, int inicio, int middle, int size) {
        //Encuentra el tamaño de los sub-vectores para unirlos.
        int n1 = middle - inicio + 1;
        int n2 = size - middle;

        //Vectores temporales.
        String[] leftArray = new String[n1];
        String[] rightArray = new String[n2];

        //Copia los datos a los arrays temporales.
        for (int i=0; i < n1; i++) {
            leftArray[i] = fecha.get(inicio+i);
        }
        for (int j=0; j < n2; j++) {
            rightArray[j] = fecha.get(middle + j + 1);
        }
        /* Une los vectorestemporales. */

        //Índices inicial del primer y segundo sub-vector.
        int i = 0;
        int j = 0;

        //Índice inicial del sub-vector arr[].
        int k = inicio;

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        String[] fecha1 = null;
        String[] fecha2 = null;

        //Ordenamiento.
        while (i < n1 && j < n2) {

            fecha1 = leftArray[i].split("-");
            fecha2 = rightArray[j].split("-");

            cal1.set(Integer.parseInt(fecha1[2]),Integer.parseInt(fecha1[1])-1,Integer.parseInt(fecha1[0]));
            cal2.set(Integer.parseInt(fecha2[2]),Integer.parseInt(fecha2[1])-1,Integer.parseInt(fecha2[0]));

            if (cal1.before(cal2)) {
                fecha.set(k,leftArray[i]);
                i++;
            } else {
                fecha.set(k,rightArray[j]);
                j++;
            }
            k++;
        }//Fin del while.

        /* Si quedan elementos por ordenar */
        //Copiar los elementos restantes de leftArray[].
        while (i < n1) {
            fecha.set(k,leftArray[i]);
            i++;
            k++;
        }
        //Copiar los elementos restantes de rightArray[].
        while (j < n2) {
            fecha.set(k,rightArray[j]);
            j++;
            k++;
        }
    }
}
