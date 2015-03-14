package client.http.android.mios.com.httpclienttiny;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

public class MainActivity extends Activity implements View.OnClickListener, HttpUtil.RequestCallback {

    //UI
    private EditText urlEditTxt;
    private Button getBtn;
    private TextView getResponseTxt;

    //Dynamic UI
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlEditTxt=(EditText)findViewById(R.id.urlEditTxt);
        getBtn=(Button)findViewById(R.id.getBtn);
        getBtn.setOnClickListener(this);
        getResponseTxt=(TextView)findViewById(R.id.getResponseTxt);
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

    /**
    View.OnClickListener - implementation
     */
    @Override
    public void onClick(View view) {
        /*
        getResponseTxt.setText(urlEditTxt.getText());
        Toast.makeText(this, "onClick", Toast.LENGTH_SHORT).show();
        */

        pd = ProgressDialog.show(this,
                "Actualizando Targets",
                "Espere, por favor ...",
                true,
                false);

        get();
    }

    private void get() {
        String url=urlEditTxt.getText().toString();
        Log.v("get - url", url);
        HttpUtil.httpGet(url, this);
    }

    /**
     * HttpUtil.RequestCallback - implementation
     */
    @Override
    public void onError(Throwable exception) {
        Log.v("onError", exception.toString());
        handler.sendEmptyMessage(0);
    }

    @Override
    public void onResponseReceived(HttpResponse response) {
        Header header=response.getFirstHeader("Content-Type");
        Log.v("Response Content-Type:", header.getValue());

        String sresponse=HttpUtil.getResponse(response);
        onResponseReceived(sresponse);
    }

    @Override
    public void onResponseReceived(String response) {
         if (response == null ||
            response.length() == 0)
        {
            Log.v("onResponseReceived", "response null or empty");
            handler.sendEmptyMessage(0);
            return;
        }

        Log.v("onResponseReceived", response);
        Bundle bundle=new Bundle();
        bundle.putString("response", response);
        Message msg=new Message();
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    /**
     * Handle messages
     */
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            pd.dismiss();

            if (msg != null) {
                Log.v("handler.handleMessage:", msg.toString());
                getResponseTxt.setText(msg.getData().getString("response"));
            }
            else {
                Log.v("handler.handleMessage", "msg=null");
            }
        }
    };
}
