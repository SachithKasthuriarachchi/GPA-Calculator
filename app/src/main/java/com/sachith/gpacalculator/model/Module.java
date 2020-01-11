package com.sachith.gpacalculator.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Module implements Parcelable {

    private String code;
    private String name;
    private double credits;

    public Module(String code, String name, double credits) {

        this.code = code;
        this.name = name;
        this.credits = credits;
    }

    private Module(Parcel in) {
        code = in.readString();
        name = in.readString();
        credits = in.readDouble();
    }

    public static final Creator<Module> CREATOR = new Creator<Module>() {
        @Override
        public Module createFromParcel(Parcel in) {
            return new Module(in);
        }

        @Override
        public Module[] newArray(int size) {
            return new Module[size];
        }
    };

    public void setName(String name) {
        this.name = name;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public double getCredits() {
        return credits;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(code);
        dest.writeString(name);
        dest.writeDouble(credits);
    }
}
