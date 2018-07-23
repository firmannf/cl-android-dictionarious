package com.firmannf.dictionarious.screen.preload;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.firmannf.dictionarious.R;
import com.firmannf.dictionarious.data.DictionaryModel;
import com.firmannf.dictionarious.data.source.local.DatabaseContract;
import com.firmannf.dictionarious.data.source.local.DictionaryHelper;
import com.firmannf.dictionarious.screen.main.MainActivity;
import com.firmannf.dictionarious.util.AppPreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreloadActivity extends AppCompatActivity {

    @BindView(R.id.preload_progressbar_loading)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preload);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(
                    this,
                    R.color.white));
        }

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        DictionaryHelper dictionaryHelper;
        AppPreference appPreference;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            dictionaryHelper = new DictionaryHelper(PreloadActivity.this);
            appPreference = new AppPreference(PreloadActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Boolean firstRun = appPreference.getFirstRun();
            if (firstRun) {
                ArrayList<DictionaryModel> englishDictionaries = preLoadRaw(R.raw.english_indonesia);
                ArrayList<DictionaryModel> indonesiaDictionaries = preLoadRaw(R.raw.indonesia_english);

                publishProgress((int) progress);

                dictionaryHelper.open();
                dictionaryHelper.beginTransaction();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / (englishDictionaries.size() + indonesiaDictionaries.size());

                try {
                    for (DictionaryModel model : englishDictionaries) {
                        dictionaryHelper.insertTransaction(model, DatabaseContract.TABLE_ENGLISH_NAME);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    for (DictionaryModel model : indonesiaDictionaries) {
                        dictionaryHelper.insertTransaction(model, DatabaseContract.TABLE_INDONESIA_NAME);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    dictionaryHelper.setTransactionSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                dictionaryHelper.endTransaction();
                dictionaryHelper.close();
                appPreference.setFirstRun(false);
                publishProgress((int) maxprogress);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            Log.d("BOOM", values[0] + " ");
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent intent = new Intent(PreloadActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public ArrayList<DictionaryModel> preLoadRaw(int data) {
        ArrayList<DictionaryModel> dictionaries = new ArrayList<>();
        String line;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream rawDictionary = res.openRawResource(data);
            reader = new BufferedReader(new InputStreamReader(rawDictionary));
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");
                DictionaryModel dictionary = new DictionaryModel(splitstr[0], splitstr[1]);
                dictionaries.add(dictionary);
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dictionaries;
    }
}
