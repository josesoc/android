package com.spring.resttemplateclient;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class MainActivity extends Activity {

    Activity me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        me=this;
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
        if (id == R.id.action_refresh) {
            new HttpRequestTask().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }


    /**
     * AsyncTask<Void, Void, RoleDto>
     * 1 - El tipo de datos que recibiremos como entrada de la tarea en el método doInBackground().

       2 - El tipo de datos con el que actualizaremos el progreso de la tarea, y que recibiremos como parámetro
           del método onProgressUpdate() y que a su vez tendremos que incluir como parámetro del método publishProgress().

       3 - El tipo de datos que devolveremos como resultado de nuestra tarea, que será el tipo de retorno del
           método doInBackground() y el tipo del parámetro recibido en el método onPostExecute().

     */
    private class HttpRequestTask extends AsyncTask<Void, Void, RoleDto> {

        @Override
        protected RoleDto doInBackground(Void... params) {
            try {

                final String url = getResources().getString(R.string.roles_uri);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                RoleDto role = restTemplate.getForObject(url, RoleDto.class);
                return role;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(RoleDto role) {
            if (role == null)
            {
                Toast.makeText(me, "Role not found", Toast.LENGTH_SHORT).show();
                return;
            }
            TextView greetingIdText = (TextView) findViewById(R.id.id_value);
            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
            greetingIdText.setText(""+role.getId());
            greetingContentText.setText(role.getName());
        }

    }
}
