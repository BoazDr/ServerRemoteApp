package com.boaz.dragonski.myremoteserverapp;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class API {

    final OkHttpClient okHttpClient = new OkHttpClient();
    static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    static final String CHANGE_FIELD = "{ \"%s\" : \"%s\" }";
    static final String BASE_URL = "http://hujipostpc2019.pythonanywhere.com/";
    static final String TOKEN = "users/%s/token/";
    static final String CONTENT_TYPE = "Content-Type";
    static final String CONTENT_JSON = "application\\json";
    static final String USER_DATA = "user/";
    static final String USER_EDIT = "user/edit/";
    static final String AUTH_KEY = "Authorization";
    static final String AUTH_VALUE = "token %s";
    private enum UserTypes {
        image_url, pretty_name;
    }

    static API INSTANCE = new API();
    private API() {}
    public static API getInstance(){
        return INSTANCE;
    }


    public UserData getUserData(String token) {
        String authorization = String.format(AUTH_VALUE, token);
        Headers headers = new Headers.Builder().add(AUTH_KEY, authorization).build();
        String requestRetVal = makeGetRequest(BASE_URL + USER_DATA, headers);
        return requestValueParsing(requestRetVal);
    }

    private UserData changeType(UserTypes type, String token, String newValue){
        String authorization = String.format(AUTH_VALUE, token);
        String json = String.format(CHANGE_FIELD, type.name(), newValue);
        RequestBody body = RequestBody.create(JSON, json);
        Headers headers = new Headers.Builder().add(AUTH_KEY, authorization).add(CONTENT_TYPE, CONTENT_JSON).build();
        String requestRetVal = makePostRequest(BASE_URL + USER_EDIT, body, headers);
        return requestValueParsing(requestRetVal);
    }

    public String getToken(String userName) {
        String address = String.format(BASE_URL + TOKEN, userName);
        String requestRetVal = makeGetRequest(address, new Headers.Builder().build());
        try {
            JSONObject json = new JSONObject(requestRetVal);
            return json.getString("data");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public UserData changePrettyName(String token, String prettyName){
        return changeType(UserTypes.pretty_name, token, prettyName);
    }

    public UserData changeImage(String token, String imageUrl){
        return changeType(UserTypes.image_url, token, imageUrl);
    }

    private UserData requestValueParsing(String requestRetVal){
        try {
            JSONObject json = new JSONObject(requestRetVal);
            JSONObject data = json.getJSONObject("data");
            String username = data.getString("username");
            String imageUrl = data.getString("image_url");
            String prettyName = data.getString("pretty_name");
            return new UserData(prettyName, username, imageUrl);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String requestQuery(Request request){
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            return response.body().string();
        }
        catch (IOException e) {
            if (response != null)
                response.close();
            throw new RuntimeException(e);
        }
    }

    private String makeGetRequest(String url, Headers headers) {
        Request request = new Request.Builder().url(url).headers(headers).build();
        return requestQuery(request);
    }

    private String makePostRequest(String url, RequestBody requestBody, Headers headers){
        Request request = new Request.Builder().url(url).headers(headers).post(requestBody).build();
        return requestQuery(request);
    }
}