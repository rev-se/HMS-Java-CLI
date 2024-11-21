import java.util.Scanner;
import java.util.List;

public class DoctorMenu {
    private Doctor doctor;

    // Constructor
    public DoctorMenu(Doctor doctor) {
        this.doctor = doctor;
    }

    // Display menu and handle interactions
    public boolean displayMenu() {
        Scanner scanner = new Scanner(System.in);
    
        while (true) {
            try {
                System.out.println("\n--- Doctor Menu ---");
                System.out.println("1. View Schedule");
                System.out.println("2. Set Availability");
                System.out.println("3. View Available Slots");
                System.out.println("4. Accept Appointment");
                System.out.println("5. Decline Appointment");
                System.out.println("6. Add Consultation Notes");
                System.out.println("7. Add Prescription");
                System.out.println("8. Log Out");
                System.out.print("Enter your choice: ");
    
                // Validate input
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number between 1 and 8.");
                    scanner.next(); // Consume invalid input
                    continue;
                }
    
                int choice = scanner.nextInt();
    
                switch (choice) {
                    case 1:
                        doctor.viewSchedule();
                        break;
                    case 2:
                        setAvailability(scanner);
                        break;
                    case 3:
                        doctor.viewAvailableSlots();
                        break;
                    case 4:
                        acceptAppointment(scanner);
                        break;
                    case 5:
                        declineAppointment(scanner);
                        break;
                    case 6:
                        addConsultationNotes(scanner);
                        break;
                    case 7:
                        addPrescription(scanner);
                        break;
                    case 8:
                        System.out.println("Logging Out...");
                        return false; // Exit the menu
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
                scanner.next(); // Consume invalid input to avoid infinite loops
            }
        }
    }
    

    // Set Availability
    private void setAvailability(Scanner scanner) {
        System.out.println("\n--- Set Availability ---");
        doctor.generateDefaultAvailability(); // Generate default slots
        System.out.println("Default slots generated. You can modify them manually if needed.");

        System.out.println("Do you want to mark any slot as unavailable? (yes/no): ");
        String response = scanner.next();
        if (response.equalsIgnoreCase("yes")) {
            System.out.println("Enter the index of the slot to mark unavailable (0-based): ");
            for (int i = 0; i < doctor.getAvailability().size(); i++) {
                System.out.println(i + ": " + doctor.getAvailability().get(i));
            }
            int index = scanner.nextInt();
            doctor.setCustomSlotAvailability(index, false); // Mark slot as unavailable
            System.out.println("Slot marked as unavailable.");
        }

        // Sync the updated availability with the centralized repository
        DoctorAvailabilityRepository.getInstance()
                .setDoctorAvailability(doctor.getUserID(), doctor.getAvailability());
    }

    // Accept Appointment
    private void acceptAppointment(Scanner scanner) {
        System.out.println("\n--- Accept Appointment ---");
        doctor.viewSchedule(); // Display current appointments
        System.out.println("Enter the index of the appointment to accept:");
        int index = scanner.nextInt();
        if (index >= 0 && index < doctor.getSchedule().size()) {
            doctor.processAppointment(doctor.getSchedule().get(index), true);
        } else {
            System.out.println("Invalid selection.");
        }
    }

    // Decline Appointment
    private void declineAppointment(Scanner scanner) {
        System.out.println("\n--- Decline Appointment ---");
        doctor.viewSchedule(); // Display current appointments
        System.out.println("Enter the index of the appointment to decline:");
        int index = scanner.nextInt();
        if (index >= 0 && index < doctor.getSchedule().size()) {
            doctor.processAppointment(doctor.getSchedule().get(index), false);
        } else {
            System.out.println("Invalid selection.");
        }
    }

    // Add Consultation Notes
    private void addConsultationNotes(Scanner scanner) {
        System.out.println("\n--- Add Consultation Notes ---");
        System.out.print("Enter Appointment ID: ");
        String appointmentId = scanner.next();
        System.out.print("Enter Notes: ");
        scanner.nextLine(); // Consume newline
        String notes = scanner.nextLine();
        doctor.recordAppointmentOutcome(appointmentId, notes, null, null);
    }

    // Add Prescription
    private void addPrescription(Scanner scanner) {
        System.out.println("\n--- Add Prescription ---");
        System.out.print("Enter Appointment ID: ");
        String appointmentId = scanner.next();
        System.out.print("Enter Prescription Details: ");
        scanner.nextLine(); // Consume newline
        String prescriptionDetails = scanner.nextLine();
        doctor.recordAppointmentOutcome(appointmentId, null, null, new Prescription(prescriptionDetails));
    }
}