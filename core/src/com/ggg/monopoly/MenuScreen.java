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

    /**
     * Konstruktor sceny menu
     * @param engine klasa nadrzedna
     * @param guiSkin zestaw tekstur interfejsu
     */
    public MenuScreen(Engine engine, Skin guiSkin){
        parent = engine;
        this.guiSkin=guiSkin;
        createObjects();
        createButtonsAndLabels();
        tableAndStageConfiguration();
        addButtonActions();

    }

    /**
     * Wywolywana kiedy wyswietla sie menu lub newgame
     */
    @Override
    public void show() {
        inputDataConfiguration();
    }

    /**
     * Wywoluje sie sama i wyswietla renderowane rzeczy
     * @param delta Czas w sekundach
     */
    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /**
     * Funkcja wywolujaca sie podczas zmiany okna
     * @param width Szerokosc
     * @param height Wysokosc
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
    private void update(){}

    /**
     * Tworzenie przycisków i etykiet interfejsu
     */
    @Override
    public void createButtonsAndLabels()
    {
        newGameButton = new TextButton("New Game", guiSkin);
        settingsButton = new TextButton("Settings", guiSkin);
        exitButton = new TextButton("Exit", guiSkin);
    }

    /**
     * Dodanie akcji do naciśnięcia przycisku
     */
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

    /**
     * Tworzenie obiektów
     */
    @Override
    public void createObjects() {
        stage = new Stage(new ScreenViewport());
        table = new Table();
    }

    /**
     * Konfiguracje odbierania danych wejściowych
     */
    @Override
    public void inputDataConfiguration() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Konfiguracja tablicy i sceny
     */
    @Override
    public void tableAndStageConfiguration(){
        table.setFillParent(true);
        stage.addActor(table);
        table.setBackground(guiSkin.getDrawable("pale-blue"));
        table.add(newGameButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(settingsButton).fillX().uniformX();
        table.row();
        table.add(exitButton).fillX().uniformX();
    }
}
