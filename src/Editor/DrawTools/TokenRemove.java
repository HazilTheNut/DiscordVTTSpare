package Editor.DrawTools;

import Data.GameMap;
import Engine.Layer;
import Engine.SpecialText;

import java.awt.*;

public class TokenRemove extends DrawTool {

    @Override
    public void onDrawStart(GameMap gameMap, Layer highlight, int col, int row, SpecialText text) {
        gameMap.getTokenLayer().editLayer(col, row, null);
    }
}
