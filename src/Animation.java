public class Animation implements Action{
    public Entity entity;
    public WorldModel world;
    public ImageStore imageStore;
    public int repeatCount;

    public Animation(
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            int repeatCount)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }

    public void executeAction(EventScheduler scheduler) {
        ((AnimatingEntity) this.entity).nextImage();

        if (this.repeatCount != 1) {scheduler.scheduleEvent(this.entity,
                Creations.createAnimationAction(this.entity, Math.max(this.repeatCount - 1, 0)),
                    ((AnimatingEntity) this.entity).getAnimationPeriod());
        }
    }

}
