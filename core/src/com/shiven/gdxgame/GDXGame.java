package com.shiven.gdxgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GDXGame extends ApplicationAdapter {
	private Stage stage;
	public static int score=0;
	private SpriteBatch spriteBatch;
	private BitmapFont bitmapFont;
	private XwingActor xwing;
	
	@Override
	public void create () {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		spriteBatch = new SpriteBatch();
		bitmapFont = new BitmapFont();
		bitmapFont.getData().setScale(4);

		xwing = new XwingActor();
		stage.addActor(xwing);
		TieActor tieActor = new TieActor();
		stage.addActor(tieActor);
		tieActor.start();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(126/256f, 192/256f, 238/256f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		spriteBatch.begin();
			//bitmapFont.draw(spriteBatch,"POINTS: "+score,200,300);
		bitmapFont.draw(spriteBatch,"POINTS: "+score, 5, 100);
		spriteBatch.end();

		if(stage.getActors().size==1){
			TieActor tieActor = new TieActor();
			stage.addActor(tieActor);
			tieActor.start();
		}

		TieActor tie = (TieActor)stage.getActors().items[1];

		xwing.moveAcross((int)tie.getX());

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
}
