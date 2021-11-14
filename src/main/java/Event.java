import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {
    private Integer patientInsuranceNumber;
    private String patientName;
    private String nurseName;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime plannedTime;
    private String eventState;
    private String cureName;
    private String cureType;
    private String dose;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime endTime;
    private String comment;

    public Integer getPatientInsuranceNumber() {
        return patientInsuranceNumber;
    }

    public void setPatientInsuranceNumber(Integer patientInsuranceNumber) {
        this.patientInsuranceNumber = patientInsuranceNumber;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getNurseName() {
        return nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    public LocalTime getPlannedTime() {
        return plannedTime;
    }

    public void setPlannedTime(LocalTime plannedTime) {
        this.plannedTime = plannedTime;
    }

    public String getEventState() {
        return eventState;
    }

    public void setEventState(String eventState) {
        this.eventState = eventState;
    }

    public String getCureName() {
        return cureName;
    }

    public void setCureName(String cureName) {
        this.cureName = cureName;
    }

    public String getCureType() {
        return cureType;
    }

    public void setCureType(String cureType) {
        this.cureType = cureType;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
