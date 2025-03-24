package edu.baylor.ecs.si;

import java.util.ArrayList;
import java.util.List;

public class AnyHolderCar {
    int maxCapacity = 4;
    private final List<AnyHolder<? extends Bicycle>> carHolders = new ArrayList<>();

    public <T extends Bicycle> void accept(T cycle) {
        if (carHolders.size() < maxCapacity) {
            carHolders.add(new AnyHolder<>(cycle));
            System.out.println(cycle.getClass().getSimpleName() + " added to car.");
        } else {
            System.out.println("Car is full.");
        }
    }
}
