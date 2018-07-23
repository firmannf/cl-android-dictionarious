package com.firmannf.dictionarious.screen.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.firmannf.dictionarious.R;
import com.firmannf.dictionarious.data.DictionaryModel;
import com.firmannf.dictionarious.data.source.local.DatabaseContract;
import com.firmannf.dictionarious.data.source.local.DictionaryHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements DictionaryAdapter.DictionaryItemListener {

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_radiogroup_dictionary)
    RadioGroup radioGroupDictionary;
    @BindView(R.id.main_radiobutton_entoid)
    AppCompatRadioButton radioButtonEnToId;
    @BindView(R.id.main_radiobutton_idtoen)
    AppCompatRadioButton radioButtonIdToEn;
    @BindView(R.id.main_searchview_word)
    SearchView searchViewWord;
    @BindView(R.id.main_recyclerview_word)
    RecyclerView recyclerView;

    DictionaryAdapter dictionaryAdapter;
    DictionaryHelper dictionaryHelper;

    String tableName = DatabaseContract.TABLE_ENGLISH_NAME;
    ArrayList<DictionaryModel> dictionaries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupToolbar();
        setupRecyclerView();
        dictionaryHelper = new DictionaryHelper(this);

        radioGroupDictionary.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                dictionaryAdapter.replaceData(new ArrayList<DictionaryModel>());
                searchViewWord.setQuery("", false);
                if (radioGroup.getCheckedRadioButtonId() == R.id.main_radiobutton_entoid) {
                    tableName = DatabaseContract.TABLE_ENGLISH_NAME;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.main_radiobutton_idtoen) {
                    tableName = DatabaseContract.TABLE_INDONESIA_NAME;
                }
            }
        });

        searchViewWord.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dictionaryHelper.open();
                dictionaries.clear();
                dictionaries = dictionaryHelper.getDataByWord(query, tableName);
                dictionaryHelper.close();
                dictionaryAdapter.replaceData(dictionaries);
                searchViewWord.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    dictionaryAdapter.replaceData(new ArrayList<DictionaryModel>());
                    return true;
                }

                dictionaryHelper.open();
                dictionaries.clear();
                dictionaries = dictionaryHelper.getDataByWord(newText, tableName);
                dictionaryHelper.close();
                dictionaryAdapter.replaceData(dictionaries);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onDictionaryClick(DictionaryModel dictionary) {

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        recyclerView.setHasFixedSize(true);

        dictionaryAdapter = new DictionaryAdapter(this, new ArrayList<DictionaryModel>(), this);
        recyclerView.setAdapter(dictionaryAdapter);
    }
}
