package networkproject3;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import javax.swing.SwingUtilities;

public class Server {
    private static final int MAX_WAITING_PLAYERS = 3;
    private static ArrayList<String> connectedPlayers = new ArrayList<>();
    static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private static ArrayList<ClientHandler> waitingRoom = new ArrayList<>();
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static boolean isTimerRunning = false;

    private static List<ClientHandler> playersInGame = new ArrayList<>();

    public static void main(String[] args) {
        try {
            // الحصول على المنفذ من البيئة أو استخدام 8080 كافتراضي
            int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
            ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName("0.0.0.0"));

            System.out.println("✅ Server is running on port: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandlers.add(clientHandler);

                System.out.println("🔗 New client connected! Total clients: " + clientHandlers.size());

                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("❌ Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
    
   
    public static List<ClientHandler> getTopScorers() {
    List<ClientHandler> topScorers = new ArrayList<>();
    int highestScore = Integer.MIN_VALUE;

    // تحديد أعلى نتيجة موجودة
    for (ClientHandler player : playersInGame) {
        if (player.getScore() > highestScore) {
            highestScore = player.getScore();
        }
    }

    // التحقق إذا كان جميع اللاعبين لديهم نفس النتيجة
    boolean allScoresEqual = true;
    for (ClientHandler player : playersInGame) {
        if (player.getScore() != highestScore) {
            allScoresEqual = false;
            break;
        }
    }

    // إذا كانت جميع النتائج متساوية، لا يوجد فائز
    if (allScoresEqual) {
        return new ArrayList<>(); // قائمة فارغة لتمثيل أنه لا يوجد فائز
    }

    // إضافة جميع اللاعبين الذين لديهم أعلى نتيجة إلى القائمة
    for (ClientHandler player : playersInGame) {
        if (player.getScore() == highestScore) {
            topScorers.add(player);
        }
    }

    return topScorers;
}

   
   public static void announceTopScorers() {
    List<ClientHandler> topScorers = getTopScorers();

    if (topScorers.isEmpty()) {
        broadcastToPlayersInGame("There is no winner");
    } else {
        StringBuilder message = new StringBuilder("Congratulations ");

        for (ClientHandler player : topScorers) {
            message.append(player.getPlayerName()).append(" ");
        }

        broadcastToPlayersInGame(message.toString());
    }

    playersInGame.clear();
}
  
    
    public static void handlePlayRequest(ClientHandler player) {
    if (!playersInGame.contains(player)) {
        playersInGame.add(player);
    }
    broadcastPlayersInGame();
}
private static void broadcastPlayersInGame() {
    StringBuilder message = new StringBuilder("Players In Game:\n");
    for (ClientHandler player : playersInGame) {
        message.append(player.getPlayerName()).append("\n");
    }

    // إرسال الرسالة فقط إلى اللاعبين داخل اللعبة (الموجودين في playersInGame)
    for (ClientHandler player : playersInGame) {
        player.sendMessage(message.toString());
    }
}

    public static synchronized void addPlayer(String playerName) {
        connectedPlayers.add(playerName);
        updateConnectedPlayersList();
    }

   /* public static synchronized void removePlayer(String playerName, ClientHandler handler) {
        connectedPlayers.remove(playerName);
        clientHandlers.remove(handler);
        waitingRoom.remove(handler);
        updateConnectedPlayersList();
        updateWaitingRoomPlayers();
    }*/
    
     public static void removePlayer(String playerName, ClientHandler handler) {
        connectedPlayers.remove(playerName);
        clientHandlers.remove(handler);
        //updateConnectedPlayersList();
        System.out.println("Player " + playerName + " removed from connected players.");
    }

  /*  public static synchronized void addToWaitingRoom(ClientHandler handler) {
        if (waitingRoom.size() < MAX_WAITING_PLAYERS) {
            waitingRoom.add(handler);
            updateWaitingRoomPlayers();

            if (waitingRoom.size() == MAX_WAITING_PLAYERS) {
                startGame();
            } else if (waitingRoom.size() > 1 && !isTimerRunning) {
                startTimerForGame();
            }
        }
    }*/
  public static synchronized void addToWaitingRoom(ClientHandler handler) {
    if (waitingRoom.size() < MAX_WAITING_PLAYERS) {
        waitingRoom.add(handler);
        updateWaitingRoomPlayers();  // تحديث قائمة اللاعبين في غرفة الانتظار بشكل فوري

        if (waitingRoom.size() == MAX_WAITING_PLAYERS) {
            startGame();
        } else if (waitingRoom.size() > 1 && !isTimerRunning) {
            startTimerForGame();
        }
    }
}

    public static List<ClientHandler> getPlayersInGame() {
        return playersInGame;
    }

    public static ArrayList<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }
    
    
   
    

    private static void startTimerForGame() {
        isTimerRunning = true;
        broadcastToWaitingRoom("Timer started: waiting for 30 seconds for more players...");
        scheduler.schedule(() -> {
            if (waitingRoom.size() > 1) {
                startGame();
            }
            isTimerRunning = false;
        }, 30, TimeUnit.SECONDS);
    }

    public static synchronized void updateConnectedPlayersList() {
        String playerList = "Connected players:\n" + String.join("\n", connectedPlayers);
        broadcastToAll(playerList);
    }

public static synchronized void updateWaitingRoomPlayers() {
    if (waitingRoom.isEmpty()) {
        broadcastToWaitingRoom("Waiting Room: No players currently in the waiting room.");
    } else {
        String waitingList = "Waiting Room:\n";
        for (ClientHandler handler : waitingRoom) {
            waitingList += handler.getPlayerName() + "\n";
        }
        broadcastToWaitingRoom(waitingList);  // إرسال التحديث إلى العملاء
    }
}  

    public static void broadcastToAll(String message) {
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.sendMessage(message);
        }
    }

    public static void broadcastToWaitingRoom(String message) {
        for (ClientHandler clientHandler : waitingRoom) {
            clientHandler.sendMessage(message);
        }
    }
    public static void broadcastToPlayersInGame(String message) {
    for (ClientHandler clientHandler : playersInGame) {
        clientHandler.sendMessage(message);
    }
}


  /*  public static synchronized void startGame() {
        broadcastToWaitingRoom("Game is starting with players: " + String.join(", ", getWaitingRoomPlayerNames()));
        waitingRoom.clear();
        isTimerRunning = false;
    }*/
       public static void startGame() {
        broadcastToWaitingRoom("Game is starting with players: ");
        //startScoreUpdateTask();
        for (ClientHandler player : waitingRoom) {
            broadcastToWaitingRoom(player.getPlayerName());
           // 
           handlePlayRequest(player);
        }
        //startScoreUpdateTask();
        updateScoreDisplay();
               
        waitingRoom.clear();
        isTimerRunning = false;

    }

      private static void startScoreUpdateTask() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                updateScoreDisplay();
            } catch (Exception e) {
                e.printStackTrace(); // معالجة الأخطاء
            }
        }, 10, 10, TimeUnit.SECONDS); // التحديث يبدأ بعد 10 ثوانٍ ويتكرر كل 10 ثوانٍ
    }
    

    private static List<String> getWaitingRoomPlayerNames() {
        List<String> playerNames = new ArrayList<>();
        for (ClientHandler handler : waitingRoom) {
            playerNames.add(handler.getPlayerName());
        }
        return playerNames;
    }
    
 /////////////////////////////////////////////////////////////   تحديث السكور

//
/*public static synchronized void increasePlayerScore(String playerName) {
    for (ClientHandler handler : clientHandlers) {
        if (handler.getPlayerName().equals(playerName)) {
            handler.increaseScore();  // زيادة السكور للاعب
            break;
        }
    }
}*/
 /*   public void increasePlayerScore(String playerName) {
    for (ClientHandler clientHandler : clientHandlers) {
        if (clientHandler.getPlayerName().equals(playerName)) {
            // زيادة السكور
            clientHandler.setScore(clientHandler.getScore() + 1);
            break;
        }
    }
}*/
public static void updateScoreDisplay() {
    StringBuilder scoreText = new StringBuilder();
 
    for (ClientHandler clientHandler : playersInGame) {
        scoreText.append(clientHandler.getPlayerName())
                 .append(":")
                 .append(clientHandler.getScore())
                 .append("   ");
        
    }

    // إرسال التحديث فقط للاعبين في اللعبة
  /*  for (ClientHandler clientHandler : playersInGame) {
        clientHandler.sendMessage("Score Update:" + scoreText.toString());
    }*/
   for (ClientHandler clientHandler : playersInGame) {
        clientHandler.sendMessage("Score for players:  " + scoreText.toString());
}

}

// في Server.java، أضف ميثود لزيادة السكور
/*public static void increasePlayerScore(String playerName) {
    for (ClientHandler clientHandler : playersInGame) {
        if (clientHandler.getPlayerName().equals(playerName)) {
            // زيادة السكور
            clientHandler.setScore(clientHandler.getScore() + 1);
            break;
        }
    }
}*/
public static void increasePlayerScore(String playerName) {
    int s;
    for (ClientHandler clientHandler : playersInGame) {
        s=clientHandler.getScore();
        if (clientHandler.getPlayerName().equals(playerName)) {
            // زيادة السكور
            s=s+1;
            clientHandler.setScore(s);
            break;
        }
    }
}
/*public static void increasePlayerScore(ClientHandler clientHandler) {
    if (playersInGame.contains(clientHandler)) {
        int newScore = clientHandler.getScore() + 1; // زيادة السكور بمقدار 1
        clientHandler.setScore(newScore);
    }
}*/
///////////////////////////////////////////////////////////////////////
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String playerName;
    private boolean hasJoinedWaitingRoom = false;
    private int Score=0;
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            playerName = in.readLine();
            Server.addPlayer(playerName);
            out.println("Welcome, " + playerName + "! You are connected.");

            String message;
            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("Play")) {
                    if (!hasJoinedWaitingRoom) {
                        Server.addToWaitingRoom(this);
                        hasJoinedWaitingRoom = true;
                    }
                }
               if (message.equalsIgnoreCase("update the Score")) {
                   System.out.println("Updating score for player: " + this.getPlayerName());
                  Server.increasePlayerScore(this.getPlayerName());
                  System.out.println("Updating score for player: " + this.getScore());
               }
               
              if (message.equalsIgnoreCase("Change")){
                 Server.updateScoreDisplay();
               }
               
                  if (message.equalsIgnoreCase("exit")){
                     Server.removePlayer(playerName, this);
                     Server.updateConnectedPlayersList();
                  }                 

                 /* if (message.equalsIgnoreCase("winner")){
                     Server.getTopScorers();
                  }  */
                 if (message.equals("get_winners")) {
    Server.announceTopScorers();
}
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Server.removePlayer(playerName, this);
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

/*public void run() {
    try {
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        playerName = in.readLine();
        Server.addPlayer(playerName);
        out.println("Welcome, " + playerName + "! You are connected.");

        String message;
        while ((message = in.readLine()) != null) {
            if (message.startsWith("Score Update:")) {
                String scores = message.substring(13);
                // تحديث واجهة المستخدم في العميل
           /*     SwingUtilities.invokeLater(() -> {
                    // من المفترض أن يكون لديك طريقة هنا لتحديث النص في jTextArea3
                    // مثلاً، ستحتاج إلى استخدام كائن JFrame أو JPanel للتفاعل مع واجهتك
                    updateScoreArea(scores);
                });
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        Server.removePlayer(playerName, this);
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/

// طريقة لتحديث النص في jTextArea3 في العميل
/*private void updateScoreArea(String scores) {
    // افترض أن لديك كائن يمثل واجهتك مثل JFrame أو JPanel
    // تأكد من أن هذه الطريقة تتفاعل مع واجهتك لتحديث النص.
    // مثال:
    jTextArea3.setText(scores);  // تحديث واجهة المستخدم
}*/

    
    
    

    public void sendMessage(String message) {
        out.println(message);
    }
public void increaseScore() {
    this.Score += 1;
}
    public String getPlayerName() {
        return playerName;
    }
     public int getScore() {
        return Score;
    }
     public void setScore(int score) {
    this.Score = score;
}

}

