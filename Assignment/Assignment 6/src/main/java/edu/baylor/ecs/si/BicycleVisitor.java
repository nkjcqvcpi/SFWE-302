package edu.baylor.ecs.si;

public interface BicycleVisitor {
    void accept(Bicycle b);
    void accept(MountainBike b);
    void accept(RoadBike b);
}
