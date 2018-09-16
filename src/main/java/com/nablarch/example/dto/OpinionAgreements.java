package com.nablarch.example.dto;

public class OpinionAgreements {

    private Integer opinionId;
    private String description;
    private Integer agreementCount;

    public Integer getOpinionId() {
        return opinionId;
    }

    public void setOpinionId(Integer opinionId) {
        this.opinionId = opinionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAgreementCount() {
        return agreementCount;
    }

    public void setAgreementCount(Integer agreementCount) {
        this.agreementCount = agreementCount;
    }
}
