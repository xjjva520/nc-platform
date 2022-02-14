package com.nc.xhq.auth.entity.info;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/18
 * @package: com.nc.xhq.auth.entity
 */
@Data
public class WxUserInfo implements Serializable {


    private static final long serialVersionUID = -219177328179009765L;

    private String openId;

    private String unionId;

    private String phoneNumber;
}
