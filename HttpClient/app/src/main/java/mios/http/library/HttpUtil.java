package mios.http.library;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

import com.google.gson.Gson;

import android.util.Log;

/**
 * Created by usuario on 02/03/2015.
 */
public class HttpUtil {
    private static final String CONTENT_TYPE_JSON="application/json";

    private static final String lineEnd="\r\n";
    private static final String twoHyphens="--";
    private static final String boundary="*****";

    public interface RequestCallback {
        public void onError(Throwable exception);
        public void onResponseReceived(HttpResponse response);
        public void onResponseReceived(String response);
    }

    public static void httpPost(final String url,
                                final String json,
                                final RequestCallback callback)
    {
        Log.i("doPost a url: " + url, "- JSON: " + json);

        final HttpClient httpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);

        Thread thread = new Thread()
        {
            public void run()
            {
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("Accept", CONTENT_TYPE_JSON);
                httpPost.addHeader("Content-Type", CONTENT_TYPE_JSON);
                try
                {
                    StringEntity entity = new StringEntity(json,
                                                           HTTP.UTF_8);

                    entity.setContentType(CONTENT_TYPE_JSON);
                    httpPost.setEntity(entity);
                    // execute is a blocking call, it's best to call this code
                    // in a thread separate from the ui's
                    HttpResponse response = httpClient.execute(httpPost);
                    callback.onResponseReceived(response);
                }
                catch (Exception ex)
                {
                    callback.onError(ex);
                }
            }
        };
        thread.start();
    }

/*	public static void httpPost(final String url,
							    final Gson json,
							    final RequestCallback callback)
	{
		httpPost(url, json.toString(), callback);
	}*/

    public static void httpPost(final String url,
                                final Map<String, String> params,
                                final RequestCallback callback)
    {
        Log.i("httpPost a url: " + url, "- Parametros: " + params);

        final HttpClient httpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);

        Thread thread = new Thread()
        {
            public void run()
            {
                HttpPost httpPost = new HttpPost(url);

                //Parametros
                List <NameValuePair> nvps = new ArrayList <NameValuePair>();
                Iterator<String> it=params.keySet().iterator();
                while(it.hasNext())
                {
                    String key=it.next();
                    nvps.add(new BasicNameValuePair(key, params.get(key)));
                }

                try
                {
                    httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
                    HttpResponse response = httpClient.execute(httpPost);
                    callback.onResponseReceived(response);

                }
                catch (Exception ex)
                {
                    callback.onError(ex);
                }
            }
        };
        thread.start();
    }


    /**
     * httpGet Asíncrono.
     * @param url
     * @param callback
     */
    public static void httpGet(final String url,
                               final RequestCallback callback)
    {
        Log.i("doGet", " - url: " + url);

        final HttpClient httpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);

        Thread thread = new Thread()
        {
            public void run()
            {
                HttpGet httpget = new HttpGet(url);
                HttpResponse response;
                try
                {
                    response = httpClient.execute(httpget);
                    callback.onResponseReceived(response);
                }
                catch (Exception ex) {
                    callback.onError(ex);
                }
            }
        };
        thread.start();
    }

    /**
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void httpGet(String url,
                               final Map<String, String> params,
                               final RequestCallback callback)
    {
        url+=getParamsQueryString(params);

        httpGet(url, callback);
    }

    public static ByteArrayInputStream httpGetFile(String url) throws Exception
    {
        URL connectURL=new URL(url);
        HttpURLConnection conn=(HttpURLConnection)connectURL.openConnection();
        InputStream in=conn.getInputStream();

        //Normalmente son archivos muy pequeños
        //con lo cual no hace falta establecer un tamaño máximo
        //de buffer. No obstante se harán las pruebas pertinentes.
        int numBytes=in.available();
        byte[] buff=new byte[numBytes];

        return new ByteArrayInputStream(buff);
    }

    private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     *
     * @param url
     * @param params
     * @return
     */
    public static String httpGetSync(String url,
                                     Map<String, String> params)
    {
        url+=getParamsQueryString(params);

        return httpGetSync(url);
    }

    /**
     * httpGetSync Síncrono.
     * @param url
     * @return
     */
    public static String httpGetSync(String url)
    {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);

        String sresponse=null;
        try {
            HttpResponse response = httpclient.execute(httpget);
            sresponse=getResponse(response);

	            /*HttpEntity entity = response.getEntity();

	            Log.v("HttpUtil.httpGet", response.getStatusLine().toString());

	            if (entity != null) {

	                InputStream instream = entity.getContent();
	                sresponse = convertStreamToString(instream);
	                instream.close();
	                entity.consumeContent();
	            }   */

        } catch (Exception ex) {

            Log.v("HttpUtil.httpGet", ex.toString());

        }
        return sresponse;
    }

    /**
     *
     * La comunicación con el servidor es Síncrona.
     * @param url
     * @param parametros
     * @return String       Respuesta del servidor
     */
    public static String httpPostSync(String url, Map<String, String> parametros)
    {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpost = new HttpPost(url);

        //Parametros
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        Iterator<String> it=parametros.keySet().iterator();
        while(it.hasNext())
        {
            String key=it.next();
            nvps.add(new BasicNameValuePair(key, parametros.get(key)));
        }

        String sresponse=null;
        String econding=HTTP.UTF_8;

        try
        {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            HttpResponse response = httpclient.execute(httpost);

            sresponse=getResponse(response);

	        /*HttpEntity entity = response.getEntity();

	        Log.v("HttpUtil.httpPost", response.getStatusLine().toString());

	        if (entity != null) {

                InputStream instream = entity.getContent();
                sresponse = convertStreamToString(instream);
                instream.close();
                entity.consumeContent();
	        }*/
        }
        catch(Exception ex)
        {
            Log.v("HttpUtil.httpPost", ex.toString());
        }

        return sresponse;
    }

    public static void httpPostMultiPart(final String url,
                                         final String fileName,
                                         final InputStream file,
                                         final Map parametros,
                                         final RequestCallback callback)
    {
        Thread thread = new Thread()
        {
            public void run()
            {
                String responseString = null;

                try {

                    URL connectURL = new URL(url);

                    HttpURLConnection conn = (HttpURLConnection) connectURL
                            .openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Content-Type",
                            "multipart/form-data;boundary=" + boundary);

                    DataOutputStream dos = new DataOutputStream(conn
                            .getOutputStream());

                    // Parametros
                    Iterator it = parametros.keySet().iterator();
                    while (it.hasNext()) {
                        String key = (String) it.next();

                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos
                                .writeBytes("Content-Disposition: form-data; name=\""
                                        + key + "\"" + lineEnd);

                        dos.writeBytes(lineEnd);
                        dos.writeBytes(parametros.get(key).toString());
                        dos.writeBytes(lineEnd);
                    }

                    // Archivo
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos
                            .writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
                                    + fileName + "\"" + lineEnd);

                    dos.writeBytes(lineEnd);

                    Log.v("httpPostMultiPart", "Headers are written");

                    // create a buffer of maximum size
                    int bytesAvailable = file.available();
                    int maxBufferSize = 1024;
                    int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    byte[] buffer = new byte[bufferSize];

                    // read file and write to server
                    int bytesRead = file.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = file.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = file.read(buffer, 0, bufferSize);
                    }

                    // send multipart form data necessary after file data ...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    // close streams
                    file.close();
                    dos.flush();

                    // Response from server

                    InputStream is = conn.getInputStream();
                    int ch;
                    StringBuffer b = new StringBuffer();
                    while ((ch = is.read()) != -1)
                        b.append((char) ch);

                    responseString = b.toString();
                    dos.close();

                    callback.onResponseReceived(responseString);
                }
                catch (Exception ex)
                {
                    callback.onError(ex);
                }
            }
        };
        thread.start();
    }

    public static String httpPostMultiPart(String url,
                                           String fileName,
                                           InputStream file,
                                           Map parametros)
            throws Exception
    {
        String responseString = null;
        URL connectURL = new URL(url);

//		InputStream fileInputStream = (InputStream) parametros
//				.get("fileInputStream");

        HttpURLConnection conn = (HttpURLConnection) connectURL.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="
                + boundary);

        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

        //Parametros
        Iterator it=parametros.keySet().iterator();
        while(it.hasNext())
        {
            String key=(String)it.next();

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\""+key+"\""
                    + lineEnd);

            dos.writeBytes(lineEnd);
            dos.writeBytes(parametros.get(key).toString());
            dos.writeBytes(lineEnd);
        }

        //Archivo
        dos.writeBytes(twoHyphens + boundary + lineEnd);
        dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
                + fileName + "\"" + lineEnd);

        dos.writeBytes(lineEnd);

        Log.v("httpPostMultiPart", "Headers are written");

        // create a buffer of maximum size
        int bytesAvailable = file.available();
        int maxBufferSize = 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        // read file and write to server
        int bytesRead = file.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            dos.write(buffer, 0, bufferSize);
            bytesAvailable = file.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = file.read(buffer, 0, bufferSize);
        }

        // send multipart form data necessary after file data ...
        dos.writeBytes(lineEnd);
        dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

        // close streams
        file.close();
        dos.flush();

        // Response from server
        InputStream is = conn.getInputStream();
        int ch;
        StringBuffer b = new StringBuffer();
        while ((ch = is.read()) != -1)
            b.append((char) ch);

        responseString = b.toString();
        dos.close();

        return responseString;
    }

    /**
     * Formatea una QueryString de parametros para agregarselos a una Url HTTP.
     * @param params
     * @return
     */
    public static String getParamsQueryString(Map<String, String> params)
    {
        String parameters="?";
        Iterator<String> it=params.keySet().iterator();
        while(it.hasNext())
        {
            if (parameters.length()>1)
                parameters+="&";

            String key=it.next();
            parameters+=key+"="+params.get(key);
        }

        return parameters;
    }

    public static String getResponse(HttpResponse response)
    {
        String sresponse=null;
        HttpEntity entity = response.getEntity();

        Log.v("HttpUtil.getResponse", response.getStatusLine().toString());

        if (entity == null)
            return null;

        try
        {
            InputStream instream = entity.getContent();
            sresponse = convertStreamToString(instream);
            instream.close();
            entity.consumeContent();
        }
        catch(Exception ex)
        {
            Log.v("HttpUtil.getResponse", ex.toString());
        }

        return sresponse;
    }
}
