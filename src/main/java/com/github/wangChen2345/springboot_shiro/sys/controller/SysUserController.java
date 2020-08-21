package com.github.wangChen2345.springboot_shiro.sys.controller;

import com.github.wangChen2345.springboot_shiro.pojo.EzuiGridData;
import com.github.wangChen2345.springboot_shiro.sys.entiy.SysUser;
import com.github.wangChen2345.springboot_shiro.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Chen's
 * @date 2019/8/21 16:21
 */
@Controller
@RequestMapping("SysUserControl")
public class SysUserController {
  Sort.Direction DIRECTION = Sort.DEFAULT_DIRECTION;
  @Autowired
  SysUserService sysUserService;

  @RequestMapping(value = "/sysUser.htm")
  public String sysUser(Model model) {
    return "sys/sysUser";
  }

  @RequiresPermissions("sysUser:find")
  @RequestMapping(value = "/find", method = RequestMethod.POST)
  @ResponseBody
  public EzuiGridData find(
      Integer page, Integer rows, String sort, String order, boolean userInvalid) {
    EzuiGridData ezuiGridData = new EzuiGridData();
    if (("desc").equals(order)) {
      DIRECTION = Sort.Direction.DESC;
    } else {
      DIRECTION = Sort.Direction.ASC;
    }
    Specification specification =
        new Specification<SysUser>() {
          @Override
          public Predicate toPredicate(
              Root<SysUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<Predicate>();
            //            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (!StringUtils.isEmpty(userInvalid)) {
              predicates.add(criteriaBuilder.equal(root.get("userInvalid"), userInvalid));
            }
            //            if (!StringUtils.isEmpty(endDate)) {
            //              try {
            //                predicates.add(
            //                    criteriaBuilder.lessThanOrEqualTo(
            //                        root.get("tideDate").as(Date.class), sdf.parse(endDate)));
            //              } catch (ParseException e) {
            //                e.printStackTrace();
            //              }
            //            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
          }
        };
    if (StringUtils.isEmpty(page) && StringUtils.isEmpty(rows)) {
      List<SysUser> sysUsers =
          sysUserService.findAll(specification, new Sort(DIRECTION, "userCode"));
      ezuiGridData.setRows(sysUsers);
    } else {
      Pageable pageable = new PageRequest(page - 1, rows, DIRECTION, sort);
      Page<SysUser> sysUsers = sysUserService.findAll(specification, pageable);
      ezuiGridData.setRows(sysUsers.getContent());
      ezuiGridData.setTotal(sysUsers.getTotalElements());
    }
    return ezuiGridData;
  }

  @RequiresPermissions("sysUser:save")
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  @ResponseBody
  public SysUser save(SysUser sysUser) {
    SysUser sUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
    Date date = new Date();
    sysUser.setCreator(sUser.getUserCode());
    sysUser.setCreatedTim(new Timestamp(date.getTime()));
    sysUser.setChanger(sUser.getUserCode());
    sysUser.setChangedTim(new Timestamp(date.getTime()));
    return sysUserService.save(sysUser);
  }

  @RequiresPermissions("sysUser:update")
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  @ResponseBody
  public SysUser update(SysUser sysUser) {
    SysUser sUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
    Date date = new Date();
    sysUser.setChanger(sUser.getUserCode());
    sysUser.setChangedTim(new Timestamp(date.getTime()));
    return sysUserService.save(sysUser);
  }

  @RequiresPermissions("sysUser:delete")
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  @ResponseBody
  public void delete(String id) {
    sysUserService.deleteById(id);
  }
}
