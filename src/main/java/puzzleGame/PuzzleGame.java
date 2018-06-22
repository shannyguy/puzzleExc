package puzzleGame;

import com.google.gson.Gson;
import puzzle.client.Puzzle;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PuzzleGame {

    private String outputFileName;
    private int threads;
    private int port;
    private ServerSocket serverSocket = null;
    private static String IMMEDIATE_RESPONSE = "\"puzzleReceived\": {\"sessionId\": %s, \"numPieces\": %s }";

    public PuzzleGame(int port, int threads){
        this.port = port;
        this.threads = threads;
    }

    public void init() {
        int sessionId = 1;
        ExecutorService executor = Executors.newFixedThreadPool(Integer.valueOf(threads));
        try {
            serverSocket = new ServerSocket(port);
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        Socket socket = null;
        try {
            while (true){

                socket = serverSocket.accept();
                try (BufferedReader clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF8"));
                 PrintStream clientOutput = new PrintStream(socket.getOutputStream(), /* autoflush */ true, "UTF8");) {
                    String json = clientInput.readLine();
                    Gson gson = new Gson(); // Or use new GsonBuilder().create();
                    Puzzle puzzle = gson.fromJson(json, Puzzle.class); //
                    int numPieces = puzzle.getPieces().length;
                    clientOutput.print(String.format(IMMEDIATE_RESPONSE, sessionId++, numPieces));
                    Map<Integer, PuzzlePiece> input = new HashMap<Integer, PuzzlePiece>();
                    for (int i = 0; i < numPieces; i++) {
                        PuzzlePiece puzzlePiece = new PuzzlePiece(puzzle.getPieces()[i].getPiece()[0], puzzle.getPieces()[i].getPiece()[1], puzzle.getPieces()[i].getPiece()[2], puzzle.getPieces()[i].getPiece()[3]);
                        input.put(puzzle.getPieces()[i].getId(), puzzlePiece);
                    }
                    PuzzleBoard puzzleBoard = new PuzzleBoard(input, clientOutput);
                    executor.execute(puzzleBoard);
                }
            }
        } catch (IOException e){
            if(socket != null && !socket.isClosed()){
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }
    }

    private void fillOutputFile(String content) throws IOException {
        outputFileName = new Date().getTime() + outputFileName;
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName, true));
        writer.write(content);
        writer.close();
    }






}
