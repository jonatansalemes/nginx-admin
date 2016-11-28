package com.jslsolucoes.nginx.admin.nginx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import com.jslsolucoes.nginx.admin.os.OperationalSystemDistribution;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE, ElementType.METHOD })
public @interface RunnerType {

	OperationalSystemDistribution value();

}