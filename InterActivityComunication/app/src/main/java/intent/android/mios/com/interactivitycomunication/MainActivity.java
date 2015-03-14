package intent.android.mios.com.interactivitycomunication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {

    Button toBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toBtn=(Button)findViewById(R.id.toBtn);
        toBtn.setOnClickListener(this);
    }

    /**
     * If we will need to adjust the menu during our activity's use such as disable a now-invalid menu choice,
     * just hold onto the Menu instance we receive in onCreateOptionsMenu(), which is called the first time
     * we push the option menu button.
     *
     * See more at:
     *  http://www.bogotobogo.com/Android/android10Menus.php#sthash.J763nQdq.dpuf
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Alternatively, we can implement onPrepareOptionsMenu(),
     * which is called just before displaying the menu each time it is requested.
     * See more at:
     *  http://www.bogotobogo.com/Android/android10Menus.php#sthash.J763nQdq.dpuf
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        return true;
    }

    /**
     * It's called when we select a menu option.
     *
     * @param item
     * @return
     */
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

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setClass(this, SecondActivity.class);
        intent.putExtra("EXTRA_ID", "SOME DATAS");
        startActivity(intent);
    }
}
