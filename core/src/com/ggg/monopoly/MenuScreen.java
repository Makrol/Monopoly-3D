package com.ggg.monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

class MenuScreen implements Screen,BasicFunctions {
    private Engine parent;
    private Stage stage;
    private Table table;
    private TextButton newGameButton;
    private TextButton settingsButton;
    private TextButton exitButton;
    private Skin guiSkin;

    public MenuScreen(Engine engine, Skin guiSkin){
        parent = engine;
        this.guiSkin=guiSkin;
        createObjects();
        createButtons();
        tableAndStageConfiguration();
        addButtonActions();

    }
    @Override
    public void show() {
        inputDataConfiguration();
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
    @Override
    public void createButtons()
    {
        newGameButton = new TextButton("New Game", guiSkin);
        settingsButton = new TextButton("Settings", guiSkin);
        exitButton = new TextButton("Exit", guiSkin);
    }
    @Override
    public void addButtonActions(){
        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                    parent.buttonSound.play();
                    parent.changeScreen(Engine.NEWGAME);

            }
        });

        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.buttonSound.play();
                parent.changeScreen(Engine.SETTINGS);
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.buttonSound.play();
                Gdx.app.exit();
            }
        });

    }

    @Override
    public void createObjects() {
        stage = new Stage(new ScreenViewport());
        table = new Table();
    }
    @Override
    public void inputDataConfiguration() {
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void tableAndStageConfiguration(){
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);
        table.setBackground(guiSkin.getDrawable("pale-blue"));
        table.add(newGameButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(settingsButton).fillX().uniformX();
        table.row();
        table.add(exitButton).fillX().uniformX();
    }
}
