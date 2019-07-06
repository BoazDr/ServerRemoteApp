package com.boaz.dragonski.myremoteserverapp;

import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;

public class MainActivity extends AppCompatActivity {
    static final String PREFS = "sp";
    static final String USER_NAME_KEY = "UserNameKey";
    static final String USER_NAME = "user_name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button submit = findViewById(R.id.submitButton);
        EditText userNameET = findViewById(R.id.userNameET);
        userNameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.equals("")){
                    submit.setVisibility(View.GONE);
                }
                else {
                    submit.setVisibility(View.VISIBLE);
                }
            }
        });

        submit.setOnClickListener(v -> {
            SharedPreferences.Editor editor = getSharedPreferences(PREFS, MODE_PRIVATE).edit();
            String userName = userNameET.getText().toString();
            editor.putString(USER_NAME, userName).apply();
            Intent intent = new Intent(this, LoadActivity.class);
            intent.putExtra(USER_NAME_KEY, userName);
            startActivity(intent);
            finish();
        });

        SharedPreferences mySP = getSharedPreferences(PREFS, MODE_PRIVATE);
        String myUserName = mySP.getString(USER_NAME, null);
        if (myUserName != null) {
            Intent intent = new Intent(this, LoadActivity.class);
            intent.putExtra(USER_NAME_KEY, myUserName);
            startActivity(intent);
            finish();
        }
    }
}
