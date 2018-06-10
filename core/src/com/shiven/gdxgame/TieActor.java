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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class TieActor extends Actor{

    private SpriteBatch batch =  new SpriteBatch();
    private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("tiefighter.png")));
    private TextureAtlas atlasMove;
    private TextureAtlas atlasHit;
    private Animation<TextureRegion> moveAni;
    private Animation<TextureRegion> hitAni;
    private float timeForMove = 0.0f;
    private float timeForHit = 0.0f;
    private boolean hit=false;

    public TieActor(){
        batch = new SpriteBatch();
        atlasMove = new TextureAtlas(Gdx.files.internal("ties/tiefighter.atlas"));
        atlasHit = new TextureAtlas(Gdx.files.internal("deadties/deadfighter.atlas"));
        moveAni = new Animation<TextureRegion>(1/5f, atlasMove.getRegions());
        hitAni = new Animation<TextureRegion>(1/5f, atlasHit.getRegions());
        setBounds(getX(),getY(),sprite.getWidth(),sprite.getHeight());
        setTouchable(Touchable.enabled);

        TieTouchListener touchListener = new TieTouchListener();
        addListener(touchListener);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //batch.draw(texture,x,y);
        //sprite.draw(batch);
        timeForMove += Gdx.graphics.getDeltaTime();
        timeForHit += Gdx.graphics.getDeltaTime();

        if(hit){
            batch.draw(hitAni.getKeyFrame(timeForHit,false),getX(),getY());
            timeForMove=0;

            if(hitAni.isAnimationFinished(timeForHit)){
                removeTie();
                GDXGame.score++;
            }
        }else{
            batch.draw(moveAni.getKeyFrame(timeForMove,true),getX(),getY());
            timeForHit=0;
        }

    }

    @Override
    protected void positionChanged() {
        sprite.setPosition(getX(),getY());
        super.positionChanged();
    }

    public void start(){
        setX((float) (Math.random() * getStage().getWidth()));
        setY(getStage().getHeight());
        setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());

        if( !(getX()+getWidth()<getStage().getWidth() && getY()+getHeight()<getStage().getHeight()) ) {
            setX((float) (Math.random() * getStage().getWidth()));
            setY(getStage().getHeight());
            setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
        }

        moveDownScreen();
    }

    public void moveDownScreen(){
        SequenceAction actionSeq = new SequenceAction();
        MoveToAction moveToBottom = new MoveToAction();
        moveToBottom.setPosition(getStage().getWidth()/2,-300);
        moveToBottom.setDuration(6f);
        actionSeq.addAction(moveToBottom);
        actionSeq.addAction(Actions.removeActor());
        addAction(actionSeq);
    }

    public void removeTie(){
        addAction(Actions.removeActor());
    }

    public class TieTouchListener extends InputListener{
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            hit = true;
            return hit;
        }
    }

    public Rectangle getRect(){
        return sprite.getBoundingRectangle();
    }
}
