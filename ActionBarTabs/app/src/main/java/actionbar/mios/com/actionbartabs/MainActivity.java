package actionbar.mios.com.actionbartabs;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtenemos una referencia a la actionbar
        ActionBar abar = getActionBar();

        //Podemos cambiar la imagen que muestra el ActionBar para que no sea la misma
        //que el logo de la aplicación (por defecto)
        //abar.setIcon(R.drawable.ic_launcher);

        /*
          Activa el botón: Up navigation button (is not back button)
            Up button allows the user to navigate your app based on the hierarchical
            relationships between screens.
        */
        //abar.setDisplayHomeAsUpEnabled(true);

        //Establecemos el modo de navegación por pestañas
        abar.setNavigationMode(
                ActionBar.NAVIGATION_MODE_TABS);

        //Ocultamos el título de la actividad
        //abar.setDisplayShowTitleEnabled(false);

        //Creamos las pestañas
        ActionBar.Tab tab1 =
                abar.newTab().setText("Tab 1");

        ActionBar.Tab tab2 =
                abar.newTab().setText("Tab 2");

        ActionBar.Tab tab3 =
                abar.newTab().setText("Tab 3");

        //Creamos los fragments de cada pestaña
        Fragment tab1frag = new Tab1Fragment();
        Fragment tab2frag = new Tab2Fragment();
        Fragment tab3frag = new Tab3Fragment();

        //Asociamos los listener a las pestañas
        tab1.setTabListener(new MiTabListener(tab1frag));
        tab2.setTabListener(new MiTabListener(tab2frag));
        tab3.setTabListener(new MiTabListener(tab3frag));

        //Añadimos las pestañas a la action bar
        abar.addTab(tab1);
        abar.addTab(tab2);
        abar.addTab(tab3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new:
                Log.i("ActionBar", "Nuevo!");
                return true;
            case R.id.menu_save:
                Log.i("ActionBar", "Guardar!");;
                return true;
            case R.id.menu_settings:
                Log.i("ActionBar", "Settings!");;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
