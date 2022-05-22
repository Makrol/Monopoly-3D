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

import java.util.ArrayList;
import java.util.Random;

public class ApplicationScreen implements Screen,BasicFunctions {
    static public ArrayList<SpecialCard> setOfCards;
    static public ArrayList<Field> setOfFields;
    private Environment environment;
    private Engine parent;
    private PerspectiveCamera cam;
    private Model model;
    private Model school;
    private ModelInstance modelInstance;
    private ModelInstance schoolInstance;
    private ModelBatch modelBatch;
    private CameraInputController cameraInputController;
    private Table topLeftTable;
    private Table topRightTable;
    private Table bottomLeftTable;
    private Table bottomRightTable;
    private Table centerTable;
    private Table topTable;
    private TextButton backButton;
    private Stage stage;
    private Skin guiSkin;
    private InputMultiplexer multiplexer;
    private ModelBuilder modelBuilder;
    private ModelLoader modelLoader;
    static public GameActionWindow actionWindow;

    private ArrayList<Player>playersList;
    private ArrayList<PlayerInfoTable>playersInfoList;

    private TextButton randButton;


    public ApplicationScreen(Engine engine,Skin guiSkin){
        initSetOfCards();
        initFields();
        parent = engine;
        this.guiSkin=new Skin(Gdx.files.internal("skins/infoWindow/infoWindow.json"));
        createObjects();
        playerConfig();
        environmentConfiguration();
        cameraConfiguration();
        loading3dObjects();
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

        cameraInputController.update();

        Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0.0902f,0.5490f,0.7098f,0);

        modelBatch.begin(cam);
        modelBatch.render(modelInstance,environment);
        modelBatch.render(schoolInstance,environment);

        for(Player p:playersList){

            p.update();
            p.render(modelBatch,environment);
        }
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
        actionWindow = new GameActionWindow(this);

        addPlayers();
        playersInfoList = new ArrayList<>();

        multiplexer = new InputMultiplexer();

        topLeftTable = new Table();
        topLeftTable.setFillParent(true);

        topRightTable = new Table();
        topRightTable.setFillParent(true);

        bottomLeftTable = new Table();
        bottomLeftTable.setFillParent(true);

        bottomRightTable = new Table();
        bottomRightTable.setFillParent(true);

        topTable = new Table();
        topTable.setFillParent(true);

        centerTable = new Table();
        centerTable.setFillParent(true);

        modelBatch = new ModelBatch();
        stage = new Stage(new ScreenViewport());
        environment = new Environment();
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        modelLoader = new ObjLoader();
        modelBuilder = new ModelBuilder();

        switch (Engine.playerNumber){
            case 2:
                playersInfoList.add(new PlayerInfoTable(Engine.playerNames.get(0),playersList.get(0)));
                playersInfoList.add(new PlayerInfoTable(Engine.playerNames.get(1),playersList.get(1)));
                playersInfoList.add(new PlayerInfoTable(Engine.playerNames.get(1),null));
                playersInfoList.add(new PlayerInfoTable(Engine.playerNames.get(1),null));
                break;
            case 3:
                playersInfoList.add(new PlayerInfoTable(Engine.playerNames.get(0),playersList.get(0)));
                playersInfoList.add(new PlayerInfoTable(Engine.playerNames.get(1),playersList.get(1)));
                playersInfoList.add(new PlayerInfoTable(Engine.playerNames.get(2),playersList.get(2)));
                playersInfoList.add(new PlayerInfoTable(Engine.playerNames.get(2),null));
                break;
            case 4:
                playersInfoList.add(new PlayerInfoTable(Engine.playerNames.get(0),playersList.get(0)));
                playersInfoList.add(new PlayerInfoTable(Engine.playerNames.get(1),playersList.get(1)));
                playersInfoList.add(new PlayerInfoTable(Engine.playerNames.get(2),playersList.get(2)));
                playersInfoList.add(new PlayerInfoTable(Engine.playerNames.get(3),playersList.get(3)));
                break;
        }



        playersInfoList.get((new Random().nextInt(Engine.playerNumber))).setActiveValues(true);



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
        modelInstance.transform.translate(new Vector3(0.0f,-0.5f,0.0f));

        school = new ObjLoader().loadModel(Gdx.files.internal("school/psk.obj"),new ObjLoader.ObjLoaderParameters(true));


        schoolInstance = new ModelInstance(school);
        schoolInstance.transform.translate(0,1.5f,0);
    }
    @Override
    public void createButtons(){
        backButton = new TextButton("Back", guiSkin);
        randButton = new TextButton("Rzut kostkami", guiSkin);

    }
    @Override
    public void inputDataConfiguration(){
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(cameraInputController);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void addButtonActions() {
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Engine.MENU);
            }
        });
        randButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                randButton.setDisabled(true);
                int num = new Random().nextInt(11)+2;
                //int num =1;
                Player tmp = getActivePlayer();
                tmp.move(num);
                //nextPlayer();
            }
        });
        actionWindow.getExitButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                randButton.setDisabled(false);
                actionWindow.getWindow().setVisible(false);
                nextPlayer();
            }
        });
        actionWindow.getBuyButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
    }

    @Override
    public void tableAndStageConfiguration() {
        topLeftTable.setDebug(true);
        topRightTable.setDebug(true);
        bottomRightTable.setDebug(true);
        bottomLeftTable.setDebug(true);
        topTable.setDebug(true);

        stage.addActor(topLeftTable);
        topLeftTable.align(Align.topLeft);
        topLeftTable.add(playersInfoList.get(0).getWindow());

        stage.addActor(topRightTable);
        topRightTable.align(Align.topRight);
        topRightTable.add(playersInfoList.get(1).getWindow());

        stage.addActor(bottomLeftTable);
        bottomLeftTable.align(Align.bottomLeft);
        bottomLeftTable.add(playersInfoList.get(2).getWindow());

        stage.addActor(bottomRightTable);
        bottomRightTable.align(Align.bottomRight);
        bottomRightTable.add(playersInfoList.get(3).getWindow());

        stage.addActor(centerTable);
        centerTable.align(Align.center);
        centerTable.add(actionWindow.getWindow());

        stage.addActor(topTable);
        topTable.align(Align.top);
        topTable.add(randButton);
    }
    private void addPlayers(){
        playersList = new ArrayList<>();
        for(int i=0;i<Engine.playerNumber;i++)
            playersList.add(new Player(Engine.playerNames.get(i),i+1));
    }
    private void playerConfig(){
        switch (Engine.playerNumber){
            case 2:
                playersInfoList.get(0).setVisible(true);
                playersInfoList.get(1).setVisible(true);
                playersInfoList.get(2).setVisible(false);
                playersInfoList.get(3).setVisible(false);
                break;
            case 3:
                playersInfoList.get(0).setVisible(true);
                playersInfoList.get(1).setVisible(true);
                playersInfoList.get(2).setVisible(true);
                playersInfoList.get(3).setVisible(false);
                break;
            case 4:
                playersInfoList.get(0).setVisible(true);
                playersInfoList.get(1).setVisible(true);
                playersInfoList.get(2).setVisible(true);
                playersInfoList.get(3).setVisible(true);
                break;
        }
    }
    public Player getActivePlayer(){
        for(int i=0;i<Engine.playerNumber;i++){
            if(playersInfoList.get(i).getActiveValues()){
                return playersList.get(i);
            }
        }
        return null;
    }

    public Player getNextPlayer(){
        for(int i=0;i<Engine.playerNumber;i++){
            if(playersInfoList.get(i).getActiveValues()){
                if(i==Engine.playerNumber-1)
                    return playersList.get(0);
                else
                    return playersList.get(i+1);
            }
        }
        return null;
    }
    private void nextPlayer(){
        for(int i=0;i<Engine.playerNumber;i++){
            if(playersInfoList.get(i).getActiveValues()){
                playersInfoList.get(i).setActiveValues(false);
                if(i==Engine.playerNumber-1){
                    playersInfoList.get(0).setActiveValues(true);
                    return;
                }
                else{
                    playersInfoList.get(i+1).setActiveValues(true);
                    return;
                }
            }
        }
    }
    void initSetOfCards(){
        setOfCards = new ArrayList<>();
        setOfCards.add(new SpecialCard("Znalazles na przerwie troche pieniedzy. Otrzymujesz 50 PsKc.",50,SpecialCard.fromOp.fromBank,SpecialCard.toOp.toMe,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Pomogles koledze z roku nizej za drobna oplata. Otrzymujesz 100 PsKc.",100,SpecialCard.fromOp.fromBank,SpecialCard.toOp.toMe,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Wygrales konkurs w swoim wydziale. Otrzymujesz 200 PsKc.",200,SpecialCard.fromOp.fromBank,SpecialCard.toOp.toMe,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Poruszasz sie w przod.",0,SpecialCard.fromOp.noAction,SpecialCard.toOp.noAction,SpecialCard.action.moveForward));
        setOfCards.add(new SpecialCard("Skontaktuj sie z wykladowca w celu odrobienia zajec. Placisz 50 PsKc.",-50,SpecialCard.fromOp.fromMe,SpecialCard.toOp.toBank,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Oblales egzamin. Przyjdz na poprawke. Placisz 100 PsKc.",-100,SpecialCard.fromOp.fromMe,SpecialCard.toOp.toBank,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Uciekles z zajec. Wracasz do domu odpoczac. Udaj sie na START. Pobierz 200 PsKc.",0,SpecialCard.fromOp.fromBank,SpecialCard.toOp.toMe,SpecialCard.action.goStart));
        setOfCards.add(new SpecialCard("Chodzac na uczelnie mozesz wygrac lub przegrac. \nNie ma trzeciej opcji. Pobierasz 150 PsKc.",150,SpecialCard.fromOp.fromBank,SpecialCard.toOp.toMe,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Mianowano Cie Starosta grupy. Pobierasz 20 PsKc od kazdego gracza.",20,SpecialCard.fromOp.fromEveryone,SpecialCard.toOp.toMe,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Mianowano Cie Starosta roku. Pobierasz 50 PsKc od kazdego gracza.",50,SpecialCard.fromOp.fromEveryone,SpecialCard.toOp.toMe,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Zaaranzuj spotkanie twojego wydzialu, w celu przemyslenia taktyki. Placisz 100 PsKc.",-100,SpecialCard.fromOp.fromMe,SpecialCard.toOp.toBank,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Poruszasz sie o 3 pola.",0,SpecialCard.fromOp.noAction,SpecialCard.toOp.noAction,SpecialCard.action.moveForward));
        setOfCards.add(new SpecialCard("Twoja siedziba zostala zlupiona. Placisz 50 PsKc.",-50,SpecialCard.fromOp.fromMe,SpecialCard.toOp.toBank,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Twoj promotor daje Ci cenna rade. Pobierasz 12 PsKc.",12,SpecialCard.fromOp.fromBank,SpecialCard.toOp.toMe,SpecialCard.action.noAction));

    }
    void initFields(){
        setOfFields = new ArrayList<>();
        setOfFields.add(new Field("Start",0,Field.type.start));
        setOfFields.add(new Field("Biblioteka",50,Field.type.normalField));
        setOfFields.add(new Field("Karta specjalna",0, Field.type.specialCard));
        setOfFields.add(new Field("Hala sportowa",75,Field.type.normalField));
        setOfFields.add(new Field("Podatek od zubozenia",200,Field.type.tax));
        setOfFields.add(new Field("Rektorat",200,Field.type.winFields));
        setOfFields.add(new Field("Sala 2.08A",100, Field.type.normalField));
        setOfFields.add(new Field("Karta specjalna",0,Field.type.specialCard));
        setOfFields.add(new Field("Sala 1.03A",100, Field.type.normalField));
        setOfFields.add(new Field("Sala 1.06A",150, Field.type.normalField));
        setOfFields.add(new Field("Wycieczka",0,Field.type.wycieczka));
        setOfFields.add(new Field("Sala 3.14B",200, Field.type.normalField));
        setOfFields.add(new Field("Uczelniana elektrownia",150,Field.type.twoOfThem));
        setOfFields.add(new Field("Sala 1.09HB",200,Field.type.normalField));
        setOfFields.add(new Field("Sala 1.06B",250,Field.type.normalField));
        setOfFields.add(new Field("Piramidy",200,Field.type.winFields));
        setOfFields.add(new Field("Sala 1.10B",275,Field.type.normalField));
        setOfFields.add(new Field("Karta specjalna",0, Field.type.specialCard));
        setOfFields.add(new Field("Sala 3.04B",275,Field.type.normalField));
        setOfFields.add(new Field("Sala 1.02HCL",325,Field.type.normalField));
        setOfFields.add(new Field("Darmowy parking",0,Field.type.parking));
        setOfFields.add(new Field("Sala 3.11C",350,Field.type.normalField));
        setOfFields.add(new Field("Karta specjalna",0,Field.type.specialCard));
        setOfFields.add(new Field("Sala 3.17C",350,Field.type.normalField));
        setOfFields.add(new Field("Audytorium 1.20",375,Field.type.normalField));
        setOfFields.add(new Field("Boisko",200,Field.type.winFields));
        setOfFields.add(new Field("Sala 2.32C",400, Field.type.normalField));
        setOfFields.add(new Field("Sala 2.06C",400, Field.type.normalField));
        setOfFields.add(new Field("Uczelniane wodociagi",150,Field.type.twoOfThem));
        setOfFields.add(new Field("Audytorium 1.15",450, Field.type.normalField));
        setOfFields.add(new Field("Polibus",0, Field.type.polibus));
        setOfFields.add(new Field("Sala 1.20D",475, Field.type.normalField));
        setOfFields.add(new Field("Sala 3.26D",475, Field.type.normalField));
        setOfFields.add(new Field("Karta specjalna",0,Field.type.specialCard));
        setOfFields.add(new Field("Sala 4.11D",525, Field.type.normalField));
        setOfFields.add(new Field("Energis",200,Field.type.winFields));
        setOfFields.add(new Field("Karta specjalna",0,Field.type.specialCard));
        setOfFields.add(new Field("Aula glowna 3",500, Field.type.normalField));
        setOfFields.add(new Field("Podatek od nauki",300,Field.type.tax));
        setOfFields.add(new Field("Aula glowna 3",550, Field.type.normalField));

    }

    public ArrayList<PlayerInfoTable> getPlayersInfoList() {
        return playersInfoList;
    }

    public ArrayList<Player> getPlayersList() {
        return playersList;
    }
}
