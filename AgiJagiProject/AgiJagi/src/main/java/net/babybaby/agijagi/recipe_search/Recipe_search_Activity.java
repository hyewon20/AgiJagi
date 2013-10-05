package net.babybaby.agijagi.recipe_search;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.babybaby.agijagi.R;

/**
 * Created by FlaShilver on 2013. 10. 4..
 */
public class Recipe_search_Activity extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView tv = (TextView) rootView.findViewById(R.id.textview);
        tv.setText("레시피 검색");
        return rootView;
    }
}
