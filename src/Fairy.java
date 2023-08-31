import processing.core.PImage;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Fairy extends ActingEntity{
    public Fairy(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod) {
        super(id, position, images, 0, animationPeriod, actionPeriod);
    }


        public boolean moveToFairy(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPos().adjacent(target.getPos())) {
            world.removeEntity(scheduler, target);
            return true;
        } else {
            Point nextPos = this.nextPositionFairy(world, target.getPos());

            if (!this.getPos().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fairyTarget = world.findNearest(this.getPos(), new ArrayList<>(Arrays.asList(Stump.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPos();

            if (this.moveToFairy(world, fairyTarget.get(), scheduler)) {

                Entity sapling = Creations.createSapling(Functions.SAPLING_KEY + "_" + fairyTarget.get().getID(), tgtPos, imageStore.getImageList(Functions.SAPLING_KEY));

                world.addEntity(sapling);
                ((ActingEntity)sapling).scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, Creations.createActivityAction(this, world, imageStore), this.getActionPeriod());
    }

    public Point nextPositionFairy(WorldModel world, Point destPos) {
        Point start = this.getPos();
        Point end = destPos;

        PathingStrategy strategy = new AStarPathingStrategy();

        Predicate<Point> canPassThrough = pos ->  world.withinBounds(pos) && !(world.isOccupied(pos));
        BiPredicate<Point, Point> withinReach = (pos1, pos2) ->
                                            (Math.abs(pos1.x-pos2.x) == 1 && Math.abs(pos1.y-pos2.y) == 0 )
                        ||  (Math.abs(pos1.y-pos2.y) == 1 && Math.abs(pos1.x-pos2.x) == 0);

        Function<Point, Stream<Point>> potentialNeighbors = PathingStrategy.CARDINAL_NEIGHBORS;

        List<Point> path = strategy.computePath(start, end, canPassThrough, withinReach, potentialNeighbors);
        if (!(path.size() > 0)){
            return start;
        }
        return path.get(0);
    }


}
