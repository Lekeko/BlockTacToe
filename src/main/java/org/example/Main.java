package org.example;


import org.example.keyBinds.GameLogic;
import org.example.keyBinds.Movekeys;
import org.example.keyBinds.ObjectsToRender;
import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {

    public static int shouldFollowCursor = 1;
    public static boolean shouldGrow = false;
    public static boolean shouldDecrease = false;
    public static int winner = 0;
    public static boolean shouldMoveUp = false;
    public static boolean shouldMoveDown = false;
    public static boolean shouldMoveLeft = false;
    public static boolean shouldMoveRight = false;
    public static boolean pressedButton = false;
    private long window;
    public static float x = 0.010f;
    public static float y = 0.010f;
    public static float size = 30f;
    public static boolean gameFinished = false;

    public static int symbolUsed = 1;

    public static final float GRAVITY_ACCELERATOR = 0.1f;
    public static float yVelocity = 0;

    public static int width = 1000;
    public static int height =  1000;
    public static int TargetWidth =  1000;
    public static int TargetHeight =  1000;
    public static float TargetAR =  (float)TargetWidth / (float)TargetHeight;

    public static boolean followCursorNoMatterWhat = false;

    private static int count = 0;
    static float[] xQueue;
    static float[] yQueue;
    static Color[] colorQueue;

   public static ArrayList<ObjectsToRender> objects = new ArrayList<>();

    public static void shouldReset() {
        count = 0;
        objects.clear();
        for (int i = 0; i <= 2; i++)
            for (int j = 0; j <= 2; j++)
                GameLogic.symbol[i][j] = 0;
        gameFinished = false;
    }


    public void run(){

        System.out.println("Test checkers on lwjgl : " + Version.getVersion());


        init();
        loop();
        yQueue = new float[100];
        xQueue = new float[100];
        colorQueue = new Color[100];

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);


        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }


    private void loop() {

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        //Set clear color
        Color backgroundColor = new Color(88, 59, 39, 255);
        glClearColor((float) backgroundColor.getRed() / 256, (float) backgroundColor.getGreen() / 256, (float) backgroundColor.getBlue() / 256, 1);

        //The classic run loop that will run until the user makes the window closeable
        while (!glfwWindowShouldClose(window)){
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);// clear the frame buffer

            DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
            DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
            //windowStuff

            IntBuffer widthBuffer= BufferUtils.createIntBuffer(1);
            IntBuffer heightBuffer= BufferUtils.createIntBuffer(1);
            glfwGetWindowSize(window, widthBuffer, heightBuffer);
            width = widthBuffer.get(0);
            height = heightBuffer.get(0);
            WindowResizeListener.resizeCallback(window, width, height);


            glfwGetCursorPos(window, xBuffer, yBuffer);
            double cursorPosX = (float) xBuffer.get(0);
            double cursorPosY = (float) yBuffer.get(0);
            //System.out.println(xBuffer.get(0));
            Color color1 = new Color(217, 52, 52, 255);
            Color color2 = new Color(52, 184, 217, 255);

            RenderQuad(0, -80f , 20f, symbolUsed % 2 == 0 ? color1 : color2);


            if (pressedButton && !gameFinished) {
                if (Mouse.isCursorInObject(cursorPosX, cursorPosY, x, y, size, symbolUsed)){
                    Main.pressedButton = false;
                    if (winner != 0) {
                        gameFinished = true;
                        System.out.println(symbolUsed % 2 != 0 ? "Red won!" : "Blue won!");
                    }
                }else pressedButton = false;
            }


            for (int i = 0; i < objects.size(); i++){
                RenderQuad(
                        objects.get(i).xQueue,
                        objects.get(i).yQueue,
                        35,
                        objects.get(i).colorQueue
                );
            }


            int xCenter = 0;
            int yCenter = 0;


            Color color = new Color(217, 207, 183, 255);

            for (float i = -1; i <= 1; i++)
                for (float j = -1; j <= 1 ; j++){
                    RenderQuad(xCenter + i * size * 1.4f, yCenter + j * size * 1.4f, size, color);
                }





            glfwSwapBuffers(window); //swap the color buffers

            //Poll the window events. The key callback above will only be invoked during this call
            glfwPollEvents();
        }

    }

    public static void RenderQuad(float x, float y, float size, Color color) {
        RenderTriangle(x, y, size, color, true);
        RenderTriangle(x, y, size, color, false);
    }

    private static void RenderTriangle(float x , float y, float size, Color color, boolean inverted) {
            x /= 100;
            y /= 100;
            size /= 100;

            float invertedModeler = 1;

            if (inverted) {
                invertedModeler = -1;
                color = new Color(color.getRed() - 24, color.getGreen() - 24, color.getBlue() - 24, color.getAlpha());
            }

            glBegin(GL_TRIANGLES);

            float sizeoffset = size/2;

            glColor4f((float) color.getRed() / 256, (float) color.getGreen() / 256, (float) color.getBlue() / 256, color.getAlpha());

            glVertex2f(x - sizeoffset, y + sizeoffset );
            glVertex2f(x + sizeoffset * invertedModeler, y + sizeoffset * invertedModeler);
            glVertex2f(x + (float) sizeoffset, y - (float) sizeoffset);


            glEnd();

    }


    private void init() {
        //print possible errors
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        //config
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // make the window not visible
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // make the window resizeable

        //window creation
        window = glfwCreateWindow(width, height, "Checkers Test", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create GLFW window");

        //Key callbacks
        glfwSetKeyCallback(window, ((window1, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window1, true);
        }));


        Movekeys.initKeyBinds(window);



        try (MemoryStack stack = MemoryStack.stackPush()){
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            //this creates the window size, the params are supplied by the glfwCreateWindow at //window creation
            glfwGetWindowSize(window, pWidth, pHeight);

            //get the resolution of the primary monitor
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            //center the window
            glfwSetWindowPos(
                    window,
                    (vidMode.width() - width) / 2,
                    (vidMode.height() - height) / 2
            );
        }

        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    public static void main(String[] args) {
        new Main().run();

    }
}