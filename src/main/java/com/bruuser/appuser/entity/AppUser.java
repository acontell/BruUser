package com.bruuser.appuser.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(name = "APPUSER")
public class AppUser implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USER_NAME")
    private String userName;

}
