package yosumo.src.activity;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import yosumo.src.R;
import yosumo.src.db.ManagerDB;
import yosumo.src.logic.Usuario;

/**
 *
 */
public class HomeActivity extends AppCompatActivity {

    private Usuario user;
    public String nombre;
    public String usuario;
    public int id;

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

        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_contador));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_factura));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_denuncias));

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
    }

    public Usuario getUser(String usario){
        ManagerDB db = new ManagerDB(getBaseContext());
        return db.getUserByUser(usario);
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

}
