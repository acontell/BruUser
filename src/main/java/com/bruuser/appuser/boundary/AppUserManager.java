package com.bruuser.appuser.boundary;

import com.bruuser.appuser.entity.AppUser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AppUserManager {

    @PersistenceContext
    EntityManager em;
    
    public AppUser getById(long userId) {
        return this.em.find(AppUser.class, userId);
    }

    public void save(AppUser user) {

    }

    public void delete(long userId) {

    }
}
