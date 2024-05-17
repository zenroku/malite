drop table if exists anime;
CREATE TABLE public.anime(
	id int8 NOT NULL,
	title text not null,
	pict_medium text null,
	pict_large text null,
	start_date date null,
	end_date date null,
	score float8 null,
	rank int4 null,
	users int4 null,
	status varchar(25) null,
	scoring_users int4 null,
	nsfw varchar(25) null,
	created_at timestamp null,
	updated_at timestamp null,
	media_type varchar(25) null,
	airing_year int4 null,
	airing_season varchar(10) null,
	source varchar(20) null,
	rating varchar(10) null,
	synced_at timestamp null,
	CONSTRAINT anime_uniq_id PRIMARY KEY (id)
);