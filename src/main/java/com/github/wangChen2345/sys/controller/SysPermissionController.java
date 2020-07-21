package com.github.wangChen2345.sys.controller;

import com.ce.pojo.EzuiGridData;
import com.ce.sys.entiy.SysPermission;
import com.ce.sys.entiy.SysUser;
import com.ce.sys.service.SysPermissionService;
import org.apache.shiro.SecurityUtils;
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
@RequestMapping("SysPermissionControl")
public class SysPermissionController {

  Sort.Direction DIRECTION = Sort.DEFAULT_DIRECTION;
  @Autowired SysPermissionService sysPermissionService;

  @RequestMapping(value = "/sysPermission.htm")
  public String page(Model model) {
    return "sys/sysPermission";
  }

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
        new Specification<SysPermission>() {
          @Override
          public Predicate toPredicate(
              Root<SysPermission> root,
              CriteriaQuery<?> criteriaQuery,
              CriteriaBuilder criteriaBuilder) {
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
      List<SysPermission> sysPermissions =
          sysPermissionService.findAll(specification, new Sort(DIRECTION, "permissionCode"));
      ezuiGridData.setRows(sysPermissions);
    } else {
      Pageable pageable = new PageRequest(page - 1, rows, DIRECTION, sort);
      Page<SysPermission> sysPermissions = sysPermissionService.findAll(specification, pageable);
      ezuiGridData.setRows(sysPermissions.getContent());
      ezuiGridData.setTotal(sysPermissions.getTotalElements());
    }
    return ezuiGridData;
  }

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  @ResponseBody
  public SysPermission save(SysPermission sysPermission) {
    SysUser sUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
    Date date = new Date();
    sysPermission.setCreator(sUser.getUserCode());
    sysPermission.setCreatedTim(new Timestamp(date.getTime()));
    sysPermission.setChanger(sUser.getUserCode());
    sysPermission.setChangedTim(new Timestamp(date.getTime()));
    return sysPermissionService.save(sysPermission);
  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  @ResponseBody
  public SysPermission update(SysPermission sysPermission) {
    SysUser sUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
    Date date = new Date();
    sysPermission.setChanger(sUser.getUserCode());
    sysPermission.setChangedTim(new Timestamp(date.getTime()));
    return sysPermissionService.save(sysPermission);
  }

  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  @ResponseBody
  public void delete(String id) {
    sysPermissionService.deleteById(id);
  }

  @RequestMapping(value = "/findMenu", method = RequestMethod.POST)
  @ResponseBody
  public List<SysPermission> findMenu() {
    return findByParentIdOrderBySortedAsc("0");
  }

  // 树
  public List<SysPermission> findByParentIdOrderBySortedAsc(String parentId) {
    List<SysPermission> sysPermissions =
        sysPermissionService.findByParentIdOrderBySortedAsc(parentId);
    for (SysPermission sysPermission : sysPermissions) {
      if (sysPermissionService.findByParentIdOrderBySortedAsc(sysPermission.getId()).isEmpty()) {
        continue;
      } else {
        sysPermission.setChildren(findByParentIdOrderBySortedAsc(sysPermission.getId()));
      }
    }
    return sysPermissions;
  }

  @RequestMapping(value = "/findByParentIdOrderBySortedAsc", method = RequestMethod.POST)
  @ResponseBody
  public EzuiGridData findByParentIdOrderBySortedAsc(Model model, String parentId) {
    EzuiGridData ezuiGridData = new EzuiGridData();
    List<SysPermission> sysPermissions =
        sysPermissionService.findByParentIdOrderBySortedAsc(parentId);
    ezuiGridData.setRows(sysPermissions);
    return ezuiGridData;
  }
}
