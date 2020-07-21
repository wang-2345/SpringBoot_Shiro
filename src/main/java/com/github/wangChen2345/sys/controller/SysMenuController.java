package com.github.wangChen2345.sys.controller;

import com.ce.sys.entiy.SysPermission;
import com.ce.sys.entiy.SysRole;
import com.ce.sys.entiy.SysUser;
import com.ce.sys.service.SysPermissionService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("SysMenuControl")
public class SysMenuController {
  @Autowired SysPermissionService sysPermissionService;

  @RequestMapping(value = "/findMenu", method = RequestMethod.POST)
  @ResponseBody
  public List<SysPermission> findMenu(Model model) {
    SysUser sUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
    // 管理员展示所有(包括无效的)
    if (("admin").equals(sUser.getUserCode())) {
      return findByParentIdOrderBySortedAsc("0");
    } else {
      Map<String, SysPermission> map = new HashMap<>();
      for (SysRole sysRole : sUser.getSysRoles()) {
        for (SysPermission sysPermission : sysRole.getSysPermissions()) {
          map.put(sysPermission.getParentId() + "_" + sysPermission.getId(), sysPermission);
          // 根据当前节点查找父节点
          findParent(map, sysPermission.getParentId());
        }
      }
      // 将map换成List菜单
      return mapByParentIdOrderBySortedAsc("0", map);
    }
  }

  // 菜单树
  private List<SysPermission> findByParentIdOrderBySortedAsc(String perantid) {
    List<SysPermission> sysPermissions =
        sysPermissionService.findByParentIdAndPermissionTypeOrderBySortedAsc(perantid, "menu");
    for (SysPermission sysPermission : sysPermissions) {
      if (sysPermissionService
          .findByParentIdAndPermissionTypeOrderBySortedAsc(sysPermission.getId(), "menu")
          .isEmpty()) {
        continue;
      } else {
        sysPermission.setChildren(findByParentIdOrderBySortedAsc(sysPermission.getId()));
      }
    }
    return sysPermissions;
  }

  private void findParent(Map<String, SysPermission> map, String parentId) {
    if (!("0").equals(parentId)) {
      SysPermission sysPermission = sysPermissionService.findById(parentId);
      map.put(sysPermission.getParentId() + "_" + sysPermission.getId(), sysPermission);
      if (!("0").equals(sysPermission.getParentId())) {
        findParent(map, sysPermission.getParentId());
      }
    }
  }

  private List<SysPermission> mapByParentIdOrderBySortedAsc(String parentId, Map map) {
    List<SysPermission> sysPermissions = mapByParentIdOrderBySortedAsc1(parentId, map, true);
    for (SysPermission sysPermission : sysPermissions) {
      // 判断一下是否还有子节点
      if (mapByParentIdOrderBySortedAsc1(sysPermission.getId(), map, false).isEmpty()) {
        continue;
      } else {
        sysPermission.setChildren(mapByParentIdOrderBySortedAsc(sysPermission.getId(), map));
      }
    }
    return sysPermissions;
  }

  private List<SysPermission> mapByParentIdOrderBySortedAsc1(
      String parentId, Map map, boolean del) {
    List<SysPermission> sysPermissions = new ArrayList<>();
    Iterator<Map.Entry<String, SysPermission>> it = map.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, SysPermission> entry = it.next();
      if (parentId.equals(entry.getKey().substring(0, entry.getKey().indexOf("_")))
          && ("menu").equals(entry.getValue().getPermissionType())) {
        sysPermissions.add(entry.getValue());
        if (del) {
          it.remove();
        }
      }
    }
    Collections.sort(
        sysPermissions,
        new Comparator<SysPermission>() {
          @Override
          public int compare(SysPermission sysPermission1, SysPermission sysPermission2) {
            int i = sysPermission1.getSorted() - sysPermission2.getSorted();
            return i;
          }
        });
    return sysPermissions;
  }
}
