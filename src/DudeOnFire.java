import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DudeOnFire extends Dude{
    public DudeOnFire(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod) {
        super(id, position, images, 0, animationPeriod, actionPeriod, resourceLimit, resourceCount);
    }



    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(this.getPos(), new ArrayList<>(Arrays.asList(Obstacle.class)));

        if (fullTarget.isPresent() && this.moveToDude(world, fullTarget.get(), scheduler)) {
            this.transformBurnt(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, Creations.createActivityAction(this, world, imageStore), this.getActionPeriod());
        }
    }

    public void transformBurnt(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {

        Dude dudeNotFull = Creations.createDudeNotFull(this.getID(), this.getPos(), this.getActionPeriod(), this.getAnimationPeriod(), this.getResourceLimit(), imageStore.getImageList(Functions.DUDE_KEY));

        world.removeEntity(scheduler, this);

        world.addEntity(dudeNotFull);
        dudeNotFull.scheduleActions(scheduler, world, imageStore);

    }

    public boolean moveToDude(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPos().adjacent(target.getPos())) {
            return true;
        } else {
            Point nextPos = this.nextPositionDude(world, target.getPos());

            if (!this.getPos().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }


}
