package org.example;

import org.example.keyBinds.GameLogic;
import org.example.keyBinds.ObjectsToRender;

public class Mouse {
    public static boolean isCursorInObject(double cursorPosX, double cursorPosY, float x, float y, float size, int symbolused) {
        int width = Main.width;
        int height = Main.height;
        int xCenter = width/2;
        int yCenter = height/2;
        float sizeoffset = size * 2.5f;

        for (float i = -1; i <= 1; i++) {
            for (float j = -1; j <= 1; j++) {
                    double boxCoordsX = xCenter + i * size * 7.0f;
                    double boxCoordsY = yCenter + j * size * 7.0f;

                    if (cursorPosX < boxCoordsX + sizeoffset && cursorPosX > boxCoordsX - sizeoffset &&
                            cursorPosY > boxCoordsY - sizeoffset && cursorPosY < boxCoordsY + sizeoffset && GameLogic.symbol[(int) (i+1)][(int) (j+1)] == 0) {
                        GameLogic.move(symbolused, (int) (i+1), (int) (j+1));
                        GameLogic.drawSymbol(0 + i * size * 1.4f, 0 + j * size * 1.4f);
                        GameLogic.currentSymbol = symbolused;

                        return true;
                    }
            }
        }
        return false;
    }
}
