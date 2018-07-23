package com.firmannf.dictionarious.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by codelabs on 23/07/18.
 */

public class DictionaryModel implements Parcelable {
    private int id;
    private String word;
    private String meaning;

    public DictionaryModel(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.word);
        dest.writeString(this.meaning);
    }

    public DictionaryModel() {
    }

    protected DictionaryModel(Parcel in) {
        this.id = in.readInt();
        this.word = in.readString();
        this.meaning = in.readString();
    }

    public static final Parcelable.Creator<DictionaryModel> CREATOR = new Parcelable.Creator<DictionaryModel>() {
        @Override
        public DictionaryModel createFromParcel(Parcel source) {
            return new DictionaryModel(source);
        }

        @Override
        public DictionaryModel[] newArray(int size) {
            return new DictionaryModel[size];
        }
    };
}
