package com.ggg.monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ApplicationScreen implements Screen {
    private Environment environment;
    private Engine parent;
    private PerspectiveCamera cam;
    private Model cube;
    private ModelInstance cubeInstance;
    private ModelBatch modelBatch;
    private CameraInputController cameraInputController;
    private Table table;
    private TextButton newGameButton;
    private Stage stage;

    public ApplicationScreen(Engine engine){
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight,0.4f,0.4f,0.4f,1f));
        environment.add(new DirectionalLight().set(0.8f,0.8f,0.8f,-1f,-0.8f,-0.2f));

        parent = engine;
        modelBatch = new ModelBatch();
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        cam.position.set(10f,10f,10f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        ModelBuilder modelBuilder = new ModelBuilder();
        cube = modelBuilder.createBox(5f,5f,5f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal);
        cubeInstance = new ModelInstance(cube);
        cameraInputController = new CameraInputController(cam);
        cameraInputController.translateButton= Input.Keys.UNKNOWN;
////////////////////////////////////

        stage = new Stage(new ScreenViewport());

    }
    @Override
    public void show() {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skins/glassy-ui.json"));
        newGameButton = new TextButton("Back", skin);
        table.add(newGameButton).fillX().uniformX();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(cameraInputController);

        Gdx.input.setInputProcessor(multiplexer);

        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Engine.MENU);
            }
        });

       //newGameButton.addListener((event) -> {
       //    parent.changeScreen(Engine.MENU);
       //    return false;
       //});
    }

    @Override
    public void render(float delta) {

        cameraInputController.update();

        Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);


        modelBatch.begin(cam);
        modelBatch.render(cubeInstance,environment);
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
        cube.dispose();
        modelBatch.dispose();
    }
}
