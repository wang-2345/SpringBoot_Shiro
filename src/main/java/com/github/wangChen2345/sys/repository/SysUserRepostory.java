package com.github.wangChen2345.sys.repository;

import com.ce.sys.entiy.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SysUserRepostory
    extends JpaRepository<SysUser, String>, JpaSpecificationExecutor<SysUser> {
  SysUser findByUserCode(String userCode);
}
