package com.study.springboot.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class ApiErrorInfo implements Serializable {

    private static final long serialVersionUID = -4521676718585992138L;

    private String message;

    private final List<ApiErrorDetailInfo> details = new ArrayList<>();

    public void addDetailInfo(String target, String message) {
        details.add(new ApiErrorDetailInfo(target, message));
    }
}
