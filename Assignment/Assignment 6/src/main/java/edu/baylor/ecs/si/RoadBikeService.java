package edu.baylor.ecs.si;

public class RoadBikeService extends BasicService {
    @Override
    public void accept(Bicycle bike) {
        super.accept(bike);
    }

    public void accept(RoadBike bike) {
        super.accept(bike);
    }
}
