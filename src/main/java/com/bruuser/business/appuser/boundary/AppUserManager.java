package com.bruuser.business.appuser.boundary;

import com.bruuser.business.appuser.entity.AppUser;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Stateless
public class AppUserManager {

    @PersistenceContext
    EntityManager em;

    public AppUser getByUserName(String userName) {
        return this.em.find(AppUser.class, userName);
    }

    public AppUser save(AppUser user) {
        return this.em.merge(user);
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
