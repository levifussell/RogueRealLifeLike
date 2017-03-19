package com.example.danielmccarragher.gohide.BackendGameLogic;

import com.example.danielmccarragher.gohide.MainActivity;

public class Main {

    public static void main(String[] args) {

        GridWorld.LOAD();
        GridWorld.LOAD_LEVEL_FROM_FILE("app/src/main/assets/TestLevels/Test1.txt");
        GridWorld.DEBUG_DRAW();
    }


}
