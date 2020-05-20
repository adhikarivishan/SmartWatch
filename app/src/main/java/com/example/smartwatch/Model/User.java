package com.example.smartwatch.Model;

public class User {

    private int userId;
    private String name;
    private boolean isSelected;


    public User(int userId, String name, boolean isSelected){
        this.userId = userId;
        this.name = name;
        this.isSelected = isSelected;

    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
