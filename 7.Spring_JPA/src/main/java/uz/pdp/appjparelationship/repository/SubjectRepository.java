package uz.pdp.appjparelationship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appjparelationship.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject,Integer> {

   // Browserdan berilgan name lilik Subject Objecti(Tableda Satr) ni  Subject Tabledan qidiradi.
    boolean existsByName(String name);

}
