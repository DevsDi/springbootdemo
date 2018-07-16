package com.dev.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD , ElementType.TYPE})
public @interface NeedAuth {
}
