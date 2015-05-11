package com.mycompany.waitinganimation;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //*** Throw Exception:
        //      requestFeature() must be called before adding content
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_main);

        new Waiting(this).execute();
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

    private class Waiting extends AsyncTask<Void, Void, Void> {

        private Activity parent;

        public Waiting(Activity parent) {
            this.parent=parent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //setProgressBarIndeterminateVisibility(true);
            parent.findViewById(R.id.text_msg).setVisibility(View.GONE);
            parent.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            for(long i=0; i<10000000; i++);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //setProgressBarIndeterminateVisibility(true);

            parent.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            parent.findViewById(R.id.text_msg).setVisibility(View.VISIBLE);

            Toast.makeText(parent, "Tarea finalizada !", Toast.LENGTH_LONG).show();
        }
    }
}
