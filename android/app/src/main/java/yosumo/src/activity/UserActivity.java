package yosumo.src.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import yosumo.src.R;
import yosumo.src.db.ManagerDB;
import yosumo.src.logic.Usuario;

/**
 *
 */
public class UserActivity extends AppCompatActivity {
    TextView tv_usuario;
    TextView tv_Nombre;
    TextView tv_correo;
    TextView tv_password;
    //ConstructorUsuarios usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //todo validacion correo y de nombre
        tv_usuario = (TextView) findViewById(R.id.editText_usuario);
        tv_Nombre = (TextView) findViewById(R.id.editText_nombre);
        tv_correo = (TextView) findViewById(R.id.editText_mail);
        tv_password = (TextView) findViewById(R.id.editText_password);
    }

    /**
     * Crea usuarios
     * @param view
     */
    public void goCreate(View view){

        if(tv_usuario.getText().length()==0 || tv_Nombre.getText().length()==0 || tv_correo.getText().length()==0 || tv_password.getText().length()==0 ){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Por favor llena todos los Campos")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }else {
            ManagerDB db = new ManagerDB(getBaseContext());
            db.insertUsuario(new Usuario(tv_usuario.getText().toString(),
                            tv_Nombre.getText().toString(),
                            tv_correo.getText().toString(),
                            tv_password.getText().toString()
                    )
            );

            // Envia al main para que realice el ingreso
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
