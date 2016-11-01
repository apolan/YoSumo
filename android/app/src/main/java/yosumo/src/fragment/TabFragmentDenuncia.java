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
import yosumo.src.logic.Denuncia;
import yosumo.src.logic.Factura;


public class TabFragmentDenuncia extends Fragment {

    ListView listView;
    private ArrayList<Denuncia> lista = new ArrayList<Denuncia>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fragment_denuncia, container, false);
        listView = (ListView)rootView.findViewById(R.id.lst_denuncias);

        ManagerDB db = new ManagerDB(rootView.getContext());
        lista = db.getAllDenuncias();

        ArrayAdapter<Denuncia> adap = new ArrayAdapter<Denuncia>(rootView.getContext(), android.R.layout.simple_list_item_1, lista){
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