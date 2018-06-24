package puzzle.client;

import puzzleGame.PuzzleErrors;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        CliArgs cliArgs = new CliArgs(args);
        String ip = cliArgs.switchValue("-ip", "127.0.0.1");
        int port = Integer.valueOf(cliArgs.switchValue("-port", "7869"));
        String inputFileName = cliArgs.switchValue("-input");
        String outputFileName = cliArgs.switchValue("-output");
        if(inputFileName == null || outputFileName == null){
            System.out.println("Error: Command line arguments, must include input and output files!!! ");
            return;
        }
        PuzzleErrors puzzleErrors = new PuzzleErrors();
        FileParser fileParser = new FileParser(inputFileName, puzzleErrors);
        String input = fileParser.parse();

        if (!puzzleErrors.getErrorsList().isEmpty()) {
            System.out.println(puzzleErrors);
            return;
        }
        try ( // try with resource for all the below
            Socket socket = new Socket(ip, port);
            BufferedReader socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF8"));
            PrintStream socketOutput = new PrintStream(socket.getOutputStream(), /*autoflush*/ true, "UTF8");) {
            String serverLine = "";
            int index = 0;
            String currentTime = "";
            socketOutput.println(input);
            while (index < 2) {
                serverLine = socketInput.readLine();
                if (serverLine != null) {
                    Date instant = new Date(System.currentTimeMillis());
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

                    if (index == 0) {
                        System.out.println("Server initial response was received at: " + sdf.format(instant));
                        System.out.println(serverLine);
                    }

                    if (index == 1) { //2nd response from the server
                        System.out.println("Server second response was received at: " + sdf.format(instant));
                        currentTime = sdf.format(instant).replace(':', '-');
                        System.out.println(serverLine);
                        PrintWriter writer = new PrintWriter(outputFileName + "_" + currentTime + ".txt", "UTF-8");
                        writer.println(serverLine);
                        writer.close();
                    }
                    index++;
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
