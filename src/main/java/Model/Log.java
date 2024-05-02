package Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;

public class Log {
    private static final String LOG_FILE_PATH = "bills.log";

    public static void appendBillToFile(Bill bill) throws IOException {
        String formattedBill = formatBill(bill);
        Files.write(Path.of(LOG_FILE_PATH), (formattedBill + System.lineSeparator()).getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
    public static String formatBill(Bill bill) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return String.format("Client %s ordered %s worth %f at time: %s",
                bill.client().getName(), bill.product().getName(), bill.price(), bill.timestamp().format(formatter));
    }
}
