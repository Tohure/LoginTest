package com.crhto.loginBackendless.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.crhto.loginBackendless.R;

public class MainActivity extends AppCompatActivity {

    private String valueEmail,valueName;
    private TextView mailUser, nameUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hola este login es para mi test!!!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initUI();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            valueEmail = extras.getString("email");
            valueName = extras.getString("name");
        }

        setValues();
    }

    private void setValues() {
        nameUser.setText(String.format("Bienvenido %s ",valueName));
        mailUser.setText(valueEmail);
    }

    private void initUI() {
        nameUser = (TextView) findViewById(R.id.dataUser);
        mailUser = (TextView) findViewById(R.id.dataMail);
    }

}
