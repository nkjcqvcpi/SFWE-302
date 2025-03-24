package edu.baylor.ecs.si;

public class MountainBikeService extends BasicService {
    @Override
    public void accept(Bicycle bike) {
        super.accept(bike);
    }

    public void accept(MountainBike bike) {
        super.accept(bike);
    }
}
