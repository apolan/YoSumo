package yosumo.src.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import yosumo.src.R;
import yosumo.src.activity.HomeActivity;
import yosumo.src.db.ManagerDB;
import yosumo.src.logic.Factura;

/**
 *
 */
public class TabFragmentDebug extends Fragment {

    ManagerDB db;
    Context cnt;

    ListView listView;
    private ArrayList<Factura> lista = new ArrayList<Factura>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_fragment_debug, container, false);
        //listView = (ListView)rootView.findViewById(R.id.lst_facturas);
     //    db = new ManagerDB(rootView.getContext());
     //   cnt = rootView.getContext();
        //ManagerDB db = new ManagerDB(rootView.getContext());
       // lista = db.getAllFacturas();

//        ArrayAdapter<Factura> adap = new ArrayAdapter<Factura>(rootView.getContext(), android.R.layout.simple_list_item_1, lista);
  //      listView.setAdapter(adap);

        return rootView;
    }



}