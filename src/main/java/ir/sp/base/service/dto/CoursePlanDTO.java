package ir.sp.base.service.dto;

public class CoursePlanDTO {
    private Long id;
    private Boolean needLab;
    private Boolean needProjector;
    private Integer practicalCredit;
    private Integer theoreticalCredit;
    private Integer practicalHours;
    private Integer theoreticalHours;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getNeedLab() {
        return needLab;
    }

    public void setNeedLab(Boolean needLab) {
        this.needLab = needLab;
    }

    public Boolean getNeedProjector() {
        return needProjector;
    }

    public void setNeedProjector(Boolean needProjector) {
        this.needProjector = needProjector;
    }

    public Integer getPracticalCredit() {
        return practicalCredit;
    }

    public void setPracticalCredit(Integer practicalCredit) {
        this.practicalCredit = practicalCredit;
    }

    public Integer getTheoreticalCredit() {
        return theoreticalCredit;
    }

    public void setTheoreticalCredit(Integer theoreticalCredit) {
        this.theoreticalCredit = theoreticalCredit;
    }

    public Integer getPracticalHours() {
        return practicalHours;
    }

    public void setPracticalHours(Integer practicalHours) {
        this.practicalHours = practicalHours;
    }

    public Integer getTheoreticalHours() {
        return theoreticalHours;
    }

    public void setTheoreticalHours(Integer theoreticalHours) {
        this.theoreticalHours = theoreticalHours;
    }
}
