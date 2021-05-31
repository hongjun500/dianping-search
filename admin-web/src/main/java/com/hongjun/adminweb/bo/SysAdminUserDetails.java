package com.hongjun.adminweb.bo;

import com.hongjun.dataobject.SysAdminDO;
import com.hongjun.dataobject.SysResourceDO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

/**
 * SpringSecurity需要的用户详情
 * @author hongjun500
 */
public class SysAdminUserDetails implements UserDetails {
    private static final long serialVersionUID = 2570839732081794700L;
    private SysAdminDO sysAdminDO;
    private List<SysResourceDO> sysResourceDOList;
    public SysAdminUserDetails(SysAdminDO sysAdminDO, List<SysResourceDO> list){
        this.sysAdminDO = sysAdminDO;
        this.sysResourceDOList = list;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的角色
        return sysResourceDOList.stream().filter(Objects::nonNull)
                .map(role -> new SimpleGrantedAuthority(role.getId() + ":" + role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return sysAdminDO.getPassword();
    }

    @Override
    public String getUsername() {
        return sysAdminDO.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return sysAdminDO.getStatus().equals(1);
    }
}
