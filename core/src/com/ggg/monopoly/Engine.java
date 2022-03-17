package com.ggg.monopoly;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Engine extends Game {
	private LoadingScreen loadingScreen;
	public MenuScreen menuScreen;
	private SettingsScreen settingsScreen;
	private ApplicationScreen applicationScreen;

	public final static int MENU =0;
	public final static int SETTINGS =1;
	public final static int APPLICATION =2;
	public final static int LOADING =3;
	private Screen currentScreen;



	
	@Override
	public void create () {

		loadingScreen = new LoadingScreen(this);
		currentScreen = loadingScreen;
		setScreen(loadingScreen);
	}

	@Override
	public void render () {

		currentScreen.render(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {

	}
	public void changeScreen(int screen){
		switch (screen){
			case MENU:
				if(menuScreen == null) menuScreen = new MenuScreen(this);
				currentScreen = menuScreen;
				this.setScreen(menuScreen);
				break;
			case SETTINGS:
				if(settingsScreen == null) settingsScreen = new SettingsScreen(this);
				currentScreen = settingsScreen;
				this.setScreen(settingsScreen);
				break;
			case APPLICATION:
				if(applicationScreen == null) applicationScreen = new ApplicationScreen(this);
				currentScreen = applicationScreen;
				this.setScreen(applicationScreen);
		}
	}
}