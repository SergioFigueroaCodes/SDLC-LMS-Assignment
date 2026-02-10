import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// Sergio Figueroa
// 202620 Software Development CRN 23585
// February 9, 2026,
// FileHelper.java
// Purpose:
// This class loads patron records from a text file for the LMS.
// Each line of the file must be in the format
// ID-NAME-ADDRESS-FINE
// If a line is invalid, it is skipped and a warning message is printed.

public class FileHelper {

    // method: loadPatrons
    // purpose: Reads patrons from a dash-separated text file and returns them as a list.
    // parameters: String file path the file location entered by the user
    // return: ArrayList Patron successfully loaded from the file
    // throws: Exception if the file cannot be opened or read

    public static ArrayList<Patron> loadPatrons(String filePath) throws IOException {

        ArrayList<Patron> patronsFromFile = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int lineNumber = 0;

        while ((line = reader.readLine()) != null) {
            lineNumber++;
            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            // Format ID-NAME-ADDRESS-FINE
            String[] parts = line.split("-", 4);

            if (parts.length != 4) {
                System.out.println("WARNING: Line " + lineNumber +
                        " skipped (expected ID-NAME-ADDRESS-FINE): " + line);
                continue;
            }

            String id = parts[0].trim();
            String name = parts[1].trim();
            String address = parts[2].trim();
            String fineStr = parts[3].trim();

            // Validate ID
            if (!isValidId(id)) {
                System.out.println("WARNING: Line " + lineNumber +
                        " skipped (ID must be exactly 7 digits): " + line);
                continue;
            }

            // Validate name and address
            if (name.isEmpty() || address.isEmpty()) {
                System.out.println("WARNING: Line " + lineNumber +
                        " skipped (name/address cannot be empty): " + line);
                continue;
            }

            // Validate fine must be a number
            double fine;
            try {
                fine = Double.parseDouble(fineStr);
            } catch (NumberFormatException ex) {
                System.out.println("WARNING: Line " + lineNumber +
                        " skipped (fine must be a number): " + line);
                continue;
            }

            // Validate fine range
            if (!isValidFine(fine)) {
                System.out.println("WARNING: Line " + lineNumber +
                        " skipped (fine must be between 0 and 250): " + line);
                continue;
            }

            // If everything is valid, create the patron and add to list
            Patron p = new Patron(id, name, address, fine);
            patronsFromFile.add(p);
        }

        reader.close();
        return patronsFromFile;
    }

    // method: isValidId
    // purpose: Checks if the ID is exactly 7 digits.
    // parameters: String id the ID to validate
    // return: boolean true if valid, false if invalid

    public static boolean isValidId(String id) {
        if (id == null) {
            return false;
        }

        id = id.trim();

        if (id.length() != 7) {
            return false;
        }

        // Check each character is a digit
        int i;
        for (i = 0; i < id.length(); i++) {
            char c = id.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }

        return true;
    }

    // method: isValidFine
    // purpose: Checks if the fine is between 0 and 250.
    // parameters: double fine, fine amount
    // return: boolean true if valid, false if invalid

    public static boolean isValidFine(double fine) {
        return fine >= 0 && fine <= 250;
    }
}
