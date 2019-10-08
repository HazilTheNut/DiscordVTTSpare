package Editor;

import Data.LayerImportances;
import Data.GameMap;
import Engine.Layer;
import Engine.LayerManager;
import Engine.SpecialGraphics.EditorLevelBoundGraphics;
import Engine.SpecialText;
import Engine.ViewWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * Created by Jared on 2/18/2018.
 */
public class EditorFrame extends JFrame {

    /**
     * EditorFrame:
     *
     * The master object behind the Sourcery Text Level Editor
     *
     * Each EditorFrame is dedicated to a singular GameMap object.
     */

    private JPanel windowLayersPanel;

    public EditorFrame(GameMap gamemap, WindowWatcher watcher){

        Container c = getContentPane();

        setLayout(new BorderLayout());

        ViewWindow window = new ViewWindow();
        c.addComponentListener(window);
        c.addKeyListener(window);

        //Create layers
        Layer mouseHighlight = new Layer(new SpecialText[window.RESOLUTION_WIDTH*4][window.RESOLUTION_HEIGHT*4], "mouse", 0, 0, LayerImportances.CURSOR);
        mouseHighlight.fixedScreenPos = true;

        //Send layers to LayerManager, which also needs to be instantiated.
        LayerManager manager = new LayerManager(window);
        manager.addLayer(gamemap.getBackdrop());
        manager.addLayer(gamemap.getTokenLayer());
        manager.addLayer(mouseHighlight);

        window.addSpecialGraphics(new EditorLevelBoundGraphics(window, manager, gamemap));

        //View Window panel
        JPanel windowPanel = new JPanel(new BorderLayout());
        windowLayersPanel = new JPanel();
        windowLayersPanel.setBorder(BorderFactory.createEtchedBorder());
        windowLayersPanel.setLayout(new WrapLayout(FlowLayout.LEFT, 2, 0));
        windowLayersPanel.add(new LayerToggler(gamemap.getBackdrop(), " Backdrop "));
        windowLayersPanel.add(new LayerToggler(gamemap.getTokenLayer(), " Tokens "));
        windowPanel.add(windowLayersPanel, BorderLayout.PAGE_START);
        windowPanel.add(window, BorderLayout.CENTER);

        c.add(windowPanel, BorderLayout.CENTER);
        //Create Text Panel

        UndoManager undoManager = new UndoManager(gamemap, this);
        //Establish mouse input
        EditorMouseInput mi = new EditorMouseInput(window, manager, mouseHighlight, gamemap.getBackdrop(), gamemap, undoManager);
        window.addMouseListener(mi);
        window.addMouseMotionListener(mi);
        window.addMouseWheelListener(mi);

        c.validate();

        setSize(new Dimension(850, 790));

        setTitle("DnDiscord");

        setVisible(true);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        watcher.update(1);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {watcher.update(-1);}
            @Override
            public void windowClosed(WindowEvent e) {}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('c'), "out");
        getRootPane().getActionMap().put("out", new Action() {
            @Override
            public Object getValue(String key) {
                return null;
            }

            @Override
            public void putValue(String key, Object value) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("[EditorFrame] Input test");
            }
        });
    }

    void updateLayerControllers(){
        for (Component c : windowLayersPanel.getComponents())
            if (c instanceof LayerToggler) {
                LayerToggler layerToggler = (LayerToggler) c;
                layerToggler.update();
            }
    }

    void removeLayerController(LayerToggler toggler){
        windowLayersPanel.remove(toggler);
    }

    /**
     * Adds a LayerToggler to the list at the top of the screen. Returns whether or not a new layer toggler was added.
     *
     * @param toggler The LayerToggler you want to add
     * @return Returns true if the LayerToggler had not already existed in the list, and false if it had.
     */
    boolean addLayerToggler(LayerToggler toggler){
        for (Component c : windowLayersPanel.getComponents())
            if (toggler.equals(c)) return false;
        windowLayersPanel.add(toggler);
        windowLayersPanel.validate();
        return true;
    }

    public ArrayList<LayerToggler> getLayerTogglers(){
        ArrayList<LayerToggler> togglers = new ArrayList<>();
        for (Component c : windowLayersPanel.getComponents())
            if (c instanceof LayerToggler) {
                togglers.add((LayerToggler)c);
            }
        return togglers;
    }

    public LayerToggler getLayerToggler(String name){
        for (LayerToggler toggler : getLayerTogglers()){
            if (toggler.getLayer().getName().equals(name))
                return toggler;
        }
        return null;
    }
}
