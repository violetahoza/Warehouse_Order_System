package Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;

/**
 * Represents a log utility for writing bill information to a file.
 */
public class Log {
    private static final String LOG_FILE_PATH = "bills.log";

    /**
     * Appends bill information to a log file.
     * @param bill The bill to append.
     * @throws IOException if an I/O error occurs.
     */
    public static void appendBillToFile(Bill bill) throws IOException {
        String formattedBill = formatBill(bill);
        /* Writes the formatted bill information to the log file. It creates the file if it doesn't exist,
        and appends the new bill information to the end of the file. The bill information is followed by
        a system-dependent line separator to ensure each bill is written on a new line. */
        Files.write(Path.of(LOG_FILE_PATH), (formattedBill + System.lineSeparator()).getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
    /**
     * Formats bill information into a string.
     * @param bill The bill to format.
     * @return The formatted bill information.
     */
    public static String formatBill(Bill bill) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return String.format("Client %s ordered %s worth %f at time: %s",
                bill.client().getName(), bill.product().getName(), bill.amount(), bill.timestamp().format(formatter));
    }
}
