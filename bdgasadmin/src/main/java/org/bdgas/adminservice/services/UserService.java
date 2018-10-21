/*****************************************************************************
 * Copyright 2018 Francois Botha                                             *
 *                                                                           *
 * Licensed under the Apache License, Version 2.0 (the "License");           *
 * you may not use this file except in compliance with the License.          *
 * You may obtain a copy of the License at                                   *
 *                                                                           *
 *  http://www.apache.org/licenses/LICENSE-2.0                               *
 *                                                                           *
 * Unless required by applicable law or agreed to in writing, software       *
 * distributed under the License is distributed on an "AS IS" BASIS,         *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *
 * See the License for the specific language governing permissions and       *
 * limitations under the License.                                            *
 *                                                                           *
 *****************************************************************************/
package org.bdgas.adminservice.services;

import org.bdgas.adminservice.domain.dao.UserRepository;
import org.bdgas.adminservice.domain.dto.UserDto;
import org.bdgas.adminservice.error.EntityNotFoundException;
import org.bdgas.adminservice.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(String id) throws EntityNotFoundException {
        
        User user = userRepository.findOneById(id);
        if(user == null){
            throw new EntityNotFoundException(User.class, "id", id.toString());
        }
        return user;
    }

    public List getAll() {

        List users = new ArrayList();

        Iterable<User> usersIt = userRepository.findAll();

        Iterator<User> iter = usersIt.iterator();

        while (iter.hasNext()) {
            users.add(iter.next());
        }

        return users;
    }

    public List getAllByOrgId(String id) throws EntityNotFoundException  {

        List users = new ArrayList();

        Iterable<User> usersIt = userRepository.findAllByOrgId(id);

        Iterator<User> iter = usersIt.iterator();

        while (iter.hasNext()) {
            users.add(iter.next());
        }

        if(users.isEmpty()
                || users.get(0) == null){
            throw new EntityNotFoundException(User.class, "id", id.toString());
        }

        return users;
    }

    public User getOne(String id) throws EntityNotFoundException {


        User user = userRepository.findOneById(id);

        return user;
    }

    public User getOneByAuthId(String id) throws EntityNotFoundException {


        User user = userRepository.findOneByAuthId(id);

        return user;
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(String id, UserDto userDto) throws EntityNotFoundException {

        User user = userRepository.findOneById(id);

        if(user == null){
            throw new EntityNotFoundException(User.class, "id", id.toString());
        }

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setAuthId(userDto.getAuthId());
        user.setOrgId("Default Org");

        return userRepository.save(user);
    }    
    
    public void delete(String id)  throws EntityNotFoundException {

        User user = userRepository.findOneById(id);

        userRepository.delete(user);
    }

}
