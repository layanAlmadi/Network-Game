/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package networkproject3;



import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class CONNECT extends JFrame {
    private JTextField nameField;
    private JButton connectButton;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public CONNECT() {
        setTitle("GUESS THE CITY");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(10, 25, 50));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title "GUESS THE CITY"
        JLabel titleLabel = new JLabel("GUESS THE CITY");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 60)); // تصغير الخط
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 0.05;
        add(titleLabel, gbc);

        // Panel containing "ENTER YOUR NAME:", text field, and "CONNECT" button
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setBackground(new Color(10, 25, 50));

        // Label "ENTER YOUR NAME:"
        JLabel nameLabel = new JLabel("ENTER YOUR NAME:");
        nameLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);
        panel.add(nameLabel);

        // Text field for name input
        nameField = new JTextField(15);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setPreferredSize(new Dimension(200, 30));
        nameField.setBackground(new Color(82, 109, 165));
        nameField.setForeground(Color.WHITE);
        nameField.setBorder(BorderFactory.createEmptyBorder());
        panel.add(nameField);

        // "CONNECT" button
        connectButton = new JButton("CONNECT") {
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
        connectButton.setFont(new Font("Monospaced", Font.BOLD, 14));
        connectButton.setForeground(new Color(255, 223, 0));
        connectButton.setBackground(new Color(0, 51, 102));
        connectButton.setFocusPainted(false);
        connectButton.setContentAreaFilled(false);
        connectButton.setBorderPainted(false);
        panel.add(connectButton);

        // Add the panel in the correct position
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 0.5;
        add(panel, gbc);

        // Action listener to connect to the server and open AfterConnect
        connectButton.addActionListener(e -> {
            String playerName = nameField.getText().trim();
            if (!playerName.isEmpty()) {
                connectToServer(playerName);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter your name!");
            }
        });

        setVisible(true);
    }

    private void connectToServer(String playerName) {
        try {
            socket = new Socket("localhost", 12345); // Connect to the server
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send the player's name to the server
            out.println(playerName);

            // Open the AfterConnect window and pass the input and output streams to it
            new AfterConnect(in, out,playerName);
            dispose(); // Close the current window
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to the server.");
        }
    }

    public static void main(String[] args) {
        new CONNECT();
    }
}