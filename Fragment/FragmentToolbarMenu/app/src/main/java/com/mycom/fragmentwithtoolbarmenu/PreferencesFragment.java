package com.mycom.fragmentwithtoolbarmenu;

/**
 * Created by usuario on 04/05/2015.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PreferencesFragment extends Fragment {

    private TextView detail;

    public PreferencesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.preferences_fragment, container, false);

        //String menu = getArguments().getString("menu_arg");
        //List Adapter
        detail = (TextView)view.findViewById(R.id.pref_detail);
        detail.setText("Preferences Fragment");

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //Oculta la opcion "0" del menu de la Activity
        //menu.getItem(0).setVisible(false);

        //Agrega las opciones de menu al menu de la Activity
        inflater.inflate(R.menu.menu_preferences, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send) {
            Toast.makeText(getActivity(), "Action Send", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
