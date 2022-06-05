package com.ggg.monopoly;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

/**
 * Klasa reprezentujaca obiekt gracza
 */
public class Player {
    private String name;
    private Pawn pawn;
    private Integer money;

    private ApplicationScreen parent;

    private Integer id;

    private boolean inGame;

    /**
     * Konstruktor gracza
     * @param name nazwa
     * @param num numer pionka
     * @param parent klasa nadrzedna
     * @param pawnModel model pionka
     * @param id id
     */
    Player(String name, int num, ApplicationScreen parent, Model pawnModel,Integer id){
        this.id = id;
        this.name = name;
        pawn = new Pawn(num,pawnModel,this);
        money = 5000;
        this.parent = parent;
        inGame = true;
    }

    /**
     * Aktualizacja poruszania pionka
     */
    void update(){

        pawn.move();
    }

    /**
     * Wyświetlanie pionka
     * @param modelBatch model
     * @param environment środowisko 3D
     */
    void render(ModelBatch modelBatch, Environment environment){
        pawn.render(modelBatch,environment);
    }

    /**
     * Rozpoczęcie poruszania
     * @param num ilość pól
     */
    void move(int num){
       pawn.setMoveCounter(num);
       pawn.startMove();
    }

    /**
     * Zwraca pionek
     * @return pionek
     */
    public Pawn getPawn() {
        return pawn;
    }

    /**
     * Zwraca imie
     * @return imie
     */
    public String getName() {
        return name;
    }

    /**
     * Zwraca pieniadze
     * @return pieniadze
     */
    public Integer getMoney() {
        return money;
    }

    /**
     * Zmienia o wartość stan konta aktywnego gracza.
     * @param val wartość
     */
    public void updateMoney(Integer val){
        money+=val;
        for (PlayerInfoTable p: parent.getPlayersInfoList()){
            if(p.getPlayer()==parent.getActivePlayer()){
                p.setMoneyInfo(money);
                return;
            }
        }
    }
    /**
     * Zmienia o wartość stan gracza.
     * @param val wartość
     */
    public void updateThisPlayerMoney(Integer val){
        money+=val;
        for (PlayerInfoTable p: parent.getPlayersInfoList()){
            if(p.getPlayer()==this){
                p.setMoneyInfo(money);
                return;
            }
        }
    }

    /**
     * Zwraca id
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sprawdza czy gracz nie odpadł z gry
     * @return false jeśli odpadł
     */
    public boolean isInGame() {
        return inGame;
    }

    /**
     * Ustala czy glacz jest w grze
     * @param inGame wartość
     */
    public void setInGame(boolean inGame) {
        this.inGame = inGame;

    }
}
