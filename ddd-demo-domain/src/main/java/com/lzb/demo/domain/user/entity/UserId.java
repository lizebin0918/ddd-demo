package com.lzb.demo.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * <br/>
 * Created on : 2022-02-14 15:50
 *
 * @author lizebin
 */
@Getter
@RequiredArgsConstructor
public class UserId implements Serializable {

    private final Long value;

}
