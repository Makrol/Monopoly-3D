package com.ggg.monopoly;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

public class Player {
    private String name;
    private Pawn pawn;
    private Integer money;

    private ApplicationScreen parent;

    private Integer id;

    private boolean inGame;

    Player(String name, int num, ApplicationScreen parent, Model pawnModel,Integer id){
        this.id = id;
        this.name = name;
        pawn = new Pawn(num,pawnModel,this);
        money = 5000;
        this.parent = parent;
        inGame = true;
    }
    void update(){

        pawn.move();
    }
    void render(ModelBatch modelBatch, Environment environment){
        pawn.render(modelBatch,environment);
    }
    void move(){
        pawn.startMove();
    }
    void move(int num){
       pawn.setMoveCounter(num);
       pawn.startMove();
    }

    public Pawn getPawn() {
        return pawn;
    }

    public String getName() {
        return name;
    }

    public Integer getMoney() {
        return money;
    }
    public void updateMoney(Integer val){
        money+=val;
        for (PlayerInfoTable p: parent.getPlayersInfoList()){
            if(p.getPlayer()==parent.getActivePlayer()){
                p.setMoneyInfo(money);
                return;
            }
        }
    }
    public void updateThisPlayerMoney(Integer val){
        money+=val;
        for (PlayerInfoTable p: parent.getPlayersInfoList()){
            if(p.getPlayer()==this){
                p.setMoneyInfo(money);
                return;
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;

    }

    public ApplicationScreen getParent() {
        return parent;
    }
}
