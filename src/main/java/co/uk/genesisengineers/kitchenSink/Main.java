package co.uk.genesisengineers.kitchenSink;

import co.uk.genesisengineers.core.clock.ClockHandler;
import co.uk.genesisengineers.kitchenSink.activites.mapEditor.MapEditorActivity;
import co.uk.genesisengineers.kitchenSink.activites.mapEditor.MapEditorToolsActivity;
import co.uk.genesisengineers.kitchenSink.entityComponent.*;
import co.uk.genesisengineers.kitchenSink.system.*;
import co.uk.genesisengineers.core.content.entityPrototypeFactory.EntityPrototypeFactory;
import co.uk.genesisengineers.kitchenSink.entityComponent.factory.EntityPrototypeFactoryJSON;
import co.uk.genesisengineers.core.drawable.DrawableManager;
import co.uk.genesisengineers.core.entity.Entity;
import co.uk.genesisengineers.core.events.Event;
import co.uk.genesisengineers.core.events.EventManager;
import org.lwjgl.PointerBuffer;
import org.lwjgl.util.nfd.NativeFileDialog;
import co.uk.genesisengineers.core.shape.ShapeManager;
import co.uk.genesisengineers.core.entity.EntityHandler;
import co.uk.genesisengineers.core.input.KeyEvent;
import co.uk.genesisengineers.core.input.KeyMapper;
import co.uk.genesisengineers.core.input.MotionEvent;
import co.uk.genesisengineers.core.input.Mouse;
import co.uk.genesisengineers.core.system.SystemHandler;
import co.uk.genesisengineers.core.ui.ActivityManager;
import co.uk.genesisengineers.core.util.FileLoader;
import co.uk.genesisengineers.core.util.Logger;
import co.uk.genesisengineers.core.util.Vector2Df;
import co.uk.genesisengineers.core.visualisation.MainWindow;
import co.uk.genesisengineers.core.visualisation.TextureManager;
import co.uk.genesisengineers.core.visualisation.Visualisation;

import java.io.File;
import java.util.Date;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.util.nfd.NativeFileDialog.*;

public class Main implements MainWindow.OnWindowCloseListener {
    private MainWindow mainWindow;
    private SystemHandler systemHandler = new SystemHandler();
    private EntityHandler entityHandler = new EntityHandler();
    private ActivityManager activityManager;
    private ApplicationContext applicationContext;

    private ShapeManager shapeManager = new ShapeManager();
    private DrawableManager drawableManager;

    private EntityPrototypeFactory entityPrototypeFactory;

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

        Visualisation.getInstance().loadFont(applicationContext, R.fonts.arial_png, R.fonts.arial_fnt);

        applicationContext.getResources().loadColors(applicationContext, R.values.colors_json);

        shapeManager.loadShapes(applicationContext,
                applicationContext.getResources().getAssetsOfType(R.shapes.TYPE));

        drawableManager =  DrawableManager.createInstance(shapeManager, TextureManager.getInstance());
        drawableManager.load(
                applicationContext,
                applicationContext.getResources().getAssetsOfType(R.drawables.TYPE));

        drawableManager.createColorDrawables(
                applicationContext.getResources().getColorList(),
                shapeManager.getShape(R.shapes.square_top_left_json));

        drawableManager.createTextureDrawables(
                TextureManager.getInstance().getTextures(),
                shapeManager.getShape(R.shapes.square_top_left_json));

        entityPrototypeFactory = new EntityPrototypeFactoryJSON(applicationContext);
        entityPrototypeFactory.loadEntities(
                applicationContext.getResources().getAssetFileAsString(R.entities.entityList_json));
        return true;
    }

    private void worldInit () {

       entityHandler.addEntity(entityPrototypeFactory.cloneEntity("player"));

        Entity entity;

        entity = entityHandler.createEntity();
        entity.addComponent(new Position(0, 0));
        entity.addComponent(new MapSquare(R.drawables.tiles_json, new Vector2Df(20, 20), new Vector2Df(64, 64)));
        entity.getId();


        systemHandler.addSystem(new KeyboardControllerSystem());
        systemHandler.addSystem(new MovementSystem());
        systemHandler.addSystem(new CollisionSystem());
        systemHandler.addSystem(new MapRenderSystem());
        systemHandler.addSystem(new RenderDrawableSystem(drawableManager));
        systemHandler.addSystem(new MouseSelectSystem());
        systemHandler.addSystem(new MapEditorSystem());

        systemHandler.init(entityHandler);

        ActivityManager.getInstance().addActivity(new MapEditorActivity(entity));
        ActivityManager.getInstance().addActivity(new MapEditorToolsActivity());
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

    private static void checkResult(int result, PointerBuffer path) {
        switch (result) {
            case NFD_OKAY:
                System.out.println("Success!");
                System.out.println(path.getStringUTF8(0));
                nNFDi_Free(path.get(0));
                break;
            case NFD_CANCEL:
                System.out.println("User pressed cancel.");
                break;
            default: // NFD_ERROR
                System.err.format("Error: %s\n", NativeFileDialog.NFD_GetError());
        }
    }


    private static void openSingle() {
        PointerBuffer outPath = memAllocPointer(1);
        try {
            checkResult(
                    NFD_OpenDialog("png,jpg;pdf", null, outPath),
                    outPath
            );
        } finally {
            memFree(outPath);
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

            Event event = EventManager.getInstance().getNext();
            while (event != null){
                systemHandler.dispatchEvent(event);
                event = EventManager.getInstance().getNext();
            }

            systemHandler.update();

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
