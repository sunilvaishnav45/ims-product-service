package pdservice.service;

import pdservice.entity.User;

public interface UserService {

    public User getLoggedInUser();

    public boolean userHasReadPermission(User user);

    public boolean userHasWritePermission(User user);

}
