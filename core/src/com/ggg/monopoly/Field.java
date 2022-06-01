package com.ggg.monopoly;

public class Field {
    public enum type {start, trip,parking,polibus,specialCard,winFields,normalField,tax,twoOfThem};
    private String title;
    private Integer price;
    private Player owner;
    private type fieldType;

    private  Integer id;
    public Field(String title, Integer price, type fieldType,Integer id) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.fieldType = fieldType;
        this.owner = null;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPrice() {
        return price;
    }

    public Player getOwner() {
        return owner;
    }

    public type getFieldType() {
        return fieldType;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }
}
