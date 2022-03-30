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

public class Pawn {
    private Model pawn;
    private ModelInstance pawnInstance;
    private static final float smallJumpValues = 3.077f;
    private static final float bigJumpValues = 4.6155f;
    private float currentJumpValues;
    private Integer currentFiledIndex;
    private  enum Direction {negativeX,positiveY,positiveX,negativeY};
    private Direction currenDirection;
    private static final float speed=3f;
    private boolean isMove;
    private float moveProgress;
    public Pawn(){
        isMove = false;
        moveProgress = 0f;
        currentJumpValues = bigJumpValues;
        currentFiledIndex = 0;
        currenDirection = Direction.negativeX;
        ModelLoader loader = new ObjLoader();
        pawn = loader.loadModel(Gdx.files.internal("pawns/pawn.obj"));
        pawnInstance = new ModelInstance(pawn);
        pawnInstance.transform.translate(16.923f,0.7f,16.923f);
       // moveToNextField();
    }
    public void render(ModelBatch bath, Environment environment){
        bath.render(pawnInstance,environment);
    }
    private void moveDataUpdate(){
        //Increment position index
        currentFiledIndex++;
        if(currentFiledIndex>39)
            currentFiledIndex=0;

        //Update move values
        if(currentFiledIndex%10==0||(currentFiledIndex+1)%10==0)
            currentJumpValues = bigJumpValues;
        else
            currentJumpValues = smallJumpValues;

        //Update move direction
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
    public void startMove(){
        isMove = true;
    }
    public void move(){
        if(isMove){
            if(moveProgress==currentJumpValues){
                moveProgress = 0f;
                moveDataUpdate();
                isMove = false;
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
}
