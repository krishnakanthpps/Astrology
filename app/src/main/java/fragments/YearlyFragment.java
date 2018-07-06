package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anagha.astrology.R;

public class YearlyFragment extends Fragment {
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //reference the xml file containing the view with all the code here.
        return rootView=inflater.inflate(R.layout.horoscopeview, container, false);
    }
}
