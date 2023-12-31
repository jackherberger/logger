import processing.core.PImage;

import java.util.List;

public class Creations {
    public static final int SAPLING_HEALTH_LIMIT = 5;
    public static final int SAPLING_ACTION_ANIMATION_PERIOD = 1; // have to be in sync since grows and gains health at same time


    public static Action createAnimationAction(Entity entity, int repeatCount) {
        return new Animation(entity, null, null,
                repeatCount);
    }

    public static Action createActivityAction(
            Entity entity, WorldModel world, ImageStore imageStore) {
        return new Activity(entity, world, imageStore);
    }

    public static House createHouse(
            String id, Point position, List<PImage> images) {
        return new House(id, position, images, 0, 0);
    }

    public static Fire createFire(
            String id, Point position, List<PImage> images) {

        return new Fire(id, position, images, 0) {
        };
    }

    public static HouseBurnt createHouseBurnt(
            String id, Point position, List<PImage> images) {
        return new HouseBurnt(id, position, images, 0, 0);
    }

    public static Obstacle createObstacle(
            String id, Point position, double animationPeriod, List<PImage> images) {
        return new Obstacle(id, position, images, animationPeriod);
    }

    public static Tree createTree(
            String id,
            Point position,
            double actionPeriod,
            double animationPeriod,
            int health,
            List<PImage> images) {
        return new Tree(id, position, images, actionPeriod, animationPeriod, health);
    }

    public static Stump createStump(
            String id,
            Point position,
            List<PImage> images) {
        return new Stump(id, position, images);
    }

    // health starts at 0 and builds up until ready to convert to Tree
    public static Sapling createSapling(
            String id,
            Point position,
            List<PImage> images) {
        return new Sapling(id, position, images,
                SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, 0, SAPLING_HEALTH_LIMIT);
    }

    public static Fairy createFairy(
            String id,
            Point position,
            double actionPeriod,
            double animationPeriod,
            List<PImage> images) {
        return new Fairy(id, position, images, actionPeriod, animationPeriod);
    }

    // need resource count, though it always starts at 0
    public static DudeNotFull createDudeNotFull(
            String id,
            Point position,
            double actionPeriod,
            double animationPeriod,
            int resourceLimit,
            List<PImage> images) {
        return new DudeNotFull(id, position, images, resourceLimit, 0,
                actionPeriod, animationPeriod);
    }

    public static DudeOnFire createDudeOnFire(
            String id,
            Point position,
            double actionPeriod,
            double animationPeriod,
            int resourceLimit,
            List<PImage> images) {
        return new DudeOnFire(id, position, images, resourceLimit, 0,
                actionPeriod, animationPeriod);
    }





    // don't technically need resource count ... full
    public static DudeFull createDudeFull(
            String id,
            Point position,
            double actionPeriod,
            double animationPeriod,
            int resourceLimit,
            List<PImage> images) {
        return new DudeFull(id, position, images, resourceLimit, 0,
                actionPeriod, animationPeriod);
    }

    public static FireDemon createFireDemon(
            String id,
            Point position,
            double actionPeriod,
            double animationPeriod,
            int resourceLimit,
            List<PImage> images) {
        return new FireDemon(id, position, images, resourceLimit, 0,
                actionPeriod, animationPeriod);
    }


}
