import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DudeNotFull extends Dude{
    public DudeNotFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod) {
        super(id, position, images, 0, animationPeriod, actionPeriod, resourceLimit, resourceCount);

    }



    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> target = world.findNearest(this.getPos(), new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));

        if (target.isEmpty() || !this.moveToDude(world, target.get(), scheduler) || !this.transformNotFull(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, Creations.createActivityAction(this, world, imageStore), this.getActionPeriod());
        }
    }

    public boolean moveToDude(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPos().adjacent(target.getPos())) {
            this.setResourceCount(this.getResourceCount() + 1);
            ((PlantEntity)target).setHealth(((PlantEntity) target).getHealth() - 1);
            return true;
        } else {
            Point nextPos = this.nextPositionDude(world, target.getPos());

            if (!this.getPos().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    public boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getResourceCount() >= this.getResourceLimit()) {
            Dude dude = Creations.createDudeFull(this.getID(), this.getPos(), this.getActionPeriod(), this.getAnimationPeriod(), this.getResourceLimit(), this.getImages());

            world.removeEntity(scheduler, this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(dude);
            dude.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }



}

