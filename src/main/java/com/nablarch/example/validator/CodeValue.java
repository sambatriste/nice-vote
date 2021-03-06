package com.nablarch.example.validator;

import com.nablarch.example.code.CodeEnum;
import nablarch.core.util.StringUtil;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * {@link CodeEnum}のコード値のバリデーションを行うクラス。
 *
 * @author Nabu Rakutaro
 */
@Documented
@Constraint(validatedBy = { CodeValue.CodeValueValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface CodeValue {

    /**
     * バリデーションエラー発生時に設定するメッセージ。
     */
    String message() default "{com.nablarch.example.app.entity.core.validation.validator.CodeValue.message}";

    /**
     * コードenumを取得する。
     */
    Class<? extends Enum<?>> enumClass();

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
         * CodeValueの配列を取得する。
         */
        CodeValue[] value();
    }

    /**
     * グループを取得する。
     */
    Class<?>[] groups() default { };

    /**
     * Payloadを取得する。
     */
    Class<? extends Payload>[] payload() default { };

    /**
     * CodeValueの検証を行う実装クラス。
     *
     * @author Nabu Rakutaro
     */
    class CodeValueValidator implements ConstraintValidator<CodeValue, String> {

        /** コードを定義したEnumの配列 */
        private Object[] enumValues;

        /**
         * CodeValueValidator を初期化する。
         * @param constraintAnnotation 対象プロパティに付与されたアノテーション
         */
        @Override
        public void initialize(CodeValue constraintAnnotation) {
            enumValues = constraintAnnotation.enumClass().getEnumConstants();
        }

        /**
         * 検証対象の値が指定したenumクラスに含まれるかどうかを検証する。
         * @param value 検証対象の値
         * @param context バリデーションコンテキスト
         * @return 含まれる場合 {@code true}
         */
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {

            if (StringUtil.isNullOrEmpty(value)) {
                return true;
            }

            if (enumValues != null) {
                for (Object enumValue : enumValues) {
                    if (value.equals(((CodeEnum) enumValue).getCode())) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

}
