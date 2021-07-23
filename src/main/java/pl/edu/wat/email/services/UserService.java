package pl.edu.wat.email.services;


import pl.edu.wat.email.dtos.UserRequestDto;

public interface UserService {

    void addUser(UserRequestDto userRequestDto);
}
