package ir.sp.base.service.dto.feign;


public class ClassRoomDTO {
    private long institutionId;
    private String institutionName;
    private long classGroupId;
    private String classGroupName;
    private long courseId;
    private String courseName;
    private boolean needLab;
    private boolean needProjector;
    private int practicalCredit;
    private int practicalHour;
    private int theoreticalCredit;
    private int theoreticalHour;
    private int day;
    private int startTime;
    private int endTime;
    private long personId;
    private String personName;
    private long roomId;
    private String roomName;

    public long getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(long classGroupId) {
        this.classGroupId = classGroupId;
    }

    public String getClassGroupName() {
        return classGroupName;
    }

    public void setClassGroupName(String classGroupName) {
        this.classGroupName = classGroupName;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public int getPracticalCredit() {
        return practicalCredit;
    }

    public void setPracticalCredit(int practicalCredit) {
        this.practicalCredit = practicalCredit;
    }

    public int getPracticalHour() {
        return practicalHour;
    }

    public void setPracticalHour(int practicalHour) {
        this.practicalHour = practicalHour;
    }

    public int getTheoreticalCredit() {
        return theoreticalCredit;
    }

    public void setTheoreticalCredit(int theoreticalCredit) {
        this.theoreticalCredit = theoreticalCredit;
    }

    public int getTheoreticalHour() {
        return theoreticalHour;
    }

    public void setTheoreticalHour(int theoreticalHour) {
        this.theoreticalHour = theoreticalHour;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public long getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(long institutionId) {
        this.institutionId = institutionId;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

}
