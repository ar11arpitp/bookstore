package com.online.bookstore.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class ViolationErrorDto {
    List<ViolationDto> violations = new ArrayList<>();
}
