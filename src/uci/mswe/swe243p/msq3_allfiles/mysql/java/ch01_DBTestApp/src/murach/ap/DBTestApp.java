package murach.ap;

import java.sql.*;
import java.text.NumberFormat;

public class DBTestApp {

    public static void main(String args[]) {
        String query
                = "SELECT vendor_name, invoice_number, invoice_total "
                + "FROM vendors INNER JOIN invoices "
                + "    ON vendors.vendor_id = invoices.vendor_id "
                + "WHERE invoice_total >= 500 "
                + "ORDER BY vendor_name, invoice_total DESC";

        String dbUrl = "jdbc:mysql://localhost:3306/ap";
        String username = "ap_tester";
        String password = "sesame";

        // define common JDBC objects
        try (Connection connection = 
                DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            // Display the results of a SELECT statement
            System.out.println("Invoices with totals over 500:\n");
            while (rs.next()) {
                String vendorName = rs.getString("vendor_name");
                String invoiceNumber = rs.getString("invoice_number");
                double invoiceTotal = rs.getDouble("invoice_total");

                NumberFormat currency = NumberFormat.getCurrencyInstance();
                String invoiceTotalString = currency.format(invoiceTotal);

                System.out.println(
                        "Vendor:     " + vendorName + "\n"
                        + "Invoice No: " + invoiceNumber + "\n"
                        + "Total:      " + invoiceTotalString + "\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}