package edu.baylor.ecs.si;

public class BasicService {
    public void accept(Bicycle bike) {
        System.out.println("fixing Bicycle");
    }

    public void accept(MountainBike bike) {
        System.out.println("fixing MountainBike");
    }

    public void accept(RoadBike bike) {
        System.out.println("fixing RoadBike");
    }
}
