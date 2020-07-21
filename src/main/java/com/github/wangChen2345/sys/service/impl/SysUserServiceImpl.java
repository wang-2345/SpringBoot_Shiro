package com.github.wangChen2345.sys.service.impl;

import com.ce.sys.entiy.SysUser;
import com.ce.sys.repository.SysUserRepostory;
import com.ce.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {
  @Autowired SysUserRepostory sysUserRepostory;

  @Override
  public SysUser save(SysUser sysUser) {
    return sysUserRepostory.save(sysUser);
  }

  @Override
  public void deleteById(String userId) {
    sysUserRepostory.deleteById(userId);
  }

  @Override
  public Page<SysUser> findAll(Pageable pageable) {
    return sysUserRepostory.findAll(pageable);
  }

  @Override
  public Page<SysUser> findAll(Specification specification, Pageable pageable) {
    return sysUserRepostory.findAll(specification, pageable);
  }

  @Override
  public List<SysUser> findAll(Sort sort) {
    return sysUserRepostory.findAll(sort);
  }

  @Override
  public List<SysUser> findAll(Specification specification, Sort sort) {
    return sysUserRepostory.findAll(specification, sort);
  }

  @Override
  public SysUser findByUserCode(String userCode) {
    return sysUserRepostory.findByUserCode(userCode);
  }
}
