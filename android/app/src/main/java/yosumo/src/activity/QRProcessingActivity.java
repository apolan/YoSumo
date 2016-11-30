package yosumo.src.activity;

import android.content.DialogInterface;
import android.icu.text.DateFormat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import yosumo.src.R;
import yosumo.src.commons.Dummy;
import yosumo.src.db.ManagerDB;
import yosumo.src.logic.Comercio;
import yosumo.src.logic.Factura;

/**
 * 20161126 - DM - Clase encargada del procesamiento del codigo QR impreso en una factura
 */
public class QRProcessingActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler
{

    private ZXingScannerView mScannerView;

    private Factura factura;

    ManagerDB managerDB;

    TextView tv_NIT;
    TextView tv_VALOR;
    TextView tv_IMPUESTO;
    TextView tv_TAG;
    TextView tv_PORCENTAJE;
    TextView tv_NOMBRE;

    TextView textResult;

    String name;
    String nit ;
    String valor ;
    String impuesto ;
    String porcentaje ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrprocessing);

        textResult = (TextView) findViewById(R.id.textQRResult);
    }

    public void goQRProcessing(View v){
        mScannerView =  new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }


    public void onPause()
    {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result)
    {

        //Manejar el resultado :P
        Log.w("handle restult", result.getText());
        /*AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setTitle("Factura escaneada");
        */

        //Resume scanning
        //mScannerView.resumeCameraPreview(this);


        try {
            JSONObject jo = new JSONObject(result.getText());

            name = jo.getString("nombre");
            nit = jo.getString("nit");
            valor = jo.getString("valor");
            impuesto = jo.getString("impuesto");
            porcentaje = jo.getString("porcentaje");
            //fecha, add al qr y parse

            /*builder.setMessage("Impuesto de "+ name+" por valor de "+impuesto);
            AlertDialog alertDialog =  builder.create();
            alertDialog.show();*/

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("QR JSON error", "no se pudo parsear el QR");
        }

        initModuleEditFactura();

    }

    public String initModuleEditFactura() {
        setContentView(R.layout.activity_editfactura);
        tv_NOMBRE = (TextView) findViewById(R.id.edit_nombre);
        tv_NIT = (TextView) findViewById(R.id.edit_NIT);
        tv_VALOR = (TextView) findViewById(R.id.edit_Compra);
        tv_IMPUESTO = (TextView) findViewById(R.id.edit_Impuesto);
        tv_TAG = (TextView) findViewById(R.id.edit_Tag);
        tv_PORCENTAJE = (TextView) findViewById(R.id.edit_Porcentaje);

        try {
            tv_NOMBRE.setText(name);
            tv_NIT.setText(nit);
            tv_VALOR.setText(valor);
            tv_IMPUESTO.setText(impuesto);
            tv_PORCENTAJE.setText(porcentaje);
            tv_TAG.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return "";
    }

    //ZXing Zebra Crossing

    /**
     * Metodo que crea la factura en la base de datos
     *
     * @param view
     */
    public void goCreate(View view) {
        // Crea la factura en la base de datos

        managerDB = new ManagerDB(getApplicationContext());

        //cómo saco el usuario actual?

        int porcentaje = Integer.parseInt(tv_PORCENTAJE.getText()+"");
        int valor = Integer.parseInt(tv_VALOR.getText()+"");

        double valorImp = (valor * (porcentaje) / 100);
        Log.d("Valor impuemultip", valor + "");

        Date date = new Date();
        String sDate = Dummy.formatTimestamp(date);

        managerDB.insertFacturaQR( tv_NOMBRE.getText()+"", tv_NIT.getText()+"", sDate,
                                        valor, // VALOR DE FACTURA
                                        tv_IMPUESTO.getText()+"",                   // TIPO DE IMPUESTO
                                        porcentaje,
                                        valorImp, // VALOR DE IMPUESTO
                                        tv_TAG.getText()+""

                                        );



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Factura registrada")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        setContentView(R.layout.activity_imgprocessing);
                        //controladorProcesoFactura("NEXT");
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }




}
