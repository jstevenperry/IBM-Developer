/*
 * Copyright 2017 Makoto Consulting Group, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.makotojava.ncaabb.dao;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.makotojava.ncaabb.model.SeasonAnalytics;
import com.makotojava.ncaabb.springconfig.ApplicationConfig;

public class SeasonAnalyticsJdbcDaoTest {

  private static final Logger log = Logger.getLogger(SeasonAnalyticsJdbcDaoTest.class);

  private ApplicationContext applicationContext;
  private SeasonAnalyticsDao classUnderTest;

  @Before
  public void setUp() throws Exception {
    applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
    classUnderTest = applicationContext.getBean(SeasonAnalyticsDao.class);
  }

  @Test
  public void testFetchByYear_2010() {
    log.info("*** BEGIN Test ***");
    Integer year = 2010;
    SeasonAnalytics seasonAnalytics = classUnderTest.fetchByYear(year);
    assertNotNull(seasonAnalytics);
    log.info("*** END Test ***");
  }

  @Test
  public void testFetchByYear_2011() {
    log.info("*** BEGIN Test ***");
    Integer year = 2011;
    SeasonAnalytics seasonAnalytics = classUnderTest.fetchByYear(year);
    assertNotNull(seasonAnalytics);
    log.info("*** END Test ***");
  }

  @Test
  public void testFetchByYear_2012() {
    log.info("*** BEGIN Test ***");
    Integer year = 2012;
    SeasonAnalytics seasonAnalytics = classUnderTest.fetchByYear(year);
    assertNotNull(seasonAnalytics);
    log.info("*** END Test ***");
  }

  @Test
  public void testFetchByYear_2013() {
    log.info("*** BEGIN Test ***");
    Integer year = 2013;
    SeasonAnalytics seasonAnalytics = classUnderTest.fetchByYear(year);
    assertNotNull(seasonAnalytics);
    log.info("*** END Test ***");
  }

  @Test
  public void testFetchByYear_2014() {
    log.info("*** BEGIN Test ***");
    Integer year = 2014;
    SeasonAnalytics seasonAnalytics = classUnderTest.fetchByYear(year);
    assertNotNull(seasonAnalytics);
    log.info("*** END Test ***");
  }

  @Test
  public void testFetchByYear_2015() {
    log.info("*** BEGIN Test ***");
    Integer year = 2015;
    SeasonAnalytics seasonAnalytics = classUnderTest.fetchByYear(year);
    assertNotNull(seasonAnalytics);
    log.info("*** END Test ***");
  }

  @Test
  public void testFetchByYear_2016() {
    log.info("*** BEGIN Test ***");
    Integer year = 2016;
    SeasonAnalytics seasonAnalytics = classUnderTest.fetchByYear(year);
    assertNotNull(seasonAnalytics);
    log.info("*** END Test ***");
  }

  @Test
  public void testFetchByYear_2017() {
    log.info("*** BEGIN Test ***");
    Integer year = 2017;
    SeasonAnalytics seasonAnalytics = classUnderTest.fetchByYear(year);
    assertNotNull(seasonAnalytics);
    log.info("*** END Test ***");
  }

}
