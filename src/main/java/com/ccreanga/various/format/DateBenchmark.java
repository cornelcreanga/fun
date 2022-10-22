package com.ccreanga.various.format;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.codehaus.jackson.map.util.ISO8601Utils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.VerboseMode;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 Benchmark                      Mode  Cnt    Score    Error  Units
 DateBenchmark.parseDateUtils1  avgt   15  782.449 ±  8.958  ns/op
 DateBenchmark.parseDateUtils2  avgt   15  857.137 ±  8.886  ns/op
 DateBenchmark.parseStd1        avgt   15  269.385 ±  1.363  ns/op
 DateBenchmark.parseStd2        avgt   15  360.210 ± 14.789  ns/op

 https://groups.google.com/forum/#!search/StdDateFormat%7Csort:date/jackson-dev/odeircl1iPM/K2ZHxxnDAQAJ
 */


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2)
@Measurement(iterations = 2)
public class DateBenchmark {

  public static final String DATE1 = "2020-01-09T14:33:56Z";
  public static final String DATE2 = "2020-01-09T08:38:20.046-05:00";

  @State(Scope.Benchmark)
  public static class BenchmarkState {

    StdDateFormat stdDateFormat;
    Iso8601TimeStampFormat iso8601TimeStampFormat;
    DateTimeFormatter fmt;
    HashMap<String, Date> timestampCache;

    @Setup
    public void setUp(){
      stdDateFormat = new StdDateFormat();
      iso8601TimeStampFormat = new Iso8601TimeStampFormat();
      fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ").withZoneUTC();      timestampCache = new HashMap<>();
      try {
        timestampCache.put(DATE1, stdDateFormat.parse(DATE1));
        timestampCache.put(DATE2, stdDateFormat.parse(DATE2));
      }catch (Exception e){}
    }

  }

//  @Benchmark
//  public Date cacheDate(BenchmarkState s) throws ParseException {
//    return s.timestampCache.get(DATE2);
//  }

//  @Benchmark
//  public Instant parseDtf1(BenchmarkState s) throws ParseException {
//    return DateTimeFormatValidator.FORMATTER.parse(DATE1, Instant::from);
//  }
//
//  @Benchmark
//  public Instant parseDtf2(BenchmarkState s) throws ParseException {
//    return DateTimeFormatValidator.FORMATTER.parse(DATE2, Instant::from);
//  }
//
//  @Benchmark
//  public Date parsefdF1(BenchmarkState s) throws ParseException {
//     return s.fastDateFormat1.parse(DATE1);
//  }
//
  @Benchmark
  public DateTime parseJoda(BenchmarkState s) throws ParseException {
    return s.fmt.parseDateTime(DATE2);
  }

  @Benchmark
  public Date parseStd(BenchmarkState s) throws ParseException {
    return s.stdDateFormat.parse(DATE2);
  }

  @Benchmark
  public Date parseIso8601(BenchmarkState s) throws ParseException {
    return s.iso8601TimeStampFormat.parse(DATE2);
  }

  @Benchmark
  public Date parseIso8601_util(BenchmarkState s) throws ParseException {
    return ISO8601Utils.parse(DATE2);
  }


//  @Benchmark
//  public void createCalendar(BenchmarkState s) throws ParseException {
//    Calendar.getInstance();
//  }


//  @Benchmark
//  public Date parseStd2(BenchmarkState s) throws ParseException {
//    return s.stdDateFormat.parse(DATE2);
//  }





//  @Benchmark
//  public Date parseDateUtils2(BenchmarkState s)  {
//    return DateTimeUtils.stringToTime(DATE2);
//  }

//  public static Date stringToTime(String s) {
//    int indexOfGMT = s.indexOf("GMT");
//    if (indexOfGMT != -1) {
//      // ISO8601 with a weird time zone specifier (2000-01-01T00:00GMT+01:00)
//      String s0 = s.substring(0, indexOfGMT);
//      String s1 = s.substring(indexOfGMT + 3);
//      // Mapped to 2000-01-01T00:00+01:00
//      stringToTime(s0 + s1);
//    } else if (!s.contains("T")) {
//      // JDBC escape string
//      if (s.contains(" ")) {
//        return Timestamp.valueOf(s);
//      } else {
//        return java.sql.Date.valueOf(s);
//      }
//    } else {
//      return DatatypeConverter.parseDateTime(s).getTime();
//    }
//  }

  public static void main(String[] args) throws RunnerException, ParseException {

    Options opt = new OptionsBuilder()
        .include(DateBenchmark.class.getSimpleName())
        .verbosity(VerboseMode.NORMAL)
        .build();
    new Runner(opt).run();
  }

}
