package com.qlvanphongpham.domain.valueobjects;

public class Password {
    private final String value;

    public Password(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Password không được để trống");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean isHashed() {
        return value.startsWith("$2a$") || value.startsWith("$2b$");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return value.equals(password.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "[PROTECTED]";
    }
}