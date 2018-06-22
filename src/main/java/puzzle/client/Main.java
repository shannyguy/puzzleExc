package puzzle.client;

import puzzleGame.PuzzleErrors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Client is up");
        CliArgs cliArgs = new CliArgs(args);
        String ip = cliArgs.switchValue("-ip", "127.0.0.1");
        int port = Integer.valueOf(cliArgs.switchValue("-port", "7095"));
        String inputFileName = cliArgs.switchValue("-input");
        String outputFileName = cliArgs.switchValue("-output");
        FileParser fileParser = new FileParser(inputFileName, new PuzzleErrors());
        String input = fileParser.parse();

        try ( // try with resource for all the below
              Socket socket = new Socket(ip, port);
              BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF8"));
              PrintStream socketOutput = new PrintStream(socket.getOutputStream(), /*autoflush*/ true, "UTF8");) {
            String serverLine = "";
            int index = 0;
            socketOutput.println(input);
            while (index < 2) {

                serverLine = socketInput.readLine();
                if (serverLine != null) {
                    System.out.println(serverLine);

                }
                index++;

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
