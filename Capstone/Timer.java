import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.FontMetrics;
import java.io.IOException;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import java.awt.Font;

public class Timer implements Runnable {
   
   private int timeRemaining = 1000;
   private static int currentTime = 0;
   public boolean runTimer = true;
   
   public static void moveMade() {
      currentTime = 0;
   }
   
   public static int getPlayerTime() {
      return Chess.playerTime;
   }
   
   public static int getBotTime() {
      return Chess.botTime;
   }
   
   @Override
   public void run() {
      try {
         Thread.sleep(500);
      } catch (InterruptedException ie) {
         ie.printStackTrace();
      }
      while (true) {
         try {
            timeIncrement();
            Thread.sleep(100);
            currentTime += 100;
         } catch (InterruptedException ie) {
            ie.printStackTrace();
         }
      }
   }
   
   public void startTimer() {
      runTimer = true;
   }
   
   public void pauseTimer() {
      runTimer = false;
   }
   
   public void timeIncrement() {
      if (runTimer) {
         if (timeRemaining == currentTime) {
            currentTime = 0;
            if (Chess.isPlayerTurn) {
               Chess.playerTime--;
               if (("" + Chess.playerTime % 60).length() == 1) {
                  chessGUI.playerTimeLabel.setText("" + Chess.playerTime / 60 + ":0" + Chess.playerTime % 60);
               } else {
                  chessGUI.playerTimeLabel.setText("" + Chess.playerTime / 60 + ":" + Chess.playerTime % 60);
               }
            } else {
               Chess.botTime--;
               if (("" + Chess.botTime % 60).length() == 1) {
                  chessGUI.botTimeLabel.setText("" + Chess.botTime / 60 + ":0" + Chess.botTime % 60);
               } else {
                  chessGUI.botTimeLabel.setText("" + Chess.botTime / 60 + ":" + Chess.botTime % 60);
               }
            }
         }
         if (Chess.playerTime == 0) {
            runTimer = false;
            chessGUI.handleGameEnd(1);
         } else if (Chess.botTime == 0) {
            runTimer = false;
            chessGUI.handleGameEnd(2);
         }
      }
   }
}