package real.Objects.GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import real.Objects.Dataset;
import real.Objects.Kernel;
import real.Objects.Services.DataManager;

/*
 Class is pretty much like JTextArea, except it also finishes table name words for you.
 pretty much ripped from the oracle example.
*/

public class TextQueryView extends JPanel implements DocumentListener
{    
    private static final String COMMIT_ACTION = "commit";     
    private static enum Mode { INSERT, COMPLETION };
    private Mode mode = Mode.INSERT;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    
    //autocompletion words for textarea
    static ArrayList<String> words = new ArrayList<>();
    
    public TextQueryView()
    {      
        this.setLayout(new BorderLayout());
        textArea = new JTextArea();
        textArea.setFont(new Font("cambria", Font.PLAIN, 15));
        textArea.setLineWrap(true);    
        textArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), COMMIT_ACTION);
        textArea.getActionMap().put(COMMIT_ACTION, new CommitAction());   
        textArea.getDocument().addDocumentListener(this);  
        textArea.setBorder(null);
        scrollPane = new JScrollPane(textArea);
        this.add(scrollPane);     
    }
    
    public static void addAutoWord(String word)
    {
        words.add(word);
        Collections.sort(words);
    }
    
    public static void removeAutoWord(String word)
    {
        words.remove(word);
    }
    
    public static void addTableAutoWords(String table)
    {
        ArrayList<Dataset> datasets = Kernel.GetService(DataManager.class).GetAllDatasets();
        for (int i = 0; i < datasets.size(); i++)
        {
            for (int k = 0; k < datasets.get(i).getColumnCount(); k++)
            {
                String keyword = datasets.get(i).getColumnName(k);
                
                if (!words.contains(keyword))
                {
                    words.add(keyword);
                    System.out.println(keyword);
                }
            }
        }
        
        
        addAutoWord(table);
    }
 
    public static void removeTableAutoWords(String table)
    {
        ArrayList<Dataset> datasets = Kernel.GetService(DataManager.class).GetAllDatasets();
        for (int i = 0; i < datasets.size(); i++)
        {
            for (int k = 0; k < datasets.get(i).getColumnCount(); k++)
            {
                String keyword = datasets.get(i).getColumnName(k);
                
                if (!words.contains(keyword))
                {
                    words.remove(keyword);
                    System.out.println(keyword);
                }
            }
        }
        
        removeAutoWord(table);
    }
    
    public JTextArea getTextArea()
    {
        return textArea;
    }
    
    
    @Override
    public void changedUpdate(DocumentEvent eve)
    {   
    }

    @Override
    public void removeUpdate(DocumentEvent eve)
    {
    }

    @Override
    public void insertUpdate(DocumentEvent ev)
    {      
        if (ev.getLength() != 1)
        {
            return;
        }

        int pos = ev.getOffset();
        String content = null;
          
        try
        {
            content = textArea.getText(0, pos + 1);
        }
        catch (BadLocationException e)
        {
            e.printStackTrace();
        }

        // Find where the word starts
        int w;
        for (w = pos; w >= 0; w--)
        {
            if (!Character.isLetter(content.charAt(w)))
            {
                break;
            }
        }
        if (pos - w < 3)
        {
            // Too few chars
            return;
        }
        
        String prefix = content.substring(w + 1).toLowerCase();
        int n = Collections.binarySearch(words, prefix);
        if (n < 0 && -n <= words.size())
        {
            String match = words.get(-n - 1);
            if (match.startsWith(prefix))
            {
                // A completion is found
                String completion = match.substring(pos - w);
                // We cannot modify Document from within notification,
                // so we submit a task that does the change later
                SwingUtilities.invokeLater(
                        new CompletionTask(completion, pos + 1));
            }
        }
        else
        {
            // Nothing found
            mode = Mode.INSERT;

        }
    }
    
    private class CompletionTask implements Runnable
    {

        String completion;
        int position;

        CompletionTask(String completion, int position)
        {
            this.completion = completion;
            this.position = position;
        }

        @Override
        public void run()
        {          
            textArea.insert(completion, position);
            textArea.setCaretPosition(position + completion.length());
            textArea.moveCaretPosition(position);
            mode = Mode.COMPLETION;
        }
    }

    private class CommitAction extends AbstractAction
    {
        @Override
        public void actionPerformed(ActionEvent ev)
        {
            if (mode == Mode.COMPLETION)
            {
                int pos = textArea.getSelectionEnd();
                textArea.insert(" ", pos);
                textArea.setCaretPosition(pos + 1);
                mode = Mode.INSERT;
            }
            else
            {                       
                textArea.replaceSelection("\n");                
            }
        }
    }
}


