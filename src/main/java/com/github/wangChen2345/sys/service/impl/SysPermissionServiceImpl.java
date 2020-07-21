package com.github.wangChen2345.sys.service.impl;

import com.ce.sys.entiy.SysPermission;
import com.ce.sys.repository.SysPermissionRepostory;
import com.ce.sys.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Chen's
 * @date 2019/8/2 17:07
 */
@Service
@Transactional
public class SysPermissionServiceImpl implements SysPermissionService {
  @Autowired SysPermissionRepostory sysPermissionRepostory;

  @Override
  public SysPermission findById(String id) {
    return sysPermissionRepostory.findById(id).get();
  }

  @Override
  public SysPermission save(SysPermission sysPermission) {
    return sysPermissionRepostory.save(sysPermission);
  }

  @Override
  public void deleteById(String id) {
    sysPermissionRepostory.deleteById(id);
  }

  @Override
  public Page<SysPermission> findAll(Pageable pageable) {
    return sysPermissionRepostory.findAll(pageable);
  }

  @Override
  public Page<SysPermission> findAll(Specification specification, Pageable pageable) {
    return sysPermissionRepostory.findAll(specification, pageable);
  }

  @Override
  public List<SysPermission> findAll() {
    return sysPermissionRepostory.findAll();
  }

  @Override
  public List<SysPermission> findAll(Sort sort) {
    return sysPermissionRepostory.findAll(sort);
  }

  @Override
  public List<SysPermission> findAll(Specification specification, Sort sort) {
    return sysPermissionRepostory.findAll(specification, sort);
  }

  @Override
  public List<SysPermission> findByParentIdOrderBySortedAsc(String parentId) {
    return sysPermissionRepostory.findByParentIdOrderBySortedAsc(parentId);
  }

  @Override
  public List<SysPermission> findByParentIdAndPermissionTypeOrderBySortedAsc(
      String parentId, String permissionType) {
    return sysPermissionRepostory.findByParentIdAndPermissionTypeOrderBySortedAsc(
        parentId, permissionType);
  }

  @Override
  public List<SysPermission> findByParentIdAndPermissionInvalidOrderBySortedAsc(
      String parentId, boolean permissionInvalid) {
    return sysPermissionRepostory.findByParentIdAndPermissionInvalidOrderBySortedAsc(
        parentId, permissionInvalid);
  }
}
