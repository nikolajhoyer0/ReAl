package real.Objects.GUI;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JTextArea;

public class ErrorView extends JTextArea implements Observer
{
    public ErrorView()
    {
        this.setEditable(false);
        this.setText("No errors detected.");
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if(this.getText().isEmpty())
        {
            this.setText((String)arg);
        }
        
        else
        {
            this.setText(this.getText() + "\n" + (String)arg);
        }
    }
}
