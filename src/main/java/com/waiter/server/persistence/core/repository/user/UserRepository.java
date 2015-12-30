package com.waiter.server.persistence.core.repository.user;

import com.waiter.server.services.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Admin on 12/23/2015.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String login, String password);

    User findUserByToken(String token);

    User findUserByHash(String hash);
}
