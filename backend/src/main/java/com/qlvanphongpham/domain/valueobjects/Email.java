package com.qlvanphongpham.domain.valueobjects;

import java.util.regex.Pattern;

public class Email {
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    private final String value;

    public Email(String value) {
        if (value == null || !isValid(value)) {
            throw new IllegalArgumentException("Email không hợp lệ: " + value);
        }
        this.value = value.toLowerCase();
    }

    public String getValue() {
        return value;
    }

    private boolean isValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return value.equals(email.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}