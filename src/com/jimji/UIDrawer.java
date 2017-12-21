package com.jimji;

import java.util.List;

public class UIDrawer {

    private final String LINE_X = "—";
    private final String LINE_Y = "|";
    private final String top_lines = " __________________________________________\n" +
            "|                                          |\n" +
            "|                                          |";
    private final String bottom_lines = "|                                          |\n" +
            "|                                          |\n" +
            " -----------------------------------------\n";

    void 画首页() {
        System.out.println(top_lines);
        System.out.println(getline("1.随机生成树"));
        System.out.println(getline("2.插入"));
        System.out.println(getline("3.删除"));
        System.out.println(getline("4.查找"));
        System.out.println(bottom_lines);

    }

    String getline(String text) {
        StringBuilder builder = new StringBuilder(text);
        int len = text.length();
        if ((42 - len) % 2 == 0) {

            builder.insert(0, LINE_Y);
            for (int i = 0; i < (42 - len) / 2; i++) {
                builder.insert(1, " ");
            }
            for (int i = 0; i < (42 - len) / 2; i++) {
                builder.append(" ");
            }
            builder.append(LINE_Y);
        } else {
            builder.insert(0, LINE_Y);
            for (int i = 0; i < (42 - len) / 2; i++) {
                builder.insert(1, " ");
            }
            for (int i = 0; i < (42 - len) / 2; i++) {
                builder.append(" ");
            }
            builder.append(" " + LINE_Y);
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        new UIDrawer().画首页();
    }
}

