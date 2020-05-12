package com.jessica.social.network.serverless.vo;

import com.jessica.social.network.serverless.bo.UserBo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private String id;
    private String userName;
    private String password;
    private String email;
    private String imageName;
    
    public boolean isValid() {
        return StringUtils.isNotBlank(this.userName)
                && StringUtils.isNotBlank(this.password);
    }

    public UserBo toBo() {
        return  UserBo.builder()
                .id(UUID.randomUUID().toString())
                .userName(this.userName)
                .password(this.password)
                .email(this.email)
                .imageName(this.imageName)
                .build();
    }
    
    public static UserVo fromBo(UserBo userBo) {
        if(userBo == null) {
            return null;
        }
        return UserVo.builder()
                .id(userBo.getId())
                .userName(userBo.getUserName())
                .password(userBo.getPassword())
                .email(userBo.getEmail())
                .imageName(userBo.getImageName())
                .build();
    }


}
