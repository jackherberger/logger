import processing.core.PImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FireDemon extends Dude{
    public FireDemon(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod) {
        super(id, position, images, 0, animationPeriod, actionPeriod, resourceLimit, resourceCount);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(this.getPos(), new ArrayList<>(Arrays.asList(House.class)));

        if (fullTarget.isPresent() && this.moveToDude(world, fullTarget.get(), scheduler)) {
            Entity houseBurnt = Creations.createHouseBurnt("house_" + getPos().x + "_" + getPos().y, getPos(), imageStore.getImageList(Functions.HOUSE_BURNT_KEY));
            houseBurnt.setPos(fullTarget.get().getPos());

            world.removeEntityAt(fullTarget.get().getPos());
            world.addEntity(houseBurnt);
            world.removeEntity(scheduler, this);


        } else {
            scheduler.scheduleEvent(this, Creations.createActivityAction(this, world, imageStore), this.getActionPeriod());
        }
    }

    public boolean moveToDude(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPos().adjacent(target.getPos())) {
            return true;
        } else {
            Point nextPos = this.nextPositionDude(world, target.getPos());

            if (!this.getPos().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(scheduler, this, nextPos);
            }
            }
            return false;
        }
    }


