package com.example.sixth_sense;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.example.sixth_sense.MainActivity.NAMES;
import static com.example.sixth_sense.MainActivity.VALUES;

public class DisplayMessageActivity extends AppCompatActivity {
    private static final String FILE_NAME = "example.txt";

    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        mEditText = findViewById(R.id.edit_text);
        mEditText.setText("Preset Voltage\n101.1\nPreset Voltage2\n101.2\nPreset Voltage2\n101.3\nPreset Voltage4\n101.4");

    }

    public void save(View v) {
        String text = mEditText.getText().toString();
        FileOutputStream fos = null;
        String spacer = "\n";
        try {
            fos = openFileOutput(FILE_NAME, 0);
            fos.write(text.getBytes());
            fos.write(spacer.getBytes());

            mEditText.getText().clear();
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(View v) {
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            int i = 1;
            int j = 0;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
                if (i%2 != 0) {
                    NAMES[j] = text;
                } else {
                    VALUES[j] = Double.parseDouble(text);
                    j++;
                }

                i++;
            }
            sb.append(NAMES[0]).append(VALUES[0]).append("\n");

            mEditText.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
