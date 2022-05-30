package com.markerhub.common.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class InLoginDto {
    @NotNull(message = "id不能为空")
    private Long id;

    @NotBlank(message = "昵称不能为空")
    private String username;

    private String avatar;

    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;

}
