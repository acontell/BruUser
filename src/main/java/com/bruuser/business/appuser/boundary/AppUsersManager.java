package com.bruuser.business.appuser.boundary;

import com.bruuser.business.appuser.entity.AppUser;
import com.bruuser.business.security.PasswordHash;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

@Stateless
public class AppUsersManager {

    @PersistenceContext
    EntityManager em;

    @Inject
    Validator validator;

    public AppUser getByUserName(String userName) {
        return this.em.find(AppUser.class, userName);
    }

    public AppUser update(AppUser storedUser, AppUser newUser) {
        return merge(updatePasswordField(storedUser, newUser));
    }

    public AppUser merge(AppUser user) {
        return this.em.merge(checkConstraints(user));
    }

    private AppUser updatePasswordField(AppUser stored, AppUser newUser) {
        if (!isPasswordModificationRequest(newUser)) {
            updateNewUserWithOldSaltedPassword(stored, newUser);
        }
        return newUser;
    }

    private boolean isPasswordModificationRequest(AppUser newUser) {
        return newUser.getPassword() != null;
    }

    private void updateNewUserWithOldSaltedPassword(AppUser stored, AppUser newUser) {
        newUser.setPassword(stored.getPassword());
        newUser.setHasPasswordBeenEncrypted(true);
    }

    private AppUser checkConstraints(AppUser user) {
        assertIsEmpty(this.validator.validate(user));
        return user;
    }

    private void assertIsEmpty(Set<ConstraintViolation<AppUser>> violatedConstraints) {
        if (!violatedConstraints.isEmpty()) {
            throw new ConstraintViolationException(violatedConstraints);
        }
    }

    public void delete(String userName) {
        try {
            this.em.remove(this.em.getReference(AppUser.class, userName));
        } catch (EntityNotFoundException ex) {
            // Trying to delete something that doesn't exist is ok for me.
        }
    }

    public List<AppUser> getAll() {
        return this.em
                .createNamedQuery(AppUser.FIND_ALL, AppUser.class)
                .getResultList();
    }

    public boolean isAuthenticatedUser(String userName, String password) {
        return isValidAuthenticationRequest(userName, password)
                && isUserStoredWithSameCredentials(userName, password);
    }

    private boolean isUserStoredWithSameCredentials(String userName, String password) {
        return Optional.ofNullable(getByUserName(userName))
                .map(AppUser::getPassword)
                .map(storedPassword -> PasswordHash.validatePassword(password, storedPassword))
                .orElse(false);
    }

    private boolean isValidAuthenticationRequest(String userName, String password) {
        return userName != null && password != null;
    }
}
