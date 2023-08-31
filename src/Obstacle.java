import processing.core.PImage;

import java.util.List;

public class Obstacle extends AnimatingEntity{

    public Obstacle(String id, Point position, List<PImage> images, double animationPeriod) {
        super(id, position, images, 0, animationPeriod);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, Creations.createAnimationAction(this, 0), this.getAnimationPeriod());
    }


}
