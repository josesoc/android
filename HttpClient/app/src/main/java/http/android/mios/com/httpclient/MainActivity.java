package http.android.mios.com.httpclient;

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
import android.widget.TextView;
import com.google.gson.Gson;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import mios.http.library.HttpUtil;

public class MainActivity extends Activity implements View.OnClickListener, HttpUtil.RequestCallback {

        //UI
        private Button getBtn;
        private Button getNullBtn;
        private Button postBtn;
        private Button postJsonBtn;
        private Button uploadBtn;
        private TextView responseTxt;

        //Dynamic UI
        private ProgressDialog pd;

        private String urlBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        urlBase=getString(R.string.url);
        getBtn=(Button)findViewById(R.id.getBtn);
        getBtn.setOnClickListener(this);

        getNullBtn=(Button)findViewById(R.id.getNullBtn);
        getNullBtn.setOnClickListener(this);

        postBtn=(Button)findViewById(R.id.postBtn);
        postBtn.setOnClickListener(this);

        postJsonBtn=(Button)findViewById(R.id.postJsonBtn);
        postJsonBtn.setOnClickListener(this);

        uploadBtn=(Button)findViewById(R.id.uploadBtn);
        uploadBtn.setOnClickListener(this);

        responseTxt=(TextView)findViewById(R.id.responseTxt);
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

    @Override
    public void onClick(View view) {
        pd = ProgressDialog.show(this,
                                 "Actualizando Targets",
                                 "Espere, por favor ...",
                                 true,
                                 false);

        if (view == getBtn)
            get();

        if (view == postBtn)
            post();

        if (view == postJsonBtn)
            postJson();

        if (view == getNullBtn)
            getNull();

        if (view == uploadBtn)
            uploadFileAndParams();
    }

    private void get() {
        String url=urlBase+"/get/getUsuario.jsp";

        HttpUtil.httpGet(url, this);
    }

    private void getNull() {
        String url=urlBase+"/get/getUsuarioNull.jsp";

        HttpUtil.httpGet(url, this);
    }

    private void post() {
        String url=urlBase+"/post/postParametros.jsp";

        String email="pote@gmail.com";
        String usuarioid="23";
        String admin="false";

        Map<String, String> params=new HashMap<String, String>();
        params.put("email", email);
        params.put("usuarioid", usuarioid);
        params.put("admin", admin);

        HttpUtil.httpPost(url, params, this);
    }

    private void postJson() {
        String url=urlBase+"/post/postJSON.jsp";

        Gson gson=new Gson();
        Amigo amigo=new Amigo();
        amigo.setAmigoid(210L);

        HttpUtil.httpPost(url, gson.toJson(amigo), this);
    }

    private void uploadFileAndParams() {
        String url=urlBase+"/upload";

        String nombreArchivo=this.getString(R.string.uploadFile);
        File fichero=new File("/sdcard/"+nombreArchivo);

        //Parametros
        Map<String, String> parametros=new HashMap<String, String>();
        parametros.put("edad", "35");
        parametros.put("profesion", "terrateniente");

        InputStream fis=null;

        try
        {
            fis = new FileInputStream(fichero);
        }
        catch (FileNotFoundException e)
        {
            onError(e);
        }

        HttpUtil.httpPostMultiPart(url,
                fichero.getName(),
                fis,
                parametros,
                this);
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            pd.dismiss();

            if (msg != null)
                responseTxt.setText(msg.getData().getString("response"));
        }
    };

    @Override
    public void onError(Throwable exception) {
        Log.v("onError", exception.toString());

        handler.sendEmptyMessage(0);
    }

    @Override
    public void onResponseReceived(HttpResponse response) {
        Header header=response.getFirstHeader("Content-Type");
        Log.v("Response Contet-Type:", header.getValue());

        String sresponse=HttpUtil.getResponse(response);
        onResponseReceived(sresponse);
    }

    @Override
    public void onResponseReceived(String response) {
        if (response == null || response.length() == 0)
        {
            handler.sendEmptyMessage(0);
            return;
        }

        Bundle bundle=new Bundle();
        bundle.putString("response", response);
        Message msg=new Message();
        msg.setData(bundle);
        handler.sendMessage(msg);
    }
}
