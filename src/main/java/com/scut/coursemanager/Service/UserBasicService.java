package com.scut.coursemanager.Service;

import com.scut.coursemanager.Exception.ModifyException;
import com.scut.coursemanager.Exception.UserRegisterException;
import com.scut.coursemanager.dto.RegisterRequest;

public interface UserBasicService {
    void UserRegister(RegisterRequest registerRequest) throws UserRegisterException;

    void modifyPassword(String password) throws ModifyException;
}
