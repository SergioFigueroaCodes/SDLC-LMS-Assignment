import java.util.ArrayList;

// Sergio Figueroa
// 202620 Software Development (CRN: 23585)
// February 9, 2026,
// PatronManager.java
// Purpose: This class manages the full list of patrons for the LMS.
// It helps with adding patrons, removing patrons by ID, preventing duplicate IDs,
// and printing all patrons stored in the system.

public class PatronManager {

    private final ArrayList<Patron> patrons;

    // Constructors do not need detailed method comments.
    public PatronManager() {
        patrons = new ArrayList<>();
    }

    // method: idExists
    // purpose: Checks if the given 7-digit ID already exists in the patron list.
    // parameters: String id the patron ID to look for
    // return: boolean true if the ID exists, false if not

    public boolean idExists(String id) {
        int i;
        for (i = 0; i < patrons.size(); i++) {
            if (patrons.get(i).id().equals(id)) {
                return true;
            }
        }
        return false;
    }

    // method: addPatron
    // purpose: Adds a patron to the list if the ID is not already used.
    // parameters: Patron patron - the patron object to add
    // return: boolean - true if added, false if duplicate ID

    public boolean addPatron(Patron patron) {
        if (idExists(patron.id())) {
            return false;
        }
        patrons.add(patron);
        return true;
    }

    // method: removePatronById
    // purpose: Removes a patron from the list if a matching ID is found.
    // parameters: String id - the 7-digit ID of the patron to remove
    // return: boolean - true if removed, false if not found

    public boolean removePatronById(String id) {
        int i;
        for (i = 0; i < patrons.size(); i++) {
            if (patrons.get(i).id().equals(id)) {
                patrons.remove(i);
                return true;
            }
        }
        return false;
    }

    // method: printAllPatrons
    // purpose: Prints all patrons currently stored. If none exist, prints a message.
    // parameters: none
    // return: void

    public void printAllPatrons() {
        if (patrons.isEmpty()) {
            System.out.println("(No patrons in the system.)");
            return;
        }

        int i;
        for (i = 0; i < patrons.size(); i++) {
            System.out.println(patrons.get(i).toString());
        }
    }
}
