package yosumo.src.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
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


import yosumo.src.R;
import yosumo.src.db.ManagerDB;
import yosumo.src.logic.Usuario;

/**
 * Clase home de la aplicación: Contiene los tabs (fragments)
 * MOD 20161030 - AFP - Adicion conexion servidor
 * TASK asincronica para abrir socket
 */
public class HomeActivity extends AppCompatActivity {

    private Usuario user;
    public String nombre;
    public String usuario;
    public int id;

    private String nm_comercio = "";

    TextView txtUser;
    ImageView imageUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.Bar_Black));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        // Se modifican add tabs
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_contador));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_factura));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_denuncias));
        //tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_debug));
       // tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_facebook));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new yosumo.src.fragment.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Intent myIntent = getIntent(); // gets the previously created intent

        usuario = myIntent.getStringExtra("usuario");

        imageUser = (ImageView) findViewById(R.id.img_user);
        txtUser = (TextView) findViewById(R.id.txtview_user);
        user = getUser(usuario);

        txtUser.setText(user.getNombre());

        // AFP -  20161029 -  I | TASK: Conexion cliente socket
        connectAndPush();
        // AFP -  20161029 -  F
    }

    /**
     * @param usario
     * @return
     */
    public Usuario getUser(String usario) {
        ManagerDB db = new ManagerDB(getBaseContext());
        if (db.getUserByUser(usario) != null) {
            return db.getUserByUser(usario);
        }

        return new Usuario("apolan", "Andres", "apolan89@gmail.com", "123");

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
     * @param v
     */
    public void goCreateDenuncia(View v) {

        // AlertDialog.Builder alert = new AlertDialog.Builder(this);
        AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogOrange));

        alert.setTitle("Nueva denuncia");
        // alert.setMessage("Message");

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

        final EditText direccion = new EditText(this);
        final TextView lbl_direccion = new TextView(this);
        lbl_direccion.setTextColor(Color.BLACK);
        lbl_direccion.setText("Dirección:");
        direccion.setInputType(InputType.TYPE_CLASS_TEXT);
        direccion.setTextColor(Color.BLACK);


        layout_2.addView(lbl_direccion);
        layout_2.addView(direccion);

        final EditText comentario = new EditText(this);
        final TextView lbl_comentario = new TextView(this);
        lbl_comentario.setTextColor(Color.BLACK);
        lbl_comentario.setText("Comentario:");
        comentario.setInputType(InputType.TYPE_CLASS_TEXT);
        comentario.setTextColor(Color.BLACK);


        layout_3.addView(lbl_comentario);
        layout_3.addView(comentario);

        layout.addView(layout_1);
        layout.addView(layout_2);
        layout.addView(layout_3);

        alert.setView(layout);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                nm_comercio = lugar.getText().toString();
                nm_comercio = direccion.getText().toString();
                nm_comercio = comentario.getText().toString();
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
    public void connectAndPush() {
        Log.d("Connect and push", " ");
        // Pide actualizar comercios
        Client myClient = new Client(this.getResources().getString(R.string.ipServerSocket),
                Integer.parseInt(this.getResources().getString(R.string.portSocket)),
                "update:comercio",
                user,
                new ManagerDB(getApplicationContext())
        );
        myClient.execute();
    }
    // AFP -  20161029 -  F
}
