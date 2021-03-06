package com.example.sixth_sense;


import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NFC extends AppCompatActivity implements Listener{

    public static final String TAG = "DEBUG";

    private EditText mEtMessage;
    private Button mBtWrite;
    private Button mBtRead;

    private NFCWriteFragment mNfcWriteFragment;
    private NFCReadFragment mNfcReadFragment;

    private boolean isDialogDisplayed = false;
    private boolean isWrite = false;

    private NfcAdapter mNfcAdapter;

    String csv;
    CSVWriter writer;
    List<String[]> data = new ArrayList<String[]>();
    String TimeStamp;
    String CSV_String = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        initViews();
        initNFC();

    }

    private void initViews() {

        mEtMessage = (EditText) findViewById(R.id.et_message);

    }

    private void initNFC(){

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    public void showWriteFragment(View view) {

        isWrite = true;

        mNfcWriteFragment = (NFCWriteFragment) getFragmentManager().findFragmentByTag(NFCWriteFragment.TAG);

        if (mNfcWriteFragment == null) {

            mNfcWriteFragment = NFCWriteFragment.newInstance();
        }
        mNfcWriteFragment.show(getFragmentManager(),NFCWriteFragment.TAG);

    }

    public void showReadFragment(View view) {

        mNfcReadFragment = (NFCReadFragment) getFragmentManager().findFragmentByTag(NFCReadFragment.TAG);

        if (mNfcReadFragment == null) {

            mNfcReadFragment = NFCReadFragment.newInstance();
        }
        mNfcReadFragment.show(getFragmentManager(),NFCReadFragment.TAG);

    }

    @Override
    public void onDialogDisplayed() {

        isDialogDisplayed = true;
    }

    @Override
    public void onDialogDismissed() {

        isDialogDisplayed = false;
        isWrite = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter[] nfcIntentFilter = new IntentFilter[]{techDetected,tagDetected,ndefDetected};

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        if(mNfcAdapter!= null)
            mNfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mNfcAdapter!= null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        Log.d(TAG, "onNewIntent: " + intent.getAction());

        if (tag != null) {
            Toast.makeText(this, getString(R.string.message_tag_detected), Toast.LENGTH_SHORT).show();
            Ndef ndef = Ndef.get(tag);

            if (isDialogDisplayed) {

                if (isWrite) {

                    String messageToWrite = mEtMessage.getText().toString();
                    mNfcWriteFragment = (NFCWriteFragment) getFragmentManager().findFragmentByTag(NFCWriteFragment.TAG);
                    mNfcWriteFragment.onNfcDetected(ndef, messageToWrite);

                } else {
                    int lastX = 0;

                    // Get time stamp
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy_HH:mm:ss");
                    Date date = new Date();
                    TimeStamp = formatter.format(date);
                    // Saves CSV
                    try {
                        csv = getFilesDir() + "/" + TimeStamp + ".csv";
                        writer = new CSVWriter(new FileWriter(csv));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


//                    // save messagetowrite onto local storage
//                    //String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
//                    String fileName = "AnalysisData.csv";
//                    String filePath = getFilesDir() + File.separator + fileName;
//                    File f = new File(filePath);
//                    CSVWriter writer = null;
//
//                    // File exist
//                    if (f.exists() && !f.isDirectory()) {
//                        FileWriter mFileWriter = null;
//                        try {
//                            mFileWriter = new FileWriter(filePath, true);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        writer = new CSVWriter(mFileWriter);
//                    } else {
//                        try {
//                            writer = new CSVWriter(new FileWriter(filePath));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
                    //String[] dummy = new String[1];


                    String LastMessage = null;
                    mNfcReadFragment = (NFCReadFragment) getFragmentManager().findFragmentByTag(NFCReadFragment.TAG);


                    for (int j = 0; j < 10; j++) {
                        String message = mNfcReadFragment.onNfcDetected(ndef);
                        if (message != LastMessage) {
                            LastMessage = message;

                            //dummy[0] = message;
                            if (message != "Message not read") {
                                //writer.writeNext(dummy);
                                data.add(new String[] {String.valueOf(lastX),message});
                                lastX++;

                            }

                        } else {
                            j--;
                        }
                    }
                    try {
                        writer.writeAll(data);
                        writer.close();
                        data.clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    try {
                        CSVReader csvReader = null;
                        csvReader = new CSVReader(new FileReader(csv));
                        String[] row = null;
                        System.out.println("FOLLOWING DATA HAS BEEN WRITTEN ONTO LOCAL DEVICE STORAGE");
                        while((row = csvReader.readNext()) != null) {
                            System.out.println(row[0] + " , " + row[1]);
                            CSV_String = CSV_String + row[0] + "," + row[1] + "\n";
                        }
                        csvReader.close();
                        System.out.println("ABOVE DATA HAS BEEN WRITTEN ONTO LOCAL DEVICE STORAGE");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Uploads CSV onto Database with fake data
                    try {
                        Double location = 1.2;
                        String NFC_ID = "0x111";
                        Boolean Virus = false;
                        Activity_Log_in.getUSER().create(TimeStamp, location,location,NFC_ID, CSV_String, Virus);
                        System.out.println("ABOVE DATA HAS BEEN WRITTEN ONTO DATABASE");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}