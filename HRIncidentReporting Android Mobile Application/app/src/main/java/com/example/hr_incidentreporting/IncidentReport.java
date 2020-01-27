package com.example.hr_incidentreporting;
/*This is the Class (blueprint) of Incident Reports. Object of this class is used to return results from some database
handler methods to main activity.java*/
public class IncidentReport {
    String IncidentID;
    String IncidentDate;
    String EmployeeNumber;
    String EmployeeName;
    String Gender;
    String Shift;
    String Department;
    String Position;
    String IncidentType;
    String InjuredBodyPart;

    public String getIncidentID() {
        return IncidentID;
    }

    public void setIncidentID(String incidentID) {
        IncidentID = incidentID;
    }

    public String getIncidentDate() {
        return IncidentDate;
    }

    public void setIncidentDate(String incidentDate) {
        IncidentDate = incidentDate;
    }

    public String getEmployeeNumber() {
        return EmployeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        EmployeeNumber = employeeNumber;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getShift() {
        return Shift;
    }

    public void setShift(String shift) {
        Shift = shift;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getIncidentType() {
        return IncidentType;
    }

    public void setIncidentType(String incidentType) {
        IncidentType = incidentType;
    }

    public String getInjuredBodyPart() {
        return InjuredBodyPart;
    }

    public void setInjuredBodyPart(String injuredBodyPart) {
        InjuredBodyPart = injuredBodyPart;
    }
}
