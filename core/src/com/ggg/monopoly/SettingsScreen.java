package com.ggg.monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class SettingsScreen implements Screen {
    private Engine parent;
    public SettingsScreen(Engine engine){
        parent = engine;
    }
    @Override
    public void show() {
        System.out.println("show settings");

    }

    @Override
    public void render(float delta) {
        System.out.println("render settings");
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
