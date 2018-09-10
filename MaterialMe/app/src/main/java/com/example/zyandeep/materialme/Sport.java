package com.example.zyandeep.materialme;

import android.os.Parcel;
import android.os.Parcelable;

public class Sport implements Parcelable {

    //Member variables representing the title and information about the sport
    private String title;
    private String info;
    private String detail;
    private final int imageResource;

    Sport(String title, String info, int imageResource, String detail) {
        this.title = title;
        this.info = info;
        this.imageResource = imageResource;
        this.detail = detail;
    }

    String getTitle() {
        return title;
    }

    String getInfo() {
        return info;
    }

    int getImageResource() {
        return this.imageResource;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.info);
        dest.writeString(this.detail);
        dest.writeInt(this.imageResource);
    }

    protected Sport(Parcel in) {
        this.title = in.readString();
        this.info = in.readString();
        this.detail = in.readString();
        this.imageResource = in.readInt();
    }

    public static final Parcelable.Creator<Sport> CREATOR = new Parcelable.Creator<Sport>() {
        @Override
        public Sport createFromParcel(Parcel source) {
            return new Sport(source);
        }

        @Override
        public Sport[] newArray(int size) {
            return new Sport[size];
        }
    };
}