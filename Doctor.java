import java.util.List;

public class Doctor extends User implements AppointmentManageable {
    private String doctorID;
    private List<TimeSlot> availability;
    private List<Appointment> schedule;

    public Doctor(String userID, String password, String name, String contactNumber, String email, String doctorID) {
        super(userID, password, name, contactNumber, email, UserRole.DOCTOR);
        this.doctorID = doctorID;
    }

    // Set availability
    @Override
    public void setAvailability(List<TimeSlot> slots) {
        this.availability = slots;
    }

    // Accept appointment
    @Override
    public void acceptAppointment(Appointment appointment) {
        appointment.updateStatus(AppointmentStatus.CONFIRMED);
        schedule.add(appointment);
    }

    // Decline appointment
    @Override
    public void declineAppointment(Appointment appointment) {
        appointment.updateStatus(AppointmentStatus.CANCELED);
    }

    // Record appointment outcome
    public void recordAppointmentOutcome(AppointmentOutcomeRecord outcomeRecord) {
        outcomeRecord.setDoctorID(this.doctorID);
        // Additional logic to update appointment record
    }

    @Override
    public void performRoleSpecificActions() {
        System.out.println("Performing doctor-specific actions.");
    }
}
