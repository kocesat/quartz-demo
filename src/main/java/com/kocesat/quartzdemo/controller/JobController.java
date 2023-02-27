package com.kocesat.quartzdemo.controller;

import com.kocesat.quartzdemo.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("job")
@RequiredArgsConstructor
public class JobController {
  private final JobService jobService;

  @PutMapping("/start-stop")
  public ResponseEntity<String> startStop(
    @RequestParam("name") String jobName,
    @RequestParam("group") String jobGroup)
  {
    return ResponseEntity.ok(jobService.startStop(jobName, jobGroup));
  }

  @PutMapping("/reschedule")
  public ResponseEntity<String> reschedule(
    @RequestParam("name") String jobName,
    @RequestParam("group") String jobGroup,
    @RequestParam("cron") String cronExpression
    )
  {
    return ResponseEntity.ok(jobService.reschedule(jobName, jobGroup, cronExpression));
  }
}
