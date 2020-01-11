package com.sachith.gpacalculator.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable {

    private double credits;
    private double results;
    private String name;

    public Result(double credits, double results, String name) {

        this.credits = credits;
        this.results = results;
        this.name = name;

    }

    private Result(Parcel in) {
        this.credits = in.readDouble();
        this.results = in.readDouble();
        this.name = in.readString();
    }

    public static final Creator<Result> CREATOR =
            new Creator<Result>() {

                public Result createFromParcel(Parcel in) {
                    return new Result(in);
                }

                public Result[] newArray(int size) {
                    return new Result[size];
                }
            };

    public void setCredits(double credits) {
        this.credits = credits;
    }

    public double getCredits() {
        return credits;
    }

    public double getResults() {
        return results;
    }

    public void setResults(double result) {
        this.results = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(credits);
        dest.writeDouble(results);
        dest.writeString(name);
    }
}
