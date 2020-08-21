package com.github.wangChen2345.springboot_shiro.sys.repository;

import com.github.wangChen2345.springboot_shiro.sys.entiy.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SysRoleRepostory
    extends JpaRepository<SysRole, String>, JpaSpecificationExecutor<SysRole> {
  SysRole findByRoleCode(String roleCode);
}
