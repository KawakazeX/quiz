package com.example.quiz.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "response")
public class Response {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "quiz_id")
    private int quizId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private int age;

    @Column(name = "fillin")
    private String fillin;

    @Column(name = "fillin_date_time")
    private LocalDateTime fillinDateTime = LocalDateTime.now();

    public Response() {
        super();
    }

    public Response(int quizId, String name, String phone, String email, int age, String fillin) {
        super();
        this.quizId = quizId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.fillin = fillin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFillin() {
        return fillin;
    }

    public void setFillin(String fillin) {
        this.fillin = fillin;
    }

    public LocalDateTime getFillinDateTime() {
        return fillinDateTime;
    }
}
