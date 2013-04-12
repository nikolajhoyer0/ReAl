package real.Objects.GUI;

import java.io.FileReader;
import java.io.IOException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class HelpWindow extends javax.swing.JFrame {

    public HelpWindow() {
        initComponents();
        initTree();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tree = new javax.swing.JTree();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        textArea.setColumns(20);
        textArea.setLineWrap(true);
        textArea.setRows(5);
        textArea.setWrapStyleWord(true);
        jScrollPane2.setViewportView(textArea);

        jScrollPane1.setViewportView(tree);

        jScrollPane3.setViewportView(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Help");

        DefaultMutableTreeNode features = new DefaultMutableTreeNode("Main features");
        features.add(new DefaultMutableTreeNode("Handling datasets"));
        root.add(features);

        DefaultMutableTreeNode operations = new DefaultMutableTreeNode("Operations");
        operations.add(new DefaultMutableTreeNode("Projection"));
        operations.add(new DefaultMutableTreeNode("Selection"));
        operations.add(new DefaultMutableTreeNode("Rename"));
        operations.add(new DefaultMutableTreeNode("Group"));
        operations.add(new DefaultMutableTreeNode("Sort"));
        operations.add(new DefaultMutableTreeNode("Remove duplicates"));
        operations.add(new DefaultMutableTreeNode("Arrow"));
        operations.add(new DefaultMutableTreeNode("Union"));
        operations.add(new DefaultMutableTreeNode("Intersection"));
        operations.add(new DefaultMutableTreeNode("Difference"));
        operations.add(new DefaultMutableTreeNode("Product"));
        operations.add(new DefaultMutableTreeNode("Natural join"));
        root.add(operations);

        tree.setModel(new DefaultTreeModel(root));
        tree.addTreeSelectionListener(new TreeSelectionListener()
        {
            @Override
            public void valueChanged(TreeSelectionEvent e)
            {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        tree.getLastSelectedPathComponent();
                loadText(node.toString());
            }
        });
    }

    private void loadText(String item) {
        try
        {
            FileReader reader = new FileReader("assets/helpfiles/english/" + item + ".txt");
            textArea.setText("");
            textArea.read(reader, null);
        }
        catch (IOException ex)
        {
            System.out.println("Help window: file not found");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea textArea;
    private javax.swing.JTree tree;
    // End of variables declaration//GEN-END:variables
}
