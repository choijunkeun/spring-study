package com.fastcampus.ch3;

import java.util.Date;
import java.util.Objects;

public class User {
    private String id;
    private String pwd;
    private String name;
    private String email;
    private Date reg_date;
    private String sns;
    private Date birth;

    public User() {}
    public User(String id, String pwd, String name, String email, Date reg_date, String sns, Date birth) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.email = email;
        this.reg_date = reg_date;
        this.sns = sns;
        this.birth = birth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    public String getSns() {
        return sns;
    }

    public void setSns(String sns) {
        this.sns = sns;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(pwd, user.pwd) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(reg_date, user.reg_date) && Objects.equals(sns, user.sns) && Objects.equals(birth, user.birth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pwd, name, email, reg_date, sns, birth);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", reg_date=" + reg_date +
                ", sns='" + sns + '\'' +
                ", birth=" + birth +
                '}';
    }
}
