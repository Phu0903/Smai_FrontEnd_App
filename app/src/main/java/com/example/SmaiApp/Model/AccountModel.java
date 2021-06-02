package com.example.SmaiApp.Model;

public class AccountModel {
    private String UserName;
    private String Password;
    private String PhoneNumber;
    private String AccessToken;

    public void setAccessToken(String accessToken) { AccessToken = accessToken; }

    public String getAccessToken() { return AccessToken; }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
