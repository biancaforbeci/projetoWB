package com.example.biaeweverton.projetowb.files.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.biaeweverton.projetowb.R;
import com.example.biaeweverton.projetowb.files.Controllers.TranslateController;

public class TranslateActivity extends AppCompatActivity {
    private EditText txtTyped;
    private EditText translated;
    private Spinner spinner;
    private TranslateController translateController = new TranslateController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        txtTyped = findViewById(R.id.txtTyped);
        translated= findViewById(R.id.translation);

        //set array to spinner
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,R.layout.activity_translate,getResources().getStringArray(R.array.languages));
        spinner.setAdapter(spinnerAdapter);


    }

    public void speak(View view) {
    }

    public void translate(View view) {
        int position = spinner.getSelectedItemPosition();

        //translateController.findLanguageSelected(position);
    }
}
