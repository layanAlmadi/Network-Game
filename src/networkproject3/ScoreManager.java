/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package networkproject3;

/**
 *
 * @author User
 */
public class ScoreManager {
    private static ScoreManager instance;
    private String scoreMessage;

    private ScoreManager() {}

    public static synchronized ScoreManager getInstance() {
        if (instance == null) {
            instance = new ScoreManager();
        }
        return instance;
    }

    public String getScoreMessage() {
        return scoreMessage;
    }

    public void setScoreMessage(String message) {
        this.scoreMessage = message;
    }
}

