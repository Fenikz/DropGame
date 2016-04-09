package com.sirpomidor.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

public class GameScreen implements Screen {

    final Drop game;
    SpriteBatch batch;
    OrthographicCamera camera;

    Texture bucketImage;
	Sound dropSound;
	Music rainMusic;
    Rectangle bucket;
    Vector3 touchPos;
    Array<Texture> dropsTextures;
    long lastDropTime;

    int dropCatched;
    Array<DropElement> dropElements;


    public GameScreen (final Drop game) {

        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();

        // TODO: 09.04.2016 Выделить bucket в отдельный класс с изменяемым состояением
        bucketImage = new Texture("bucket.png");

        dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));

        rainMusic.setLooping(true);
        rainMusic.play();

        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 /2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;

        touchPos = new Vector3();

        dropsTextures = new Array<Texture>();
        dropsTextures.add(new Texture(Gdx.files.internal("mercedes.png")));
        dropsTextures.add(new Texture(Gdx.files.internal("burger.png")));
        dropsTextures.add(new Texture(Gdx.files.internal("beer.png")));
        // TODO: 09.04.2016 Добавить текстуру деньги
        // TODO: 09.04.2016 Добавить текстуру каки

        dropElements = new Array<DropElement>();
        spawnDrop();
    }

	@Override
	public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        game.font.draw(game.batch, "Drops: " + dropCatched, 0, 480);
        game.batch.draw(bucketImage, bucket.x, bucket.y);

        for (DropElement dropElement : dropElements) {
            game.batch.draw(dropElement.getTexture(), dropElement.getX(), dropElement.getY(),
                    dropElement.getWidth(), dropElement.getHeight());
        }

        game.batch.end();

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = (int) touchPos.x - 64 / 2;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bucket.x += 200 * Gdx.graphics.getDeltaTime();
        }

        if (bucket.x < 0) {
            bucket.x = 0;
        }

        if (bucket.x > 800 - 64) {
            bucket.x = 800 - 64;
        }

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            spawnDrop();
        }

        Iterator<DropElement> iterator = dropElements.iterator();

        while (iterator.hasNext()) {
            DropElement dropElement = iterator.next();
            dropElement.setY((int) (dropElement.getY() - 200 * Gdx.graphics.getDeltaTime()));
            if (dropElement.getY() + 64 < 0) {
                iterator.remove();
            }
            // TODO: 09.04.2016 Добавить условие Overlaps
            if (dropElement.overlaps(bucket)) {
                dropCatched++;
                dropSound.play();
                iterator.remove();
            }
        }
	}

    private void spawnDrop() {
        DropElement dropElement = new DropElement(MathUtils.random(0, 800 - 64), 480,
                64, 64, dropsTextures.get(MathUtils.random(2)));
        dropElements.add(dropElement);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void dispose() {
        dropSound.dispose();
        bucketImage.dispose();
        rainMusic.dispose();
        batch.dispose();

        for (DropElement dropElement : dropElements) {
            dropElement.dispose();
        }
    }

    @Override
    public void show() {
        rainMusic.play();
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
}
