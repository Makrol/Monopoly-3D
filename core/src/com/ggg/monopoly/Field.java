package com.ggg.monopoly;

public class Field {
    public enum type {start,wycieczka,parking,polibus,specialCard,winFields,normalField,tax,twoOfThem};
    private String title;
    private Integer price;
    private Player owner;
    private type fieldType;

    public Field(String title, Integer price, type fieldType) {
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
}
