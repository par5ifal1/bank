package com.example.bankBackend.dto;

public class CardInputDto {
    private Long cardNum;
    private int cardDate;
    private int cardCVV;

    public Long getCardNum() {
        return cardNum;
    }

    public void setCardNum(Long cardNum) {
        this.cardNum = cardNum;
    }

    public int getCardDate() {
        return cardDate;
    }

    public void setCardDate(int cardDate) {
        this.cardDate = cardDate;
    }

    public int getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(int cardCVV) {
        this.cardCVV = cardCVV;
    }
}
