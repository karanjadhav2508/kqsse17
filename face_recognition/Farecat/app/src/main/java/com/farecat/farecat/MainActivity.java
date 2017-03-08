package com.farecat.farecat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.farecat.farecat.helpers.AlertDialogHelper;
import com.farecat.farecat.records.AttendanceRecord;
import com.farecat.farecat.records.DBManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

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
    private static final String SUCCESS = "success";
    private static final String MESSAGE_FAILURE = "message";

    private static final String APP_ID_VALUE = "76395980";
    private static final String APP_KEY_VALUE = "fe00f9411bbadb0269b4f0757050aaf2";
    private static final String GALLERY_VALUE = "students";

    private static final String EMPTY_USER = "User name cannot be blank";
    private static final String ALREADY_ENROLLED = " is already enrolled";
    private static final String COMM_PROBLEM = "Communication problem. Please try again.";

    private DBManager dbManager;
    private EditText subjectText;
    private String subject_id;
    private String apiService;
    private ArrayList<Button> buttons;

    static final String TAG = "MainActivity";
    final Context context = this;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this, null, null, 1);
        subjectText = (EditText) findViewById(R.id.subjectText);
        buttons = new ArrayList<Button>();
        Button enrollButton = (Button) findViewById(R.id.enrollButton);
        Button recognizeButton = (Button) findViewById(R.id.recognizeButton);
        Button viewAttendancePageButton = (Button) findViewById(R.id.viewAttendancePageButton);
        buttons.add(enrollButton);
        buttons.add(recognizeButton);
        buttons.add(viewAttendancePageButton);

        if(!hasCamera()) {
            enrollButton.setEnabled(false);
            recognizeButton.setEnabled(false);
        }
        alertDialog = AlertDialogHelper.buildAlertDialog(MainActivity.this, "Alert", "OK");
    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
    }

    public void takePhoto(View v) {
        apiService = ((Button)v).getText().toString().toLowerCase();
        if(apiService.equals(ENROLL)) {
            subject_id = subjectText.getText().toString();
            if(subject_id.matches("")) {
                alertDialog.setMessage(EMPTY_USER);
                alertDialog.show();
                return;
            }
        }
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(photoIntent, REQUEST_IMAGE_CAPTURE);
    }

    public void goToAttendance(View v) {
        Intent viewAttIntent = new Intent(getApplicationContext(), ViewAttendanceActivity.class);
        startActivity(viewAttIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE  && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            String base64Photo = bitmapToBase64(photo);

            try {
                subjectText.setText("");
                alertDialog.setMessage(apiService + " in progress..");
                for(Button b : buttons) {
                    b.setEnabled(false);
                }
                alertDialog.show();
                new KairosApiTask().execute(base64Photo);
            } catch(Exception e) {
                Log.i(TAG, "KairosApiTask problem : "+e.getMessage());
                e.printStackTrace();
            }
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

            Request request = buildRequest(base64Photo, apiService);
            Response response = null;
            ResponseResult rr = new ResponseResult();
            String respJsonString = "";

            try {
                if(apiService.equals(ENROLL)) {
                    Request recRequest = buildRequest(base64Photo, RECOGNIZE);
                    Response recResponse = client.newCall(recRequest).execute();
                    if(recResponse != null && recResponse.isSuccessful()) {
                        String recResponseJSON = recResponse.body().string();
                        ResponseResult recRespResult = parseJSONResponse(recResponseJSON);
                        if(!recRespResult.isSuccessful()) {
                            response = client.newCall(request).execute();
                            if(response != null && response.isSuccessful()) {
                                respJsonString = response.body().string();
                                rr = parseJSONResponse(respJsonString);
                            }
                        }
                        else {
                            rr.setResultMessage("User image already registered.");
                        }
                    }
                }
                else if(apiService.equals(RECOGNIZE)) {
                    response = client.newCall(request).execute();
                    if(response != null && response.isSuccessful()) {
                        respJsonString = response.body().string();
                        rr = parseJSONResponse(respJsonString);
                        if(rr.isSuccessful()) {
                            dbManager.addAttRecord(new AttendanceRecord(subject_id));
                        }
                    }
                }
            } catch(IOException i) {
                Log.i(TAG, "Problem in :\n1. Executing Kairon POST request\nOR\n2.Getting body of response");
                i.printStackTrace();
                rr.setResultMessage(COMM_PROBLEM);
            }
            return rr.getResultMessage();
            /*try {
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
                            Log.i(TAG, "Kairos Status : "+kairosStatus);
                            if(kairosStatus.equals(SUCCESS)) {
                                String subject = transaction.getString(SUBJECT_ID);
                                resultMessage = apiService+" "+subject+" : "+kairosStatus;
                                if(apiService.equals(RECOGNIZE)) {
                                    dbManager.addAttRecord(new AttendanceRecord(subject));
                                }
                            }
                            else {
                                resultMessage = apiService+" : "+kairosStatus+" - "+
                                        transaction.getString(MESSAGE_FAILURE);
                            }
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
            return resultMessage;*/
        }

        private Request buildRequest(String base64Photo, String apiService) {
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

            return request;
        }

        private ResponseResult parseJSONResponse(String respJsonString) {
            ResponseResult rr = new ResponseResult();
            try {
                Log.i(TAG, "JSON : "+respJsonString);
                JSONObject jo = new JSONObject(respJsonString);
                if(jo.has(ERRORS)) {
                    String kairosError = ((JSONObject)jo.getJSONArray(ERRORS).get(0)).getString(MESSAGE);
                    rr.setResultMessage(apiService+" : "+kairosError);
                    Log.i(TAG, "kairosError : " + kairosError);
                }
                else {
                    JSONObject transaction =((JSONObject)jo.getJSONArray(IMAGES).get(0))
                            .getJSONObject(TRANSACTION);
                    String kairosStatus = transaction.getString(STATUS);
                    Log.i(TAG, "kairosStatus : " + kairosStatus);
                    if(kairosStatus.equals(SUCCESS)) {
                        String subject = transaction.getString(SUBJECT_ID);
                        rr.setResultMessage(apiService+" "+subject+" : "+kairosStatus);
                        rr.setSuccessful(true);
                    }
                    else {
                        rr.setResultMessage(apiService+" : "+kairosStatus+" - "+
                                transaction.getString(MESSAGE_FAILURE));
                    }
                }
            } catch(JSONException j) {
                Log.i(TAG, "Problem in parsing response JSON");
                j.printStackTrace();
            }
            return rr;
        }

        @Override
        protected void onPostExecute(String s) {
            if(alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
            for(Button b : buttons) {
                b.setEnabled(true);
            }
            alertDialog.setMessage(s);
            alertDialog.show();
            super.onPostExecute(s);
        }
    }

    private class ResponseResult {
        private boolean isSuccessful = false;
        private String resultMessage = "";

        public void setResultMessage(String resultMessage) {
            this.resultMessage = resultMessage;
        }
        public void setSuccessful(boolean successful) {
            isSuccessful = successful;
        }
        public boolean isSuccessful() {
            return isSuccessful;
        }
        public String getResultMessage() {
            return resultMessage;
        }
    }

}