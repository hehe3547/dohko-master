package com.dohko.core.services.support;

import com.dohko.core.base.Holder;

public class ContextHolder {
	// 存储请求的userHolder
	private static final ThreadLocal<Holder> threadLocal = new ThreadLocal<Holder>();

	public static Holder getHolder() {
		return threadLocal.get();
	}

	public static void setHolder(Holder userHolder) {
		threadLocal.set(userHolder);
	}
}
