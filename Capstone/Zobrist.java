import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

public class Zobrist {
   
   public static FileWriter writer;
   public static File file;
   public static long[][][] hashmap = new long[8][8][6];
   public static long[] enPassant = new long[8];
   public static long[] castling = new long[4];
   public static long isBlack;
   public static int percentCount = 1;
   public static int currentLoad = 0;
   public static boolean slowLoading = false;
   
   public static long getUniqueTimestamp() {
      long max = 9223372036854775807L;
      long min = max / 2;
      return (long)(Math.random() * (max - min)) + min;
   }
   
   public static void initalize() {
      try {
         writer = new FileWriter("ZobristData.txt");
         for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
               for (int index = 0; index < 6; index++) {
                  hashmap[row][col][index] = getUniqueTimestamp();
                  try {
                     writer.write("" + hashmap[row][col][index] + ",");
                  } catch (IOException ie) {
                     ie.printStackTrace();
                  }
               }
               writer.write("\n");
            }
         }
         for (int col = 0; col < 8; col++) {
            writer.write(getUniqueTimestamp() + ",");
         }
         writer.write("\n");
         for (int col = 0; col < 4; col++) {
            writer.write(getUniqueTimestamp() + ",");
         }
         writer.write("\n");
         writer.write("" + getUniqueTimestamp());
         writer.close();
      } catch (IOException ie) {
         ie.printStackTrace();
      }
   } 
   
   public static long getHash(Position position) {
      long hash = 0;
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
            int pieceValue = Math.abs(position.getBoard()[row][col]);
            int hashIndex = -1;
            if (pieceValue != 0) {
               if (pieceValue == 1) {
                  hashIndex = 0;
               } else if (pieceValue == 3) {
                  hashIndex = 1;
               } else if (pieceValue == 4) {
                  hashIndex = 2;
               } else if (pieceValue == 5) {
                  hashIndex = 3;
               } else if (pieceValue == 9) {
                  hashIndex = 4;
               } else if (pieceValue == 15) {
                  hashIndex = 5;
               }
               hash ^= hashmap[row][col][hashIndex];
            }  
         }
      }
      int[] castlingData = position.getCastlingData();
      if (castlingData[1] == 1) {
         if (castlingData[0] == 1) {
            hash ^= castling[0];
         }
         if (castlingData[2] == 1) {
            hash ^= castling[1];
         }
      }
      if (castlingData[4] == 1) {
         if (castlingData[3] == 1) {
            hash ^= castling[2];
         }
         if (castlingData[5] == 1) {
            hash ^= castling[3];
         }
      } 
      if (position.getEPassY() != -1) {
         hash ^= enPassant[position.getEPassY()];
      }
      if (Chess.playerIsWhite) {
         hash ^= isBlack;
      } 
      return hash;
   }
   
   public static Message getData() {
      boolean status = true;
      try {
         BufferedReader reader = new BufferedReader(new FileReader("ZobristData.txt"));
         String line = reader.readLine();
         int iteration = 0;
         int count = 0;
         boolean[] loadedItems = new boolean[3];
         currentLoad = 64;
         while (line != null) {
            if (iteration <= 63) {
               int index = line.indexOf(",");
               while (index != -1) {
                  hashmap[iteration / 8][iteration % 8][count] = Long.parseLong(line.substring(0, index));
                  line = line.substring(index + 1);
                  index = line.indexOf(",");
                  count++; 
               }
               count = 0;
               line = reader.readLine();
               chessGUI.loading.setPercent((int)(((100 / (double)loadingScreen.loadingItems) / 64.0) * iteration));
            } else if (iteration == 64) {
               if (!loadedItems[0]) {
                  currentLoad = 8;
                  loadedItems[0] = true;
                  loadingScreen.loadingProgress++;
               }
               int index = line.indexOf(",");
               count = 0;
               while (index != -1) {
                  enPassant[count] = Long.parseLong(line.substring(0, index));
                  line = line.substring(index + 1);
                  index = line.indexOf(",");
                  chessGUI.loading.setPercent((100 / loadingScreen.loadingItems * 1) + (int)(((100 / (double)loadingScreen.loadingItems) / 8.0) * count));
                  count++;
               }
               line = reader.readLine();
            } else if (iteration == 65) {
               if (!loadedItems[1]) {
                  currentLoad = 4;
                  loadedItems[1] = true;
                  loadingScreen.loadingProgress++;
               }
               int index = line.indexOf(",");
               count = 0;
               while (index != -1) {
                  castling[count] = Long.parseLong(line.substring(0, index));
                  line = line.substring(index + 1);
                  index = line.indexOf(",");
                  chessGUI.loading.setPercent((100 / loadingScreen.loadingItems * 2) + (int)(((100 / (double)loadingScreen.loadingItems) / 4.0) * count));
                  count++;
               }
               line = reader.readLine();
            } else if (iteration == 66) {
               if (!loadedItems[2]) {
                  currentLoad = 1;
                  loadedItems[2] = true;
                  loadingScreen.loadingProgress++;
               }
               isBlack = Long.parseLong(line);
               chessGUI.loading.setPercent((100 / loadingScreen.loadingItems * 3) + (int)(100 / (double)loadingScreen.loadingItems));
               line = reader.readLine();
            }
            iteration++;
            if (slowLoading) {
               try {
                  Thread.sleep((int)((Math.random() * ((2000 / currentLoad) - (1000 / currentLoad) + 1)) + (1000 / currentLoad)));
               } catch (InterruptedException ie) {
                  ie.printStackTrace();
               }
            }
         }
         reader.close();
      } catch (IOException ie) {
         ie.printStackTrace();
         status = false;
      }
      return new Message(status, "Could not read Zobrist data");
   }
}