package real.Objects.GUI;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private ArrayList<OperationBase> operands;
    private TreeWindow treeWindow;
    
    public TreeView(TreeWindow parent)
    {
        super();
        
        this.treeWindow = parent;      
        operands = new ArrayList<>();
        
        this.setLayout(new BorderLayout());
        
        
        graph = new mxGraph();
        graph.setCellsLocked(false);
        graph.setAllowDanglingEdges(false);
        graph.setConnectableEdges(false);   

        mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);  
        layout.execute(graph.getDefaultParent());
        graphComponent = new mxGraphComponent(graph);
  
        graphComponent.addMouseWheelListener(new MouseWheelListener()
        {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e)
            {
                int steps = e.getWheelRotation();

                if(steps < 0)
                {
                    graphComponent.zoomOut();
                }
                
                else
                {
                    graphComponent.zoomIn();                    
                }

            }
        });
        
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
                        System.out.println(cell.getId());
                        OperationBase base = operands.get(Integer.parseInt(cell.getId()));
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
        operands.clear();
        
        Object parent = graph.getDefaultParent();

        graph.removeCells(graph.getChildVertices(graph.getDefaultParent()));
        
        graph.getModel().beginUpdate();
        try
        {     
            traverseTree(base, parent, null, 0);
            mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);  
            layout.execute(graph.getDefaultParent());
        }
        finally
        {
            graph.getModel().endUpdate();
        }    
        
        
    }
 
    private void traverseTree(OperationBase tree, Object parent, Object lastNode, int y)
    {
  
        if(tree != null)
        {
            if(tree instanceof UnaryOperationBase)
            {
                UnaryOperationBase unary = (UnaryOperationBase) tree;
                
                int id = operands.size();
                operands.add(unary);
                
                Object v2 = graph.insertVertex(parent, String.valueOf(id), unary.toString(), 3, 2, 100, 40);
                
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
                
                int id = operands.size();
                operands.add(ref);

                Object v2 = graph.insertVertex(parent, String.valueOf(id), tree.toString(), 3, 2, 100, 40, "fillColor=yellow");

                if(lastNode != null)
                {
                    graph.insertEdge(parent, null, null, lastNode,v2);
                }                
            }
           
            else
            {
                BinaryOperationBase binary = (BinaryOperationBase) tree;
                
                int id = operands.size();
                operands.add(binary);

                Object v2 = graph.insertVertex(parent, String.valueOf(id), binary.toString(), 5, 6, 100, 40);
                 
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
