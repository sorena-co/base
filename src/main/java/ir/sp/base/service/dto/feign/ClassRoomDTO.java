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
    private short practicalCredit;
    private short practicalHour;
    private short theoreticalCredit;
    private short theoreticalHour;
    private short day;
    private short startTime;
    private short endTime;
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

    public short getPracticalCredit() {
        return practicalCredit;
    }

    public void setPracticalCredit(short practicalCredit) {
        this.practicalCredit = practicalCredit;
    }

    public short getPracticalHour() {
        return practicalHour;
    }

    public void setPracticalHour(short practicalHour) {
        this.practicalHour = practicalHour;
    }

    public short getTheoreticalCredit() {
        return theoreticalCredit;
    }

    public void setTheoreticalCredit(short theoreticalCredit) {
        this.theoreticalCredit = theoreticalCredit;
    }

    public short getTheoreticalHour() {
        return theoreticalHour;
    }

    public void setTheoreticalHour(short theoreticalHour) {
        this.theoreticalHour = theoreticalHour;
    }

    public short getDay() {
        return day;
    }

    public void setDay(short day) {
        this.day = day;
    }

    public short getStartTime() {
        return startTime;
    }

    public void setStartTime(short startTime) {
        this.startTime = startTime;
    }

    public short getEndTime() {
        return endTime;
    }

    public void setEndTime(short endTime) {
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
