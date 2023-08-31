import processing.core.PImage;

import java.util.List;

public class Sapling extends PlantEntity{
    private int healthLimit;
    public Sapling(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int health, int healthLimit)
    {
        super(id, position, images, 0, animationPeriod, actionPeriod, health);
        this.healthLimit = healthLimit;
    }



        public boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.health <= 0) {
            Entity stump = Creations.createStump(Functions.STUMP_KEY + "_" + this.getID(), this.getPos(), imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return true;
        } else if (this.health >= this.healthLimit) {
            PlantEntity tree = Creations.createTree(Functions.TREE_KEY + "_" + this.getID(), this.getPos(), Functions.getNumFromRange( Functions.TREE_ACTION_MAX, Functions.TREE_ACTION_MIN), Functions.getNumFromRange(Functions.TREE_ANIMATION_MAX, Functions.TREE_ANIMATION_MIN), Functions.getIntFromRange(Functions.TREE_HEALTH_MAX, Functions.TREE_HEALTH_MIN), imageStore.getImageList(Functions.TREE_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.health++;
        if (!this.transformPlant(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, Creations.createActivityAction(this, world, imageStore), this.getActionPeriod());
        }


    }

    public int getHealthLimit() {
        return healthLimit;
    }

    public void setHealthLimit(int healthLimit) {
        this.healthLimit = healthLimit;
    }
}
