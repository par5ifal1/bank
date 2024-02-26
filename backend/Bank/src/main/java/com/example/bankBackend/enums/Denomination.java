package com.example.bankBackend.enums;

import java.util.Objects;

public enum Denomination {
    ONE(1), TWO(2), FIVE(5), TEN(10), TWENTY(20), FIFTY(50),
    HUNDRED(100), TWO_HUNDRED(200), FIVE_HUNDRED(500), THOUSAND(1000);
    private int value;

    Denomination(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static Denomination getDenominationByValue(int value){
        for (Denomination denomination: Denomination.values()){
            if (Objects.equals(denomination.value, value)){
                return denomination;
            }
        }
        return null;
    }
}
