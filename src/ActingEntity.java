import processing.core.PImage;

import java.util.List;


public abstract class ActingEntity extends AnimatingEntity{


    /**
     * An entity that exists in the world. See EntityKind for the
     * different kinds of entities that exist.
     */
    private double  actionPeriod;


    public ActingEntity(String id, Point position, List<PImage> images, int imageIndex, double animationPeriod, double actionPeriod) {
        super(id, position, images, imageIndex, animationPeriod);
        this.actionPeriod = actionPeriod;
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, Creations.createActivityAction(this, world, imageStore), this.actionPeriod);
        scheduler.scheduleEvent(this, Creations.createAnimationAction(this, 0), this.getAnimationPeriod());
    }

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    public double getActionPeriod() {
        return actionPeriod;
    }

    public void setActionPeriod(double actionPeriod) {
        this.actionPeriod = actionPeriod;
    }

    }
