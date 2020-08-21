package com.github.wangChen2345.springboot_shiro.sys.service;

import com.github.wangChen2345.springboot_shiro.sys.entiy.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @author Chen's
 * @date 2019/8/2 15:56
 */
public interface SysRoleService {
  SysRole save(SysRole sysRole);

  void deleteById(String roleId);

  Page<SysRole> findAll(Pageable pageable);

  Page<SysRole> findAll(Specification specification, Pageable pageable);

  List<SysRole> findAll();

  List<SysRole> findAll(Sort sort);

  List<SysRole> findAll(Specification specification, Sort sort);

  SysRole findByRoleCode(String roleCode);
}
