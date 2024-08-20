package org.example;

import static org.lwjgl.opengl.GL11.glViewport;

public class WindowResizeListener {

    public static void resizeCallback (long glfwWindow, int width, int height){
        Main.width = width;
        Main.height = height;

        int aspectWidth = width;
        int aspectHeight = (int)((float)aspectWidth / Main.TargetAR);

        if (aspectHeight > height){
            aspectHeight = height;
            aspectWidth = (int)((float)aspectHeight * Main.TargetAR);
        }

        int vpX = (int) ((float)width / 2f - ((float)aspectWidth/2f));
        int vpY = (int) ((float)height / 2f - ((float)aspectHeight/2f));

        glViewport(vpX, vpY, aspectWidth, aspectHeight);
    }
}
