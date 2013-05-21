package real.Objects.Services;

import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import real.Interfaces.IService;
import real.Objects.Dataset;
import real.Objects.Exceptions.*;
import real.Objects.*;
import real.Objects.GUI.*;


public class MainWindow extends javax.swing.JFrame implements IService
{
    private DefaultListModel relationModel = new DefaultListModel();
    private Query query;
    private ErrorView errorView;
    static private Font standardFont;

    public MainWindow()
    {
        this.initComponents();
        errorView = new ErrorView();
        Kernel.GetService(ErrorSystem.class).addObserver(errorView);
        queryView.add(errorView, "Run Errors");
        this.setLocationRelativeTo(null);
        // Setup listener so closing the window will close the kernel.
        this.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent evt)
            {
                saveOnExit(null);
            }
        });
    }

    public JTextArea getCurrentWorksheet()
    {
        TextQueryView view = (TextQueryView)worksheetPane.getSelectedComponent();

        if(view == null)
        {
            return null;
        }

        return view.getTextArea();
    }

    public JTable getCurrentQueryTable()
    {
        JScrollPane scroll = (JScrollPane)queryView.getSelectedComponent();
        return (JTable)scroll.getViewport().getView();
    }

    @Override
    public void Initialize()
    {
        ImageIcon image = new ImageIcon("assets/icon/Icon.png");
        query = new Query();
        this.setIconImage(image.getImage());
        relationView.setModel(relationModel);
        TextQueryView.setOpManager(query.getTokenOpManager());
        
        // Implement keybinds for various buttons
        Action runKeybindAction = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent a) {
                runButtonActionPerformed(a);
            }
        };
        Action newSheetKeybindAction = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent a) {
                newSheetButtonActionPerformed(a);
            }
        };
        Action removeSheetKeybindAction = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent a) {
                removeSheetButtonActionPerformed(a);
            }
        };
        Action saveButtonKeybindAction = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent a) {
                saveButtonActionPerformed(a);
            }
        };
        Action treeButtonKeybindAction = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent a) {
                showTreeButtonActionPerformed(a);
            }
        };
        KeyStroke keystrokeEnter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK);
        KeyStroke keystrokeN = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK);
        KeyStroke keystrokeR = KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK);
        KeyStroke keystrokeU = KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK);
        KeyStroke keystrokeT = KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK);

        worksheetPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(keystrokeEnter, "enterPressed");
        worksheetPane.getActionMap().put("enterPressed", runKeybindAction);

        jToolBar2.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keystrokeN, "nPressed");
        jToolBar2.getActionMap().put("nPressed", newSheetKeybindAction);

        jToolBar2.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keystrokeR, "rPressed");
        jToolBar2.getActionMap().put("rPressed", removeSheetKeybindAction);

        jToolBar1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keystrokeU, "uPressed");
        jToolBar1.getActionMap().put("uPressed", saveButtonKeybindAction);

        jToolBar1.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keystrokeT, "tPressed");
        jToolBar1.getActionMap().put("tPressed", treeButtonKeybindAction);
        // Done setting keybinds

        //setting the default font
        try
        {
            InputStream is = new BufferedInputStream(new FileInputStream("assets/font/Asana-Math.ttf"));
            standardFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 18);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(standardFont);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.err.println("assets/font/Asana-Math.ttf" + " not loaded.  Using serif font.");
            standardFont = new Font("serif", Font.PLAIN, 14);
        }
        piButton.setFont(standardFont);
        sigmaButton.setFont(standardFont);
        rhoButton.setFont(standardFont);
        gammaButton.setFont(standardFont);
        tauButton.setFont(standardFont);
        deltaButton.setFont(standardFont);
        arrowButton.setFont(standardFont);
        unionButton.setFont(standardFont);
        intersectionButton.setFont(standardFont);
        differenceButton.setFont(standardFont);
        productButton.setFont(standardFont);
        joinButton.setFont(standardFont);
        leftouterjoinButton.setFont(standardFont);
        rightouterjoinButton.setFont(standardFont);
        fullouterjoinButton.setFont(standardFont);
    }

    @Override
    public void Start()
    {
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void Stop()
    {
    }

    public void setLocalTables()
    {
        LocalDataManager local = Kernel.GetService(LocalDataManager.class);
        String[] tables = local.getAllKeys();

        //ignore the last one which is the error pane
        for(int i = tables.length-1; i >= 0;i--)
        {
            JTable table = new JTable();
            JScrollPane scroll = new JScrollPane();
            table.setModel(local.findDataset(tables[i]));
            table.setName(tables[i]);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            scroll.setViewportView(table);
            queryView.insertTab(tables[i], null, scroll, null, queryView.getTabCount()-1);
        }
    }

    public static Font getStandardFont()
    {
        return standardFont;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loadProjectChooser = new javax.swing.JFileChooser();
        saveProjectChooser = new real.Objects.GUI.FileChooserSave();
        importTableChooser = new javax.swing.JFileChooser();
        exportTableChooser = new real.Objects.GUI.FileChooserSave();
        loadScriptChooser = new javax.swing.JFileChooser();
        saveScriptChooser = new real.Objects.GUI.FileChooserSave();
        combinedView = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        runButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        newSheetButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        removeSheetButton = new javax.swing.JButton();
        queryView = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        queryTable = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        saveButton = new javax.swing.JButton();
        showTreeButton = new javax.swing.JButton();
        jToolBar3 = new javax.swing.JToolBar();
        piButton = new javax.swing.JButton();
        sigmaButton = new javax.swing.JButton();
        rhoButton = new javax.swing.JButton();
        gammaButton = new javax.swing.JButton();
        tauButton = new javax.swing.JButton();
        deltaButton = new javax.swing.JButton();
        arrowButton = new javax.swing.JButton();
        unionButton = new javax.swing.JButton();
        intersectionButton = new javax.swing.JButton();
        differenceButton = new javax.swing.JButton();
        productButton = new javax.swing.JButton();
        joinButton = new javax.swing.JButton();
        leftouterjoinButton = new javax.swing.JButton();
        rightouterjoinButton = new javax.swing.JButton();
        fullouterjoinButton = new javax.swing.JButton();
        worksheetPane = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableView = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        relationView = new javax.swing.JList();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        saveProjectMenuItem = new javax.swing.JMenuItem();
        loadProjectMenuItem = new javax.swing.JMenuItem();
        saveScriptMenuItem = new javax.swing.JMenuItem();
        loadScriptMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        importMenuItem = new javax.swing.JMenuItem();
        exportMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();
        helpMenuItem = new javax.swing.JMenuItem();

        loadProjectChooser.setApproveButtonText("Load");
        loadProjectChooser.setDialogTitle("Load Project");
        loadProjectChooser.setFileFilter(new ExtensionFileFilter("real", new String[]{"real"}));

        saveProjectChooser.setAcceptAllFileFilterUsed(false);
        saveProjectChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        saveProjectChooser.setApproveButtonText("Save");
        saveProjectChooser.setDialogTitle("Save Project");
        saveProjectChooser.setFileFilter(new ExtensionFileFilter("real", new String[]{"real"}));

        importTableChooser.setApproveButtonText("Import");
        importTableChooser.setDialogTitle("Import");
        importTableChooser.setFileFilter(new ExtensionFileFilter("csv", new String[]{"csv"}));

        exportTableChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        exportTableChooser.setApproveButtonText("Export");
        exportTableChooser.setDialogTitle("Export");
        exportTableChooser.setFileFilter(new ExtensionFileFilter("csv", new String[]{"csv"}));

        loadScriptChooser.setAcceptAllFileFilterUsed(false);
        loadScriptChooser.setApproveButtonText("Load");
        loadScriptChooser.setFileFilter(new ExtensionFileFilter("txt", new String[]{"txt"}));

        saveScriptChooser.setAcceptAllFileFilterUsed(false);
        saveScriptChooser.setApproveButtonText("Save");
        saveScriptChooser.setFileFilter(new ExtensionFileFilter("txt", new String[]{"txt"}));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ReAl");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        runButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        runButton.setText("Run");
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(runButton);
        jToolBar2.add(jSeparator2);

        newSheetButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        newSheetButton.setText("New sheet");
        newSheetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newSheetButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(newSheetButton);
        jToolBar2.add(jSeparator3);

        removeSheetButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        removeSheetButton.setText("Remove sheet");
        removeSheetButton.setFocusable(false);
        removeSheetButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeSheetButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        removeSheetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeSheetButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(removeSheetButton);

        queryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        queryTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(queryTable);

        queryView.addTab("Query result", jScrollPane1);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        saveButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        saveButton.setText("Save current table");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(saveButton);

        showTreeButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        showTreeButton.setText("Show tree");
        showTreeButton.setFocusable(false);
        showTreeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showTreeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        showTreeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showTreeButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(showTreeButton);

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);

        piButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        piButton.setText("π");
        piButton.setFocusable(false);
        piButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        piButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        piButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                piButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(piButton);

        sigmaButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        sigmaButton.setText("σ");
        sigmaButton.setFocusable(false);
        sigmaButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sigmaButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sigmaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sigmaButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(sigmaButton);

        rhoButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        rhoButton.setText("ρ");
        rhoButton.setFocusable(false);
        rhoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rhoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rhoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rhoButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(rhoButton);

        gammaButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        gammaButton.setText("γ");
        gammaButton.setFocusable(false);
        gammaButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gammaButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gammaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gammaButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(gammaButton);

        tauButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        tauButton.setText("τ");
        tauButton.setFocusable(false);
        tauButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tauButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tauButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tauButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(tauButton);

        deltaButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        deltaButton.setText("δ");
        deltaButton.setFocusable(false);
        deltaButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deltaButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        deltaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deltaButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(deltaButton);

        arrowButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        arrowButton.setText("→");
        arrowButton.setFocusable(false);
        arrowButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        arrowButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        arrowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arrowButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(arrowButton);

        unionButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        unionButton.setText("∪");
        unionButton.setFocusable(false);
        unionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        unionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        unionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unionButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(unionButton);

        intersectionButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        intersectionButton.setText("∩");
        intersectionButton.setFocusable(false);
        intersectionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        intersectionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        intersectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intersectionButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(intersectionButton);

        differenceButton.setFont(new java.awt.Font("Cambria", 0, 24)); // NOI18N
        differenceButton.setText("–");
        differenceButton.setFocusable(false);
        differenceButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        differenceButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        differenceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                differenceButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(differenceButton);

        productButton.setFont(new java.awt.Font("Cambria", 0, 24)); // NOI18N
        productButton.setText("×");
        productButton.setFocusable(false);
        productButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        productButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        productButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(productButton);

        joinButton.setFont(joinButton.getFont().deriveFont(joinButton.getFont().getSize()+13f));
        joinButton.setText("⋈");
        joinButton.setFocusable(false);
        joinButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        joinButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        joinButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(joinButton);

        leftouterjoinButton.setFont(new java.awt.Font("Aharoni", 0, 10)); // NOI18N
        leftouterjoinButton.setText("⟕");
        leftouterjoinButton.setFocusable(false);
        leftouterjoinButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        leftouterjoinButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        leftouterjoinButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftouterjoinButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(leftouterjoinButton);

        rightouterjoinButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        rightouterjoinButton.setText("⟖");
        rightouterjoinButton.setFocusable(false);
        rightouterjoinButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rightouterjoinButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rightouterjoinButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rightouterjoinButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(rightouterjoinButton);

        fullouterjoinButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        fullouterjoinButton.setText("⟗");
        fullouterjoinButton.setFocusable(false);
        fullouterjoinButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        fullouterjoinButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        fullouterjoinButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullouterjoinButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(fullouterjoinButton);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(queryView, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(worksheetPane)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(worksheetPane, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(queryView, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        combinedView.addTab("Query view", jPanel1);

        tableView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableView.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableView.setFocusable(false);
        tableView.setRowSelectionAllowed(false);
        jScrollPane4.setViewportView(tableView);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
        );

        combinedView.addTab("Table view", jPanel2);

        relationView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relationViewMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(relationView);

        fileMenu.setText("File");

        saveProjectMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveProjectMenuItem.setText("Save project");
        saveProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveProjectMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveProjectMenuItem);

        loadProjectMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        loadProjectMenuItem.setText("Load project");
        loadProjectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadProjectMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(loadProjectMenuItem);

        saveScriptMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K, java.awt.event.InputEvent.CTRL_MASK));
        saveScriptMenuItem.setText("Save script");
        saveScriptMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveScriptMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveScriptMenuItem);

        loadScriptMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        loadScriptMenuItem.setText("Load script");
        loadScriptMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadScriptMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(loadScriptMenuItem);
        fileMenu.add(jSeparator1);

        importMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        importMenuItem.setText("Import table");
        importMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(importMenuItem);

        exportMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        exportMenuItem.setText("Export table");
        exportMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exportMenuItem);

        deleteMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        deleteMenuItem.setText("Delete table");
        deleteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(deleteMenuItem);
        fileMenu.add(jSeparator4);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exitMenuItem.setText("Quit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        jMenuBar1.add(fileMenu);

        helpMenu.setText("Help");

        aboutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        helpMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        helpMenuItem.setText("Help");
        helpMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(helpMenuItem);

        jMenuBar1.add(helpMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combinedView)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(combinedView)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_saveButtonActionPerformed
    {//GEN-HEADEREND:event_saveButtonActionPerformed
        String name = queryView.getTitleAt(queryView.getSelectedIndex());
        Dataset dataset = Kernel.GetService(LocalDataManager.class).findDataset(name);

        if(dataset != null)
        {
            //There must not be another table with the same name.
            if(!relationModel.contains(name))
            {
                Kernel.GetService(DataManager.class).setDataset(dataset);
                relationModel.addElement(name);
                TextQueryView.addTableAutoWords(name);
            }
            else
            {
                JOptionPane.showMessageDialog(rootPane,"Table name already exists.");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(rootPane,"No table to save.");
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_runButtonActionPerformed
    {//GEN-HEADEREND:event_runButtonActionPerformed
        try
        {
            if (getCurrentWorksheet() != null)
            {
                //clear the tabs except run error
                queryView.removeAll();
                queryView.addTab("Run Errors", errorView);
                errorView.setText("");
                query.interpret(getCurrentWorksheet().getText());
                //if no throws we can assume that i went without errors
                errorView.setText("Successful run");
                setLocalTables();
                queryView.setSelectedIndex(0);
            }
            else {
                JOptionPane.showMessageDialog(rootPane,"Nothing to run.");
            }
        }
        catch (InvalidSchema | NoSuchAttribute | InvalidParameters | InvalidEvaluation | WrongType | InvalidParsing ex)
        {
            setLocalTables();
            queryView.setSelectedIndex(queryView.getTabCount() - 1);
            Kernel.GetService(ErrorSystem.class).print(ex);
        }
    }//GEN-LAST:event_runButtonActionPerformed

    private void insertSymbol(String symbol)
    {
        JTextArea area = getCurrentWorksheet();
        if(area != null)
        {
           area.insert(symbol, area.getCaretPosition());
        }
        else
        {
            JOptionPane.showMessageDialog(rootPane,"Please create a new worksheet first.");
        }
    }

    private void piButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_piButtonActionPerformed
        insertSymbol("π");
    }//GEN-LAST:event_piButtonActionPerformed

    private void sigmaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sigmaButtonActionPerformed
        insertSymbol("σ");
    }//GEN-LAST:event_sigmaButtonActionPerformed

    private void rhoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rhoButtonActionPerformed
        insertSymbol("ρ");
    }//GEN-LAST:event_rhoButtonActionPerformed

    private void gammaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gammaButtonActionPerformed

        insertSymbol("γ");
    }//GEN-LAST:event_gammaButtonActionPerformed

    private void tauButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tauButtonActionPerformed
        insertSymbol("τ");
    }//GEN-LAST:event_tauButtonActionPerformed

    private void unionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unionButtonActionPerformed
        insertSymbol("∪");
    }//GEN-LAST:event_unionButtonActionPerformed

    private void intersectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intersectionButtonActionPerformed
        insertSymbol("∩");
    }//GEN-LAST:event_intersectionButtonActionPerformed

    private void differenceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_differenceButtonActionPerformed
        insertSymbol("–");
    }//GEN-LAST:event_differenceButtonActionPerformed

    private void productButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productButtonActionPerformed
        insertSymbol("×");
    }//GEN-LAST:event_productButtonActionPerformed

    private void joinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinButtonActionPerformed
        insertSymbol("⋈");
    }//GEN-LAST:event_joinButtonActionPerformed

    private void leftouterjoinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftouterjoinButtonActionPerformed
        insertSymbol("⟕");
    }//GEN-LAST:event_leftouterjoinButtonActionPerformed

    private void rightouterjoinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightouterjoinButtonActionPerformed
        insertSymbol("⟖");
    }//GEN-LAST:event_rightouterjoinButtonActionPerformed

    private void fullouterjoinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullouterjoinButtonActionPerformed
        insertSymbol("⟗");
    }//GEN-LAST:event_fullouterjoinButtonActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        saveOnExit(evt);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        AboutWindow window = new AboutWindow(this, true);
        window.setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void saveProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveProjectMenuItemActionPerformed
        // Get the components to save
        ArrayList<Dataset> datasets = Kernel.GetService(DataManager.class).GetAllDatasets();
        ArrayList<TextQueryView> worksheets = new ArrayList<>();
        for(int i=0; i < worksheetPane.getTabCount(); i++)
        {
            Component component = worksheetPane.getComponentAt(i);
            if(component instanceof TextQueryView)
            {
                TextQueryView tab = (TextQueryView) component;
                worksheets.add(tab);
            }
        }
        // Abort if nothing to save
        if(datasets.isEmpty() && worksheets.isEmpty())
        {
            JOptionPane.showMessageDialog(rootPane, "Nothing to save.");
        }
        else
        {
            int returnVal = saveProjectChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File file = new File(Utility.addExtension(saveProjectChooser.getSelectedFile().getAbsolutePath(), ".real"));
                try
                {
                    ProjectState state = new ProjectState(worksheets, datasets);
                    ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
                    output.writeObject(state);
                    output.flush();
                    output.close();
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                    ex.getMessage();
                    JOptionPane.showMessageDialog(rootPane, "Problem saving file at " + file.getAbsolutePath());
                }
            }
            else
            {
                System.out.println("File save cancelled by user.");
            }
        }
    }//GEN-LAST:event_saveProjectMenuItemActionPerformed

    private void loadProjectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadProjectMenuItemActionPerformed

        if(worksheetPane.getTabCount() != 0 || ! Kernel.GetService(DataManager.class).GetAllDatasets().isEmpty())
        {
            JOptionPane.showMessageDialog(loadProjectChooser,
                    "An active project is open.\nLoading a new project will "
                  + "override any unsaved datasets and/or worksheets.",
                    "Warning",
                     JOptionPane.WARNING_MESSAGE);
        }
        int returnVal = loadProjectChooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = loadProjectChooser.getSelectedFile();
            try
            {
                ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));

                ProjectState state = (ProjectState) input.readObject();
                ArrayList<Dataset> datasets = state.getDatasets();
                ArrayList<TextQueryView> worksheets = state.getWorksheets();

                worksheetPane.removeAll();
                Kernel.GetService(DataManager.class).removeAllDatasets();
                relationModel.removeAllElements();

                for(Dataset dataset : datasets)
                {
                    String name = dataset.getName();
                    Kernel.GetService(DataManager.class).setDataset(dataset);
                    relationModel.addElement(name);
                    TextQueryView.addTableAutoWords(name);
                }

                for(TextQueryView worksheet : worksheets)
                {
                    worksheetPane.addTab(worksheet.getName(), worksheet);
                }
            }
            catch (IOException ex)
            {
                JOptionPane.showMessageDialog(rootPane, "Problem accessing file " + file.getAbsolutePath());
            }
            catch (ClassNotFoundException ex)
            {
                System.out.println("Class not found");
            }
        }
        else
        {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_loadProjectMenuItemActionPerformed

    private void importMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importMenuItemActionPerformed
        //todo: need to write my own filechooser - not use the save and load from script
        int returnVal = importTableChooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = importTableChooser.getSelectedFile();

            try
            {
                //ask for the table name
                String str = (String) JOptionPane.showInputDialog(rootPane,
                            "Please enter the name for the table.", "Table", JOptionPane.PLAIN_MESSAGE, null, null, Utility.filename(file.getName()));

                if(str == null)
                {
                    //user pressed cancel
                }

                else if(str.isEmpty())
                {
                    JOptionPane.showMessageDialog(rootPane, "Table name can't be empty");
                }

                else if(str.contains(" "))
                {
                    JOptionPane.showMessageDialog(rootPane, "Table name can't contain space");
                }

                else
                {
                    Kernel.GetService(DataManager.class).LoadDataset(file.getAbsolutePath(), str);
                    relationModel.addElement(str);
                    //add the word completions for this table
                    TextQueryView.addTableAutoWords(str);
                }
            }
            catch (InvalidDataset | DatasetDuplicate | StringIndexOutOfBoundsException ex)
            {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }

        else
        {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_importMenuItemActionPerformed

    private void exportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportMenuItemActionPerformed
        try {
            Dataset dataset = Kernel.GetService(DataManager.class).getDataset((String) relationModel.getElementAt(relationView.getSelectedIndex()));
            int returnVal = exportTableChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File file = exportTableChooser.getSelectedFile();
                try
                {
                    FileWriter fw = new FileWriter(Utility.addExtension(
                            file.getAbsoluteFile().getAbsolutePath(), ".csv"), true);
                    if(dataset != null)
                    {
                        fw.write(dataset.getCSV());
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(
                                rootPane,"problem saving file at " + file.getAbsolutePath());
                    }
                    fw.close();
                }
                catch (IOException ex)
                {
                    JOptionPane.showMessageDialog(
                            rootPane,"Problem saving file at " + file.getAbsolutePath());
                }
            }
            else
            {
                System.out.println("File save cancelled by user.");
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(rootPane,"Please select a table to export.");
        }
    }//GEN-LAST:event_exportMenuItemActionPerformed

    private void arrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arrowButtonActionPerformed
        insertSymbol("→");
    }//GEN-LAST:event_arrowButtonActionPerformed

    private void newSheetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newSheetButtonActionPerformed
        String str = (String) JOptionPane.showInputDialog(rootPane, "Please enter the name for the worksheet.", "Worksheet", JOptionPane.PLAIN_MESSAGE);

        if(str == null)
        {
            //the user pressed exit
        }

        else if(str.isEmpty())
        {
            JOptionPane.showMessageDialog(rootPane, "Can't accept empty name!");
        }

        else
        {
            boolean foundDup = false;
            //we must check if a tab has the same name.
            for(int i = 0; i < worksheetPane.getTabCount(); ++i)
            {
                if(worksheetPane.getTitleAt(i).equals(str))
                {
                    foundDup = true;
                }
            }

            //if we found the duplicate
            if(foundDup)
            {
                JOptionPane.showMessageDialog(rootPane, "Worksheet name already exists!");
            }

            else
            {
                TextQueryView t = new TextQueryView(str);
                worksheetPane.addTab(str, t);
            }
        }

    }//GEN-LAST:event_newSheetButtonActionPerformed

    private void removeSheetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSheetButtonActionPerformed

        //if the tabbedpane is empty no point in asking
        if (worksheetPane.getTabCount() != 0)
        {
            int n = JOptionPane.showConfirmDialog(
                    rootPane,
                    "Are you sure that you want to remove the current worksheet?",
                    "remove",
                    JOptionPane.YES_NO_OPTION);

            //yes
            if (n == 0)
            {
                int index = worksheetPane.getSelectedIndex();
                worksheetPane.removeTabAt(index);
            }

            //no
            else
            {
            }
        }
        else
        {
            JOptionPane.showMessageDialog(rootPane, "No worksheets to remove.");
        }
    }//GEN-LAST:event_removeSheetButtonActionPerformed

    private void relationViewMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_relationViewMouseClicked
    {//GEN-HEADEREND:event_relationViewMouseClicked
        int index = relationView.getSelectedIndex();
        String str = (String) relationModel.getElementAt(index);

        if (str != null)
        {

            Dataset dataset = Kernel.GetService(DataManager.class).getDataset(str);

            if(dataset != null)
            {
                tableView.setModel(dataset);
                //focus it for cool effect
                //1 is table view.
                combinedView.setSelectedIndex(1);
            }

            else
            {
                System.out.println("couldn't find " + str);
            }

        }

    }//GEN-LAST:event_relationViewMouseClicked

    private void deleteMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_deleteMenuItemActionPerformed
    {//GEN-HEADEREND:event_deleteMenuItemActionPerformed

        int index = relationView.getSelectedIndex();
        String str = "";

        if(index == -1)
        {
            JOptionPane.showMessageDialog(rootPane, "Please select a table to delete.");
        }

        else
        {
            str = (String)relationModel.getElementAt(index);
        }


        if(!str.isEmpty())
        {
            relationModel.remove(relationView.getSelectedIndex());
            TextQueryView.removeTableAutoWords(str);

            try
            {
                Kernel.GetService(DataManager.class).removeDataset(str);
            }
            catch (NoSuchDataset ex)
            {
                JOptionPane.showMessageDialog(rootPane, ex.getMessage());
            }
        }
    }//GEN-LAST:event_deleteMenuItemActionPerformed

    private void deltaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deltaButtonActionPerformed
        insertSymbol("δ");
    }//GEN-LAST:event_deltaButtonActionPerformed

    private void showTreeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_showTreeButtonActionPerformed
    {//GEN-HEADEREND:event_showTreeButtonActionPerformed
        try
        {
            Dataset dataset = (Dataset)getCurrentQueryTable().getModel();
            TreeWindow window = new TreeWindow(Kernel.GetService(LocalDataManager.class).findOperation(dataset.getName()));
            window.setVisible(true);
        }
        catch(ClassCastException e)
        {
            JOptionPane.showMessageDialog(rootPane, "No dataset available. Please run a query first.");
        }
    }//GEN-LAST:event_showTreeButtonActionPerformed

    private void helpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpMenuItemActionPerformed
        HelpWindow window = new HelpWindow();
        window.setVisible(true);
    }//GEN-LAST:event_helpMenuItemActionPerformed

    private void saveScriptMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveScriptMenuItemActionPerformed
        TextQueryView view = (TextQueryView)worksheetPane.getSelectedComponent();
        if(view == null || view.getTextArea().getText().equals(""))
        {
            JOptionPane.showMessageDialog(rootPane, "Nothing to save.");
        }
        else
        {
            int returnVal = saveScriptChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
                File file = saveScriptChooser.getSelectedFile();
               try
                {
                    FileWriter fw = new FileWriter(Utility.addExtension(file.getAbsoluteFile().getAbsolutePath(), ".txt"), true);
                    fw.write(view.getTextArea().getText());
                    fw.close();
                }
                catch (IOException ex)
                {
                    JOptionPane.showMessageDialog(rootPane, "Problem accessing file " + file.getAbsolutePath());
                }
            }
            else
            {
                System.out.println("File access cancelled by user.");
            }
        }
    }//GEN-LAST:event_saveScriptMenuItemActionPerformed

    private void loadScriptMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadScriptMenuItemActionPerformed
        int returnVal = loadScriptChooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = loadScriptChooser.getSelectedFile();
            String fileName = Utility.filename(file.getName());
            
            String s = (String)JOptionPane.showInputDialog(
                    loadScriptChooser, "Please choose a name for the new worksheet:", "Worksheet name", JOptionPane.PLAIN_MESSAGE, null, null, fileName);

            boolean foundDup = false;
            for(int i=0; i < worksheetPane.getTabCount(); i++)
            {
                if(worksheetPane.getTitleAt(i).equals(s))
                {
                    foundDup = true;
                }
            }

            if(s == null)
            {
                //user pressed x
            }

            else if ((s.length() > 0) && foundDup == false)
            {
                String content = null;

                try
                {
                    FileReader reader = new FileReader(file);
                    char[] chars = new char[(int) file.length()];
                    reader.read(chars);
                    content = new String(chars);
                    reader.close();
                    
                    TextQueryView t = new TextQueryView(s);
                    worksheetPane.addTab(s, t);
                    worksheetPane.setSelectedComponent(t);
                    JTextArea area = getCurrentWorksheet();
                    area.setText(content);
                }
                catch (IOException ex)
                {
                    JOptionPane.showMessageDialog(rootPane, "Problem accessing file " + file.getAbsolutePath());
                }               
            }
            else
            {
                JOptionPane.showMessageDialog(loadScriptChooser, "Worksheet name already exists!");
                loadScriptMenuItemActionPerformed(evt);
            }


        }
        else
        {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_loadScriptMenuItemActionPerformed

    private void saveOnExit(java.awt.event.ActionEvent evt) {
    if(worksheetPane.getTabCount() == 0 && Kernel.GetService(DataManager.class).GetAllDatasets().isEmpty())
        {
            Kernel.Stop();
            System.exit(0);
        }
        else
        {
            // Mostly taken from the java dialog tutorial
            final JOptionPane optionPane = new JOptionPane(
                "There are active worksheets and/or tables.\n"
                + "Any unsaved changes will be lost.\n"
                + "Do you want to save your progress first?",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_CANCEL_OPTION);
            final JDialog dialog = new JDialog(this, "Save?", true);
            
           dialog.setContentPane(optionPane);
           optionPane.addPropertyChangeListener(
           new PropertyChangeListener()
           {
               @Override
               public void propertyChange(PropertyChangeEvent e)
               {
                   String prop = e.getPropertyName();
                   if (dialog.isVisible() && (e.getSource() == optionPane) && (prop.equals(JOptionPane.VALUE_PROPERTY)))
                   {
                       //If you were going to check something
                       //before closing the window, you'd do
                       //it here.
                       dialog.setVisible(false);
                   }
               }
           });
           dialog.pack();
           dialog.setLocationRelativeTo(null);
           dialog.setVisible(true);

           int value = ((Integer)optionPane.getValue()).intValue();
           if (value == JOptionPane.YES_OPTION)
           {
               saveProjectMenuItemActionPerformed(evt);
               Kernel.Stop();
               System.exit(0);
           }
           else if (value == JOptionPane.NO_OPTION)
           {
               Kernel.Stop();
               System.exit(0);
           }
           else if (value == JOptionPane.CANCEL_OPTION)
           {
               // Do nothing
           }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JButton arrowButton;
    private javax.swing.JTabbedPane combinedView;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JButton deltaButton;
    private javax.swing.JButton differenceButton;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenuItem exportMenuItem;
    private javax.swing.JFileChooser exportTableChooser;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JButton fullouterjoinButton;
    private javax.swing.JButton gammaButton;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem helpMenuItem;
    private javax.swing.JMenuItem importMenuItem;
    private javax.swing.JFileChooser importTableChooser;
    private javax.swing.JButton intersectionButton;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JButton joinButton;
    private javax.swing.JButton leftouterjoinButton;
    private javax.swing.JFileChooser loadProjectChooser;
    private javax.swing.JMenuItem loadProjectMenuItem;
    private javax.swing.JFileChooser loadScriptChooser;
    private javax.swing.JMenuItem loadScriptMenuItem;
    private javax.swing.JButton newSheetButton;
    private javax.swing.JButton piButton;
    private javax.swing.JButton productButton;
    private javax.swing.JTable queryTable;
    private javax.swing.JTabbedPane queryView;
    private javax.swing.JList relationView;
    private javax.swing.JButton removeSheetButton;
    private javax.swing.JButton rhoButton;
    private javax.swing.JButton rightouterjoinButton;
    private javax.swing.JButton runButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JFileChooser saveProjectChooser;
    private javax.swing.JMenuItem saveProjectMenuItem;
    private javax.swing.JFileChooser saveScriptChooser;
    private javax.swing.JMenuItem saveScriptMenuItem;
    private javax.swing.JButton showTreeButton;
    private javax.swing.JButton sigmaButton;
    private javax.swing.JTable tableView;
    private javax.swing.JButton tauButton;
    private javax.swing.JButton unionButton;
    private javax.swing.JTabbedPane worksheetPane;
    // End of variables declaration//GEN-END:variables
}
