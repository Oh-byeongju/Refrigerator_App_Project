package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class Recommend_recipe {
    ArrayList<String> f_name = new ArrayList<String>();
    public ArrayList<String> Return_recipe(ArrayList<String> p_name){
        if((p_name.contains("김치") && p_name.contains("돼지고기") && p_name.contains("대파"))){
            f_name.add("김치찌개");
        }
        if((p_name.contains("김치") && p_name.contains("양파") && p_name.contains("계란"))){
            f_name.add("김치볶음밥");
        }
        if((p_name.contains("오이") && p_name.contains("고추") && p_name.contains("양파"))){
            f_name.add("오이무침");
        }
        if((p_name.contains("돼지고기") && p_name.contains("양파") && p_name.contains("대파") && p_name.contains("당근"))){
            f_name.add("두루치기");
        }
        if((p_name.contains("떡") && p_name.contains("고추장") && p_name.contains("대파"))) {
            f_name.add("떡볶이");
        }
        if((p_name.contains("닭고기") && p_name.contains("감자") && p_name.contains("양파") && p_name.contains("대파"))) {
            f_name.add("닭볶음탕");
        }
        if((p_name.contains("두부") && p_name.contains("대파") && p_name.contains("양파"))) {
            f_name.add("두부조림");
        }
        if((p_name.contains("소고기") && p_name.contains("미역"))) {
            f_name.add("미역국");
        }
        if((p_name.contains("된장") && p_name.contains("애호박") && p_name.contains("양파") && p_name.contains("대파"))) {
            f_name.add("된장찌개");
        }
        if((p_name.contains("계란") && p_name.contains("마늘") && p_name.contains("간장"))) {
            f_name.add("계란장조림");
        }
        if((p_name.contains("새우") && p_name.contains("당근") && p_name.contains("양파"))) {
            f_name.add("새우볶음밥");
        }

        return f_name;
    }
}
