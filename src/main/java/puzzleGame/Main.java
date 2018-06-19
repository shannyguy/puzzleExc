package puzzleGame;

import puzzle.client.CliArgs;
import sun.nio.ch.ThreadPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {



    public static void main(String[] args) throws IOException {
        CliArgs cliArgs = new CliArgs(args);
        int threads = Integer.valueOf(cliArgs.switchValue("-threads", "4"));
        int port = Integer.valueOf(cliArgs.switchValue("-port", "7095"));

        PuzzleGame puzzleGame = new PuzzleGame(Integer.valueOf(port), Integer.valueOf(threads));
        puzzleGame.init();
    }

}