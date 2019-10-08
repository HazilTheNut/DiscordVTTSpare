package Start;

import Editor.EditorFrame;
import Data.GameMap;
import Editor.WindowWatcher;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jared on 2/18/2018.
 */
public class EditorStart {

    public void main (){

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        UIManager.put( "text", new Color( 230, 230, 230) );

        UIManager.getDefaults().put("Button.showMnemonics", true);

        GameMap gamemap = new GameMap();
        gamemap.initialize();

        WindowWatcher watcher = new WindowWatcher();

        new EditorFrame(gamemap, watcher);
    }
}
