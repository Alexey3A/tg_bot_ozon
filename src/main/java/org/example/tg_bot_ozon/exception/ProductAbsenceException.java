package org.example.tg_bot_ozon.exception;

public class ProductAbsenceException extends RuntimeException {

    public String getMessage() {
        return "Товар отсутствует";
    }
}