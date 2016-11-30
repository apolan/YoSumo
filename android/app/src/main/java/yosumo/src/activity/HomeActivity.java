package yosumo.src.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.util.List;

import yosumo.src.R;
import yosumo.src.db.ManagerDB;
import yosumo.src.logic.Denuncia;
import yosumo.src.logic.Usuario;
import yosumo.src.sensor.GPSTracker;
import yosumo.src.sensor.MapsActivity;

/**
 * Clase home de la aplicación: Contiene los tabs (fragments)
 * MOD 20161030 - AFP - Adicion conexion servidor
 * TASK asincronica para abrir socket
 */
public class HomeActivity extends AppCompatActivity implements LocationListener, SensorEventListener {

    private Usuario user;
    public String nombre;
    public String usuario;
    public int id;

    // AFP -  20161030 -  I | TASK: GPSTRACKER
    LocationManager locationmanager;
    double latitude;
    double longitude;
    private String nm_comercio = "";
    private String nm_comentario = "";
    GPSTracker gps;
    // AFP -  20161030 -  F

    // AFP -  20161127 -  I | TASK: Light sensor
    SensorEventListener lightSensorEventListener;
    Sensor lightSensor;
    int lightLow = 10;
    int lightHigh = 60;

    float currentlight;

    // AFP -  20161030 -  I | TASK: SOCKETPORT AND IP
    String SOCKET_IP;
    String SOCKET_PORT;
    // AFP -  20161030 -  F

    Profile profile;
    TextView txtUser;
    ImageView imageUser;
    ProfilePictureView profilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initGUI("WINDOW");
        initGUI("TABS");
        initGUI("FACEBOOK");
        initGUI("USER");

        // AFP -  20161030 -  I | TASK: GPSTRACKER
        initSensor("GPS");
        //initSensor("LIGHT");
        initSensor("SERVER");
        // AFP -  20161030 -  F

    }

    public void initGUI(String tag) {
        if (tag.equalsIgnoreCase("TABS")) {
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            // Se modifican add tabs
            tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_contador));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_factura));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_denuncias));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_debug));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_vis));

            final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            final PagerAdapter adapter = new yosumo.src.fragment.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                   //Log.d("Tag tab", (String)tab.getTag());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });

        } else if (tag.equalsIgnoreCase("WINDOW")) {

            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Bar_Black));

        } else if (tag.equalsIgnoreCase("FACEBOOK")) {
            profile = Profile.getCurrentProfile();
            Log.d("Profie ", " " +profile.getName());
        } else if (tag.equalsIgnoreCase("USER")) {
            //Intent myIntent = getIntent(); // gets the previously created intent
            //usuario = myIntent.getStringExtra("usuario");

            txtUser = (TextView) findViewById(R.id.txtview_user);
            txtUser.setText(profile.getName());
            profilePic = (ProfilePictureView) findViewById(R.id.profile_picture);
            profilePic.setProfileId(profile.getId());

            ManagerDB db = new ManagerDB(getBaseContext());
            if (db.getUserById(profile.getId()) != null) {
                //user = db.getUserByUser(profile.getId());
            }
            user = new Usuario("apolan", "Andres", "apolan89@gmail.com", "123");

        } else {
            Log.e("Not find tag", " ");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    /**
     * @param v
     */
    public void goImgProcessing(View v) {
        Intent intent = new Intent(this, ImgProcessingActivity.class);
        startActivity(intent);
    }

    /**
     * Realiza el intent de la app de mapas
     * @param v
     */
    public void goCreateDenunciaMapa(View v){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    /**
     * @param v
     */
    public void goCreateDenuncia(View v) {

        gps = new GPSTracker(HomeActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            Log.d("coordeantes: ", latitude + ":" + longitude);
        } else {
            gps.showSettingsAlert();
        }

        // AlertDialog.Builder alert = new AlertDialog.Builder(this);
        AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogOrange));

        alert.setTitle("Nueva denuncia");
        Context context = this.getApplicationContext();
        // Este el grande

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(25, 0, 0, 0);

        LinearLayout layout_1 = new LinearLayout(context);
        layout_1.setOrientation(LinearLayout.HORIZONTAL);
        layout_1.setLayoutParams(lp);

        LinearLayout layout_2 = new LinearLayout(context);
        layout_2.setOrientation(LinearLayout.HORIZONTAL);
        layout_2.setLayoutParams(lp);

        LinearLayout layout_3 = new LinearLayout(context);
        layout_3.setOrientation(LinearLayout.HORIZONTAL);
        layout_3.setLayoutParams(lp);

        final EditText lugar = new EditText(this);
        final TextView lbl_lugar = new TextView(this);
        lbl_lugar.setTextColor(Color.BLACK);
        lbl_lugar.setText("Nombre Lugar:");
        lugar.setInputType(InputType.TYPE_CLASS_TEXT);
        lugar.setTextColor(Color.BLACK);

        layout_1.addView(lbl_lugar);
        layout_1.addView(lugar);

        final TextView lbl_latitud = new TextView(this);
        final TextView lbl_longitud = new TextView(this);
        lbl_latitud.setTextColor(Color.BLACK);
        lbl_longitud.setTextColor(Color.BLACK);
        lbl_latitud.setText("Latitud: " + latitude);
        lbl_longitud.setText("Longitud: " + longitude);

        LinearLayout layout_LA = new LinearLayout(context);
        layout_LA.setOrientation(LinearLayout.HORIZONTAL);
        layout_LA.setLayoutParams(lp);

        LinearLayout layout_LO = new LinearLayout(context);
        layout_LO.setOrientation(LinearLayout.HORIZONTAL);
        layout_LO.setLayoutParams(lp);

        layout_LA.addView(lbl_latitud);
        layout_LO.addView(lbl_longitud);

        final EditText comentario = new EditText(this);
        final TextView lbl_comentario = new TextView(this);
        lbl_comentario.setTextColor(Color.BLACK);
        lbl_comentario.setText("Comentario:");
        comentario.setInputType(InputType.TYPE_CLASS_TEXT);
        comentario.setTextColor(Color.BLACK);

        layout_3.addView(lbl_comentario);
        layout_3.addView(comentario);

        layout.addView(layout_LA);
        layout.addView(layout_LO);
        layout.addView(layout_1);
        layout.addView(layout_3);
        layout.setLayoutParams(lp);

        alert.setView(layout);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                nm_comercio = lugar.getText().toString();
                //nm_comercio = direccion.getText().toString();
                nm_comentario = comentario.getText().toString();
                ManagerDB db = new ManagerDB(getApplicationContext());
                db.insertDenuncia(new Denuncia(user.getUsuario(), nm_comercio, nm_comentario, latitude, longitude));
                Toast.makeText(getApplicationContext(), "Se ha creado la denuncia", Toast.LENGTH_LONG).show();
                connectAndPush("denuncia");
            }
        });

        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

        alert.show();
    }


    // AFP -  20161029 -  I | TASK: Conexion cliente socket

    /**
     * Conecta el cliente socket
     */
    public void connectAndPull(String tag) {
        Log.d("Connect and pull :tag ", tag);


        if (tag.equalsIgnoreCase("comercio")) {
            tag = "update:comercio";
        } else if (tag.equalsIgnoreCase("denuncia")) {
            tag = "update:denuncia";
        } else if (tag.equalsIgnoreCase("factura")) {
            tag = "update:factura";
        } else if (tag.equalsIgnoreCase("impuesto")) {
            tag = "update:impuesto";
        } else {
            tag = "Not find " + tag;
            Log.d("Not find ", " ");
            return;
        }

        ClientSocket myClientSocket = new ClientSocket(SOCKET_IP, Integer.parseInt(SOCKET_PORT), tag, user, new ManagerDB(getApplicationContext()));
        myClientSocket.execute();
    }

    /**
     * Conecta el cliente socket
     */
    public void connectAndPush(String tag) {
        Log.d("Connect and push tag: ", tag);
        ManagerDB db = new ManagerDB(getApplicationContext());

        if (tag.equalsIgnoreCase("denuncia")) {
            tag = "public:denuncia";
        } else {
            tag = "Not find " + tag;
            Log.d("Not find ", " ");
            return;
        }

        try {
            for (Denuncia denuncia : db.getAllDenuncias()) { // Por cada denuncia en estado pendiente
                if (denuncia.getEstado().equalsIgnoreCase("pendiente")) {
                    ClientSocket myClientSocket = new ClientSocket(SOCKET_IP, Integer.parseInt(SOCKET_PORT), tag, user, db);
                    myClientSocket.setDenuncia(denuncia.toSocket());
                    myClientSocket.execute();
                }
            }


        } catch (Exception a) {
            System.out.println("Error: " + a.getMessage());
        } finally {
        }
    }

    /**
     * @param v
     */
    public void pushDenuncia(View v) {
        //connectAndPull("comercio");
        connectAndPull("denuncia");
        connectAndPush("denuncia");
        ManagerDB db = new ManagerDB(getApplicationContext());
        db.updateDenuncia(); // Pasa las denuncias a estado enviado

    }

    /**
     * @param v
     */
    public void pushFactura(View v) {
        connectAndPull("comercio");
        connectAndPull("factura");
        connectAndPull("impuesto");

        //ManagerDB db = new ManagerDB(getApplicationContext());
        //db.updateDenuncia();
        // TabFragmentFactura fragment = (TabFragmentFactura) getFragmentManager().findFragmentById(R.id.example_fragment);
        // fragment.<specific_function_name>();
    }


    public void setPortAndIp(View v) {

        AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogOrange));

        alert.setTitle("[Servidor] Ingrese el puerto y su IP");
        Context context = this.getApplicationContext();
        // Este el grande

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(25, 0, 0, 0);

        LinearLayout layout_1 = new LinearLayout(context);
        layout_1.setOrientation(LinearLayout.HORIZONTAL);
        layout_1.setLayoutParams(lp);

        LinearLayout layout_2 = new LinearLayout(context);
        layout_2.setOrientation(LinearLayout.HORIZONTAL);
        layout_2.setLayoutParams(lp);

        final EditText IP = new EditText(this);
        IP.setText(SOCKET_IP);
        final TextView lbl_lugar = new TextView(this);
        lbl_lugar.setTextColor(Color.BLACK);
        lbl_lugar.setText("IP:");
        IP.setInputType(InputType.TYPE_CLASS_TEXT);
        IP.setTextColor(Color.BLACK);

        layout_1.addView(lbl_lugar);
        layout_1.addView(IP);

        final EditText PORT = new EditText(this);
        PORT.setText(SOCKET_PORT);
        final TextView lbl_comentario = new TextView(this);
        lbl_comentario.setTextColor(Color.BLACK);
        lbl_comentario.setText("PORT:");
        PORT.setInputType(InputType.TYPE_CLASS_TEXT);
        PORT.setTextColor(Color.BLACK);

        layout_2.addView(lbl_comentario);
        layout_2.addView(PORT);

        layout.addView(layout_1);
        layout.addView(layout_2);
        alert.setView(layout);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                SOCKET_IP = IP.getText().toString();
                SOCKET_PORT = PORT.getText().toString();
                Toast.makeText(getApplicationContext(), "Se ha actualizado la inforamcion", Toast.LENGTH_LONG).show();
            }
        });

        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

        alert.show();
    }
    // AFP -  20161029 -  F

    // AFP -  20161030 -  I | TASK: GPS TRACKER
    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Log.d("GPS CHANGED", latitude + "|" + longitude);
    }


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }
    // AFP -  20161030 -  F

    // AFP -  20161101 -  I | TASK: debug

    /**
     * @param v
     */
    public void dropTableDenuncia(View v) {
        ManagerDB db = new ManagerDB(getApplicationContext());
        db.dropTable("denuncia");
        Toast.makeText(getApplicationContext(), "Se ha hecho drop de la tabla", Toast.LENGTH_LONG).show();
    }

    /**
     * @param v
     */
    public void dropTableFactura(View v) {
        ManagerDB db = new ManagerDB(getApplicationContext());
        db.dropTable("factura");
        db.deleteTable("factura");
        Toast.makeText(getApplicationContext(), "Se ha hecho drop de la factura", Toast.LENGTH_LONG).show();
    }

    /**
     * @param v
     */
    public void dropTableImpuesto(View v) {
        ManagerDB db = new ManagerDB(getApplicationContext());
        db.dropTable("impuesto");
        Toast.makeText(getApplicationContext(), "Se ha hecho drop de la tabla", Toast.LENGTH_LONG).show();
    }
    // AFP -  20161101 -  F


    // AFP -  20161127 -  I | TASK: INIT SENSOR

    /**
     * Inicializa los sensores
     *
     * @param sensor tipo de sensor que se quiere inicializar
     */
    public void initSensor(String sensor) {
        if (sensor.equalsIgnoreCase("GPS")) {
            Criteria cri = new Criteria();
            locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            String provider = locationmanager.getBestProvider(cri, false);

            if (provider != null & !provider.equals("")) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Location location = locationmanager.getLastKnownLocation(provider);
                locationmanager.requestLocationUpdates(provider, 2000, 0.2f, this);
                if (location != null) {
                    onLocationChanged(location);
                }
            }
        } else if (sensor.equalsIgnoreCase("LIGHT")) {
            SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else if (sensor.equalsIgnoreCase("SERVER")) {
            SOCKET_PORT = this.getResources().getString(R.string.portSocket);
            SOCKET_IP = this.getResources().getString(R.string.ipServerSocket);
        } else {
            Log.d("Not find sensor", sensor);
        }
    }


    public void getLightSensor(View v) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getBaseContext());
        builder1.setMessage("Valores de luz. " + currentlight);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        Log.d("evento sensor s", "" + event.sensor.getType());

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            String modo = "";
            currentlight = event.values[0];
            //Log.d("Cambio la luz: ", "" + currentlight);
            if (currentlight < lightLow) { // Light: low
                modo = "low";
            } else if (currentlight >= lightLow && currentlight < lightHigh) { // Light: Medium
                modo = "medium";
            } else if (currentlight > lightHigh) { // Light: High
                modo = "high";
            }
          //  Fragment currentFragment = getActivity().getFragmentManager().findFragmentById(R.id.fr);
            //Log.d("Modo: ", modo);
            // changeModeLight(modo)
        }
    }
    // AFP -  20161127 -  F



    public void shareit(){
        CallbackManager callbackManager;
        ShareDialog shareDialog;
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
//        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() { ... });


        Bitmap image = null;
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
    }




    public void goQRProcessing (View v) {
        Intent intent = new Intent(this, QRProcessingActivity.class);
        startActivity(intent);
    }
}
