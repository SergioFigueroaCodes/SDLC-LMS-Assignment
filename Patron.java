// Sergio Figueroa
// 202620 Software Development (CRN: 23585)
// February 9, 2026,
// Patron.java
// Purpose: This class represents a single library patron record for the
// Library Patron Management System. It stores the patron's
// 7-digit ID, name, address, and overdue fine amount.

public record Patron(String id, String name, String address, double fine) {

    // Constructors does not need method comments.

    // method: toString
    // purpose: Returns a formatted string for displaying a patron record on the console.
    // parameters: none
    // return: String formatted patron information

    public String toString() {
        return "ID: " + id
                + " | Name: " + name
                + " | Address: " + address
                + " | Fine: $" + String.format("%.2f", fine);
    }
}
