package com.kocesat.quartzdemo.repository;

import com.kocesat.quartzdemo.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Integer> {
  List<Auction> findAuctionByStatus(Integer status);
}
