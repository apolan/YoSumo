package yosumo.src.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import yosumo.src.R;
import yosumo.src.db.ConstructorUsuarios;
import yosumo.src.logic.Usuario;

public class UserActivity extends AppCompatActivity {
    TextView tv_Nombre;
    TextView tv_correo;
    TextView tv_password;
    ConstructorUsuarios usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        tv_Nombre = (TextView) findViewById(R.id.edit_NIT);
        tv_correo = (TextView) findViewById(R.id.edit_Compra);
        tv_password = (TextView) findViewById(R.id.edit_Impuesto);

    }




    /**
     * Crea usuarios
     * @param view
     */
    public void goCreate(View view){
        Log.d("usuario","algo");

        Usuario usuario =new Usuario(tv_Nombre.getText()+ "",
                                     tv_correo.getText()+ "",
                                     tv_password.getText()+ "");

        usuarios = new ConstructorUsuarios(getBaseContext());
        usuarios.registrarUsuario(tv_Nombre.getText()+ "",  tv_correo.getText()+ "",tv_password.getText()+ "");


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Look at this dialog!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
