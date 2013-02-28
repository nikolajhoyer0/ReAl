package real.Objects;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import javax.swing.JFrame;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import real.Objects.Parser.TokenTree;

public class TreeView extends JFrame
{

    /**
     *
     */
    private static final long serialVersionUID = -2707712944901661771L;

    final mxGraph graph;
    
    public TreeView(TokenTree tree)
    {
        super("Hello, World!");

        graph = new mxGraph();
       // graph.setCellsLocked(true);
        graph.setAllowDanglingEdges(false);
        graph.setConnectableEdges(false);

        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try
        {
            traverseTree(tree, parent, null, 0);
        }
        finally
        {
            graph.getModel().endUpdate();
        }

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
    
    private void traverseTree(TokenTree tree, Object parent, Object thing, int y)
    {
                        
        if(tree.getChildren() != null)
        {
            int x = 0;
            Object v1;
            
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
    }
}
