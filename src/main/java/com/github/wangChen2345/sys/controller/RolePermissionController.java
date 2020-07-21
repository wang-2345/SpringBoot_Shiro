package com.github.wangChen2345.sys.controller;

import com.ce.sys.entiy.SysPermission;
import com.ce.sys.entiy.SysRole;
import com.ce.sys.service.SysPermissionService;
import com.ce.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("RolePermissionControl")
public class RolePermissionController {
  @Autowired SysRoleService sysRoleService;
  @Autowired SysPermissionService sysPermissionService;

  @RequestMapping(value = "/rolePermission.htm")
  public String rolePermission(Model model) {
    return "base/rolePermission";
  }

  @RequiresPermissions("rolePermission:save")
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  @ResponseBody
  public SysRole save(@RequestBody SysRole sysRole) {
    return sysRoleService.save(sysRole);
  }

  @RequiresPermissions("rolePermission:find")
  @RequestMapping(value = "/find", method = RequestMethod.POST)
  @ResponseBody
  public List<SysPermission> findAllTreeBypermissionInvalid(String roleCode) {
    List<SysPermission> sysPermissions =
        findByParentIdAndPermissionInvalidOrderBySortedAsc("0", false);
    if (roleCode != null) {
      for (SysPermission sysPermission :
          sysRoleService.findByRoleCode(roleCode).getSysPermissions()) {
        isCheck(sysPermissions, sysPermission);
      }
    }
    return sysPermissions;
  }
  // 判断当前角色哪些被选中
  public void isCheck(List<SysPermission> sysPermissions, SysPermission sysPermission) {
    for (SysPermission sysPermission1 : sysPermissions) {
      if (sysPermission.getId().equals(sysPermission1.getId())) {
        sysPermission1.setChecked(true);
        break;
      } else if (sysPermission1.getChildren() != null) {
        isCheck(sysPermission1.getChildren(), sysPermission);
      }
    }
  }

  // 树
  public List<SysPermission> findByParentIdAndPermissionInvalidOrderBySortedAsc(
      String parentId, boolean permissionInvalid) {
    List<SysPermission> sysPermissions =
        sysPermissionService.findByParentIdAndPermissionInvalidOrderBySortedAsc(
            parentId, permissionInvalid);
    for (SysPermission sysPermission : sysPermissions) {
      if (sysPermissionService
          .findByParentIdAndPermissionInvalidOrderBySortedAsc(
              sysPermission.getId(), permissionInvalid)
          .isEmpty()) {
        sysPermission.setState("");
      } else {
        sysPermission.setChildren(
            findByParentIdAndPermissionInvalidOrderBySortedAsc(
                sysPermission.getId(), permissionInvalid));
      }
    }
    return sysPermissions;
  }
}
