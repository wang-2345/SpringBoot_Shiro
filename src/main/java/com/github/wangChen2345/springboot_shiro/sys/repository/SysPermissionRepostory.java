package com.github.wangChen2345.springboot_shiro.sys.repository;

import com.github.wangChen2345.springboot_shiro.sys.entiy.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author Chen's
 * @date 2019/8/2 16:28
 */
public interface SysPermissionRepostory
    extends JpaRepository<SysPermission, String>, JpaSpecificationExecutor<SysPermission> {
  List<SysPermission> findByParentIdOrderBySortedAsc(String parentId);

  List<SysPermission> findByParentIdAndPermissionTypeOrderBySortedAsc(
          String parentId, String permissionType);

  List<SysPermission> findByParentIdAndPermissionInvalidOrderBySortedAsc(
          String parentId, boolean permissionInvalid);
}
