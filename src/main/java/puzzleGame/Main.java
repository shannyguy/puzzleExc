package puzzleGame;

import puzzle.client.CliArgs;

import java.io.IOException;

public class Main {



    public static void main(String[] args) throws IOException {

        System.out.println("Server is up");
        CliArgs cliArgs = new CliArgs(args);
        int threads = Integer.valueOf(cliArgs.switchValue("-threads", "4"));
        int port = Integer.valueOf(cliArgs.switchValue("-port", "7869"));

        PuzzleGame puzzleGame = new PuzzleGame(Integer.valueOf(port), Integer.valueOf(threads));
        puzzleGame.init();
    }

}