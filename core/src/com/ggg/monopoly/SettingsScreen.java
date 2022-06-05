package com.ggg.monopoly;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Klasa reprezentujaca okno z ustawieniami programu
 */
public class SettingsScreen implements Screen,BasicFunctions {
    private Engine parent;
    private Stage stage;
    private Skin guiSkin;
    private Table table;
    private CheckBox fullScreen;


    private Boolean debugMode;

    private Window window;
    private TextButton backButton;

    /**
     * Konstruktor scenu
     * @param engine referencja do klasy nadrzednej
     * @param guiSkin zestaw tekstur interfajsu
     * @param debugMode stan trybu debug
     */
    public SettingsScreen(Engine engine, Skin guiSkin,Boolean debugMode){
        this.debugMode = debugMode;
        this.guiSkin=guiSkin;
        parent = engine;
        createObjects();
        createButtonsAndLabels();
        tableAndStageConfiguration();
        addButtonActions();
    }

    /**
     * Metoda wywoływania gdy scena zostanie wyświetlona
     */
    @Override
    public void show() {
        inputDataConfiguration();
    }

    /**
     * Wyświetlanie sceny
     * @param delta czas pomiędzy klatkami
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    /**
     * Metoda wywoływania podczas zmiany rozmiaru okna
     * @param width dlugosc
     * @param height szerokosc
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
    public void dispose() {}

    /**
     * Tworzy obiekty sceny i tabele
     */
    public void createObjects(){
        stage = new Stage(new ScreenViewport());
        table = new Table();
    }

    /**
     * Dodaje dane konfiguracyjne
     */
    public void inputDataConfiguration(){
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Tworzy przyciski i etykiety
     */
    public void createButtonsAndLabels(){
        fullScreen = new CheckBox(" Full Screen",guiSkin);
        window = new Window("",guiSkin);
        backButton = new TextButton("Back",guiSkin);
        debugModeAction();
    }

    /**
     * Dodawanie akcji które zostaną wykonane po naciśnięciu przycisków
     */
    public void addButtonActions()
    {
        fullScreen.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(Gdx.graphics.isFullscreen()) {
                    parent.buttonSound.play();
                    Gdx.graphics.setWindowedMode(1920, 1080);

                }
                else{
                    parent.buttonSound.play();
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

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

    /**
     * Akcje dla trybu debugowania
     */
    private void debugModeAction(){
        table.setDebug(debugMode);
        fullScreen.setDebug(debugMode);
    }

    /**
     * Konfiguracja tablicy i sceny
     */
    @Override
    public void tableAndStageConfiguration() {
        table.setBackground(guiSkin.getDrawable("pale-blue"));
        table.setFillParent(true);
        stage.addActor(table);
        table.setTransform(true);
        table.add(window);
        window.row();
        window.add(new Label("",guiSkin)).pad(120,100,0,0);
        window.add(new Label("",guiSkin)).pad(120,0,0,0);
        window.row().pad(0,0,15,0);
        window.add(fullScreen).fillX().uniformX().pad(0,100,50,0);
        window.row().pad(15,0,0,0);
        window.row();
        window.add(backButton).fillX().uniformX().pad(0,100,100,0);
    }
}
