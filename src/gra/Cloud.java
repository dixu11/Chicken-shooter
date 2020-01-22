package gra;

public class Cloud extends FlyingObject{

    public Cloud(int x, int y) {
        super(x, y, 200, 100, -1, "/Cloud.png");
    }

    @Override
    protected boolean isKilled() {
        return true;
    }
}
