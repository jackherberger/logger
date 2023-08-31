import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import processing.core.PImage;

public abstract class Dude extends ActingEntity{


    /**
     * An entity that exists in the world. See EntityKind for the
     * different kinds of entities that exist.
     */
    private int resourceLimit;
    private int resourceCount;

    public Dude(String id, Point position, List<PImage> images, int imageIndex, double animationPeriod, double actionPeriod, int resourceLimit, int resourceCount) {
        super(id, position, images, 0, animationPeriod, actionPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    public Point nextPositionDude(WorldModel world, Point destPos) {
        Point start = this.getPos();
        Point end = destPos;

        PathingStrategy strategy = new AStarPathingStrategy();

        Predicate<Point> canPassThrough = pos ->  !(world.isOccupied(pos)) && world.withinBounds(pos);
//        BiPredicate<Point, Point> withinReach = (pos1, pos2) -> !(Math.abs(pos1.x - pos2.x) > 1)
//                && !(Math.abs(pos1.y) - pos2.y > 1); need to check actual positions
        BiPredicate<Point, Point> withinReach = (pos1, pos2) ->
                                                 (Math.abs(pos1.y-pos2.y) == 1 && Math.abs(pos1.x-pos2.x) == 0)
                                            ||   (Math.abs(pos1.x-pos2.x) == 1 && Math.abs(pos1.y-pos2.y) == 0 )
        ;

        Function<Point, Stream<Point>> potentialNeighbors = PathingStrategy.CARDINAL_NEIGHBORS;

        List<Point> path = strategy.computePath(start, end, canPassThrough, withinReach, potentialNeighbors);
        if (!(path.size() > 0)){
            return start;
        }
        return path.get(0);
    }


    public abstract boolean moveToDude(WorldModel world, Entity target, EventScheduler scheduler);

    public int getResourceLimit() {
        return resourceLimit;
    }

    public void setResourceLimit(int resourceLimit) {this.resourceLimit = resourceLimit;}

    public int getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }








}
