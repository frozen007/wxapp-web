package com.myz.inf.batch;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JobEntry
 * Created by myz
 * Date 2020/12/3 20:09
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface JobEntry {

    /**
     * JobEntry name, must be unique in a scheduler
     * @return name
     */
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String cron() default "";
}
