import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.BorderFactory;

public class Menu extends JPanel {
   
   public static JPanel buttons;
   public static JButton start;
   public static JButton exit;
   public static BufferedImage logo;
   
   public static int width = -1;
   public static int height = -1;
   
   public Menu() {
      setLayout(null);
      try {
         logo = ImageIO.read(new File("images/ChessterLogo.png"));
      } catch (IOException ie) {
         ie.printStackTrace();
      }
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      width = screenSize.width;
      height = screenSize.height;
      start = new JButton();
      exit = new JButton();
      start.setText("Start");
      start.setBackground(new Color(216, 224, 227));
      start.setFont(new Font("Helvetica", 1, 20));;
      start.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
      start.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseEntered(java.awt.event.MouseEvent evt) {
              start.setBackground(new Color(133, 138, 140));
          }
      
          public void mouseExited(java.awt.event.MouseEvent evt) {
              start.setBackground(new Color(216, 224, 227));
          }
      });
      start.addActionListener(new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent e) {
            chessGUI.getGameMode();
         }
      });
      exit.setText("Exit");
      exit.setBackground(new Color(216, 224, 227));
      exit.setFont(new Font("Helvetica", 1, 20));;
      exit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
      exit.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseEntered(java.awt.event.MouseEvent evt) {
              exit.setBackground(new Color(133, 138, 140));
          }
      
          public void mouseExited(java.awt.event.MouseEvent evt) {
              exit.setBackground(new Color(216, 224, 227));
          }
      });
      exit.addActionListener(new AbstractAction() {
         @Override
         public void actionPerformed(ActionEvent e) {
            System.exit(0);
         }
      });
      buttons = new JPanel();
      buttons.setBackground(new Color(162, 235, 192));
      buttons.setLayout(new GridLayout(3, 1));
      buttons.setBounds((screenSize.width / 2) - 200, (screenSize.height / 2), 400, 200);
      buttons.add(start);
      buttons.add(new JLabel());
      buttons.add(exit);
      add(buttons);
   }
   
   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(logo, (width / 2) - (logo.getWidth() / 2), (height / 2) - (4 * logo.getHeight() / 5), null);
   }
}