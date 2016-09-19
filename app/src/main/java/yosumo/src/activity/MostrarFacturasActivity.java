package yosumo.src.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import java.util.ArrayList;
import yosumo.src.R;
import yosumo.src.db.ConstructorFacturas;
import yosumo.src.logic.Factura;

public class MostrarFacturasActivity extends Activity {

    ListView listView;
    private ArrayList<Factura> lista = new ArrayList<Factura>();
    private Factura usuario;
    private ConstructorFacturas constructorFacturass;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_facturas);

        LinearLayout ventana = (LinearLayout)findViewById(R.id.layout_usuarios);

        listView = (ListView)findViewById(R.id.lista_usuarios);

        constructorFacturass = new ConstructorFacturas(getApplicationContext());

        lista = constructorFacturass.obtenerDatos();

        ArrayAdapter<Factura> adap = new ArrayAdapter<Factura>(getApplicationContext(),android.R.layout.simple_list_item_1,lista);
        listView.setAdapter(adap);
    }



}
