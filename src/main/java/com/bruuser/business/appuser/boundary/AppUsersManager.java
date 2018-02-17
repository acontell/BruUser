package com.bruuser.business.appuser.boundary;

import com.bruuser.business.appuser.entity.AppUser;
import java.util.List;
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
        if (newUser.getPassword() == null) {
            newUser.setPassword(stored.getPassword());
            newUser.setHasPasswordBeenEncrypted(true);
        }
        return newUser;
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
}
