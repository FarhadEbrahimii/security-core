package com.sadad.orca.securitycore.exceptions;


public class ErrorData {

    private String name;
    private String summery;
    private String detail;

    public ErrorData(String name, String summery, String detail) {
        this.name = name;
        this.summery = summery;
        this.detail = detail;
    }

    public ErrorData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummery() {
        return summery;
    }

    public void setSummery(String summery) {
        this.summery = summery;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "ErrorDataEnum{" +
                "name='" + name + '\'' +
                ", summery='" + summery + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
