package com.ggg.monopoly;

public class Field {
    public enum type {start, trip,parking,polibus,specialCard,winFields,normalField,tax,twoOfThem};
    private String title;
    private Integer price;
    private Player owner;
    private type fieldType;
    private  Integer id;
    /**
     * Konstruktor ladujacy dane do nowo utworzonego obiektu
     * @param title tytul pola
     * @param price cena pola
     * @param fieldType rodzaj pola
     * @param id numer pola
     */
    public Field(String title, Integer price, type fieldType,Integer id) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.fieldType = fieldType;
        this.owner = null;
    }

    /**
     * Zwraca tytul pola
     * @return tytul pola
     */
    public String getTitle() {
        return title;
    }

    /**
     * Zwraca cene pola
     * @return cene pola
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Zwraca wlasciciela pola
     * @return wlasciciela pola
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Zwraca rodzaj pola pola
     * @return rodzaj pola pola
     */
    public type getFieldType() {
        return fieldType;
    }

    /**
     * Ustawia wlasciciela pola
     * @param owner wlasciciel
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Zwraca id pola
     * @return id pola
     */
    public Integer getId() {
        return id;
    }
}
