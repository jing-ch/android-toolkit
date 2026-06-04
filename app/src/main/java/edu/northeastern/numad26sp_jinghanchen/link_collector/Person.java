package edu.northeastern.numad26sp_jinghanchen.link_collector;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    private final String name;
    private final String url;

    public Person(String name, String url) {
        this.name = name;
        this.url = url;
    }

    protected Person(Parcel in) {
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getName() {
        return name;
    }
    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(url);
    }
}

