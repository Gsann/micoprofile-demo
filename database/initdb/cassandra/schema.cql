DROP KEYSPACE IF EXISTS demo;

CREATE KEYSPACE demo WITH replication = {
  'class': 'SimpleStrategy',
  'replication_factor' : 1
};

USE demo;

CREATE TABLE book_borrow_tx (
  book_id text,
  create_datetime timestamp,
  tx_type boolean,
  user map<text,text>,
  book map<text,text>,
  PRIMARY KEY (book_id, create_datetime)
) WITH CLUSTERING ORDER BY (create_datetime DESC);
