package com.eren.aethra.enums;

public enum PaymentMethod {

    WIRE_TRANSFER ("Havale ile Ödeme"),
    CREDIT_CART ("Kredi Kartı ile Ödeme"),
    AT_THE_DOOR ("Kapıda Ödeme");

    private final String name;

    private PaymentMethod(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
