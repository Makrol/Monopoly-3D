package com.ggg.monopoly;


import com.badlogic.gdx.scenes.scene2d.ui.*;

import java.util.ArrayList;
import java.util.Random;

public class GameActionWindow {
    static private Window window;
    private Skin skin;
    private TextButton exitButton;

    private TextButton buyButton;

    private TextButton payFineButton;

    private ApplicationScreen applicationScreen;

    private Table table;
    private Label title;
    private Label owner;
    private Label price;

    private Label fine;

    private Label text;

    private Table buttonTab;


    private TextButton giveUpButton;

    static Integer specialCardIndex=0;

    /**
     * Konstruktor okna dialogowego
     * @param applicationScreen obiekt nadrzedny
     */
    GameActionWindow(ApplicationScreen applicationScreen){

        this.applicationScreen=applicationScreen;
        skin = Engine.getGuiSkin();
        window = new Window("",skin);
        exitButton = new TextButton("Zamknij",skin);
        buyButton = new TextButton("Kup",skin);
        payFineButton = new TextButton("Zaplac\nkare",skin);
        giveUpButton = new TextButton("Poddaj sie",skin);
        window.setVisible(false);
        initTable();
    }

    /**
     * Zwraca okno dialogowe
     * @return okno
     */
    public Window getWindow(){
        return window;
    }

    /**
     * Zwraca przycisk wyjscia z okna
     * @return przycisk
     */
    public TextButton getExitButton(){
        return exitButton;
    }

    /**
     * Zwraca przycisk kupowania
     * @return prezycisk
     */
    public TextButton getBuyButton() {
        return buyButton;
    }

    /**
     * Pokazuje okno dialogowe
     */
    static public void showWindow(){
        window.setVisible(true);
    }

    /**
     * Laduje dane do okna dialogowego
     */
    public void loadData(){
        int playerPos = applicationScreen.getActivePlayer().getPawn().getCurrentFiledIndex();
        System.out.println("dziala"+playerPos);
        Field currentField = ApplicationScreen.setOfFields.get(playerPos);
        if(currentField.getFieldType()==Field.type.parking||currentField.getFieldType()==Field.type.start){
            applicationScreen.getRandButton().setDisabled(false);
            applicationScreen.nextPlayer();
        }
        if(currentField.getFieldType()==Field.type.start){
            window.setVisible(false);
            return;
        }

        if(currentField.getFieldType()==Field.type.normalField&&currentField.getOwner()!=null){
            if(currentField.getOwner()==applicationScreen.getActivePlayer())
                return;
            title.setVisible(true);
            title.setText(currentField.getTitle());
            text.setVisible(false);
            owner.setVisible(true);
            owner.setText("wlasciciel: "+currentField.getOwner().getName());
            price.setVisible(true);
            price.setText("cena: "+currentField.getPrice()*1.8);
            buyButton.setVisible(true);
            buyButton.setDisabled(false);
            payFineButton.setDisabled(false);
            payFineButton.setVisible(true);
            exitButton.setVisible(false);
            exitButton.setDisabled(true);
            fine.setVisible(true);
            fine.setText("kara: "+currentField.getPrice());
            giveUpButton.setVisible(true);
            giveUpButton.setDisabled(false);
            return;
        }
        payFineButton.setDisabled(true);
        payFineButton.setVisible(false);
        fine.setVisible(false);
        giveUpButton.setVisible(false);
        giveUpButton.setDisabled(true);

        if(currentField.getFieldType()==Field.type.normalField|| currentField.getFieldType()==Field.type.winFields){
            owner.setVisible(true);
            title.setText(currentField.getTitle());
            if(currentField.getOwner()== null){
                owner.setText("Wlasciciel: brak");
            }
            else{
                owner.setText("Wlasciciel: "+currentField.getOwner().getName());
            }
            exitButton.setDisabled(false);
            exitButton.setVisible(true);
            giveUpButton.setVisible(true);
            giveUpButton.setDisabled(false);
            price.setText("Cena: "+currentField.getPrice());
            text.setVisible(false);
            price.setVisible(true);
            title.setVisible(true);
            buyButton.setVisible(true);
            buyButton.setDisabled(false);
        }else if (currentField.getFieldType()==Field.type.trip){
            window.setVisible(false);
            applicationScreen.getActivePlayer().updateMoney((int) (-0.5* applicationScreen.getActivePlayer().getMoney()));
            applicationScreen.getActivePlayer().move(20);

        }else if (currentField.getFieldType()==Field.type.start){
            window.setVisible(false);

        }else if(currentField.getFieldType()==Field.type.twoOfThem){
            owner.setVisible(true);
            if(currentField.getOwner()== null){
                owner.setText("Wlasciciel: brak");
            }
            else{
                owner.setText("Wlasciciel: "+currentField.getOwner().getName());
            }
            title.setVisible(true);
            title.setText(currentField.getTitle());
            price.setText("Cena: "+currentField.getPrice());
            price.setVisible(true);
            text.setVisible(false);
            exitButton.setDisabled(false);
            exitButton.setVisible(true);
            buyButton.setVisible(true);
            buyButton.setDisabled(false);

        }else if(currentField.getFieldType()==Field.type.specialCard){

            int tmp =new Random().nextInt(13);
            specialCardIndex = tmp;
            text.setVisible(true);
            text.setText(ApplicationScreen.setOfCards.get(tmp).getTitle());
            owner.setVisible(false);
            price.setVisible(false);
            title.setVisible(false);
            buyButton.setVisible(false);
            buyButton.setDisabled(true);
            exitButton.setDisabled(false);
            exitButton.setVisible(true);

            SpecialCard specialTmp = ApplicationScreen.setOfCards.get(tmp);

            if(specialTmp.getMoneyFrom()==SpecialCard.fromOp.fromBank&&specialTmp.getMoneyTo()==SpecialCard.toOp.toMe){
                applicationScreen.getActivePlayer().updateMoney(specialTmp.getMoneyChange());
            }
            else if(specialTmp.getMoneyFrom()==SpecialCard.fromOp.fromEveryone&&specialTmp.getMoneyTo()==SpecialCard.toOp.toMe) {
                applicationScreen.getActivePlayer().updateMoney(specialTmp.getMoneyChange()*(Engine.playerNumber-1));
                for (Player pl : applicationScreen.getPlayersList()) {
                    if (pl != applicationScreen.getActivePlayer())
                        pl.updateMoney(-specialTmp.getMoneyChange());
                }
            }
            else if(specialTmp.getMoneyFrom()==SpecialCard.fromOp.fromMe&&specialTmp.getMoneyTo()==SpecialCard.toOp.toBank){
                applicationScreen.getActivePlayer().updateMoney(specialTmp.getMoneyChange());
            }

        }else if(currentField.getFieldType()==Field.type.tax){
            title.setText(ApplicationScreen.setOfFields.get(playerPos).getTitle());
            owner.setVisible(false);
            price.setText("Podatek: "+currentField.getPrice());
            buyButton.setVisible(false);
            buyButton.setDisabled(true);
            exitButton.setVisible(true);
            exitButton.setDisabled(false);
            text.setVisible(false);
            title.setVisible(true);
            applicationScreen.getActivePlayer().updateMoney(-ApplicationScreen.setOfFields.get(playerPos).getPrice());
        }else if(currentField.getFieldType()==Field.type.polibus){
            int tmp =new Random().nextInt(18);
            applicationScreen.getActivePlayer().move(tmp);
            window.setVisible(false);
        }else if(currentField.getFieldType()==Field.type.parking){
            window.setVisible(false);
        }


    }

    /**
     * Konfiguruje układ elementów okna dialogowego
     */
    void initTable(){
        buttonTab = new Table(skin);
        text = new Label("to jest test",skin);
        table = new Table(skin);
        table.pad(70);
        table.padTop(100);
        title = new Label("To jetst napis",skin);
        owner = new Label("wlasciciel: ",skin);
        price = new Label("cena: ",skin);
        fine = new Label("kara: ",skin);

        buttonTab.add(buyButton);
        buttonTab.add(exitButton);
        buttonTab.add(payFineButton);

        window.add(table);

        table.row();
        table.row();
        table.add(title).padTop(20);
        table.row();
        table.add(text);
        table.row();
        table.add(owner);
        table.row();
        table.add(price);
        table.row();
        table.add(fine);
        table.row();
        table.add(buttonTab);
        table.row();
        table.add(giveUpButton).padTop(10);
    }

    /**
     * Zwraca przycisk do zaplaty graczowi
     * @return przycisk do zaplaty graczowi
     */
    public TextButton getPayFineButton() {
        return payFineButton;
    }

    /**
     * Zwraca przycisk do poddania sie
     * @return przycisk do poddania sie
     */
    public TextButton getGiveUpButton() {
        return giveUpButton;
    }

    /**
     * Pokazuje okno końca gry
     * @param player obiekt gracza
     */
    public void showWinWindow(Player player){

        title.setText("Koniec Gry! \nWygral gracz: "+player.getName());
        configWinWindow();

    }

    /**
     * Pokazuje okno końca gry
     */
    public void showWinWindow(){

        title.setText("Koniec Gry! \nWygral gracz: "+findWinner().getName());
        configWinWindow();

    }

    /**
     * Konfiguruje okno końca gry
     */
    private void configWinWindow(){
        window.setVisible(true);
        title.setVisible(true);
        text.setVisible(false);
        owner.setVisible(false);
        price.setVisible(false);
        fine.setVisible(false);
        giveUpButton.setDisabled(true);
        giveUpButton.setVisible(false);
        payFineButton.setDisabled(true);
        payFineButton.setVisible(false);
        buyButton.setDisabled(true);
        buyButton.setVisible(false);
    }

    /**
     * Poszukuje gracza który wygrał
     * @return gracz
     */
    private Player findWinner(){
        ArrayList<Integer> playerSum = new ArrayList<>();
        int tmp =0;
        for(int i=0;i<Engine.playerNumber;i++){
            tmp=0;
            for(Field f:ApplicationScreen.setOfFields){
                if(f.getOwner()==applicationScreen.getPlayersList().get(i))
                {
                   tmp+=f.getPrice();
                }
            }
            tmp+=applicationScreen.getPlayersList().get(i).getMoney();
            playerSum.add(tmp);
        }
        int index=0;
        for(int i=0;i<Engine.playerNumber;i++){
            if(playerSum.get(i)>playerSum.get(index)){
                index=i;
            }
        }
        return applicationScreen.getPlayersList().get(index);
    }
}
