package com.example.dietmanagement;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class CostomListView implements Parcelable {

    private String foodName;
    private float foodKcal;
    private Uri imageUri;

    public CostomListView(String foodName, float foodKcal, Uri imageUri) {
        this.foodName = foodName;
        this.foodKcal = foodKcal;
        this.imageUri = imageUri;
    }

    protected CostomListView(Parcel in) {
        foodName = in.readString();
        foodKcal = in.readFloat();
        imageUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<CostomListView> CREATOR = new Creator<CostomListView>() {
        @Override
        public CostomListView createFromParcel(Parcel in) {
            return new CostomListView(in);
        }

        @Override
        public CostomListView[] newArray(int size) {
            return new CostomListView[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(foodName);
        dest.writeFloat(foodKcal);
        dest.writeParcelable(imageUri, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getFood_Name() {
        return foodName;
    }

    public float getFood_Kcal() {
        return foodKcal;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}