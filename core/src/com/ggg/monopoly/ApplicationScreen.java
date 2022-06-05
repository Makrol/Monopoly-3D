package com.ggg.monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Klasa reprezentujaca glowne okno aplikacje w którym odbywa sie rozgrywka
 */
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

    private ModelInstance pyramidModel;


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

    private HashMap<Integer,ModelInstance> cubeInstance;

    private ArrayList<Model> pawnsModels;

    private HashMap<Integer, Vector3> cubePos;

    private ModelInstance routerModel;

    private  Table bottomCenterTable;

    private Label counterLabel;

    private LocalDateTime startTime;
    private LocalDateTime currentTime;

    private Integer minutes;
    private Integer seconds;

    private Boolean timerRun;


    /**
     * Inicjalizacja głownego okna aplikacji
     * @param engine referencja do klasy nadrzednej
     * @param guiSkin zestaw tekstur interfejsu
     */
    public ApplicationScreen(Engine engine,Skin guiSkin){

        createCubes();
        createCubePos();
        initCubePos();

        loadPawnModels();
        initSetOfCards();
        initFields();
        parent = engine;
        this.guiSkin=new Skin(Gdx.files.internal("skins/infoWindow/infoWindow.json"));
        createObjects();
        playerConfig();
        environmentConfiguration();
        cameraConfiguration();
        loading3dObjects();
        createButtonsAndLabels();
        tableAndStageConfiguration();
        addButtonActions();

    }

    /**
     *Metoda aktywowana po aktywacji sceny
     */
    @Override
    public void show() {

        inputDataConfiguration();
    }

    /**
     * Wyświetlanie elementów aplikacji
     * @param delta czas pomiędzy klatkami
     */
    @Override
    public void render(float delta) {
        updateTimer();
        cameraInputController.update();

        Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0.537f,0.847f,0.902f,0);



        modelBatch.begin(cam);
        modelBatch.render(modelInstance,environment);
        modelBatch.render(schoolInstance,environment);
        modelBatch.render(pyramidModel,environment);
        modelBatch.render(routerModel,environment);

        for(Map.Entry<Integer,ModelInstance> set:cubeInstance.entrySet()){
            modelBatch.render(set.getValue(),environment);
        }

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

    /**
     * Tworzenie obiektów klasy
     */
    @Override
    public void createObjects(){

        timerRun = true;

        startTime = LocalDateTime.now();
        currentTime = LocalDateTime.now();



        System.out.println(ChronoUnit.SECONDS.between(startTime,currentTime));

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

        bottomCenterTable = new Table();
        bottomCenterTable.setFillParent(true);



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

    /**
     * Konfiguracja kamery przestrzeni 3D
     */
    private void cameraConfiguration(){
        cam.position.set(0f,10f,30f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
        cameraInputController = new CameraInputController(cam);
        cameraInputController.translateButton= Input.Keys.UNKNOWN;
    }

    /**
     * Konfiguracja środowiska 3D
     */
    private void environmentConfiguration(){
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight,0.4f,0.4f,0.4f,1f));
        environment.add(new DirectionalLight().set(0.8f,0.8f,0.8f,-1f,-0.8f,-0.2f));
    }

    /**
     * Ladowanie obiektów 3D
     */
    private void loading3dObjects(){
        model = modelLoader.loadModel(Gdx.files.internal("plansza/board.obj"));
        modelInstance = new ModelInstance(model);
        modelInstance.transform.translate(new Vector3(0.0f,-0.5f,0.0f));

        school = new ObjLoader().loadModel(Gdx.files.internal("school/psk.obj"),new ObjLoader.ObjLoaderParameters(true));


        schoolInstance = new ModelInstance(school);
        schoolInstance.transform.translate(0,1.5f,0);

        pyramidModel = new ModelInstance(modelLoader.loadModel(Gdx.files.internal("piramida/pyramid.obj")));
        pyramidModel.transform.translate(12f,1.25f,9f);

        routerModel = new ModelInstance(modelLoader.loadModel(Gdx.files.internal("router/router.obj")));
        routerModel.transform.translate(0f,-2.5f,0f);
        routerModel.transform.rotate(180,0,0,180);


    }

    /**
     * Tworzenie przycisków i napisów interfejsu
     */
    @Override
    public void createButtonsAndLabels(){
        backButton = new TextButton("Back", guiSkin);
        randButton = new TextButton("Rzut kostkami", guiSkin);
        counterLabel = new Label("10:00",guiSkin);

    }

    /**
     * Konfiguracja odbioru sygnałów wejściowych dla środowiska 2D i 3D
     */
    @Override
    public void inputDataConfiguration(){
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(cameraInputController);
        Gdx.input.setInputProcessor(multiplexer);
    }

    /**
     * Dodanie akcji dla kliknięcia przycisków
     */
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
                Player tmp = getActivePlayer();
                tmp.move(num);
            }
        });
        actionWindow.getExitButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(timerRun==false)
                    System.exit(0);
                Field currentField = setOfFields.get(getActivePlayer().getPawn().getCurrentFiledIndex());
                if(currentField.getFieldType()==Field.type.specialCard){
                    SpecialCard tmpCard = setOfCards.get(GameActionWindow.specialCardIndex);
                    if(tmpCard.getCardAction()==SpecialCard.action.moveForward){
                        getActivePlayer().move(tmpCard.getMoneyChange());
                    }
                    else if(tmpCard.getCardAction()==SpecialCard.action.goStart){
                        getActivePlayer().move(40-getActivePlayer().getPawn().getCurrentFiledIndex());
                        randButton.setDisabled(false);
                        actionWindow.getWindow().setVisible(false);
                        return;
                    }
                }
                randButton.setDisabled(false);
                actionWindow.getWindow().setVisible(false);
                nextPlayer();
            }
        });
        actionWindow.getBuyButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Field currentField = setOfFields.get(getActivePlayer().getPawn().getCurrentFiledIndex());
                if(currentField.getOwner()!=null){
                    if(getActivePlayer().getMoney()>=currentField.getPrice()*1.8) {
                        changeCubeColor(currentField);
                        randButton.setDisabled(false);
                        actionWindow.getWindow().setVisible(false);

                        currentField.getOwner().updateThisPlayerMoney((int) (currentField.getPrice()*1.8));

                        currentField.setOwner(getActivePlayer());

                        getActivePlayer().updateMoney((int) (-currentField.getPrice()*1.8));

                    }
                    if(currentField.getFieldType()==Field.type.winFields &&
                            setOfFields.get(5).getOwner()==getActivePlayer() &&
                            setOfFields.get(15).getOwner()==getActivePlayer() &&
                            setOfFields.get(25).getOwner()==getActivePlayer() &&
                            setOfFields.get(35).getOwner()==getActivePlayer()) {
                        GameActionWindow.showWindow();
                        actionWindow.showWinWindow(getActivePlayer());
                    }
                    nextPlayer();
                }
                else{
                    if(getActivePlayer().getMoney()>=currentField.getPrice()){

                        changeCubeColor(currentField);
                        randButton.setDisabled(false);
                        actionWindow.getWindow().setVisible(false);
                        currentField.setOwner(getActivePlayer());
                        getActivePlayer().updateMoney(-currentField.getPrice());
                        nextPlayer();
                    }
                }

            }
        });
        actionWindow.getGiveUpButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                randButton.setDisabled(false);
                actionWindow.getWindow().setVisible(false);
                playerGiveUp(getActivePlayer());
                nextPlayer();
            }
        });
        actionWindow.getPayFineButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Field currentField = setOfFields.get(getActivePlayer().getPawn().getCurrentFiledIndex());
                if(getActivePlayer().getMoney()>=currentField.getPrice()){



                    randButton.setDisabled(false);
                    actionWindow.getWindow().setVisible(false);
                    getActivePlayer().updateMoney(-currentField.getPrice());
                    currentField.getOwner().updateThisPlayerMoney(currentField.getPrice());
                    nextPlayer();
                }
            }
        });
    }

    /**
     * Konfiguracja układu interfejsu
     */
    @Override
    public void tableAndStageConfiguration() {


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

        stage.addActor(bottomCenterTable);
        bottomCenterTable.align(Align.bottom);
        bottomCenterTable.add(counterLabel).padBottom(30);


    }

    /**
     * Utworzenie nowych graczy
     */
    private void addPlayers(){
        playersList = new ArrayList<>();
        for(int i=0;i<Engine.playerNumber;i++)
            playersList.add(new Player(Engine.playerNames.get(i),i+1,this,pawnsModels.get(i),i));
    }

    /**
     * Konfiguracja okienek informacyjnych dla graczy
     */
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

    /**
     * Zwraca aktywnego gracza
     * @return gracz
     */
    public Player getActivePlayer(){
        for(int i=0;i<Engine.playerNumber;i++){
            if(playersInfoList.get(i).getActiveValues()){
                return playersList.get(i);
            }
        }
        return null;
    }

    /**
     * Zmienia ture nastepnego gracza
     */
    public void nextPlayer(){
        do {
            for (int i = 0; i < Engine.playerNumber; i++) {
                if (playersInfoList.get(i).getActiveValues()) {
                    playersInfoList.get(i).setActiveValues(false);
                    if (i == Engine.playerNumber - 1) {
                        playersInfoList.get(0).setActiveValues(true);
                        break;
                    } else {
                        playersInfoList.get(i + 1).setActiveValues(true);
                        break;
                    }
                }
            }
        }while (getActivePlayer().isInGame()==false);
    }

    /**
     * Konfiguracja informacji o kartach specjalnych
     */
    void initSetOfCards(){
        setOfCards = new ArrayList<>();
        setOfCards.add(new SpecialCard("Znalazles na przerwie troche pieniedzy.\n Otrzymujesz 50 PsKc.",50,SpecialCard.fromOp.fromBank,SpecialCard.toOp.toMe,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Pomogles koledze z roku nizej za drobna\n oplata. Otrzymujesz 100 PsKc.",100,SpecialCard.fromOp.fromBank,SpecialCard.toOp.toMe,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Wygrales konkurs w swoim wydziale. \nOtrzymujesz 200 PsKc.",200,SpecialCard.fromOp.fromBank,SpecialCard.toOp.toMe,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Poruszasz sie w przod.",1,SpecialCard.fromOp.noAction,SpecialCard.toOp.noAction,SpecialCard.action.moveForward));
        setOfCards.add(new SpecialCard("Skontaktuj sie z wykladowca w celu\n odrobienia zajec. Placisz 50 PsKc.",-50,SpecialCard.fromOp.fromMe,SpecialCard.toOp.toBank,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Oblales egzamin. Przyjdz na poprawke.\n Placisz 100 PsKc.",-100,SpecialCard.fromOp.fromMe,SpecialCard.toOp.toBank,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Uciekles z zajec. Wracasz do domu odpoczac.\n Udaj sie na START. Pobierz 200 PsKc.",0,SpecialCard.fromOp.fromBank,SpecialCard.toOp.toMe,SpecialCard.action.goStart));
        setOfCards.add(new SpecialCard("Chodzac na uczelnie mozesz wygrac lub \nprzegrac. Nie ma trzeciej opcji.\n Pobierasz 150 PsKc.",150,SpecialCard.fromOp.fromBank,SpecialCard.toOp.toMe,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Mianowano Cie Starosta grupy. Pobierasz\n 20 PsKc od kazdego gracza.",20,SpecialCard.fromOp.fromEveryone,SpecialCard.toOp.toMe,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Mianowano Cie Starosta roku. Pobierasz 50 PsKc\n od kazdego gracza.",50,SpecialCard.fromOp.fromEveryone,SpecialCard.toOp.toMe,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Zaaranzuj spotkanie twojego wydzialu,\n w celu przemyslenia taktyki.\n Placisz 100 PsKc.",-100,SpecialCard.fromOp.fromMe,SpecialCard.toOp.toBank,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Poruszasz sie o 3 pola.",3,SpecialCard.fromOp.noAction,SpecialCard.toOp.noAction,SpecialCard.action.moveForward));
        setOfCards.add(new SpecialCard("Twoja siedziba zostala zlupiona. \nPlacisz 50 PsKc.",-50,SpecialCard.fromOp.fromMe,SpecialCard.toOp.toBank,SpecialCard.action.noAction));
        setOfCards.add(new SpecialCard("Twoj promotor daje Ci cenna rade. \nPobierasz 12 PsKc.",12,SpecialCard.fromOp.fromBank,SpecialCard.toOp.toMe,SpecialCard.action.noAction));

    }

    /**
     * Konfiguracja informacji o polach na planszy
     */
    void initFields(){
        setOfFields = new ArrayList<>();
        setOfFields.add(new Field("Start",0,Field.type.start,0));
        setOfFields.add(new Field("Biblioteka",50,Field.type.normalField,1));
        setOfFields.add(new Field("Karta specjalna",0, Field.type.specialCard,2));
        setOfFields.add(new Field("Hala sportowa",75,Field.type.normalField,3));
        setOfFields.add(new Field("Podatek od zubozenia",200,Field.type.tax,4));
        setOfFields.add(new Field("Rektorat",200,Field.type.winFields,5));
        setOfFields.add(new Field("Sala 2.08A",100, Field.type.normalField,6));
        setOfFields.add(new Field("Karta specjalna",0,Field.type.specialCard,7));
        setOfFields.add(new Field("Sala 1.03A",100, Field.type.normalField,8));
        setOfFields.add(new Field("Sala 1.06A",150, Field.type.normalField,9));
        setOfFields.add(new Field("Wycieczka",0,Field.type.trip,10));
        setOfFields.add(new Field("Sala 3.14B",200, Field.type.normalField,11));
        setOfFields.add(new Field("Uczelniana elektrownia",150,Field.type.twoOfThem,12));
        setOfFields.add(new Field("Sala 1.09HB",200,Field.type.normalField,13));
        setOfFields.add(new Field("Sala 1.06B",250,Field.type.normalField,14));
        setOfFields.add(new Field("Piramidy",200,Field.type.winFields,15));
        setOfFields.add(new Field("Sala 1.10B",275,Field.type.normalField,16));
        setOfFields.add(new Field("Karta specjalna",0, Field.type.specialCard,17));
        setOfFields.add(new Field("Sala 3.04B",275,Field.type.normalField,18));
        setOfFields.add(new Field("Sala 1.02HCL",325,Field.type.normalField,19));
        setOfFields.add(new Field("Darmowy parking",0,Field.type.parking,20));
        setOfFields.add(new Field("Sala 3.11C",350,Field.type.normalField,21));
        setOfFields.add(new Field("Karta specjalna",0,Field.type.specialCard,22));
        setOfFields.add(new Field("Sala 3.17C",350,Field.type.normalField,23));
        setOfFields.add(new Field("Audytorium 1.20",375,Field.type.normalField,24));
        setOfFields.add(new Field("Boisko",200,Field.type.winFields,25));
        setOfFields.add(new Field("Sala 2.32C",400, Field.type.normalField,26));
        setOfFields.add(new Field("Sala 2.06C",400, Field.type.normalField,27));
        setOfFields.add(new Field("Uczelniane wodociagi",150,Field.type.twoOfThem,28));
        setOfFields.add(new Field("Audytorium 1.15",450, Field.type.normalField,29));
        setOfFields.add(new Field("Polibus",0, Field.type.polibus,30));
        setOfFields.add(new Field("Sala 1.20D",475, Field.type.normalField,31));
        setOfFields.add(new Field("Sala 3.26D",475, Field.type.normalField,32));
        setOfFields.add(new Field("Karta specjalna",0,Field.type.specialCard,33));
        setOfFields.add(new Field("Sala 4.11D",525, Field.type.normalField,34));
        setOfFields.add(new Field("Energis",200,Field.type.winFields,35));
        setOfFields.add(new Field("Karta specjalna",0,Field.type.specialCard,36));
        setOfFields.add(new Field("Aula glowna 3",500, Field.type.normalField,37));
        setOfFields.add(new Field("Podatek od nauki",300,Field.type.tax,38));
        setOfFields.add(new Field("Aula glowna 3",550, Field.type.normalField,39));

    }

    /**
     * Zwraca tablice z informacjami gracza
     * @return tablice z informacjami gracza
     */
    public ArrayList<PlayerInfoTable> getPlayersInfoList() {
        return playersInfoList;
    }

    /**
     * Zwraca liste graczy
     * @return liste graczy
     */
    public ArrayList<Player> getPlayersList() {
        return playersList;
    }

    /**
     * Tworzy szesciany odpowiadajace za pokazywanie czyje jest pole na planszy
     */
    private void createCubes(){
        cubeInstance = new HashMap<>();
        ModelBuilder tmpBuilder = new ModelBuilder();
            System.out.println("d1");
            cubeInstance.put(1,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(3,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(5,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(6,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(8,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(9,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(11,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(12,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(13,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(14,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(15,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(16,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(18,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(19,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(21,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(23,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(24,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(25,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(26,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(27,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(28,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(29,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(31,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(32,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(34,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(35,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(37,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
            cubeInstance.put(39,new ModelInstance(tmpBuilder.createBox(1f,1f,1f, new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
    }

    /**
     * Laduje modele pionkow
     */
    private void loadPawnModels() {
        ModelLoader loader = new ObjLoader();
        pawnsModels = new ArrayList<>();
        pawnsModels.add(loader.loadModel(Gdx.files.internal("pawns/red_pawn.obj")));
        pawnsModels.add(loader.loadModel(Gdx.files.internal("pawns/blue_pawn.obj")));
        pawnsModels.add(loader.loadModel(Gdx.files.internal("pawns/green_pawn.obj")));
        pawnsModels.add(loader.loadModel(Gdx.files.internal("pawns/yellow_pawn.obj")));
    }

    /**
     * Inicjalizuje pozycje szescianow
     */
    private void initCubePos(){

       cubeInstance.get(1).transform.translate(cubePos.get(1));
       cubeInstance.get(3).transform.translate(cubePos.get(3));
       cubeInstance.get(5).transform.translate(cubePos.get(5));
       cubeInstance.get(6).transform.translate(cubePos.get(6));
       cubeInstance.get(8).transform.translate(cubePos.get(8));
       cubeInstance.get(9).transform.translate(cubePos.get(9));
       cubeInstance.get(11).transform.translate(cubePos.get(11));

       cubeInstance.get(12).transform.translate(cubePos.get(12));
       cubeInstance.get(13).transform.translate(cubePos.get(13));
       cubeInstance.get(14).transform.translate(cubePos.get(14));
       cubeInstance.get(15).transform.translate(cubePos.get(15));
       cubeInstance.get(16).transform.translate(cubePos.get(16));
       cubeInstance.get(18).transform.translate(cubePos.get(18));
       cubeInstance.get(19).transform.translate(cubePos.get(19));

        cubeInstance.get(21).transform.translate(cubePos.get(21));
        cubeInstance.get(23).transform.translate(cubePos.get(23));
        cubeInstance.get(24).transform.translate(cubePos.get(24));
        cubeInstance.get(25).transform.translate(cubePos.get(25));
        cubeInstance.get(26).transform.translate(cubePos.get(26));
        cubeInstance.get(27).transform.translate(cubePos.get(27));
        cubeInstance.get(28).transform.translate(cubePos.get(28));
        cubeInstance.get(29).transform.translate(cubePos.get(29));

        cubeInstance.get(31).transform.translate(cubePos.get(31));
        cubeInstance.get(32).transform.translate(cubePos.get(32));
        cubeInstance.get(34).transform.translate(cubePos.get(34));
        cubeInstance.get(35).transform.translate(cubePos.get(35));
        cubeInstance.get(37).transform.translate(cubePos.get(37));
        cubeInstance.get(39).transform.translate(cubePos.get(39));

    }

    /**
     * Tworzenie pozycji dla szescianow
     */
    private void createCubePos(){
        cubePos = new HashMap<>();
        cubePos.put(1,new Vector3(12.2f,1,13.6f));
        cubePos.put(3,new Vector3(5.9f,1,13.6f));
        cubePos.put(5,new Vector3(-0.2f,1,13.6f));
        cubePos.put(6,new Vector3(-3.2f,1,13.6f));
        cubePos.put(8,new Vector3(-9.4f,1,13.6f));
        cubePos.put(9,new Vector3(-12.5f,1,13.6f));
        cubePos.put(11,new Vector3(-13.6f,1,12.5f));
        cubePos.put(12,new Vector3(-13.6f,1,9.4f));
        cubePos.put(13,new Vector3(-13.6f,1,6.3f));
        cubePos.put(14,new Vector3(-13.6f,1,3.3f));
        cubePos.put(15,new Vector3(-13.6f,1,0.2f));
        cubePos.put(16,new Vector3(-13.6f,1,-3.0f));
        cubePos.put(18,new Vector3(-13.6f,1,-9.2f));
        cubePos.put(19,new Vector3(-13.6f,1,-12.2f));
        cubePos.put(21,new Vector3(-12.5f,1,-13.3f));
        cubePos.put(23,new Vector3(-6.1f,1,-13.3f));
        cubePos.put(24,new Vector3(-3.2f,1,-13.3f));
        cubePos.put(25,new Vector3(-0.2f,1,-13.3f));
        cubePos.put(26,new Vector3(2.9f,1,-13.3f));
        cubePos.put(27,new Vector3(5.9f,1,-13.3f));
        cubePos.put(28,new Vector3(9.0f,1,-13.3f));
        cubePos.put(29,new Vector3(12.2f,1,-13.3f));
        cubePos.put(31,new Vector3(13.3f,1,-12.2f));
        cubePos.put(32,new Vector3(13.3f,1,-9.2f));
        cubePos.put(34,new Vector3(13.3f,1,-3.0f));
        cubePos.put(35,new Vector3(13.3f,1, 0.2f));
        cubePos.put(37,new Vector3(13.3f,1, 6.3f));
        cubePos.put(39,new Vector3(13.3f,1, 12.5f));

    }

    /**
     * Zmiana koloru sześcianu na ten który odpowiada właścicielowi pola
     * @param currentField pole
     */
    void changeCubeColor(Field currentField){
        switch (getActivePlayer().getId()){
            case 0:
                cubeInstance.replace(currentField.getId(), new ModelInstance(new ModelBuilder().createBox(1f,1f,1f,
                        new Material(ColorAttribute.createDiffuse(Color.RED)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
                break;
            case 1:
                cubeInstance.replace(currentField.getId(), new ModelInstance(new ModelBuilder().createBox(1f,1f,1f,
                        new Material(ColorAttribute.createDiffuse(Color.BLUE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
                break;
            case 2:
                cubeInstance.replace(currentField.getId(), new ModelInstance(new ModelBuilder().createBox(1f,1f,1f,
                        new Material(ColorAttribute.createDiffuse(Color.GREEN)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
                break;
            case 3:
                cubeInstance.replace(currentField.getId(), new ModelInstance(new ModelBuilder().createBox(1f,1f,1f,
                        new Material(ColorAttribute.createDiffuse(Color.YELLOW)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
                break;
        }
        cubeInstance.get(currentField.getId()).transform.translate(cubePos.get(currentField.getId()));
    }

    /**
     * Usunięcie gracza który chce sie poddać
     * @param player gracz
     */
    private void playerGiveUp(Player player){
        int i=0;
        player.setInGame(false);
        player.getPawn().setPawnVisable(false);
        for (PlayerInfoTable p:playersInfoList) {
            if(p.getPlayer()==player)
                p.setVisible(false);
        }
        for (Field f:setOfFields){
            if(f.getOwner()==player){
                f.setOwner(null);
                cubeInstance.replace(i,new ModelInstance(new ModelBuilder().createBox(1f,1f,1f,
                        new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal)));
                cubeInstance.get(i).transform.translate(cubePos.get(f.getId()));
            }
            i++;
        }
    }

    /**
     * Aktualizuje licznik odmierzający 20min
     */
    public void updateTimer(){
        if(timerRun){
            currentTime=LocalDateTime.now();
            Integer tmp = Math.toIntExact(ChronoUnit.SECONDS.between(startTime, currentTime));
            minutes = tmp/60;
            tmp-=minutes*60;
            seconds = tmp;

            counterLabel.setText((19-minutes)+":"+(59-seconds));
            if(19-minutes==0&&59-seconds==0)
            {
                timerRun=false;
               actionWindow.showWinWindow();
            }
        }
    }

    /**
     * Zwraca przycisk losujacy ilosc pól
     * @return przycisk
     */
    public TextButton getRandButton() {
        return randButton;
    }
}
