package com.nablarch.example.form;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import nablarch.core.util.StringUtil;

/**
 * 日付の期間を表すクラス。
 *
 * @author Nabu Rakutaro
 */
public class DateRange {

    /** 開始 */
    private final Optional<LocalDate> start;

    /** 終了 */
    private final Optional<LocalDate> end;

    /**
     * 開始と終了を元に日付の期間を生成する。
     *
     * @param start 開始日付
     * @param end 終了日付
     */
    public DateRange(final String start, final String end) {
        this.start = toDate(start);
        this.end = toDate(end);
    }

    /**
     * 文字列で表現された日付を{@link LocalDate}に変換する。
     *
     * @param date 日付文字列
     * @return 変換した値
     */
    private static Optional<LocalDate> toDate(final String date) {
        if (StringUtil.isNullOrEmpty(date)) {
            return Optional.empty();
        } else {
            return Optional.of(LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE));
        }
    }

    /**
     * 期間が有効かどうか判定する。
     * <p>
     * 開始、終了ともに値が設定されていて、
     * 開始 < 終了の関係であれば有効な期間と判断する。
     *
     * @return 有効な期間の場合は{@code true}
     */
    public boolean isValid() {
        return start.flatMap(s -> end.flatMap(e -> Optional.of(s.isBefore(e))))
                    .orElse(true);
    }
}
