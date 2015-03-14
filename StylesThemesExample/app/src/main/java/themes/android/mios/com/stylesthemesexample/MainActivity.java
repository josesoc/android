package themes.android.mios.com.stylesthemesexample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


/**
 * http://mrbool.com/how-to-change-the-layout-theme-of-an-android-application/25837
 *
 * http://developer.xamarin.com/recipes/android/resources/general/style_a_button/
 *
 * Menus: http://www.sgoliver.net/blog/action-bar-en-android-i/
 */
public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Util.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);

        findViewById(R.id.androideImg).setOnClickListener(this);
    }
    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        switch (v.getId())
        {
            case R.id.button1:
                Util.changeToTheme(this, Util.THEME_DEFAULT);
                break;
            case R.id.button2:
                Util.changeToTheme(this, Util.THEME_WHITE);
                break;
            case R.id.button3:
                Util.changeToTheme(this, Util.THEME_BLUE);
                break;
            case R.id.androideImg:
                Util.changeToTheme(this, Util.THEME_AQUA);
                break;
        }
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

        Log.v("onOptionsItemSelected", "id: "+id);

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */
        return super.onOptionsItemSelected(item);
    }

}
