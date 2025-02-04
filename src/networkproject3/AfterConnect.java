package networkproject3;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class AfterConnect extends JFrame {
    private JButton playButton;
    private JTextArea connectedPlayersArea;
    private PrintWriter out;
    private BufferedReader in;
    private String playerName;

    public AfterConnect(BufferedReader in, PrintWriter out, String playerName) {
        this.in = in;
        this.out = out;
        this.playerName = playerName;

        setTitle("AFTER CONNECT");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(10, 25, 50));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // إعداد زر "PLAY"
        playButton = new JButton("PLAY") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(50, 150, 255));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
        };
        playButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        playButton.setForeground(new Color(255, 223, 0));
        playButton.setBackground(new Color(0, 51, 102));
        playButton.setFocusPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(playButton, gbc);

        // إعداد منطقة النص لعرض اللاعبين المتصلين
        connectedPlayersArea = new JTextArea(10, 30);
        connectedPlayersArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        connectedPlayersArea.setBackground(new Color(82, 109, 165));
        connectedPlayersArea.setForeground(Color.WHITE);
        connectedPlayersArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(connectedPlayersArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(400, 200));
        gbc.gridy = 1;
        add(scrollPane, gbc);

        // إنشاء Thread للاستماع إلى التحديثات من السيرفر
        new Thread(() -> {
            try {
               System.out.print(playerName);

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.contains("Game is starting")) {
                       
                        SwingUtilities.invokeLater(() -> {
                            new StartGame(playerName,in,out).setVisible(true);
                           
                            dispose(); // إغلاق الإطار الحالي
                        });
                        break;
                    } else {
                        connectedPlayersArea.append(message + "\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // الاستماع لزر "PLAY"
        playButton.addActionListener(e -> {
            out.println("Play");
            playButton.setEnabled(false); // تعطيل الزر بعد الضغط عليه
        });

        setVisible(true);
    }
}
