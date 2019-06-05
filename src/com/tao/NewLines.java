package com.tao;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class NewLines {
    public static void newLines(JTextPane text) {
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyledDocument doc = text.getStyledDocument();
        FontMetrics fm = text.getFontMetrics(text.getFont());   //得到当前字体尺寸
        int paneWidth = text.getWidth();

        try {
            String tt = text.getText();
            for (int i = 0, cnt = 0; i< tt.length(); i++) {
                if ((cnt += fm.charWidth(tt.charAt(i))) >= paneWidth) {
                    cnt = 0;
                    doc.insertString(doc.getLength(),"\n",set);
                    continue;
                }
                doc.insertString(doc.getLength(),"\n",set);

                text.setCaretPosition(doc.getLength());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
