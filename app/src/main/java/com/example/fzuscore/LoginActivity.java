package com.example.fzuscore;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private EditText mAccountNumber;
    private EditText mPassword;
    private View mProgressView;
    private View mLoginFormView;
    MediaType mediaType = MediaType.parse("application/json");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mAccountNumber =  findViewById(R.id.et_account_number);

        mPassword =  findViewById(R.id.et_password);
        FloatingActionButton fab = findViewById(R.id.fab_sign_in);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        postJson();
                    }
                }).start();

//                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(intent);
            }
        });

    }

    private void postJson() {
        OkHttpClient okHttpClient = new OkHttpClient();
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("student_id",mAccountNumber.getText());
            jsonObject.put("student_password",mPassword.getText());
        }catch (JSONException e){
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(mediaType,jsonObject.toString());
        Request request = new Request.Builder()
                .url("http://47.112.10.160:3389/api/login")
                .post(requestBody)
                .build();
        try{
            Response response = okHttpClient.newCall(request).execute();
            String result = response.body().string();
            Toast.makeText(LoginActivity.this,result,Toast.LENGTH_LONG).show();
            response.body().close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
}
