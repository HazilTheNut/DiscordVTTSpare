package Data;

import Engine.Layer;

public class GameMap {

    private Layer backdrop;
    private Layer tokenLayer;

    public Layer getBackdrop() {
        return backdrop;
    }

    public Layer getTokenLayer() {
        return tokenLayer;
    }

    public GameMap copy(){
        GameMap copy = new GameMap();
        copy.backdrop = backdrop.copy();
        copy.tokenLayer = tokenLayer.copy();
        return copy;
    }

    public void setAllData(GameMap other){
        backdrop = other.getBackdrop().copy();
        tokenLayer = other.getTokenLayer().copy();
    }

    public void initialize(){
        backdrop = new Layer(52, 22, "backdrop", 0, 0, LayerImportances.BACKDROP);
        tokenLayer = new Layer(52, 22, "tokenLayer", 0, 0, LayerImportances.TOKENS);
    }

    public void resize(int newWidth, int newHeight){

    }
}
