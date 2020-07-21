package com.github.wangChen2345.sys.service;

import com.ce.sys.entiy.SysPermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @author Chen's
 * @date 2019/8/2 17:06
 */
public interface SysPermissionService {
  SysPermission findById(String id);

  SysPermission save(SysPermission sysPermission);

  void deleteById(String id);

  Page<SysPermission> findAll(Pageable pageable);

  Page<SysPermission> findAll(Specification specification, Pageable pageable);

  List<SysPermission> findAll();

  List<SysPermission> findAll(Sort sort);

  List<SysPermission> findAll(Specification specification, Sort sort);

  List<SysPermission> findByParentIdOrderBySortedAsc(String parentId);

  List<SysPermission> findByParentIdAndPermissionTypeOrderBySortedAsc(
          String parentId, String permissionType);

  List<SysPermission> findByParentIdAndPermissionInvalidOrderBySortedAsc(
          String parentId, boolean permissionInvalid);
}
