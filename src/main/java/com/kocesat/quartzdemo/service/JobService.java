package com.kocesat.quartzdemo.service;

import com.kocesat.quartzdemo.exception.AppRuntimeException;
import com.kocesat.quartzdemo.exception.JobNotFoundException;
import org.quartz.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@Service
@Transactional
public class JobService {
  private final Scheduler scheduler;

  public JobService(Scheduler scheduler) {
    this.scheduler = scheduler;
  }

  public String reschedule(String jobName, String jobGroup, String cronExpression) {
    final JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
    try {
      if (!scheduler.checkExists(jobKey))
        throw new JobNotFoundException();
      var triggers = scheduler.getTriggersOfJob(jobKey);
      if (triggers.isEmpty()) {
        throw new JobNotFoundException("Trigger not found");
      }
      Trigger oldTrigger = triggers.get(0);
      Trigger newTrigger = oldTrigger
        .getTriggerBuilder()
        .withSchedule((ScheduleBuilder)cronSchedule(cronExpression))
        .build();

      scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
      return "Job rescheduled";

    } catch (SchedulerException e) {
      throw new AppRuntimeException("Job start stop exception");
    }
  }

  public String startStop(String jobName, String jobGroup) {
    final JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
    try {
      if (!scheduler.checkExists(jobKey))
        throw new JobNotFoundException();
      var triggers = scheduler.getTriggersOfJob(jobKey);
      if (triggers.isEmpty()) {
        throw new JobNotFoundException("Trigger not found");
      }
      var trigger = scheduler.getTriggersOfJob(jobKey).get(0);
      Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
      if (triggerState == Trigger.TriggerState.PAUSED) {
        scheduler.resumeTrigger(trigger.getKey());
        return "Job resumed";
      } else {
        scheduler.pauseTrigger(trigger.getKey());
        return "Job paused";
      }
    } catch (SchedulerException e) {
      throw new AppRuntimeException("Job start stop exception");
    }
  }
}
