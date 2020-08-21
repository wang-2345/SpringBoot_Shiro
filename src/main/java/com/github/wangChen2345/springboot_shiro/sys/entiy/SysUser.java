package com.github.wangChen2345.springboot_shiro.sys.entiy;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "SYS_USER", catalog = "")
public class SysUser implements Serializable {
  private static final long serialVersionUID = 6766500281593842879L;
  private String userId;
  private String userCode;
  private String userName;
  private String password;
  private boolean userInvalid;
  private String description;
  private String creator;
  private Timestamp createdTim;
  private String changer;
  private Timestamp changedTim;

  private List<SysRole> sysRoles;

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "uuid")
  @Column(name = "USER_ID")
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @Basic
  @Column(name = "USER_CODE")
  public String getUserCode() {
    return userCode;
  }

  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }

  @Basic
  @Column(name = "USER_NAME")
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Basic
  @Column(name = "PASSWORD")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Basic
  @Column(name = "USER_INVALID")
  public boolean isUserInvalid() {
    return userInvalid;
  }

  public void setUserInvalid(boolean userInvalid) {
    this.userInvalid = userInvalid;
  }

  @Basic
  @Column(name = "DESCRIPTION")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Basic
  @Column(name = "CREATOR")
  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  @Basic
  @Column(name = "CREATED_TIM")
  public Timestamp getCreatedTim() {
    return createdTim;
  }

  public void setCreatedTim(Timestamp createdTim) {
    this.createdTim = createdTim;
  }

  @Basic
  @Column(name = "CHANGER")
  public String getChanger() {
    return changer;
  }

  public void setChanger(String changer) {
    this.changer = changer;
  }

  @Basic
  @Column(name = "CHANGED_TIM")
  public Timestamp getChangedTim() {
    return changedTim;
  }

  public void setChangedTim(Timestamp changedTim) {
    this.changedTim = changedTim;
  }

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(
      name = "REF_USER_ROLE",
      joinColumns = {@JoinColumn(name = "REF_USER_ID", referencedColumnName = "USER_ID")},
      inverseJoinColumns = {@JoinColumn(name = "REF_ROLE_ID", referencedColumnName = "ROLE_ID")})
  @Where(clause = "ROLE_INVALID=0")
  public List<SysRole> getSysRoles() {
    return sysRoles;
  }

  public void setSysRoles(List<SysRole> sysRoles) {
    this.sysRoles = sysRoles;
  }
}
