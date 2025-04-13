package com.example.userauthservice.services;

import com.example.userauthservice.exceptions.PasswordMismatchException;
import com.example.userauthservice.exceptions.UserAlreadyExistException;
import com.example.userauthservice.exceptions.UserNotRegisteredException;
import org.antlr.v4.runtime.misc.Pair;
import com.example.userauthservice.models.User;

public interface IAuthService {

    User signup(String email,String password) throws UserAlreadyExistException;

    Pair<User,String> login(String email, String password) throws UserNotRegisteredException, PasswordMismatchException;

    Boolean validateToken(String token,Long userId);
}
