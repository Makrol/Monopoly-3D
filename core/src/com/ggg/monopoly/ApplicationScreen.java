package com.ggg.monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ApplicationScreen implements Screen,BasicFunctions {
    private Environment environment;
    private Engine parent;
    private PerspectiveCamera cam;
    private Model model;
    private ModelInstance modelInstance;
    private ModelBatch modelBatch;
    private CameraInputController cameraInputController;
    private Table table;
    private TextButton backButton;
    private Stage stage;
    private Skin guiSkin;
    private InputMultiplexer multiplexer;
    private ModelBuilder modelBuilder;
    private ModelLoader modelLoader;

    private final Pawn pawn;
    public ApplicationScreen(Engine engine,Skin guiSkin){
        parent = engine;
        this.guiSkin=guiSkin;
        pawn = new Pawn();
        createObjects();
        environmentConfiguration();
        cameraConfiguration();
        loading3dObjects();
        createButtons();
        inputDataConfiguration();
        tableAndStageConfiguration();
        addButtonActions();
    }
    @Override
    public void show() {
        inputDataConfiguration();
    }

    @Override
    public void render(float delta) {
        pawn.move();
        cameraInputController.update();

        Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        modelBatch.render(modelInstance,environment);
        pawn.render(modelBatch,environment);
        modelBatch.end();
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
        modelBatch.dispose();
        model.dispose();
    }
    @Override
    public void createObjects(){
        multiplexer = new InputMultiplexer();
        table = new Table();
        modelBatch = new ModelBatch();
        stage = new Stage(new ScreenViewport());
        environment = new Environment();
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        modelLoader = new ObjLoader();
        modelBuilder = new ModelBuilder();
    }
    private void cameraConfiguration(){
        cam.position.set(0f,10f,0f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
        cameraInputController = new CameraInputController(cam);
        cameraInputController.translateButton= Input.Keys.UNKNOWN;
    }
    private void environmentConfiguration(){
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight,0.4f,0.4f,0.4f,1f));
        environment.add(new DirectionalLight().set(0.8f,0.8f,0.8f,-1f,-0.8f,-0.2f));
    }
    private void loading3dObjects(){
        model = modelLoader.loadModel(Gdx.files.internal("plansza/board.obj"));
        modelInstance = new ModelInstance(model);
        modelInstance.transform.translate(new Vector3(0.0f,0.0f,0.0f));
    }
    @Override
    public void createButtons(){
        backButton = new TextButton("Back", guiSkin);

    }
    @Override
    public void inputDataConfiguration(){
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(cameraInputController);
    }

    @Override
    public void addButtonActions() {
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //parent.changeScreen(Engine.MENU);
                //test///////////////////////
                pawn.startMove();
            }
        });
    }

    @Override
    public void tableAndStageConfiguration() {
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);
        table.align(Align.topRight);
        table.add(backButton).fillX().uniformX();
    }
}
