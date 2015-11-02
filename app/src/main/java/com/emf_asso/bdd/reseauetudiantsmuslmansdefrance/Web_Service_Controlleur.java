package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.os.AsyncTask;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.joda.time.DateTime;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;

/**
 * Created by Omar on 29/10/2015.
 */
public class Web_Service_Controlleur extends AsyncTask<String,String,String>{

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String  urlWB="http://www.emf-asso.com/bdd/wb/wb_android_api.php";
    public OkHttpClient client = new OkHttpClient();
    String reponse = "";
    MainActivity mActivity;
    RequestBody formBody;
    DateTime DT;

    public Web_Service_Controlleur(MainActivity activity, RequestBody RequestformBody) {
        mActivity = activity;
        formBody = RequestformBody;
    }
    protected void onPreExecute()
    {

    }
    protected String doInBackground(String...urls)
    {
        try {

            DT = new DateTime();
            reponse = post(urlWB, formBody);
        }
        catch (IOException e)
        {
            mActivity.ReceptionResponse(new HttpReponse(false, e.toString()));
        }
        return reponse;
    }
    protected void onPostExecute(String result)
    {
        try {
            JSONObject jsonResult = (JSONObject) new JSONParser().parse(result);
            mActivity.ReceptionResponse(new HttpReponse(jsonResult, true, (jsonResult.get("action")).toString(), DT));
        } catch (Exception e) {
            mActivity.ReceptionResponse(new HttpReponse(false, e.toString()));
        }

    }

    public String post(String url, RequestBody body) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    protected void onProgressUpdate(String str)
    {

    }

    public String runOkHttp(String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
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
