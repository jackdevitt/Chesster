import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class transpositionTable {
   
   public static ArrayList<Transpose> table = new ArrayList<Transpose>();
   
   public static void append(Transpose transpose) {
      table.add(transpose);
   }
   
   public static Transpose search(long target, int depth) {
      for (int index = 0; index < table.size(); index++) {
         Transpose search = table.get(index);
         if (search.getHash() == (target)) {
            if (search.getDepth() == depth)
            return search;
         }
      }
      return null;
   }
   
   public static void write(Transpose transpose) {
      if (transpose.getHash() == 3354903063720328704L) {
         System.out.println("Flawed Score: " + transpose.getScore());
      }
      try {
         FileWriter writer = new FileWriter("TranspositionTable.txt", true);
         writer.write("" + transpose.getHash() + ":" + transpose.getScore() + "," + transpose.getDepth() + "," + transpose.getAlpha() + "," + transpose.getBeta());
         writer.write("\n");
         writer.close();
      } catch (IOException ie) {
         ie.printStackTrace();
      }
   }
   
   public static void load() {
      try {
         BufferedReader reader = new BufferedReader(new FileReader("TranspositionTable.txt"));
         String line = reader.readLine();
         while (line != null) {
            long hash = Long.parseLong(line.substring(0, line.indexOf(":")));
            int score = Integer.parseInt(line.substring(line.indexOf(":") + 1, line.indexOf(",")));
            line = line.substring(line.indexOf(",") + 1);
            int depth = Integer.parseInt(line.substring(0, line.indexOf(",")));
            table.add(new Transpose(hash, -1, -1, score, depth));
            line = reader.readLine();
         }
      } catch (IOException ie) {
         ie.printStackTrace();
      }
      chessGUI.loading.setPercent(100);
   }
}