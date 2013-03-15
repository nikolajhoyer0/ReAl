package real.Objects.GUI;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.awt.BorderLayout;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;
import real.BaseClasses.BinaryOperationBase;
import real.BaseClasses.OperationBase;
import real.BaseClasses.UnaryOperationBase;
import real.Objects.RAOperations.ReferencedDataset;

public class TreeView extends JPanel {
    private mxGraph graph;
    private mxGraphComponent graphComponent;
    
    public TreeView()
    {
        super();
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
                
                Object v2 = graph.insertVertex(parent, null, unary.toString(), 3, 2, 90, 40);
                
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
                Object v2 = graph.insertVertex(parent, null, tree.toString(), 3, 2, 90, 40);
                
                if(lastNode != null)
                {
                    graph.insertEdge(parent, null, null, lastNode,v2);
                } 
                
                traverseTree(ref.getOperand(), parent, v2, 0);
            }
           
            else
            {
                BinaryOperationBase binary = (BinaryOperationBase) tree;
                
                Object v2 = graph.insertVertex(parent, null, binary.toString(), 5, 6, 90, 40);
  
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
