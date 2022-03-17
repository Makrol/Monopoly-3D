package com.ggg.monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen implements Screen {
    private Engine parent;
    private Stage stage;

    private TextButton newGameButton;
    private TextButton settingsButton;
    private TextButton exitButton;
    private Skin guiSkin;

    public MenuScreen(Engine engine, Skin guiSkin){
        parent = engine;
        this.guiSkin=guiSkin;
        stage = new Stage(new ScreenViewport());
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        createButtons();
        addButtonsActions();

    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
    private void update(){

    }
    private void createButtons()
    {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);

        stage.addActor(table);

        table.setBackground(guiSkin.getDrawable("pale-blue"));
        newGameButton = new TextButton("New Game", guiSkin);
        settingsButton = new TextButton("Settings", guiSkin);
        exitButton = new TextButton("Exit", guiSkin);



        table.add(newGameButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(settingsButton).fillX().uniformX();
        table.row();
        table.add(exitButton).fillX().uniformX();
    }
    private void addButtonsActions(){
        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Engine.APPLICATION);
            }
        });

        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Engine.SETTINGS);
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

    }

}
