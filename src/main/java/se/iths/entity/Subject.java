package se.iths.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull @NotEmpty @Size(min = 2)
    private String name;
    private LocalDate createDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Teacher teacher;

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Student> students = new ArrayList<>();

    public Subject(String name) {
        this.name = name;
    }

    public Subject() {
    }
    @PrePersist
    public void getCurrentDate(){
        setCreateDate(LocalDate.now());
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
