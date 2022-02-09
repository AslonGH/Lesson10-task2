package uz.pdp.appjparelationship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationship.entity.Faculty;
import uz.pdp.appjparelationship.entity.University;
import uz.pdp.appjparelationship.payload.FacultyDto;
import uz.pdp.appjparelationship.repository.FacultyRepository;
import uz.pdp.appjparelationship.repository.UniversityRepository;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/faculty")
public class FacultyController {

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    UniversityRepository universityRepository;


    @PostMapping
    public String addFaculty(@RequestBody FacultyDto facultyDto) {

    boolean exists = facultyRepository.existsByNameAndUniversityId(facultyDto.getName(),facultyDto.getUniversityId());
    if (exists)
    return "This University such faculty exist";

    Faculty faculty = new Faculty();
    faculty.setName(facultyDto.getName());

    Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getUniversityId());
    if (!optionalUniversity.isPresent())
        return "University not found";
        faculty.setUniversity(optionalUniversity.get());
        facultyRepository.save(faculty);
        return "Faculty saved";
    }


    //VAZIRLIK UCHUN . Bu butun respublika Universitet/i  fakultet/ini oladi
    @GetMapping
    public List<Faculty>getFaculties(){
        return facultyRepository.findAll();
    }


    //UNIVERSITET XODIMI UCHUN.BU faqat özining Universiteti fakultet/ini oladi
    @GetMapping("/byUniversityId/{universityId}")
    public List<Faculty> getFacultiesByUniversityId(@PathVariable Integer universityId){
         List<Faculty>allByUniversityId=facultyRepository.findAllByUniversityId(universityId);
         return allByUniversityId;
    }


    @DeleteMapping("/{id}")
    public String deleteFaculty(@PathVariable Integer id) {
        try {
            facultyRepository.deleteById(id);
            return "Faculty deleted";
        } catch (Exception e) {
            return "Error in deleting";
        }
    }


    @PutMapping("/{id}")
    public String editFaculty(@PathVariable Integer id,@RequestBody FacultyDto facultyDto){

        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isPresent()){
            Faculty faculty = optionalFaculty.get();
            faculty.setName(facultyDto.getName());
            Optional<University> optionalUniversity = universityRepository.findById(id);
            if (!optionalUniversity.isPresent()){
                return "University not found";
            }
            faculty.setUniversity(optionalUniversity.get());
            facultyRepository.save(faculty);
            return "Faculty edited";
        }
        return "Faculty not found";
    }


}


