package real.Objects.GUI;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxGraph;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import real.BaseClasses.BinaryOperationBase;
import real.BaseClasses.OperationBase;
import real.BaseClasses.UnaryOperationBase;
import real.Objects.Dataset;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchAttribute;
import real.Objects.RAOperations.ReferencedDataset;
import real.Objects.RAOperations.TupleList;

public class TreeView extends JPanel {
    
    private mxGraph graph;
    private mxGraphComponent graphComponent;
    
    public TreeView()
    {
        super();
            
        this.setLayout(new BorderLayout());       
        this.setFont(new Font("cambria", Font.PLAIN, 15));
        
        graph = new mxGraph()
        {
            //no editing.
            @Override
            public boolean isCellEditable(Object cell)
            {
                return false;
            }

            @Override
            public String convertValueToString(Object cell)
            {
                Object value = ((mxICell) cell).getValue();
                
                if(value != null)
                {
                    return value.toString();
                }
                
                else
                {
                    return "";
                }
            }         
        };
        
        graph.setAllowDanglingEdges(false);
        graph.setConnectableEdges(false);   
        graph.setAutoSizeCells(true);
        graph.setCellsResizable(true);
        graph.setLabelsClipped(false);
        graph.setDropEnabled(false);
        
        HashMap<String, Object> style = new HashMap<>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style.put(mxConstants.STYLE_ROUNDED, true);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        style.put(mxConstants.STYLE_FONTFAMILY, "cambria");
        style.put(mxConstants.STYLE_FONTSIZE, 15);

        graph.getStylesheet().putCellStyle("DEFAULT", style);
       
        graphComponent = new mxGraphComponent(graph);  
        graphComponent.setAntiAlias(true);
        graphComponent.setDragEnabled(false);
        graphComponent.setTextAntiAlias(true);
        graphComponent.setConnectable(false);
        graph.setCellsEditable(false);
    }

    public void initialize(final TreeWindow treeWindow)
    {
        graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                mxICell cell = (mxICell)graphComponent.getCellAt(e.getX(), e.getY());
                
                if (cell != null)
                {
                    try
                    {
                        OperationBase base = (OperationBase)cell.getValue();
                        
                        if(base instanceof TupleList)
                        {
                            TupleList tupleList = (TupleList)base;
                            Dataset dependency = tupleList.getDependency().execute();
                            
                            treeWindow.getTableView().setModel(TupleList.createDataset(tupleList, dependency));
                        }
                        
                        else
                        {
                            treeWindow.getTableView().setModel(base.execute());
                        }
                    }
                    catch (InvalidSchema ex)
                    {
                        Logger.getLogger(TreeView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch (NoSuchAttribute ex)
                    {
                        Logger.getLogger(TreeView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch (InvalidParameters ex)
                    {
                        Logger.getLogger(TreeView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch (InvalidEvaluation ex)
                    {
                        Logger.getLogger(TreeView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }); 
        
        add(graphComponent);
    }
    
    public void loadTree(OperationBase base)
    {   
        Object parent = graph.getDefaultParent();

        graph.removeCells(graph.getChildVertices(graph.getDefaultParent()));
        
        graph.getModel().beginUpdate();
        try
        {                
            traverseTree(base, parent, null, 0);
            mxCompactTreeLayout layout = new mxCompactTreeLayout(graph);
            layout.setHorizontal(false);
            layout.execute(parent);
            graph.setCellsLocked(true);
        }
        finally
        { 
            //mxICell cell = (mxICell)(graph.getModel()).getChildAt(parent, 0);
            //graph.getView().setTranslate(new mxPoint(this.getWidth() / 2 - cell.getGeometry().getWidth(), 10));                    
            graph.getModel().endUpdate();
        }    
        
        
    }
 
    public void drawImage(File file) throws AWTException, IOException
    {
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        
        BufferedImage image = mxCellRenderer.createBufferedImage(graph, null,               
                1, Color.WHITE, true, null);
        
        ImageIO.write(image, "png", out);
        
        out.close();
    }
    
    //sets width related to amount of chars.
    private int setWidth(String str)
    {
        int value = str.length() * 10;
        
        if(value < 125)
        {
            return 125;
        }
        
        return value;
    }
    
    private void traverseTree(OperationBase tree, Object parent, Object lastNode, int y)
    {

        if(tree != null)
        {
            if(tree instanceof UnaryOperationBase)
            {
                UnaryOperationBase unary = (UnaryOperationBase) tree;          
                
                Object v2 = graph.insertVertex(parent, null, unary, 30, 2, setWidth(tree.toString()), 40, "DEFAULT");
                
                if(lastNode != null)
                {
                    graph.insertEdge(parent, null, null, lastNode,v2);
                } 
                
                traverseTree(unary.getOperand(), parent, v2, 0);
            }
            
            //end leafs
            else if(tree instanceof ReferencedDataset)
            {                                
                ReferencedDataset ref = (ReferencedDataset)tree;
        
                if(ref.getOperand() != null)
                {
                    traverseTree(ref.getOperand(), parent, lastNode, 0);
                }
                
                else
                {
                    Object v2 = graph.insertVertex(parent, null, tree, 3, 2, setWidth(tree.toString()), 40, "DEFAULT;fillColor=yellow");
                 
                    if (lastNode != null)
                    {
                        graph.insertEdge(parent, null, null, lastNode, v2);
                    }
                }                                            
            }
           
            else if (tree instanceof TupleList) {
                Object v2 = graph.insertVertex(parent, null, tree, 3, 2, setWidth(tree.toString()), 40, "DEFAULT;fillColor=green");

                if (lastNode != null) {
                    graph.insertEdge(parent, null, null, lastNode, v2);
                }
            }
            
            else
            {
                BinaryOperationBase binary = (BinaryOperationBase) tree;
                
                Object v2 = graph.insertVertex(parent, null, binary, 5, 6, setWidth(tree.toString()), 40, "DEFAULT");
                 
                if(lastNode != null)
                {
                    graph.insertEdge(parent, null, null, lastNode,v2);
                } 
                
                traverseTree(binary.getOperandA(), parent, v2, 0);
                traverseTree(binary.getOperandB(), parent, v2, 0);
            }
            
        }

        
    }
}
