package puzzle.client;

import puzzleGame.PuzzleErrors;

public class Main {
    public static void main(String[] args) {

        CliArgs cliArgs = new CliArgs(args);
        String ip = cliArgs.switchValue("-ip", "127.0.0.1");
        int port = Integer.valueOf(cliArgs.switchValue("-port", "7095"));
        String inputFileName = cliArgs.switchValue("-input");
        String outputFileName = cliArgs.switchValue("-output");
        FileParser fileParser = new FileParser(inputFileName, new PuzzleErrors());
        fileParser.parse();

    }
}
