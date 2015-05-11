package com.mycompany.detectconnections;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void opcion1() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mMobile = connManager .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        showInfo(mWifi, mMobile);
    }

    private void opcion2() {
        //Recogemos el servicio ConnectivityManager
        //el cual se encarga de todas las conexiones del terminal
        ConnectivityManager conMan = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        //Recogemos el estado del 3G
        //como vemos se recoge con el parámetro 0
        NetworkInfo.State internet_movil = conMan.getNetworkInfo(0).getState();

        //Recogemos el estado del wifi
        //En este caso se recoge con el parámetro 1
        NetworkInfo.State wifi = conMan.getNetworkInfo(1).getState();

        showInfo( (wifi == NetworkInfo.State.CONNECTED
                   || wifi == NetworkInfo.State.CONNECTING),
                  (internet_movil == NetworkInfo.State.CONNECTED
                   || internet_movil == NetworkInfo.State.CONNECTING));
    }

    private void opcion3() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean is3g = manager.getNetworkInfo(
            ConnectivityManager.TYPE_MOBILE)
                        .isConnectedOrConnecting();
        boolean isWifi = manager.getNetworkInfo(
                        ConnectivityManager.TYPE_WIFI)
                        .isConnectedOrConnecting();

        showInfo(isWifi, is3g);
    }

    private void showInfo(NetworkInfo mWifi, NetworkInfo mMobile) {
        if (!mWifi.isConnected()){
            Toast.makeText(getApplicationContext(),
                    "No se detecta conexión Wifi",
                    Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(getApplicationContext(),
                "Conexión Wifi detectada",
                Toast.LENGTH_LONG).show();

        if (!mMobile.isConnected()) {
            Toast.makeText(getApplicationContext(),
                    "No se detecta conexión de datos 3G/4G",
                    Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(getApplicationContext(),
                "Conexión de datos 3G/4G detectada",
                Toast.LENGTH_LONG).show();
    }

    private void showInfo(boolean mWifi, boolean mMobile) {
        if (!mWifi){
            Toast.makeText(getApplicationContext(),
                    "No se detecta conexión Wifi",
                    Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(getApplicationContext(),
                "Conexión Wifi detectada",
                Toast.LENGTH_LONG).show();

        if (!mMobile) {
            Toast.makeText(getApplicationContext(),
                    "No se detecta conexión de datos 3G/4G",
                    Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(getApplicationContext(),
                "Conexión de datos 3G/4G detectada",
                Toast.LENGTH_LONG).show();
    }

    public void checkingConnections1(View view) {
        Toast.makeText(this, "Checking connections (opcion1) ...", Toast.LENGTH_LONG).show();
        opcion1();
    }
    public void checkingConnections2(View view) {
        Toast.makeText(this, "Checking connections (opcion2) ...", Toast.LENGTH_LONG).show();
        opcion2();
    }
    public void checkingConnections3(View view) {
        Toast.makeText(this, "Checking connections (opcion3) ...", Toast.LENGTH_LONG).show();
        opcion3();
    }
}


