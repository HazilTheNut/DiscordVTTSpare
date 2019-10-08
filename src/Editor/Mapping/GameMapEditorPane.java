package Editor.Mapping;

import Data.GameMap;
import Data.LayerImportances;
import Editor.DrawTools.*;
import Engine.Layer;
import Engine.LayerManager;
import Engine.SpecialGraphics.EditorLevelBoundGraphics;
import Engine.SpecialText;
import Engine.ViewWindow;

import javax.swing.*;
import java.awt.*;

public class GameMapEditorPane extends JPanel {

    private EditorMouseInput mouseInput;

    public GameMapEditorPane(GameMap gameMap, JTabbedPane owner){

        setLayout(new BorderLayout());
        setFocusable(true);

        //Create view window
        ViewWindow viewWindow = new ViewWindow();
        viewWindow.setFocusable(true);
        viewWindow.setEnabled(true);
        addComponentListener(viewWindow);

        viewWindow.addMouseListener(viewWindow);
        viewWindow.addKeyListener(viewWindow);
        owner.addKeyListener(viewWindow);

        SingleTextRenderer activeTextDisplay = new SingleTextRenderer(new SpecialText(' '));
        JPanel topToolbar = new JPanel();
        JPanel toolOptionsPanel = new JPanel();
        toolOptionsPanel.setLayout(new BorderLayout());
        toolOptionsPanel.setBorder(BorderFactory.createEmptyBorder());
        JLabel activeTextLabel = new JLabel(activeTextDisplay);
        DrawToolManager drawToolManager = new DrawToolManager(toolOptionsPanel, activeTextDisplay, activeTextLabel, topToolbar);

        //Create mousee highlight
        Layer mouseHighlight = new Layer(new SpecialText[viewWindow.RESOLUTION_WIDTH*4][viewWindow.RESOLUTION_HEIGHT*4], "mouse", 0, 0, LayerImportances.CURSOR);
        mouseHighlight.fixedScreenPos = true;

        //Send layers to LayerManager, which also needs to be instantiated.
        LayerManager manager = new LayerManager(viewWindow);
        manager.addLayer(gameMap.getBackdrop());
        manager.addLayer(gameMap.getTokenLayer());
        manager.addLayer(mouseHighlight);

        //Hook everything else up
        viewWindow.addSpecialGraphics(new EditorLevelBoundGraphics(viewWindow, manager, gameMap));
        UndoManager undoManager = new UndoManager(gameMap, null);
        mouseInput = new EditorMouseInput(viewWindow, manager, mouseHighlight, gameMap, undoManager, drawToolManager);
        viewWindow.addMouseListener(mouseInput);
        viewWindow.addMouseMotionListener(mouseInput);

        viewWindow.addKeyListener(drawToolManager);
        viewWindow.requestFocusInWindow();
        addKeyListener(drawToolManager);

        //Assemble user interface.

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEtchedBorder());
        centerPanel.add(viewWindow, BorderLayout.CENTER);
        centerPanel.addMouseListener(viewWindow);
        centerPanel.validate();

        add(centerPanel, BorderLayout.CENTER);

        topToolbar.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 0));

        topToolbar.add(drawToolManager.generateToolButton(new ExpandRoom(manager), "Icons/expand.png", "Expand Area ; click outside bounds to expand size"));

        topToolbar.add(new JLabel(" | "));

        topToolbar.add(new LayerToggler(gameMap.getBackdrop(), "backdrop"));
        topToolbar.add(activeTextLabel);
        topToolbar.add(drawToolManager.generateToolButton(new ArtBrush(manager), "Icons/brush.png", "(Backdrop) Brush Tool"));
        topToolbar.add(drawToolManager.generateToolButton(new ArtLine(manager), "Icons/line.png", "(Backdrop) Line Tool"));
        topToolbar.add(drawToolManager.generateToolButton(new ArtRectangle(manager), "Icons/rectangle.png", "(Backdrop) Rectangle Tool"));
        topToolbar.add(drawToolManager.generateToolButton(new ArtFill(), "Icons/fill.png", "(Backdrop) Fill Tool"));
        topToolbar.add(toolOptionsPanel);

        topToolbar.add(new JLabel(" | "));

        topToolbar.add(new LayerToggler(gameMap.getTokenLayer(), "tokens"));
        topToolbar.add(drawToolManager.generateToolButton(new TokenPlace(), "Icons/tokenplace.png", "(Tokens) Place Token"));
        topToolbar.add(drawToolManager.generateToolButton(new TokenRemove(), "Icons/tokenremove.png", "(Tokens) Remove Token"));
        topToolbar.add(drawToolManager.generateToolButton(new TokenMove(manager), "Icons/tokenmove.png", "(Tokens) Move Token"));

        topToolbar.add(new JLabel(" | "));

        topToolbar.add(new LayerToggler(gameMap.getHideLayer(), "hide"));

        add(topToolbar, BorderLayout.PAGE_START);
    }
}
