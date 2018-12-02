package ir.sp.base.service.dto.feign;


import java.util.ArrayList;
import java.util.List;

public class GaModel {
    private List<ClassRoomDTO> classRooms = new ArrayList<>();
    private Double cost;

    public GaModel() {
    }

    public List<ClassRoomDTO> getClassRooms() {
        return classRooms;
    }

    public void setClassRooms(List<ClassRoomDTO> classRooms) {
        this.classRooms = classRooms;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
