package edu.wofford.woclo;

public static class MismatchException extends RuntimeException {
    public MismatchException(String message) {
        super(message);
    }
}