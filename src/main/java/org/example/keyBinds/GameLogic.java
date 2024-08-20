package org.example.keyBinds;

import org.example.Main;

import java.awt.*;

public class GameLogic {
    public static int xPos;
    public static int yPos;

    public static int currentSymbol = 1;
    public static int[][] symbol = new int[3][3];
    //0 = nothing
    //1 = X
    //2 = O


    public static int winCheck(){

        for (int i = 0; i < 2; i++){
            if (((symbol[0][i] == symbol[1][i]) && (symbol[1][i] == symbol[2][i])) && symbol[1][i] != 0){
                return symbol[1][i];
            }
        }

        for (int i = 0; i < 2; i++){
            if (((symbol[i][0] == symbol[i][1]) && (symbol[i][1] == symbol[i][2])) && symbol[i][1] != 0){
                return symbol[i][1];
            }
        }

        if (((symbol[0][0] == symbol[1][1]) && (symbol[1][1] == symbol[2][2])) && symbol[1][1] != 0){
            return symbol[1][1];
        }

        if (((symbol[2][0] == symbol[1][1]) && (symbol[1][1] == symbol[0][2])) && symbol[1][1] != 0){
            return symbol[1][1];
        }



        return 0;

    }

    public static void move(int symbolUsed, int x, int y){
        symbol[x][y] = symbolUsed;
        changePlayer();
        Main.winner = winCheck();

    }

    public static void changePlayer() {
        if (Main.symbolUsed == 1) Main.symbolUsed = 2; else Main.symbolUsed  = 1;

    }

    public static void drawSymbol(float x, float y) {
        float size = 51.0f;
        Color color1 = new Color(217, 52, 52, 255);
        Color color2 = new Color(52, 184, 217, 255);
        if (Main.symbolUsed == 1)
            Main.objects.add(new ObjectsToRender(x, -y, color1));
        else
            Main.objects.add(new ObjectsToRender(x, -y, color2));
    }


    public static Color getColor() {
        Color color1 = new Color(217, 52, 52, 255);
        Color color2 = new Color(52, 184, 217, 255);
        return  currentSymbol == 1 ? color1 : color2;
    }
}
