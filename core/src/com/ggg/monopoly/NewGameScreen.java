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
    private CheckBoxGroupController checkBoxGroupController;

    private Window window;
    public NewGameScreen(Engine engine,Skin guiSkin){

        this.guiSkin = guiSkin;
        parent = engine;
        createObjects();
        createButtonsAndLabels();
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
        checkBoxGroupController = new CheckBoxGroupController();
    }

    @Override
    public void createButtonsAndLabels() {
        window = new Window("",guiSkin);
        twoPlayers = new CheckBox("2 players",guiSkin);
        twoPlayers.setName("two");
        threePlayers = new CheckBox("3 players",guiSkin);
        threePlayers.setName("three");
        fourPlayers = new CheckBox("4 players",guiSkin);
        fourPlayers.setName("four");
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

        checkBoxGroupController.addCheckBox(twoPlayers);
        checkBoxGroupController.addCheckBox(threePlayers);
        checkBoxGroupController.addCheckBox(fourPlayers);
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
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(checkBoxGroupController.getSelected()!=null){
                    if(checkBoxGroupController.getSelected().getName().equals("two"))
                        Engine.playerNumber=2;
                    else if(checkBoxGroupController.getSelected().getName().equals("three"))
                        Engine.playerNumber=3;
                    else if(checkBoxGroupController.getSelected().getName().equals("four"))
                        Engine.playerNumber=4;
                    Engine.playerNames.add(firstPlayerName.getText());
                    Engine.playerNames.add(secondPlayerName.getText());
                    Engine.playerNames.add(thirdPlayerName.getText());
                    Engine.playerNames.add(fourthPlayerName.getText());
                    parent.changeScreen(Engine.APPLICATION);
                }
            }
        });

        twoPlayers.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                checkBoxGroupController.selectCheckBox(twoPlayers);
                disableUselessTextFields(checkBoxGroupController.getSelected());
            }
        });
        threePlayers.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                checkBoxGroupController.selectCheckBox(threePlayers);
                disableUselessTextFields(checkBoxGroupController.getSelected());
            }
        });
        fourPlayers.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                checkBoxGroupController.selectCheckBox(fourPlayers);
                disableUselessTextFields(checkBoxGroupController.getSelected());
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

        stage.addActor(table);
        table.add(window);

        //table.add(backButton).fillX().uniformX().align(Align.top);
        //table.add(title);


        window.row();
        window.row();//.pad(400,0,400,0);
        window.add(playersNumber).pad(150,0,0,0);
        window.add(playersNames).pad(150,0,0,0);
        window.row();
        window.add(twoPlayers).pad(10,0,10,0);//.pad(50);
        window.add(firstPlayerName).pad(10,0,10,0);
        window.row();
        window.add(threePlayers).pad(10,0,10,0);//.pad(50);
        window.add(secondPlayerName).pad(10,0,10,0);//.pad(50,50,50,100);
        window.row();
        window.add(fourPlayers).pad(10,0,10,0);
        window.add(thirdPlayerName).pad(10,0,10,0);
        window.row();
        window.add(new Label("",guiSkin)).pad(10,0,10,0);
        window.add(fourthPlayerName).pad(10,0,10,0);
        window.row();
        window.add(backButton).pad(50,100,100,50);
        window.add(startButton).pad(50,50,100,100);


       // window.add(threePlayers).fillX().uniformX();
       // window.add(fourPlayers).fillX().uniformX();
        //window.row();


        table.setBackground(guiSkin.getDrawable("pale-blue"));
    }
    void disableUselessTextFields(CheckBox checkBox){
        if(checkBox==null){
            firstPlayerName.setDisabled(true);
            secondPlayerName.setDisabled(true);
            thirdPlayerName.setDisabled(true);
            fourthPlayerName.setDisabled(true);
        }else if(checkBox.getName().equals("two")){
            firstPlayerName.setDisabled(false);
            secondPlayerName.setDisabled(false);
            thirdPlayerName.setDisabled(true);
            fourthPlayerName.setDisabled(true);
        }else if(checkBox.getName().equals("three")){
            firstPlayerName.setDisabled(false);
            secondPlayerName.setDisabled(false);
            thirdPlayerName.setDisabled(false);
            fourthPlayerName.setDisabled(true);
        }else{
            firstPlayerName.setDisabled(false);
            secondPlayerName.setDisabled(false);
            thirdPlayerName.setDisabled(false);
            fourthPlayerName.setDisabled(false);
        }
    }

}
