package com.example.danielmccarragher.gohide.BackendGameLogic;

public class Main {

    public static void main(String[] args) {

        GridWorld.LOAD();
        GridWorld.LOAD_LEVEL_FROM_FILE("app/src/main/java/com/example/danielmccarragher/gohide/TestLevels/Test1.txt");
        GridWorld.DEBUG_DRAW();
    }


}
