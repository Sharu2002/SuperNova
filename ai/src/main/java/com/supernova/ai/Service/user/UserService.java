package com.supernova.ai.Service.user;

import com.supernova.ai.DTO.admin.AdminLoginDto;
import com.supernova.ai.DTO.user.UserLoginDto;
import com.supernova.ai.Repository.admin.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    AdminRepository adminRepository;


    public HttpStatus adminLogin(UserLoginDto userLoginDto){

        if(adminRepository.findByEmail(userLoginDto.getEmail()).isPresent()){

            if(adminRepository.findByEmail(userLoginDto.getEmail()).get().getPassword().equals(userLoginDto.getPassword())){
                return HttpStatus.OK;
            }
            else{
                return HttpStatus.UNAUTHORIZED;
            }
        }

        else{

            return HttpStatus.NOT_FOUND;
        }

    }
}
