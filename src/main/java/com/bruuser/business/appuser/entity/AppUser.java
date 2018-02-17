package com.bruuser.business.appuser.entity;

import static com.bruuser.business.validation.StringValidation.isNotEmptyAlphaNumericOnly;
import static com.bruuser.business.validation.StringValidation.isNotEmptyAtLeastOneDigitAndUpperCase;
import static com.bruuser.business.validation.StringValidation.isNotEmptyCharsAndSpacesOnly;
import com.bruuser.business.validation.ValidEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@NamedQuery(name = AppUser.FIND_ALL, query = "SELECT t FROM AppUser t")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name = "APPUSER")
public class AppUser implements ValidEntity, Serializable {

    private static final String PREFIX = "appuser.entity.AppUser.";
    public static final String FIND_ALL = PREFIX + "findAll";

    @Version
    private long version;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "FULL_NAME")
    private String fullName;

    @NotNull
    @Id
    @Size(min = 1, max = 20)
    @Column(name = "USER_NAME")
    private String userName;

    @NotNull
    @Size(min = 8)
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "LAST_UPDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        // Even though new dates come from OF, they will be overridden (therefore they're not editable).
        lastUpdate = new Date();
    }

    public AppUser() {
    }

    public AppUser(String userName) {
        this(userName, null, null);
    }

    public AppUser(String userName, String fullName, String password) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public boolean isValid() {
        return isNotEmptyAlphaNumericOnly(userName)
                && isNotEmptyCharsAndSpacesOnly(fullName)
                && isNotEmptyAtLeastOneDigitAndUpperCase(password);
    }
}
