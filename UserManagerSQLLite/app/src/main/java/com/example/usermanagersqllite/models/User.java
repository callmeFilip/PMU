package com.example.usermanagersqllite.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

    private int _id;
    private String _UserName;
    private String _password;

    private String _phoneNumber;
    private String _email;
    private String _address;
    private int _age;

    public User() {
    }

    public User(int id, String Username, String _pass, String phoneNumber, String email, String address, int age) {
        this._id = id;
        this._UserName = Username;
        this._password = _pass;
        this._phoneNumber = phoneNumber;
        this._email = email;
        this._address = address;
        this._age = age;
    }

    public User(String Username, String _pass, String phoneNumber, String email, String address, int age) {
        this._UserName = Username;
        this._password = (_pass);
        this._phoneNumber = phoneNumber;
        this._email = email;
        this._address = address;
        this._age = age;
    }

    public User(int id, String Username, String _pass) {
        this._id = id;
        this._UserName = Username;
        this._password = hashPassword(_pass);
    }

    public User(String Username, String _pass) {
        this._UserName = Username;
        this._password = hashPassword(_pass);
    }

    public int getID() {
        return this._id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public String getUserName() {
        return this._UserName;
    }

    public void setUserName(String name) {
        this._UserName = name;
    }

    public String getPassword() {
        return this._password;
    }

    public void setPassword(String _pass) {
        this._password = hashPassword(_pass);
    }

    public String getPhoneNumber() { return _phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this._phoneNumber = phoneNumber; }

    public String getEmail() { return _email; }
    public void setEmail(String email) { this._email = email; }

    public String getAddress() { return _address; }
    public void setAddress(String address) { this._address = address; }

    public int getAge() { return _age; }
    public void setAge(int age) { this._age = age; }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password;
        }
    }
}
