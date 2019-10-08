package Editor.Mapping;

import Engine.ViewWindow;

import javax.swing.*;
import java.awt.*;

public class GameMapEditorPane extends JPanel {

    public GameMapEditorPane(){

        setLayout(new BorderLayout());

        ViewWindow viewWindow = new ViewWindow();


        JPanel topToolbar = new JPanel();
        topToolbar.setLayout(new BoxLayout(topToolbar, BoxLayout.LINE_AXIS));

    }
}
