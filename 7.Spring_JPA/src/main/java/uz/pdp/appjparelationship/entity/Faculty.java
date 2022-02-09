package uz.pdp.appjparelationship.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity


@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name","university_id"}))

                            // AGGREGATION- Faculty DEPEND ON University
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;

    private String name;

    @ManyToOne
    private University university;

}
