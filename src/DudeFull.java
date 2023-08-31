import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DudeFull extends Dude{
    private boolean fireFlag = false;
    public DudeFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod) {
        super(id, position, images, 0, animationPeriod, actionPeriod, resourceLimit, resourceCount);
    }



    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(this.getPos(), new ArrayList<>(Arrays.asList(House.class, HouseBurnt.class)));
//        Optional<Entity> nextFullTarget = world.findNearest(this.getPos(), new ArrayList<>(Arrays.asList(House.class)));


        if (fullTarget.isPresent() && this.moveToDude(world, fullTarget.get(), scheduler)) {
            if (fullTarget.get().getClass() == HouseBurnt.class){
                this.transformDudeOnFire(world, scheduler, imageStore);
            }
            else{
                this.transformFull(world, scheduler, imageStore);
            }
        } else {
            scheduler.scheduleEvent(this, Creations.createActivityAction(this, world, imageStore), this.getActionPeriod());
        }
    }

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Dude dude = Creations.createDudeNotFull(this.getID(), this.getPos(), this.getActionPeriod(), this.getAnimationPeriod(), this.getResourceLimit(), this.getImages());

        world.removeEntity(scheduler, this);

        world.addEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);
    }

    public void transformDudeOnFire(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Dude dudeOnFire = Creations.createDudeOnFire("dudeOnFire_" + getPos().x + "_" + getPos().y, getPos(), Functions.FAIRY_ACTION_PERIOD -  0.6, Functions.DUDE_ANIMATION_PERIOD - 0.86, Functions.DUDE_LIMIT, imageStore.getImageList(Functions.DUDE_ON_FIRE_KEY));
        world.removeEntity(scheduler, this);

        world.addEntity(dudeOnFire);
        dudeOnFire.scheduleActions(scheduler, world, imageStore);

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
