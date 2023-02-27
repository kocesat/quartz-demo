package com.kocesat.quartzdemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "auctions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Auction implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String title;
  private LocalDateTime closingTime;
  private Integer status; // 1-açık, 0-kapalı
  private String bids; // "23.50|14.30|200.00" şeklinde tutacağız
  private BigDecimal winnerPrice;
}
