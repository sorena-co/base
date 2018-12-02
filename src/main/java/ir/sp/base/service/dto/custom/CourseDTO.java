package ir.sp.base.service.dto.custom;

public class CourseDTO {
    private long id;
    private String name;
    private boolean needLab;
    private boolean needProjector;
    private Integer practicalCredit;
    private Integer practicalHour;
    private Integer theoreticalCredit;
    private Integer theoreticalHour;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNeedLab() {
        return needLab;
    }

    public void setNeedLab(boolean needLab) {
        this.needLab = needLab;
    }

    public boolean isNeedProjector() {
        return needProjector;
    }

    public void setNeedProjector(boolean needProjector) {
        this.needProjector = needProjector;
    }

    public Integer getPracticalCredit() {
        return practicalCredit;
    }

    public void setPracticalCredit(Integer practicalCredit) {
        this.practicalCredit = practicalCredit;
    }

    public Integer getPracticalHour() {
        return practicalHour;
    }

    public void setPracticalHour(Integer practicalHour) {
        this.practicalHour = practicalHour;
    }

    public Integer getTheoreticalCredit() {
        return theoreticalCredit;
    }

    public void setTheoreticalCredit(Integer theoreticalCredit) {
        this.theoreticalCredit = theoreticalCredit;
    }

    public Integer getTheoreticalHour() {
        return theoreticalHour;
    }

    public void setTheoreticalHour(Integer theoreticalHour) {
        this.theoreticalHour = theoreticalHour;
    }
}
