package com.vn.newspaperbe.service;
import com.vn.newspaperbe.entity.DAOUser;
import com.vn.newspaperbe.entity.News;
import com.vn.newspaperbe.payloads.UserDTO;
public interface IUserService extends IGeneralService<DAOUser> {
//    UserDTO getUserByName(String name);
    UserDTO getUserByUsername(String username);


}
