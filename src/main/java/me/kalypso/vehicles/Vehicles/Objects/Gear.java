package me.kalypso.vehicles.Vehicles.Objects;

public class Gear {

    private final int factor;
    private final boolean reverse;

    public Gear(int factor, boolean reverse) {
        this.factor = factor;
        this.reverse = reverse;
    }

    public int getFactor() {
        return factor;
    }

    public boolean isReverse() {
        return reverse;
    }

}
