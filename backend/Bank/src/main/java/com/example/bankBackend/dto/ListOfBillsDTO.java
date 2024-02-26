package com.example.bankBackend.dto;

import java.util.List;

public class ListOfBillsDTO {
    private List<BillsDTO> billsDTOList;

    public List<BillsDTO> getBillsDTOList() {
        return billsDTOList;
    }

    public void setBillsDTOList(List<BillsDTO> billsDTOList) {
        this.billsDTOList = billsDTOList;
    }
}
