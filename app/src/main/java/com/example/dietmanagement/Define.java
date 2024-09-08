package com.example.dietmanagement;

public class Define {

    private static Define singletonObject;
    public String userId = "";
    public String nickName = "";
    public String pw = "";
    public int height = 0;
    public int weight = 0;
    public String gender = "";

    public static Define ins() {
        if (singletonObject == null) {
            singletonObject = new Define();
        }
        return singletonObject;
    }

}
