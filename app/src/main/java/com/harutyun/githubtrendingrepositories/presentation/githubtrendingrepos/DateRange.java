package com.harutyun.githubtrendingrepositories.presentation.githubtrendingrepos;

import java.util.Calendar;
import java.util.Date;

public enum DateRange {

    LAST_DAY(getDateXDaysAgo(1)), LAST_WEEK(getDateXDaysAgo(7)), LAST_MONTH(getDateXMonthsAgo(1));

    private final Date date;

    DateRange(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    private static Date getDateXDaysAgo(int x) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -x);
        return cal.getTime();
    }

    private static Date getDateXMonthsAgo(int x) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -x);
        return cal.getTime();
    }
}
