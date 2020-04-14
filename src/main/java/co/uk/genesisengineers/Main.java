package co.uk.genesisengineers;

import clock.ClockHandler;
import co.uk.genesisengineers.activites.TestActivity;
import co.uk.genesisengineers.kitchenSink.recyclerViewTest.RecyclerViewActivity;
import com.sun.javafx.geom.Vec3f;
import content.entityPrototypeFactory.EntityPrototypeFactory;
import content.entityPrototypeFactory.json.EntityPrototypeFactoryJSON;
import drawable.DrawableManager;
import entity.Entity;
import shape.ShapeManager;
import entity.EntityHandler;
import entity.component.types.*;
import input.KeyEvent;
import input.KeyMapper;
import input.MotionEvent;
import input.Mouse;
import system.SystemHandler;
import system.types.*;
import ui.ActivityManager;
import util.FileLoader;
import util.Logger;
import util.Vector2Df;
import visualisation.MainWindow;
import visualisation.Texture;
import visualisation.TextureManager;
import visualisation.Visualisation;

import co.uk.genesisengineers.kitchenSink.R;

import java.io.File;
import java.util.Date;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class Main implements MainWindow.OnWindowCloseListener {
    private MainWindow mainWindow;
    private SystemHandler systemHandler = new SystemHandler();
    private EntityHandler entityHandler = new EntityHandler();
    private ActivityManager activityManager;
    private ApplicationContext applicationContext;

    private ShapeManager shapeManager = new ShapeManager();
    private DrawableManager drawableManager;

    private EntityPrototypeFactory entityPrototypeFactory = new EntityPrototypeFactoryJSON();

    private void run (String[] args) {
        mainWindow = new MainWindow();
        mainWindow.setOnWindowCloseListener(this);

        if (!init()) {
            System.exit(0);
        }
        worldInit();
        loop();

        mainWindow.destroy();
    }

    private boolean init () {
        Logger.debug("Debug messages enabled.");

        applicationContext = new ApplicationContext();

        activityManager = ActivityManager.createInstance(applicationContext);

        int windowWidth = 1200;
        int windowHeight = 800;


        if (!mainWindow.init(windowWidth, windowHeight)) {
            return false;
        }

        // key mapper / handler
        KeyMapper keyMapper = KeyMapper.getInstance();
        keyMapper.init(mainWindow.getWindow());

        // mouse handler
        Mouse mouse = Mouse.getInstance();
        mouse.init(mainWindow.getWindow());

        // visualisation
        Visualisation visualisation = Visualisation.getInstance();
        visualisation.init(mainWindow.getWindow());
        visualisation.setWindowDimensions(windowWidth, windowHeight);

        TextureManager.getInstance().load(
                applicationContext,
                applicationContext.getResources().getAssetsOfType(R.textures.TYPE)
        );

        //TODO: make this dynamic loading for all fonts
        Visualisation.getInstance().loadFont(applicationContext, R.fonts.arial_png, R.fonts.arial_fnt);

        applicationContext.getResources().loadColors(applicationContext, R.values.colors_json);

        String value = applicationContext.getResources().getColor(R.color.red).hexValue;

        shapeManager.loadShapes(applicationContext,
                applicationContext.getResources().getAssetsOfType(R.shapes.TYPE));

        drawableManager = new DrawableManager(shapeManager, TextureManager.getInstance());
        drawableManager.load(
                applicationContext,
                applicationContext.getResources().getAssetsOfType(R.drawables.TYPE));

        drawableManager.createColorDrawables(
                applicationContext.getResources().getColorList(),
                shapeManager.getShape(R.shapes.square_json));

        ActivityManager.getInstance().addActivity(new TestActivity());
        ActivityManager.getInstance().addActivity(new RecyclerViewActivity());

        entityPrototypeFactory.loadEntities(
                applicationContext.getResources().getAssetFileAsString(R.entities.entityList_json));
        return true;
    }

    private void worldInit () {

       entityHandler.addEntity(entityPrototypeFactory.cloneEntity("player"));

        Entity entity;

        entity = entityHandler.createEntity();
        entity.addComponent(new Position(45));
        entity.addComponent(new BasicDrawable(new Vector2Df(100, 100), R.drawables.text_json, 45));
        entity.addComponent(new Collision(new Vector2Df(100, 100)));
        entity.addComponent(new Movement(new Vector2Df(100, 300), //startPosition
                new Vector2Df(0, 0), //startVelocity
                new Vector2Df(0, 0) //acceleration
        ));

        entity = entityHandler.createEntity();
        entity.addComponent(new Position());
        entity.addComponent(new BasicDrawable(new Vector2Df(100, 100), R.drawables.redSquare_json, 0));
        entity.addComponent(new Collision(new Vector2Df(100, 100)));
        entity.addComponent(new Movement(new Vector2Df(200, 300), //startPosition
                new Vector2Df(0, 0), //startVelocity
                new Vector2Df(0, 0) //acceleration
        ));

        entity = entityHandler.createEntity();
        entity.addComponent(new Position());
        entity.addComponent(new BasicDrawable(new Vector2Df(100, 100), R.drawables.greenCircle_json, 45));
        entity.addComponent(new Collision(new Vector2Df(50, 50)));
        entity.addComponent(new Select(new Vector2Df(60, 60), 0,0,false));
        entity.addComponent(new Movement(new Vector2Df(300, 300), //startPosition
                new Vector2Df(0, 0), //startVelocity
                new Vector2Df(0, 0) //acceleration
        ));

//        entity = entityHandler.createEntity();
//        entity.addComponent(new Position(20, 20));
//        entity.addComponent(new MapSquare(new Vector2Df(8, 4), new Vector2Df(64, 64)));


        systemHandler.addSystem(new KeyboardControllerSystem());
        systemHandler.addSystem(new MovementSystem());
        systemHandler.addSystem(new CollisionSystem());
        systemHandler.addSystem(new MapRenderSystem());
        systemHandler.addSystem(new RenderTextureSystem());
        systemHandler.addSystem(new RenderColourSystem());
        systemHandler.addSystem(new RenderDrawableSystem(drawableManager));
        systemHandler.addSystem(new MouseSelectSystem());

        systemHandler.init(entityHandler);
    }

    private long getCurrentTime(){
        return (new Date()).getTime();
    }

    private long lastUpdateTime = getCurrentTime();
    private int sleepTime = 0;

    private int calculateThreadSleepTime(){
        long currentTime = getCurrentTime();
        long timeDiff = (currentTime - lastUpdateTime);
        lastUpdateTime = currentTime;

        if(timeDiff < 16){
            return Math.toIntExact(timeDiff - 16);
        } else {
            return 0;
        }
    }
    private void sleepThread(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loop () {

        Visualisation visualisation = Visualisation.getInstance();

        List<File> fileList = FileLoader.listFiles("test");
        for (File aFileList : fileList) {
            Logger.info(aFileList.getAbsolutePath());
        }

        lastUpdateTime = getCurrentTime();

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!mainWindow.shouldClose()) {

            sleepTime = calculateThreadSleepTime();

            ClockHandler.getInstance().update();

            visualisation.setWindowDimensions(mainWindow.getWidth(), mainWindow.getHeight());
            visualisation.initProjection();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            visualisation.clearMatrixStack();

            MotionEvent motionEvent = Mouse.getInstance().getNextMotionEvent();
            while (motionEvent != null){
               if(!ActivityManager.getInstance().dispatchTouchEvent(motionEvent)){
                   systemHandler.dispatchTouchEvent(motionEvent);
               }
                motionEvent = Mouse.getInstance().getNextMotionEvent();
            }

            KeyMapper.getInstance().update();
            KeyEvent keyEvent = KeyMapper.getInstance().getNextKeyEvent();
            while (keyEvent != null){
                if(!ActivityManager.getInstance().dispatchKeyEvent(keyEvent)){
                    systemHandler.dispatchKeyEvent(keyEvent);
                }
                keyEvent = KeyMapper.getInstance().getNextKeyEvent();
            }

            systemHandler.update();

            drawableManager.draw(R.drawables.redSquare_json, new Vector2Df(100, 100), new Vector2Df(100, 100), 0);
            drawableManager.draw(R.drawables.ringGreen_json, new Vector2Df(200, 100), new Vector2Df(100, 100), 0);
            drawableManager.draw(R.drawables.blueCircle_json, new Vector2Df(300, 100), new Vector2Df(100, 100), 0);
            ActivityManager.getInstance().update();
            ActivityManager.getInstance().renderActivityList();

            visualisation.finalise();

            glfwPollEvents();

            if(sleepTime > 0) sleepThread(sleepTime);
        }
    }

    public static void main (String[] args) {
        new Main().run(args);
    }

    @Override
    public void onWindowClose () {

    }
}
