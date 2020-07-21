package com.github.wangChen2345.sys.entiy;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "SYS_ROLE", catalog = "")
public class SysRole implements Serializable {
  private static final long serialVersionUID = -3091829915327296626L;
  private String roleId;
  private String roleCode;
  private String roleName;
  private boolean roleInvalid;
  private String description;
  private String creator;
  private Timestamp createdTim;
  private String changer;
  private Timestamp changedTim;

  private List<SysPermission> sysPermissions;

  private boolean ck;

  @Id
  @Column(name = "ROLE_ID")
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "uuid")
  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  @Basic
  @Column(name = "ROLE_CODE")
  public String getRoleCode() {
    return roleCode;
  }

  public void setRoleCode(String roleCode) {
    this.roleCode = roleCode;
  }

  @Basic
  @Column(name = "ROLE_NAME")
  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  @Basic
  @Column(name = "ROLE_INVALID")
  public boolean isRoleInvalid() {
    return roleInvalid;
  }

  public void setRoleInvalid(boolean roleInvalid) {
    this.roleInvalid = roleInvalid;
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
      name = "REF_ROLE_PERMISSION",
      joinColumns = {@JoinColumn(name = "REF_ROLE_ID", referencedColumnName = "ROLE_ID")},
      inverseJoinColumns = {
        @JoinColumn(name = "REF_PERMISSION_ID", referencedColumnName = "PERMISSION_ID")
      })
  @Where(clause = "PERMISSION_STATE=1")
  public List<SysPermission> getSysPermissions() {
    return sysPermissions;
  }

  public void setSysPermissions(List<SysPermission> sysPermissions) {
    this.sysPermissions = sysPermissions;
  }

  @Transient
  public boolean isCk() {
    return ck;
  }

  public void setCk(boolean ck) {
    this.ck = ck;
  }
}
