package com.eren.aethra.enums;

public enum OrderStatus {

    CREATED ("Oluşturuldu"),
    RECEIVED ("Alındı"),
    IN_PREPARATION ("Hazırlanıyor"),
    SHIPPING ("Kargoye Verildi"),
    DELIVERED ("Teslim Edildi"),
    COMPLETED ("Tamamlandı"),
    RETURN_REQUEST_RECEIVED ("İade İsteği Alındı"),
    RETURNED ("İade Edildi"),
    CANCEL_REQUEST_RECEIVED ("İptal İsteği Alındı"),
    CANCELLED ("İptal Edildi"),
    AT_THE_DOOR ("Kapıda Ödeme");

    private final String name;

    private OrderStatus(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }

}