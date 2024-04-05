//Imports
import java.lang.Math;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.embed.swing.JFXPanel;

//Main Class
//---------------
//Other Classes Required:
//chessEngine
//chessGUI
//Position
public class Chess implements Runnable {
   
   //Chesster the chess bot! Made entirely with Java
   //No API or external calls are present in this program
   //-----Citations-----
   //Primary Theme: https://www.hiclipart.com/free-transparent-background-png-clipart-qnbrg#google_vignette
   //Chesster Logo: https://www.google.com/url?sa=i&url=https%3A%2F%2Fcommons.wikimedia.org%2Fwiki%2FFile%3AGreen_Pawn_Xogos_da_Meiga_chess_icons_family.svg&psig=AOvVaw2FhYd0k9H0auLKau6vqQyr&ust=1710872627358000&source=images&cd=vfe&opi=89978449&ved=0CBMQjRxqFwoTCJCBjLG3_oQDFQAAAAAdAAAAABAE

   //SETUP VARIABLES
   //---------------------
   //Designates who plays white
   public static boolean playerIsWhite = false;
   public static boolean firstRound = true;
   //playerState as 0: Randomly select white
   //playerState as 1: Bot plays white
   //playerState as 2: Player plays white
   public static int playerState = 2;
   public static long botMoveStartTime = -1;
   public static long botMoveEndTime = -1;
   public static final JFXPanel sound = new JFXPanel();
   public static Thread t = Thread.currentThread();
   
   //DEBUGGING VARIABLES
   //---------------------
   public static int numNodes = 0;
   public static int printChildIndex = 0;
   public static int iteration = 0;
   public static int nodesPruned = 0;
   public static boolean timeToMove = true;
   public static int numBotMoves = 0;
   public static int numPlayerMoves = 0;
   public static int numMoves = 0;
   
   //WORKING VARIABLES
   //---------------------
   //Designates the current active turn of the game
   public static boolean isPlayerTurn = false;
   public static boolean gameEnded = false;
   public static Thread timer = new Thread(new Timer());
   public static int playerTime = -1;
   public static int botTime = -1;
   //1 = Bullet Game (1 Min per side)
   //2 = Standard Game (10 Min per side)
   //3 = Tournamnet Game (20 Min per side)
   public static int gameType = 2;
   
   //DVOP (Dynamic Value Of a Piece) will be automatically update to reflect the ideal sacrifice/keep ratio for the bot
   public static int DVOP = 1;
   
   //Sector designation for King Saftey (Information is organized so that the first number is the start index and the second is the end index, SECTORS MUST BE SQUARES)
   public static int[] Sector1 = {0, 3, 0, 3};
   public static int[] Sector2 = {0, 3, 4, 7};
   public static int[] Sector3 = {4, 7, 0, 3};
   public static int[] Sector4 = {4, 7, 4, 7};
   public static int[][] Sectors = {Sector1, Sector2, Sector3, Sector4};
   
   //Score accumulator for selection of best move
   public static ArrayList<Integer> Scores = new ArrayList<Integer>();
   
   //Arena bonuses for general weighting (Starts at Row: 0, Column: 0 and move down the board first by column then row)
   public static int[][] knightAreaBonus = {{-2, -2, -2, -2, -2, -2, -2, -2}, {-1, 0, 1, 1, 1, 1, 0, -1}, {-1, 2, 3, 3, 3, 3, 2, -1}, {-1, 3, 5, 10, 10, 5, 3, -1}, {-1, 3, 5, 10, 10, 5, 3, -1}, {-1, 2, 3, 3, 3, 3, 2, -1}, {-1, 0, 1, 1, 1, 1, 0, -1}, {-2, -2, -2, -2, -2, -2, -2, -2}};
   public static int[][] bishopAreaBonus = {{-2, -2, -2, -2, -2, -2, -2, -2}, {-1, 1, 1, 1, 1, 1, 1, -1}, {-1, 5, 3, 3, 3, 3, 5, -1}, {-1, 3, 10, 10, 10, 10, 3, -1}, {-1, 3, 10, 10, 10, 10, 3, -1}, {-1, 5, 3, 3, 3, 3, 3, 5, -1}, {-1, 1, 1, 1, 1, 1, 1, -1}, {-2, -2, -2, -2, -2, -2, -2, -2}};
   public static int[][] rookAreaBonus = {{-3, -2, 0, 3, 3, 0, -2, -3}, {-2, 1, 1, 1, 1, 1, 1, -2}, {3, 2, 2, 2, 2, 2, 2, 3}, {3, 4, 5, 5, 5, 5, 4, 3}, {3, 4, 5, 5, 5, 5, 4, 3}, {3, 2, 2, 2, 2, 2, 2, 2, 3}, {-2, 1, 1, 1, 1, 1, 1, -2}, {-3, -2, 0, 3, 3, 0, -2, -3}};
   public static int[][] queenAreaBonus = {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 1, 1, 1, 1, 1, 0}, {-1, 2, 2, 2, 2, 2, 2, -1}, {-3, 4, 5, 10, 10, 5, 4, -3}, {-3, 4, 5, 10, 10, 5, 4, -3}, {-1, 2, 2, 2, 2, 2, 2, 2, -1}, {0, 1, 1, 1, 1, 1, 1, 0}, {0, 0, 0, 0, 0, 0, 0, 0}};
   public static int[][] kingAreaBonus = {{10, 5, 3, 0, 0, 3, 5, 10}, {0, 0, 0, 0, 0, 0, 0, 0}, {-2, -2, -2, -2, -2, -2, -2, -2}, {-5, -5, -5, -5, -5, -5, -5, -5}, {-5, -5, -5, -5, -5, -5, -5, -5}, {-2, -2, -2, -2, -2, -2, -2, -2, -2}, {0, 0, 0, 0, 0, 0, 0, 0}, {10, 5, 3, 0, 0, 3, 5, 10}};
   public static int[][] botPawnAreaBonus = {{0, 0, 0, 0, 0, 0, 0, 0}, {2, 0, 2, 0, 2, 0, 2, 0}, {0, 2, 0, 2, 0, 2, 0, 2}, {2, 2, 2, 2, 2, 2, 2, 2}, {2, 2, 2, 2, 2, 2, 2, 2}, {3, 3, 3, 3, 3, 3, 3, 3, 3}, {5, 5, 5, 5, 5, 5, 5, 5}, {15, 15, 15, 15, 15, 15, 15, 15}};
   public static int[][] playerPawnAreaBonus = {{15, 15, 15, 15, 15, 15, 15, 15}, {5, 5, 5, 5, 5, 5, 5, 5}, {3, 3, 3, 3, 3, 3, 3, 3}, {2, 2, 2, 2, 2, 2, 2, 2}, {2, 2, 2, 2, 2, 2, 2, 2}, {0, 2, 0, 2, 0, 2, 0, 2}, {2, 0, 2, 0, 2, 0, 2, 0, 2}, {0, 0, 0, 0, 0, 0, 0, 0}};
   
   //BOT CONTROL VARIABLES
   //---------------------
   //Depth (Exponentially increases nodes searched): 
   public static int depth = 3;
   //Piece Value (How much a piece matters when compared to other features):
   public static int pieceValue = 40;
   //Threat Value (How much a hostile piece near the king will matter to the bot):
   public static int threatValue = 20;
   //Threat Value (How much a friendly, protecting piece near the king will matter to the bot):
   public static int protectValue = 20;
   //Aggression (Increases how often the bot will try to capture over defend):
   public static int aggression = 1;
   //Defense (Increases how often the bot will try to defend its pieces):
   public static int defense = 1;


   public static void main(String[] args) {
      load();
   }
   
   
   @Override
   public void run() {
      botMove();
   }
   
   public static void load() {
      chessGUI.frame = new JFrame();
      chessGUI.loading = new loadingScreen();
      chessGUI.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      chessGUI.frame.add(chessGUI.loading);
      chessGUI.frame.show();
      Zobrist.getData();
      transpositionTable.load();
   }
   
   public static void init() {
      chessGUI.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
      chessGUI.frame.revalidate();
      chessGUI.frame.remove(chessGUI.menu);
      if (gameType == 1) {
         playerTime = 60;
         botTime = 60;
      } else if (gameType == 2) {
         playerTime = 600;
         botTime = 600;
      } else if (gameType == 3) {
         playerTime = 1800;
         botTime = 1800;
      }
      chessGUI.playerTimeLabel.setText("" + Chess.playerTime / 60 + ":0" + Chess.playerTime % 60);
      chessGUI.botTimeLabel.setText("" + Chess.botTime / 60 + ":0" + Chess.botTime % 60);
      if (!firstRound) {
         for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
               chessEngine.chessBoard[row][col] = 0;
            }
         }
         gameEnded = false;
         chessGUI.selectedPieceX = -1;
         chessGUI.selectedPieceY = -1;
         chessGUI.formerSquareX = -1;
         chessGUI.formerSquareY = -1;
         chessGUI.currentSquareX = -1;
         chessGUI.currentSquareY = -1;
         chessGUI.redSquareX = -1;
         chessGUI.redSquareY = -1;
         chessGUI.previouslySelectedRow = -1;
         chessGUI.previouslySelectedCol = -1;
         chessGUI.backToMenu.setBounds(0, 0, 0, 0);
         chessGUI.playAgainMenu.setBounds(0, 0, 0, 0);
         chessGUI.backToMenu.remove(chessGUI.backToMenuButton);
         chessGUI.frame.remove(chessGUI.backToMenu);
         chessGUI.playAgainMenu.remove(chessGUI.playAgain);
         chessGUI.frame.remove(chessGUI.playAgainMenu);
         chessEngine.ePassX = -1;
         chessEngine.ePassY = -1;
         chessEngine.isEPassWhite = false;
         chessEngine.castlingData = new int[]{1, 1, 1, 1, 1, 1};
         chessGUI.botPawnCaptureCount = 0;
         chessGUI.botKnightCaptureCount = 0;
         chessGUI.botBishopCaptureCount = 0;
         chessGUI.botRookCaptureCount = 0;
         chessGUI.botQueenCaptureCount = 0;
         chessGUI.playerPawnCaptureCount = 0;
         chessGUI.playerKnightCaptureCount = 0;
         chessGUI.playerBishopCaptureCount = 0;
         chessGUI.playerRookCaptureCount = 0;
         chessGUI.playerQueenCaptureCount = 0;
         chessGUI.botPawnCaptureLabel.setText("X0");
         chessGUI.botKnightCaptureLabel.setText("X0");
         chessGUI.botBishopCaptureLabel.setText("X0");
         chessGUI.botRookCaptureLabel.setText("X0");
         chessGUI.botQueenCaptureLabel.setText("X0");
         chessGUI.playerPawnCaptureLabel.setText("X0");
         chessGUI.playerKnightCaptureLabel.setText("X0");
         chessGUI.playerBishopCaptureLabel.setText("X0");
         chessGUI.playerRookCaptureLabel.setText("X0");
         chessGUI.playerQueenCaptureLabel.setText("X0");
         numPlayerMoves = 0;
         numBotMoves = 0;
         chessGUI.botMoveLabel.setText("0");
         chessGUI.playerMoveLabel.setText("0");
      }
      if (playerState == 1) {
         playerIsWhite = false;
         isPlayerTurn = false;
      } else if (playerState == 2) {
         playerIsWhite = true;
         isPlayerTurn = true;
      } else {
         boolean state = (((int)(Math.random() * 2) + 1) == 1);
         playerIsWhite = state;
         isPlayerTurn = state;
      }
      chessEngine.setupBoard(playerIsWhite);
      if (firstRound) {
         chessGUI.showUI();
      }
      chessGUI.updateBoard(chessEngine.chessBoard);
      chessGUI.updateBoard(chessEngine.chessBoard);
      chessEngine.findPlayerMoves(chessEngine.chessBoard, playerIsWhite, chessEngine.castlingData, chessEngine.ePassX, chessEngine.ePassY, chessEngine.isEPassWhite);
      chessGUI.frame.revalidate();
      timer = new Thread(new Timer());
      timer.start();
      firstRound = false;
      if (!isPlayerTurn) {
         botMove();
      }
   }
   
   public static void acceptBotMove(int moveIndex) {
      chessEngine.findLegalMoves(chessEngine.chessBoard, playerIsWhite, chessEngine.castlingData, chessEngine.ePassX, chessEngine.ePassY, chessEngine.isEPassWhite);
      if (Math.abs(chessEngine.chessBoard[chessEngine.moveList.get(moveIndex)[0]][chessEngine.moveList.get(moveIndex)[1]]) == 1) {
         if (chessEngine.moveList.get(moveIndex)[0] + 2 == chessEngine.moveList.get(moveIndex)[2]) {
            chessEngine.ePassX = chessEngine.moveList.get(moveIndex)[2];
            chessEngine.ePassY = chessEngine.moveList.get(moveIndex)[3];
            chessEngine.isEPassWhite = !(Chess.playerIsWhite);
         } else if (chessEngine.moveList.get(moveIndex)[0] == chessEngine.ePassX) {
            if (chessEngine.moveList.get(moveIndex)[1] + 1 == chessEngine.ePassY && (chessEngine.moveList.get(moveIndex)[0] + 1 == chessEngine.moveList.get(moveIndex)[2] && chessEngine.moveList.get(moveIndex)[1] + 1 == chessEngine.moveList.get(moveIndex)[3])) {
               chessEngine.chessBoard[chessEngine.moveList.get(moveIndex)[0]][chessEngine.moveList.get(moveIndex)[1] + 1] = 0;
            } else if (chessEngine.moveList.get(moveIndex)[1] - 1 == chessEngine.ePassY && (chessEngine.moveList.get(moveIndex)[0] + 1 == chessEngine.moveList.get(moveIndex)[2] && chessEngine.moveList.get(moveIndex)[1] - 1 == chessEngine.moveList.get(moveIndex)[3])) {
               chessEngine.chessBoard[chessEngine.moveList.get(moveIndex)[0]][chessEngine.moveList.get(moveIndex)[1] - 1] = 0;
            }
         } else {
            chessEngine.ePassX = -1;
            chessEngine.ePassY = -1;
         }
      } else {
         chessEngine.ePassX = -1;
         chessEngine.ePassY = -1;
      }
     if (Math.abs(chessEngine.chessBoard[chessEngine.moveList.get(moveIndex)[2]][chessEngine.moveList.get(moveIndex)[3]]) == 5) {
         if (chessEngine.moveList.get(moveIndex)[2] == 0 && chessEngine.moveList.get(moveIndex)[3] == 0) {
            chessEngine.castlingData[0] = 0;
         } else if (chessEngine.moveList.get(moveIndex)[2] == 0 && chessEngine.moveList.get(moveIndex)[3] == 7) {
            chessEngine.castlingData[2] = 0;
         } else if (chessEngine.moveList.get(moveIndex)[2] == 7 && chessEngine.moveList.get(moveIndex)[3] == 0) {
            chessEngine.castlingData[3] = 0;
         } else if (chessEngine.moveList.get(moveIndex)[2] == 7 && chessEngine.moveList.get(moveIndex)[3] == 7) {
            chessEngine.castlingData[5] = 0;
         }
      }
      if (Math.abs(chessEngine.chessBoard[chessEngine.moveList.get(moveIndex)[0]][chessEngine.moveList.get(moveIndex)[1]]) == 5) {
         if (!Chess.playerIsWhite) {
            if (chessEngine.moveList.get(moveIndex)[1] == 0) {
               chessEngine.castlingData[0] = 0;
            } else if (chessEngine.moveList.get(moveIndex)[1] == 7) {
               chessEngine.castlingData[2] = 0;
            } 
         } else {
            if (chessEngine.moveList.get(moveIndex)[1] == 0) {
               chessEngine.castlingData[3] = 0;
            } else if (chessEngine.moveList.get(moveIndex)[1] == 7) {
               chessEngine.castlingData[5] = 0;
            }
         }
      }
      if (Math.abs(chessEngine.chessBoard[chessEngine.moveList.get(moveIndex)[0]][chessEngine.moveList.get(moveIndex)[1]]) == 15) {
         if (!Chess.playerIsWhite) {
            chessEngine.castlingData[1] = 0;
         } else {
            chessEngine.castlingData[4] = 0;
         }
         if (chessEngine.moveList.get(moveIndex)[1] - 2 == chessEngine.moveList.get(moveIndex)[3]) {
            chessEngine.chessBoard[7][0] = 0;
            if (!Chess.playerIsWhite) {
               chessEngine.chessBoard[chessEngine.moveList.get(moveIndex)[2]][chessEngine.moveList.get(moveIndex)[3] + 1] = 5;
            } else {
               chessEngine.chessBoard[chessEngine.moveList.get(moveIndex)[2]][chessEngine.moveList.get(moveIndex)[3] + 1] = -5;
            }
         } else if (chessEngine.moveList.get(moveIndex)[1] + 2 == chessEngine.moveList.get(moveIndex)[3]) {
            chessEngine.chessBoard[7][7] = 0;
            if (!Chess.playerIsWhite) {
               chessEngine.chessBoard[chessEngine.moveList.get(moveIndex)[2]][chessEngine.moveList.get(moveIndex)[3] - 1] = 5;
            } else {
               chessEngine.chessBoard[chessEngine.moveList.get(moveIndex)[2]][chessEngine.moveList.get(moveIndex)[3] - 1] = -5;
            }
         }
      }
      boolean didPromote = false;
      if (Math.abs(chessEngine.chessBoard[chessEngine.moveList.get(moveIndex)[0]][chessEngine.moveList.get(moveIndex)[1]]) == 1) {
         if (chessEngine.moveList.get(moveIndex)[2] == 7) {
            if (Chess.playerIsWhite) {
               chessEngine.chessBoard[chessEngine.moveList.get(moveIndex)[2]][chessEngine.moveList.get(moveIndex)[3]] = -9;
            } else {
               chessEngine.chessBoard[chessEngine.moveList.get(moveIndex)[2]][chessEngine.moveList.get(moveIndex)[3]] = 9;
            }
            didPromote = true;
         }
      }
      int value = Math.abs(chessEngine.chessBoard[chessEngine.moveList.get(moveIndex)[2]][chessEngine.moveList.get(moveIndex)[3]]);
      boolean didCapture = false;
      if (value == 1) {
         chessGUI.botPawnCaptureCount++;
         chessGUI.botPawnCaptureLabel.setText("X" + chessGUI.botPawnCaptureCount);
         didCapture = true;
      } else if (value == 3) {
         chessGUI.botKnightCaptureCount++;
         chessGUI.botKnightCaptureLabel.setText("X" + chessGUI.botKnightCaptureCount);
         didCapture = true;
      } else if (value == 4) {
         chessGUI.botBishopCaptureCount++;
         chessGUI.botBishopCaptureLabel.setText("X" + chessGUI.botBishopCaptureCount);
         didCapture = true;
      } else if (value == 5) {
         chessGUI.botRookCaptureCount++;
         chessGUI.botRookCaptureLabel.setText("X" + chessGUI.botRookCaptureCount);
         didCapture = true;
      } else if (value == 9) {
         chessGUI.botQueenCaptureCount++;
         chessGUI.botQueenCaptureLabel.setText("X" + chessGUI.botQueenCaptureCount);
         didCapture = true;
      }
      if (!didPromote) {
         chessEngine.chessBoard[chessEngine.moveList.get(moveIndex)[2]][chessEngine.moveList.get(moveIndex)[3]] = chessEngine.chessBoard[chessEngine.moveList.get(moveIndex)[0]][chessEngine.moveList.get(moveIndex)[1]];
      }
      botMoved();
      chessEngine.chessBoard[chessEngine.moveList.get(moveIndex)[0]][chessEngine.moveList.get(moveIndex)[1]] = 0;
      calculateDVOP(chessEngine.chessBoard);
      chessGUI.formerSquareX = chessEngine.moveList.get(moveIndex)[0];
      chessGUI.formerSquareY = chessEngine.moveList.get(moveIndex)[1];
      chessGUI.currentSquareX = chessEngine.moveList.get(moveIndex)[2];
      chessGUI.currentSquareY = chessEngine.moveList.get(moveIndex)[3];
      int kingX = -1;
      int kingY = -1;
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
            if (!Chess.playerIsWhite) {
               if (chessEngine.chessBoard[row][col] == -15) {
                  kingX = row;
                  kingY = col;
               }
            } else {
               if (chessEngine.chessBoard[row][col] == 15) {
                  kingX = row;
                  kingY = col;
               }
            }
         }
      }
      if (chessEngine.futureCheck(chessEngine.chessBoard, kingX, kingY, kingX, kingY, false)) {
         chessGUI.redSquareX = kingX;
         chessGUI.redSquareY = kingY;
      } else {
         chessGUI.redSquareX = -1;
         chessGUI.redSquareY = -1;
      }
      Timer.moveMade();
      chessGUI.updateBoard(chessEngine.chessBoard);
      chessEngine.moveList.clear();
      chessEngine.findPlayerMoves(chessEngine.chessBoard, Chess.playerIsWhite, chessEngine.castlingData, chessEngine.ePassX, chessEngine.ePassY, chessEngine.isEPassWhite);
      Position newBoard = new Position(chessEngine.chessBoard, chessEngine.castlingData, chessEngine.ePassX, chessEngine.ePassY, chessEngine.isEPassWhite);
      if (chessEngine.isCheckmate(newBoard, true) == 2) {
         chessGUI.handleGameEnd(1);
      } else if (chessEngine.isCheckmate(newBoard, true) == 1) {
         chessGUI.handleGameEnd(3);
      } else {
         isPlayerTurn = true;
      }
      if (!didCapture) {
         try {
            File f = new File("move.mp3");
            Media move = new Media(f.toURI().toString());
            MediaPlayer mp = new MediaPlayer(move);
            mp.play();
         } catch (Exception e) {
            e.printStackTrace();
         }
      } else {
         try {
            File f = new File("capture.mp3");
            Media move = new Media(f.toURI().toString());
            MediaPlayer mp = new MediaPlayer(move);
            mp.play();
         } catch (Exception exc) {
            exc.printStackTrace();
         }
      }
   }
   
   public static void botMove() {
      chessEngine.findLegalMoves(chessEngine.chessBoard, playerIsWhite, chessEngine.castlingData, chessEngine.ePassX, chessEngine.ePassY, chessEngine.isEPassWhite);
      Position newBoard = new Position(chessEngine.chessBoard, chessEngine.castlingData, chessEngine.ePassX, chessEngine.ePassY, chessEngine.isEPassWhite);
      if (chessEngine.isCheckmate(newBoard, false) == 2) {
         chessGUI.handleGameEnd(2);
      } else if (chessEngine.isCheckmate(newBoard, false) == 1) {
         chessGUI.handleGameEnd(3);
      } else {
         Position currentBoard = new Position(chessEngine.chessBoard, chessEngine.castlingData, chessEngine.ePassX, chessEngine.ePassY, chessEngine.isEPassWhite);
         Scores.clear();
         botMoveStartTime = System.currentTimeMillis();
         minimax(currentBoard, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true, chessEngine.moveList);
         int highestScore = Integer.MIN_VALUE;
         int highestIndex = 0;
         for (int index = 0; index < Scores.size(); index++) {
            if (Scores.get(index) > highestScore) {
               highestScore = Scores.get(index);
               highestIndex = index;
            }
         }
         botMoveEndTime = System.currentTimeMillis();
         if (botMoveEndTime - botMoveStartTime < 400) {
            try {
               Thread.sleep(400 - (botMoveEndTime - botMoveStartTime));
            } catch (InterruptedException ie) {
               ie.printStackTrace();
            }
         }
         Chess.acceptBotMove(highestIndex);
      }
   }
   
   public static int minimax(Position board, int depth, int alpha, int beta, boolean isPlayer, ArrayList<int[]> choices) {
      int[][] position = board.getBoard();
      chessEngine.findLegalMoves(position, playerIsWhite, board.getCastlingData(), board.getEPassX(), board.getEPassY(), board.getIsEPassWhite());
      chessEngine.findPlayerMoves(position, playerIsWhite, board.getCastlingData(), board.getEPassX(), board.getEPassY(), board.getIsEPassWhite());
      if (depth == 0 || (chessEngine.moveList.size() == 0 && isPlayer) || (chessEngine.playerMoveList.size() == 0 && !isPlayer)) {
         int score;
         score = evaluation(board);
         if (depth == Chess.depth) {
            Scores.add(score);
         }
         return score;
      }
      ArrayList<Position> children = findChildren(board, isPlayer);
      numNodes += children.size();
      if (isPlayer) {
         int maxEval = Integer.MIN_VALUE;
         for (int index = 0; index < children.size(); index++) {
            Position child = children.get(index);
            if (depth == Chess.depth) {
               if (chessEngine.isCheckmate(child, true) == 2) {
                  Scores.add(Integer.MAX_VALUE);
                  return Integer.MAX_VALUE;
               }
            }
            int eval;
            eval = minimax(child, depth - 1, alpha, beta, false, chessEngine.moveList);   
            if (eval > maxEval) {
               maxEval = eval;
            }
            if (eval > alpha) {
               alpha = eval;
            }
            if (beta <= alpha) {
               break;
            }
            if (depth == Chess.depth) {
               Scores.add(eval);
            }
         }
         return maxEval;
      } else {
         int minEval = Integer.MAX_VALUE;
         for (int index = 0; index < children.size(); index++) {
            Position child = children.get(index);
            int eval;
            int[][] childBoard = children.get(index).getBoard();
            eval = minimax(child, depth - 1, alpha, beta, true, chessEngine.playerMoveList);
            if (eval < minEval) {
               minEval = eval;
            }
            if (eval < beta) {
               beta = eval;
            }
            if (beta <= alpha) {
               break;
            }
         }
         return minEval;       
      }
   }
   
   public static void findNodesPruned(Position position, boolean isPlayer, int depth) {
      if (depth == 0) {
         numNodes++;
         nodesPruned++;
      } else {
         ArrayList<Position> children = findChildren(position, isPlayer);
         for (int i = 0; i < children.size(); i++) {
            numNodes++;
            nodesPruned++;
            findNodesPruned(children.get(i), !isPlayer, depth - 1);
         }
      }
   }
   
   public static ArrayList<Position> findChildren(Position position, boolean isPlayer) {
      ArrayList<Position> children = new ArrayList<Position>();
      int[][] board = position.getBoard();
      if (isPlayer) {
         chessEngine.findLegalMoves(board, playerIsWhite, position.getCastlingData(), position.getEPassX(), position.getEPassY(), position.getIsEPassWhite());
         for (int moveIndex = 0; moveIndex < chessEngine.moveList.size(); moveIndex++) {
            int[][] childBoard = new int[8][8];
            int[] childCastlingData = new int[6];
            for (int index = 0; index < 6; index++) {
               childCastlingData[index] = position.getCastlingData()[index];
            }
            int childEPassX = -1;
            int childEPassY = -1;
            boolean childIsEPassWhite = false;
            for (int row = 0; row < 8; row++) {
               for (int col = 0; col < 8; col++) {
                  childBoard[row][col] = board[row][col];
               }
            }
            childBoard[chessEngine.moveList.get(moveIndex)[2]][chessEngine.moveList.get(moveIndex)[3]] = board[chessEngine.moveList.get(moveIndex)[0]][chessEngine.moveList.get(moveIndex)[1]];
            childBoard[chessEngine.moveList.get(moveIndex)[0]][chessEngine.moveList.get(moveIndex)[1]] = 0;
            if (Math.abs(board[chessEngine.moveList.get(moveIndex)[0]][chessEngine.moveList.get(moveIndex)[1]]) == 1) {
               if (chessEngine.moveList.get(moveIndex)[2] == 7) {
                  if (Chess.playerIsWhite) {
                     childBoard[chessEngine.moveList.get(moveIndex)[2]][chessEngine.moveList.get(moveIndex)[3]] = -9;
                  } else {
                     childBoard[chessEngine.moveList.get(moveIndex)[2]][chessEngine.moveList.get(moveIndex)[3]] = 9;
                  }
               }
               if (chessEngine.moveList.get(moveIndex)[0] + 2 == chessEngine.moveList.get(moveIndex)[3]) {
                  childEPassX = chessEngine.moveList.get(moveIndex)[2];
                  childEPassY = chessEngine.moveList.get(moveIndex)[3];
                  childIsEPassWhite = !playerIsWhite;
               }
               if (chessEngine.moveList.get(moveIndex)[0] == chessEngine.ePassX) {
                  if (chessEngine.moveList.get(moveIndex)[1] + 1 == chessEngine.ePassY && (chessEngine.moveList.get(moveIndex)[0] + 1 == chessEngine.moveList.get(moveIndex)[2] && chessEngine.moveList.get(moveIndex)[1] + 1 == chessEngine.moveList.get(moveIndex)[3])) {
                     board[chessEngine.moveList.get(moveIndex)[0]][chessEngine.moveList.get(moveIndex)[1] + 1] = 0;
                  } else if (chessEngine.moveList.get(moveIndex)[1] - 1 == chessEngine.ePassY && (chessEngine.moveList.get(moveIndex)[0] + 1 == chessEngine.moveList.get(moveIndex)[2] && chessEngine.moveList.get(moveIndex)[1] - 1 == chessEngine.moveList.get(moveIndex)[3])) {
                     board[chessEngine.moveList.get(moveIndex)[0]][chessEngine.moveList.get(moveIndex)[1] - 1] = 0;
                  }
               }
            }
            if (Math.abs(board[chessEngine.moveList.get(moveIndex)[0]][chessEngine.moveList.get(moveIndex)[1]]) == 5) {
               if (playerIsWhite) {
                  if (chessEngine.moveList.get(moveIndex)[0] == 0) {
                     if (chessEngine.moveList.get(moveIndex)[1] == 0) {
                        childCastlingData[3] = 0;
                     } else if (chessEngine.moveList.get(moveIndex)[1] == 7) {
                        childCastlingData[5] = 0;
                     }
                  }
               } else {
                  if (chessEngine.moveList.get(moveIndex)[0] == 0) {
                     if (chessEngine.moveList.get(moveIndex)[1] == 0) {
                        childCastlingData[0] = 0;
                     } else if (chessEngine.moveList.get(moveIndex)[1] == 7) {
                        childCastlingData[2] = 0;
                     }
                  }
               }
            }
            if (Math.abs(board[chessEngine.moveList.get(moveIndex)[0]][chessEngine.moveList.get(moveIndex)[1]]) == 15) {
               if (playerIsWhite) {
                  childCastlingData[4] = 0;
               } else {
                  childCastlingData[1] = 0;
               }
               if (chessEngine.moveList.get(moveIndex)[1] - 2 == chessEngine.moveList.get(moveIndex)[3]) {
                  board[7][0] = 0;
                  if (!Chess.playerIsWhite) {
                     board[chessEngine.moveList.get(moveIndex)[2]][chessEngine.moveList.get(moveIndex)[3] + 1] = 5;
                  } else {
                     board[chessEngine.moveList.get(moveIndex)[2]][chessEngine.moveList.get(moveIndex)[3] + 1] = -5;
                  }
               } else if (chessEngine.moveList.get(moveIndex)[1] + 2 == chessEngine.moveList.get(moveIndex)[3]) {
                  board[7][7] = 0;
                  if (!Chess.playerIsWhite) {
                     board[chessEngine.moveList.get(moveIndex)[2]][chessEngine.moveList.get(moveIndex)[3] - 1] = 5;
                  } else {
                     board[chessEngine.moveList.get(moveIndex)[2]][chessEngine.moveList.get(moveIndex)[3] - 1] = -5;
                  }
               }
            }
            Position childNode = new Position(childBoard, childCastlingData, childEPassX, childEPassY, childIsEPassWhite);
            children.add(childNode);
         }
      } else {
         chessEngine.findPlayerMoves(position.getBoard(), playerIsWhite, position.getCastlingData(), position.getEPassX(), position.getEPassY(), position.getIsEPassWhite());
         for (int moveIndex = 0; moveIndex < chessEngine.playerMoveList.size(); moveIndex++) {
            int[][] childBoard = new int[8][8];
            int[] childCastlingData = new int[6];
            for (int index = 0; index < 6; index++) {
               childCastlingData[index] = position.getCastlingData()[index];
            }
            int childEPassX = -1;
            int childEPassY = -1;
            boolean childIsEPassWhite = false;
            for (int row = 0; row < 8; row++) {
               for (int col = 0; col < 8; col++) {
                  childBoard[row][col] = board[row][col];
               }
            }
            childBoard[chessEngine.playerMoveList.get(moveIndex)[2]][chessEngine.playerMoveList.get(moveIndex)[3]] = board[chessEngine.playerMoveList.get(moveIndex)[0]][chessEngine.playerMoveList.get(moveIndex)[1]];
            childBoard[chessEngine.playerMoveList.get(moveIndex)[0]][chessEngine.playerMoveList.get(moveIndex)[1]] = 0;
            if (Math.abs(board[chessEngine.playerMoveList.get(moveIndex)[0]][chessEngine.playerMoveList.get(moveIndex)[1]]) == 1) {
               if (chessEngine.playerMoveList.get(moveIndex)[2] == 0) {
                  if (Chess.playerIsWhite) {
                     childBoard[chessEngine.moveList.get(moveIndex)[2]][chessEngine.moveList.get(moveIndex)[3]] = 9;
                  } else {
                     childBoard[chessEngine.moveList.get(moveIndex)[2]][chessEngine.moveList.get(moveIndex)[3]] = -9;
                  }
               }
               if (chessEngine.playerMoveList.get(moveIndex)[0] - 2 == chessEngine.playerMoveList.get(moveIndex)[3]) {
                  childEPassX = chessEngine.playerMoveList.get(moveIndex)[2];
                  childEPassY = chessEngine.playerMoveList.get(moveIndex)[3];
                  childIsEPassWhite = playerIsWhite;
               }
               if (chessEngine.playerMoveList.get(moveIndex)[0] == chessEngine.ePassX) {
                  if (chessEngine.playerMoveList.get(moveIndex)[1] + 1 == chessEngine.ePassY && (chessEngine.playerMoveList.get(moveIndex)[0] + 1 == chessEngine.playerMoveList.get(moveIndex)[2] && chessEngine.playerMoveList.get(moveIndex)[1] + 1 == chessEngine.playerMoveList.get(moveIndex)[3])) {
                     board[chessEngine.playerMoveList.get(moveIndex)[0]][chessEngine.playerMoveList.get(moveIndex)[1] + 1] = 0;
                  } else if (chessEngine.playerMoveList.get(moveIndex)[1] - 1 == chessEngine.ePassY && (chessEngine.playerMoveList.get(moveIndex)[0] + 1 == chessEngine.playerMoveList.get(moveIndex)[2] && chessEngine.playerMoveList.get(moveIndex)[1] - 1 == chessEngine.playerMoveList.get(moveIndex)[3])) {
                     board[chessEngine.playerMoveList.get(moveIndex)[0]][chessEngine.playerMoveList.get(moveIndex)[1] - 1] = 0;
                  }
               }
            }
            if (Math.abs(board[chessEngine.playerMoveList.get(moveIndex)[0]][chessEngine.playerMoveList.get(moveIndex)[1]]) == 5) {
               if (playerIsWhite) {
                  if (chessEngine.playerMoveList.get(moveIndex)[0] == 0) {
                     if (chessEngine.playerMoveList.get(moveIndex)[1] == 0) {
                        childCastlingData[0] = 0;
                     } else if (chessEngine.playerMoveList.get(moveIndex)[1] == 7) {
                        childCastlingData[2] = 0;
                     }
                  }
               } else {
                  if (chessEngine.playerMoveList.get(moveIndex)[0] == 0) {
                     if (chessEngine.playerMoveList.get(moveIndex)[1] == 0) {
                        childCastlingData[3] = 0;
                     } else if (chessEngine.playerMoveList.get(moveIndex)[1] == 7) {
                        childCastlingData[5] = 0;
                     }
                  }
               }
            }
            if (Math.abs(board[chessEngine.playerMoveList.get(moveIndex)[0]][chessEngine.playerMoveList.get(moveIndex)[1]]) == 15) {
               if (playerIsWhite) {
                  childCastlingData[1] = 0;
               } else {
                  childCastlingData[4] = 0;
               }
               if (chessEngine.playerMoveList.get(moveIndex)[1] - 2 == chessEngine.playerMoveList.get(moveIndex)[3]) {
                  board[7][0] = 0;
                  if (Chess.playerIsWhite) {
                     board[chessEngine.playerMoveList.get(moveIndex)[2]][chessEngine.playerMoveList.get(moveIndex)[3] + 1] = 5;
                  } else {
                     board[chessEngine.playerMoveList.get(moveIndex)[2]][chessEngine.playerMoveList.get(moveIndex)[3] + 1] = -5;
                  }
               } else if (chessEngine.playerMoveList.get(moveIndex)[1] + 2 == chessEngine.playerMoveList.get(moveIndex)[3]) {
                  board[7][7] = 0;
                  if (Chess.playerIsWhite) {
                     board[chessEngine.playerMoveList.get(moveIndex)[2]][chessEngine.playerMoveList.get(moveIndex)[3] - 1] = 5;
                  } else {
                     board[chessEngine.playerMoveList.get(moveIndex)[2]][chessEngine.playerMoveList.get(moveIndex)[3] - 1] = -5;
                  }
               }
            }
            Position childNode = new Position(childBoard, childCastlingData, childEPassX, childEPassY, childIsEPassWhite);
            children.add(childNode);
         }
      }
      return children;
   }
   
   public static int evaluation(Position board) {
      int[][] position = board.getBoard();
      int playerCheckmateState = chessEngine.isCheckmate(board, true);
      int botCheckmateState = chessEngine.isCheckmate(board, false);
      if (playerCheckmateState == 1 || botCheckmateState == 1) {
         return 0;
      } else if (playerCheckmateState == 2) {
         return Integer.MAX_VALUE;
      } else if (botCheckmateState == 2) {
         return Integer.MIN_VALUE;
      }
      int score = 0;
      if (playerIsWhite) {
         for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
               if (position[row][col] < 0) {
                  if (position[row][col] == -1) {
                     score += botPawnAreaBonus[row][col];
                  } else if (position[row][col] == -3) {
                     score += knightAreaBonus[row][col];
                  } else if (position[row][col] == -4) {
                     score += bishopAreaBonus[row][col];
                  } else if (position[row][col] == -5) {
                     score += rookAreaBonus[row][col];
                  } else if (position[row][col] == -9) {
                     score += queenAreaBonus[row][col];
                  } else if (position[row][col] == -15) {
                     score += kingAreaBonus[row][col];
                  }
                  score += position[row][col] * -1 * defense * pieceValue;
               } else if (position[row][col] > 0) {
                  if (position[row][col] == 1) {
                     score -= playerPawnAreaBonus[row][col];
                  } else if (position[row][col] == 3) {
                     score -= knightAreaBonus[row][col];
                  } else if (position[row][col] == 4) {
                     score -= bishopAreaBonus[row][col];
                  } else if (position[row][col] == 5) {
                     score -= rookAreaBonus[row][col];
                  } else if (position[row][col] == 9) {
                     score -= queenAreaBonus[row][col];
                  } else if (position[row][col] == 15) {
                     score -= kingAreaBonus[row][col];
                  }
                  score -= position[row][col] * aggression * pieceValue;
               }
            }
         }
      } else {
         for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
               if (position[row][col] > 0) {
                  if (position[row][col] == 1) {
                     score += botPawnAreaBonus[row][col];
                  } else if (position[row][col] == 3) {
                     score += knightAreaBonus[row][col];
                  } else if (position[row][col] == 4) {
                     score += bishopAreaBonus[row][col];
                  } else if (position[row][col] == 5) {
                     score += rookAreaBonus[row][col];
                  } else if (position[row][col] == 9) {
                     score += queenAreaBonus[row][col];
                  } else if (position[row][col] == 15) {
                     score += kingAreaBonus[row][col];
                  }
                  score += position[row][col] * defense * pieceValue;
               } else if (position[row][col] < 0) {
                  if (position[row][col] == -1) {
                     score -= playerPawnAreaBonus[row][col];
                  } else if (position[row][col] == -3) {
                     score -= knightAreaBonus[row][col];
                  } else if (position[row][col] == -4) {
                     score -= bishopAreaBonus[row][col];
                  } else if (position[row][col] == -5) {
                     score -= rookAreaBonus[row][col];
                  } else if (position[row][col] == -9) {
                     score -= queenAreaBonus[row][col];
                  } else if (position[row][col] == -15) {
                     score -= kingAreaBonus[row][col];
                  }
                  score -= position[row][col] * -1 * aggression * pieceValue;
               }
            }
         }
      }
      score += calculateKingSafety(position);
      score += DVOP * pieceValue;
      return score;
   }
   
   public static void calculateDVOP(int[][] board) {
      int pieceAccumulation = 0;
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
            int pieceValue = board[row][col];
            if (playerIsWhite) {
               if (pieceValue < 0) {
                  pieceAccumulation += pieceValue * -1;
               } else {
                  pieceAccumulation -= pieceValue;
               }
            } else {
               if (pieceValue > 0) {
                  pieceAccumulation += pieceValue; 
               } else {
                  pieceAccumulation -= pieceValue * -1;
               }
            }
         }
      }
      DVOP = pieceAccumulation;
   }
   
   public static int calculateKingSafety(int[][] board) {
      int score = 0;
      int kingX = -1;
      int kingY = -1;
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
            if (playerIsWhite) {
               if (board[row][col] == -15) {
                  kingX = row;
                  kingY = col;
               }
            } else {
               if (board[row][col] == 15) {
                  kingX = row;
                  kingY = col;
               }
            }
         }
      }
      int presentSector = -1;
      for (int index = 0; index < Sectors.length; index++) {
         int[] currentSector = Sectors[index];
         if (kingX >= currentSector[0] && kingX <= currentSector[1] && kingY >= currentSector[2] && kingY <= currentSector[3]) {
            presentSector = index;
            break;
         }
      }
      int threatScore = 0;
      int numThreats = 0;
      int defenseScore = 0;
      int numDefenders = 0;
      for (int row = Sectors[presentSector][0]; row < Sectors[presentSector][1]; row++) {
         for (int col = Sectors[presentSector][2]; col < Sectors[presentSector][3]; col++) {
            int piece = chessEngine.chessBoard[row][col];
            if (playerIsWhite) {
               if (piece > 1) {
                  threatScore += piece * threatValue;
                  numThreats++;
               } else if (piece < 1) {
                  defenseScore += piece * -1 * threatValue;
                  numDefenders++;
               }
            } else {
               if (piece < 1) {
                  threatScore += piece * -1 * threatValue;
                  numThreats++;
               } else if (piece > 1) {
                  defenseScore += piece * threatValue;
                  numDefenders++;
               } 
            }
         }
      }
      if (numDefenders > numThreats) {
         defenseScore = defenseScore / 3;
      }
      if (numDefenders + 1 <= numThreats) {
         threatScore = threatScore * 2;
      }
      score = defenseScore = threatScore;  
      return score;
   }
   
   public static void playerMoved() {
      numPlayerMoves++;
      chessGUI.playerMoveLabel.setText("" + numPlayerMoves);
   }
   
   public static void botMoved() {
      numBotMoves++;
      chessGUI.botMoveLabel.setText("" + numBotMoves);
   }
   
}