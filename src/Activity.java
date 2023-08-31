/**
 * An action that can be taken by an entity
 */
public final class Activity implements Action {
    public Entity entity;
    public WorldModel world;
    public ImageStore imageStore;
    public int repeatCount;

    public Activity(
            Entity entity,
            WorldModel world,
            ImageStore imageStore) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    public void executeAction(EventScheduler scheduler) {
        ((ActingEntity) this.entity).executeActivity(this.world,
                this.imageStore, scheduler);
    }
    }


