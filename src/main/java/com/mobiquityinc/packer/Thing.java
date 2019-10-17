package com.mobiquityinc.packer;

public class Thing {
    private int index;
    private float weight;
    private float cost;

    public Thing(int index, float weight, float cost) {
        this.index=index;
        this.weight=weight;
        this.cost=cost;
    }

    public int getIndex() {
        return index;
    }


    public float getWeight() {
        return weight;
    }


    public float getCost() {
        return cost;
    }

}
