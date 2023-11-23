package com.example.legato;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class MainActivity extends AppCompatActivity {
    String Appid = "legato-mobile-giepz";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnEntrar = findViewById(R.id.btnEntrar);

        Realm.init(this);
        App app = new App(new AppConfiguration.Builder(Appid).build());

        app.loginAsync(Credentials.anonymous(), new App.Callback<User>(){
            @Override
            public void onResult(App.Result<User> result) {
                if (result.isSuccess()) {
                    Log.v("User", "Logged In anonymously");
                } else {
                    Log.v("User", "Failedo to Login");
                }
            }
        });

        btnEntrar.setOnClickListener(e->{
            Intent intent = new Intent(MainActivity.this, Upload_transcricao.class);
            startActivity(intent);
        });
    }
}