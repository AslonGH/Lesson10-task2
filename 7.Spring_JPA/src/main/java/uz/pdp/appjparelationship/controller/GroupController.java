package uz.pdp.appjparelationship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationship.entity.Faculty;
import uz.pdp.appjparelationship.entity.Group;
import uz.pdp.appjparelationship.payload.GroupDto;
import uz.pdp.appjparelationship.repository.FacultyRepository;
import uz.pdp.appjparelationship.repository.GroupRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    FacultyRepository facultyRepository;

    // VAZIRLIK UCHUN
    // READ
    @GetMapping
    public List<Group> getGroups(){
        List<Group> groups = groupRepository.findAll();
        return groups;
    }


    //UNIVERSITET MASUL XODIMI UCHUN
    @GetMapping("/byUniversityId/{universityId}")
    public List<Group>getGroupsByUniversityId(@PathVariable Integer universityId){

       List<Group> allByFaculty_universityId = groupRepository.findAllByFaculty_UniversityId(universityId);
       List<Group> groupsByUniversityId = groupRepository.getGroupsByUniversityId(universityId);
       List<Group> groupsByUniversityIdNative = groupRepository.getGroupsByUniversityIdNative(universityId);
      // return allByFaculty_universityId;
       return groupsByUniversityIdNative;
   }


   //CREATE
    @PostMapping
    public String addGroup(@RequestBody GroupDto groupDto){

        Group group=new Group();
        group.setName(groupDto.getName());

        Optional<Faculty> facultyOptional = facultyRepository.findById(groupDto.getFacultyId());
        if (!facultyOptional.isPresent()) {
            return "such faculty not found";
        }
        group.setFaculty(facultyOptional.get());
        groupRepository.save(group);
        return "Group added";
    }

   //EDIT
    @PutMapping("/{id}")
    public String editeGroup(@PathVariable Integer id,@RequestBody GroupDto groupDto){

        Optional<Group> optionalGroup = groupRepository.findById(id);
        if(optionalGroup.isPresent()){
            Group group = optionalGroup.get();
            group.setName(groupDto.getName());
            Optional<Faculty> optionalFaculty = facultyRepository.findById(groupDto.getFacultyId());
            if (!optionalFaculty.isPresent()){
                return "Faculty not found";
            }
              group.setFaculty(optionalFaculty.get());
              groupRepository.save(group);
              return "Group edited";
            }else {
            return "Group not found";
            }
    }

    //DELETE
    @DeleteMapping("/{id}")
    public String deleteGroup(@PathVariable Integer id){
        try {
            groupRepository.deleteById(id);
            return "Group deleted";
        }catch (Exception e){
            return "Error in deleting";
        }
    }


}
