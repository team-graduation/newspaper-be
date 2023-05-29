package com.vn.newspaperbe.controller;

import com.vn.newspaperbe.config.JwtTokenUtil;
import com.vn.newspaperbe.entity.DAOUser;
import com.vn.newspaperbe.payloads.JwtRequest;
import com.vn.newspaperbe.payloads.JwtResponse;
import com.vn.newspaperbe.payloads.UserDTO;
import com.vn.newspaperbe.service.INewsService;
import com.vn.newspaperbe.service.IUserService;
import com.vn.newspaperbe.service.impl.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(user));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        final UserDTO userDTO = this.iUserService.getUserByUsername(authenticationRequest.getUsername());
        Hashtable<String, Object> result = new Hashtable<String, Object>();
        result.put("info",userDTO);
        result.put("token",new JwtResponse(token).getToken());
        return ResponseEntity.ok(result);
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
