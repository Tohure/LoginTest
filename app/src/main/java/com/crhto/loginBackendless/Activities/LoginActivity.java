package com.crhto.loginBackendless.Activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.crhto.loginBackendless.Helpers.LoadingCallback;
import com.crhto.loginBackendless.R;
import com.crhto.loginBackendless.Helpers.Constantes;
import com.crhto.loginBackendless.Helpers.ValidatorHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailUser, passwordUser;
    private static final int REGISTER_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        LoginActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) { getWindow().setStatusBarColor(colorPrimaryDark); }

        Backendless.initApp( this, Constantes.APLICATION_ID, Constantes.ANDROID_SECRET_ID, Constantes.VERSION_APP);

        initUI();
    }

    private void initUI() {
        Button loginButton = (Button) findViewById(R.id.btn_ingresar);
        loginButton.setOnClickListener(this);
        emailUser = (EditText) findViewById( R.id.edit_text_email );
        passwordUser = (EditText) findViewById( R.id.edit_text_pass );
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_ingresar){
            CharSequence email = emailUser.getText();
            CharSequence password = md5(passwordUser.getText().toString());

            if( validarValuesLogin( email, password ) ) {
                LoadingCallback<BackendlessUser> loginCallback = createLoginCallback();

                loginCallback.showLoading();
                loginUser( email.toString(), password.toString(), loginCallback );
            }
        }
    }

    public LoadingCallback<BackendlessUser> createLoginCallback() {
        return new LoadingCallback<BackendlessUser>( this, getString( R.string.loading_login ) ) {
            @Override
            public void handleResponse( BackendlessUser loggedInUser ) {
                super.handleResponse( loggedInUser );
                String email = loggedInUser.getEmail();
                String name = (String) loggedInUser.getProperty("name");

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("email", email);
                i.putExtra("name", name);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation,R.anim.animation2).toBundle();
                startActivity(i, bndlanimation);
                finish();
                Toast.makeText( LoginActivity.this, String.format( getString( R.string.info_logged_in ), loggedInUser.getObjectId() ), Toast.LENGTH_LONG ).show();
            }
        };
    }

    private boolean validarValuesLogin(CharSequence email, CharSequence password) { return ValidatorHelper.isEmailValid( this, email ) && ValidatorHelper.isPasswordValid( this, password ); }

    public void loginUser( String email, String password, AsyncCallback<BackendlessUser> loginCallback ){ Backendless.UserService.login( email, password, loginCallback ); }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {

            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2){ h = "0" + h; }

                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}
