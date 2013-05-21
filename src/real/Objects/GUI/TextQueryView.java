package real.Objects.GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
import real.Objects.Parser.TokenOpManager;
import real.Objects.Services.DataManager;
import real.Objects.Services.MainWindow;

/*
 Class is pretty much like JTextArea, except it also finishes table name words for you and converts specific strings to symbols.
 pretty much ripped from the oracle example.
*/

public class TextQueryView extends JPanel implements DocumentListener, KeyListener
{
    private static final String COMMIT_ACTION = "commit";
    private static enum Mode { INSERT, COMPLETION };
    private Mode mode = Mode.INSERT;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private String name;

    //autocompletion words for textarea
    static private ArrayList<String> words = new ArrayList<>();
    static private TokenOpManager opManager = null;
    
    public TextQueryView(String name)
    {
        this.name = name;
        this.setLayout(new BorderLayout());
        textArea = new JTextArea();
        textArea.setFont(MainWindow.getStandardFont());
        textArea.setLineWrap(true);
        textArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), COMMIT_ACTION);
        textArea.getActionMap().put(COMMIT_ACTION, new CommitAction());
        textArea.getDocument().addDocumentListener(this);
        textArea.addKeyListener(this);
        textArea.setBorder(null);
        scrollPane = new JScrollPane(textArea);
        this.add(scrollPane);

        addAutoWord("projection");
        addAutoWord("selection");
        addAutoWord("rename");
        addAutoWord("group");
        addAutoWord("naturaljoin");
        addAutoWord("union");
        addAutoWord("intersection");
        addAutoWord("difference");
        addAutoWord("product");
        addAutoWord("leftouterjoin");
        addAutoWord("rightouterjoin");
        addAutoWord("fullouterjoin");
        addAutoWord("sort");       
    }

    @Override
    public String getName()
    {
        return name;
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
    
    public static void setOpManager(TokenOpManager manager)
    {
        opManager = manager;
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
                words.remove(keyword);
            }
        }

        removeAutoWord(table);
    }

    public JTextArea getTextArea()
    {
        return textArea;
    }
    
    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        SwingUtilities.invokeLater(
                        new CheckTask(textArea.getCaretPosition()));
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
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

    //class that checks the words that gets typed and changes relation operator
    //terms to the real symbols.
    private class CheckTask implements Runnable
    {
        private int pos;

        public CheckTask(int pos)
        {
            this.pos = pos;
        }

        private void convertToSymbol(final int start, final int end, String str)
        {
            System.out.println(str.substring(start, end).toLowerCase());
            
            switch (str.substring(start, end).toLowerCase())
            {
                case "projection":
                    textArea.replaceRange("π", start, end);
                    break;
                case "selection":
                    textArea.replaceRange("σ", start, end);
                    break;
                case "removeduplicates":
                    textArea.replaceRange("δ", start, end);
                    break;
                case "rename":
                    textArea.replaceRange("ρ", start, end);
                    break;
                case "group":
                    textArea.replaceRange("γ", start, end);
                    break;
                case "->":
                    textArea.replaceRange("→", start, end);
                    break;
                case "sort":
                    textArea.replaceRange("τ", start, end);
                    break;
                case "union":
                    textArea.replaceRange("∪", start, end);
                    break;
                case "intersection":
                    textArea.replaceRange("∩", start, end);
                    break;
                case "difference":
                    textArea.replaceRange("–", start, end);
                    break;
                case "product":
                    textArea.replaceRange("×", start, end);
                    break;
                case "naturaljoin":
                    textArea.replaceRange("⋈", start, end);
                    break;
                case "leftouterjoin":
                    textArea.replaceRange("⟕", start, end);
                    break;
                case "rightouterjoin":
                    textArea.replaceRange("⟖", start, end);
                    break;
                case "fullouterjoin":
                    textArea.replaceRange("⟗", start, end);
                    break;
            }
        }

        @Override
        public void run()
        {
            String str = textArea.getText();

            if (str.length() > pos)
            {
                //if we are in the string we only look for one string
                if (!Character.isWhitespace(str.charAt(pos)) && opManager.getNonLetterOp(Character.toString(str.charAt(pos))) == null)
                {
                    int leftIndex;
                    int rightIndex;
                    
                    for (leftIndex = pos; leftIndex >= 0; --leftIndex)
                    {
                        System.out.println(leftIndex);
                        
                        if (Character.isWhitespace(str.charAt(leftIndex)) || opManager.getNonLetterOp(Character.toString(str.charAt(leftIndex))) != null)
                        {                           
                            break;
                        }

                    }
                    
                    leftIndex++;
                    
                    for (rightIndex = pos; rightIndex < str.length(); ++rightIndex)
                    {

                        if (Character.isWhitespace(str.charAt(rightIndex)) || opManager.getNonLetterOp(Character.toString(str.charAt(rightIndex))) != null)
                        {
                            break;                        
                        }

                    }

                    convertToSymbol(leftIndex, rightIndex, str);
                }
            }
        }
    }
}


