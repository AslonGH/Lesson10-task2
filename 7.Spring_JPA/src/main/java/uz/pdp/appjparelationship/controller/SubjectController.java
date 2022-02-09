package uz.pdp.appjparelationship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationship.entity.Subject;
import uz.pdp.appjparelationship.payload.UniversityDto;
import uz.pdp.appjparelationship.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {

    @Autowired
    SubjectRepository subjectRepository;

    //CREATE
    @RequestMapping(method = RequestMethod.POST)
    public String addSubject(@RequestBody Subject subject) {

        //SUBJECT CLASSDA; BIR FAN MO da 2 MARTA KELMASIN, DEB UNIQUE QÖYGANMIZ,endi TEKSHIRIB OLAMIZ
        boolean existsByName = subjectRepository.existsByName(subject.getName());
        if (existsByName) {
            return "Subject already exist";
        } else {
            subjectRepository.save(subject);
            return "added subject";
        }
    }

    //RED
    // @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public List<Subject>getSubject(){
        List<Subject>subjectList=subjectRepository.findAll();
        return subjectList;
    }


    // @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @PutMapping(value = "/{id}")
    public String editSubject(@PathVariable Integer id, @RequestBody Subject subject) {

          Optional<Subject> repositoryById = subjectRepository.findById(id);
          // SUBJECT CLASSIDA UNIQUE BORLIGI U-N TEKSHIRIB OLAMIZ
          boolean existsByName = subjectRepository.existsByName(subject.getName());

          if (existsByName) {
              return "Subject already exist";
          } else {
              if (repositoryById.isPresent()) {
                  Subject subject1 = repositoryById.get();
                  subject1.setName(subject.getName());
                  subjectRepository.save(subject1);
                  return "Subject edited";
              }
              return "Subject not found";
          }
     }


    @DeleteMapping(value = "/{id}")
    public String deleteSubject(@PathVariable Integer id){
         subjectRepository.deleteById(id);
         return "Subject deleted";
    }

}
