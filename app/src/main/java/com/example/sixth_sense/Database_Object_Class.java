package com.example.sixth_sense;

import android.os.StrictMode;
import android.util.Log;

import java.sql.*;

public class Database_Object_Class {


    // Initialises variables to connect to database
    private static String dbUrl = "jdbc:postgresql://ec2-46-137-120-243.eu-west-1.compute.amazonaws.com:5432/daku93qk12ot3o?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory&user=ejzndyzesfoyqk&password=e16216b71f70ac9b098db783817afd90c45b71a0b4c117590985968f9ea31bb8";
    private Connection conn;
    private Statement s;

    // Return Statement s to main class
    public Statement getS() {
        return this.s;
    }
    public void setS(Statement s) {
        this.s = s;
    }

    // Initiates connection to database and return status
    public boolean connect()  {
        try {
            // Check if device SDK is supported
            if (android.os.Build.VERSION.SDK_INT > 9)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            // Registers the driver
            Class.forName("org.postgresql.Driver");
            conn= DriverManager.getConnection(dbUrl);
            setS(conn.createStatement());
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            String stack = Log.getStackTraceString(e);
            Log.d("DEBUGGG",stack);
            System.out.println("Could not find the database driver ");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            String stack = Log.getStackTraceString(e);
            Log.d("DEBUGGG",stack);
            System.out.println("Could not connect to the database");
            return false;
        }
    }

    public void constructDB_DATA()  {

        String sqlStr = "create table DATA (\n" +
                    "                          ID SERIAL PRIMARY KEY,\n" +
                    "                          USER_ID varchar(32),\n" +
                    "                          TIME_STAMP varchar(32),\n" +
                    "                          LATITUDE varchar(32),\n" +
                    "                          LONGITUDE varchar(32),\n" +
                    "                          NFC_ID varchar(64),\n" +
                    "                          SCAN_DATA varchar(8192),\n" +
                    "                          BOOLEAN varchar(8)\n" +

                    ");\n" +

                "insert into DATA (USER_ID,TIME_STAMP,LATITUDE,LONGITUDE,NFC_ID,SCAN_DATA) values('1','12:00'+1.0000','-1.0000','0xF213','HELLOW world!','FALSE');\n" +
                "\n";

        try {
            getS().execute (sqlStr);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void constructDB_USER()  {

        String sqlStr = "create table USERS (\n" +
                "                          ID SERIAL PRIMARY KEY,\n" +
                "                          FIRST_NAME varchar(32),\n" +
                "                          LAST_NAME varchar(32),\n" +
                "                          EMAIL varchar(32),\n" +
                "                          PASSWORD varchar(32),\n" +
                "                          ACCOUNT_TYPE varchar(32)\n" +
                ");\n" +

                "insert into USERS (FIRST_NAME,LAST_NAME,EMAIL,PASSWORD,ACCOUNT_TYPE) values('John','Doe','John@ic.com','password','ADMIN');\n" +
                "\n";

        try {
            getS().execute (sqlStr);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}