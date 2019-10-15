package Editor.DrawTools;

import Data.GameMap;
import Editor.Mapping.MappingTheme;
import Engine.Layer;
import Engine.SpecialText;

import java.awt.*;

public class VisionToggleAll extends DrawTool {

    @Override
    public void onDrawStart(GameMap gameMap, Layer highlight, int col, int row, SpecialText text) {
        boolean visibleSpaces = false;
        for (int c = 0; c < gameMap.getHideLayer().getCols(); c++) {
            for (int r = 0; r < gameMap.getHideLayer().getRows(); r++) {
                visibleSpaces |= (gameMap.getHideLayer().getSpecialText(c, r) == null);
                gameMap.getHideLayer().editLayer(c, r, new SpecialText(' ', Color.WHITE, MappingTheme.VISION_HIDE_COLOR));
            }
        }
        if (!visibleSpaces)
            gameMap.getHideLayer().clearLayer();
    }
}
