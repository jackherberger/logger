import java.util.List;

import processing.core.PImage;

/**
 * Represents a background for the 2D world.
 */
public final class Background {
    public String id;
    public List<PImage> images;
    public int imageIndex;

    public Background(String id, List<PImage> images) {
        this.id = id;
        this.images = images;
    }

    public static PImage getCurrentImage(Object object) {
        if (object instanceof Background background) {
            return background.images.get(background.imageIndex);
        } else if (object instanceof Entity entity) {
            return entity.getImages().get(entity.getImageIndex() % entity.getImages().size());
        } else {
            throw new UnsupportedOperationException(String.format("getCurrentImage not supported for %s", object));
        }
    }
}
