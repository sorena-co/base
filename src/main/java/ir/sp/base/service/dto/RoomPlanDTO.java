package ir.sp.base.service.dto;

public class RoomPlanDTO {
private Long id;
private Boolean projector;
private Boolean lab;
private Integer size;

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public Boolean getProjector() {
    return projector;
}

public void setProjector(Boolean projector) {
    this.projector = projector;
}

public Boolean getLab() {
    return lab;
}

public void setLab(Boolean lab) {
    this.lab = lab;
}

public Integer getSize() {
    return size;
}

public void setSize(Integer size) {
    this.size = size;
}
}
