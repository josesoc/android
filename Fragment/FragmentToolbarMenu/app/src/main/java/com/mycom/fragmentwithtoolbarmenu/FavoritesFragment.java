package com.mycom.fragmentwithtoolbarmenu;

/**
 * Created by usuario on 04/05/2015.
 */
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class FavoritesFragment extends Fragment {

    private TextView detail;
    private Map<String, String> sharedData;

    public FavoritesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.favorites_fragment, container, false);

        //String menu = getArguments().getString("menu_arg");
        //List Adapter
        detail = (TextView)view.findViewById(R.id.fav_detail);
        detail.setText("Favorites Fragment");

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
        inflater.inflate(R.menu.menu_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            //Toast.makeText(getActivity(), "Action Share", Toast.LENGTH_SHORT).show();
            makeSharedData();
            share();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void makeSharedData() {
        if (sharedData == null)
            sharedData=new HashMap<String, String>();

        sharedData.put("EXTRA_SUBJECT", "temita1");
        sharedData.put("EXTRA_TEXT", "datos sobre temita1");
    }

    public void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, sharedData.get("EXTRA_SUBJECT"));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, sharedData.get("EXTRA_TEXT"));
        startActivity(Intent.createChooser(sharingIntent, "Compartir contenido"));
    }
}
