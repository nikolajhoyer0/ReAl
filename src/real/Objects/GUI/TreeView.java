package real.Objects.GUI;

import com.mxgraph.view.mxGraph;
import javax.swing.JPanel;

public class TreeView extends JPanel {
    private mxGraph graph;
    
    public TreeView()
    {
        graph = new mxGraph();
        graph.setCellsLocked(false);
        graph.setAllowDanglingEdges(false);
        graph.setConnectableEdges(false); 
        
    }
}
