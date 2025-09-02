package com.study.springboot.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiErrorDetailInfo {

    private String target;

    private String message;
}
