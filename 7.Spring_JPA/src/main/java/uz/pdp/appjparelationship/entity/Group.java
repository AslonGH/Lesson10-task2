package uz.pdp.appjparelationship.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "groups")

public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String  name;

    @ManyToOne
    private Faculty faculty;


   /* @OneToMany
    private List<Student>students;*/


}
