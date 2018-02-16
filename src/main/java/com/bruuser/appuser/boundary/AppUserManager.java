package com.bruuser.appuser.boundary;

import com.bruuser.appuser.entity.AppUser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Stateless
public class AppUserManager {

    @PersistenceContext
    EntityManager em;

    public AppUser getById(long userId) {
        return this.em.find(AppUser.class, userId);
    }

    public AppUser save(AppUser user) {
        return this.em.merge(user);
    }

    public void delete(long userId) {
        try {
            this.em.remove(this.em.getReference(AppUser.class, userId));
        } catch (EntityNotFoundException ex) {
            // Trying to delete something that doesn't exist is ok for me.
        }
    }
}
