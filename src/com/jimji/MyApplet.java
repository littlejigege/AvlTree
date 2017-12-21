package com.jimji;

import javafx.scene.text.Text;

import java.applet.Applet;
import java.awt.*;

public class MyApplet extends Applet {
    TextArea btInsert;
    TextArea btDelete;
    TextArea btSearh;
    Choice choiceInsert;
    Choice choiceDelete;
    Choice choiceSearh;
    AVLTree<Integer> tree = new AVLTree<>(1);
    int toFind = -1;

    @Override
    public void init() {
        super.init();
        setupTree();
        resize(500, 500);

        btInsert = new TextArea("insert");
        btDelete = new TextArea("delete");
        btSearh = new TextArea("search");
        choiceDelete = new Choice();
        choiceInsert = new Choice();
        choiceSearh = new Choice();
        for (int i = 0; i < 50; i++) {
            choiceSearh.add(i + "");
            choiceInsert.add(i + "");
            choiceDelete.add(i + "");
        }
        btInsert.setBounds(0, 0, 60, 20);
        choiceInsert.setBounds(0, 20, 60, 20);
        btDelete.setBounds(0, 40, 60, 20);
        choiceDelete.setBounds(0, 60, 60, 20);
        btSearh.setBounds(0, 80, 60, 20);
        choiceSearh.setBounds(0, 100, 60, 20);

        setLayout(null);
        add(btInsert);
        add(btDelete);
        add(btSearh);
        add(choiceInsert);
        add(choiceDelete);
        add(choiceSearh);

    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintTree(g, tree, 0, getWidth(), 20, toFind);
    }

    void paintText(Graphics g, String string, int x, int y) {
        g.drawString(string, x, y);
    }

    @Override
    public boolean action(Event evt, Object what) {
        if (evt.target == choiceInsert) {
            tree.insert(Integer.parseInt(evt.arg.toString()));
        } else if (evt.target == choiceSearh) {
            toFind = Integer.parseInt(evt.arg.toString());
        } else if (evt.target == choiceDelete) {
            tree.delete(Integer.parseInt(evt.arg.toString()));
        }
        repaint();
        return super.action(evt, what);
    }

    void paintTree(Graphics g, AVLTree tree, int x, int width, int y, int tofind) {
        g.setColor(Color.BLUE);
        if (tofind == (Integer) tree.data) {
            g.drawRect(x + width / 2 - 10, y - 20, 40, 20);
        }
        paintText(g, tree.data.toString(), x + width / 2, y);
        g.setColor(Color.RED);
        paintText(g, String.valueOf(tree.bln), x + width / 2, y + 20);
        if (tree.leftChild != null) {
            g.setColor(Color.GREEN);
            g.drawLine(x + width / 2, y, (x + width / 4), y + 100);
            paintTree(g, (AVLTree) tree.leftChild, x, width / 2, y + 100, tofind);
        }
        if (tree.rightChild != null) {
            g.setColor(Color.GREEN);
            g.drawLine(x + width / 2, y, x + width / 2 + width / 4, y + 100);
            paintTree(g, (AVLTree) tree.rightChild, x + width / 2, width / 2, y + 100, tofind);
        }

    }

    void setupTree() {
        tree.insert(49);
        tree.insert(48);
        tree.insert(5);
        tree.insert(4);
    }
}
