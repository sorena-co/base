package ir.sp.base.service.dto;

public class TimePlanDTO {
    private Float startTime;
    private Float endTime;
    private Integer day;

    public TimePlanDTO(Float startTime, Float endTime, Integer day) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }

    public TimePlanDTO() {
    }

    public Float getStartTime() {
        return startTime;
    }

    public void setStartTime(Float startTime) {
        this.startTime = startTime;
    }

    public Float getEndTime() {
        return endTime;
    }

    public void setEndTime(Float endTime) {
        this.endTime = endTime;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }
}
