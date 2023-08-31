import processing.core.PImage;

import java.util.List;

public class House extends Entity{
    private int resourceLimit;
    private int resourceCount;

    public House(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount) {
        super(id, position, images, 0);
                this.resourceLimit = resourceLimit;
                this.resourceCount = resourceCount;

    }

    public int getResourceLimit() {return resourceLimit;}

    public void setResourceLimit(int resourceLimit) {
        this.resourceLimit = resourceLimit;
    }

    public int getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }
}

