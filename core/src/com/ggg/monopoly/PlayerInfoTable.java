package com.ggg.monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;


public class PlayerInfoTable {
    private Window window;
    private static Skin skin = new Skin(Gdx.files.internal("skins/infoWindow/infoWindow.json"));
    private Label name;
    private Label money;
    private CheckBox active;

    private Player player;
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

       // window.setDebug(true);
    }
    public Window getWindow(){
        return window;
    }
    public void setVisible(Boolean values){
        window.setVisible(values);
    }
    public void setActiveValues(Boolean values){
        active.setChecked(values);
    }
    public Boolean getActiveValues(){
        return active.isChecked();
    }

    public void setMoneyInfo(Integer val){
        money.setText(val.toString());
    }

    public Player getPlayer() {
        return player;
    }
}
