package uz.pdp.appjparelationship.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity


                       // AGGREGATION- Student DEPEND ON Address and subjects
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;


    @OneToOne
    private Address address;


    @ManyToMany
    private List<Subject> subjects;


    @ManyToOne
    private Group group;

}
