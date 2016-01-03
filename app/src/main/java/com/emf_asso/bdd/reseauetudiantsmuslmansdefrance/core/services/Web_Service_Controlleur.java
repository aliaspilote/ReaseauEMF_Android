package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import android.os.AsyncTask;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.ActivityConnectedWeb;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.Messages;
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
    ActivityConnectedWeb mActivity;
    RequestBody formBody;
    DateTime DT;
    Boolean tryInternet = true;

    public Web_Service_Controlleur(ActivityConnectedWeb activity, RequestBody RequestformBody) {
        mActivity = activity;
        formBody = RequestformBody;
    }
    protected void onPreExecute()
    {
    }
    protected String doInBackground(String...urls)
    {
        tryInternet = mActivity.testInternetConnection.isConnectingToInternet();
        if (tryInternet) {
            reponse = post(urlWB, formBody);
            return reponse;
        } else {
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("TryParse", "false");
            jsonResult.put("result", "false");
            return jsonResult.toString();
        }
    }
    protected void onPostExecute(String result)
    {
        DT = new DateTime();
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("TryParse", "false");
        jsonResult.put("result", "false");
        if (!tryInternet || result == null || result.contains("Unable to resolve host"))
            mActivity.ReceptionResponse(new HttpReponse(false, Messages.error_no_internet));
        else {
            try {
                jsonResult = (JSONObject) new JSONParser().parse(result);
            } catch (Exception e) {

                mActivity.ReceptionResponse(new HttpReponse(false, e.getMessage()));
            }
            String action = "";
            try {
                action = (jsonResult.get("action")).toString();
            } catch (Exception e) {

                mActivity.ReceptionResponse(new HttpReponse(false, e.getMessage()));
            }
            mActivity.ReceptionResponse(new HttpReponse(jsonResult, true, action, DT, null));
        }
    }

    public String post(String url, RequestBody body) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
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
