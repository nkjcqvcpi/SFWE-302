package edu.baylor.ecs.si;

public class RoadBike extends Bicycle{
    private int tireWidth;

    public RoadBike(int startCadence, int startSpeed, int startGear, Color color, int tireWidth) {
        super(startCadence, startSpeed, startGear, color);
        this.setTireWidth(tireWidth);
    }

    public int getTireWidth() {
        return this.tireWidth;
    }

    public void setTireWidth(int tireWidth) {
        this.tireWidth = tireWidth;
    }

    @Override
    public void printDescription(){
        System.out.println("\nBike is " + "in gear " + this.gear
                + " with a cadence of " + this.cadence +
                " and travelling at a speed of " + this.speed + ". " +
                "The tire width is " + this.tireWidth +
                ", the color is " + this.color + ". ");
    }

    @Override
    public void visit(BasicService bs) {
        bs.accept(this);
    }

    public void visit(BicycleVisitor servis) {
        servis.accept(this); // i v dalsich tridach
    }
}
