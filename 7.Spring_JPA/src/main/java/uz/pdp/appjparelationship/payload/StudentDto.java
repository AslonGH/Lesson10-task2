package uz.pdp.appjparelationship.payload;

import lombok.Data;

import java.util.List;

@Data
public class StudentDto {

    private String  firstName;
    private String  lastName;

    private String  street;
    private String  city;
    private String  district;

    private List<Integer> subjectIds;

    private Integer groupId;

}
