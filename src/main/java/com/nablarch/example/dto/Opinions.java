package com.nablarch.example.dto;

import com.nablarch.example.entity.Theme;

import java.util.List;

public class Opinions {

    private final Theme theme;
    private final List<Agreements> agreements;

    public Opinions(Theme theme, List<Agreements> agreements) {
        this.theme = theme;
        this.agreements = agreements;

    }

    public Integer getThemeId() {
        return theme.getThemeId();
    }

    public String getTitle() {
        return theme.getTitle();
    }

    public List<Agreements> getAgreements() {
        return agreements;
    }
}
