package srmbs.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class Calculator {
    public static double calculateCost(LocalDateTime start, LocalDateTime end, double costPerHour) {
        long hours = Duration.between(start, end).toHours();
        return hours * costPerHour;
    }
}