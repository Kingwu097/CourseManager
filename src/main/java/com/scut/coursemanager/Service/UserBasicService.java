package com.scut.coursemanager.Service;

import com.scut.coursemanager.Exception.UserRegisterException;
import com.scut.coursemanager.dto.RegisterRequest;

public interface UserBasicService {
    void UserRegister(RegisterRequest registerRequest) throws UserRegisterException;
}
