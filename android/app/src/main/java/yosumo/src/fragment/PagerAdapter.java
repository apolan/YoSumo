package yosumo.src.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 *  MOD 20161029 - AFP - Adicion  tab debuug
 *  MOD 20161029 - AFP - Adicion  tab facebook
 *  MOD 20161124 - AFP - Adicion  tab vis
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragmentContador tab0 = new TabFragmentContador();
                return tab0;
            case 1:
                TabFragmentFactura tab1 = new TabFragmentFactura();
                return tab1;
            case 2:
                TabFragmentDenuncia tab2 = new TabFragmentDenuncia();
                return tab2;
            case 3:
                TabFragmentDebug tab3 = new TabFragmentDebug();
                return tab3;
            case 4:
                TabFragmentVis tab4 = new TabFragmentVis();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
