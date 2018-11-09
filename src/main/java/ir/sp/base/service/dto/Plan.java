package ir.sp.base.service.dto;

import java.util.ArrayList;
import java.util.List;

public class Plan {
    private Double fitness;
    private List<Entity> entity = new ArrayList<>();

    public Double getFitness() {
        return fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    public List<Entity> getEntity() {
        return entity;
    }

    public void setEntity(List<Entity> entity) {
        this.entity = entity;
    }
}

