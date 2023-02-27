package com.kocesat.quartzdemo.service;

import com.kocesat.quartzdemo.exception.AppRuntimeException;
import com.kocesat.quartzdemo.model.Auction;
import com.kocesat.quartzdemo.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional(rollbackFor = Throwable.class)
@RequiredArgsConstructor
public class AuctionService {
  private final AuctionRepository repository;
  @Transactional(readOnly = true)
  public List<Auction> getOpenAuctions() {
    return repository.findAuctionByStatus(1);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void decideWinner(Integer id) {
    final Auction auction = repository
      .findById(id)
      .orElseThrow(AppRuntimeException::new);
    auction.setWinnerPrice(winnerPrice(auction.getBids()));
    auction.setStatus(0);
    repository.save(auction);
  }

  private BigDecimal winnerPrice(String bids) {
    if (bids == null) {
      return BigDecimal.ZERO;
    }
    return Arrays.stream(bids.split("\\|"))
      .map(BigDecimal::new)
      .max(BigDecimal::compareTo)
      .orElseThrow(AppRuntimeException::new);
  }

}
