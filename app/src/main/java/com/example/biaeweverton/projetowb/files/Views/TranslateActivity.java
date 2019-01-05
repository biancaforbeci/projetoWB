package com.example.biaeweverton.projetowb.files.Views;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.biaeweverton.projetowb.R;
import com.ibm.watson.developer_cloud.language_translator.v3.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslationResult;
import com.ibm.watson.developer_cloud.language_translator.v3.util.Language;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Model;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.GetModelOptions;

import java.util.Timer;
import java.util.TimerTask;


public class TranslateActivity extends AppCompatActivity {
    private TextView input;
    private TextView translatedText;
    private LanguageTranslator translationService;
    private String selectedTargetLanguage = Language.ENGLISH;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        translationService = initLanguageTranslatorService();
        input = findViewById(R.id.inputText);
        translatedText = findViewById(R.id.translated);
        final BootstrapButton clean = (BootstrapButton) findViewById(R.id.btnClean);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                timer = new Timer();
                final long DELAY = 500; //delay before changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            if(!input.getText().toString().isEmpty()) {
                                callTranslateClass();  //taking words if input isn't empty and translation
                            }
                        }catch (Exception e){
                            translatedText.setText(input.getText().toString());
                        }
                    }
                },1000);
            }
        });

        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translatedText.setText("");
            }
        });


    }

    private void callTranslateClass(){
        new TranslationTask().execute(input.getText().toString());
    }

    private void showTranslation(final String translation) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                translatedText.setText(translation);
            }
        });
    }

    private LanguageTranslator initLanguageTranslatorService() {
        LanguageTranslator service = new LanguageTranslator("2018-05-01");
        String username = getString(R.string.language_translator_username);
        String password = getString(R.string.language_translator_password);
        service.setUsernameAndPassword(username, password);
        service.setEndPoint(getString(R.string.language_translator_url));
        return service;
    }

    private abstract class EmptyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        // assumes text is initially empty
        private boolean isEmpty = true;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                isEmpty = true;
                onEmpty(true);
            } else if (isEmpty) {
                isEmpty = false;
                onEmpty(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        public abstract void onEmpty(boolean empty);
    }

    private class TranslationTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            TranslateOptions translateOptions = new TranslateOptions.Builder()
                    .addText(params[0])
                    .source(Language.PORTUGUESE)
                    .target(selectedTargetLanguage)
                    .build();
            TranslationResult result = translationService.translate(translateOptions).execute();
            String firstTranslation = result.getTranslations().get(0).getTranslationOutput();
            showTranslation(firstTranslation);
            return "Did translate";
        }
    }

}
