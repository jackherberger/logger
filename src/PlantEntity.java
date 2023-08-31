import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.*;
import processing.core.PImage;

public abstract class PlantEntity extends ActingEntity{

    /**
     * An entity that exists in the world. See EntityKind for the
     * different kinds of entities that exist.
     */
        public int health;

    public PlantEntity(String id, Point pos, List<PImage> images, int imageIndex, double animationPeriod, double actionPeriod, int health) {
        super(id, pos, images, imageIndex, animationPeriod, actionPeriod);
        this.health = health;
    }

    public abstract boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

    public int getHealth(){return this.health;}
//    public int getHealthLimit(){return this.healthLimit;}
    public void setHealth(int health){this.health = health;}
//    public void setHealthLimit(int healthLimit){this.healthLimit = healthLimit;}

}




