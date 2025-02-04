/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package networkproject3;



import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


/**
 *
 * @author Njood
 */
public class StartGame extends javax.swing.JFrame{
private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
//private static ArrayList<Server> client = new ArrayList<>();
String playerName;
String scoreText;
 private PrintWriter out;
    private BufferedReader in;
    private String answer=null;
    String message;
   private javax.swing.JButton activeButton = null;
  // int x = clientHandlers.get(0).getScore();
//private Server server;
   //private GameSession gameSession;
   // private Player currentPlayer;
 public StartGame(String playerName,BufferedReader in, PrintWriter out) {
       // clientHandlers=(ArrayList<ClientHandler>) server.getPlayersInGame();
        // clientHandlers=(ArrayList<ClientHandler>) server.getClientHandlers();
         // احصل على قائمة العملاء من الخادم الحالي
    clientHandlers = Server.getClientHandlers();
    System.out.println("Number of players in clientHandlers: " + clientHandlers.size());

     this.in = in;
     this.out = out;
           //server = new Server();
          // playerName=clientHandlers.get(0).getPlayerName();
          this.playerName=playerName;
       // Add the player to the list
        initComponents();
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(10, 25, 50));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        jTextArea3.setEditable(false);
        // setExtendedState(StartGame.MAXIMIZED_BOTH);
      
        //jTextArea3.setText("dss");
        //updateScoreDisplay();  // Display initial score
        startTimer();  // Start the 30-second timer
        /* new Thread(() -> {
            try {
               // String message;
               // message;
                while ((message = in.readLine()) != null) {
                    if (message.contains("Score Update:")) {
                        SwingUtilities.invokeLater(() -> {
                            // الانتقال إلى StartGame مع تمرير playerName
                          // jTextArea3.setText(message);
                          jTextArea3.append(message);
                        });
                        break;
                    } else {
                        //connectedPlayersArea.append(message + "\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();*/
        new Thread(() -> {
    try {
        String message;
        while ((message = in.readLine()) != null) {
            if (message.contains("Score for players:  ")) {
                ScoreManager.getInstance().setScoreMessage(message);
                SwingUtilities.invokeLater(() -> {
                    jTextArea3.append(ScoreManager.getInstance().getScoreMessage() + "\n");
                });
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}).start();

        
        
    }
 /*  public StartGame(String playerName) {
    this.gameSession = new GameSession();
    this.currentPlayer = new Player(playerName);
    gameSession.addPlayer(currentPlayer);
    initComponents();
    updateScoreDisplay();  // عرض السكور بعد إضافة اللاعبين
}*/


    // Method to start a timer that waits 30 seconds before transitioning
private void startTimer() {
    Timer timer = new Timer(10000, new ActionListener() { // تغيير إلى 10 ثوانٍ
        @Override
        public void actionPerformed(ActionEvent e) {
            // بعد 10 ثوانٍ، يتم الانتقال إلى الفريم التالي
            handleAnswer("Germany");
            transitionToNextFrame();
            
        }
    });
    timer.setRepeats(false);  // التايمر لا يتكرر
    timer.start();  // بدء التايمر
}



private void transitionToNextFrame() {
    // إخفاء الفريم الحالي وفتح الفريم التالي
    SwingUtilities.invokeLater(() -> {
        this.setVisible(false);  // إخفاء الفريم الحالي
        
      //  new Q3(playerName).setVisible(true);  // فتح الفريم التالي
      new StartGame22(playerName,in,out).setVisible(true);
      
        this.dispose();  // تحرير الموارد
    });
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(java.awt.SystemColor.activeCaption);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(java.awt.Color.blue);
        setMinimumSize(new java.awt.Dimension(581, 337));

        jPanel1.setBackground(new java.awt.Color(10, 25, 50));
        jPanel1.setMinimumSize(new java.awt.Dimension(581, 337));

        jButton1.setText("Germany");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("France");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Belgium");
        jButton3.setActionCommand("");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Choose the correct country for this flag");
        jLabel2.setToolTipText("");
        jLabel2.setPreferredSize(new java.awt.Dimension(250, 16));

        jButton4.setText("Dis connect");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTextArea3.setBackground(new java.awt.Color(204, 204, 204));
        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jTextArea3.setInheritsPopupMenu(true);
        jTextArea3.setVerifyInputWhenFocusTarget(false);
        jScrollPane3.setViewportView(jTextArea3);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/download.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(jButton1)
                .addGap(33, 33, 33)
                .addComponent(jButton2)
                .addGap(45, 45, 45)
                .addComponent(jButton3)
                .addContainerGap(132, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(186, 186, 186))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(204, 204, 204))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(104, 104, 104))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //Germany buuton
        answer="Germany";
        // handleAnswer(answer);
        updateButtonState(jButton1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        //Belgum
        answer="Belgum";
        // handleAnswer(answer);
         updateButtonState(jButton3);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        //disconnect
         out.println("exit"); 
      ///////////////////  server.removePlayer(playerName, clientHandlers.get(0)); // إضافة الفاصلة المنقوطة
       // System.exit(0);
       try {
        in.close();
        out.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

    // إخفاء الشاشة الحالية
    SwingUtilities.invokeLater(() -> {
        this.setVisible(false);
        this.dispose();
    });
       
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        //France button
         answer="France";
          //handleAnswer(answer);
          updateButtonState(jButton2);
    }//GEN-LAST:event_jButton2ActionPerformed
// الطريقة التي تتحكم في النقر على الأزرار
   private void updateButtonState(javax.swing.JButton selectedButton) {
    if (activeButton != null) {
        activeButton.setBackground(new java.awt.Color(255, 255, 255)); // إعادة اللون الافتراضي
        activeButton.setEnabled(true);  // إعادة تمكين الزر
    }

    selectedButton.setBackground(new java.awt.Color(204, 204, 204)); // تغيير لون الزر المحدد
    selectedButton.setEnabled(false);  // تعطيل الزر المحدد
    activeButton = selectedButton;
}

  
  
 private void updateScoreDisplay() {
    StringBuilder scoreTextBuilder = new StringBuilder();  // استخدام StringBuilder لتجنب الأداء الضعيف
clientHandlers = Server.getClientHandlers();
System.out.println("Number of players in clientHandlers: " + clientHandlers.size());
    // تجميع السكور لجميع اللاعبين
    for (ClientHandler clientHandler : Server.clientHandlers) {
        scoreTextBuilder.append(clientHandler.getPlayerName())
                         .append("Score for players:  ")
                         .append(clientHandler.getScore())
                         .append("\n");
    }

    // تحديث النص في واجهة المستخدم (التأكد من تحديث الواجهة في الخيط الخاص بـ GUI)
    SwingUtilities.invokeLater(() -> jTextArea3.setText(scoreTextBuilder.toString()));
}
 /*private void updateScoreDisplay() {
    StringBuilder scoreTextBuilder = new StringBuilder();

    // المرور عبر جميع اللاعبين في الجلسة
    for (Player player : gameSession.getPlayers()) {
        scoreTextBuilder.append(player.toString()).append("\n");  // إضافة اسم اللاعب والسكور الخاص به
    }

    // تحديث النص في jTextArea3 لعرض السكور لجميع اللاعبين
    SwingUtilities.invokeLater(() -> jTextArea3.setText(scoreTextBuilder.toString()));
}*/
/*private void updateScoreDisplay() {
    SwingUtilities.invokeLater(() -> {
        // مسح النص السابق في الـ jTextArea3
        jTextArea3.setText("");

        // إضافة أسماء اللاعبين وسكورهم مباشرة إلى jTextArea3
        for (Player player : gameSession.getPlayers()) {
            jTextArea3.append(player.toString() + "\n"); // اسم اللاعب والسكور في كل سطر
        }
        
    });
}*/




   /* private void handleAnswer(String countryName) {
        gameSession.checkAnswer(currentPlayer, countryName);
        updateScoreDisplay();  // تحديث عرض السكور
    }*/

  
      
 ///////////////////////////      
  // Method to handle the answer when a button is clicked      
  // الطريقة التي تعالج الإجابة عند النقر على الزر
private void handleAnswer(String countryName) {
    if (answer != null && countryName.equals(answer)) {
        // الإجابة صحيحة، قم بزيادة السكور
      //Server.increasePlayerScore(playerName);
     
       out.println("update the Score"); 
     System.out.println("exallant");
        //updateScoreDisplay();  // تحديث عرض السكور
    } else {
        // الإجابة خاطئة، لا يتم زيادة السكور
        System.out.println("إجابة خاطئة");
    }
}     
        // Method to update the score from the client (this could be used if you need external updates)
      
        
  
    /*    private void processServerMessage(String message) {
    if (message.startsWith("Players In Game:")) {
        String playersList = message.substring(17); // استخراج قائمة اللاعبين
        updatePlayersArea(playersList); // تحديث واجهة المستخدم
    } else if (message.startsWith("Score Update:")) {
        String scores = message.substring(13); // استخراج السكور بعد "Score Update:"
        updateScoreArea(scores); // تحديث واجهة المستخدم
    }
}
private void updatePlayersArea(String playersList) {
    SwingUtilities.invokeLater(() -> jTextArea3.setText(playersList));
}

 private void updateScoreArea(String scores) {
    SwingUtilities.invokeLater(() -> jTextArea3.setText(scores));  // تحديث واجهة المستخدم
}*/
 
        
        
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StartGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StartGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StartGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               // new StartGame().setVisible(true);
            }
        });
    }
/*public class Player {
    private String playerName;
    private int score;

    public Player(String playerName) {
        this.playerName = playerName;
        this.score = 0;  // يبدأ السكور من 0
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        this.score++;  // زيادة السكور عند الإجابة الصحيحة
    }

    @Override
    public String toString() {
        return playerName + " Score: " + score;
    }
}*/




/*public class GameSession {
    private ArrayList<Player> players;
    private String correctAnswer;

    public GameSession() {
        players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void setCorrectAnswer(String answer) {
        this.correctAnswer = answer;
    }

    public void checkAnswer(Player player, String answer) {
        if (answer.equals(correctAnswer)) {
            player.increaseScore();  // زيادة السكور إذا كانت الإجابة صحيحة
        }
    }

    public void updateScores() {
        for (Player player : players) {
            System.out.println(player);
        }
    }

       public Iterable<Player> getPlayers() {
    return players;  // إرجاع قائمة اللاعبين
}

    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    // End of variables declaration//GEN-END:variables
}


