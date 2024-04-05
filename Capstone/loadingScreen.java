import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.lang.Runnable;
import java.awt.Font;
import java.awt.Color;
import java.awt.FontMetrics;

public class loadingScreen extends JPanel {
   
   public static int temp = 0;
   public static int loadingProgress = 0;
   public static int loadingItems = 5;
   public static int percent = 0;
   public static int width = -1;
   public static int height = -1;
   public static int animIndex = 0;
   public static int animSpeed = 100;
   public static int animBuffer = 15;
   public static int completeBuffer = 10;
   public static int completeBufferProgress = 0;
   public static boolean playAnim = false;
   public static Thread animThread;
   public static String[][] loadingMessages = {{"Loading Zobrist values.", "Loading Zobrist values..", "Loading Zobrist values..."}, {"Loading En Passant values.", "Loading En Passant values..", "Loading En Passant values..."}, {"Loading Castling values.", "Loading Castling values..", "Loading Castling values..."}, {"Loading Turn values.", "Loading Turn values..", "Loading Turn values..."}, {"Loading Transposition data.", "Loading Transposition data..", "Loading Transposition data..."}, {"Loading Transposition Table.", "Loading Transposition Table..", "Loading Transposition Table..."}};
   public static boolean loadingComplete = false;
   
   public loadingScreen() {
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      width = screenSize.width;
      height = screenSize.height;
      playAnim = true;
      animThread = new Thread(new AnimPlayer());
      animThread.start();
   }
   
   public void setPercent(int increase) {
      percent = increase;
   }
   
   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setFont(new Font("Helvetica", 1, 25));
      FontMetrics fm = g.getFontMetrics();
      if (!loadingComplete) {
         g.drawString(loadingMessages[loadingProgress][animIndex], (width - fm.stringWidth(loadingMessages[loadingProgress][0])) / 2, height / 2 - 75);
      } else {
         g.drawString("Loading Complete", (width - fm.stringWidth("Loading Complete")) / 2, height / 2 - 75);
         completeBufferProgress++;
         if (completeBufferProgress >= completeBuffer) {
            chessGUI.handleLoad();
         }
      }
      g.setColor(Color.BLUE);
      g.fillRect(width / 2 - 250, height / 2 - 25, (500 / 100) * (percent), 50);
      g.setColor(Color.BLACK);
      g.drawRect(width / 2 - 250, height / 2 - 25, 500, 50);
      g.drawString("" + percent + "%", (width - fm.stringWidth("" + percent + "%")) / 2, (height / 2 + 10));
   }
   
   public static class AnimPlayer implements Runnable {
   
      @Override
      public void run() {
         int bufferValue = 0;
         while (playAnim) {
            chessGUI.loading.repaint();
            if (bufferValue == animBuffer) {
               bufferValue = 0;
               animIndex++;
               if (animIndex == 3) {
                  animIndex = 0;
               }
            }
            if (percent == 100) {
               loadingComplete = true;
            }
            bufferValue++;
            try {
               Thread.sleep(loadingScreen.animSpeed);
            } catch (InterruptedException ie) {
               ie.printStackTrace();
            }
         } 
      }
   }
}