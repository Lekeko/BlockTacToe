package org.example.keyBinds;

import org.example.Main;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class Movekeys {

    public static void initKeyBinds(long window){
        DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);


        glfwSetMouseButtonCallback(window, ((window1, button, action, mods) -> {
            if (button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS) {
                Main.pressedButton = true;
            }
        }));


        glfwSetKeyCallback(window, (window1, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_KP_ADD && action == GLFW_PRESS){
                Main.shouldGrow = true;
            }
            if (key == GLFW_KEY_KP_ADD && action == GLFW_RELEASE){
                Main.shouldGrow = false;
            }

            if (key == GLFW_KEY_R){
                Main.shouldReset();
            }

            if (action == GLFW_PRESS){
                if (key == GLFW_KEY_W || key == GLFW_KEY_UP) {
                    Main.shouldMoveUp = true;
                }

                if (key == GLFW_KEY_A || key == GLFW_KEY_LEFT) {
                    Main.shouldMoveLeft = true;
                }

                if (key == GLFW_KEY_S || key == GLFW_KEY_DOWN) {
                    Main.shouldMoveDown = true;
                }

                if (key == GLFW_KEY_D || key == GLFW_KEY_RIGHT) {
                    Main.shouldMoveRight = true;
                }
            }

            if (action == GLFW_RELEASE){
                if (key == GLFW_KEY_W || key == GLFW_KEY_UP) {
                    Main.shouldMoveUp = false;
                }

                if (key == GLFW_KEY_A || key == GLFW_KEY_LEFT) {
                    Main.shouldMoveLeft = false;
                }

                if (key == GLFW_KEY_S || key == GLFW_KEY_DOWN) {
                    Main.shouldMoveDown = false;
                }

                if (key == GLFW_KEY_D || key == GLFW_KEY_RIGHT) {
                    Main.shouldMoveRight = false;
                }
            }



        });

    }



}
