package uz.pdp.appjparelationship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjparelationship.entity.Address;
import uz.pdp.appjparelationship.entity.University;
import uz.pdp.appjparelationship.payload.UniversityDto;
import uz.pdp.appjparelationship.repository.AddressRepository;
import uz.pdp.appjparelationship.repository.UniversityRepository;
import java.util.List;
import java.util.Optional;

@RestController
public class UniversityController {

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    AddressRepository addressRepository;

    //READ
    @RequestMapping(value = "/university", method = RequestMethod.GET)
    public List<University> getUniversities() {

        List<University> universityList = universityRepository.findAll();
        return universityList;
    }

    //Create
    @RequestMapping(value = "/university", method = RequestMethod.POST)
    public String addUniversity(@RequestBody UniversityDto dto) {

        Address address = new Address();
        address.setCity(dto.getCity());
        address.setDistrict(dto.getDistrict());
        address.setStreet(dto.getStreet());

        Address saveAddress = addressRepository.save(address);

        University university = new University();
        university.setName(dto.getName());

        university.setAddress(saveAddress);

        universityRepository.save(university);

        return "University added";
    }


    //Update
    @RequestMapping(value = "/university/{id}", method = RequestMethod.PUT)
    public String editUniversity(@PathVariable Integer id, @RequestBody UniversityDto dto) {
        Optional<University> optionalUniversity = universityRepository.findById(id);

        if (optionalUniversity.isPresent()) {
            University university = optionalUniversity.get();
            university.setName(dto.getName());
/*
        // Basadagi Address Tablega yana bitta Address oBjecti qöshiladi va shuning ID sini Universitet oladi

            Address address = new Address();
            address.setCity(dto.getCity());
            address.setDistrict(dto.getDistrict());
            address.setStreet(dto.getStreet());

            Address savedAddress = addressRepository.save(address);
            university.setAddress(savedAddress);

            universityRepository.save(university);
*/

          //Basadagi Universitet adresini olib,tahrirlaymiz
            Address address = university.getAddress();

            address.setCity(dto.getCity());
            address.setDistrict(dto.getDistrict());
            address.setStreet(dto.getStreet());

            addressRepository.save(address);
            universityRepository.save(university);

            return "University edited";
        }
        return "University not found";
    }


    //Delete
    @RequestMapping(value = "/universitet/{id}",method = RequestMethod.DELETE)
    public String deleteUniversity(@PathVariable Integer id){

        universityRepository.deleteById(id);
        return "University deleted";
    }


}

