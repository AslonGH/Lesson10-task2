package uz.pdp.appjparelationship.repository;

import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appjparelationship.entity.Group;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group,Integer> {

      List<Group>findAllByFaculty_UniversityId(Integer faculty_university_id);

    // IKKINCHI USUL :- universityId si, biz bergan universityId ga bölgan, faculty table ning Guruhlari ni qaytaradi
    @Query (" select gr from groups  gr where  gr.faculty.university.id=:universityId") //.id=?1 (:universityId)
    List<Group>getGroupsByUniversityId(Integer universityId);


    // UCHINCHI USUL NATIVE QUERY ORQALI
      @Query(value = "select *\n" +
                     "from groups  g\n" +
                    " join faculty f on f.id = g.faculty_id\n" +
                    " join university u on u.id = f.university_id\n" +
                    " where u.id=:universityId", nativeQuery = true)
     //Query QAYTARGA GROUPS LISTINI SHU METHODGA OLAMIZ
    List<Group>getGroupsByUniversityIdNative(Integer universityId);

}
