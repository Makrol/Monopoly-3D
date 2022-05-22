package com.ggg.monopoly;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

public class Player {
    private String name;
    private Pawn pawn;
    private Integer money;

    Player(String name,int num){
        this.name = name;
        pawn = new Pawn(num);
        money = 5000;
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
    }
}
