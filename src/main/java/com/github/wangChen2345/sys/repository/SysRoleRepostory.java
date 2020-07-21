package com.github.wangChen2345.sys.repository;

import com.ce.sys.entiy.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SysRoleRepostory
    extends JpaRepository<SysRole, String>, JpaSpecificationExecutor<SysRole> {
  SysRole findByRoleCode(String roleCode);
}
