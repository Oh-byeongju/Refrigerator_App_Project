package com.example.myapplication;

public class ProductIcon {

    int p_id;

    public int p_icon(String name){
        if(name.contains("양파"))
            p_id = R.raw.onion;
        else if(name.contains("고기"))
            p_id = R.raw.meat;
        else if(name.contains("콜라"))
            p_id = R.raw.coke;
        else if(name.contains("김치"))
            p_id = R.raw.kimchi;
        else if(name.contains("마늘"))
            p_id = R.raw.garlic;
        else if(name.contains("두부"))
            p_id = R.raw.bean_curd;
        else if(name.contains("바게트"))
            p_id = R.raw.baguette;
        else if(name.contains("버터"))
            p_id = R.raw.butter;
        else if(name.contains("식빵"))
            p_id = R.raw.bread;
        else if(name.contains("식용유") || name.contains("요리유"))
            p_id = R.raw.cooking_oil;
        else if(name.contains("삼다수") || name.contains("물"))
            p_id = R.raw.water;
        else if(name.contains("소금"))
            p_id = R.raw.salt;
        else if(name.contains("간장"))
            p_id = R.raw.soybean;
        else if(name.contains("감자"))
            p_id = R.raw.photato;
        else if(name.contains("대파") || name.contains("쪽파") || name.contains("실파"))
            p_id = R.raw.green_onion;
        else if(name.contains("사과"))
            p_id = R.raw.apple;
        else if(name.contains("레몬"))
            p_id = R.raw.lemon;
        else if(name.contains("당근"))
            p_id = R.raw.carrot;
        else if(name.contains("양배추"))
            p_id = R.raw.cabbage;
        else if(name.contains("새우"))
            p_id = R.raw.shrimp;
        else if(name.contains("만두"))
            p_id = R.raw.mandoo;
        else if(name.contains("오징어"))
            p_id = R.raw.squid;
        else if(name.contains("바나나"))
            p_id = R.raw.banana;
        else if(name.contains("계란"))
            p_id = R.raw.egg;
        else if(name.contains("아이스크림") || name.contains("메로나") || name.contains("쿠엔크") || name.contains("콘"))
            p_id = R.raw.icecream;

        else
            p_id = R.mipmap.ic_ref_logo_round;

        return p_id;
    }
}
