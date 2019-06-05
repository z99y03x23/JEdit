package com.tao.Imp;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.jar.JarFile;

public class EMethod {
    public static void newFile() {

    }

    public static void openFile(JFrame jFrame) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new FileNameExtensionFilter("文本文件","txt"));
        int returnValue = jFileChooser.showOpenDialog(jFrame.getContentPane());
    }

    public static void exitFile() {
        System.exit(0);
    }

    public static void shearText() {

    }

    public static void pasteText() {

    }

    public static void copyText() {

    }
}
