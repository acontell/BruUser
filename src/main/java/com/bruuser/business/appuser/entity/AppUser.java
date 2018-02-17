package com.bruuser.business.appuser.entity;

import com.bruuser.business.adapters.HalfDuplexXmlAdapter;
import com.bruuser.business.security.PasswordHash;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.bruuser.business.validation.CrossCheck;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@NamedQuery(name = AppUser.FIND_ALL, query = "SELECT t FROM AppUser t")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@CrossCheck
@Table(name = "APPUSER")
public class AppUser implements ValidEntity, Serializable {

    private static final String PREFIX = "appuser.entity.AppUser.";
    public static final String FIND_ALL = PREFIX + "findAll";

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "FULL_NAME")
    private String fullName;

    @NotNull
    @Id
    @Size(min = 1, max = 20)
    @Column(name = "USER_NAME")
    private String userName;

    @Size(min = 8)
    @Column(name = "PASSWORD")
    @XmlJavaTypeAdapter(HalfDuplexXmlAdapter.class)
    private String password;

    @Column(name = "LAST_UPDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    @Transient
    @XmlTransient
    private boolean hasPasswordBeenEncrypted;

    @PreUpdate
    @PrePersist
    public void updateCalculatedFields() {
        this.lastUpdate = new Date();
        if (!this.hasPasswordBeenEncrypted) {
            this.password = PasswordHash.createHash(this.password);
            setHasPasswordBeenEncrypted(true);
        }
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

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isHasPasswordBeenEncrypted() {
        return hasPasswordBeenEncrypted;
    }

    public void setHasPasswordBeenEncrypted(boolean hasPasswordBeenEncrypted) {
        this.hasPasswordBeenEncrypted = hasPasswordBeenEncrypted;
    }

    @Override
    public boolean isValid() {
        return isNotEmptyAlphaNumericOnly(userName)
                && isNotEmptyCharsAndSpacesOnly(fullName)
                && checkPassword();
    }

    private boolean checkPassword() {
        return this.hasPasswordBeenEncrypted || isNotEmptyAtLeastOneDigitAndUpperCase(password);
    }
}
