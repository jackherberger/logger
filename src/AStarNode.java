import java.util.Objects;

public class AStarNode {
    public Point pos;
    public int h;
    public int g;
    public int f;
    public AStarNode neighbor;

    public AStarNode(Point pos, int h, int g, int f, AStarNode neighbor){
        this.pos = pos;
        this.h = h;
        this.g = g;
        this. f = f;
        this. neighbor = neighbor;
    }
    @Override
    public boolean equals(Object other){
        if (other != null){
            if (other instanceof AStarNode && ((AStarNode)other).pos.equals(this.pos)){
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "AStarNode{" +
                "pos=" + pos +
                ", h=" + h +
                ", g=" + g +
                ", f=" + f +
                ", neighbor=" + neighbor +
                '}';
    }
}
