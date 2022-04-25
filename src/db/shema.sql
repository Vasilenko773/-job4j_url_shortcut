create table sites(
    id       serial primary key not null,
    name    varchar(500),
login varchar(500),
password varchar(500)
);

create table urls(
 id       serial primary key not null,
 value varchar(500),
 code varchar(500),
 count int
);

create table sites_urls(
id       serial primary key not null,
 site_id int references sites (id),
 urls_id int references urls (id)
);

create table hash(
id       serial primary key not null,
 site_id int references sites (id),
 urls_id int references urls (id)
);
