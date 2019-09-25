package kr.or.ddit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) //클래스 선언부에만 사용 가능
@Retention(RetentionPolicy.RUNTIME) //실행시점까지 어노테이션이 생존
public @interface Preparer {

}
