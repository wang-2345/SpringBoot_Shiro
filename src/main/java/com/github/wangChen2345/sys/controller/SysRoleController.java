package com.github.wangChen2345.sys.controller;

import com.ce.pojo.EzuiGridData;
import com.ce.sys.entiy.SysRole;
import com.ce.sys.entiy.SysUser;
import com.ce.sys.service.SysRoleService;
import com.ce.sys.service.SysUserService;
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
 * @date 2019/8/21 16:22
 */
@Controller
@RequestMapping("SysRoleControl")
public class SysRoleController {
  Sort.Direction DIRECTION = Sort.DEFAULT_DIRECTION;
  @Autowired SysRoleService sysRoleService;
  @Autowired SysUserService sysUserService;

  @RequestMapping(value = "/sysRole.htm")
  public String page(Model model) {
    return "sys/sysRole";
  }

  @RequiresPermissions("sysRole:find")
  @RequestMapping(value = "/find", method = RequestMethod.POST)
  @ResponseBody
  public EzuiGridData find(Integer page, Integer rows, String sort, String order) {
    EzuiGridData ezuiGridData = new EzuiGridData();
    if (("desc").equals(order)) {
      DIRECTION = Sort.Direction.DESC;
    } else {
      DIRECTION = Sort.Direction.ASC;
    }
    Specification specification =
        new Specification<SysRole>() {
          @Override
          public Predicate toPredicate(
              Root<SysRole> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<Predicate>();
            //            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //            if (!StringUtils.isEmpty(startDate)) {
            //              try {
            //                predicates.add(
            //                    criteriaBuilder.greaterThanOrEqualTo(
            //                        root.get("tideDate").as(Date.class), sdf.parse(startDate)));
            //              } catch (ParseException e) {
            //                e.printStackTrace();
            //              }
            //            }
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
      List<SysRole> sysRoles =
          sysRoleService.findAll(specification, new Sort(DIRECTION, "roleCode"));
      ezuiGridData.setRows(sysRoles);
    } else {
      Pageable pageable = new PageRequest(page - 1, rows, DIRECTION, sort);
      Page<SysRole> sysRoles = sysRoleService.findAll(specification, pageable);
      ezuiGridData.setRows(sysRoles.getContent());
      ezuiGridData.setTotal(sysRoles.getTotalElements());
    }
    return ezuiGridData;
  }

  @RequiresPermissions("sysRole:save")
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  @ResponseBody
  public SysRole save(SysRole sysRole) {
    SysUser sUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
    Date date = new Date();
    sysRole.setCreator(sUser.getUserCode());
    sysRole.setCreatedTim(new Timestamp(date.getTime()));
    sysRole.setChanger(sUser.getUserCode());
    sysRole.setChangedTim(new Timestamp(date.getTime()));
    return sysRoleService.save(sysRole);
  }

  @RequiresPermissions("sysRole:update")
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  @ResponseBody
  public SysRole update(SysRole sysRole) {
    SysUser sUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
    Date date = new Date();
    sysRole.setChanger(sUser.getUserCode());
    sysRole.setChangedTim(new Timestamp(date.getTime()));
    return sysRoleService.save(sysRole);
  }

  @RequiresPermissions("sysRole:delete")
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  @ResponseBody
  public void delete(String id) {
    sysRoleService.deleteById(id);
  }
}
