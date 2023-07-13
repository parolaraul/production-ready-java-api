package com.parolaraul.recipeapi.service.criteria;

import java.util.ArrayList;
import java.util.List;

public class Filter<FIELD_TYPE> {
    private FIELD_TYPE eq;
    private FIELD_TYPE neq;
    private List<FIELD_TYPE> in;
    private List<FIELD_TYPE> nin;
    private FIELD_TYPE contains;
    private FIELD_TYPE notContains;

    public Filter() {
    }

    public Filter(Filter<FIELD_TYPE> filter) {
        this.eq = filter.eq;
        this.neq = filter.neq;
        this.in = filter.in == null ? null : new ArrayList<>(filter.in);
        this.nin = filter.nin == null ? null : new ArrayList<>(filter.nin);
        this.contains = filter.contains;
        this.notContains = filter.notContains;
    }

    public Filter<FIELD_TYPE> copy() {
        return new Filter<>(this);
    }

    public FIELD_TYPE getEq() {
        return eq;
    }

    public void setEq(FIELD_TYPE eq) {
        this.eq = eq;
    }

    public FIELD_TYPE getNeq() {
        return neq;
    }

    public void setNeq(FIELD_TYPE neq) {
        this.neq = neq;
    }

    public List<FIELD_TYPE> getIn() {
        return in;
    }

    public void setIn(List<FIELD_TYPE> in) {
        this.in = in;
    }

    public List<FIELD_TYPE> getNin() {
        return nin;
    }

    public void setNin(List<FIELD_TYPE> nin) {
        this.nin = nin;
    }

    public FIELD_TYPE getContains() {
        return contains;
    }

    public void setContains(FIELD_TYPE contains) {
        this.contains = contains;
    }

    public FIELD_TYPE getNotContains() {
        return notContains;
    }

    public void setNotContains(FIELD_TYPE notContains) {
        this.notContains = notContains;
    }
}
