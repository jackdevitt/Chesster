public class Position {
   
   private int[][] board = new int[8][8];
   private int[] castlingData = new int[6];
   private int ePassX = 0;
   private int ePassY = 0;
   private boolean isEPassWhite = false;
   
   public Position(int[][] board, int[] castlingData, int ePassX, int ePassY, boolean isEPassWhite) {
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
            this.board[row][col] = board[row][col];
         }
      }
      for (int index = 0; index < 6; index++) {
         this.castlingData[index] = castlingData[index];
      }
      this.ePassX = ePassX;
      this.ePassY = ePassY;
      this.isEPassWhite = isEPassWhite;
   }
   
   public int[][] getBoard() {
      return board;
   }
   
   public int[] getCastlingData() {
      return castlingData;
   }
   
   public int getEPassX() {
      return ePassX;
   } 
   
   public int getEPassY() {
      return ePassY;
   }
   
   public boolean getIsEPassWhite() {
      return isEPassWhite;
   }
}