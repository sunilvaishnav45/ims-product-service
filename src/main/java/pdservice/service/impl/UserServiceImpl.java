package pdservice.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pdservice.entity.User;
import pdservice.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {


    @Override
    public User getLoggedInUser() {
        //TODO : Update your logic here
        return null;
    }

    @Override
    public boolean userHasReadPermission(User user) {
        //TODO : Update your logic here
        return true;
    }

    @Override
    public boolean userHasWritePermission(User user) {
        //TODO : Update your logic here
        return true;
    }
}
