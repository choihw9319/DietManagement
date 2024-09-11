package com.example.dietmanagement;

public class Define {

    private static Define singletonObject;
    public String userId = "";
    public String nickName = "";
    public String pw = "";
    public int height = 0;
    public int weight = 0;
    public String gender = "";

    public double properweight;
    public double getProperweight() {

        if (height > 150) {
            if (height > 160){
                properweight = (height - 100) * 0.9;
            }
            else{
                properweight = (height - 100) * 0.5 + 50;
            }
        }
        else {
            properweight = height - 100;
        }

        return properweight;
    }
    public double needed_kcal;
    public double getNeeded_kcal() {
        properweight =  properweight * 30;

        return needed_kcal;
    }

    public static Define ins() {
        if (singletonObject == null) {
            singletonObject = new Define();
        }
        return singletonObject;
    }

}
