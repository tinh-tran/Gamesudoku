package Graphics;

import Game.Clock;
import Game.Level;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ControlPanel extends JPanel {

    private JButton checkSolutionButton;
    private JButton newGameButton;
    private JLabel solutionTimeLabel;
    private JLabel levelLabel;
    private JButton aboutAuthorButton;
    private Window mainFrame;
    private Clock clock;
    private JRadioButton eazyRButton;
    private JRadioButton mediumRButton;
    private JRadioButton hardRButton;
    private ButtonGroup levelButtons;

    public ControlPanel(Window window) {
        mainFrame = window;
        levelButtons = new ButtonGroup();
        setConfiguration();
        addNewGameButton();
        addCheckButton();
        addAuthorButton();
        addCheckBoxes();
        addLevelLabel();
    }

    private void addCheckButton() {
        checkSolutionButton = new JButton("Kiểm Tra");
        checkSolutionButton.setEnabled(false);
        checkSolutionButton.setFocusPainted(false);
        checkSolutionButton.setBounds(50, 200, 100, 50);
        checkSolutionButton.setBackground(Color.LIGHT_GRAY);
        checkSolutionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkGame(mainFrame)) {
                    clock.getTimer().cancel();
                    checkSolutionButton.setEnabled(false);
                    JOptionPane pane = new JOptionPane(
                        "Xin Chuc Mung Ban Da Hoan Thanh Xong Game"
                        + "\nMember" + solutionTimeLabel.getText());
                    JDialog d = pane.createDialog(null, "Thong Bao!");
                    d.setLocation(mainFrame.getX() + 150, mainFrame.getY() + 200);
                    d.setVisible(true);
                } else {
                    clock.setSeconds(clock.getSeconds() + 9000);
                }
            }
        });
        add(checkSolutionButton);
    }

    private void addNewGameButton() {
        newGameButton = new JButton("Bắt Đầu ");
        newGameButton.setBounds(50, 130, 100, 50);
        newGameButton.setBackground(Color.LIGHT_GRAY);
        newGameButton.setFocusPainted(false);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //dùng try catch để bắt lỗi. Nếu hàm try đúng thì bỏ qua các hàm trong catch 
                //nếu try sai thực thi hàm trong catch 
                try {
                    Level levelType;
                    if (eazyRButton.isSelected()) {
                        levelType = Level.EAZY;
                    } else if (mediumRButton.isSelected()) {
                        levelType = Level.MEDIUM;
                    } else {
                        levelType = Level.HARD;
                    }
                    //get giá trị từ radio check để chọn level 
                    mainFrame.generateNewGame(levelType);
                    checkSolutionButton.setEnabled(true);
                    if (solutionTimeLabel != null) {
                        remove(solutionTimeLabel);
                        clock.getTimer().cancel();
                    }
                    addTimeLabel();
                    //Đếm thời gian bắt đầu chạy khi bấm bắt đầu 
                } catch (FileNotFoundException ex) {
                    JOptionPane pane = new JOptionPane("Vui Long Chon Level");
                    pane.setMessageType(JOptionPane.ERROR_MESSAGE);
                    JDialog d = pane.createDialog(null, "Thong Bao");
                    d.setLocation(new Point(300, 300));
                    d.setVisible(true);
                }
            }
        });
        add(newGameButton);
    }

    private void addTimeLabel() {
        solutionTimeLabel = new JLabel();
        solutionTimeLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        solutionTimeLabel.setText("Time: 00:00");
        solutionTimeLabel.setBounds(40, 50, 200, 30);
        solutionTimeLabel.setForeground(new Color(255, 153, 51));
        add(solutionTimeLabel);
        clock = new Clock(solutionTimeLabel);
    }

    private void addAuthorButton() {
        aboutAuthorButton = new JButton("Version");
        aboutAuthorButton.setBounds(50, 270, 100, 50);
        aboutAuthorButton.setBackground(Color.LIGHT_GRAY);
        aboutAuthorButton.setFocusPainted(false);
        aboutAuthorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane pane = new JOptionPane(
                    "Super Sudoku "
                    + "\nNhóm  4 © 2017 "
                    + "\nKiên ,Nhật ,Tính ,Cường,Q.Anh ");
                pane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
                JDialog d = pane.createDialog(null, "Version 1");
                d.setLocation(mainFrame.getX() + 150, mainFrame.getY() + 200);
                d.setVisible(true);
            }
        });
        add(aboutAuthorButton);
    }

    private boolean checkGame(Window window) {
        boolean isCorrect = true;
        int[][] solution = window.getSudokuGame().getSolution();
        LinkedList<Cell> userSolution = window.getSudokuPanel().getBoardCells();
        int cellIndex = 0;
        Color wrongColor = new Color(255, 77, 77);
        Color goodColor = Color.LIGHT_GRAY;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    if (solution[i][j] != Integer.parseInt(userSolution.get(cellIndex).getCellValue().getText())) {
                        isCorrect = false;
                        userSolution.get(cellIndex).setBackground(wrongColor);
                    } else {
                        if (userSolution.get(cellIndex).getBackground() != goodColor) {
                            userSolution.get(cellIndex).setBackground(goodColor);
                        }
                    }
                } catch (NumberFormatException ex) {
                    isCorrect = false;
                    userSolution.get(cellIndex).setBackground(new Color(255, 77, 77));
                }
                cellIndex++;
            }
        }
        return isCorrect;
    }

    public void addCheckBoxes() {
        addEazyLevelCheckBox();
        addMediumLevelCheckBox();
        addHardLevelCheckBox();
        levelButtons.add(eazyRButton);
        levelButtons.add(mediumRButton);
        levelButtons.add(hardRButton);
    }

    private void addEazyLevelCheckBox() {
        eazyRButton = new JRadioButton("Dễ");
        eazyRButton.setBounds(55, 410,150, 20);
        eazyRButton.setBackground(Color.DARK_GRAY);
        eazyRButton.setForeground(Color.WHITE);
        eazyRButton.setBorderPainted(false);
        eazyRButton.setFocusPainted(false);
        eazyRButton.setSelected(true);
        eazyRButton.setVisible(true);
        add(eazyRButton);
    }

    private void addMediumLevelCheckBox() {
        mediumRButton = new JRadioButton("Trung Bình ");
        mediumRButton.setBounds(55, 430, 150, 20);
        mediumRButton.setBackground(Color.DARK_GRAY);
        mediumRButton.setForeground(Color.WHITE);
        mediumRButton.setBorderPainted(false);
        mediumRButton.setFocusPainted(false);
        mediumRButton.setSelected(false);
        mediumRButton.setVisible(true);
        add(mediumRButton);
    }

    private void addHardLevelCheckBox() {
        hardRButton = new JRadioButton("Khó ");
        hardRButton.setBounds(55, 450, 150, 20);
        hardRButton.setBackground(Color.DARK_GRAY);
        hardRButton.setForeground(Color.WHITE);
        hardRButton.setBorderPainted(false);
        hardRButton.setFocusPainted(false);
        hardRButton.setSelected(false);
        hardRButton.setVisible(true);
        add(hardRButton);
    }

    private void addLevelLabel() {
        levelLabel = new JLabel("Level");
        levelLabel.setBounds(50, 360, 100, 50);
        levelLabel.setForeground(new Color(255, 153, 51));
        levelLabel.setFont(new Font("Calibri", Font.BOLD, 25));
        levelLabel.setVisible(true);
        add(levelLabel);
    }

    private void setConfiguration() {
        setLayout(null);
        setBounds(500, 0, 200, 530);
        setPreferredSize(new Dimension(180, 530));
        setBackground(Color.DARK_GRAY);
        setOpaque(true);
    }
}
