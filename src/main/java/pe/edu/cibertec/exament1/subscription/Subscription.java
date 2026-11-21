package pe.edu.cibertec.exament1.subscription;

import java.time.LocalDate;

public record Subscription(Long id, String status, LocalDate nextBillingDate) {}
