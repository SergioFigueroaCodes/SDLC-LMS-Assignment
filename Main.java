import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Sergio Figueroa
// 202620 Software Development CRN: 23585
// February 9, 2026,
// Main.java
// This class holds the main method and runs the Library Patron Management System.
// The program allows a librarian:
// load patron records from a dash-separated text file
// Show all patrons
// add a new patron manually
// remove a patron by 7-digit ID
// exit the program
// The menu repeats until the user chooses to exit.

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final PatronManager manager = new PatronManager();

    // method: main
    // purpose: Starts the program and keeps showing the menu until the user exits.
    // parameters: String[] args not used
    //  return: void

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println(" Library Patron Management System (LMS) ");
        System.out.println("========================================");

        boolean keepRunning = true;

        while (keepRunning) {

            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> loadFromFile();
                case "2" -> printAll();
                case "3" -> addPatron();
                case "4" -> removePatron();
                case "5" -> {
                    System.out.println("Exiting... Goodbye!");
                    keepRunning = false;
                }
                default -> System.out.println("Invalid choice. Please enter 1, 2, 3, 4, or 5.");
            }
        }
    }

    // method: printMenu
    // purpose: shows the menu options for the user to select.
    // parameters: none
    // return: void

    private static void printMenu() {
        System.out.println();
        System.out.println("--------- MAIN MENU ---------");
        System.out.println("1) Load patrons from file");
        System.out.println("2) Print all patrons");
        System.out.println("3) Add a new patron");
        System.out.println("4) Remove a patron by ID");
        System.out.println("5) Exit");
        System.out.print("Choose an option: ");
    }

    // method: loadFromFile
    // purpose: Prompts the user for a file path, loads patrons from the file,
    // adds them to the system passing by duplicate IDs, and prints all patrons.
    // parameters: none
    // return: void

    private static void loadFromFile() {

        System.out.print("Enter the file path to load patrons: ");
        String filePath = scanner.nextLine().trim();

        try {
            ArrayList<Patron> loadedPatrons = FileHelper.loadPatrons(filePath);

            int addedCount = 0;
            int duplicateCount = 0;

            int i;
            for (i = 0; i < loadedPatrons.size(); i++) {
                Patron p = loadedPatrons.get(i);

                boolean added = manager.addPatron(p);
                if (added) {
                    addedCount++;
                } else {
                    duplicateCount++;
                }
            }

            System.out.println("File load finished.");
            System.out.println("Patrons added: " + addedCount);

            if (duplicateCount > 0) {
                System.out.println("Duplicates skipped: " + duplicateCount);
            }

            System.out.println();
            System.out.println("--- Current Patron List ---");
            manager.printAllPatrons();

        } catch (IOException e) {
            System.out.println("ERROR: Could not read the file. Check the path and try again.");
        }
    }

    // method: printAll
    //  purpose: Prints all patrons currently stored in the system.
    //  parameters: none
    //  return: void

    private static void printAll() {
        System.out.println();
        System.out.println("--- All Patrons ---");
        manager.printAllPatrons();
    }

    // method: addPatron
    // purpose: Lets the user enter a new patron record with validation,
    // adds the patron to the system, and prints all patrons after adding.
    // parameters: none
    // return: void

    private static void addPatron() {

        System.out.println();
        System.out.println("--- Add New Patron ---");

        String id;
        while (true) {
            System.out.print("Enter 7-digit ID: ");
            id = scanner.nextLine().trim();

            if (!FileHelper.isValidId(id)) {
                System.out.println("ERROR: ID must be exactly 7 digits.");
            } else if (manager.idExists(id)) {
                System.out.println("ERROR: That ID already exists. Please enter a different ID.");
            } else {
                break;
            }
        }

        String name;
        while (true) {
            System.out.print("Enter name: ");
            name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("ERROR: Name cannot be empty.");
            } else {
                break;
            }
        }

        String address;
        while (true) {
            System.out.print("Enter address: ");
            address = scanner.nextLine().trim();

            if (address.isEmpty()) {
                System.out.println("ERROR: Address cannot be empty.");
            } else {
                break;
            }
        }

        double fine;
        while (true) {
            System.out.print("Enter overdue fine (0 - 250): ");
            String fineStr = scanner.nextLine().trim();

            try {
                fine = Double.parseDouble(fineStr);
            } catch (NumberFormatException ex) {
                System.out.println("ERROR: Fine must be a number.");
                continue;
            }

            if (!FileHelper.isValidFine(fine)) {
                System.out.println("ERROR: Fine must be between 0 and 250.");
            } else {
                break;
            }
        }

        Patron newPatron = new Patron(id, name, address, fine);
        manager.addPatron(newPatron);

        System.out.println("Patron added successfully.");

        System.out.println();
        System.out.println("--- Updated Patron List ---");
        manager.printAllPatrons();
    }

    // method: removePatron
    // purpose: Prompts the user for an ID, removes the patron if found,
    // prints all patrons after the removal attempt.
    // parameters: none
    // return: void

    private static void removePatron() {

        System.out.println();
        System.out.println("--- Remove Patron ---");

        System.out.print("Enter the 7-digit ID to remove: ");
        String id = scanner.nextLine().trim();

        if (!FileHelper.isValidId(id)) {
            System.out.println("ERROR: ID must be exactly 7 digits.");
            return;
        }

        boolean removed = manager.removePatronById(id);

        if (removed) {
            System.out.println("Patron removed successfully.");
        } else {
            System.out.println("ERROR: No patron found with that ID.");
        }

        System.out.println();
        System.out.println("--- Updated Patron List ---");
        manager.printAllPatrons();
    }
}
