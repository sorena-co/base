package ir.sp.base.service.dto;

import java.util.ArrayList;
import java.util.List;

public class ProfPlanDTO {
    private Long id;
    private List<Long> courseIds = new ArrayList<>();
    private List<TimePlanDTO> preferenceHours = new ArrayList<>();
    private Integer maxCredits;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<Long> courseIds) {
        this.courseIds = courseIds;
    }

    public List<TimePlanDTO> getPreferenceHours() {
        return preferenceHours;
    }

    public void setPreferenceHours(List<TimePlanDTO> preferenceHours) {
        this.preferenceHours = preferenceHours;
    }

    public Integer getMaxCredits() {
        return maxCredits;
    }

    public void setMaxCredits(Integer maxCredits) {
        this.maxCredits = maxCredits;
    }
}
