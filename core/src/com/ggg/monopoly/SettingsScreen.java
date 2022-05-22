package com.ggg.monopoly;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class SettingsScreen implements Screen,BasicFunctions {
    private Engine parent;
    private Stage stage;
    private Skin guiSkin;
    private Table table;
    private CheckBox fullScreen;
    private SelectBox<String> resolution;
    private Slider volumeLevel;
    private Boolean debugMode;
    private Slider musicLevel;
    private Window window;
    private TextButton backButton;
    public SettingsScreen(Engine engine, Skin guiSkin,Boolean debugMode){
        this.debugMode = debugMode;
        this.guiSkin=guiSkin;
        parent = engine;
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
    public void createObjects(){
        stage = new Stage(new ScreenViewport());
        table = new Table();
    }
    public void inputDataConfiguration(){
        Gdx.input.setInputProcessor(stage);
    }
    public void createButtons(){
        fullScreen = new CheckBox(" Full Screen",guiSkin);
        resolution = new SelectBox<String>(guiSkin);
        resolution.setItems("2560 x 1440","2048 x 1536","1920 x 1080","1600 x 1200","1280 x 720","1024 x 768","800 x 600");
        volumeLevel = new Slider(0f,100f,5f,false,guiSkin);
        musicLevel = new Slider(0f,100f,5f,false,guiSkin);
        window = new Window("",guiSkin);
        backButton = new TextButton("Back",guiSkin);
        debugModeAction();
    }
    public void addButtonActions()
    {
        fullScreen.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(Gdx.graphics.isFullscreen()) {
                    parent.buttonSound.play();
                    Gdx.graphics.setWindowedMode(1920, 1080);
                    resolution.setDisabled(false);
                }
                else{
                    parent.buttonSound.play();
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

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Engine.MENU);
            }
        });
    }
    private void debugModeAction(){
        table.setDebug(debugMode);
        fullScreen.setDebug(debugMode);
        resolution.setDebug(debugMode);
        volumeLevel.setDebug(debugMode);
    }

    @Override
    public void tableAndStageConfiguration() {
        table.setBackground(guiSkin.getDrawable("pale-blue"));
        table.setFillParent(true);
        stage.addActor(table);
        table.setTransform(true);
        table.add(window);
        //window.setDebug(true);

        window.row();
        window.add(new Label("",guiSkin)).pad(120,100,0,0);
        window.add(new Label("",guiSkin)).pad(120,0,0,0);
        window.add(new Label("Resolution",guiSkin)).pad(120,0,0,100);

        window.row().pad(0,0,15,0);
        window.add(fullScreen).fillX().uniformX().pad(0,100,50,0);
        window.add(new Label("",guiSkin)).pad(0,20,50,20);
        window.add(resolution).fillX().uniformX().pad(0,0,50,100);

        window.row().pad(15,0,0,0);
        window.add(new Label("Volume",guiSkin)).pad(0,100,0,0);
        window.add(new Label("",guiSkin));
        window.add(new Label("Music",guiSkin)).pad(0,0,0,100);

        window.row();
        window.add(volumeLevel).fillX().uniformX().pad(0,100,50,0);
        window.add(new Label("",guiSkin)).pad(0,20,50,20);
        window.add(musicLevel).fillX().uniformX().pad(0,0,50,100);

        window.row();
        window.add(backButton).fillX().uniformX().pad(0,100,100,0);
    }
}
