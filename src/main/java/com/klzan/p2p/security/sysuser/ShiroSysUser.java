package com.klzan.p2p.security.sysuser;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Set;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
 */
public class ShiroSysUser implements Serializable {
    private static final long serialVersionUID = -1373760761780840081L;
    public Integer id;
    public String loginName;
    public String name;
    public String mobile;
    private Set<String> permissions;
    private Set<String> roles;

    public ShiroSysUser(Integer id, String loginName, String name, String mobile, Set<String> permissions, Set<String> roles) {
        this.id = id;
        this.loginName = loginName;
        this.name = name;
        this.mobile = mobile;
        this.permissions = permissions;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public Set<String> getRoles() {
        return roles;
    }

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return loginName;
    }

    /**
     * 重载hashCode,只计算loginName;
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(loginName);
    }

    /**
     * 重载equals,只计算loginName;
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ShiroSysUser other = (ShiroSysUser) obj;
        if (loginName == null) {
            if (other.loginName != null) {
                return false;
            }
        } else if (!loginName.equals(other.loginName)) {
            return false;
        }
        return true;
    }
}
