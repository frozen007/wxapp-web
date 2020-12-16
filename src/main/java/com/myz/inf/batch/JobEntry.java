package com.myz.inf.batch;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

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
     * @return
     */
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String cron() default "";
}
