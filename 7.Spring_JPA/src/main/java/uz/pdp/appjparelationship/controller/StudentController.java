package uz.pdp.appjparelationship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationship.entity.*;
import uz.pdp.appjparelationship.payload.GroupDto;
import uz.pdp.appjparelationship.payload.StudentDto;
import uz.pdp.appjparelationship.repository.AddressRepository;
import uz.pdp.appjparelationship.repository.GroupRepository;
import uz.pdp.appjparelationship.repository.StudentRepository;
import uz.pdp.appjparelationship.repository.SubjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    SubjectRepository subjectRepository;


    // 1.Vazirlik
    @GetMapping("/forMinistry")
    public Page<Student>getStudentListForMinistry(@RequestParam int page){

       // DATA BASE DA SHUNDAY YOZAR EDIK
       // select * from student limit 10  offset (0*10) //1-10 ta sahifani körsatadi
       // select * from student limit 10  offset (1*10) //10-20 ta körsatadi
       // select * from student limit 10  offset (2*10) //20-30 ta körsatadi
       // select * from student limit 10  offset (3*10) //30-40 ta sahifani körsatadi

        Pageable pageable= PageRequest.of(page,2);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage;
    }

    //2.University rektori uchun
    @GetMapping("/forUniversity/{universityId}")
    public Page<Student>getStudentListForUniversity(@PathVariable Integer universityId, @RequestParam int page){

        Pageable pageable= PageRequest.of(page,10); // BU CLASS 10 TALIK STUDENT QILIB OLIBKELADI
        Page<Student> studentPage = studentRepository.findAllByGroup_Faculty_UniversityId(universityId, pageable);
        return studentPage;
    }


    // 3.Faculty dekan  uchun
    @GetMapping("/forFaculty/{facultyId}")
    public Page<Student>getStudentListForFaculty(@PathVariable Integer facultyId, @RequestParam int page){

        Pageable pageable=PageRequest.of(page,10);
        Page<Student> allByGroup_facultyId = studentRepository.findAllByGroup_FacultyId(facultyId, pageable);
        return allByGroup_facultyId;
    }


    //4.Guruh rahbari  uchun
    @GetMapping("/forGroup/{groupId}")
    public Page<Student>getStudentListForGroup(@PathVariable Integer groupId, @RequestParam int page){

        Pageable pageable=PageRequest.of(page,10);
        Page<Student> allByGroupId = studentRepository.findAllByGroupId(groupId, pageable);
        return allByGroupId;
    }

    //CREATE
    @PostMapping
    public String addStudent(@RequestBody StudentDto studentDto){

          Student student=new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());

        Address address=new Address();
        address.setStreet(studentDto.getStreet());
        address.setDistrict(studentDto.getDistrict());
        address.setCity(studentDto.getDistrict());
        Address savedAddress = addressRepository.save(address);
        student.setAddress(savedAddress);

        Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());
        if (!optionalGroup.isPresent())
           return "Group not found";
        student.setGroup(optionalGroup.get());

        List<Subject> subjects=new ArrayList<>();
        for (Integer subjectId : studentDto.getSubjectIds()) {
            Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
            if (optionalSubject.isPresent()){
                subjects.add(optionalSubject.get());
            }else {
                return subjectId+" Subject not found";
            }
        }
        student.setSubjects(subjects);
        studentRepository.save(student);
        return "Student added";
    }

    //EDIT
    @PutMapping("/{id}")
    public String editeStudent(@PathVariable Integer id,@RequestBody StudentDto studentDto){

        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(optionalStudent.isPresent()){
            Student student = optionalStudent.get();
            student.setFirstName(studentDto.getFirstName());
            student.setLastName(studentDto.getLastName());


            Address address=new Address();
            address.setStreet(studentDto.getStreet());
            address.setDistrict(studentDto.getDistrict());
            address.setCity(studentDto.getDistrict());
            Address savedAddress = addressRepository.save(address);
            student.setAddress(savedAddress);

            Optional<Group> optionalGroup = groupRepository.findById(studentDto.getGroupId());

            if (!optionalGroup.isPresent()){
                return "Group not found";
            }
            student.setGroup(optionalGroup.get());

            List<Subject> subjects=new ArrayList<>();
            for (Integer subjectId : studentDto.getSubjectIds()) {
                Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
                if (optionalSubject.isPresent()){
                    subjects.add(optionalSubject.get());
                }else {
                    return subjectId+" Subject not found";
                }
            }
            student.setSubjects(subjects);
            studentRepository.save(student);
            return "Student edited";
        }else {
            return "Student not found";
        }
    }

    //DELETE
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id){
        try {
            studentRepository.deleteById(id);
            return "Student deleted";
        }catch (Exception e){
            return "Error in deleting";
        }
    }




}
