package com.example.animationmaker;

public class StudentModel {
    private String name , mobile_no;
    private  int image_res;
    public StudentModel(String name , String mobile_no , int image_res) {
        this.name = name;
        this.mobile_no = mobile_no;
        this.image_res = image_res;
    }

    public int getImage_res() {
        return image_res;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public String getName() {
        return name;
    }
}
