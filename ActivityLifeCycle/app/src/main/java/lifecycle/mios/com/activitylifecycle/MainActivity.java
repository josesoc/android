package lifecycle.mios.com.activitylifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
            Log.v("onOptionsItemSelected", "Menu.itemId: "+item.getItemId());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Activity lifecycle
     * Init Application: onStart, onResume
     * Change Application: onPause, onStop, onDestroy
     */

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("onStart", "starting activity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("onRestart", "restarting activity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("onResume", "Resuming activity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("onPause", "Pausing activity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("onStop", "stoping activity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("onDestroy", "destroying activity");
    }
}
