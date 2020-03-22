import clock.ClockHandler;
import com.sun.javafx.geom.Vec3f;
import content.Context;
import content.Resources;
import entity.Entity;
import entity.EntityHandler;
import entity.component.types.*;
import input.KeyEvent;
import input.KeyMapper;
import input.MotionEvent;
import input.Mouse;
import system.SystemHandler;
import system.types.*;
import ui.ActivityManager;
import ui.activity.RecyclerViewActivity;
import util.FileLoader;
import util.Logger;
import util.Vector2Df;
import visualisation.MainWindow;
import visualisation.Texture;
import visualisation.TextureManager;
import visualisation.Visualisation;

import java.io.File;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class Main implements MainWindow.OnWindowCloseListener {
    private MainWindow mainWindow;
    private SystemHandler systemHandler = new SystemHandler();
    private EntityHandler entityHandler = new EntityHandler();



    private void run (String[] args) {
        mainWindow = new MainWindow();
        mainWindow.setOnWindowCloseListener(this);

        if (init() == false) {
            System.exit(0);
        }
        worldInit();
        loop();

        mainWindow.destroy();
    }

    private boolean init () {
        Logger.debug("Debug messages enabled.");

        Context context = new Context();
        Resources resources = context.getResources();
        resources.init();



        if (mainWindow.init(600, 400) == false) {
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
        visualisation.setWindowDimensions(600, 400);

        // load textures
        //Texture texture = TextureManager.getInstance().addTexture("textures/test_texture.png");
        Texture texture = TextureManager.getInstance().addTexture(resources.getAssetFilePath(R.textures.test_texture_png));
        texture.addTextureRegion(new Vector2Df(0, 0), new Vector2Df(9, 9));

        //texture = TextureManager.getInstance().addTexture("textures/tiles.png");
        texture = TextureManager.getInstance().addTexture(resources.getAssetFilePath(R.textures.tiles_png));
        for (int i = 0; i < 21; i++) {
            texture.addTextureRegion(new Vector2Df(64 * i, 0), new Vector2Df(64, 64));
        }

        Visualisation.getInstance().loadFonts();

        ActivityManager.getInstance().addActivity(new TestActivity());
        ActivityManager.getInstance().addActivity(new RecyclerViewActivity());

        Logger.info("getResource2 getPath "+ Main.class.getClassLoader().getResource("test.txt").getPath());

        return true;
    }

    private void worldInit () {



        Entity entity = entityHandler.createEntity();
        entity.addComponent(new Position());
        entity.addComponent(new Collision(new Vector2Df(25, 25)));
        entity.addComponent(new Movement(new Vector2Df(100, 100)));
        entity.addComponent(new BasicTexturedSquare(new Vector2Df(25, 25), 0, 0));
        entity.addComponent(new KeyboardController());

        entity = entityHandler.createEntity();
        entity.addComponent(new Position(45));
        entity.addComponent(new BasicTexturedSquare(new Vector2Df(100, 100), 0, 0));
        entity.addComponent(new Collision(new Vector2Df(100, 100)));
        entity.addComponent(new Movement(new Vector2Df(100, 300), //startPosition
                new Vector2Df(0, 0), //startVelocity
                new Vector2Df(0, 0) //acceleration
        ));

        entity = entityHandler.createEntity();
        entity.addComponent(new Position());
        entity.addComponent(new BasicColouredSquare(new Vector2Df(100, 100), new Vec3f(0f, 1f, 0f)));
        entity.addComponent(new Collision(new Vector2Df(100, 100)));
        entity.addComponent(new Movement(new Vector2Df(200, 300), //startPosition
                new Vector2Df(0, 0), //startVelocity
                new Vector2Df(0, 0) //acceleration
        ));

        entity = entityHandler.createEntity();
        entity.addComponent(new Position());
        entity.addComponent(new BasicColouredSquare(new Vector2Df(100, 100), new Vec3f(1f, 0f, 0f)));
        entity.addComponent(new Collision(new Vector2Df(100, 100)));
        entity.addComponent(new Movement(new Vector2Df(300, 300), //startPosition
                new Vector2Df(0, 0), //startVelocity
                new Vector2Df(0, 0) //acceleration
        ));

        entity = entityHandler.createEntity();
        entity.addComponent(new Position(20, 20));
        entity.addComponent(new MapSquare(new Vector2Df(8, 4), new Vector2Df(64, 64)));


        systemHandler.addSystem(new KeyboardControllerSystem());
        systemHandler.addSystem(new MovementSystem());
        systemHandler.addSystem(new CollisionSystem());
        systemHandler.addSystem(new MapRenderSystem());
        systemHandler.addSystem(new RenderTextureSystem());
        systemHandler.addSystem(new RenderColourSystem());

        systemHandler.init(entityHandler);
    }

    private void loop () {

        Visualisation visualisation = Visualisation.getInstance();

        List<File> fileList = FileLoader.listFiles("test");
        for(int i=0; i < fileList.size(); i++){
            Logger.info(fileList.get(i).getAbsolutePath());
        }


        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.

        while (!mainWindow.shouldClose()) {





            ClockHandler.getInstance().update();

            visualisation.setWindowDimensions(mainWindow.getWidth(), mainWindow.getHeight());
            visualisation.initProjection();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer



            visualisation.clearMatrixStack();

            MotionEvent motionEvent = Mouse.getInstance().getNextMotionEvent();
            while (motionEvent != null){
                ActivityManager.getInstance().dispatchTouchEvent(motionEvent);
                motionEvent = Mouse.getInstance().getNextMotionEvent();
            }

            KeyMapper.getInstance().update();

            KeyEvent keyEvent = KeyMapper.getInstance().getNextKeyEvent();
            while (keyEvent != null){
                ActivityManager.getInstance().dispatchKeyEvent(keyEvent);
                keyEvent = KeyMapper.getInstance().getNextKeyEvent();
            }

            systemHandler.update();
            ActivityManager.getInstance().update();
            ActivityManager.getInstance().renderActivityList();

            visualisation.finalise();

            glfwPollEvents();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main (String[] args) {
        new Main().run(args);
    }

    @Override
    public void onWindowClose () {

    }
}
