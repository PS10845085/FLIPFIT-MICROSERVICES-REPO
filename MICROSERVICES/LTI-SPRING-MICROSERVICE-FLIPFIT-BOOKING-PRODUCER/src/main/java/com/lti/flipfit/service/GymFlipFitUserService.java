
package com.lti.flipfit.service;

import java.util.List;
import com.lti.flipfit.entity.GymFlipFitUser;

public interface GymFlipFitUserService {
    List<GymFlipFitUser> getAllUsers();
    GymFlipFitUser getUserById(Long id);
    GymFlipFitUser createUser(GymFlipFitUser user);
    GymFlipFitUser updateUser(Long id, GymFlipFitUser user);
    String deleteUser(Long id);
}
