package com.mobiquityinc.packer;

import java.util.ArrayList;
import java.util.List;

public class Goal {
    private float maxWeight;
    private List<Thing> posibleSolutions=new ArrayList<>();
    private List<Thing> solutions=new ArrayList<>();

    public float getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(float maxWeight) {
        this.maxWeight = maxWeight;
    }

    public List<Thing> getPosibleSolutions() {
        return posibleSolutions;
    }

    public void setPosibleSolutions(List<Thing> posibleSolutions) {
        this.posibleSolutions = posibleSolutions;
    }

    public List<Thing> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Thing> solutions) {
        this.solutions = solutions;
    }

    @Override
    public String toString() {
        return "Goal: "+maxWeight+", PS:"+posibleSolutions+", S:"+solutions;
    }
}
