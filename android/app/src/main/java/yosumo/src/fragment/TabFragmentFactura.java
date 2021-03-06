package yosumo.src.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import yosumo.src.R;
import yosumo.src.db.ManagerDB;
import yosumo.src.logic.Factura;

/**
 *
 */
public class TabFragmentFactura extends Fragment {

    ListView listView;
    private ArrayList<Factura> lista = new ArrayList<Factura>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_fragment_factura, container, false);
        listView = (ListView)rootView.findViewById(R.id.lst_facturas);

        ManagerDB db = new ManagerDB(rootView.getContext());
        lista = db.getAllFacturas();

        ArrayAdapter<Factura> adap = new ArrayAdapter<Factura>(rootView.getContext(), android.R.layout.simple_list_item_1, lista){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);
                TextView textView=(TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.BLACK);

                return view;
            }
        };
        listView.setAdapter(adap);
        return rootView;
    }



}