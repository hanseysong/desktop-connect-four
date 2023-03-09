package four;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConnectFour1 extends JFrame {
    final int[] turn = {0};

    static ArrayList<JButton> buttonList = new ArrayList<>();

    private boolean gameFinished;

    public void ParentPanel() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public ConnectFour1() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
        setLayout(new BorderLayout());
        setTitle("Connect Four");
        Board board = new Board();
        board.setInitBoard();

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(board.getRowNum(), board.getColNum(), 0, 0));

        for (int i = board.getInitBoard().size() - 1; i >= 0; i--) {
            for (int j = 0; j < board.getInitBoard().get(i).size(); j++) {
                JButton button = new JButton(" ");
                button.setName("Button" + board.getInitBoard().get(i).get(j));

                gridPanel.add(button);
                buttonList.add(button);
                addListener(button);
            }
        }

        JPanel reset = new JPanel();
        reset.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton resetButton = new JButton("Reset");
        resetButton.setName("ButtonReset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Component[] components = gridPanel.getComponents();
                for (Component component: components) {
                    if (component instanceof JButton) {
                        ((JButton) component).setText(" ");
                    }
                }
            }
        });
        reset.add(resetButton);

        add(gridPanel, BorderLayout.CENTER);
        add(reset, BorderLayout.SOUTH);

        setVisible(true);
    }

    void addListener(JButton button) {

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();
                System.out.println(source.getName());
                JButton target = (JButton) getFirstButton(source);
                assert target != null;
                if (turn[0] % 2 == 0) {
                    if (Objects.equals(target.getText(), " ")) {
                        target.setText("X");
                    }

                } else {
                    if (Objects.equals(target.getText(), " ")) {
                        target.setText("O");
                    }
                }
                turn[0] += 1;
            }
        });
    }

    void clearPanel() {

    }

    static JButton getFirstButton(JButton button) {
        char colNum = button.getName().toCharArray()[6];
        ArrayList<JButton> sameCol = buttonList.stream()
                .filter(el -> el.getName().toCharArray()[6] == colNum)
                .collect(Collectors.toCollection(ArrayList::new));

        for (int i = sameCol.size() - 1; i >= 0; i--) {
            if (sameCol.get(i) != null) {
                JButton targetButton = (JButton) sameCol.get(i);
                if (targetButton.getName().toCharArray()[6] == colNum && Objects.equals(targetButton.getText(), " ")) {
                    return targetButton;
                }
            }
        }
        return button;
    }

}

class Board {
    private final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private ArrayList<ArrayList<String>> initBoard = new ArrayList<ArrayList<String>>();
    private int rowNum;
    private int colNum;


    public Board() {
        this.rowNum = 0;
        this.colNum = 0;
    }

    public void addRow() {
        this.rowNum += 1;
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < 7; i++) {
            temp.add(alphabet[i] + String.valueOf(rowNum));
        }
        initBoard.add(temp);
    }

    public void setInitBoard() {
        for (int i = 0; i < 6; i++) {
            addRow();
        }
    }

    public int getColNum() {
        return colNum;
    }

    public int getRowNum() {
        return rowNum;
    }

    public ArrayList<ArrayList<String>> getInitBoard() {
        return initBoard;
    }
}