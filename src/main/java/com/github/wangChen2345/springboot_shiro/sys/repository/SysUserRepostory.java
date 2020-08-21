package com.github.wangChen2345.springboot_shiro.sys.repository;

import com.github.wangChen2345.springboot_shiro.sys.entiy.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SysUserRepostory
    extends JpaRepository<SysUser, String>, JpaSpecificationExecutor<SysUser> {
  SysUser findByUserCode(String userCode);
}
