package com.ggg.monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class LoadingScreen implements Screen {
    private Engine parent;
    public LoadingScreen(Engine engine){
        parent = engine;
    }
    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {


        parent.changeScreen(Engine.MENU);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
