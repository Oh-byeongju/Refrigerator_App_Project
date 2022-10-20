package com.example.myapplication;

public class RecipeItem {

    String name, product;
    // Generate > Constructor
    public RecipeItem(String name) {
        this.name = name;

    }

    // Generate > Getter and Setter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Generate > toString() : 아이템을 문자열로 출력

    @Override
    public String toString() {
        return "RecipeItem{" +
                "name='" + name + '\''
                 + '\'' +
                '}';
    }
}