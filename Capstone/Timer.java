public class Timer implements Runnable {
   
   private int timeRemaining = 1000;
   private static int currentTime = 0;
   public boolean runTimer = true;
   
   public static void moveMade() {
      currentTime = 0;
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
            Thread.sleep(1);
            currentTime += 1;
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
      }
   }
}