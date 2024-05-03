package Model;

import java.time.LocalDateTime;

 /** A record representing a bill generated for a purchase.*/
public record Bill(int orderId, Client client, Product product, double amount, LocalDateTime timestamp) {}
