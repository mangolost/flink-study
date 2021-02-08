package com.mangolost.flink.mysql.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 *
 */
public class EmployeeAddDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String employeeNo;
    private String employeeName;
    private Integer age;
    private String position;
    private String degree;
    private String remark;

    public EmployeeAddDto() {

    }

    public EmployeeAddDto(String employeeNo, String employeeName, Integer age, String position, String degree, String remark) {
        this.employeeNo = employeeNo;
        this.employeeName = employeeName;
        this.age = age;
        this.position = position;
        this.degree = degree;
        this.remark = remark;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
