package Model;

import java.time.LocalDateTime;

public record Bill(int orderId, Client client, Product product, double price, LocalDateTime timestamp) {}
