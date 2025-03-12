package edu.baylor.ecs.si;

import java.util.ArrayList;
import java.util.List;

public class Car implements BicycleVisitor {
    int maxCapacity = 4;
    private List<BicycleHolder> carHolders = new ArrayList<BicycleHolder>();

    public void accept(Bicycle bike) {
        if (carHolders.size() < maxCapacity) {
            carHolders.add(new BicycleHolder(bike));
            System.out.println("Bicycle added to car.");
        } else {
            System.out.println("Cannot add Bicycle because car is full.");
        }
    }

    public void accept(MountainBike bike) {
        if (carHolders.size() < maxCapacity) {
            carHolders.add(new MountainBikeHolder(bike));
            System.out.println("MountainBike added to car.");
        } else {
            System.out.println("Cannot add MountainBike because car is full.");
        }
    }

    public void accept(RoadBike bike) {
        if (carHolders.size() < maxCapacity) {
            carHolders.add(new RoadBikeHolder(bike));
            System.out.println("RoadBike added to car.");
        } else {
            System.out.println("Cannot add RoadBike because car is full.");
        }
    }
}
