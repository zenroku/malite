drop table if exists registered_fields;
drop sequence if exists registered_fields_id_seq;
CREATE SEQUENCE registered_fields_id_seq;
CREATE TABLE public.registered_fields(
	id int4 NOT NULL default nextval('registered_fields_id_seq'),
	"key" varchar(255) not null,
	"field_name" varchar(255) not null,
	"column_name" varchar(255) not null,
	"data_type" varchar(255) not null,
	"is_active" boolean not null default true,
	CONSTRAINT registered_fields_uniq_id PRIMARY KEY (id)
);