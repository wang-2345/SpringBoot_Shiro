package com.github.wangChen2345.springboot_shiro.sys.service;

import com.github.wangChen2345.springboot_shiro.sys.entiy.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * @author Chen's
 * @date 2019/7/25 16:18
 */
public interface SysUserService {

  SysUser save(SysUser sysUser);

  void deleteById(String userId);

  Page<SysUser> findAll(Pageable pageable);

  Page<SysUser> findAll(Specification specification, Pageable pageable);

  List<SysUser> findAll(Sort sort);

  List<SysUser> findAll(Specification specification, Sort sort);

  SysUser findByUserCode(String userCode);
}
