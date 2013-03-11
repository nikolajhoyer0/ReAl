package real.Objects.Services;

import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import real.Enumerations.OpTypes;
import real.Interfaces.IService;
import real.Objects.Dataset;
import real.Objects.Exceptions.DatasetDuplicate;
import real.Objects.Exceptions.InvalidDataset;
import real.Objects.Exceptions.InvalidParsing;
import real.Objects.Exceptions.NoSuchDataset;
import real.Objects.GUI.TextQueryView;
import real.Objects.Kernel;
import real.Objects.Parser.ExpressionParser;
import real.Objects.Parser.Token;
import real.Objects.Parser.TokenOpManager;
import real.Objects.Parser.TokenStream;
import real.Objects.Parser.TokenTree;
import real.Objects.TreeView;

public class MainWindow extends javax.swing.JFrame implements IService
{

    private ExpressionParser parser;
    private TreeView view;
    private DefaultListModel relationModel = new DefaultListModel();
    
    public MainWindow()
    {
        this.initComponents();

        // Setup listener so closing the window will close the kernel.
        this.addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent winEvt)
            {
                Kernel.Stop();
            }
        });
    }

    //will always return non null, since we can not have a empty worksheet
    public JTextArea getCurrentWorksheet()
    {
        TextQueryView view = (TextQueryView)worksheetPane.getSelectedComponent(); 
        return view.getTextArea();
    }
    
    @Override
    public void Initialize()
    {
        ImageIcon image = new ImageIcon("assets/icon/icon.png");
        this.setIconImage(image.getImage());

        relationView.setModel(relationModel);
        
        view = new TreeView();
        view.setSize(800, 820);
        view.setVisible(true);

        //testing
        //code will be removed when we get the query model done
        TokenOpManager opManager = new TokenOpManager();

        opManager.addOp(new Token("+", 4, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("-", 4, EnumSet.of(OpTypes.LEFT, OpTypes.UNARY)));
        opManager.addOp(new Token("*", 6, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("/", 6, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("=", 2, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("<=", 2, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token(">=", 2, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("<", 2, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token(">", 2, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("AND", 1, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("OR", 0, EnumSet.of(OpTypes.LEFT)));
        opManager.addOp(new Token("^", 9, EnumSet.of(OpTypes.RIGHT)));
        
        //function operators
        opManager.addOp(new Token("π", 0, EnumSet.of(OpTypes.NONE)));  
        opManager.addOp(new Token("δ", 0, EnumSet.of(OpTypes.NONE)));   
        opManager.addOp(new Token("ρ", 0, EnumSet.of(OpTypes.NONE)));   
        opManager.addOp(new Token("γ", 0, EnumSet.of(OpTypes.NONE))); 
        opManager.addOp(new Token("τ", 0, EnumSet.of(OpTypes.NONE)));
        
        //relational binary operators      
        //todo: figure out the proper precendence for each operator.
        opManager.addOp(new Token("∪", 6, EnumSet.of(OpTypes.LEFT)));   
        opManager.addOp(new Token("∩", 6, EnumSet.of(OpTypes.LEFT)));   
        opManager.addOp(new Token("‒", 6, EnumSet.of(OpTypes.LEFT)));   
        opManager.addOp(new Token("×", 6, EnumSet.of(OpTypes.LEFT)));   
        opManager.addOp(new Token("⋈", 6, EnumSet.of(OpTypes.LEFT)));   
        opManager.addOp(new Token("→", 2, EnumSet.of(OpTypes.LEFT))); 
        opManager.addOp(new Token("⟕", 6, EnumSet.of(OpTypes.LEFT)));   
        opManager.addOp(new Token("⟖", 6, EnumSet.of(OpTypes.LEFT)));   
        opManager.addOp(new Token("⟗", 6, EnumSet.of(OpTypes.LEFT))); 
        
        TokenStream tokenStream = new TokenStream(opManager);

        parser = new ExpressionParser(tokenStream);
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

    public void setup(Dataset ds)
    {
        this.jTable1.setModel(ds);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        loadFileChooser = new javax.swing.JFileChooser();
        saveFileChooser = new javax.swing.JFileChooser();
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
        jTable1 = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        saveButton = new javax.swing.JButton();
        jToolBar3 = new javax.swing.JToolBar();
        piButton = new javax.swing.JButton();
        deltaButton = new javax.swing.JButton();
        rhoButton = new javax.swing.JButton();
        gammaButton = new javax.swing.JButton();
        tauButton = new javax.swing.JButton();
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
        loadMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        importMenuItem = new javax.swing.JMenuItem();
        exportMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        loadFileChooser.setDialogTitle("Load script");

        saveFileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        saveFileChooser.setDialogTitle("Save script");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ReAl");
        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);
        jToolBar2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        runButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        runButton.setText("Run");
        runButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                runButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(runButton);
        jToolBar2.add(jSeparator2);

        newSheetButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        newSheetButton.setText("New sheet");
        newSheetButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
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
        removeSheetButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                removeSheetButtonActionPerformed(evt);
            }
        });
        jToolBar2.add(removeSheetButton);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(jTable1);

        queryView.addTab("Query result", jScrollPane1);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        saveButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                saveButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(saveButton);

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);

        piButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        piButton.setText("π");
        piButton.setFocusable(false);
        piButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        piButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        piButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                piButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(piButton);

        deltaButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        deltaButton.setText("δ");
        deltaButton.setFocusable(false);
        deltaButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deltaButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        deltaButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                deltaButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(deltaButton);

        rhoButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        rhoButton.setText("ρ");
        rhoButton.setFocusable(false);
        rhoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rhoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rhoButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                rhoButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(rhoButton);

        gammaButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        gammaButton.setText("γ");
        gammaButton.setFocusable(false);
        gammaButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gammaButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gammaButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                gammaButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(gammaButton);

        tauButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        tauButton.setText("τ");
        tauButton.setFocusable(false);
        tauButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        tauButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tauButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                tauButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(tauButton);

        arrowButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        arrowButton.setText("→");
        arrowButton.setFocusable(false);
        arrowButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        arrowButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        arrowButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                arrowButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(arrowButton);

        unionButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        unionButton.setText("∪");
        unionButton.setFocusable(false);
        unionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        unionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        unionButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                unionButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(unionButton);

        intersectionButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        intersectionButton.setText("∩");
        intersectionButton.setFocusable(false);
        intersectionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        intersectionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        intersectionButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                intersectionButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(intersectionButton);

        differenceButton.setFont(new java.awt.Font("Cambria", 0, 24)); // NOI18N
        differenceButton.setText("‒");
        differenceButton.setFocusable(false);
        differenceButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        differenceButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        differenceButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                differenceButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(differenceButton);

        productButton.setFont(new java.awt.Font("Cambria", 0, 24)); // NOI18N
        productButton.setText("×");
        productButton.setFocusable(false);
        productButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        productButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        productButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                productButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(productButton);

        joinButton.setFont(new java.awt.Font("Cambria", 0, 24)); // NOI18N
        joinButton.setText("⋈");
        joinButton.setFocusable(false);
        joinButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        joinButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        joinButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                joinButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(joinButton);

        leftouterjoinButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        leftouterjoinButton.setText("⟕");
        leftouterjoinButton.setFocusable(false);
        leftouterjoinButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        leftouterjoinButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        leftouterjoinButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                leftouterjoinButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(leftouterjoinButton);

        rightouterjoinButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        rightouterjoinButton.setText("⟖");
        rightouterjoinButton.setFocusable(false);
        rightouterjoinButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rightouterjoinButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rightouterjoinButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                rightouterjoinButtonActionPerformed(evt);
            }
        });
        jToolBar3.add(rightouterjoinButton);

        fullouterjoinButton.setFont(new java.awt.Font("Cambria", 0, 20)); // NOI18N
        fullouterjoinButton.setText("⟗");
        fullouterjoinButton.setFocusable(false);
        fullouterjoinButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        fullouterjoinButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        fullouterjoinButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
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
                .addComponent(worksheetPane, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(queryView, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        combinedView.addTab("Query view", jPanel1);

        tableView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
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
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
        );

        combinedView.addTab("Table view", jPanel2);

        relationView.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                relationViewMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(relationView);

        fileMenu.setText("File");

        loadMenuItem.setText("Load Project");
        loadMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                loadMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(loadMenuItem);

        saveMenuItem.setText("Save Project");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);

        importMenuItem.setText("Import table");
        importMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                importMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(importMenuItem);

        exportMenuItem.setText("Export table");
        exportMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                exportMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exportMenuItem);

        deleteMenuItem.setText("Delete table");
        deleteMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                deleteMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(deleteMenuItem);
        fileMenu.add(jSeparator1);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        jMenuBar1.add(fileMenu);

        editMenu.setText("Edit");
        jMenuBar1.add(editMenu);

        helpMenu.setText("Help");

        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

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

    }//GEN-LAST:event_saveButtonActionPerformed

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_runButtonActionPerformed
    {//GEN-HEADEREND:event_runButtonActionPerformed
        // TODO add your handling code here:
        try
        {
            TokenTree tree = parser.parse(this.getCurrentWorksheet().getText()).pop();
            view.load(tree);
        }
        catch (InvalidParsing ex)
        {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_runButtonActionPerformed

    private void piButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_piButtonActionPerformed
        JTextArea area = getCurrentWorksheet();
        area.insert("π", area.getCaretPosition());
    }//GEN-LAST:event_piButtonActionPerformed

    private void deltaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deltaButtonActionPerformed
        JTextArea area = getCurrentWorksheet();
        area.insert("δ", area.getCaretPosition());
    }//GEN-LAST:event_deltaButtonActionPerformed

    private void rhoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rhoButtonActionPerformed
        JTextArea area = getCurrentWorksheet();
        area.insert("ρ", area.getCaretPosition());
    }//GEN-LAST:event_rhoButtonActionPerformed

    private void gammaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gammaButtonActionPerformed
        JTextArea area = getCurrentWorksheet();
        area.insert("γ", area.getCaretPosition());
    }//GEN-LAST:event_gammaButtonActionPerformed

    private void tauButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tauButtonActionPerformed
        JTextArea area = getCurrentWorksheet();
        area.insert("τ", area.getCaretPosition());
    }//GEN-LAST:event_tauButtonActionPerformed

    private void unionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unionButtonActionPerformed
        JTextArea area = getCurrentWorksheet();
        area.insert("∪", area.getCaretPosition());
    }//GEN-LAST:event_unionButtonActionPerformed

    private void intersectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intersectionButtonActionPerformed
        JTextArea area = getCurrentWorksheet();
        area.insert("∩", area.getCaretPosition());
    }//GEN-LAST:event_intersectionButtonActionPerformed

    private void differenceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_differenceButtonActionPerformed
        JTextArea area = getCurrentWorksheet();
        area.insert("‒", area.getCaretPosition());
    }//GEN-LAST:event_differenceButtonActionPerformed

    private void productButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productButtonActionPerformed
        JTextArea area = getCurrentWorksheet();
        area.insert("×", area.getCaretPosition());
    }//GEN-LAST:event_productButtonActionPerformed

    private void joinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinButtonActionPerformed
        JTextArea area = getCurrentWorksheet();
        area.insert("⋈", area.getCaretPosition());
    }//GEN-LAST:event_joinButtonActionPerformed

    private void leftouterjoinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftouterjoinButtonActionPerformed
        JTextArea area = getCurrentWorksheet();
        area.insert("⟕", area.getCaretPosition());
    }//GEN-LAST:event_leftouterjoinButtonActionPerformed

    private void rightouterjoinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightouterjoinButtonActionPerformed
        JTextArea area = getCurrentWorksheet();
        area.insert("⟖", area.getCaretPosition());
    }//GEN-LAST:event_rightouterjoinButtonActionPerformed

    private void fullouterjoinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullouterjoinButtonActionPerformed
        JTextArea area = getCurrentWorksheet();
        area.insert("⟗", area.getCaretPosition());
    }//GEN-LAST:event_fullouterjoinButtonActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        Kernel.Stop();
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(rootPane, "This is ReAl ver. 1E-10 \n\n"
                + "Authors: \nDaniel Gavin \nTobias Kristoffer Scavenius \nNikolaj Høyer", "About", WIDTH);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        int returnVal = saveFileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = saveFileChooser.getSelectedFile();
            try {
                FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                this.getCurrentWorksheet().write(fw);
            }
            catch (IOException ex) {
                System.out.println("Problem saving file at " + file.getAbsolutePath());
            }
        }
        else {
            System.out.println("File save cancelled by user.");
        }
    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void loadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMenuItemActionPerformed
        int returnVal = loadFileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = loadFileChooser.getSelectedFile();
            try
            {
                this.getCurrentWorksheet().read(new FileReader(file.getAbsolutePath()), null);
            }
            catch (IOException ex)
            {
                System.out.println("Problem accessing file " + file.getAbsolutePath());
            }
        }
        else
        {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_loadMenuItemActionPerformed

    private void importMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importMenuItemActionPerformed
        //todo: need to write my own filechooser - not use the save and load from script
        int returnVal = loadFileChooser.showOpenDialog(this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = loadFileChooser.getSelectedFile();
            try
            {
                //ask for the table name
                String str = (String) JOptionPane.showInputDialog(rootPane, 
                            "Please enter the name for the table.", "Table", JOptionPane.PLAIN_MESSAGE);
                
                if(str == null)
                {
                    //user pressed cancel
                }
                
                else if(str.isEmpty())
                {
                    JOptionPane.showMessageDialog(rootPane, "Table name can't be empty");
                }
                
                else
                {
                    Kernel.GetService(DataManager.class).LoadDataset(file.getAbsolutePath(), str);
                    relationModel.addElement(str);
                    //add the word completions for this table
                    TextQueryView.addTableAutoWords(str);
                }
            }
            catch (InvalidDataset ex)
            {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (DatasetDuplicate ex)
            {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else
        {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_importMenuItemActionPerformed

    private void exportMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportMenuItemActionPerformed
        //todo: need to write my own filechooser - not use the save and load from script
        int returnVal = saveFileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = saveFileChooser.getSelectedFile();
            try
            {
                FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                Dataset dataset = Kernel.GetService(DataManager.class).getDataset((String) relationModel.getElementAt(relationView.getSelectedIndex()));
                fw.write(dataset.getCSV());
                fw.flush();
            }
            catch (IOException ex)
            {
                System.out.println("Problem saving file at " + file.getAbsolutePath());
            }
            catch (NoSuchDataset ex)
            {
            }
        }
        else
        {
            System.out.println("File save cancelled by user.");
        }
    }//GEN-LAST:event_exportMenuItemActionPerformed

    private void arrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arrowButtonActionPerformed
        JTextArea area = getCurrentWorksheet();
        area.insert("→", area.getCaretPosition());
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
                TextQueryView t = new TextQueryView();
                worksheetPane.addTab(str, t);
            }
        }

    }//GEN-LAST:event_newSheetButtonActionPerformed

    private void removeSheetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSheetButtonActionPerformed
      
        //if the tabbedpane is empty no point in asking
        //todo: we have to discuss if we want to stop removing when there is one tab left
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
    }//GEN-LAST:event_removeSheetButtonActionPerformed

    private void relationViewMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_relationViewMouseClicked
    {//GEN-HEADEREND:event_relationViewMouseClicked
        //if the user double clicks.
        if (evt.getClickCount() == 2)
        {
            int index = relationView.getSelectedIndex();
            String str = (String) relationModel.getElementAt(index);

            if (str != null)
            {
                try
                {
                    Dataset dataset = Kernel.GetService(DataManager.class).getDataset(str);
                    tableView.setModel(dataset);
                    //focus it for cool effect
                    //1 is table view.
                    combinedView.setSelectedIndex(1);
                }
                catch (NoSuchDataset ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_relationViewMouseClicked

    private void deleteMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_deleteMenuItemActionPerformed
    {//GEN-HEADEREND:event_deleteMenuItemActionPerformed
        String str = (String)relationModel.getElementAt(relationView.getSelectedIndex());
        
        if(str != null)
        {
            relationModel.remove(relationView.getSelectedIndex());

            try
            {
                Kernel.GetService(DataManager.class).removeDataset(str);
            }
            catch (NoSuchDataset ex)
            {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_deleteMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JButton arrowButton;
    private javax.swing.JTabbedPane combinedView;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JButton deltaButton;
    private javax.swing.JButton differenceButton;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenuItem exportMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JButton fullouterjoinButton;
    private javax.swing.JButton gammaButton;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem importMenuItem;
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
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JButton joinButton;
    private javax.swing.JButton leftouterjoinButton;
    private javax.swing.JFileChooser loadFileChooser;
    private javax.swing.JMenuItem loadMenuItem;
    private javax.swing.JButton newSheetButton;
    private javax.swing.JButton piButton;
    private javax.swing.JButton productButton;
    private javax.swing.JTabbedPane queryView;
    private javax.swing.JList relationView;
    private javax.swing.JButton removeSheetButton;
    private javax.swing.JButton rhoButton;
    private javax.swing.JButton rightouterjoinButton;
    private javax.swing.JButton runButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JFileChooser saveFileChooser;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JTable tableView;
    private javax.swing.JButton tauButton;
    private javax.swing.JButton unionButton;
    private javax.swing.JTabbedPane worksheetPane;
    // End of variables declaration//GEN-END:variables
}
