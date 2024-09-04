create table if not exists endpoint_hits (
id bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
app VARCHAR(255) NOT NULL,
uri VARCHAR(512) NOT NULL,
ip VARCHAR(50) NOT NULL,
time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
CONSTRAINT pk_endpoint_hits PRIMARY KEY (id)
);
