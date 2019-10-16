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

    public void setIndex(int index) {
        this.index = index;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Thing: "+index+", "+weight+" "+cost;
    }
}
