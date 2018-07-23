package com.firmannf.dictionarious.screen.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firmannf.dictionarious.R;
import com.firmannf.dictionarious.data.DictionaryModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by codelabs on 16/07/18.
 */

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.ViewHolder> {
    private Context context;
    private List<DictionaryModel> dictionaries;
    private DictionaryAdapter.DictionaryItemListener dictionaryItemListener;

    public DictionaryAdapter(Context context, List<DictionaryModel> dictionaries, DictionaryItemListener dictionaryItemListener) {
        this.context = context;
        this.dictionaries = dictionaries;
        this.dictionaryItemListener = dictionaryItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_dictionary, null);
        return new DictionaryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DictionaryModel dictionary = dictionaries.get(position);
        holder.textViewWord.setText(dictionary.getWord());
        holder.textViewMeaning.setText(dictionary.getMeaning());
    }

    @Override
    public int getItemCount() {
        return dictionaries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.dictionary_textview_word)
        TextView textViewWord;
        @BindView(R.id.dictionary_textview_meaning)
        TextView textViewMeaning;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            dictionaryItemListener.onDictionaryClick(dictionaries.get(getAdapterPosition()));
        }
    }

    public void replaceData(List<DictionaryModel> dictionaries) {
        this.dictionaries = dictionaries;
        notifyDataSetChanged();
    }

    public interface DictionaryItemListener {
        void onDictionaryClick(DictionaryModel dictionary);
    }
}
