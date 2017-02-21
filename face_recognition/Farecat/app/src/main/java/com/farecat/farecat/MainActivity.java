package com.farecat.farecat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String CONTENT = "Content-Type";
    private static final String CONTENT_TYPE = "application/json";

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    static final String KAIROS_DOMAIN = "https://api.kairos.com";
    private static final String ENROLL = "enroll";
    private static final String RECOGNIZE = "recognize";
    private static final String APP_ID = "app_id";
    private static final String APP_KEY = "app_key";
    private static final String IMAGE = "image";
    private static final String SUBJECT_ID = "subject_id";
    private static final String GALLERY_NAME = "gallery_name";

    private static final String IMAGES = "images";
    private static final String TRANSACTION = "transaction";
    private static final String STATUS = "status";
    private static final String ERRORS = "Errors";
    private static final String MESSAGE = "Message";

    private static final String APP_ID_VALUE = "76395980";
    private static final String APP_KEY_VALUE = "fe00f9411bbadb0269b4f0757050aaf2";
    private static final String GALLERY_VALUE = "students";

    private static final String EMPTY_USER = "User name cannot be blank";
    private static final String NO_STATUS = "";
    private static final String COMM_PROBLEM = "Communication problem. Please try again.";
    private EditText subjectText;
    private String subject_id;
    private String apiService;
    private TextView status;

    static final String TAG = "logger";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subjectText = (EditText) findViewById(R.id.subjectText);
        status = (TextView) findViewById(R.id.statusTextView);
        Button enrollButton = (Button) findViewById(R.id.enrollButton);
        Button recognizeButton = (Button) findViewById(R.id.recognizeButton);
        if(!hasCamera()) {
            enrollButton.setEnabled(false);
            recognizeButton.setEnabled(false);
        }
    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
    }

    public void takePhoto(View v) {
        status.setText(NO_STATUS);
        apiService = ((Button)v).getText().toString().toLowerCase();
        if(apiService.equals(ENROLL)) {
            subject_id = subjectText.getText().toString();
            if(subject_id.matches("")) {
                status.setText(EMPTY_USER);
                return;
            }
        }
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(photoIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE  && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
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
            String resultMessage = "";
            String base64Photo = params[0];
            JSONObject json = new JSONObject();
            Log.i(TAG, "Kairos API call :\nSERVICE : "+apiService+" GALLERY : "+GALLERY_VALUE);
            try {
                json.putOpt(IMAGE, base64Photo);
                json.putOpt(GALLERY_NAME, GALLERY_VALUE);
                if(apiService.equals(ENROLL)) {
                    Log.i(TAG, "SUBJECT : "+subject_id);
                    json.putOpt(SUBJECT_ID, subject_id);
                }
            } catch(JSONException j) {
                Log.i(TAG, "Problem in adding parameters to JSON");
                j.printStackTrace();
            }
            RequestBody requestBody = RequestBody.create(JSON, json.toString());
            Request request = new Request.Builder()
                    .url(KAIROS_DOMAIN+"/"+apiService)
                    .addHeader(APP_ID, APP_ID_VALUE)
                    .addHeader(APP_KEY, APP_KEY_VALUE)
                    .addHeader(CONTENT, CONTENT_TYPE)
                    .post(requestBody)
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                if(response != null && response.isSuccessful()) {
                    Log.i(TAG, "Response successful");
                    String respJsonString = response.body().string();
                    try {
                        JSONObject jo = new JSONObject(respJsonString);
                        Log.i(TAG, "Response JSON : "+respJsonString);
                        if(jo.has(ERRORS)) {
                            String kairosError = ((JSONObject)jo.getJSONArray(ERRORS).get(0)).getString(MESSAGE);
                            Log.i(TAG, "Kairos Error : "+kairosError);
                            resultMessage = apiService+" : "+kairosError;
                        }
                        else {
                            JSONObject transaction =((JSONObject)jo.getJSONArray(IMAGES).get(0))
                                    .getJSONObject(TRANSACTION);
                            String kairosStatus = transaction.getString(STATUS);
                            String subject = transaction.getString(SUBJECT_ID);
                            Log.i(TAG, "Kairos Status : "+kairosStatus);
                            resultMessage = apiService+" "+subject+" : "+kairosStatus;
                        }
                    } catch(JSONException j) {
                        Log.i(TAG, "Problem in parsing response JSON");
                        j.printStackTrace();
                    }
                }
                else {
                    Log.i(TAG, "Response failed : "+response.code());
                }
            } catch(IOException i) {
                Log.i(TAG, "Problem in :\n1. Executing Kairon POST request\nOR\n2.Getting body of response");
                resultMessage = COMM_PROBLEM;
                i.printStackTrace();
            }
            return resultMessage;
        }

        @Override
        protected void onPostExecute(String s) {
            subjectText.setText("");
            status.setText(s);
            super.onPostExecute(s);
        }
    }
}