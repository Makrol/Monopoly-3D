package com.ggg.monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

/**
 * Klasa reprezentujaca okna informacji znajdujace sie p rogach ekranu
 */
public class PlayerInfoTable {
    private Window window;
    private static Skin skin = new Skin(Gdx.files.internal("skins/infoWindow/infoWindow.json"));
    private Label name;
    private Label money;
    private CheckBox active;
    private Player player;

    /**
     * Konstruktor ladujacy dane do nowo utworzonego obiektu
     * @param playerName nazwa gracza
     * @param player obiekt gracza
     */
    public PlayerInfoTable(String playerName,Player player){
        this.player = player;
        name = new Label(playerName,skin);
        if(player!=null)
            money = new Label(player.getMoney().toString(),skin);
        window = new Window("",skin);
        active = new CheckBox("",skin);
        active.setDisabled(true);
        window.row();
        window.add(name);
        window.add(active);
        window.row();
        window.add(money);
    }

    /**
     * Zwraca okno dialogowe
     * @return okno dialogowe
     */
    public Window getWindow(){
        return window;
    }

    /**
     * Ustawia widocznosc okna
     * @param values wartosc true-dla wlaczonego, false-dla wylaczonego
     */
    public void setVisible(Boolean values){
        window.setVisible(values);
    }

    /**
     * Ustawia aktywnosc pola
     * @param values wartosc true-dla aktywnego, false-dla wylaczonego
     */
    public void setActiveValues(Boolean values){
        active.setChecked(values);
    }

    /**
     * Zwraca aktywnosc pola
     * @return aktywnosc pola
     */
    public Boolean getActiveValues(){
        return active.isChecked();
    }

    /**
     * Ustawia informacje na temat pieniedzy
     * @param val wartosc pieniedzy
     */
    public void setMoneyInfo(Integer val){
        money.setText(val.toString());
    }

    /**
     * Zwraca obiekt gracza
     * @return obiekt gracza
     */
    public Player getPlayer() {
        return player;
    }
}
