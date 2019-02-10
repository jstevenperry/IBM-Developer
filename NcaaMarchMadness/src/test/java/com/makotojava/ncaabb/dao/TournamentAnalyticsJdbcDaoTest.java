package com.makotojava.ncaabb.dao;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.makotojava.ncaabb.model.TournamentAnalytics;
import com.makotojava.ncaabb.springconfig.ApplicationConfig;

@RunWith(JUnitPlatform.class)
public class TournamentAnalyticsJdbcDaoTest {

  private ApplicationContext applicationContext;
  private TournamentAnalyticsDao classUnderTest;

  @BeforeEach
  public void setUp() throws Exception {
    applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
    classUnderTest = applicationContext.getBean(TournamentAnalyticsDao.class);
  }

  @Test
  @DisplayName("Testing fetchByYear()")
  public void fetchByYear() {

    assertAll(
        () -> {
          Integer year = 2010;

          TournamentAnalytics tournamentAnalytics = classUnderTest.fetchByYear(year);
          assertNotNull(tournamentAnalytics);
          assertEquals(Integer.valueOf(44), tournamentAnalytics.getMinScore());
          assertEquals(Integer.valueOf(101), tournamentAnalytics.getMaxScore());
        },
        () -> {
          Integer year = 2011;
          TournamentAnalytics tournamentAnalytics = classUnderTest.fetchByYear(year);
          assertNotNull(tournamentAnalytics);
          assertEquals(Integer.valueOf(41), tournamentAnalytics.getMinScore());
          assertEquals(Integer.valueOf(102), tournamentAnalytics.getMaxScore());
        },
        () -> {
          Integer year = 2012;

          TournamentAnalytics tournamentAnalytics = classUnderTest.fetchByYear(year);
          assertNotNull(tournamentAnalytics);
          assertEquals(Integer.valueOf(41), tournamentAnalytics.getMinScore());
          assertEquals(Integer.valueOf(102), tournamentAnalytics.getMaxScore());
        },
        () -> {
          Integer year = 2013;

          TournamentAnalytics tournamentAnalytics = classUnderTest.fetchByYear(year);
          assertNotNull(tournamentAnalytics);
          assertEquals(Integer.valueOf(34), tournamentAnalytics.getMinScore());
          assertEquals(Integer.valueOf(95), tournamentAnalytics.getMaxScore());
        },
        () -> {
          Integer year = 2014;

          TournamentAnalytics tournamentAnalytics = classUnderTest.fetchByYear(year);
          assertNotNull(tournamentAnalytics);
          assertEquals(Integer.valueOf(35), tournamentAnalytics.getMinScore());
          assertEquals(Integer.valueOf(93), tournamentAnalytics.getMaxScore());
        },
        () -> {
          Integer year = 2015;

          TournamentAnalytics tournamentAnalytics = classUnderTest.fetchByYear(year);
          assertNotNull(tournamentAnalytics);
          assertEquals(Integer.valueOf(39), tournamentAnalytics.getMinScore());
          assertEquals(Integer.valueOf(94), tournamentAnalytics.getMaxScore());
        },
        () -> {
          Integer year = 2016;

          TournamentAnalytics tournamentAnalytics = classUnderTest.fetchByYear(year);
          assertNotNull(tournamentAnalytics);
          assertEquals(Integer.valueOf(43), tournamentAnalytics.getMinScore());
          assertEquals(Integer.valueOf(105), tournamentAnalytics.getMaxScore());
        },
        () -> {
          Integer year = 2017;

          TournamentAnalytics tournamentAnalytics = classUnderTest.fetchByYear(year);
          assertNotNull(tournamentAnalytics);
          assertEquals(Integer.valueOf(39), tournamentAnalytics.getMinScore());
          assertEquals(Integer.valueOf(103), tournamentAnalytics.getMaxScore());
        });
  }

}
