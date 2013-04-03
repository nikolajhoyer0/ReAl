package real.Objects.GUI;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxGraph;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import real.BaseClasses.BinaryOperationBase;
import real.BaseClasses.OperationBase;
import real.BaseClasses.UnaryOperationBase;
import real.Objects.Exceptions.InvalidEvaluation;
import real.Objects.Exceptions.InvalidParameters;
import real.Objects.Exceptions.InvalidSchema;
import real.Objects.Exceptions.NoSuchAttribute;
import real.Objects.RAOperations.ReferencedDataset;

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
            @Override
            public boolean isCellEditable(Object cell)
            {
                return !getModel().isEdge(cell);
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
                    return null;
                }
            }
        };
        
        graph.setCellsLocked(false);
        graph.setAllowDanglingEdges(false);
        graph.setConnectableEdges(false);   
        graph.setAutoSizeCells(true);
        graph.setCellsResizable(true);
        graph.setLabelsClipped(false);

        
        HashMap<String, Object> style = new HashMap<>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style.put(mxConstants.STYLE_ROUNDED, true);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        style.put(mxConstants.STYLE_FONTFAMILY, "cambria");
        style.put(mxConstants.STYLE_FONTSIZE, 15);

        graph.getStylesheet().putCellStyle("DEFAULT", style);
       
        graphComponent = new mxGraphComponent(graph);  
        
        
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
                        treeWindow.getTableView().setModel(base.execute());
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
        }
        finally
        { 
            mxICell cell = (mxICell)(graph.getModel()).getChildAt(parent, 0);
            
            graph.getView().setTranslate(new mxPoint(this.getWidth() / 2 - cell.getGeometry().getWidth(), 10));
            graph.updateCellSize(parent, false);           
            graph.getModel().endUpdate();
        }    
        
        
    }
 
    public void drawImage(File file) throws AWTException, IOException
    {
        BufferedImage image = new Robot().createScreenCapture(new Rectangle(this.getLocationOnScreen().x, this.getLocationOnScreen().y, this.getWidth(), this.getHeight()));
        ImageIO.write(image, "png", file);
    }
    
    private void traverseTree(OperationBase tree, Object parent, Object lastNode, int y)
    {
  
        if(tree != null)
        {
            if(tree instanceof UnaryOperationBase)
            {
                UnaryOperationBase unary = (UnaryOperationBase) tree;          
                
                Object v2 = graph.insertVertex(parent, null, unary, 30, 2, 115, 40, "DEFAULT");
                
                if(lastNode != null)
                {
                    graph.insertEdge(parent, null, null, lastNode,v2);
                } 
                
                traverseTree(unary.getOperand(), parent, v2, 0);
            }
            
            //end leafs
            else if(tree instanceof ReferencedDataset)
            {                      
                Object v2 = graph.insertVertex(parent, null, tree, 3, 2, 115, 40, "DEFAULT;fillColor=yellow");

                if(lastNode != null)
                {
                    graph.insertEdge(parent, null, null, lastNode,v2);
                }                
            }
           
            else
            {
                BinaryOperationBase binary = (BinaryOperationBase) tree;
                
                Object v2 = graph.insertVertex(parent, null, binary, 5, 6, 115, 40, "DEFAULT");
                 
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
