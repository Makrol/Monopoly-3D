package com.ggg.monopoly;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class SettingsScreen implements Screen {
    private Engine parent;
    private Stage stage;
    private Skin guiSkin;
    private Table table;
    private CheckBox fullScreen;
    private SelectBox<String> resolution;
    private Slider soundLevel;

    public SettingsScreen(Engine engine, Skin guiSkin){
        this.guiSkin=guiSkin;
        parent = engine;

        createObjects();
        createButtons();
        inputDataConfiguration();
        addButtonActions();
    }
    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
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

    }
    void createObjects(){
        stage = new Stage(new ScreenViewport());
        table = new Table();
        fullScreen = new CheckBox("Full Screen",guiSkin);
        resolution = new SelectBox<String>(guiSkin);
        resolution.setItems("2560 x 1440","2048 x 1536","1920 x 1080","1600 x 1200","1280 x 720","1024 x 768","800 x 600");
        soundLevel = new Slider(0f,100f,5f,false,guiSkin);


    }
    private void inputDataConfiguration(){
        Gdx.input.setInputProcessor(stage);
    }
    private void createButtons(){
        table.setBackground(guiSkin.getDrawable("pale-blue"));
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);
        table.add(fullScreen).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(resolution);
        table.row().pad(10, 0, 10, 0);
        table.add(soundLevel);

    }
    private void addButtonActions()
    {
        fullScreen.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(Gdx.graphics.isFullscreen()) {
                    Gdx.graphics.setWindowedMode(1920, 1080);
                    resolution.setDisabled(false);
                }
                else{
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                    resolution.setDisabled(true);
                }

            }
        });

        resolution.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(resolution.getSelected().equals("1920 x 1080")){
                    Gdx.graphics.setWindowedMode(1920,1080);
                }else if(resolution.getSelected().equals("1280 x 720")) {
                    Gdx.graphics.setWindowedMode(1280,720);
                }else if(resolution.getSelected().equals("800 x 600")) {
                    Gdx.graphics.setWindowedMode(800, 600);
                }else if(resolution.getSelected().equals("1024 x 768")) {
                    Gdx.graphics.setWindowedMode(1024, 768);
                }else if(resolution.getSelected().equals("1600 x 1200")) {
                    Gdx.graphics.setWindowedMode(1600, 1200);
                }else if(resolution.getSelected().equals("2048 x 1536")) {
                    Gdx.graphics.setWindowedMode(2048, 1536);
                }else if(resolution.getSelected().equals("2560 x 1440")) {
                    Gdx.graphics.setWindowedMode(2560, 1440);
                }
            }
        });
    }
}
