package com.vn.newspaperbe.controller;

import com.vn.newspaperbe.payloads.ApiResponse;
import com.vn.newspaperbe.payloads.UserDTO;
import com.vn.newspaperbe.service.IUserService;
import com.vn.newspaperbe.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth/")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDTO>> getAllUsers()
    {
        List<UserDTO> users = iUserService.getAllUsers();
        return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile()
    {
        UserDTO userDTO = iUserService.getProfile();
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
        iUserService.delete(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
    }
}
