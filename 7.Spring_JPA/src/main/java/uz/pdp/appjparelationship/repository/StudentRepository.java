package uz.pdp.appjparelationship.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appjparelationship.entity.Student;

public interface StudentRepository extends JpaRepository<Student,Integer>{

/*
select * from student join groups on groups.id=student.group_id join faculty on
groups.faculty_id=faculty.id join university on faculty.university_id=university_Id
*/


    Page<Student>findAllByGroup_Faculty_UniversityId(Integer group_faculty_university_id, Pageable pageable);


    Page<Student>findAllByGroup_FacultyId(Integer group_faculty_id, Pageable pageable);


    Page<Student>findAllByGroupId(Integer group_id, Pageable pageable);


   // boolean existsBySubjectListId(Integer subject_list_id);


}
