package com.github.wangChen2345.springboot_shiro.config.shiro;

import com.github.wangChen2345.springboot_shiro.sys.entiy.SysPermission;
import com.github.wangChen2345.springboot_shiro.sys.entiy.SysRole;
import com.github.wangChen2345.springboot_shiro.sys.entiy.SysUser;
import com.github.wangChen2345.springboot_shiro.sys.service.SysPermissionService;
import com.github.wangChen2345.springboot_shiro.sys.service.SysRoleService;
import com.github.wangChen2345.springboot_shiro.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShiroRealm extends AuthorizingRealm {
  @Autowired
  SysUserService sysUserService;
  @Autowired
  SysRoleService sysRoleService;
  @Autowired
  SysPermissionService sysPermissionService;
  /**
   * 验证用户身份
   *
   * @param authenticationToken 前台登录的信息
   * @return
   * @throws AuthenticationException
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {
    // 获取用户名密码 第一种方式
    // String username = (String) authenticationToken.getPrincipal();
    // String password = new String((char[]) authenticationToken.getCredentials());
    // 获取用户名 密码 第二种方式
    UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
    String username = usernamePasswordToken.getUsername();
    String password = new String(usernamePasswordToken.getPassword());
    // 从数据库查询用户信息
    SysUser sysUser = sysUserService.findByUserCode(username);
    // 可以在这里直接对用户名校验,或者调用 CredentialsMatcher 校验
    if (sysUser == null) {
      throw new UnknownAccountException("用户名错误！");
    }
    if (!password.equals(sysUser.getPassword())) {
      throw new IncorrectCredentialsException("密码错误！");
    }
    if (sysUser.isUserInvalid()) {
      throw new LockedAccountException("账号已被锁定,请联系管理员！");
    }
    // 调用 CredentialsMatcher 校验 还需要创建一个类 继承CredentialsMatcher  如果在上面校验了,这个就不需要了
    // 配置自定义权限登录器
    SimpleAuthenticationInfo info =
        new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(), null, getName());
    return info;
  }
  /**
   * 授权用户权限 授权的方法是在碰到<shiro:hasPermission name=''></shiro:hasPermission>标签的时候调用的
   * 它会去检测shiro框架中的权限(这里的permissions)是否包含有该标签的name值,如果有,里面的内容显示 如果没有,里面的内容不予显示(这就完成了对于权限的认证.)
   *
   * <p>shiro的权限授权是通过继承AuthorizingRealm抽象类，重载doGetAuthorizationInfo();
   * 当访问到页面的时候，链接配置了相应的权限或者shiro标签才会执行此方法否则不会执行 所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可。
   *
   * <p>在这个方法中主要是使用类：SimpleAuthorizationInfo 进行角色的添加和权限的添加。
   * authorizationInfo.addRole(role.getRole());
   * authorizationInfo.addStringPermission(p.getPermission());
   *
   * <p>当然也可以添加set集合：roles是从数据库查询的当前用户的角色，stringPermissions是从数据库查询的当前用户对应的权限
   * authorizationInfo.setRoles(roles); authorizationInfo.setStringPermissions(stringPermissions);
   *
   * <p>就是说如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "perms[权限添加]");
   * 就说明访问/add这个链接必须要有“权限添加”这个权限才可以访问
   *
   * <p>如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "roles[100002]，perms[权限添加]");
   * 就说明访问/add这个链接必须要有 "权限添加" 这个权限和具有 "100002" 这个角色才可以访问
   *
   * @param principalCollection
   * @return
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    // 获取用户
    SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
    if (("admin").equals(sysUser.getUserCode())) {
      List<SysRole> sysRoles = sysRoleService.findAll();
      for (SysRole sysRole : sysRoles) {
        authorizationInfo.addRole(sysRole.getRoleCode());
      }
      List<SysPermission> sysPermissions = sysPermissionService.findAll();
      for (SysPermission sysPermission : sysPermissions) {
        authorizationInfo.addStringPermission(sysPermission.getCode());
      }
    } else {
      // 获取用户角
      List<SysRole> sysRoles = sysUser.getSysRoles();
      // 添加角色
      for (SysRole sysRole : sysRoles) {
        authorizationInfo.addRole(sysRole.getRoleCode());
        // 获取用户权限
        List<SysPermission> sysPermissions = sysRole.getSysPermissions();
        // 添加权限
        for (SysPermission sysPermission : sysPermissions) {
          if (("button").equals(sysPermission.getPermissionType())
              && !sysPermission.isPermissionInvalid()) {
            authorizationInfo.addStringPermission(sysPermission.getCode());
          }
          // 如果是菜单的最后最后节点 添加按钮权限
          //          List<SysPermission> sysPermissions1 =
          //              sysPermissionService
          //                  .findByParentIdAndPermissionInvalidOrderBySortedAsc(
          //                      sysPermission.getId(), false);
          //          for (SysPermission sysPermission1 : sysPermissions1) {
          //            authorizationInfo.addStringPermission(sysPermission1.getCode());
          //          }
        }
      }
    }
    return authorizationInfo;
  }
}
