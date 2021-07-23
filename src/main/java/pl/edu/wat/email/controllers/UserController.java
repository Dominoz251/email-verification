package pl.edu.wat.email.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.email.dtos.UserRequestDto;
import pl.edu.wat.email.services.EmailService;
import pl.edu.wat.email.services.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @PostMapping(path = "/api/user")
    public ResponseEntity addUser(@RequestBody UserRequestDto userRequestDto)
    {
        userService.addUser(userRequestDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @GetMapping(path = "/api/confirmation")
    public ResponseEntity<String> confirmedEmail(@RequestParam("token") String token)
    {
        return new ResponseEntity(emailService.confirmEmail(token), HttpStatus.OK);
    }
}
