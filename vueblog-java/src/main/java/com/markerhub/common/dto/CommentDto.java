package com.markerhub.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    @NotNull(message = "id不能为空")
    private Long id;

    @NotBlank(message = "昵称不能为空")
    private String username;

    private String content;

    @NotBlank(message = "时间不能为空")
    private LocalDateTime created;

    private String avatar;
}
