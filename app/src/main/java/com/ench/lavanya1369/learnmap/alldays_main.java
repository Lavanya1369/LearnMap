package com.ench.lavanya1369.learnmap;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by enchanter on 13/6/17.
 */

public class alldays_main implements Parcelable {


    String lat_andro;
    String long_andro;

    public String getLat_andro() {
        return lat_andro;
    }

    public void setLat_andro(String lat_andro) {
        this.lat_andro = lat_andro;
    }

    public String getLong_andro() {
        return long_andro;
    }

    public void setLong_andro(String long_andro) {
        this.long_andro = long_andro;
    }

    public static Creator<alldays_main> getCREATOR() {
        return CREATOR;
    }




    protected alldays_main(Parcel in) {
        lat_andro = in.readString();
        long_andro = in.readString();
    }

    public static final Creator<alldays_main> CREATOR = new Creator<alldays_main>() {
        @Override
        public alldays_main createFromParcel(Parcel in) {
            return new alldays_main(in);
        }

        @Override
        public alldays_main[] newArray(int size) {
            return new alldays_main[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lat_andro);
        dest.writeString(long_andro);
    }
}
