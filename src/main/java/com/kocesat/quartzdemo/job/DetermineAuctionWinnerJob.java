package com.kocesat.quartzdemo.job;

import com.kocesat.quartzdemo.model.Auction;
import com.kocesat.quartzdemo.service.AuctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@DisallowConcurrentExecution
@RequiredArgsConstructor
@Slf4j
public class DetermineAuctionWinnerJob extends QuartzJobBean {
  private final AuctionService auctionService;

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    log.info("DetermineAuctionWinnerJob fired at " + LocalDateTime.now());
    try {
      final List<Auction> auctions = auctionService.getOpenAuctions();
      if (auctions.isEmpty()) {
        log.info("No open auctions found. Returning!!!");
      }
      auctions.forEach(auction -> {
        log.info(auction.toString());
        if (auction.getClosingTime().isBefore(LocalDateTime.now())) {
          auctionService.decideWinner(auction.getId());
          log.info(auction + " updated and closed!");
        }
      });
    } catch (Exception e) {
      log.error("DetermineAuctionWinnerJob exception: " + e.getMessage(), e);
    }
  }

}
