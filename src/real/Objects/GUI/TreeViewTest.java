package real.Objects.GUI;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import javax.swing.JFrame;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import real.Objects.Parser.TokenTree;

public class TreeViewTest extends JFrame
{

    /**
     *
     */
    private static final long serialVersionUID = -2707712944901661771L;

    private final mxGraph graph;
    
    public TreeViewTest()
    {
        super("Hello, World!");

        graph = new mxGraph();
        graph.setCellsLocked(false);
        graph.setAllowDanglingEdges(false);
        graph.setConnectableEdges(false); 
        
        mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);  
        layout.execute(graph.getDefaultParent());
        
        final mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);

        graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                Object cell = graphComponent.getCellAt(e.getX(), e.getY());

                
                if (cell != null)
                {
                    System.out.println("cell=" + graph.getLabel(cell));
                }
            }
        });      
    }
    
    public void load(TokenTree tree)
    {
        Object parent = graph.getDefaultParent();

        graph.removeCells(graph.getChildVertices(graph.getDefaultParent()));
        
        graph.getModel().beginUpdate();
        try
        {
            traverseTree(tree, parent, null, 0);
            mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);  
            layout.execute(graph.getDefaultParent());
        }
        finally
        {
            graph.getModel().endUpdate();
        }    
    }
    
    private void traverseTree(TokenTree tree, Object parent, Object thing, int y)
    {
        Object v1;
                        
        if(tree.getChildren() != null)
        {
            int x = 0;
            
            
            if(thing != null)
            {
                v1 = thing;
            }
            
            else 
            {
                v1 = graph.insertVertex(parent, null, tree.getToken().getSymbol(), 0, y, 80,
                        30);
            }
            
            for (TokenTree tr : tree.getChildren())
            {
                
                Object v2 = graph.insertVertex(parent, null, tr.getToken().getSymbol(), x, y+70, 80,
                        30);
                graph.insertEdge(parent, null, null, v1,v2);
                
                x+=190;
                
                traverseTree(tr, parent, v2, y+100);
            }
        }  
        
        else
        {
            if(thing != null)
            {
                v1 = thing;
            }
            
            else 
            {
                v1 = graph.insertVertex(parent, null, tree.getToken().getSymbol(), 0, y, 80,
                        30);
                
                
            }
            
            
        }
    }
}
