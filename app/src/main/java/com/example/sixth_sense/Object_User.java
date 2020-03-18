package com.example.sixth_sense;

import java.sql.*;

public class Object_User {


    // Initialises objects
    private int User_ID;        //Stores user's ID, unique, used for database searching
    private String password;      //Stores user's password, used for login
    private String email;         //Stores user's email, unique, used for login and account retrieval
    private String First_name;         //Stores user first name
    private String last_name;         //Stores user last name
    private String AC;         //Stores user account type

    // Statement to connect to Postgresql
    private Statement s;

    // Passes on Statement s to parent
    public void setS(Statement s) {
        this.s = s;
    }
    // Setters and Getters
    public void setUser_ID(int User_ID) { this.User_ID = User_ID; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setFirst_name(String first_name) {
        First_name = first_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public void setAC(String AC) {
        this.AC = AC;
    }

    public int getUser_ID() {
        return User_ID;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public String getFirst_name() {
        return First_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public String getAC() {
        return AC;
    }
    public Statement getS() {
        return s;
    }

    // Retrieves info associated with parent login credentials and return state to main class
    public boolean login(String email, String password) throws SQLException {
        // Sets credentials
        setEmail(email);
        setPassword(password);
        // Checks credentials with database
        String sqlStr = "SELECT * FROM users WHERE email =\'" + email + "\' and password = \'" + password + "\';";
        ResultSet rset = s.executeQuery(sqlStr);
        setFirst_name(null);
        while (rset.next()) {
            // Set data from database
            setUser_ID(rset.getInt("id"));
            setEmail(rset.getString("email"));
            setFirst_name(rset.getString("first_name"));
            setLast_name(rset.getString("last_name"));
            setAC(rset.getString("account_type"));

        }
        if (getFirst_name() == null) {
            // Returns status to main class to show user message
            return false;
        } else {
            // Returns status to main class to show user message
            return true;
        }
    }

    // Create answers for new date
    public void create(String TimeStamp, double lat, double longi, String answers, String CSV, Boolean Virus) throws SQLException {
        String sqlStr;
        sqlStr = "INSERT INTO DATA (USER_ID,TIME_STAMP,LATITUDE,LONGITUDE,NFC_ID,SCAN_DATA,BOOLEAN) VALUES (\'" + getUser_ID() + "\',\'"+ TimeStamp +"\',\'"+ lat +"\',\'"+ longi +"\',\'"+ answers +"\',\'" + CSV + "\',\'" + Virus + "\');";
        getS().execute (sqlStr);
    }

    // Create answers for new date
    public String getHistory() throws SQLException {
        String CSV = "";
        try {
            String sqlStr = "SELECT * FROM DATA WHERE USER_ID = '2';";
            ResultSet rset=s.executeQuery(sqlStr);
            while(rset.next()){
                CSV = rset.getString("SCAN_DATA");
            }
            rset.close();
        }
        catch (Exception e){
        }
        return CSV;
    }



}