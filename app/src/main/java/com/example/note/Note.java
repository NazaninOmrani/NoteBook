package com.example.note;


import android.os.Parcel;
import android.os.Parcelable;


public class Note implements Parcelable {
    private int id;
    private String tvTitle;
    private String tvDescription;


    public String getTextViewNote() {
        return tvTitle;
    }

    public void setTextViewNote(String textViewNOte) {
        this.tvTitle = textViewNOte;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tvTitle);
        dest.writeString(this.tvDescription);
    }

    public String getTvDescription() {
        return tvDescription;
    }

    public void setTvDescription(String tvDescription) {
        this.tvDescription = tvDescription;
    }
}
