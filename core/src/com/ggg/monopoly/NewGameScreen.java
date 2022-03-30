package com.ggg.monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.*;

public class NewGameScreen implements Screen,BasicFunctions {
    private Engine parent;
    private Stage stage;
    private Skin guiSkin;
    private Table table;
    private Label title;
    private CheckBox twoPlayers;
    private CheckBox threePlayers;
    private CheckBox fourPlayers;
    private Label playersNumber;
    private Label playersNames;
    private TextField firstPlayerName;
    private TextField secondPlayerName;
    private TextField thirdPlayerName;
    private TextField fourthPlayerName;
    private TextButton startButton;
    private TextButton backButton;

    private Window window;
    public NewGameScreen(Engine engine,Skin guiSkin){
        this.guiSkin = guiSkin;
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

    @Override
    public void createObjects() {
        window = new Window("Napis",guiSkin);
        stage = new Stage(new ScreenViewport());
        table = new Table(guiSkin);
    }

    @Override
    public void createButtons() {
        window = new Window("",guiSkin);
        twoPlayers = new CheckBox("2 players",guiSkin);
        threePlayers = new CheckBox("3 players",guiSkin);
        fourPlayers = new CheckBox("4 players",guiSkin);
        playersNumber = new Label("Number of players: ",guiSkin);
        playersNames = new Label("Player names: ",guiSkin);
        firstPlayerName = new TextField("",guiSkin);
        firstPlayerName.setAlignment(Align.center);
        secondPlayerName = new TextField("",guiSkin);
        secondPlayerName.setAlignment(Align.center);
        thirdPlayerName = new TextField("",guiSkin);
        thirdPlayerName.setAlignment(Align.center);
        fourthPlayerName = new TextField("",guiSkin);
        fourthPlayerName.setAlignment(Align.center);
        backButton = new TextButton("Back", guiSkin);
        startButton = new TextButton("Start", guiSkin);
    }

    @Override
    public void addButtonActions() {
        backButton.align(Align.topRight);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.buttonSound.play();
                parent.changeScreen(Engine.MENU);
            }
        });
    }

    @Override
    public void inputDataConfiguration() {
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void tableAndStageConfiguration(){
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);
        table.add(window);
        //window.setDebug(true);
        //table.add(backButton).fillX().uniformX().align(Align.top);
        //table.add(title);


        window.row();
        window.row();//.pad(400,0,400,0);
        window.add(playersNames).pad(150,0,0,0);
        window.add(playersNumber).pad(150,0,0,0);
        window.row();
        window.add(twoPlayers).pad(10,0,10,0);//.pad(50);
        window.add(firstPlayerName).pad(10,0,10,0);;
        window.row();
        window.add(threePlayers).pad(10,0,10,0);;//.pad(50);
        window.add(secondPlayerName).pad(10,0,10,0);;//.pad(50,50,50,100);
        window.row();
        window.add(fourPlayers).pad(10,0,10,0);;
        window.add(thirdPlayerName).pad(10,0,10,0);;
        window.row();
        window.add(new Label("",guiSkin)).pad(10,0,10,0);;
        window.add(fourthPlayerName).pad(10,0,10,0);;
        window.row();
        window.add(backButton).pad(50,100,100,50);
        window.add(startButton).pad(50,50,100,100);


       // window.add(threePlayers).fillX().uniformX();
       // window.add(fourPlayers).fillX().uniformX();
        //window.row();


        table.setBackground(guiSkin.getDrawable("pale-blue"));
    }
}
