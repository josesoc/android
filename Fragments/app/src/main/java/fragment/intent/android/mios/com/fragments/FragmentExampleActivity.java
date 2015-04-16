package fragment.intent.android.mios.com.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * http://www.techotopia.com/index.php/Using_Fragments_in_Android_-_A_Worked_Example
 */
public class FragmentExampleActivity extends Activity implements ToolbarFragment.ToolbarListener {

    /**
     * ToolbarFragment.ToolbarListener implementation
     *
     * @param fontsize
     * @param text
     */
    @Override
    public void onButtonClick(int fontsize, String text) {
        Toast.makeText(this, "onButtonClick", Toast.LENGTH_SHORT).show();

        TextFragment textFragment = (TextFragment)getFragmentManager().findFragmentById(R.id.text_fragment);
        Log.v("onButtonClick", ""+textFragment.getActivity().getTitle());

        textFragment.changeTextProperties(fontsize, text);
    }

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
            Log.v("onOptionsItemSelected", "R.id.action_settings option menu clicked: "+item.getItemId());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
