import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;

import java.time.LocalTime;

/**
 * Class describing event which contains information about patient,
 * what time and which cure they have to be given.
 * It also contains nurse name.
 */
public class Event {

    /**
     * Patient unique insurance number.
     */
    private Integer patientInsuranceNumber;

    /**
     * Patient name, second name.
     */
    private String patientName;

    /**
     * Nurse name, second name, who is performer of current event.
     */
    private String nurseName;

    /**
     * Planned date when current event has to be performed.
     */
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime plannedTime;

    /**
     * Cure name which is used for patient's treating.
     */
    private String cureName;

    /**
     * Cure type.
     */
    private String cureType;

    /**
     * Particular amount of current cure.
     */
    private String dose;

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
}
