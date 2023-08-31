import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class Fire extends Entity {

    public Fire(String id, Point position, List<PImage> images, int imageIndex) {
        super(id, position, images, imageIndex);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

        List points = new ArrayList<Point>();
        points.add(new Point(getPos().x - 1, getPos().y + 1));
        points.add(new Point(getPos().x + 1, getPos().y));
        points.add(new Point(getPos().x + 2, getPos().y + 1));
        points.add(new Point(getPos().x, getPos().y + 1));
        points.add(new Point(getPos().x + 1, getPos().y + 1));

        world.setBackgroundCell(getPos(), new Background("burnt", imageStore.getImageList("burnt")));
        for (Object p : points) {
            world.setBackgroundCell((Point) p, new Background("burnt", imageStore.getImageList("burnt")));
            Optional<Entity> e = world.getOccupant(((Point) p));
        }

        Dude fireDemon = Creations.createFireDemon("fireDemon" + getPos().x + "_" + getPos().y, getPos(), Functions.FAIRY_ACTION_PERIOD - 0.8, Functions.DUDE_ANIMATION_PERIOD - 0.8, Functions.DUDE_LIMIT, imageStore.getImageList(Functions.FIREDEMON_KEY));
        world.addEntity(fireDemon);
        fireDemon.scheduleActions(scheduler, world, imageStore);


    }


}

