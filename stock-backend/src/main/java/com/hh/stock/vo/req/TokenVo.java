package com.hh.stock.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : hh
 * @date : 2023/1/26 13:07
 * @description : some description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenVo {
    //过期时间
    private Long expireTime;
    //token
    private String token;

}
