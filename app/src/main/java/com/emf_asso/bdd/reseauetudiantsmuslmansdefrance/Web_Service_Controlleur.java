package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.Activity;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Omar on 29/10/2015.
 */
public class Web_Service_Controlleur extends AsyncTask<String,String,String>{

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String  urlWB="http://www.emf-asso.com/bdd/wb/wb_android_api.php";
    public OkHttpClient client = new OkHttpClient();
    public String reponse  = "";
    Activity mActivity;

    public Web_Service_Controlleur(Activity activity) {
        mActivity = activity;
    }

    protected void onPreExecute()
    {

    }

    protected String doInBackground(String...urls)
    {
        try {
            reponse = post(urlWB, createFormBody());
        }
        catch (IOException e)
        {
            reponse = e.toString();
        }
        return reponse;
    }

    protected void onProgressUpdate(String str)
    {

    }

    protected void onPostExecute(String result)
    {

    }

    public String runOkHttp(String url)  throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();

    }

    public RequestBody createFormBody ()
    {
        RequestBody formBody = new FormEncodingBuilder()
                .add("action", "check_mail")
                .add("mail", "latreche.omar@gmail.com")
                .build();
        return formBody;
    }

    public String post(String url, RequestBody body) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
