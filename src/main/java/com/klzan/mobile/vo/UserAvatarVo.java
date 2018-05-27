package com.klzan.mobile.vo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/26 0026.
 */
public class UserAvatarVo implements Serializable {


  private String  extension;
  private String  avatarData;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getAvatarData() {
        return avatarData;
    }

    public void setAvatarData(String avatarData) {
        this.avatarData = avatarData;
    }
}
