package com.ggg.monopoly;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Vector;

/**
 * Glowna klasa aplikacji zazadzajaca calym programem
 */
public class Engine extends Game {


	public MenuScreen menuScreen;

	private SettingsScreen settingsScreen;

	private ApplicationScreen applicationScreen;

	private NewGameScreen newGameScreen;

	static private Skin guiSkin;

	static Integer playerNumber=0;

	static Vector<String> playerNames = new Vector<>();


	public final static int MENU =0;

	public final static int SETTINGS =1;

	public final static int APPLICATION =2;

	public final static int NEWGAME =3;

	private Screen currentScreen;

	private Boolean debugMode;

	public Sound buttonSound;

	/**
	 * Inicjalizacja głównych elementów
	 */
	@Override
	public void create () {
		buttonSound = Gdx.audio.newSound(Gdx.files.internal("sounds/button-click.mp3"));

		debugMode = false;
		guiSkin= new Skin(Gdx.files.internal("skins/guiSkin/guiSkin.json"));
		menuScreen= new MenuScreen(this,guiSkin);
		currentScreen = menuScreen;
		setScreen(menuScreen);
	}

	/**
	 * Wyświetlanie obecnie aktywnej sceny
	 */
	@Override
	public void render () {

		currentScreen.render(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {

	}

	/**
	 * Zmiana wyświetlanej sceny na wskananą
	 * @param screen numer sceny
	 */
	public void changeScreen(int screen){
		switch (screen){
			case MENU:
				if(menuScreen == null) menuScreen = new MenuScreen(this,guiSkin);
				currentScreen = menuScreen;
				this.setScreen(menuScreen);
				break;
			case SETTINGS:
				if(settingsScreen == null) settingsScreen = new SettingsScreen(this,guiSkin,debugMode);
				currentScreen = settingsScreen;
				this.setScreen(settingsScreen);
				break;
			case NEWGAME:
				if(newGameScreen == null) newGameScreen = new NewGameScreen(this,guiSkin);
				currentScreen = newGameScreen;
				this.setScreen(newGameScreen);
				break;
			case APPLICATION:
				if(applicationScreen == null) applicationScreen = new ApplicationScreen(this,guiSkin);
				currentScreen = applicationScreen;
				this.setScreen(applicationScreen);
		}
	}

	/**
	 * Dostęp do zestawu tekstur interfajsu
	 * @return zestw tekstur interfejsu
	 */
	static public Skin getGuiSkin(){
		return guiSkin;
	}
}
