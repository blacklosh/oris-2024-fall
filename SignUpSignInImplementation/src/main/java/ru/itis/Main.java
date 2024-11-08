package ru.itis;

import ru.itis.dto.SignInRequest;
import ru.itis.dto.SignUpRequest;
import ru.itis.service.UserService;
import ru.itis.service.impl.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        //System.out.println(userService.signUp(new SignUpRequest("1teach.gusev@mail.ru", "aB1111", "teach1")));
        //System.out.println(userService.signIn(new SignInRequest("1teach.gusev@mail.ru", "aB1111")));
        //System.out.println(userService.signInByToken("275719d2-70cf-4e81-81db-7e2293388d89"));

        
    }
}