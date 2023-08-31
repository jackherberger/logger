import processing.core.PImage;

import java.util.List;



public class Tree extends PlantEntity{
    public Tree(
            String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health)
    {
        super(id, position, images, 0, animationPeriod, actionPeriod, health);

    }


    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

        if (!this.transformPlant(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this, Creations.createActivityAction(this, world, imageStore), this.getActionPeriod());
        }
    }

    public boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.health <= 0) {
            Entity stump = Creations.createStump(Functions.STUMP_KEY + "_" + this.getID(), this.getPos(), imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return true;
        }

        return false;
    }

}
