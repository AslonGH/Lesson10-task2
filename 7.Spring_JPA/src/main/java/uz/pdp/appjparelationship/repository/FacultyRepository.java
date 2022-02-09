package uz.pdp.appjparelationship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appjparelationship.entity.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty,Integer> {


    boolean existsByNameAndUniversityId(String name, Integer university_id);


    //UNIVERSITET XODIMI UCHUN.BU faqat özining Universiteti fakultet/ini oladi
    List<Faculty> findAllByUniversityId(Integer university_id);

}
