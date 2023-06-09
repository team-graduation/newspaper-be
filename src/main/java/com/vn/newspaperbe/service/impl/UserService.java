package com.vn.newspaperbe.service.impl;

import com.vn.newspaperbe.entity.DAOUser;
import com.vn.newspaperbe.entity.News;
import com.vn.newspaperbe.exceptions.ResourceNotFoundException;
import com.vn.newspaperbe.payloads.NewsDTO;
import com.vn.newspaperbe.payloads.UserDTO;
import com.vn.newspaperbe.repository.INewsRepository;
import com.vn.newspaperbe.repository.IUserRepository;
import com.vn.newspaperbe.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        List<DAOUser> users = this.iUserRepository.findAll();
        List<UserDTO> userDTOS = users.stream().map((p) -> this.modelMapper.map(p, UserDTO.class)).collect(Collectors.toList());
        return userDTOS;
    }

    @Override
    public Iterable<DAOUser> findAll() {
        return null;
    }

    @Override
    public Optional findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public DAOUser save(DAOUser daoUser) {
        return null;
    }


    @Override
    public void delete(Integer id) {

    }

//    @Override
//    public UserDTO getUserByName(String name) {
////        return null;
////        News news = this.iNewsRepository.findById(newsId).orElseThrow(() -> new ResourceNotFoundException("News", "Id", newsId));
////        return this.modelMapper.map(newsId, NewsDTO.class);
//        DAOUser user = this.iUserRepository.findByUsername(name);
//        return this.modelMapper.map(user, UserDTO.class);
//    }


    @Override
    public UserDTO getUserByUsername(String username) {
        DAOUser user = this.iUserRepository.findByUsername(username);
        return this.modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserById(Integer id) {
        DAOUser user = this.iUserRepository.findDAOUserById(id);
        
        return this.modelMapper.map(user, UserDTO.class);
    }

    //    @Override
//    public UserDTO findByUsername(String username) {
//        return null;
//    }



}
