package com.nablarch.example.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import nablarch.core.util.DateUtil;
import nablarch.core.util.StringUtil;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.nablarch.example.validator.YYYYMMDD.YYYYMMDDValidator;

/**
 * 許容する年月日フォーマットを指定するアノテーション。
 *
 * @author Nabu Rakutaro
 */
@Documented
@Constraint(validatedBy = { YYYYMMDDValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface YYYYMMDD {

    /** グループ */
    Class<?>[] groups() default { };

    /** バリデーションエラー時のメッセージ */
    String message() default "{com.nablarch.example.app.entity.core.validation.validator.YYYYMMDD.message}";

     /** ペイロード **/
    Class<? extends Payload>[] payload() default { };

    /** 許容するフォーマット */
    String allowFormat() default "yyyyMMdd";

    /**
     * 複数指定用のアノテーション
     *
     * @author Nabu Rakutaro
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        /**
         * YYYYMMDDの配列を取得する。
         */
        YYYYMMDD[] value();
    }

    /**
     * YYYYMMDDの検証を行う実装クラス。
     *
     * @author Nabu Rakutaro
     */
    class YYYYMMDDValidator implements ConstraintValidator<YYYYMMDD, String> {

        /**
         * 許容する年月日のフォーマット
         */
        private String allowFormat;

        @Override
        public void initialize(YYYYMMDD constraintAnnotation) {
            this.allowFormat = constraintAnnotation.allowFormat();
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (StringUtil.isNullOrEmpty(value)) {
                return true;
            }
            try {
                return DateUtil.getParsedDate(value, allowFormat) != null;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
    }

}
