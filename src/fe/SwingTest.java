 package fe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;
import be.*;
import be.Point;
import enums.*;
import java.util.HashMap;
import java.util.Map;

public class SwingTest extends JFrame {

    private JPanel boardPanel;
    private MineSweeperGame game;
    private JLabel timerLabel;
    private Timer timer;
    private int timeElapsed;
    private JButton newGameButton;
    private boolean isFirstClick;
    private Map<Integer, Color> numberColors;
    private ImageIcon flagIcon;

    public SwingTest() {
        initializeUI();
        setResizable(false);
    }

    private void initializeUI() {
        game = new MineSweeperGame(10, 10, 10);
        game.initializeGame();
        isFirstClick = true;

        boardPanel = new JPanel(new GridLayout(10, 10)); // 10x10 board
        initializeBoard();

        timerLabel = new JLabel("Time: 0");
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> restartGame());

        JPanel topPanel = new JPanel();
        topPanel.add(timerLabel);
        topPanel.add(newGameButton);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        setTitle("Mayın Tarlası");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        initializeColors();
        //flagIcon = new ImageIcon(new ImageIcon("flag.jpg").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)); // Bayrak resmini yükle
        flagIcon = new ImageIcon(new ImageIcon(getClass().getResource("/resources/flag.jpg")).getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
    }

    private void initializeBoard() {
        boardPanel.removeAll();
        Cell[][] cells = game.getGameBoard().getBoard();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                final int x = i;
                final int y = j;
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(50, 50));
                button.setFont(new Font("Arial", Font.PLAIN, 12));
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            onCellClicked(x, y);
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            onCellRightClicked(x, y);
                        }
                    }
                });
                boardPanel.add(button);
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void onCellClicked(int x, int y) {
        if (isFirstClick) {
            startTimer();
            isFirstClick = false;
        }

        game.revealCell(new Point(x, y));
        updateBoard();
        if (game.getGameState() == GameState.GAME_WON) {
            stopTimer();
            JOptionPane.showMessageDialog(this, "Kazandınız!");
        } else if (game.getGameState() == GameState.GAME_LOST) {
            stopTimer();
            revealAllMines();
            JOptionPane.showMessageDialog(this, "Kaybettiniz!");
        }
    }

    private void onCellRightClicked(int x, int y) {
        game.toggleFlag(new Point(x, y));
        updateBoard();
    }

    private void updateBoard() {
        Cell[][] cells = game.getGameBoard().getBoard();
        Component[] buttons = boardPanel.getComponents();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                JButton button = (JButton) buttons[i * cells.length + j];
                Cell cell = cells[i][j];
                if (cell.isRevealed()) {
                    if (cell.isMine()) {
                        button.setText("X");
                        button.setBackground(Color.RED);
                    } else {
                        int minesAround = cell.getMinesAround();
                        button.setText(minesAround > 0 ? String.valueOf(minesAround) : "");
                        button.setBackground(numberColors.getOrDefault(minesAround, Color.LIGHT_GRAY));
                    }
                    button.setOpaque(true); // Rengin gözükmesini sağlar
                    button.setBorderPainted(false);
                    button.setEnabled(false);
                } else if (cell.isFlag()) {
                    button.setIcon(flagIcon);
                } else {
                    button.setIcon(null);
                    button.setText("");
                    button.setBackground(null);
                    button.setOpaque(true); // Rengin gözükmesini sağlar
                    button.setBorderPainted(true);
                }
            }
        }
    }

    private void revealAllMines() {
        Cell[][] cells = game.getGameBoard().getBoard();
        Component[] buttons = boardPanel.getComponents();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                JButton button = (JButton) buttons[i * cells.length + j];
                Cell cell = cells[i][j];
                if (!cell.isRevealed() && !cell.isFlag()) {
                    if (cell.isMine()) {
                        button.setText("X");
                        button.setBackground(Color.RED);
                    } else {
                        button.setBackground(Color.DARK_GRAY);
                    }
                    button.setOpaque(true); // Rengin gözükmesini sağlar
                    button.setBorderPainted(false);
                    button.setEnabled(false);
                }
            }
        }
    }

    private void startTimer() {
        timeElapsed = 0;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeElapsed++;
                timerLabel.setText("Time: " + timeElapsed);
            }
        }, 1000, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void restartGame() {
        stopTimer();
        game.initializeGame();
        initializeBoard();
        timerLabel.setText("Time: 0");
        isFirstClick = true;
    }

    private void initializeColors() {
        numberColors = new HashMap<>();
        numberColors.put(1, new Color(173, 216, 230)); // Soluk mavi
        numberColors.put(2, new Color(135, 206, 235)); // Daha parlak mavi
        numberColors.put(3, new Color(0, 191, 255));  // Mavi
        numberColors.put(4, new Color(30, 144, 255)); // Daha koyu mavi
        numberColors.put(5, new Color(255, 165, 0));  // Turuncu
        numberColors.put(6, new Color(255, 140, 0));  // Daha koyu turuncu
        numberColors.put(7, new Color(255, 69, 0));   // Kırmızı
        numberColors.put(8, new Color(255, 0, 0));    // Çok sıcak kırmızı
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SwingTest::new);
    }
}
