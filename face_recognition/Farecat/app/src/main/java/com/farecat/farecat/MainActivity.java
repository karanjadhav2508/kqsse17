package com.farecat.farecat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final String KAIROS_DOMAIN = "https://api.kairos.com";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    private ImageView photoView;
    private String apiService;
    private Bitmap bitmapPhoto;

    static final String TAG = "karansMessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photoView = (ImageView) findViewById(R.id.photoView);
        Button enrollButton = (Button) findViewById(R.id.enrollButton);
        Button verifyButton = (Button) findViewById(R.id.verifyButton);

        if(!hasCamera()) {
            enrollButton.setEnabled(false);
            verifyButton.setEnabled(false);
        }
    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
    }

    public void takePhoto(View v) {
        apiService = ((Button)v).getText().toString().toLowerCase();
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(photoIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE  && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            photoView.setImageBitmap(photo);    //remove later. just for reference
            bitmapPhoto = photo;

            //Speculative code
            String base64Photo = bitmapToBase64(photo);
            new KairosApiTask().execute(base64Photo);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String bitmapToBase64(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] ba = baos.toByteArray();
        return Base64.encodeToString(ba, Base64.DEFAULT);
    }

    private class KairosApiTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String base64Photo = params[0];
            JSONObject json = new JSONObject();
            try {
                json.putOpt("image", base64Photo);
                json.putOpt("subject_id", "karan");
                json.putOpt("gallery_name", "karan");
            } catch(JSONException j) {
                Log.i(TAG, "json : "+j.toString());
            }
            RequestBody requestBody = RequestBody.create(JSON, json.toString());
            Request request = new Request.Builder()
                    .url(KAIROS_DOMAIN+"/"+apiService)
                    .addHeader("app_id", "76395980")
                    .addHeader("app_key", "fe00f9411bbadb0269b4f0757050aaf2")
                    .addHeader("Content-Type", "application/json")
                    .post(requestBody)
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
            }
            catch(IOException i) {
                Log.i(TAG, "response : "+i.toString());
            }
            if(response != null && response.isSuccessful()) {
                Log.i(TAG, "Success!");
                try {
                    //Log.i(TAG, "response : "+response.body().string());
                    try {
                        JSONObject jo = new JSONObject(response.body().string());
                        Log.i(TAG, "parsedStatus : "+jo.getJSONArray("images")
                                .getJSONObject(0)
                                .getJSONObject("transaction")
                                .getString("status")
                        );
                    } catch(JSONException j) {
                        j.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                Log.i(TAG, "Hag diya");
            }
            return response.toString();
        }
    }
}
