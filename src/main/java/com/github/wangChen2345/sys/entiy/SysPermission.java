package com.github.wangChen2345.sys.entiy;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "SYS_PERMISSION", catalog = "")
public class SysPermission implements Serializable {
  private static final long serialVersionUID = -2286808921745271405L;
  private String id;
  private String parentId;
  private String code;
  private String text;
  private String permissionType;
  private boolean permissionInvalid;
  private String description;
  private String state;
  private String iconcls;
  private String href;
  private int sorted;
  private String creator;
  private Timestamp createdTim;
  private String changer;
  private Timestamp changedTim;

  private List<SysPermission> children;
  private boolean checked;

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "uuid")
  @Column(name = "PERMISSION_ID")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Basic
  @Column(name = "PERMISSION_PARENTID")
  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  @Basic
  @Column(name = "PERMISSION_CODE")
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Basic
  @Column(name = "PERMISSION_NAME")
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Basic
  @Column(name = "PERMISSION_TYPE")
  public String getPermissionType() {
    return permissionType;
  }

  public void setPermissionType(String permissionType) {
    this.permissionType = permissionType;
  }

  @Basic
  @Column(name = "PERMISSION_INVALID")
  public boolean isPermissionInvalid() {
    return permissionInvalid;
  }

  public void setPermissionInvalid(boolean permissionInvalid) {
    this.permissionInvalid = permissionInvalid;
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
  @Column(name = "MENU_STATE")
  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  @Basic
  @Column(name = "MENU_ICONCLS")
  public String getIconcls() {
    return iconcls;
  }

  public void setIconcls(String iconcls) {
    this.iconcls = iconcls;
  }

  @Basic
  @Column(name = "PERMISSION_URL")
  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  @Basic
  @Column(name = "SORTED")
  public int getSorted() {
    return sorted;
  }

  public void setSorted(int sorted) {
    this.sorted = sorted;
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

  @Transient
  public List<SysPermission> getChildren() {
    return children;
  }

  public void setChildren(List<SysPermission> children) {
    this.children = children;
  }

  @Transient
  public boolean isChecked() {
    return checked;
  }

  public void setChecked(boolean checked) {
    this.checked = checked;
  }
}
