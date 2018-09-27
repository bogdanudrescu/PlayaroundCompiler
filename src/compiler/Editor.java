/*
 * Created on Feb 14, 2005
 */
package compiler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

import compiler.analizer.Compiler;
import compiler.exception.AnalizerException;

/**
 *  
 */
public class Editor extends JFrame {

    /**
     *  
     */
    public class KeyHandler extends KeyAdapter {

        private int[] lockedCodes = { 0, 16, 17, 18, 20, 33, 34, 35, 36, 37, 38, 39, 40, 112, 113, 114, 115, 116, 117,
                118, 119, 120, 121, 122, 123, 144, 145, 155 };

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
         */
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            boolean state = false;
            for (int i = 0; i < lockedCodes.length; i++) {
                if (code == lockedCodes[i]) {
                    state = true;
                    break;
                }
            }
            if (!state) {
                saved = false;
                compilation = null;
            }
        }
    }

    /**
     *  
     */
    public class ActionHandler implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
         * ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            if ("new".equals(cmd)) {
                if (!saved) {
                    System.out.println("not saved");
                }
                textArea.setText("");
                compilation = null;
            } else if ("open".equals(cmd)) {
                int state = fileChooser.showOpenDialog(Editor.this);
                if (state == JFileChooser.APPROVE_OPTION) {
                    open(fileChooser.getSelectedFile().getAbsolutePath());
                }
                compilation = null;
            } else if ("saveAs".equals(cmd)) {
                int state = fileChooser.showSaveDialog(Editor.this);
                if (state == JFileChooser.APPROVE_OPTION) {
                    save(fileChooser.getSelectedFile().getAbsolutePath(), fileChooser.getFileFilter().getDescription());
                }
            } else if ("save".equals(cmd)) {
                if (fileName == null) {
                    int state = fileChooser.showSaveDialog(Editor.this);
                    if (state == JFileChooser.APPROVE_OPTION) {
                        save(fileChooser.getSelectedFile().getAbsolutePath(),
                                fileChooser.getFileFilter().getDescription());
                    }
                } else {
                    save(fileName, null);
                }
            } else if ("exit".equals(cmd)) {
                System.exit(1);
            } else if ("compile".equals(cmd)) {
                try {
                    compilation = compiler.compile(textArea.getText());
                    System.out.println("Program compiled.");
                } catch (AnalizerException ex) {
                    System.out.println("The following exception occurs:");
                    System.out.println(ex);
                }
            } else if ("run".equals(cmd)) {
                if (compilation == null) {
                    System.out.println("Compile the program first.");
                    return;
                }
                runner.startPerform(compilation);
            }
        }

    }

    /**
     * The text are into which the code is written
     */
    private JTextArea textArea;

    /**
     * States whether the file was saved or not
     */
    private boolean saved = true;

    /**
     * The open/save dialog
     */
    private JFileChooser fileChooser;

    /**
     * The name of the current file
     */
    private String fileName;

    /**
     * The file type
     */
    private static final String FILE_EXT = ".cmp";

    /**
     * The file description
     */
    private static final String FILE_DESC = "Compiler file (*.cmp)";

    /**
     * The compiler
     */
    private Compiler compiler;

    /**
     * The compilation list
     */
    private List compilation;

    /**
     * The runner
     */
    private Runner runner;

    /**
     * Create an text editor
     */
    public Editor() {
        compiler = new Compiler();
        runner = new Runner();

        textArea = new JTextArea();
        textArea.setTabSize(2);
        textArea.addKeyListener(new KeyHandler());

        JScrollPane scrollPane = new JScrollPane(textArea);
        getContentPane().add(scrollPane);

        ActionListener actionListener = new ActionHandler();

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.setDisplayedMnemonicIndex(0);
        JMenuItem newItem = new JMenuItem("New");
        newItem.setActionCommand("new");
        newItem.addActionListener(actionListener);
        newItem.setMnemonic(KeyEvent.VK_N);
        newItem.setDisplayedMnemonicIndex(0);
        JMenuItem openItem = new JMenuItem("Open");
        openItem.setMnemonic(KeyEvent.VK_O);
        openItem.setDisplayedMnemonicIndex(0);
        openItem.setActionCommand("open");
        openItem.addActionListener(actionListener);
        JMenuItem saveAsItem = new JMenuItem("Save As");
        saveAsItem.setMnemonic(KeyEvent.VK_A);
        saveAsItem.setDisplayedMnemonicIndex(5);
        saveAsItem.setActionCommand("saveAs");
        saveAsItem.addActionListener(actionListener);
        // JMenuItem saveItem = new JMenuItem("Save");
        // saveItem.setActionCommand("save");
        // saveItem.addActionListener(actionListener);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic(KeyEvent.VK_X);
        exitItem.setDisplayedMnemonicIndex(1);
        exitItem.setActionCommand("exit");
        exitItem.addActionListener(actionListener);
        fileMenu.add(newItem);
        fileMenu.addSeparator();
        fileMenu.add(openItem);
        fileMenu.add(saveAsItem);
        // fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu projectMenu = new JMenu("Project");
        projectMenu.setMnemonic(KeyEvent.VK_P);
        projectMenu.setDisplayedMnemonicIndex(0);
        JMenuItem compileItem = new JMenuItem("Compile");
        compileItem.setMnemonic(KeyEvent.VK_C);
        compileItem.setDisplayedMnemonicIndex(0);
        compileItem.setActionCommand("compile");
        compileItem.addActionListener(actionListener);
        JMenuItem runItem = new JMenuItem("Run");
        runItem.setMnemonic(KeyEvent.VK_R);
        runItem.setDisplayedMnemonicIndex(0);
        runItem.setActionCommand("run");
        runItem.addActionListener(actionListener);
        projectMenu.add(compileItem);
        projectMenu.add(runItem);

        menuBar.add(fileMenu);
        menuBar.add(projectMenu);

        setJMenuBar(menuBar);

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {

            public boolean accept(File f) {
                String name = f.getName();
                if (f.isDirectory() || (name.endsWith(FILE_EXT))) {
                    return true;
                }
                return false;
            }

            public String getDescription() {
                return FILE_DESC;
            }

        });

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(1);
            }
        });
    }

    /**
     * @param absolutePath
     */
    private void open(String absolutePath) {
        try {
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new FileReader(absolutePath));
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line + "\n");
                line = reader.readLine();
            }
            textArea.setText(buffer.toString());
        } catch (IOException e) {
        }
    }

    /**
     * @param selectedFile
     *            the selected file
     * @param description
     *            the file desctiption
     */
    private void save(String selectedFile, String description) {
        BufferedWriter writer;

        try {
            String path;
            if (description.equals(FILE_DESC) && !selectedFile.endsWith(FILE_EXT)) {
                path = selectedFile + FILE_EXT;
            } else {
                path = selectedFile;
            }
            writer = new BufferedWriter(new FileWriter(path));
            writer.write(textArea.getText());
            writer.close();
            saved = true;
        } catch (IOException e) {
        }
    }

    /**
     * The main method
     * 
     * @param args
     */
    public static void main(String[] args) {
        Editor editor = new Editor();
        editor.setBounds(100, 100, 800, 600);
        editor.setVisible(true);
    }

}