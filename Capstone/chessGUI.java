import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Color;
import java.lang.Math;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.FontMetrics;
import java.io.IOException;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.awt.Container;
import java.awt.Font;
import javax.swing.SwingConstants;

public class chessGUI {
   
   public static loadingScreen loading;
   public static JFrame frame;
   public static JPanel gameMode = new JPanel();
   public static JButton bullet = new JButton();
   public static JButton standard = new JButton();
   public static JButton tournament = new JButton();
   public static JLabel gameModeLabel = new JLabel("Select Game Mode");
   public static JPanel playerTimer = new JPanel();
   public static JPanel botTimer = new JPanel();
   public static JPanel panel;
   public static JButton[][] buttons = new JButton[8][8];
   public static JLabel botTimeLabel = new JLabel("", SwingConstants.CENTER);
   public static JLabel playerTimeLabel = new JLabel("", SwingConstants.CENTER);
   public static JLabel botMoveLabel = new JLabel("1", SwingConstants.CENTER);
   public static JLabel playerMoveLabel = new JLabel("1", SwingConstants.CENTER);
   
   public static Menu menu;
   public static boolean goToMenu = true;
   
   public static Image dot;
   public static Image blackPawn;
   public static Image blackPawnCapture;
   public static Image blackKing;
   public static Image blackKnight;
   public static Image blackKnightCapture;
   public static Image blackBishop;
   public static Image blackBishopCapture;
   public static Image blackRook;
   public static Image blackRookCapture;
   public static Image blackQueen;
   public static Image blackQueenCapture;
   public static Image whitePawn;
   public static Image whitePawnCapture;
   public static Image whiteKing;
   public static Image whiteKnight;
   public static Image whiteKnightCapture;
   public static Image whiteBishop;
   public static Image whiteBishopCapture;
   public static Image whiteRook;
   public static Image whiteRookCapture;
   public static Image whiteQueen;
   public static Image whiteQueenCapture;
   public static Image standardImage;
   public static Image tournamentImage;
   public static Image bulletImage;
   public static Image blackKingMate;
   public static Image blackKingStalemate;
   public static Image whiteKingMate;
   public static Image whiteKingStalemate;
   public static JPanel devPanel;
   public static JLabel currentPage;
   public static JButton nextPage;
   public static JButton previousPage;
   
   public static Color lightSquare = new Color(184, 139, 74);
   public static Color darkSquare = new Color(227, 193, 111);
   public static Color highlight = new Color(130, 197, 255);
   public static Color movement = new Color(184, 204, 163);
   
   public static boolean pieceSelected = false;
   public static ArrayList<int[]> pieceMoves = new ArrayList<int[]>();
   public static int selectedPieceValue = -1;
   public static int selectedPieceX = -1;
   public static int selectedPieceY = -1;
   
   public static int previouslySelectedRow = -1;
   public static int previouslySelectedCol = -1;
   
   public static JButton backToMenuButton = new JButton();
   public static JPanel backToMenu = new JPanel(new GridLayout(1, 1));
   public static JButton playAgain = new JButton();
   public static JPanel playAgainMenu = new JPanel(new GridLayout(1, 1));
   
   public static int botPawnCaptureCount = 0;
   public static JLabel botPawnCaptureLabel = new JLabel("X0");
   public static int botKnightCaptureCount = 0;
   public static JLabel botKnightCaptureLabel = new JLabel("X0");
   public static int botBishopCaptureCount = 0;
   public static JLabel botBishopCaptureLabel = new JLabel("X0");
   public static int botRookCaptureCount = 0;
   public static JLabel botRookCaptureLabel = new JLabel("X0");
   public static int botQueenCaptureCount = 0;
   public static JLabel botQueenCaptureLabel = new JLabel("X0");
   
   public static int playerPawnCaptureCount = 0;
   public static JLabel playerPawnCaptureLabel = new JLabel("X0");
   public static int playerKnightCaptureCount = 0;
   public static JLabel playerKnightCaptureLabel = new JLabel("X0");
   public static int playerBishopCaptureCount = 0;
   public static JLabel playerBishopCaptureLabel = new JLabel("X0");
   public static int playerRookCaptureCount = 0;
   public static JLabel playerRookCaptureLabel = new JLabel("X0");
   public static int playerQueenCaptureCount = 0;
   public static JLabel playerQueenCaptureLabel = new JLabel("X0");
   
   public static int redSquareX = -1;
   public static int redSquareY = -1;
   
   public static int formerSquareX = -1;
   public static int formerSquareY = -1;
   public static int currentSquareX = -1;
   public static int currentSquareY = -1;
   
   public static int loadingProgress = 0;
   public static int loadingItems = 2;
   
   public static boolean[][] dotGrid = new boolean[8][8];
   
   public static void showUI() {
      
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
            dotGrid[row][col] = false;
         }
      }
      
      frame.setLayout(null);
      frame.getContentPane().setBackground(new Color(162, 235, 192));
      panel = new JPanel();
      panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
      panel.setLayout(new GridLayout(8, 8));
      
      try {
         dot = ImageIO.read(new File("images/Dot.png"));
         dot = dot.getScaledInstance(65, 50,  java.awt.Image.SCALE_SMOOTH );
         blackPawn = ImageIO.read(new File("images/blackPawn.png"));
         blackPawn = blackPawn.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH);
         blackPawnCapture = ImageIO.read(new File("images/blackPawnCapture.png"));
         blackPawnCapture = blackPawnCapture.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         blackKnight = ImageIO.read(new File("images/blackKnight.png"));
         blackKnight = blackKnight.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         blackKnightCapture = ImageIO.read(new File("images/blackKnightCapture.png"));
         blackKnightCapture = blackKnightCapture.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         blackBishop = ImageIO.read(new File("images/blackBishop.png"));
         blackBishop = blackBishop.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         blackBishopCapture = ImageIO.read(new File("images/blackBishopCapture.png"));
         blackBishopCapture = blackBishopCapture.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         blackRook = ImageIO.read(new File("images/blackRook.png"));
         blackRook = blackRook.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         blackRookCapture = ImageIO.read(new File("images/blackRookCapture.png"));
         blackRookCapture = blackRookCapture.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         blackQueen = ImageIO.read(new File("images/blackQueen.png"));
         blackQueen = blackQueen.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         blackQueenCapture = ImageIO.read(new File("images/blackQueenCapture.png"));
         blackQueenCapture = blackQueenCapture.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         blackKing = ImageIO.read(new File("images/blackKing.png"));
         blackKing = blackKing.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         whitePawn = ImageIO.read(new File("images/whitePawn.png"));
         whitePawn = whitePawn.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH);
         whitePawnCapture = ImageIO.read(new File("images/whitePawnCapture.png"));
         whitePawnCapture = whitePawnCapture.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         whiteKnight = ImageIO.read(new File("images/whiteKnight.png"));
         whiteKnight = whiteKnight.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         whiteKnightCapture = ImageIO.read(new File("images/whiteKnightCapture.png"));
         whiteKnightCapture = whiteKnightCapture.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         whiteBishop = ImageIO.read(new File("images/whiteBishop.png"));
         whiteBishop = whiteBishop.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         whiteBishopCapture = ImageIO.read(new File("images/whiteBishopCapture.png"));
         whiteBishopCapture = whiteBishopCapture.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         whiteRook = ImageIO.read(new File("images/whiteRook.png"));
         whiteRook = whiteRook.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         whiteRookCapture = ImageIO.read(new File("images/whiteRookCapture.png"));
         whiteRookCapture = whiteRookCapture.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         whiteQueen = ImageIO.read(new File("images/whiteQueen.png"));
         whiteQueen = whiteQueen.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         whiteQueenCapture = ImageIO.read(new File("images/whiteQueenCapture.png"));
         whiteQueenCapture = whiteQueenCapture.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         whiteKing = ImageIO.read(new File("images/whiteKing.png"));
         whiteKing = whiteKing.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         whiteKingMate = ImageIO.read(new File("images/whiteKingMate.png"));
         whiteKingMate = whiteKingMate.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         whiteKingStalemate = ImageIO.read(new File("images/whiteKingStalemate.png"));
         whiteKingStalemate = whiteKingStalemate.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         blackKingMate = ImageIO.read(new File("images/blackKingMate.png"));
         blackKingMate = blackKingMate.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
         blackKingStalemate = ImageIO.read(new File("images/blackKingStalemate.png"));
         blackKingStalemate = blackKingStalemate.getScaledInstance(80, 65,  java.awt.Image.SCALE_SMOOTH );
      } catch (IOException ioe) {
         ioe.printStackTrace();
      } 
      Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
      botTimer.setBounds((ss.width / 2) - 100 - 450, ss.height / 2 - 300, 200, 400);
      botTimer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
      botTimer.setBackground(new Color(216, 224, 227));
      botTimer.setLayout(new GridLayout(9, 1));
      JLabel botTimerLabel = new JLabel("Bot's Time", SwingConstants.CENTER);
      botTimerLabel.setFont(new Font("Helvetica", 1, 20));
      botTimeLabel.setFont(new Font("Helvetica", 1, 15));
      botTimer.add(botTimerLabel);
      botTimer.add(botTimeLabel);
      JLabel botMoveCountLabel = new JLabel("Bot Moves", SwingConstants.CENTER); 
      botMoveCountLabel.setFont(new Font("Helvetica", 1, 20));
      botMoveLabel.setFont(new Font("Helvetica", 1, 15));
      botTimer.add(botMoveCountLabel);
      botTimer.add(botMoveLabel);
      botTimeLabel.setText("" + Chess.botTime / 60 + ":0" + Chess.botTime % 60);
      if (Chess.playerIsWhite) {
         JPanel botPawnWhite = new JPanel(new GridLayout(1, 2));
         botPawnWhite.add(new JLabel(new ImageIcon(whitePawn.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         botPawnWhite.add(botPawnCaptureLabel);
         botPawnWhite.setBackground(new Color(216, 224, 227));
         botTimer.add(botPawnWhite);
         JPanel botKnightWhite = new JPanel(new GridLayout(1, 2));
         botKnightWhite.add(new JLabel(new ImageIcon(whiteKnight.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         botKnightWhite.add(botKnightCaptureLabel);
         botKnightWhite.setBackground(new Color(216, 224, 227));
         botTimer.add(botKnightWhite);
         JPanel botBishopWhite = new JPanel(new GridLayout(1, 2));
         botBishopWhite.add(new JLabel(new ImageIcon(whiteBishop.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         botBishopWhite.add(botBishopCaptureLabel);
         botBishopWhite.setBackground(new Color(216, 224, 227));
         botTimer.add(botBishopWhite);
         JPanel botRookWhite = new JPanel(new GridLayout(1, 2));
         botRookWhite.add(new JLabel(new ImageIcon(whiteRook.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         botRookWhite.add(botRookCaptureLabel);
         botRookWhite.setBackground(new Color(216, 224, 227));
         botTimer.add(botRookWhite);
         JPanel botQueenWhite = new JPanel(new GridLayout(1, 2));
         botQueenWhite.add(new JLabel(new ImageIcon(whiteQueen.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         botQueenWhite.add(botQueenCaptureLabel);
         botQueenWhite.setBackground(new Color(216, 224, 227));
         botTimer.add(botQueenWhite);
      } else {
         JPanel botPawnBlack = new JPanel(new GridLayout(1, 2));
         botPawnBlack.add(new JLabel(new ImageIcon(blackPawn.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         botPawnBlack.add(botPawnCaptureLabel);
         botPawnBlack.setBackground(new Color(216, 224, 227));
         botTimer.add(botPawnBlack);
         JPanel botKnightBlack = new JPanel(new GridLayout(1, 2));
         botKnightBlack.add(new JLabel(new ImageIcon(blackKnight.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         botKnightBlack.add(botKnightCaptureLabel);
         botKnightBlack.setBackground(new Color(216, 224, 227));
         botTimer.add(botKnightBlack);
         JPanel botBishopBlack = new JPanel(new GridLayout(1, 2));
         botBishopBlack.add(new JLabel(new ImageIcon(blackBishop.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         botBishopBlack.add(botBishopCaptureLabel);
         botBishopBlack.setBackground(new Color(216, 224, 227));
         botTimer.add(botBishopBlack);
         JPanel botRookBlack = new JPanel(new GridLayout(1, 2));
         botRookBlack.add(new JLabel(new ImageIcon(blackRook.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         botRookBlack.add(botRookCaptureLabel);
         botRookBlack.setBackground(new Color(216, 224, 227));
         botTimer.add(botRookBlack);
         JPanel botQueenBlack = new JPanel(new GridLayout(1, 2));
         botQueenBlack.add(new JLabel(new ImageIcon(blackQueen.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         botQueenBlack.add(botQueenCaptureLabel);
         botQueenBlack.setBackground(new Color(216, 224, 227));
         botTimer.add(botQueenBlack);
      }
      
      playerTimer.setBounds((ss.width / 2) - 100 + 450, ss.height / 2 - 300, 200, 400);
      playerTimer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
      playerTimer.setBackground(new Color(216, 224, 227));
      playerTimer.setLayout(new GridLayout(9, 1));
      JLabel playerTimerLabel = new JLabel("Players's Time", SwingConstants.CENTER);
      playerTimerLabel.setFont(new Font("Helvetica", 1, 20));
      playerTimeLabel.setFont(new Font("Helvetica", 1, 15));
      playerTimer.add(playerTimerLabel);
      playerTimer.add(playerTimeLabel);
      JLabel playerMoveCountLabel = new JLabel("Player Moves", SwingConstants.CENTER);
      playerMoveCountLabel.setFont(new Font("Helvetica", 1, 20));
      playerMoveLabel.setFont(new Font("Helvetica", 1, 15));
      playerTimer.add(playerMoveCountLabel);
      playerTimer.add(playerMoveLabel);
      playerTimeLabel.setText("" + Chess.playerTime / 60 + ":0" + Chess.playerTime % 60);
      if (!Chess.playerIsWhite) {
         JPanel playerPawnWhite = new JPanel(new GridLayout(1, 2));
         playerPawnWhite.add(new JLabel(new ImageIcon(whitePawn.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         playerPawnWhite.add(playerPawnCaptureLabel);
         playerPawnWhite.setBackground(new Color(216, 224, 227));
         playerTimer.add(playerPawnWhite);
         JPanel playerKnightWhite = new JPanel(new GridLayout(1, 2));
         playerKnightWhite.add(new JLabel(new ImageIcon(whiteKnight.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         playerKnightWhite.add(playerKnightCaptureLabel);
         playerKnightWhite.setBackground(new Color(216, 224, 227));
         playerTimer.add(playerKnightWhite);
         JPanel playerBishopWhite = new JPanel(new GridLayout(1, 2));
         playerBishopWhite.add(new JLabel(new ImageIcon(whiteBishop.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         playerBishopWhite.add(playerBishopCaptureLabel);
         playerBishopWhite.setBackground(new Color(216, 224, 227));
         playerTimer.add(playerBishopWhite);
         JPanel playerRookWhite = new JPanel(new GridLayout(1, 2));
         playerRookWhite.add(new JLabel(new ImageIcon(whiteRook.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         playerRookWhite.add(playerRookCaptureLabel);
         playerRookWhite.setBackground(new Color(216, 224, 227));
         playerTimer.add(playerRookWhite);
         JPanel playerQueenWhite = new JPanel(new GridLayout(1, 2));
         playerQueenWhite.add(new JLabel(new ImageIcon(whiteQueen.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         playerQueenWhite.add(playerQueenCaptureLabel);
         playerQueenWhite.setBackground(new Color(216, 224, 227));
         playerTimer.add(playerQueenWhite);
      } else {
         JPanel playerPawnBlack = new JPanel(new GridLayout(1, 2));
         playerPawnBlack.add(new JLabel(new ImageIcon(blackPawn.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         playerPawnBlack.add(playerPawnCaptureLabel);
         playerPawnBlack.setBackground(new Color(216, 224, 227));
         playerTimer.add(playerPawnBlack);
         JPanel playerKnightBlack = new JPanel(new GridLayout(1, 2));
         playerKnightBlack.add(new JLabel(new ImageIcon(blackKnight.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         playerKnightBlack.add(playerKnightCaptureLabel);
         playerKnightBlack.setBackground(new Color(216, 224, 227));
         playerTimer.add(playerKnightBlack);
         JPanel playerBishopBlack = new JPanel(new GridLayout(1, 2));
         playerBishopBlack.add(new JLabel(new ImageIcon(blackBishop.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         playerBishopBlack.add(playerBishopCaptureLabel);
         playerBishopBlack.setBackground(new Color(216, 224, 227));
         playerTimer.add(playerBishopBlack);
         JPanel playerRookBlack = new JPanel(new GridLayout(1, 2));
         playerRookBlack.add(new JLabel(new ImageIcon(blackRook.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         playerRookBlack.add(playerRookCaptureLabel);
         playerRookBlack.setBackground(new Color(216, 224, 227));
         playerTimer.add(playerRookBlack);
         JPanel playerQueenBlack = new JPanel(new GridLayout(1, 2));
         playerQueenBlack.add(new JLabel(new ImageIcon(blackQueen.getScaledInstance(60, 45,  java.awt.Image.SCALE_SMOOTH))));
         playerQueenBlack.add(playerQueenCaptureLabel);
         playerQueenBlack.setBackground(new Color(216, 224, 227));
         playerTimer.add(playerQueenBlack);
      }
      frame.add(botTimer);
      frame.add(playerTimer);
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
            buttons[row][col] = new JButton();
            final int tempRow = row;
            final int tempCol = col;
            buttons[row][col].setBorder(null);
            buttons[row][col].addActionListener(new AbstractAction() {
               @Override
               public void actionPerformed(ActionEvent e) {
                  for (int row = 0; row < 8; row++) {
                     for (int col = 0; col < 8; col++) {
                        dotGrid[row][col] = false;
                     }
                  }
                  if (Chess.isPlayerTurn) {
                     updateBoard(chessEngine.chessBoard);
                     if ((chessEngine.chessBoard[tempRow][tempCol] < 0 && !Chess.playerIsWhite) || (chessEngine.chessBoard[tempRow][tempCol] > 0 && Chess.playerIsWhite) || pieceSelected) {
                        if (!pieceSelected) {
                           pieceSelected = true;
                           buttons[tempRow][tempCol].setBackground(highlight);
                           if (!(tempRow == previouslySelectedRow && tempCol == previouslySelectedCol)) {
                              previouslySelectedRow = tempRow;
                              previouslySelectedCol = tempCol;
                              for (int entry = 0; entry < chessEngine.playerMoveList.size(); entry++) {
                                 if (chessEngine.playerMoveList.get(entry)[0] == tempRow && chessEngine.playerMoveList.get(entry)[1] == tempCol) {                               
                                    dotGrid[chessEngine.playerMoveList.get(entry)[2]][chessEngine.playerMoveList.get(entry)[3]] = true;
                                    pieceMoves.add(new int[]{chessEngine.playerMoveList.get(entry)[2], chessEngine.playerMoveList.get(entry)[3]});
                                 }
                              }
                              updateBoard(chessEngine.chessBoard);
                           } else {
                              previouslySelectedRow = -1;
                              previouslySelectedCol = -1;
                              for (int row = 0; row < 8; row++) {
                                 for (int col = 0; col < 8; col++) {
                                    dotGrid[row][col] = false;
                                 }
                              }
                           }
                        } else {
                           if ((chessEngine.chessBoard[tempRow][tempCol] < 0 && !Chess.playerIsWhite) || (chessEngine.chessBoard[tempRow][tempCol] > 0 && Chess.playerIsWhite)) {
                              updateBoard(chessEngine.chessBoard);
                              previouslySelectedRow = tempRow;
                              previouslySelectedCol = tempCol;
                              pieceMoves.clear();
                              for (int entry = 0; entry < chessEngine.playerMoveList.size(); entry++) {
                                 if (chessEngine.playerMoveList.get(entry)[0] == tempRow && chessEngine.playerMoveList.get(entry)[1] == tempCol) {
                                    buttons[chessEngine.playerMoveList.get(entry)[2]][chessEngine.playerMoveList.get(entry)[3]].setIcon(new ImageIcon(dot));
                                    dotGrid[chessEngine.playerMoveList.get(entry)[2]][chessEngine.playerMoveList.get(entry)[3]] = true;
                                    int pieceValue = chessEngine.chessBoard[chessEngine.playerMoveList.get(entry)[2]][chessEngine.playerMoveList.get(entry)[3]];
                                    JButton square = buttons[chessEngine.playerMoveList.get(entry)[2]][chessEngine.playerMoveList.get(entry)[3]]; 
                                    if (pieceValue > 0) {
                                       if (pieceValue == 1) {
                                          if (dotGrid[chessEngine.playerMoveList.get(entry)[2]][chessEngine.playerMoveList.get(entry)[3]]) {
                                             square.setIcon(new ImageIcon(whitePawnCapture));
                                          } else {
                                             square.setIcon(new ImageIcon(whitePawn));
                                          }
                                       } else if (pieceValue == 3) {
                                          if (dotGrid[chessEngine.playerMoveList.get(entry)[2]][chessEngine.playerMoveList.get(entry)[3]]) {
                                             square.setIcon(new ImageIcon(whiteKnightCapture));
                                          } else {
                                             square.setIcon(new ImageIcon(whiteKnight));
                                          }
                                       } else if (pieceValue == 4) {
                                          if (dotGrid[chessEngine.playerMoveList.get(entry)[2]][chessEngine.playerMoveList.get(entry)[3]]) {
                                             square.setIcon(new ImageIcon(whiteBishopCapture));
                                          } else {
                                             square.setIcon(new ImageIcon(whiteBishop));
                                          }
                                       } else if (pieceValue == 5) {
                                          if (dotGrid[chessEngine.playerMoveList.get(entry)[2]][chessEngine.playerMoveList.get(entry)[3]]) {
                                             square.setIcon(new ImageIcon(whiteRookCapture));
                                          } else {
                                             square.setIcon(new ImageIcon(whiteRook));
                                          }
                                       } else if (pieceValue == 9) {
                                          if (dotGrid[chessEngine.playerMoveList.get(entry)[2]][chessEngine.playerMoveList.get(entry)[3]]) {
                                             square.setIcon(new ImageIcon(whiteQueenCapture));
                                          } else {
                                             square.setIcon(new ImageIcon(whiteQueen));
                                          }
                                       } else if (pieceValue == 15) {
                                          square.setIcon(new ImageIcon(whiteKing));
                                       }
                                    } else {
                                       if (pieceValue == -1) {
                                          if (dotGrid[chessEngine.playerMoveList.get(entry)[2]][chessEngine.playerMoveList.get(entry)[3]]) {
                                             square.setIcon(new ImageIcon(blackPawnCapture));
                                          } else {
                                             square.setIcon(new ImageIcon(blackPawn));
                                          }
                                       } else if (pieceValue == -3) {
                                          if (dotGrid[chessEngine.playerMoveList.get(entry)[2]][chessEngine.playerMoveList.get(entry)[3]]) {
                                             square.setIcon(new ImageIcon(blackKnightCapture));
                                          } else {
                                             square.setIcon(new ImageIcon(blackKnight));
                                          }
                                       } else if (pieceValue == -4) {
                                          if (dotGrid[chessEngine.playerMoveList.get(entry)[2]][chessEngine.playerMoveList.get(entry)[3]]) {
                                             square.setIcon(new ImageIcon(blackBishopCapture));
                                          } else {
                                             square.setIcon(new ImageIcon(blackBishop));
                                          }
                                       } else if (pieceValue == -5) {
                                          if (dotGrid[chessEngine.playerMoveList.get(entry)[2]][chessEngine.playerMoveList.get(entry)[3]]) {
                                             square.setIcon(new ImageIcon(blackRookCapture));
                                          } else {
                                             square.setIcon(new ImageIcon(blackRook));
                                          }
                                       } else if (pieceValue == -9) {
                                          if (dotGrid[chessEngine.playerMoveList.get(entry)[2]][chessEngine.playerMoveList.get(entry)[3]]) {
                                             square.setIcon(new ImageIcon(blackQueenCapture));
                                          } else {
                                             square.setIcon(new ImageIcon(blackQueen));
                                          }
                                       } else if (pieceValue == -15) {
                                          square.setIcon(new ImageIcon(blackKing));
                                       }
                                    }
                                    pieceMoves.add(new int[]{chessEngine.playerMoveList.get(entry)[2], chessEngine.playerMoveList.get(entry)[3]});
                                 }
                              }
                           } else {
                              boolean found = false;
                              for (int i = 0; i < pieceMoves.size(); i++) {
                                 if (pieceMoves.get(i)[0] == tempRow && pieceMoves.get(i)[1] == tempCol) {
                                    found = true;
                                 }
                              }
                              if (found) {
                                 if (Math.abs(chessEngine.chessBoard[previouslySelectedRow][previouslySelectedCol]) == 1) {
                                    if (tempRow - 2 == previouslySelectedRow) {
                                       chessEngine.ePassX = tempRow;
                                       chessEngine.ePassY = tempCol;
                                       chessEngine.isEPassWhite = Chess.playerIsWhite;
                                    } else if (previouslySelectedRow == chessEngine.ePassX) {
                                       if (previouslySelectedCol + 1 == chessEngine.ePassY && (tempRow == chessEngine.ePassX - 1 && tempCol == chessEngine.ePassY)) {
                                          chessEngine.chessBoard[tempRow + 1][tempCol] = 0;
                                       } else if (previouslySelectedCol - 1 == chessEngine.ePassY && (tempRow == chessEngine.ePassX - 1 && tempCol == chessEngine.ePassY)) {
                                          chessEngine.chessBoard[tempRow + 1][tempCol] = 0;
                                       }
                                    } else {
                                       chessEngine.ePassX = -1;
                                       chessEngine.ePassY = -1;
                                    }
                                 } else {
                                    chessEngine.ePassX = -1;
                                    chessEngine.ePassY = -1;
                                 }
                                 if (Math.abs(chessEngine.chessBoard[tempRow][tempCol]) == 5) {
                                    if (tempRow == 0 && tempCol == 0) {
                                       chessEngine.castlingData[0] = 0;
                                    } else if (tempRow == 0 && tempCol == 7) {
                                       chessEngine.castlingData[2] = 0;
                                    } else if (tempRow == 7 && tempCol == 0) {
                                       chessEngine.castlingData[3] = 0;
                                    } else if (tempRow == 7 && tempCol == 7) {
                                       chessEngine.castlingData[5] = 0;
                                    }
                                 }
                                 if (Math.abs(chessEngine.chessBoard[previouslySelectedRow][previouslySelectedCol]) == 5) {
                                    if (Chess.playerIsWhite) {
                                       if (previouslySelectedCol == 0) {
                                          chessEngine.castlingData[0] = 0;
                                       } else if (previouslySelectedCol == 7) {
                                          chessEngine.castlingData[2] = 0;
                                       } 
                                    } else {
                                       if (previouslySelectedCol == 0) {
                                          chessEngine.castlingData[3] = 0;
                                       } else if (previouslySelectedCol == 7) {
                                          chessEngine.castlingData[5] = 0;
                                       }
                                    }
                                 }
                                 if (Math.abs(chessEngine.chessBoard[previouslySelectedRow][previouslySelectedCol]) == 15) {
                                    if (Chess.playerIsWhite) {
                                       chessEngine.castlingData[1] = 0;
                                    } else {
                                       chessEngine.castlingData[4] = 0;
                                    }
                                    if (previouslySelectedCol - 2 == tempCol) {
                                       chessEngine.chessBoard[7][0] = 0;
                                       if (Chess.playerIsWhite) {
                                          chessEngine.chessBoard[tempRow][tempCol + 1] = 5;
                                       } else {
                                          chessEngine.chessBoard[tempRow][tempCol + 1] = -5;
                                       }
                                    } else if (previouslySelectedCol + 2 == tempCol) {
                                       chessEngine.chessBoard[7][7] = 0;
                                       if (Chess.playerIsWhite) {
                                          chessEngine.chessBoard[tempRow][tempCol - 1] = 5;
                                       } else {
                                          chessEngine.chessBoard[tempRow][tempCol - 1] = -5;
                                       }
                                    }
                                 }
                                 boolean didPromote = false;
                                 if (Math.abs(chessEngine.chessBoard[previouslySelectedRow][previouslySelectedCol]) == 1) {
                                    if (tempRow == 0) {
                                       if (Chess.playerIsWhite) {
                                          chessEngine.chessBoard[tempRow][tempCol] = 9;
                                       } else {
                                          chessEngine.chessBoard[tempRow][tempCol] = -9;
                                       }
                                       didPromote = true;
                                    }
                                 }
                                 int value = Math.abs(chessEngine.chessBoard[tempRow][tempCol]);
                                 if (value == 1) {
                                    playerPawnCaptureCount++;
                                    playerPawnCaptureLabel.setText("X" + playerPawnCaptureCount);
                                 } else if (value == 3) {
                                    playerKnightCaptureCount++;
                                    playerKnightCaptureLabel.setText("X" + playerKnightCaptureCount);
                                 } else if (value == 4) {
                                    playerBishopCaptureCount++;
                                    playerBishopCaptureLabel.setText("X" + playerBishopCaptureCount);
                                 } else if (value == 5) {
                                    playerRookCaptureCount++;
                                    playerRookCaptureLabel.setText("X" + playerRookCaptureCount);
                                 } else if (value == 9) {
                                    playerQueenCaptureCount++;
                                    playerQueenCaptureLabel.setText("X" + playerQueenCaptureCount);
                                 }
                                 if (!didPromote) {
                                    chessEngine.chessBoard[tempRow][tempCol] = chessEngine.chessBoard[previouslySelectedRow][previouslySelectedCol];
                                 }
                                 chessEngine.chessBoard[previouslySelectedRow][previouslySelectedCol] = 0;
                                 formerSquareX = previouslySelectedRow;
                                 formerSquareY = previouslySelectedCol;
                                 currentSquareX = tempRow;
                                 currentSquareY = tempCol;
                                 for (int row = 0; row < 8; row++) {
                                    for (int col = 0; col < 8; col++) {
                                       dotGrid[row][col] = false;
                                    }
                                 }
                                 updateBoard(chessEngine.chessBoard);
                                 chessEngine.playerMoveList.clear();
                                 Chess.playerMoved();
                                 Chess.isPlayerTurn = false;
                                 int kingX = -1;
                                 int kingY = -1;
                                 for (int row = 0; row < 8; row++) {
                                    for (int col = 0; col < 8; col++) {
                                       if (Chess.playerIsWhite) {
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
                                 if (chessEngine.futureCheck(chessEngine.chessBoard, kingX, kingY, kingX, kingY, true)) {
                                    redSquareX = kingX;
                                    redSquareY = kingY;  
                                 } else {
                                    redSquareX = -1;
                                    redSquareY = -1;
                                 }
                                 Timer.moveMade();
                                 updateBoard(chessEngine.chessBoard);
                                 Thread minimax = new Thread(new Chess());
                                 minimax.start();
                              }
                              pieceSelected = false;
                              previouslySelectedRow = -1;
                              previouslySelectedCol = -1;
                              pieceMoves.clear();
                           }
                        }
                     }
                  }
               }
            });       
            if ((col + row) % 2 != 0) {
               buttons[row][col].setBackground(lightSquare);
            } else {
               buttons[row][col].setBackground(darkSquare);
            }
            panel.add(buttons[row][col]);
         }
      }
      
      panel.setBounds((ss.width / 2) - 300, (ss.height / 2) - 340, 600, 600);
      frame.add(panel);
      frame.setVisible(true);
      frame.revalidate();
   }
   
   public static void updateBoard(int[][] chessBoard) {
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
            int pieceValue = chessBoard[row][col];
            JButton square = buttons[row][col];
            if (dotGrid[row][col]) {
               square.setIcon(new ImageIcon(dot));
            } else {
               square.setIcon(null);
            }
            if ((col + row) % 2 != 0) {
               square.setBackground(lightSquare);
            } else {
               square.setBackground(darkSquare);
            }
            if (pieceValue != 0) {
               if (pieceValue > 0) {
                  square.setForeground(Color.WHITE);
               } else {
                  square.setForeground(Color.BLACK);
               }
               if (pieceValue > 0) {
                  if (pieceValue == 1) {
                     if (dotGrid[row][col]) {
                        square.setIcon(new ImageIcon(whitePawnCapture));
                     } else {
                        square.setIcon(new ImageIcon(whitePawn));
                     }
                  } else if (pieceValue == 3) {
                     if (dotGrid[row][col]) {
                        square.setIcon(new ImageIcon(whiteKnightCapture));
                     } else {
                        square.setIcon(new ImageIcon(whiteKnight));
                     }
                  } else if (pieceValue == 4) {
                     if (dotGrid[row][col]) {
                        square.setIcon(new ImageIcon(whiteBishopCapture));
                     } else {
                        square.setIcon(new ImageIcon(whiteBishop));
                     }
                  } else if (pieceValue == 5) {
                     if (dotGrid[row][col]) {
                        square.setIcon(new ImageIcon(whiteRookCapture));
                     } else {
                        square.setIcon(new ImageIcon(whiteRook));
                     }
                  } else if (pieceValue == 9) {
                     if (dotGrid[row][col]) {
                        square.setIcon(new ImageIcon(whiteQueenCapture));
                     } else {
                        square.setIcon(new ImageIcon(whiteQueen));
                     }
                  } else if (pieceValue == 15) {
                     square.setIcon(new ImageIcon(whiteKing));
                  }

               } else {
                  if (pieceValue == -1) {
                     if (dotGrid[row][col]) {
                        square.setIcon(new ImageIcon(blackPawnCapture));
                     } else {
                        square.setIcon(new ImageIcon(blackPawn));
                     }
                  } else if (pieceValue == -3) {
                     if (dotGrid[row][col]) {
                        square.setIcon(new ImageIcon(blackKnightCapture));
                     } else {
                        square.setIcon(new ImageIcon(blackKnight));
                     }
                  } else if (pieceValue == -4) {
                     if (dotGrid[row][col]) {
                        square.setIcon(new ImageIcon(blackBishopCapture));
                     } else {
                        square.setIcon(new ImageIcon(blackBishop));
                     }
                  } else if (pieceValue == -5) {
                     if (dotGrid[row][col]) {
                        square.setIcon(new ImageIcon(blackRookCapture));
                     } else {
                        square.setIcon(new ImageIcon(blackRook));
                     }
                  } else if (pieceValue == -9) {
                     if (dotGrid[row][col]) {
                        square.setIcon(new ImageIcon(blackQueenCapture));
                     } else {
                        square.setIcon(new ImageIcon(blackQueen));
                     }
                  } else if (pieceValue == -15) {
                     square.setIcon(new ImageIcon(blackKing));
                  }
               }    
            }
            if ((row == formerSquareX && col == formerSquareY) || (row == currentSquareX && col == currentSquareY)) {
               square.setBackground(movement);
            }
         }
      }
      if (redSquareX != -1 && redSquareY != -1) {
         buttons[redSquareX][redSquareY].setBackground(new Color(219, 81, 81));
      }
      frame.revalidate();
   }
   
   public static void getGameMode() {
      frame.remove(menu);
      chessGUI.gameMode.setLayout(null);
      chessGUI.gameMode.setBackground(new Color(162, 235, 192));
      Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
      chessGUI.bullet.setBounds(((ss.width / 6) * 1) - 150, ss.height / 2 - 150, 300, 300);
      chessGUI.standard.setBounds(((ss.width / 6) * 3) - 150, ss.height / 2 - 150, 300, 300);
      chessGUI.tournament.setBounds(((ss.width / 6) * 5) - 150, ss.height / 2 - 150, 300, 300);
      gameModeLabel.setFont(new Font("Verdana", Font.PLAIN, 46));
      gameModeLabel.setBounds(ss.width / 2 - 200, 100, 800, 75);
      try {
         chessGUI.bulletImage = ImageIO.read(new File("images/Bullet.png"));
         chessGUI.bulletImage = chessGUI.bulletImage.getScaledInstance(300, 300,  java.awt.Image.SCALE_SMOOTH);
         chessGUI.standardImage = ImageIO.read(new File("images/Standard.png"));
         chessGUI.standardImage = chessGUI.standardImage.getScaledInstance(300, 300,  java.awt.Image.SCALE_SMOOTH);
         chessGUI.tournamentImage = ImageIO.read(new File("images/Tournament.png"));
         chessGUI.tournamentImage = chessGUI.tournamentImage.getScaledInstance(300, 300,  java.awt.Image.SCALE_SMOOTH);
      } catch (IOException ie) {
         ie.printStackTrace();
      }
      chessGUI.bullet.setIcon(new ImageIcon(chessGUI.bulletImage));
      chessGUI.standard.setIcon(new ImageIcon(chessGUI.standardImage));
      chessGUI.tournament.setIcon(new ImageIcon(chessGUI.tournamentImage));
      bullet.addActionListener(new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent e) {
            gameMode.remove(bullet);
            gameMode.remove(standard);
            gameMode.remove(tournament);
            gameMode.setBackground(Color.WHITE);
            frame.setComponentZOrder(gameMode, 1);
            gameMode.revalidate();
            Chess.gameType = 1;
            Chess.playerTime = 60;
            Chess.depth = 3;
            Chess.botTime = 60;
            Chess.init();
         }
      });
      standard.addActionListener(new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent e) {
            gameMode.remove(bullet);
            gameMode.remove(standard);
            gameMode.remove(tournament);
            gameMode.setBackground(Color.WHITE);
            frame.setComponentZOrder(gameMode, 1);
            gameMode.revalidate();
            Chess.gameType = 2;
            Chess.playerTime = 600;
            Chess.botTime = 600;
            Chess.init();
         }
      });
      tournament.addActionListener(new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent e) {
            gameMode.remove(bullet);
            gameMode.remove(standard);
            gameMode.remove(tournament);
            gameMode.setBackground(Color.WHITE);
            frame.setComponentZOrder(gameMode, 1);
            gameMode.revalidate();
            Chess.gameType = 3;
            Chess.playerTime = 1800;
            Chess.botTime = 1800;
            Chess.init();
         }
      });
      chessGUI.gameMode.add(chessGUI.bullet);
      chessGUI.gameMode.add(chessGUI.standard);
      chessGUI.gameMode.add(chessGUI.tournament);
      gameMode.add(gameModeLabel);
      chessGUI.frame.add(chessGUI.gameMode);
      chessGUI.frame.revalidate();
   }
   
   public static void handleLoad() {
      if (goToMenu) {
         frame.remove(chessGUI.loading);
         menu = new Menu();
         menu.setBackground(new Color(162, 235, 192));
         frame.add(menu);
         frame.revalidate();
      }
   }
   
   public static void returnToMenu() {
      System.exit(0);
   }
   
   public static void handleGameEnd(int gameState) {
      Chess.gameEnded = true;
      Chess.timer.stop();
      if (gameState == 1) {
         int target;
         if (Chess.playerIsWhite) {
            target = 15;
         } else {
            target = -15;
         }
         for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
               if (chessEngine.chessBoard[row][col] == target) {
                  if (Chess.playerIsWhite) {
                     buttons[row][col].setIcon(new ImageIcon(whiteKingMate));
                  } else {
                     buttons[row][col].setIcon(new ImageIcon(blackKingMate));
                  }
               }
            }
         } 
      } else if (gameState == 2) {
         int target;
         if (!Chess.playerIsWhite) {
            target = 15;
         } else {
            target = -15;
         }
         for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
               if (chessEngine.chessBoard[row][col] == target) {
                  if (!Chess.playerIsWhite) {
                     buttons[row][col].setIcon(new ImageIcon(whiteKingMate));
                  } else {
                     buttons[row][col].setIcon(new ImageIcon(blackKingMate));
                  }
               }
            }
         } 
      } else if (gameState == 3) {
         for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
               if (chessEngine.chessBoard[row][col] == 15) {
                  buttons[row][col].setIcon(new ImageIcon(whiteKingStalemate));
               } else if (chessEngine.chessBoard[row][col] == -15) {
                  buttons[row][col].setIcon(new ImageIcon(blackKingStalemate));
               }
            }
         }
      }
      Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
      backToMenuButton = new JButton("Exit");
      backToMenuButton.setBackground(new Color(216, 224, 227));
      backToMenuButton.setFont(new Font("Helvetica", 1, 20));;
      backToMenuButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
      backToMenuButton.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseEntered(java.awt.event.MouseEvent evt) {
              backToMenuButton.setBackground(new Color(133, 138, 140));
          }
      
          public void mouseExited(java.awt.event.MouseEvent evt) {
              backToMenuButton.setBackground(new Color(216, 224, 227));
          }
      });
      backToMenuButton.addActionListener(new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent e) {
            returnToMenu();
         }
      });
      backToMenu.setBounds((ss.width / 2) - 100 - 450, ss.height - 200, 200, 100);
      backToMenu.add(backToMenuButton);
      playAgain = new JButton("Play Again");
      playAgain.setBackground(new Color(216, 224, 227));
      playAgain.setFont(new Font("Helvetica", 1, 20));;
      playAgain.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
      playAgain.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseEntered(java.awt.event.MouseEvent evt) {
              playAgain.setBackground(new Color(133, 138, 140));
          }
      
          public void mouseExited(java.awt.event.MouseEvent evt) {
              playAgain.setBackground(new Color(216, 224, 227));
          }
      });
      playAgain.addActionListener(new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Chess.init();
         }
      });
      playAgainMenu.setBounds((ss.width / 2) - 100 + 450, ss.height - 200, 200, 100);
      playAgainMenu.add(playAgain);
      frame.add(playAgainMenu);
      frame.add(backToMenu);
      frame.revalidate();
   }
}