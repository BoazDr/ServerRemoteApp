package com.boaz.dragonski.myremoteserverapp;

import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        UserData userData = getIntent().getParcelableExtra(LoadActivity.USER_DATA);
        Button prettyNameButton = findViewById(R.id.submitPrettyName);
        Button imageUrlButton = findViewById(R.id.submitImageUrl);
        String token = getIntent().getStringExtra(LoadActivity.TOKEN);
        EditText prettyNameET = findViewById(R.id.prettyNameET);
        EditText imageUrlET = findViewById(R.id.changeImageET);
        prettyNameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.equals("")) {
                    prettyNameButton.setVisibility(View.GONE);
                }
                else {
                    prettyNameButton.setVisibility(View.VISIBLE);
                }
            }
        });
        imageUrlET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.equals("")) {
                    imageUrlButton.setVisibility(View.GONE);
                }
                else {
                    imageUrlButton.setVisibility(View.VISIBLE);
                }
            }
        });
        imageUrlButton.setOnClickListener(v -> {
            String newImageUrl = imageUrlET.getText().toString();
            imageUrlET.getText().clear();
            AppExe.getInstance().getThread().execute(() -> {
                        UserData mUserData = API.getInstance().changeImage(token, newImageUrl);
                        runOnUiThread(() -> changeUI(mUserData));
                    });
        });
        prettyNameButton.setOnClickListener(v -> {
            String newName = prettyNameET.getText().toString();
            prettyNameET.getText().clear();
            AppExe.getInstance().getThread().execute(() -> {
                        UserData mUserData = API.getInstance().changePrettyName(token, newName);
                        runOnUiThread(() -> changeUI(mUserData));
                    });
        });

        changeUI(userData);
    }

    private void changeUI(UserData userData) {
        TextView welcomeView = findViewById(R.id.welcomeTextView);
        ImageView profileImage = findViewById(R.id.profileImage);
        String name = userData.getUserPrettyName().equals("") ? userData.getUserName() : userData.getUserPrettyName();
        String welcomeText = "welcome " + name + "!";
        welcomeView.setText(welcomeText);

        //Picasso.get().load(API.BASE_URL + userDetails.getImageUrl()).into(profileImage);
    }
}
