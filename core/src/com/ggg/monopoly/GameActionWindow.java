package com.ggg.monopoly;


import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;

import java.util.Random;

public class GameActionWindow {
    static private Window window;
    private Skin skin;
    private TextButton exitButton;

    private TextButton buyButton;

    private ApplicationScreen applicationScreen;

    private Table table;
    private Label title;
    private Label owner;
    private Label price;
    //private TextArea text;

    private Label text;

    private Table buttonTab;


    private Slider numberOfFields;

    GameActionWindow(ApplicationScreen applicationScreen){

        this.applicationScreen=applicationScreen;
        skin = Engine.getGuiSkin();
        window = new Window("",skin);
        exitButton = new TextButton("Zamknij",skin);
        buyButton = new TextButton("Kup",skin);
       // window.add(exitButton);
        //window.align(Align.center);
        window.setVisible(false);
        //window.setDebug(true);
        initTable();
    }
    public Window getWindow(){
        return window;
    }
    public TextButton getExitButton(){
        return exitButton;
    }

    public TextButton getBuyButton() {
        return buyButton;
    }

    static public void showWindow(){
        window.setVisible(true);
    }
    public void loadData(){
        int playerPos = applicationScreen.getActivePlayer().getPawn().getCurrentFiledIndex();
        if(ApplicationScreen.setOfFields.get(playerPos).getFieldType()==Field.type.normalField||
                ApplicationScreen.setOfFields.get(playerPos).getFieldType()==Field.type.winFields
                ){
            owner.setVisible(true);
            title.setText(ApplicationScreen.setOfFields.get(playerPos).getTitle());
            if(ApplicationScreen.setOfFields.get(playerPos).getOwner()== null){
                owner.setText("Wlasciciel: brak");
            }
            else{
                owner.setText("Wlasciciel: "+ApplicationScreen.setOfFields.get(playerPos).getOwner().getName());
            }
            price.setText("Cena: "+ApplicationScreen.setOfFields.get(playerPos).getPrice());
            text.setVisible(false);
            price.setVisible(true);
            title.setVisible(true);
            buyButton.setVisible(true);
            buyButton.setDisabled(false);
        }else if (ApplicationScreen.setOfFields.get(playerPos).getFieldType()==Field.type.wycieczka){
            title.setText(ApplicationScreen.setOfFields.get(playerPos).getTitle());
            owner.setVisible(false);
            buyButton.setVisible(false);
            price.setVisible(false);
            text.setVisible(false);
        }else if (ApplicationScreen.setOfFields.get(playerPos).getFieldType()==Field.type.start){
            window.setVisible(false);
        }else if(ApplicationScreen.setOfFields.get(playerPos).getFieldType()==Field.type.twoOfThem){
            owner.setVisible(true);
            title.setText(ApplicationScreen.setOfFields.get(playerPos).getTitle());
            price.setText("Cena: "+ApplicationScreen.setOfFields.get(playerPos).getPrice());
            text.setVisible(false);
        }else if(ApplicationScreen.setOfFields.get(playerPos).getFieldType()==Field.type.specialCard){

            //int tmp =new Random().nextInt(13);
            int tmp =5;
            text.setVisible(true);
            text.setText(ApplicationScreen.setOfCards.get(tmp).getTitle());
            owner.setVisible(false);
            price.setVisible(false);
            title.setVisible(false);
            buyButton.setVisible(false);
            buyButton.setDisabled(true);

            SpecialCard specialTmp = ApplicationScreen.setOfCards.get(tmp);

            if(specialTmp.getMoneyFrom()==SpecialCard.fromOp.fromBank&&specialTmp.getMoneyTo()==SpecialCard.toOp.toMe){
                applicationScreen.getActivePlayer().updateMoney(specialTmp.getMoneyChange());
                 for(PlayerInfoTable plInf :applicationScreen.getPlayersInfoList()){
                     if(plInf.getPlayer()==applicationScreen.getActivePlayer()){
                         plInf.setMoneyInfo(applicationScreen.getActivePlayer().getMoney());
                     }
                 }
            }
            else if(specialTmp.getMoneyFrom()==SpecialCard.fromOp.fromEveryone&&specialTmp.getMoneyTo()==SpecialCard.toOp.toMe) {
                applicationScreen.getActivePlayer().updateMoney(specialTmp.getMoneyChange()*(Engine.playerNumber-1));
                for (Player pl : applicationScreen.getPlayersList()) {
                    if (pl != applicationScreen.getActivePlayer())
                        pl.updateMoney(-specialTmp.getMoneyChange());
                    for (PlayerInfoTable plInf : applicationScreen.getPlayersInfoList()) {
                        if(plInf.getPlayer()==null)
                            continue;
                        if(plInf.getPlayer()==pl)
                        plInf.setMoneyInfo(pl.getMoney());
                    }
                }
            }
            else if(specialTmp.getMoneyFrom()==SpecialCard.fromOp.fromMe&&specialTmp.getMoneyTo()==SpecialCard.toOp.toBank){
                applicationScreen.getActivePlayer().updateMoney(specialTmp.getMoneyChange());
                for(PlayerInfoTable plInf :applicationScreen.getPlayersInfoList()){
                    if(plInf.getPlayer()==applicationScreen.getActivePlayer()){
                        plInf.setMoneyInfo(applicationScreen.getActivePlayer().getMoney());
                    }
                }

            }

        }else if(ApplicationScreen.setOfFields.get(playerPos).getFieldType()==Field.type.tax){
            title.setText(ApplicationScreen.setOfFields.get(playerPos).getTitle());
            owner.setVisible(false);
            price.setText("Podatek: "+ApplicationScreen.setOfFields.get(playerPos).getPrice());
            buyButton.setVisible(false);
            buyButton.setDisabled(true);
            exitButton.setVisible(true);
            exitButton.setDisabled(false);
            text.setVisible(false);
            title.setVisible(true);
        }else if(ApplicationScreen.setOfFields.get(playerPos).getFieldType()==Field.type.polibus){

        }


    }
    void initTable(){
        buttonTab = new Table(skin);
        text = new Label("to jest test",skin);
       // text.setDisabled(true);
       // text.setSize(400,400);
        table = new Table(skin);
        table.pad(70);
        table.setDebug(true);
        title = new Label("To jetst napis",skin);
        owner = new Label("wlasciciel: ",skin);
        price = new Label("cena: ",skin);

        //numberOfFields = new Slider(1,39,)

        buttonTab.add(buyButton);
        buttonTab.add(exitButton);

        window.add(table);

        table.row();
        table.row();
        table.add(title);
        table.row();
        table.add(text);
        table.row();
        table.add(owner);
        table.row();
        table.add(price);
        table.row();
        table.add(buttonTab);
        //table.add(exitButton);
        //table.add(buyButton);



    }
}
