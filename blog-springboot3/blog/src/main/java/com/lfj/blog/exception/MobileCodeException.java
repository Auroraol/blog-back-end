package com.lfj.blog.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 手机号验证码认证 验证码不正确异常
 **/
public class MobileCodeException extends AuthenticationException {

	public MobileCodeException(String msg, Throwable t) {
		super(msg, t);
	}

	public MobileCodeException(String msg) {
		super(msg);
	}
}
