package graphics.js.android.mios.com.androidjsgraphicsembedded;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * http://androidcookbook.com/Recipe.seam?recipeId=851&title=Android%20HTML5%20RGraph%20Charting
 *
 * https://github.com/IanDarwin/Android-Cookbook-Examples
 */
public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain reference to the WebView holder
        WebView webview = (WebView) this.findViewById(R.id.webview);

        // Get the settings
        WebSettings webSettings = webview.getSettings();

        // Enable Javascript for user interaction clicks
        webSettings.setJavaScriptEnabled(true);

        // Display Zoom Controles
        webSettings.setBuiltInZoomControls(true);
        webview.requestFocusFromTouch();

        // Set the client
        webview.setWebViewClient(new WebViewClient());
        webview.setWebChromeClient(new WebChromeClient());

        // Load the URL
        //webview.loadUrl("file:///android_asset/www/rgraphview.html");
        webview.loadUrl("file:///android_asset/www/mytest.html");
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
}
