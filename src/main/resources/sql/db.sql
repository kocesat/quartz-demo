create table auctions (
  id serial not null,
  title varchar(255),
  closing_time timestamp(6),
  status integer,
  bids varchar(255),
  winner_price numeric(38,2) null,
  primary key (id)
)

INSERT INTO auctions (title, closing_time, status, bids)
VALUES
    ('Tesla İhalesi', '2022/02/27 23:00', 1, '2300.50|1400.30|2000.00'),
    ('Macbook İhalesi', '2022/02/27 17:00', 1, '100.50|120.30|90.00'),
    ('Iphone İhalesi', '2022/02/27 23:00', 1, '34.50|82.30|62.00')