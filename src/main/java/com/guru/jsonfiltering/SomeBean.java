package com.guru.jsonfiltering;

import com.fasterxml.jackson.annotation.JsonFilter;


//static filtring :
//@JsonIgnoreProperties(value={"field1"})
@JsonFilter("SomeBeanFilter")
public class SomeBean {
    private String name;
    private String phone;
//JsonIgnore indicates that the annotated method or field is to be ignored  
//@JsonIgnore  
    private String salary;

//generating constructor  
    public SomeBean(String name, String phone, String salary) {
        super();
        this.name = name;
        this.phone = phone;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
