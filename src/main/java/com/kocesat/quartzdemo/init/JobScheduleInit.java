package com.kocesat.quartzdemo.init;

import com.kocesat.quartzdemo.job.DetermineAuctionWinnerJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

@Component
@Slf4j
public class JobScheduleInit implements CommandLineRunner {

  private final Scheduler scheduler;

  public JobScheduleInit(Scheduler scheduler) {
    this.scheduler = scheduler;
  }

  @Override
  public void run(String... args) throws Exception {
    log.info("Application started");
    final JobKey jobKey = JobKey.jobKey("determineAuctionWinner", "demoApp");
    if (scheduler.checkExists(jobKey)) {
      scheduler.deleteJob(jobKey);
    }

    final JobDetail jobDetail = JobBuilder.newJob(DetermineAuctionWinnerJob.class)
      .withIdentity(jobKey.getName(), jobKey.getGroup())
      .withDescription("Determines the auction winner")
      .storeDurably()
      .build();

    final TriggerKey triggerKey = TriggerKey.triggerKey("determineAuctionWinnerTrigger", "demoApp");
    final CronTrigger cronTrigger = TriggerBuilder.newTrigger()
      .forJob(jobDetail)
      .withIdentity(triggerKey)
      .withSchedule(
        CronScheduleBuilder
          .cronSchedule("0/20 * * * * ?")
          .withMisfireHandlingInstructionFireAndProceed()
          .inTimeZone(TimeZone.getTimeZone("Europe/Istanbul"))
      )
      .build();

    final Trigger simpleTrigger = TriggerBuilder.newTrigger()
        .forJob(jobDetail)
        .withIdentity(triggerKey)
        .withSchedule(
          SimpleScheduleBuilder
            .simpleSchedule()
            .withIntervalInSeconds(20)
            .repeatForever()
        )
        .startNow()
        .build();

    scheduler.scheduleJob(jobDetail, cronTrigger);
  }
}