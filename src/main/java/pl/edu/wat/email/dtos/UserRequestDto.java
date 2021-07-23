package pl.edu.wat.email.dtos;

import lombok.Getter;

@Getter
public class UserRequestDto {
    private String name;
    private String surname;
    private String email;
    private Boolean active;
}
