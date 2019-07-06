package com.boaz.dragonski.myremoteserverapp;

import android.os.Parcel;
import android.os.Parcelable;

public class UserData implements Parcelable {

    private String imageUrl, userName, userPrettyName;

    public UserData(Parcel in) {
        userName = in.readString();
        userPrettyName = in.readString();
        imageUrl = in.readString();
    }

    public UserData(String userPrettyName, String userName, String imageUrl) {
        this.userName = userName;
        this.userPrettyName = userPrettyName;
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(userPrettyName);
        dest.writeString(imageUrl);
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }
    };
    public String getUserName() {
        return userName;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getUserPrettyName() {
        return userPrettyName;
    }
}
