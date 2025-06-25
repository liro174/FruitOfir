package com.example.fruitofir;

public class FruitItem {
    private int imageResource;
    private String name;
    private String description;
    private boolean IsFavorite;

    public FruitItem(int imageResource, String name, String description,boolean favorite) {
        this.imageResource = imageResource;
        this.name = name;
        this.description = description;
        this.IsFavorite=favorite;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public boolean getIsFavorite(){return  IsFavorite;}
    public void setFavorite(boolean fav){this.IsFavorite=fav;}
}
