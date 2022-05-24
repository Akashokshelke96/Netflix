package com.prepfortech.netflixclone.controller.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerifyEmailInput {
    private  String otp;
}
