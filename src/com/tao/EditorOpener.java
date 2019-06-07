package com.tao;

import com.tao.Imp.EMethod;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;


public class EditorOpener extends JFrame {
    private JMenuBar bar = new JMenuBar();

    private JMenu file = new JMenu();
    private JMenuItem file_open = new JMenuItem();
    private JMenuItem file_new = new JMenuItem();
    private JMenuItem file_exit = new JMenuItem();
    private JMenuItem file_save = new JMenuItem();

    private JMenu edit = new JMenu();
    private JMenuItem edit_shear = new JMenuItem();
    private JMenuItem edit_copy = new JMenuItem();
    private JMenuItem edit_paste = new JMenuItem();

    private JTextPane text = new JTextPane();

    private boolean modified = false;
    private boolean status = false;

    String fileName;
    String filePath;

    File chooseFile;

    public EditorOpener() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("文本编辑器");
        setBounds(100,100,400,500);
        setJMenuBar(bar);
        file.setText("文件");
        edit.setText("编辑");
        bar.add(file);
        bar.add(edit);
        file_new.setText("新建");
        file_open.setText("打开");
        file_exit.setText("退出");
        file_save.setText("保存");

        edit_shear.setText("剪切");
        edit_copy.setText("复制");
        edit_paste.setText("粘贴");
        file_new.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EMethod.newFile();
            }
        });
        file_open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
                status = true;
            }
        });
        file_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });
        file_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitFile();
            }
        });
        edit_shear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EMethod.shearText();
            }
        });
        edit_paste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EMethod.pasteText();
            }
        });
        edit_copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EMethod.copyText();
            }
        });
        file.add(file_new);
        file.add(file_open);
        file.add(file_save);
        file.add(file_exit);
        edit.add(edit_copy);
        edit.add(edit_paste);
        edit.add(edit_shear);

        text.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (status == true) {
                    modified = true;
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (status == true) {
                    modified = true;
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (status == true) {
                    modified = true;
                }
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitFile();
            }
        });

        final JTabbedPane tabbedPane=new JTabbedPane();    //创建选项卡面板
        getContentPane().add(tabbedPane,BorderLayout.CENTER);   //把选项卡面板放到窗体中央
        final JScrollPane scrollPane = new JScrollPane();    //创建滚动面板
        tabbedPane.add(scrollPane);
        scrollPane.setViewportView(text);

        NewLines.newLines(text);
    }

    public static void main(String[] args) {
        SwingConsle.run(new EditorOpener(), 600, 600);
    }

    public void openFile() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new FileNameExtensionFilter("文本文件","txt"));
        int returnValue = jFileChooser.showOpenDialog(getContentPane());
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            chooseFile = jFileChooser.getSelectedFile();
            fileName = chooseFile.getName();
            filePath = chooseFile.getPath();
            FileReader reader;
            BufferedReader in;
            try {
                reader = new FileReader(chooseFile);
                in = new BufferedReader(reader);
                String info = in.readLine();
                while (info != null) {
                    SimpleAttributeSet set = new SimpleAttributeSet();
                    StyledDocument doc = text.getStyledDocument();
                    doc.insertString(doc.getLength(), info+"\n", set);
                    info = in.readLine();
                }
                in.close();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveFile() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.setCurrentDirectory(chooseFile);
        int rVal = jFileChooser.showSaveDialog(EditorOpener.this);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            FileWriter writer;
            BufferedWriter out;
            try {
                //System.out.println(text.getText());
                writer = new FileWriter(chooseFile);
                String str = text.getText();
                writer.write(str);
                writer.close();
                modified = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void exitFile() {
        if (modified == true) {
            int rVal = JOptionPane.showConfirmDialog(null, "是" +
                    "否将更改保存", "Editor", JOptionPane.YES_NO_CANCEL_OPTION);
            if (rVal == JOptionPane.YES_OPTION) {
                saveFile();
                System.exit(0);
            } else if (rVal == JOptionPane.NO_OPTION){
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }
}
