import java.util.ArrayList;

public class chessEngine {
   
   public static int[][] chessBoard = new int[8][8]; 
   public static ArrayList<int[]> moveList = new ArrayList<int[]>();
   public static ArrayList<int[]> playerMoveList = new ArrayList<int[]>();
   
   public static int ePassX = -1;
   public static int ePassY = -1;
   public static boolean isEPassWhite = false;
   public static int[] castlingData = {1, 1, 1, 1, 1, 1};
   
   public static void setSquare(int row, int col, int pieceVal) {
      chessBoard[row][col] = pieceVal;
   }
   
   public static void setupBoard(boolean isWhite) {
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
            int pieceValue = 0;
            int pieceMultiplier = 0;
            if (row == 1 || row == 6) {
               pieceValue = 1;
            }
            if (isWhite) {
               if (row >= 6) {
                  pieceMultiplier = 1;
               } else if (row <= 1) {
                  pieceMultiplier = -1;
               }
            } else {
               if (row >= 6) {
                  pieceMultiplier = -1;
               } else if (row <= 1) {
                  pieceMultiplier = 1;
               }
            }
            if (row == 7 || row == 0) {
               switch(col) {
                  case 0:
                     pieceValue = 5;
                     break;
                  case 1:
                     pieceValue = 3;
                     break;
                  case 2:
                     pieceValue = 4;
                     break;
                  case 3:
                     if (isWhite) {
                        pieceValue = 9;
                     } else {
                        pieceValue = 15;
                     }
                     break;
                  case 4:
                     if (isWhite) {
                        pieceValue = 15;
                     } else {
                        pieceValue = 9;
                     }
                     break;
                  case 5:
                     pieceValue = 4;
                     break;
                  case 6:
                     pieceValue = 3;
                     break;
                  case 7:
                     pieceValue = 5;
                     break;
               }
            }
            chessBoard[row][col] = pieceValue * pieceMultiplier;
         }
      }
   } 
   
   public static void findLegalMoves(int[][] chessBoard, boolean playerIsWhite, int[] castlingData, int ePassX, int ePassY, boolean isEPassWhite) {
      moveList.clear();
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
            int squareValue = chessBoard[row][col];
            if (squareValue < 0 && Chess.playerIsWhite) {
               switch(squareValue) {
                  case -1:
                     if (row < 7 && chessBoard[row + 1][col] == 0 && !futureCheck(chessBoard, row, col, row + 1, col, true)) {
                        moveList.add(new int[]{row, col, row + 1, col});
                     }
                     if (row == 1 && chessBoard[row + 2][col] == 0 && chessBoard[row + 1][col] == 0 && !futureCheck(chessBoard, row, col, row + 2, col, true)) {
                        moveList.add(new int[]{row, col, row + 2, col});
                     }
                     if (row < 7 && col < 7 && chessBoard[row + 1][col + 1] > 0 && !futureCheck(chessBoard, row, col, row + 1, col + 1, true)) {
                        moveList.add(new int[]{row, col, row + 1, col + 1});
                     }
                     if (row < 7 && col > 0 && chessBoard[row + 1][col - 1] > 0 && !futureCheck(chessBoard, row, col, row + 1, col - 1, true)) {
                        moveList.add(new int[]{row, col, row + 1, col - 1});
                     }
                     if (row < 7 && col > 0 && col - 1 == ePassY && row == ePassX && isEPassWhite && !futureCheck(chessBoard, row, col, row + 1, col - 1, true)) {
                        moveList.add(new int[]{row, col, row + 1, col - 1});
                     }
                     if (row < 7 && col < 7 && col + 1 == ePassY && row == ePassX && isEPassWhite && !futureCheck(chessBoard, row, col, row + 1, col + 1, true)) {
                        moveList.add(new int[]{row, col, row + 1, col + 1});
                     }
                     break;
                  case -3:
                     if (row < 7 && col < 6 && chessBoard[row + 1][col + 2] >= 0 && !futureCheck(chessBoard, row, col, row + 1, col + 2, true)) {
                        moveList.add(new int[]{row, col, row + 1, col + 2});
                     }
                     if (row < 7 && col > 1 && chessBoard[row + 1][col - 2] >= 0 && !futureCheck(chessBoard, row, col, row + 1, col - 2, true)) {
                        moveList.add(new int[]{row, col, row + 1, col - 2});
                     }
                     if (row > 0 && col < 6 && chessBoard[row - 1][col + 2] >= 0 && !futureCheck(chessBoard, row, col, row - 1, col + 2, true)) {
                        moveList.add(new int[]{row, col, row - 1, col + 2});
                     }
                     if (row > 0 && col > 1 && chessBoard[row - 1][col - 2] >= 0 && !futureCheck(chessBoard, row, col, row - 1, col - 2, true)) {
                        moveList.add(new int[]{row, col, row - 1, col - 2});
                     }
                     if (row < 6 && col < 7 && chessBoard[row + 2][col + 1] >= 0 && !futureCheck(chessBoard, row, col, row + 2, col + 1, true)) {
                        moveList.add(new int[]{row, col, row + 2, col + 1});
                     }
                     if (row < 6 && col > 0 && chessBoard[row + 2][col - 1] >= 0 && !futureCheck(chessBoard, row, col, row + 2, col - 1, true)) {
                        moveList.add(new int[]{row, col, row + 2, col - 1});
                     }
                     if (row > 1 && col < 7 && chessBoard[row - 2][col + 1] >= 0 && !futureCheck(chessBoard, row, col, row - 2, col + 1, true)) {
                        moveList.add(new int[]{row, col, row - 2, col + 1});
                     }
                     if (row > 1 && col > 0 && chessBoard[row - 2][col - 1] >= 0 && !futureCheck(chessBoard, row, col, row - 2, col - 1, true)) {
                        moveList.add(new int[]{row, col, row - 2, col - 1});
                     }
                     break;
                  case -4:
                     int increment = 1;
                     while (row - increment >= 0 && col - increment >= 0 && chessBoard[row - increment][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col - increment, true)) {
                           moveList.add(new int[]{row, col, row - increment, col - increment});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && col - increment >= 0 && chessBoard[row - increment][col - increment] > 0 && !futureCheck(chessBoard, row, col, row - increment, col - increment, true)) {
                        moveList.add(new int[]{row, col, row - increment, col - increment});
                     }
                     increment = 1;
                     while (row - increment >= 0 && col + increment < 8 && chessBoard[row - increment][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col + increment, true)) {
                           moveList.add(new int[]{row, col, row - increment, col + increment});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && col + increment < 8 && chessBoard[row - increment][col + increment] > 0 && !futureCheck(chessBoard, row, col, row - increment, col + increment, true)) {
                        moveList.add(new int[]{row, col, row - increment, col + increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && col - increment >= 0 && chessBoard[row + increment][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col - increment, true)) {
                           moveList.add(new int[]{row, col, row + increment, col - increment});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && col - increment >= 0 && chessBoard[row + increment][col - increment] > 0 && !futureCheck(chessBoard, row, col, row + increment, col - increment, true)) {
                        moveList.add(new int[]{row, col, row + increment, col - increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && col + increment < 8 && chessBoard[row + increment][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col + increment, true)) {
                           moveList.add(new int[]{row, col, row + increment, col + increment});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && col + increment < 8 && chessBoard[row + increment][col + increment] > 0 && !futureCheck(chessBoard, row, col, row + increment, col + increment, true)) {
                        moveList.add(new int[]{row, col, row + increment, col + increment});
                     }
                     break;
                  case -5:
                     increment = 1;
                     while (row + increment < 8 && chessBoard[row + increment][col] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col, true)) {
                           moveList.add(new int[]{row, col, row + increment, col});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && chessBoard[row + increment][col] > 0 && !futureCheck(chessBoard, row, col, row + increment, col, true)) {
                        moveList.add(new int[]{row, col, row + increment, col});
                     }
                     increment = 1;
                     while (row - increment >= 0 && chessBoard[row - increment][col] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col, true)) {
                           moveList.add(new int[]{row, col, row - increment, col});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && chessBoard[row - increment][col] > 0 && !futureCheck(chessBoard, row, col, row - increment, col, true)) {
                        moveList.add(new int[]{row, col, row - increment, col});
                     }
                     increment = 1;
                     while (col - increment >= 0 && chessBoard[row][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row, col - increment, true)) {
                           moveList.add(new int[]{row, col, row, col - increment});
                        }
                        increment++;
                     }
                     if (col - increment >= 0 && chessBoard[row][col - increment] > 0 && !futureCheck(chessBoard, row, col, row, col - increment, true)) {
                        moveList.add(new int[]{row, col, row, col - increment});
                     }
                     increment = 1;
                     while (col + increment < 8 && chessBoard[row][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row, col + increment, true)) {
                           moveList.add(new int[]{row, col, row, col + increment});
                        }
                        increment++;
                     }
                     if (col + increment < 8 && chessBoard[row][col + increment] > 0 && !futureCheck(chessBoard, row, col, row, col + increment, true)) {
                        moveList.add(new int[]{row, col, row, col + increment});
                     }
                     break;
                  case -9:
                     increment = 1;
                     while (row - increment >= 0 && col - increment >= 0 && chessBoard[row - increment][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col - increment, true)) {
                           moveList.add(new int[]{row, col, row - increment, col - increment});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && col - increment >= 0 && chessBoard[row - increment][col - increment] > 0 && !futureCheck(chessBoard, row, col, row - increment, col - increment, true)) {
                        moveList.add(new int[]{row, col, row - increment, col - increment});
                     }
                     increment = 1;
                     while (row - increment >= 0 && col + increment < 8 && chessBoard[row - increment][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col + increment, true)) {
                           moveList.add(new int[]{row, col, row - increment, col + increment});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && col + increment < 8 && chessBoard[row - increment][col + increment] > 0 && !futureCheck(chessBoard, row, col, row - increment, col + increment, true)) {
                        moveList.add(new int[]{row, col, row - increment, col + increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && col - increment >= 0 && chessBoard[row + increment][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col - increment, true)) {
                           moveList.add(new int[]{row, col, row + increment, col - increment});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && col - increment >= 0 && chessBoard[row + increment][col - increment] > 0 && !futureCheck(chessBoard, row, col, row + increment, col - increment, true)) {
                        moveList.add(new int[]{row, col, row + increment, col - increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && col + increment < 8 && chessBoard[row + increment][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col + increment, true)) {
                           moveList.add(new int[]{row, col, row + increment, col + increment});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && col + increment < 8 && chessBoard[row + increment][col + increment] > 0 && !futureCheck(chessBoard, row, col, row + increment, col + increment, true)) {
                        moveList.add(new int[]{row, col, row + increment, col + increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && chessBoard[row + increment][col] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col, true)) {
                           moveList.add(new int[]{row, col, row + increment, col});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && chessBoard[row + increment][col] > 0 && !futureCheck(chessBoard, row, col, row + increment, col, true)) {
                        moveList.add(new int[]{row, col, row + increment, col});
                     }
                     increment = 1;
                     while (row - increment >= 0 && chessBoard[row - increment][col] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col, true)) {
                           moveList.add(new int[]{row, col, row - increment, col});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && chessBoard[row - increment][col] > 0 && !futureCheck(chessBoard, row, col, row - increment, col, true)) {
                        moveList.add(new int[]{row, col, row - increment, col});
                     }
                     increment = 1;
                     while (col - increment >= 0 && chessBoard[row][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row, col - increment, true)) {
                           moveList.add(new int[]{row, col, row, col - increment});
                        }
                        increment++;
                     }
                     if (col - increment >= 0 && chessBoard[row][col - increment] > 0 && !futureCheck(chessBoard, row, col, row, col - increment, true)) {
                        moveList.add(new int[]{row, col, row, col - increment});
                     }
                     increment = 1;
                     while (col + increment < 8 && chessBoard[row][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row, col + increment, true)) {
                           moveList.add(new int[]{row, col, row, col + increment});
                        }
                        increment++;
                     }
                     if (col + increment < 8 && chessBoard[row][col + increment] > 0 && !futureCheck(chessBoard, row, col, row, col + increment, true)) {
                        moveList.add(new int[]{row, col, row, col + increment});
                     }
                     break;
                  case -15:
                     if (row > 0 && chessBoard[row - 1][col] >= 0 && !futureCheck(chessBoard, row, col, row - 1, col, true)) {
                        moveList.add(new int[]{row, col, row - 1, col});
                     }
                     if (row > 0 && col > 0 && chessBoard[row - 1][col - 1] >= 0 && !futureCheck(chessBoard, row, col, row - 1, col - 1, true)) {
                        moveList.add(new int[]{row, col, row - 1, col - 1});
                     }
                     if (row > 0 && col < 7 && chessBoard[row - 1][col + 1] >= 0 && !futureCheck(chessBoard, row, col, row - 1, col + 1, true)) {
                        moveList.add(new int[]{row, col, row - 1, col + 1});
                     }
                     if (row < 7 && chessBoard[row + 1][col] >= 0 && !futureCheck(chessBoard, row, col, row + 1, col, true)) {
                        moveList.add(new int[]{row, col, row + 1, col});
                     }
                     if (row < 7 && col > 0 && chessBoard[row + 1][col - 1] >= 0 && !futureCheck(chessBoard, row, col, row + 1, col - 1, true)) {
                        moveList.add(new int[]{row, col, row + 1, col - 1});
                     }
                     if (row < 7 && col < 7 && chessBoard[row + 1][col + 1] >= 0 && !futureCheck(chessBoard, row, col, row + 1, col + 1, true)) {
                        moveList.add(new int[]{row, col, row + 1, col + 1});
                     }
                     if (col > 0 && chessBoard[row][col - 1] >= 0 && !futureCheck(chessBoard, row, col, row, col - 1, true)) {
                        moveList.add(new int[]{row, col, row, col - 1});
                     }
                     if (col < 7 && chessBoard[row][col + 1] >= 0 && !futureCheck(chessBoard, row, col, row, col + 1, true)) {
                        moveList.add(new int[]{row, col, row, col + 1});
                     }
                     if (castlingData[3] == 1 && castlingData[4] == 1 && chessBoard[row][col - 1] == 0 && chessBoard[row][col - 2] == 0 && chessBoard[row][col - 3] == 0 && !futureCheck(chessBoard, row, col, row, col, true) && !futureCheck(chessBoard, row, col, row, col - 1, true) && !futureCheck(chessBoard, row, col, row, col - 2, true)) {
                        playerMoveList.add(new int[]{row, col, row, col - 2});
                     }
                     if (castlingData[5] == 1 && castlingData[4] == 1 && chessBoard[row][col + 1] == 0 && chessBoard[row][col + 2] == 0 && !futureCheck(chessBoard, row, col, row, col, true) && !futureCheck(chessBoard, row, col, row, col + 1, true) && !futureCheck(chessBoard, row, col, row, col + 2, true)) {
                        playerMoveList.add(new int[]{row, col, row, col + 2});
                     }
                     break;
               }
            } else if (squareValue > 0 && !Chess.playerIsWhite) {
               switch(squareValue) {
                  case 1:
                     if (row < 7 && chessBoard[row + 1][col] == 0 && !futureCheck(chessBoard, row, col, row + 1, col, true)) {
                        moveList.add(new int[]{row, col, row + 1, col});
                     }
                     if (row == 1 && chessBoard[row + 2][col] == 0 && chessBoard[row + 1][col] == 0 && !futureCheck(chessBoard, row, col, row + 2, col, true)) {
                        moveList.add(new int[]{row, col, row + 2, col});
                     }
                     if (row < 7 && col < 7 && chessBoard[row + 1][col + 1] < 0 && !futureCheck(chessBoard, row, col, row + 1, col + 1, true)) {
                        moveList.add(new int[]{row, col, row + 1, col + 1});
                     }
                     if (row < 7 && col > 0 && chessBoard[row + 1][col - 1] < 0 && !futureCheck(chessBoard, row, col, row + 1, col - 1, true)) {
                        moveList.add(new int[]{row, col, row + 1, col - 1});
                     }
                     if (row < 7 && col > 0 && col - 1 == ePassY && row == ePassX && !isEPassWhite && !futureCheck(chessBoard, row, col, row + 1, col - 1, true)) {
                        moveList.add(new int[]{row, col, row + 1, col - 1});
                     }
                     if (row < 7 && col < 7 && col + 1 == ePassY && row == ePassX && !isEPassWhite && !futureCheck(chessBoard, row, col, row + 1, col + 1, true)) {
                        moveList.add(new int[]{row, col, row + 1, col + 1});
                     }
                     break;
                  case 3:
                     if (row < 7 && col < 6 && chessBoard[row + 1][col + 2] <= 0 && !futureCheck(chessBoard, row, col, row + 1, col + 2, true)) {
                        moveList.add(new int[]{row, col, row + 1, col + 2});
                     }
                     if (row < 7 && col > 1 && chessBoard[row + 1][col - 2] <= 0 && !futureCheck(chessBoard, row, col, row + 1, col - 2, true)) {
                        moveList.add(new int[]{row, col, row + 1, col - 2});
                     }
                     if (row > 0 && col < 6 && chessBoard[row - 1][col + 2] <= 0 && !futureCheck(chessBoard, row, col, row - 1, col + 2, true)) {
                        moveList.add(new int[]{row, col, row - 1, col + 2});
                     }
                     if (row > 0 && col > 1 && chessBoard[row - 1][col - 2] <= 0 && !futureCheck(chessBoard, row, col, row - 1, col - 2, true)) {
                        moveList.add(new int[]{row, col, row - 1, col - 2});
                     }
                     if (row < 6 && col < 7 && chessBoard[row + 2][col + 1] <= 0 && !futureCheck(chessBoard, row, col, row + 2, col + 1, true)) {
                        moveList.add(new int[]{row, col, row + 2, col + 1});
                     }
                     if (row < 6 && col > 0 && chessBoard[row + 2][col - 1] <= 0 && !futureCheck(chessBoard, row, col, row + 2, col - 1, true)) {
                        moveList.add(new int[]{row, col, row + 2, col - 1});
                     }
                     if (row > 1 && col < 7 && chessBoard[row - 2][col + 1] <= 0 && !futureCheck(chessBoard, row, col, row - 2, col + 1, true)) {
                        moveList.add(new int[]{row, col, row - 2, col + 1});
                     }
                     if (row > 1 && col > 0 && chessBoard[row - 2][col - 1] <= 0 && !futureCheck(chessBoard, row, col, row - 2, col - 1, true)) {
                        moveList.add(new int[]{row, col, row - 2, col - 1});
                     }
                     break;
                  case 4:
                     int increment = 1;
                     while (row - increment >= 0 && col - increment >= 0 && chessBoard[row - increment][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col - increment, true)) {
                           moveList.add(new int[]{row, col, row - increment, col - increment});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && col - increment >= 0 && chessBoard[row - increment][col - increment] < 0 && !futureCheck(chessBoard, row, col, row - increment, col - increment, true)) {
                        moveList.add(new int[]{row, col, row - increment, col - increment});
                     }
                     increment = 1;
                     while (row - increment >= 0 && col + increment < 8 && chessBoard[row - increment][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col + increment, true)) {
                           moveList.add(new int[]{row, col, row - increment, col + increment});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && col + increment < 8 && chessBoard[row - increment][col + increment] < 0 && !futureCheck(chessBoard, row, col, row - increment, col + increment, true)) {
                        moveList.add(new int[]{row, col, row - increment, col + increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && col - increment >= 0 && chessBoard[row + increment][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col - increment, true)) {
                           moveList.add(new int[]{row, col, row + increment, col - increment});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && col - increment >= 0 && chessBoard[row + increment][col - increment] < 0 && !futureCheck(chessBoard, row, col, row + increment, col - increment, true)) {
                        moveList.add(new int[]{row, col, row + increment, col - increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && col + increment < 8 && chessBoard[row + increment][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col + increment, true)) {
                           moveList.add(new int[]{row, col, row + increment, col + increment});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && col + increment < 8 && chessBoard[row + increment][col + increment] < 0 && !futureCheck(chessBoard, row, col, row + increment, col + increment, true)) {
                        moveList.add(new int[]{row, col, row + increment, col + increment});
                     }
                     break;
                  case 5:
                     increment = 1;
                     while (row + increment < 8 && chessBoard[row + increment][col] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col, true)) {
                           moveList.add(new int[]{row, col, row + increment, col});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && chessBoard[row + increment][col] < 0 && !futureCheck(chessBoard, row, col, row + increment, col, true)) {
                        moveList.add(new int[]{row, col, row + increment, col});
                     }
                     increment = 1;
                     while (row - increment >= 0 && chessBoard[row - increment][col] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col, true)) {
                           moveList.add(new int[]{row, col, row - increment, col});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && chessBoard[row - increment][col] < 0 && !futureCheck(chessBoard, row, col, row - increment, col, true)) {
                        moveList.add(new int[]{row, col, row - increment, col});
                     }
                     increment = 1;
                     while (col - increment >= 0 && chessBoard[row][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row, col - increment, true)) {
                           moveList.add(new int[]{row, col, row, col - increment});
                        }
                        increment++;
                     }
                     if (col - increment >= 0 && chessBoard[row][col - increment] < 0 && !futureCheck(chessBoard, row, col, row, col - increment, true)) {
                        moveList.add(new int[]{row, col, row, col - increment});
                     }
                     increment = 1;
                     while (col + increment < 8 && chessBoard[row][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row, col + increment, true)) {
                           moveList.add(new int[]{row, col, row, col + increment});
                        }
                        increment++;
                     }
                     if (col + increment < 8 && chessBoard[row][col + increment] < 0 && !futureCheck(chessBoard, row, col, row, col + increment, true)) {
                        moveList.add(new int[]{row, col, row, col + increment});
                     }
                     break;
                  case 9:
                     increment = 1;
                     while (row - increment >= 0 && col - increment >= 0 && chessBoard[row - increment][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col - increment, true)) {
                           moveList.add(new int[]{row, col, row - increment, col - increment});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && col - increment >= 0 && chessBoard[row - increment][col - increment] < 0 && !futureCheck(chessBoard, row, col, row - increment, col - increment, true)) {
                        moveList.add(new int[]{row, col, row - increment, col - increment});
                     }
                     increment = 1;
                     while (row - increment >= 0 && col + increment < 8 && chessBoard[row - increment][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col + increment, true)) {
                           moveList.add(new int[]{row, col, row - increment, col + increment});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && col + increment < 8 && chessBoard[row - increment][col + increment] < 0 && !futureCheck(chessBoard, row, col, row - increment, col + increment, true)) {
                        moveList.add(new int[]{row, col, row - increment, col + increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && col - increment >= 0 && chessBoard[row + increment][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col - increment, true)) {
                           moveList.add(new int[]{row, col, row + increment, col - increment});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && col - increment >= 0 && chessBoard[row + increment][col - increment] < 0 && !futureCheck(chessBoard, row, col, row + increment, col - increment, true)) {
                        moveList.add(new int[]{row, col, row + increment, col - increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && col + increment < 8 && chessBoard[row + increment][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col + increment, true)) {
                           moveList.add(new int[]{row, col, row + increment, col + increment});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && col + increment < 8 && chessBoard[row + increment][col + increment] < 0 && !futureCheck(chessBoard, row, col, row + increment, col + increment, true)) {
                        moveList.add(new int[]{row, col, row + increment, col + increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && chessBoard[row + increment][col] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col, true)) {
                           moveList.add(new int[]{row, col, row + increment, col});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && chessBoard[row + increment][col] < 0 && !futureCheck(chessBoard, row, col, row + increment, col, true)) {
                        moveList.add(new int[]{row, col, row + increment, col});
                     }
                     increment = 1;
                     while (row - increment >= 0 && chessBoard[row - increment][col] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col, true)) {
                           moveList.add(new int[]{row, col, row - increment, col});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && chessBoard[row - increment][col] < 0 && !futureCheck(chessBoard, row, col, row - increment, col, true)) {
                        moveList.add(new int[]{row, col, row - increment, col});
                     }
                     increment = 1;
                     while (col - increment >= 0 && chessBoard[row][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row, col - increment, true)) {
                           moveList.add(new int[]{row, col, row, col - increment});
                        }
                        increment++;
                     }
                     if (col - increment >= 0 && chessBoard[row][col - increment] < 0 && !futureCheck(chessBoard, row, col, row, col - increment, true)) {
                        moveList.add(new int[]{row, col, row, col - increment});
                     }
                     increment = 1;
                     while (col + increment < 8 && chessBoard[row][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row, col + increment, true)) {
                           moveList.add(new int[]{row, col, row, col + increment});
                        }
                        increment++;
                     }
                     if (col + increment < 8 && chessBoard[row][col + increment] < 0 && !futureCheck(chessBoard, row, col, row, col + increment, true)) {
                        moveList.add(new int[]{row, col, row, col + increment});
                     }
                     break;
                  case 15:
                     if (row > 0 && chessBoard[row - 1][col] <= 0 && !futureCheck(chessBoard, row, col, row - 1, col, true)) {
                        moveList.add(new int[]{row, col, row - 1, col});
                     }
                     if (row > 0 && col > 0 && chessBoard[row - 1][col - 1] <= 0 && !futureCheck(chessBoard, row, col, row - 1, col - 1, true)) {
                        moveList.add(new int[]{row, col, row - 1, col - 1});
                     }
                     if (row > 0 && col < 7 && chessBoard[row - 1][col + 1] <= 0 && !futureCheck(chessBoard, row, col, row - 1, col + 1, true)) {
                        moveList.add(new int[]{row, col, row - 1, col + 1});
                     }
                     if (row < 7 && chessBoard[row + 1][col] <= 0 && !futureCheck(chessBoard, row, col, row + 1, col, true)) {
                        moveList.add(new int[]{row, col, row + 1, col});
                     }
                     if (row < 7 && col > 0 && chessBoard[row + 1][col - 1] <= 0 && !futureCheck(chessBoard, row, col, row + 1, col - 1, true)) {
                        moveList.add(new int[]{row, col, row + 1, col - 1});
                     }
                     if (row < 7 && col < 7 && chessBoard[row + 1][col + 1] <= 0 && !futureCheck(chessBoard, row, col, row + 1, col + 1, true)) {
                        moveList.add(new int[]{row, col, row + 1, col + 1});
                     }
                     if (col > 0 && chessBoard[row][col - 1] <= 0 && !futureCheck(chessBoard, row, col, row, col - 1, true)) {
                        moveList.add(new int[]{row, col, row, col - 1});
                     }
                     if (col < 7 && chessBoard[row][col + 1] <= 0 && !futureCheck(chessBoard, row, col, row, col + 1, true)) {
                        moveList.add(new int[]{row, col, row, col + 1});
                     }
                     if (castlingData[3] == 1 && castlingData[4] == 1 && chessBoard[row][col - 1] == 0 && chessBoard[row][col - 2] == 0 && !futureCheck(chessBoard, row, col, row, col, true) && !futureCheck(chessBoard, row, col, row, col - 1, true) && !futureCheck(chessBoard, row, col, row, col - 2, true)) {
                        playerMoveList.add(new int[]{row, col, row, col - 2});
                     }
                     if (castlingData[5] == 1 && castlingData[4] == 1 && chessBoard[row][col + 1] == 0 && chessBoard[row][col + 2] == 0 && chessBoard[row][col + 3] == 0 && !futureCheck(chessBoard, row, col, row, col, true) && !futureCheck(chessBoard, row, col, row, col + 1, true) && !futureCheck(chessBoard, row, col, row, col + 2, true)) {
                        playerMoveList.add(new int[]{row, col, row, col + 2});
                     }
                     break;
               }
            }
         }
      } 
   }
   
   public static void findPlayerMoves(int[][] chessBoard, boolean playerIsWhite, int[] castlingData, int ePassX, int ePassY, boolean isEPassWhite) {
      playerMoveList.clear();
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
            int squareValue = chessBoard[row][col];
            if (squareValue > 0 && Chess.playerIsWhite) {
               switch(squareValue) {
                  case 1:
                     if (row > 0 && chessBoard[row - 1][col] == 0 && !futureCheck(chessBoard, row, col, row - 1, col, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col});
                     }
                     if (row == 6 && chessBoard[row - 2][col] == 0 && chessBoard[row - 1][col] == 0 && !futureCheck(chessBoard, row, col, row - 2, col, false)) {
                        playerMoveList.add(new int[]{row, col, row - 2, col});
                     }
                     if (row > 0 && col < 7 && chessBoard[row - 1][col + 1] < 0 && !futureCheck(chessBoard, row, col, row - 1, col + 1, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col + 1});
                     }
                     if (row > 0 && col > 0 && chessBoard[row - 1][col - 1] < 0 && !futureCheck(chessBoard, row, col, row - 1, col - 1, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col - 1});
                     }
                     if (row > 0 && col > 0 && col - 1 == ePassY && row == ePassX && !isEPassWhite && !futureCheck(chessBoard, row, col, row - 1, col - 1, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col - 1});
                     }
                     if (row > 0 && col < 7 && col + 1 == ePassY && row == ePassX && !isEPassWhite && !futureCheck(chessBoard, row, col, row - 1, col + 1, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col + 1});
                     }
                     break;
                  case 3:
                     if (row < 7 && col < 6 && chessBoard[row + 1][col + 2] <= 0 && !futureCheck(chessBoard, row, col, row + 1, col + 2, false)) {
                        playerMoveList.add(new int[]{row, col, row + 1, col + 2});
                     }
                     if (row < 7 && col > 1 && chessBoard[row + 1][col - 2] <= 0 && !futureCheck(chessBoard, row, col, row + 1, col - 2, false)) {
                        playerMoveList.add(new int[]{row, col, row + 1, col - 2});
                     }
                     if (row > 0 && col < 6 && chessBoard[row - 1][col + 2] <= 0 && !futureCheck(chessBoard, row, col, row - 1, col + 2, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col + 2});
                     }
                     if (row > 0 && col > 1 && chessBoard[row - 1][col - 2] <= 0 && !futureCheck(chessBoard, row, col, row - 1, col - 2, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col - 2});
                     }
                     if (row < 6 && col < 7 && chessBoard[row + 2][col + 1] <= 0 && !futureCheck(chessBoard, row, col, row + 2, col + 1, false)) {
                        playerMoveList.add(new int[]{row, col, row + 2, col + 1});
                     }
                     if (row < 6 && col > 0 && chessBoard[row + 2][col - 1] <= 0 && !futureCheck(chessBoard, row, col, row + 2, col - 1, false)) {
                        playerMoveList.add(new int[]{row, col, row + 2, col - 1});
                     }
                     if (row > 1 && col < 7 && chessBoard[row - 2][col + 1] <= 0 && !futureCheck(chessBoard, row, col, row - 2, col + 1, false)) {
                        playerMoveList.add(new int[]{row, col, row - 2, col + 1});
                     }
                     if (row > 1 && col > 0 && chessBoard[row - 2][col - 1] <= 0 && !futureCheck(chessBoard, row, col, row - 2, col - 1, false)) {
                        playerMoveList.add(new int[]{row, col, row - 2, col - 1});
                     }
                     break;
                  case 4:
                     int increment = 1;
                     while (row - increment >= 0 && col - increment >= 0 && chessBoard[row - increment][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col - increment, false)) {
                           playerMoveList.add(new int[]{row, col, row - increment, col - increment});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && col - increment >= 0 && chessBoard[row - increment][col - increment] < 0 && !futureCheck(chessBoard, row, col, row - increment, col - increment, false)) {
                        playerMoveList.add(new int[]{row, col, row - increment, col - increment});
                     }
                     increment = 1;
                     while (row - increment >= 0 && col + increment < 8 && chessBoard[row - increment][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col + increment, false)) {
                           playerMoveList.add(new int[]{row, col, row - increment, col + increment});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && col + increment < 8 && chessBoard[row - increment][col + increment] < 0 && !futureCheck(chessBoard, row, col, row - increment, col + increment, false)) {
                        playerMoveList.add(new int[]{row, col, row - increment, col + increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && col - increment >= 0 && chessBoard[row + increment][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col - increment, false)) {
                           playerMoveList.add(new int[]{row, col, row + increment, col - increment});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && col - increment >= 0 && chessBoard[row + increment][col - increment] < 0 && !futureCheck(chessBoard, row, col, row + increment, col - increment, false)) {
                        playerMoveList.add(new int[]{row, col, row + increment, col - increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && col + increment < 8 && chessBoard[row + increment][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col + increment, false)) {
                           playerMoveList.add(new int[]{row, col, row + increment, col + increment});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && col + increment < 8 && chessBoard[row + increment][col + increment] < 0 && !futureCheck(chessBoard, row, col, row + increment, col + increment, false)) {
                        playerMoveList.add(new int[]{row, col, row + increment, col + increment});
                     }
                     break;
                  case 5:
                     increment = 1;
                     while (row + increment < 8 && chessBoard[row + increment][col] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col, false)) {
                           playerMoveList.add(new int[]{row, col, row + increment, col});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && chessBoard[row + increment][col] < 0 && !futureCheck(chessBoard, row, col, row + increment, col, false)) {
                        playerMoveList.add(new int[]{row, col, row + increment, col});
                     }
                     increment = 1;
                     while (row - increment >= 0 && chessBoard[row - increment][col] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col, false)) {
                           playerMoveList.add(new int[]{row, col, row - increment, col});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && chessBoard[row - increment][col] < 0 && !futureCheck(chessBoard, row, col, row - increment, col, false)) {
                        playerMoveList.add(new int[]{row, col, row - increment, col});
                     }
                     increment = 1;
                     while (col - increment >= 0 && chessBoard[row][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row, col - increment, false)) {
                           playerMoveList.add(new int[]{row, col, row, col - increment});
                        }
                        increment++;
                     }
                     if (col - increment >= 0 && chessBoard[row][col - increment] < 0 && !futureCheck(chessBoard, row, col, row, col - increment, false)) {
                        playerMoveList.add(new int[]{row, col, row, col - increment});
                     }
                     increment = 1;
                     while (col + increment < 8 && chessBoard[row][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row, col + increment, false)) {
                           playerMoveList.add(new int[]{row, col, row, col + increment});
                        }
                        increment++;
                     }
                     if (col + increment < 8 && chessBoard[row][col + increment] < 0 && !futureCheck(chessBoard, row, col, row, col + increment, false)) {
                        playerMoveList.add(new int[]{row, col, row, col + increment});
                     }
                     break;
                  case 9:
                     increment = 1;
                     while (row - increment >= 0 && col - increment >= 0 && chessBoard[row - increment][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col - increment, false)) {
                           playerMoveList.add(new int[]{row, col, row - increment, col - increment});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && col - increment >= 0 && chessBoard[row - increment][col - increment] < 0 && !futureCheck(chessBoard, row, col, row - increment, col - increment, false)) {
                        playerMoveList.add(new int[]{row, col, row - increment, col - increment});
                     }
                     increment = 1;
                     while (row - increment >= 0 && col + increment < 8 && chessBoard[row - increment][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col + increment, false)) {
                           playerMoveList.add(new int[]{row, col, row - increment, col + increment});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && col + increment < 8 && chessBoard[row - increment][col + increment] < 0 && !futureCheck(chessBoard, row, col, row - increment, col + increment, false)) {
                        playerMoveList.add(new int[]{row, col, row - increment, col + increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && col - increment >= 0 && chessBoard[row + increment][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col - increment, false)) {
                           playerMoveList.add(new int[]{row, col, row + increment, col - increment});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && col - increment >= 0 && chessBoard[row + increment][col - increment] < 0 && !futureCheck(chessBoard, row, col, row + increment, col - increment, false)) {
                        playerMoveList.add(new int[]{row, col, row + increment, col - increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && col + increment < 8 && chessBoard[row + increment][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col + increment, false)) {
                           playerMoveList.add(new int[]{row, col, row + increment, col + increment});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && col + increment < 8 && chessBoard[row + increment][col + increment] < 0 && !futureCheck(chessBoard, row, col, row + increment, col + increment, false)) {
                        playerMoveList.add(new int[]{row, col, row + increment, col + increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && chessBoard[row + increment][col] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col, false)) {
                           playerMoveList.add(new int[]{row, col, row + increment, col});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && chessBoard[row + increment][col] < 0 && !futureCheck(chessBoard, row, col, row + increment, col, false)) {
                        playerMoveList.add(new int[]{row, col, row + increment, col});
                     }
                     increment = 1;
                     while (row - increment >= 0 && chessBoard[row - increment][col] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col, false)) {
                           playerMoveList.add(new int[]{row, col, row - increment, col});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && chessBoard[row - increment][col] < 0 && !futureCheck(chessBoard, row, col, row - increment, col, false)) {
                        playerMoveList.add(new int[]{row, col, row - increment, col});
                     }
                     increment = 1;
                     while (col - increment >= 0 && chessBoard[row][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row, col - increment, false)) {
                           playerMoveList.add(new int[]{row, col, row, col - increment});
                        }
                        increment++;
                     }
                     if (col - increment >= 0 && chessBoard[row][col - increment] < 0 && !futureCheck(chessBoard, row, col, row, col - increment, false)) {
                        playerMoveList.add(new int[]{row, col, row, col - increment});
                     }
                     increment = 1;
                     while (col + increment < 8 && chessBoard[row][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row, col + increment, false)) {
                           playerMoveList.add(new int[]{row, col, row, col + increment});
                        }
                        increment++;
                     }
                     if (col + increment < 8 && chessBoard[row][col + increment] < 0 && !futureCheck(chessBoard, row, col, row, col + increment, false)) {
                        playerMoveList.add(new int[]{row, col, row, col + increment});
                     }
                     break;
                  case 15:
                     if (row > 0 && chessBoard[row - 1][col] <= 0 && !futureCheck(chessBoard, row, col, row - 1, col, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col});
                     }
                     if (row > 0 && col > 0 && chessBoard[row - 1][col - 1] <= 0 && !futureCheck(chessBoard, row, col, row - 1, col - 1, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col - 1});
                     }
                     if (row > 0 && col < 7 && chessBoard[row - 1][col + 1] <= 0 && !futureCheck(chessBoard, row, col, row - 1, col + 1, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col + 1});
                     }
                     if (row < 7 && chessBoard[row + 1][col] <= 0 && !futureCheck(chessBoard, row, col, row + 1, col, false)) {
                        playerMoveList.add(new int[]{row, col, row + 1, col});
                     }
                     if (row < 7 && col > 0 && chessBoard[row + 1][col - 1] <= 0 && !futureCheck(chessBoard, row, col, row + 1, col - 1, false)) {
                        playerMoveList.add(new int[]{row, col, row + 1, col - 1});
                     }
                     if (row < 7 && col < 7 && chessBoard[row + 1][col + 1] <= 0 && !futureCheck(chessBoard, row, col, row + 1, col + 1, false)) {
                        playerMoveList.add(new int[]{row, col, row + 1, col + 1});
                     }
                     if (col > 0 && chessBoard[row][col - 1] <= 0 && !futureCheck(chessBoard, row, col, row, col - 1, false)) {
                        playerMoveList.add(new int[]{row, col, row, col - 1});
                     }
                     if (col < 7 && chessBoard[row][col + 1] <= 0 && !futureCheck(chessBoard, row, col, row, col + 1, false)) {
                        playerMoveList.add(new int[]{row, col, row, col + 1});
                     }
                     if (castlingData[0] == 1 && castlingData[1] == 1 && chessBoard[row][col - 1] == 0 && chessBoard[row][col - 2] == 0 && chessBoard[row][col - 3] == 0 && !futureCheck(chessBoard, row, col, row, col, false) && !futureCheck(chessBoard, row, col, row, col - 1, false) && !futureCheck(chessBoard, row, col, row, col - 2, false)) {
                        playerMoveList.add(new int[]{row, col, row, col - 2});
                     }
                     if (castlingData[2] == 1 && castlingData[1] == 1 && chessBoard[row][col + 1] == 0 && chessBoard[row][col + 2] == 0 && !futureCheck(chessBoard, row, col, row, col, false) && !futureCheck(chessBoard, row, col, row, col + 1, false) && !futureCheck(chessBoard, row, col, row, col + 2, false)) {
                        playerMoveList.add(new int[]{row, col, row, col + 2});
                     }
                     break;
               }
            } else if (squareValue < 0 && !Chess.playerIsWhite) {
               switch(squareValue) {
                  case -1:
                     if (row > 0 && chessBoard[row - 1][col] == 0 && !futureCheck(chessBoard, row, col, row - 1, col, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col});
                     }
                     if (row == 6 && chessBoard[row - 2][col] == 0 && chessBoard[row - 1][col] == 0 && !futureCheck(chessBoard, row, col, row - 2, col, false)) {
                        playerMoveList.add(new int[]{row, col, row - 2, col});
                     }
                     if (row > 0 && col < 7 && chessBoard[row - 1][col + 1] > 0 && !futureCheck(chessBoard, row, col, row - 1, col + 1, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col + 1});
                     }
                     if (row > 0 && col > 0 && chessBoard[row - 1][col - 1] > 0 && !futureCheck(chessBoard, row, col, row - 1, col - 1, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col - 1});
                     }
                     if (row > 0 && col > 0 && col - 1 == ePassY && row == ePassX && isEPassWhite && !futureCheck(chessBoard, row, col, row - 1, col - 1, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col - 1});
                     }
                     if (row > 0 && col < 7 && col + 1 == ePassY && row == ePassX && isEPassWhite && !futureCheck(chessBoard, row, col, row - 1, col + 1, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col + 1});
                     }
                     break;
                  case -3:
                     if (row < 7 && col < 6 && chessBoard[row + 1][col + 2] >= 0 && !futureCheck(chessBoard, row, col, row + 1, col + 2, false)) {
                        playerMoveList.add(new int[]{row, col, row + 1, col + 2});
                     }
                     if (row < 7 && col > 1 && chessBoard[row + 1][col - 2] >= 0 && !futureCheck(chessBoard, row, col, row + 1, col - 2, false)) {
                        playerMoveList.add(new int[]{row, col, row + 1, col - 2});
                     }
                     if (row > 0 && col < 6 && chessBoard[row - 1][col + 2] >= 0 && !futureCheck(chessBoard, row, col, row - 1, col + 2, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col + 2});
                     }
                     if (row > 0 && col > 1 && chessBoard[row - 1][col - 2] >= 0 && !futureCheck(chessBoard, row, col, row - 1, col - 2, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col - 2});
                     }
                     if (row < 6 && col < 7 && chessBoard[row + 2][col + 1] >= 0 && !futureCheck(chessBoard, row, col, row + 2, col + 1, false)) {
                        playerMoveList.add(new int[]{row, col, row + 2, col + 1});
                     }
                     if (row < 6 && col > 0 && chessBoard[row + 2][col - 1] >= 0 && !futureCheck(chessBoard, row, col, row + 2, col - 1, false)) {
                        playerMoveList.add(new int[]{row, col, row + 2, col - 1});
                     }
                     if (row > 1 && col < 7 && chessBoard[row - 2][col + 1] >= 0 && !futureCheck(chessBoard, row, col, row - 2, col + 1, false)) {
                        playerMoveList.add(new int[]{row, col, row - 2, col + 1});
                     }
                     if (row > 1 && col > 0 && chessBoard[row - 2][col - 1] >= 0 && !futureCheck(chessBoard, row, col, row - 2, col - 1, false)) {
                        playerMoveList.add(new int[]{row, col, row - 2, col - 1});
                     }
                     break;
                  case -4:
                     int increment = 1;
                     while (row - increment >= 0 && col - increment >= 0 && chessBoard[row - increment][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col - increment, false)) {
                           playerMoveList.add(new int[]{row, col, row - increment, col - increment});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && col - increment >= 0 && chessBoard[row - increment][col - increment] > 0 && !futureCheck(chessBoard, row, col, row - increment, col - increment, false)) {
                        playerMoveList.add(new int[]{row, col, row - increment, col - increment});
                     }
                     increment = 1;
                     while (row - increment >= 0 && col + increment < 8 && chessBoard[row - increment][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col + increment, false)) {
                           playerMoveList.add(new int[]{row, col, row - increment, col + increment});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && col + increment < 8 && chessBoard[row - increment][col + increment] > 0 && !futureCheck(chessBoard, row, col, row - increment, col + increment, false)) {
                        playerMoveList.add(new int[]{row, col, row - increment, col + increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && col - increment >= 0 && chessBoard[row + increment][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col - increment, false)) {
                           playerMoveList.add(new int[]{row, col, row + increment, col - increment});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && col - increment >= 0 && chessBoard[row + increment][col - increment] > 0 && !futureCheck(chessBoard, row, col, row + increment, col - increment, false)) {
                        playerMoveList.add(new int[]{row, col, row + increment, col - increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && col + increment < 8 && chessBoard[row + increment][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col + increment, false)) {
                           playerMoveList.add(new int[]{row, col, row + increment, col + increment});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && col + increment < 8 && chessBoard[row + increment][col + increment] > 0 && !futureCheck(chessBoard, row, col, row + increment, col + increment, false)) {
                        playerMoveList.add(new int[]{row, col, row + increment, col + increment});
                     }
                     break;
                  case -5:
                     increment = 1;
                     while (row + increment < 8 && chessBoard[row + increment][col] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col, false)) {
                           playerMoveList.add(new int[]{row, col, row + increment, col});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && chessBoard[row + increment][col] > 0 && !futureCheck(chessBoard, row, col, row + increment, col, false)) {
                        playerMoveList.add(new int[]{row, col, row + increment, col});
                     }
                     increment = 1;
                     while (row - increment >= 0 && chessBoard[row - increment][col] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col, false)) {
                           playerMoveList.add(new int[]{row, col, row - increment, col});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && chessBoard[row - increment][col] > 0 && !futureCheck(chessBoard, row, col, row - increment, col, false)) {
                        playerMoveList.add(new int[]{row, col, row - increment, col});
                     }
                     increment = 1;
                     while (col - increment >= 0 && chessBoard[row][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row, col - increment, false)) {
                           playerMoveList.add(new int[]{row, col, row, col - increment});
                        }
                        increment++;
                     }
                     if (col - increment >= 0 && chessBoard[row][col - increment] > 0 && !futureCheck(chessBoard, row, col, row, col - increment, false)) {
                        playerMoveList.add(new int[]{row, col, row, col - increment});
                     }
                     increment = 1;
                     while (col + increment < 8 && chessBoard[row][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row, col + increment, false)) {
                           playerMoveList.add(new int[]{row, col, row, col + increment});
                        }
                        increment++;
                     }
                     if (col + increment < 8 && chessBoard[row][col + increment] > 0 && !futureCheck(chessBoard, row, col, row, col + increment, false)) {
                        playerMoveList.add(new int[]{row, col, row, col + increment});
                     }
                     break;
                  case -9:
                     increment = 1;
                     while (row - increment >= 0 && col - increment >= 0 && chessBoard[row - increment][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col - increment, false)) {
                           playerMoveList.add(new int[]{row, col, row - increment, col - increment});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && col - increment >= 0 && chessBoard[row - increment][col - increment] > 0 && !futureCheck(chessBoard, row, col, row - increment, col - increment, false)) {
                        playerMoveList.add(new int[]{row, col, row - increment, col - increment});
                     }
                     increment = 1;
                     while (row - increment >= 0 && col + increment < 8 && chessBoard[row - increment][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col + increment, false)) {
                           playerMoveList.add(new int[]{row, col, row - increment, col + increment});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && col + increment < 8 && chessBoard[row - increment][col + increment] > 0 && !futureCheck(chessBoard, row, col, row - increment, col + increment, false)) {
                        playerMoveList.add(new int[]{row, col, row - increment, col + increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && col - increment >= 0 && chessBoard[row + increment][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col - increment, false)) {
                           playerMoveList.add(new int[]{row, col, row + increment, col - increment});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && col - increment >= 0 && chessBoard[row + increment][col - increment] > 0 && !futureCheck(chessBoard, row, col, row + increment, col - increment, false)) {
                        playerMoveList.add(new int[]{row, col, row + increment, col - increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && col + increment < 8 && chessBoard[row + increment][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col + increment, false)) {
                           playerMoveList.add(new int[]{row, col, row + increment, col + increment});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && col + increment < 8 && chessBoard[row + increment][col + increment] > 0 && !futureCheck(chessBoard, row, col, row + increment, col + increment, false)) {
                        playerMoveList.add(new int[]{row, col, row + increment, col + increment});
                     }
                     increment = 1;
                     while (row + increment < 8 && chessBoard[row + increment][col] == 0) {
                        if (!futureCheck(chessBoard, row, col, row + increment, col, false)) {
                           playerMoveList.add(new int[]{row, col, row + increment, col});
                        }
                        increment++;
                     }
                     if (row + increment < 8 && chessBoard[row + increment][col] > 0 && !futureCheck(chessBoard, row, col, row + increment, col, false)) {
                        playerMoveList.add(new int[]{row, col, row + increment, col});
                     }
                     increment = 1;
                     while (row - increment >= 0 && chessBoard[row - increment][col] == 0) {
                        if (!futureCheck(chessBoard, row, col, row - increment, col, false)) {
                           playerMoveList.add(new int[]{row, col, row - increment, col});
                        }
                        increment++;
                     }
                     if (row - increment >= 0 && chessBoard[row - increment][col] > 0 && !futureCheck(chessBoard, row, col, row - increment, col, false)) {
                        playerMoveList.add(new int[]{row, col, row - increment, col});
                     }
                     increment = 1;
                     while (col - increment >= 0 && chessBoard[row][col - increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row, col - increment, false)) {
                           playerMoveList.add(new int[]{row, col, row, col - increment});
                        }
                        increment++;
                     }
                     if (col - increment >= 0 && chessBoard[row][col - increment] > 0 && !futureCheck(chessBoard, row, col, row, col - increment, false)) {
                        playerMoveList.add(new int[]{row, col, row, col - increment});
                     }
                     increment = 1;
                     while (col + increment < 8 && chessBoard[row][col + increment] == 0) {
                        if (!futureCheck(chessBoard, row, col, row, col + increment, false)) {
                           playerMoveList.add(new int[]{row, col, row, col + increment});
                        }
                        increment++;
                     }
                     if (col + increment < 8 && chessBoard[row][col + increment] > 0 && !futureCheck(chessBoard, row, col, row, col + increment, false)) {
                        playerMoveList.add(new int[]{row, col, row, col + increment});
                     }
                     break;
                  case -15:
                     if (row > 0 && chessBoard[row - 1][col] >= 0 && !futureCheck(chessBoard, row, col, row - 1, col, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col});
                     }
                     if (row > 0 && col > 0 && chessBoard[row - 1][col - 1] >= 0 && !futureCheck(chessBoard, row, col, row - 1, col - 1, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col - 1});
                     }
                     if (row > 0 && col < 7 && chessBoard[row - 1][col + 1] >= 0 && !futureCheck(chessBoard, row, col, row - 1, col + 1, false)) {
                        playerMoveList.add(new int[]{row, col, row - 1, col + 1});
                     }
                     if (row < 7 && chessBoard[row + 1][col] >= 0 && !futureCheck(chessBoard, row, col, row + 1, col, false)) {
                        playerMoveList.add(new int[]{row, col, row + 1, col});
                     }
                     if (row < 7 && col > 0 && chessBoard[row + 1][col - 1] >= 0 && !futureCheck(chessBoard, row, col, row + 1, col - 1, false)) {
                        playerMoveList.add(new int[]{row, col, row + 1, col - 1});
                     }
                     if (row < 7 && col < 7 && chessBoard[row + 1][col + 1] >= 0 && !futureCheck(chessBoard, row, col, row + 1, col + 1, false)) {
                        playerMoveList.add(new int[]{row, col, row + 1, col + 1});
                     }
                     if (col > 0 && chessBoard[row][col - 1] >= 0 && !futureCheck(chessBoard, row, col, row, col - 1, false)) {
                        playerMoveList.add(new int[]{row, col, row, col - 1});
                     }
                     if (col < 7 && chessBoard[row][col + 1] >= 0 && !futureCheck(chessBoard, row, col, row, col + 1, false)) {
                        playerMoveList.add(new int[]{row, col, row, col + 1});
                     }
                     if (castlingData[0] == 1 && castlingData[1] == 1 && chessBoard[row][col - 1] == 0 && chessBoard[row][col - 2] == 0 && !futureCheck(chessBoard, row, col, row, col, false) && !futureCheck(chessBoard, row, col, row, col - 1, false) && !futureCheck(chessBoard, row, col, row, col - 2, false)) {
                        playerMoveList.add(new int[]{row, col, row, col - 2});
                     }
                     if (castlingData[2] == 1 && castlingData[1] == 1 && chessBoard[row][col + 1] == 0 && chessBoard[row][col + 2] == 0 && chessBoard[row][col + 3] == 0 && !futureCheck(chessBoard, row, col, row, col, true) && !futureCheck(chessBoard, row, col, row, col + 1, false) && !futureCheck(chessBoard, row, col, row, col + 2, false)) {
                        playerMoveList.add(new int[]{row, col, row, col + 2});
                     }
                     break;
               }
            }
         }
      } 
   }
   
   public static void printMoveList() {
      for (int i = 0; i < moveList.size(); i++) {
         System.out.println("Move #" + (i + 1) + ": r(" + moveList.get(i)[0] + "), c(" + moveList.get(i)[1] + "), tr(" + moveList.get(i)[2] + "), tc(" + moveList.get(i)[3] + ")");
      }
   }
   
   public static void printBoard(int[][] board) {
      for (int i = 0; i < 8; i++) {
         for (int i2 = 0; i2 < 8; i2++) {
            System.out.print(board[i][i2] + " ");
         }
         System.out.println("");
      }
   }
   
   public static boolean futureCheck(int[][] board, int rowFrom, int colFrom, int rowTo, int colTo, boolean isBot) {
      int[][] futureBoard = new int[8][8];
      int kingX = 0;
      int kingY = 0;
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
            if (rowFrom == row && colFrom == col) {
               futureBoard[rowFrom][colFrom] = 0;
               futureBoard[rowTo][colTo] = board[row][col];
            } else if (!(rowTo == row && colTo == col)) {
               futureBoard[row][col] = board[row][col];
            }
         }
      }
      if (isBot) {
         for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
               if (futureBoard[row][col] == -15 && Chess.playerIsWhite) {
                  kingX = row;
                  kingY = col;
               } else if (futureBoard[row][col] == 15 && !Chess.playerIsWhite) {
                  kingX = row;
                  kingY = col;
               }
            }
         }
      } else {
         for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
               if (futureBoard[row][col] == 15 && Chess.playerIsWhite) {
                  kingX = row;
                  kingY = col;
               } else if (futureBoard[row][col] == -15 && !Chess.playerIsWhite) {
                  kingX = row;
                  kingY = col;
               }
            }
         }
      }
      if (isBot) {
         if (Chess.playerIsWhite) {
            for (int row = kingX - 1, col = kingY - 1; row >= 0 && col >= 0; row--, col--) {
               if (futureBoard[row][col] < 0) {
                  break;
               } else if (futureBoard[row][col] == 4 || futureBoard[row][col] == 9) {
                  return true;
               } else if (futureBoard[row][col] > 0) {
                  break;
               }
            }
            for (int row = kingX + 1, col = kingY - 1; row < 8 && col >= 0; row++, col--) {
               if (futureBoard[row][col] < 0) {
                  break;
               } else if (futureBoard[row][col] == 4 || futureBoard[row][col] == 9) {
                  return true;
               } else if (futureBoard[row][col] > 0) {
                  break;
               }
            }
            for (int row = kingX - 1, col = kingY + 1; row >= 0 && col < 8; row--, col++) {
               if (futureBoard[row][col] < 0) {
                  break;
               } else if (futureBoard[row][col] == 4 || futureBoard[row][col] == 9) {
                  return true;
               } else if (futureBoard[row][col] > 0) {
                  break;
               }
            }
            for (int row = kingX + 1, col = kingY + 1; row < 8 && col < 8; row++, col++) {
               if (futureBoard[row][col] < 0) {
                  break;
               } else if (futureBoard[row][col] == 4 || futureBoard[row][col] == 9) {
                  return true;
               } else if (futureBoard[row][col] > 0) {
                  break;
               }
            }
            for (int col = kingY + 1; col < 8; col++) {
               if (futureBoard[kingX][col] < 0) {
                  break;
               } else if (futureBoard[kingX][col] == 5 || futureBoard[kingX][col] == 9) {
                  return true;
               } else if (futureBoard[kingX][col] > 0) {
                  break;
               }
            }
            for (int col = kingY - 1; col >= 0; col--) {
               if (futureBoard[kingX][col] < 0) {
                  break;
               } else if (futureBoard[kingX][col] == 5 || futureBoard[kingX][col] == 9) {
                  return true;
               } else if (futureBoard[kingX][col] > 0) {
                  break;
               }
            }
            for (int row = kingX + 1; row < 8; row++) {
               if (futureBoard[row][kingY] < 0) {
                  break;
               } else if (futureBoard[row][kingY] == 5 || futureBoard[row][kingY] == 9) {
                  return true;
               } else if (futureBoard[row][kingY] > 0) {
                  break;
               }
            }
            for (int row = kingX - 1; row >= 0; row--) {
               if (futureBoard[row][kingY] < 0) {
                  break;
               } else if (futureBoard[row][kingY] == 5 || futureBoard[row][kingY] == 9) {
                  return true;
               } else if (futureBoard[row][kingY] > 0) {
                  break;
               }
            }
            if (kingX < 7 && kingY > 0 && futureBoard[kingX + 1][kingY - 1] == 1) {
               return true;
            }
            if (kingX < 7 && kingY < 7 && futureBoard[kingX + 1][kingY + 1] == 1) {
               return true;
            } 
            if (kingX < 6 && kingY < 7 && futureBoard[kingX + 2][kingY + 1] == 3) {
               return true;
            } 
            if (kingX < 6 && kingY > 0 && futureBoard[kingX + 2][kingY - 1] == 3) {
               return true;
            }
            if (kingX < 7 && kingY < 6 && futureBoard[kingX + 1][kingY + 2] == 3) {
               return true;
            }
            if (kingX < 7 && kingY > 1 && futureBoard[kingX + 1][kingY - 2] == 3) {
               return true;
            }
            if (kingX > 1 && kingY > 0 && futureBoard[kingX - 2][kingY - 1] == 3) {
               return true;
            }
            if (kingX > 1 && kingY < 7 && futureBoard[kingX - 2][kingY + 1] == 3) {
               return true;
            } 
            if (kingX > 0 && kingY < 6 && futureBoard[kingX - 1][kingY + 2] == 3) {
               return true;
            }
            if (kingX > 0 && kingY > 1 && futureBoard[kingX - 1][kingY - 2] == 3) {
               return true;
            }
            if (kingX < 7 && kingY > 0 && futureBoard[kingX + 1][kingY - 1] == 15) {
               return true;
            }
            if (kingX < 7 && futureBoard[kingX + 1][kingY] == 15) {
               return true;
            }
            if (kingX < 7 && kingY < 7 && futureBoard[kingX + 1][kingY + 1] == 15) {
               return true;
            }
            if (kingY < 7 && futureBoard[kingX][kingY + 1] == 15) {
               return true;
            }
            if (kingX > 0 && kingY < 7 && futureBoard[kingX - 1][kingY + 1] == 15) {
               return true;
            }
            if (kingX > 0 && futureBoard[kingX - 1][kingY] == 15) {
               return true;
            }
            if (kingX > 0 && kingY > 0 && futureBoard[kingX - 1][kingY - 1] == 15) {
               return true;
            }
            if (kingY > 0 && futureBoard[kingX][kingY - 1] == 15) {
               return true;
            } 
         } else {
            for (int row = kingX - 1, col = kingY - 1; row >= 0 && col >= 0; row--, col--) {
               if (futureBoard[row][col] > 0) {
                  break;
               } else if (futureBoard[row][col] == -4 || futureBoard[row][col] == -9) {
                  return true;
               } else if (futureBoard[row][col] < 0) {
                  break;
               }
            }
            for (int row = kingX + 1, col = kingY - 1; row < 8 && col >= 0; row++, col--) {
               if (futureBoard[row][col] > 0) {
                  break;
               } else if (futureBoard[row][col] == -4 || futureBoard[row][col] == -9) {
                  return true;
               } else if (futureBoard[row][col] < 0) {
                  break;
               }
            }
            for (int row = kingX - 1, col = kingY + 1; row >= 0 && col < 8; row--, col++) {
               if (futureBoard[row][col] > 0) {
                  break;
               } else if (futureBoard[row][col] == -4 || futureBoard[row][col] == -9) {
                  return true;
               } else if (futureBoard[row][col] < 0) {
                  break;
               }
            }
            for (int row = kingX + 1, col = kingY + 1; row < 8 && col < 8; row++, col++) {
               if (futureBoard[row][col] > 0) {
                  break;
               } else if (futureBoard[row][col] == -4 || futureBoard[row][col] == -9) {
                  return true;
               } else if (futureBoard[row][col] < 0) {
                  break;
               }
            }
            for (int col = kingY + 1; col < 8; col++) {
               if (futureBoard[kingX][col] > 0) {
                  break;
               } else if (futureBoard[kingX][col] == -5 || futureBoard[kingX][col] == -9) {
                  return true;
               } else if (futureBoard[kingX][col] < 0) {
                  break;
               }
            }
            for (int col = kingY - 1; col >= 0; col--) {
               if (futureBoard[kingX][col] > 0) {
                  break;
               } else if (futureBoard[kingX][col] == -5 || futureBoard[kingX][col] == -9) {
                  return true;
               } else if (futureBoard[kingX][col] < 0) {
                  break;
               }
            }
            for (int row = kingX + 1; row < 8; row++) {
               if (futureBoard[row][kingY] > 0) {
                  break;
               } else if (futureBoard[row][kingY] == -5 || futureBoard[row][kingY] == -9) {
                  return true;
               } else if (futureBoard[row][kingY] < 0) {
                  break;
               }
            }
            for (int row = kingX - 1; row >= 0; row--) {
               if (futureBoard[row][kingY] > 0) {
                  break;
               } else if (futureBoard[row][kingY] == -5 || futureBoard[row][kingY] == -9) {
                  return true;
               } else if (futureBoard[row][kingY] < 0) {
                  break;
               }
            }
            if (kingX < 7 && kingY > 0 && futureBoard[kingX + 1][kingY - 1] == -1) {
               return true;
            }
            if (kingX < 7 && kingY < 7 && futureBoard[kingX + 1][kingY + 1] == -1) {
               return true;
            } 
            if (kingX < 6 && kingY < 7 && futureBoard[kingX + 2][kingY + 1] == -3) {
               return true;
            } 
            if (kingX < 6 && kingY > 0 && futureBoard[kingX + 2][kingY - 1] == -3) {
               return true;
            }
            if (kingX < 7 && kingY < 6 && futureBoard[kingX + 1][kingY + 2] == -3) {
               return true;
            }
            if (kingX < 7 && kingY > 1 && futureBoard[kingX + 1][kingY - 2] == -3) {
               return true;
            }
            if (kingX > 1 && kingY > 0 && futureBoard[kingX - 2][kingY - 1] == -3) {
               return true;
            }
            if (kingX > 1 && kingY < 7 && futureBoard[kingX - 2][kingY + 1] == -3) {
               return true;
            } 
            if (kingX > 0 && kingY < 6 && futureBoard[kingX - 1][kingY + 2] == -3) {
               return true;
            }
            if (kingX > 0 && kingY > 1 && futureBoard[kingX - 1][kingY - 2] == -3) {
               return true;
            }
            if (kingX < 7 && kingY > 0 && futureBoard[kingX + 1][kingY - 1] == -15) {
               return true;
            }
            if (kingX < 7 && futureBoard[kingX + 1][kingY] == -15) {
               return true;
            }
            if (kingX < 7 && kingY < 7 && futureBoard[kingX + 1][kingY + 1] == -15) {
               return true;
            }
            if (kingY < 7 && futureBoard[kingX][kingY + 1] == -15) {
               return true;
            }
            if (kingX > 0 && kingY < 7 && futureBoard[kingX - 1][kingY + 1] == -15) {
               return true;
            }
            if (kingX > 0 && futureBoard[kingX - 1][kingY] == -15) {
               return true;
            }
            if (kingX > 0 && kingY > 0 && futureBoard[kingX - 1][kingY - 1] == -15) {
               return true;
            }
            if (kingY > 0 && futureBoard[kingX][kingY - 1] == -15) {
               return true;
            } 
         }
         return false;
      } else {
         if (!Chess.playerIsWhite) {
            for (int row = kingX - 1, col = kingY - 1; row >= 0 && col >= 0; row--, col--) {
               if (futureBoard[row][col] < 0) {
                  break;
               } else if (futureBoard[row][col] == 4 || futureBoard[row][col] == 9) {
                  return true;
               } else if (futureBoard[row][col] > 0) {
                  break;
               }
            }
            for (int row = kingX + 1, col = kingY - 1; row < 8 && col >= 0; row++, col--) {
               if (futureBoard[row][col] < 0) {
                  break;
               } else if (futureBoard[row][col] == 4 || futureBoard[row][col] == 9) {
                  return true;
               } else if (futureBoard[row][col] > 0) {
                  break;
               }
            }
            for (int row = kingX - 1, col = kingY + 1; row >= 0 && col < 8; row--, col++) {
               if (futureBoard[row][col] < 0) {
                  break;
               } else if (futureBoard[row][col] == 4 || futureBoard[row][col] == 9) {
                  return true;
               } else if (futureBoard[row][col] > 0) {
                  break;
               }
            }
            for (int row = kingX + 1, col = kingY + 1; row < 8 && col < 8; row++, col++) {
               if (futureBoard[row][col] < 0) {
                  break;
               } else if (futureBoard[row][col] == 4 || futureBoard[row][col] == 9) {
                  return true;
               } else if (futureBoard[row][col] > 0) {
                  break;
               }
            }
            for (int col = kingY + 1; col < 8; col++) {
               if (futureBoard[kingX][col] < 0) {
                  break;
               } else if (futureBoard[kingX][col] == 5 || futureBoard[kingX][col] == 9) {
                  return true;
               } else if (futureBoard[kingX][col] > 0) {
                  break;
               }
            }
            for (int col = kingY - 1; col >= 0; col--) {
               if (futureBoard[kingX][col] < 0) {
                  break;
               } else if (futureBoard[kingX][col] == 5 || futureBoard[kingX][col] == 9) {
                  return true;
               } else if (futureBoard[kingX][col] > 0) {
                  break;
               }
            }
            for (int row = kingX + 1; row < 8; row++) {
               if (futureBoard[row][kingY] < 0) {
                  break;
               } else if (futureBoard[row][kingY] == 5 || futureBoard[row][kingY] == 9) {
                  return true;
               } else if (futureBoard[row][kingY] > 0) {
                  break;
               }
            }
            for (int row = kingX - 1; row >= 0; row--) {
               if (futureBoard[row][kingY] < 0) {
                  break;
               } else if (futureBoard[row][kingY] == 5 || futureBoard[row][kingY] == 9) {
                  return true;
               } else if (futureBoard[row][kingY] > 0) {
                  break;
               }
            }
            if (kingX > 0 && kingY > 0 && futureBoard[kingX - 1][kingY - 1] == 1) {
               return true;
            }
            if (kingX > 0 && kingY < 7 && futureBoard[kingX - 1][kingY + 1] == 1) {
               return true;
            } 
            if (kingX < 6 && kingY < 7 && futureBoard[kingX + 2][kingY + 1] == 3) {
               return true;
            } 
            if (kingX < 6 && kingY > 0 && futureBoard[kingX + 2][kingY - 1] == 3) {
               return true;
            }
            if (kingX < 7 && kingY < 6 && futureBoard[kingX + 1][kingY + 2] == 3) {
               return true;
            }
            if (kingX < 7 && kingY > 1 && futureBoard[kingX + 1][kingY - 2] == 3) {
               return true;
            }
            if (kingX > 1 && kingY > 0 && futureBoard[kingX - 2][kingY - 1] == 3) {
               return true;
            }
            if (kingX > 1 && kingY < 7 && futureBoard[kingX - 2][kingY + 1] == 3) {
               return true;
            } 
            if (kingX > 0 && kingY < 6 && futureBoard[kingX - 1][kingY + 2] == 3) {
               return true;
            }
            if (kingX > 0 && kingY > 1 && futureBoard[kingX - 1][kingY - 2] == 3) {
               return true;
            }
            if (kingX < 7 && kingY > 0 && futureBoard[kingX + 1][kingY - 1] == 15) {
               return true;
            }
            if (kingX < 7 && futureBoard[kingX + 1][kingY] == 15) {
               return true;
            }
            if (kingX < 7 && kingY < 7 && futureBoard[kingX + 1][kingY + 1] == 15) {
               return true;
            }
            if (kingY < 7 && futureBoard[kingX][kingY + 1] == 15) {
               return true;
            }
            if (kingX > 0 && kingY < 7 && futureBoard[kingX - 1][kingY + 1] == 15) {
               return true;
            }
            if (kingX > 0 && futureBoard[kingX - 1][kingY] == 15) {
               return true;
            }
            if (kingX > 0 && kingY > 0 && futureBoard[kingX - 1][kingY - 1] == 15) {
               return true;
            }
            if (kingY > 0 && futureBoard[kingX][kingY - 1] == 15) {
               return true;
            } 
         } else {
            for (int row = kingX - 1, col = kingY - 1; row >= 0 && col >= 0; row--, col--) {
               if (futureBoard[row][col] > 0) {
                  break;
               } else if (futureBoard[row][col] == -4 || futureBoard[row][col] == -9) {
                  return true;
               } else if (futureBoard[row][col] < 0) {
                  break;
               }
            }
            for (int row = kingX + 1, col = kingY - 1; row < 8 && col >= 0; row++, col--) {
               if (futureBoard[row][col] > 0) {
                  break;
               } else if (futureBoard[row][col] == -4 || futureBoard[row][col] == -9) {
                  return true;
               } else if (futureBoard[row][col] < 0) {
                  break;
               }
            }
            for (int row = kingX - 1, col = kingY + 1; row >= 0 && col < 8; row--, col++) {
               if (futureBoard[row][col] > 0) {
                  break;
               } else if (futureBoard[row][col] == -4 || futureBoard[row][col] == -9) {
                  return true;
               } else if (futureBoard[row][col] < 0) {
                  break;
               }
            }
            for (int row = kingX + 1, col = kingY + 1; row < 8 && col < 8; row++, col++) {
               if (futureBoard[row][col] > 0) {
                  break;
               } else if (futureBoard[row][col] == -4 || futureBoard[row][col] == -9) {
                  return true;
               } else if (futureBoard[row][col] < 0) {
                  break;
               }
            }
            for (int col = kingY + 1; col < 8; col++) {
               if (futureBoard[kingX][col] > 0) {
                  break;
               } else if (futureBoard[kingX][col] == -5 || futureBoard[kingX][col] == -9) {
                  return true;
               } else if (futureBoard[kingX][col] < 0) {
                  break;
               }
            }
            for (int col = kingY - 1; col >= 0; col--) {
               if (futureBoard[kingX][col] > 0) {
                  break;
               } else if (futureBoard[kingX][col] == -5 || futureBoard[kingX][col] == -9) {
                  return true;
               } else if (futureBoard[kingX][col] < 0) {
                  break;
               }
            }
            for (int row = kingX + 1; row < 8; row++) {
               if (futureBoard[row][kingY] > 0) {
                  break;
               } else if (futureBoard[row][kingY] == -5 || futureBoard[row][kingY] == -9) {
                  return true;
               } else if (futureBoard[row][kingY] < 0) {
                  break;
               }
            }
            for (int row = kingX - 1; row >= 0; row--) {
               if (futureBoard[row][kingY] > 0) {
                  break;
               } else if (futureBoard[row][kingY] == -5 || futureBoard[row][kingY] == -9) {
                  return true;
               } else if (futureBoard[row][kingY] < 0) {
                  break;
               }
            }
            if (kingX > 0 && kingY > 0 && futureBoard[kingX - 1][kingY - 1] == -1) {
               return true;
            }
            if (kingX > 0 && kingY < 7 && futureBoard[kingX - 1][kingY + 1] == -1) {
               return true;
            } 
            if (kingX < 6 && kingY < 7 && futureBoard[kingX + 2][kingY + 1] == -3) {
               return true;
            } 
            if (kingX < 6 && kingY > 0 && futureBoard[kingX + 2][kingY - 1] == -3) {
               return true;
            }
            if (kingX < 7 && kingY < 6 && futureBoard[kingX + 1][kingY + 2] == -3) {
               return true;
            }
            if (kingX < 7 && kingY > 1 && futureBoard[kingX + 1][kingY - 2] == -3) {
               return true;
            }
            if (kingX > 1 && kingY > 0 && futureBoard[kingX - 2][kingY - 1] == -3) {
               return true;
            }
            if (kingX > 1 && kingY < 7 && futureBoard[kingX - 2][kingY + 1] == -3) {
               return true;
            } 
            if (kingX > 0 && kingY < 6 && futureBoard[kingX - 1][kingY + 2] == -3) {
               return true;
            }
            if (kingX > 0 && kingY > 1 && futureBoard[kingX - 1][kingY - 2] == -3) {
               return true;
            }
            if (kingX < 7 && kingY > 0 && futureBoard[kingX + 1][kingY - 1] == -15) {
               return true;
            }
            if (kingX < 7 && futureBoard[kingX + 1][kingY] == -15) {
               return true;
            }
            if (kingX < 7 && kingY < 7 && futureBoard[kingX + 1][kingY + 1] == -15) {
               return true;
            }
            if (kingY < 7 && futureBoard[kingX][kingY + 1] == -15) {
               return true;
            }
            if (kingX > 0 && kingY < 7 && futureBoard[kingX - 1][kingY + 1] == -15) {
               return true;
            }
            if (kingX > 0 && futureBoard[kingX - 1][kingY] == -15) {
               return true;
            }
            if (kingX > 0 && kingY > 0 && futureBoard[kingX - 1][kingY - 1] == -15) {
               return true;
            }
            if (kingY > 0 && futureBoard[kingX][kingY - 1] == -15) {
               return true;
            } 
         }
         return false;
      }
   }
   
   public static int isCheckmate(Position position, boolean isPlayer) {
      int targetValue = -1;
      int kingX = -1;
      int kingY = -1;
      int[][] board = position.getBoard();
      if (isPlayer) {
         if (Chess.playerIsWhite) {
            targetValue = 15;
         } else {
            targetValue = -15;
         }
      } else {
         if (Chess.playerIsWhite) {
            targetValue = -15;
         } else {
            targetValue = 15;
         }
      }
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
            if (board[row][col] == targetValue) {
               kingX = row;
               kingY = col;
            }
         }
      }
      if (isPlayer) {
         findPlayerMoves(board, Chess.playerIsWhite, position.getCastlingData(), position.getEPassX(), position.getEPassY(), position.getIsEPassWhite());
         if (playerMoveList.size() == 0) {
            if (futureCheck(board, kingX, kingY, kingX, kingY, false)) {
               return 2;
            } else {
               return 1;
            }
         } else {
            return 0;
         }
      } else {
         findLegalMoves(board, Chess.playerIsWhite, position.getCastlingData(), position.getEPassX(), position.getEPassY(), position.getIsEPassWhite());
         if (moveList.size() == 0) {
            if (futureCheck(board, kingX, kingY, kingX, kingY, true)) {
               return 2;
            } else {
               return 1;
            }
         } else {
            return 0;
         }
      }
   }
}