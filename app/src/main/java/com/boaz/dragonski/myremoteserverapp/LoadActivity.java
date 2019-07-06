package com.boaz.dragonski.myremoteserverapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoadActivity extends AppCompatActivity {
    public static final String USER_DATA = "userData";
    public static final String TOKEN = "token";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        Intent intent = getIntent();
        String userName = intent.getStringExtra(MainActivity.USER_NAME_KEY);
        AppExe.getInstance().getThread().execute(() -> {
                    String userToken = API.getInstance().getToken(userName);
                    final UserData userData = API.getInstance().getUserData(userToken);
                    runOnUiThread(() -> initUserActivity(userData, userToken));
                });
    }

    private void initUserActivity(UserData userData, String token){
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra(USER_DATA, userData);
        intent.putExtra(TOKEN, token);
        startActivity(intent);
        finish();
    }
}
