package com.shiven.gdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class XwingActor extends Actor {

    private SpriteBatch batch =  new SpriteBatch();
    private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("xwing.png")));
    private TextureAtlas atlasMove;
    private TextureAtlas atlasHit;
    private Animation<TextureRegion> moveAni;
    private Animation<TextureRegion> hitAni;
    private float timeForMove = 0.0f;
    private float timeForHit = 0.0f;
    private boolean hit=false;

    public XwingActor(){
        batch = new SpriteBatch();
        atlasMove = new TextureAtlas(Gdx.files.internal("xwings/xwingsheet.atlas"));
        atlasHit = new TextureAtlas(Gdx.files.internal("deadwings/pack.atlas"));
        moveAni = new Animation<TextureRegion>(1/10f, atlasMove.getRegions());
        hitAni = new Animation<TextureRegion>(1/5f, atlasHit.getRegions());
        setBounds(getX(),getY(), sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        timeForMove += Gdx.graphics.getDeltaTime();
        timeForHit += Gdx.graphics.getDeltaTime();

        if(hit){
            batch.draw(hitAni.getKeyFrame(timeForHit,false),getX(),getY());
            timeForMove=0;

            if(hitAni.isAnimationFinished(timeForHit))
                hit=false;
        }else{
            batch.draw(moveAni.getKeyFrame(timeForMove,true),getX(),getY());
            timeForHit=0;
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        sprite.setPosition(getX(),getY());

        TieActor tie = (TieActor)getStage().getActors().items[1];

        if(tie.getRect().overlaps(getRect())){
            hit();
            tie.removeTie();
            if(GDXGame.score!=0)
                GDXGame.score--;
        }
    }

    public void moveAcross(int pos){
        MoveToAction followTie = new MoveToAction();
        followTie.setPosition(pos,getY());
        followTie.setDuration(6f);
        addAction(followTie);
    }

    public void hit(){
        hit = !hit;
    }

    public Rectangle getRect(){
        return sprite.getBoundingRectangle();
    }
}
