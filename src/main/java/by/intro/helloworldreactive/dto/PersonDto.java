package by.intro.helloworldreactive.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonDto implements Comparable<PersonDto> {

    private String name;
    private int age;
    private String email;

    @Override
    public int compareTo(PersonDto p) {

        return age == p.getAge() ? 0 : age > p.age ? 1 : -1;
    }
}
