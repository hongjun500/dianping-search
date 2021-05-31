package com.hongjun.app.vo;

import com.hongjun.dataobject.UmsUserInfoDO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @author hongjun500
 * @date 2021/4/14 17:50
 * @tool ThinkPadX1隐士
 * Created with 2019.3.2.IntelliJ IDEA
 * Description:
 */
public class UmsUserInfoDetails implements UserDetails {
    private static final long serialVersionUID = -7884323194744806324L;
    private UmsUserInfoDO UmsUserInfoDO;

    public UmsUserInfoDetails(UmsUserInfoDO umsMember) {
        this.UmsUserInfoDO = umsMember;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return Collections.singletonList(new SimpleGrantedAuthority("APP"));
    }

    @Override
    public String getPassword() {
        return UmsUserInfoDO.getPassword();
    }

    @Override
    public String getUsername() {
        return UmsUserInfoDO.getUsername();
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
        // 账号是否禁用
        return UmsUserInfoDO.getStatus()==1;
    }

    public UmsUserInfoDO getUmsMember() {
        return UmsUserInfoDO;
    }
}
