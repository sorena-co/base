package ir.sp.base.service.dto;

public class TimePlanDTO {
    private Float startHour;
    private Float endHour;
    private Integer day;

    public TimePlanDTO(Float startHour, Float endHour, Integer day) {
        this.startHour = startHour;
        this.endHour = endHour;
        this.day = day;
    }

    public TimePlanDTO() {
    }

    public Float getStartHour() {
        return startHour;
    }

    public void setStartHour(Float startHour) {
        this.startHour = startHour;
    }

    public Float getEndHour() {
        return endHour;
    }

    public void setEndHour(Float endHour) {
        this.endHour = endHour;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }
}
