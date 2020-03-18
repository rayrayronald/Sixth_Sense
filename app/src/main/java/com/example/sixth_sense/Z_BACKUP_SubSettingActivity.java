package com.example.sixth_sense;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/*import static com.example.sixth_sense.Z_BACKUP_MainActivity.VALUE_1_1;
import static com.example.sixth_sense.Z_BACKUP_MainActivity.NAME_1_1;*/
import static com.example.sixth_sense.Z_BACKUP_MainActivity.NAMES;
import static com.example.sixth_sense.Z_BACKUP_MainActivity.VALUES;

public class Z_BACKUP_SubSettingActivity extends AppCompatActivity {



    //private TextView edittext1 = findViewById(R.id.editText2);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_setting);

        TextView edittext1 = findViewById(R.id.editText2);
        TextView textView1 = findViewById(R.id.textView2);
        textView1.setText(NAMES[0]);
        edittext1.setText(String.valueOf(VALUES[0]));


    }

    /** Called when the user taps the UPDATE button */
    public void update(View view) {
        TextView edittext1 = findViewById(R.id.editText2);
        VALUES[0] = Double.parseDouble(edittext1.getText().toString());
    }
}
