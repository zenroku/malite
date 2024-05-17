package com.zahir.fathurrahman.malite.core.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class MALDataConverter {
    public static String str(Object str){
        return String.valueOf(str);
    }

    public static Double dbl(Object dbl){
            try {
                return Double.parseDouble(str(dbl));
            } catch (Exception e) {
                log.info("invalid value of {}",dbl);
                return null;
            }
    }

    public static LocalDate ld(Object ld){
        try {
            return LocalDate.parse(str(ld), DateTimeFormatter.ISO_DATE);
        } catch (Exception e) {
            log.info("invalid value of {}",ld);
            return null;
        }
    }

    public static LocalDateTime ldt(Object ldt) {
        try {
            return LocalDateTime.parse(str(ldt),DateTimeFormatter.ISO_DATE_TIME);
        } catch (Exception e) {
            log.info("invalid value of {}",ldt);
            return null;
        }
    }

    public static Long lg(Object lg) {
        try {
            return Long.parseLong(str(lg));
        } catch (Exception e) {
            log.info("invalid value of {}",lg);
            return null;
        }
    }

    public static Integer itg(Object itg) {
        try {
            return Integer.parseInt(str(itg));
        } catch (Exception e) {
            log.info("invalid value of {}",itg);
            return null;
        }
    }
}
