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
import com.example.biaeweverton.projetowb.R;
import com.ibm.watson.developer_cloud.language_translator.v3.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.developer_cloud.language_translator.v3.model.TranslationResult;
import com.ibm.watson.developer_cloud.language_translator.v3.util.Language;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Model;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.GetModelOptions;


public class TranslateActivity extends AppCompatActivity {
    private TextView input;
    private Button translate;
    private Button clean;
    private Spinner spinner;
    private TextView translatedText;
    private LanguageTranslator translationService;
    private String selectedTargetLanguage = Language.ENGLISH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        translationService = initLanguageTranslatorService();
        input = findViewById(R.id.inputText);
        translate = findViewById(R.id.translate);
        translatedText = findViewById(R.id.translated);
        spinner = findViewById(R.id.spinner);
        clean= findViewById(R.id.btnClean);

        input.addTextChangedListener(new EmptyTextWatcher() {
            @Override
            public void onEmpty(boolean empty) {
                if (empty) {
                    translate.setEnabled(false);
                    clean.setEnabled(false);
                } else {
                    translate.setEnabled(true);
                    clean.setEnabled(true);
                }
            }
        });

        translate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int posicao = spinner.getSelectedItemPosition();
                languague(posicao);
                new TranslationTask().execute(input.getText().toString());
            }
        });
    }

    private void languague(int position){

        switch (position){
            case 0:
                selectedTargetLanguage=Language.ENGLISH;
                break;
            case 1:
                selectedTargetLanguage=Language.SPANISH;
                break;
            case 3:
                selectedTargetLanguage=Language.RUSSIAN;
                break;
            case 4:
                selectedTargetLanguage=Language.FRENCH;
                break;
            case 5:
                selectedTargetLanguage=Language.ITALIAN;
                break;
        }
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

    public void clean(View view) {
        translatedText.setText("");
    }

    private abstract class EmptyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
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
        public void afterTextChanged(Editable s) {}

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
