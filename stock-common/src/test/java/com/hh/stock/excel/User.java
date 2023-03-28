package com.hh.stock.excel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : hh
 * @date : 2023/3/21 14:21
 * @description : some description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private String userName;
    private Integer age;
    private String asddress;
    private Date birthDate;

}
