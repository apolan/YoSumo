package yosumo.src.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import yosumo.src.R;
import yosumo.src.db.ManagerDB;
import yosumo.src.logic.Factura;

/**
 *
 */
public class TabFragmentVis extends Fragment {

    private WebView webview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_fragment_vis, container, false);
        webview = (WebView) rootView.findViewById(R.id.webviewID);
        initPieChart();
        return rootView;
    }

    public void initPieChart() {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(
                    WebView view,
                    String url) {
                loadPieChart();
            }
        });
        webview.loadUrl("file:///android_asset/html/piechart.html");
    }

    public void loadPieChart() {
        int dataset[] = new int[]{5, 10, 15, 20, 35};
        String text = Arrays.toString(dataset);

        // pass the array to the JavaScript function
        webview.loadUrl("javascript:loadPieChart(" + text + ")");
    }

}