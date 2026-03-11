package com.qlvanphongpham.domain.valueobjects;

import java.util.regex.Pattern;

public class PhoneNumber {
    // FIX: Sửa regex pattern để chấp nhận số điện thoại Việt Nam
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^(\\+84|0)(3[2-9]|5[2689]|7[06-9]|8[1-689]|9[0-9])[0-9]{7}$");
    
    private final String value;

    public PhoneNumber(String value) {
        if (value != null && !value.trim().isEmpty() && !isValid(value)) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ: " + value);
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private boolean isValid(String phoneNumber) {
        // Cho phép null hoặc empty
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return true;
        }
        return PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return value.equals(that.value);
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