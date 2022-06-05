package com.ggg.monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.concurrent.TimeUnit;

/**
 * KLasa reprezentujaca obiekt pionka
 */
public class Pawn {
    private static Vector3 startPos1 = new Vector3(18.5f,0.7f,18.5f);
    private static Vector3 startPos2 = new Vector3(17.5f,0.7f,17.5f);
    private static Vector3 startPos3 = new Vector3(16.5f,0.7f,16.5f);
    private static Vector3 startPos4 = new Vector3(15.5f,0.7f,15.5f);


    private Model pawn;
    private ModelInstance pawnInstance;
    private final float currentBigJump;
    private static final float smallJumpValues = 3.077f;
    private static final float bigJumpValues1 = 6.1925f;
    private static final float bigJumpValues2 = 5.1925f;
    private static final float bigJumpValues3 = 4.1925f;
    private static final float bigJumpValues4 = 3.1925f;
    private float currentJumpValues;
    private Integer currentFiledIndex;
    private  enum Direction {negativeX,positiveY,positiveX,negativeY};
    private Direction currenDirection;
    private static final float speed=12f;
    private boolean isMove;
    private float moveProgress;
    private Integer moveCounter;

    private boolean visable;

    private Player parent;

    /**
     * Konstruktor ladujacy dane do nowo utworzonego obiektu
     * @param num numer pionka
     * @param pawnModel model pionka
     * @param parent klasa nadrzedna
     */
    public Pawn(int num,Model pawnModel,Player parent){
        this.parent = parent;
        visable =true;
        this.pawn = pawnModel;
        moveCounter = 0;
        switch (num){
            case 1:
                currentBigJump=bigJumpValues1;
                break;
            case 2:
                currentBigJump=bigJumpValues2;
                break;
            case 3:
                currentBigJump=bigJumpValues3;
                break;
            case 4:
                currentBigJump=bigJumpValues4;
                break;
            default:
                currentBigJump=0f;
        }
        isMove = false;
        moveProgress = 0f;
        currentJumpValues = currentBigJump;
        currentFiledIndex = 0;
        currenDirection = Direction.negativeX;
        pawnInstance = new ModelInstance(pawn);
        if(num == 1){
            pawnInstance.transform.translate(startPos1);
        }else if(num == 2){
            pawnInstance.transform.translate(startPos2);
        }
        else if(num == 3){
            pawnInstance.transform.translate(startPos3);
        }
        else if(num == 4){
            pawnInstance.transform.translate(startPos4);
        }
    }
    public void render(ModelBatch bath, Environment environment){
        if(visable)
            bath.render(pawnInstance,environment);
    }
    private void moveDataUpdate(){
        currentFiledIndex++;
        if(currentFiledIndex>39)
        {
            currentFiledIndex=0;
            parent.updateMoney(200);
        }
        if(currentFiledIndex%10==0||(currentFiledIndex+1)%10==0)
            currentJumpValues = currentBigJump;
        else
            currentJumpValues = smallJumpValues;
        if(currentFiledIndex%10==0){
            switch (currenDirection){
                case positiveY:
                    currenDirection = Direction.positiveX;
                    break;
                case positiveX:
                    currenDirection = Direction.negativeY;
                    break;
                case negativeY:
                    currenDirection = Direction.negativeX;
                    break;
                case negativeX:
                    currenDirection = Direction.positiveY;
                    break;
            }
        }
    }

    /**
     * Funkcja startujaca ruch
     */
    public void startMove(){
        isMove = true;
    }

    /**
     * Funkcja poruszajaca pionka
     */
    public void move(){
        if(isMove){
            if(moveProgress==currentJumpValues){
                moveProgress = 0f;
                moveDataUpdate();
                moveCounter--;
                if(moveCounter==0) {
                    isMove = false;
                    GameActionWindow.showWindow();
                    ApplicationScreen.actionWindow.loadData();
                    return;
                }
            }else{
                float deltaTime = Gdx.graphics.getDeltaTime();
                float moveValues = deltaTime*speed;
                moveProgress += moveValues;
                if(moveProgress>currentJumpValues){
                    moveProgress = currentJumpValues;
                    moveValues = currentJumpValues - moveProgress;
                }
                switch (currenDirection){
                    case negativeX:
                        pawnInstance.transform.translate((float)-moveValues,0,0);
                        break;
                    case negativeY:
                        pawnInstance.transform.translate(0,0,moveValues);
                        break;
                    case positiveX:
                        pawnInstance.transform.translate(moveValues,0,0);
                        break;
                    case positiveY:
                        pawnInstance.transform.translate(0,0,-moveValues);
                        break;
                }
            }
        }
    }

    /**
     * Ustawia ilosc pozostalych ruchow
     * @param moveCounter licznik ruchow
     */
    public void setMoveCounter(Integer moveCounter) {
        this.moveCounter = moveCounter;
    }

    /**
     * Indeks pola na którym stoji gracz
     * @return indeks
     */
    public Integer getCurrentFiledIndex() {
        return currentFiledIndex;
    }

    /**
     * Ustawienie widoczności pionka
     * @param val wartość
     */
    public void setPawnVisable(boolean val){
        visable=val;
    }
}
