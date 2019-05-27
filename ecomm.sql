--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.17
-- Dumped by pg_dump version 9.5.17

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;



--
-- Name: ecomm; Type: SCHEMA; Schema: -; Owner: garima
--

CREATE SCHEMA ecomm;


ALTER SCHEMA ecomm OWNER TO garima;

--
-- Name: item; Type: TABLE; Schema: ecomm; Owner: garima
--

CREATE TABLE ecomm.item (
    item_id integer NOT NULL,
    item_name character varying,
    item_price integer,
    quantity_left integer
);


ALTER TABLE ecomm.item OWNER TO garima;

--
-- Name: item_item_id_seq; Type: SEQUENCE; Schema: ecomm; Owner: garima
--

CREATE SEQUENCE ecomm.item_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ecomm.item_item_id_seq OWNER TO garima;

--
-- Name: item_item_id_seq; Type: SEQUENCE OWNED BY; Schema: ecomm; Owner: garima
--

ALTER SEQUENCE ecomm.item_item_id_seq OWNED BY ecomm.item.item_id;


--
-- Name: order; Type: TABLE; Schema: ecomm; Owner: garima
--

CREATE TABLE ecomm.order (
    order_id integer NOT NULL,
    email_id character varying,
    order_made_at timestamp without time zone
);


ALTER TABLE ecomm.order OWNER TO garima;

--
-- Name: order_order_id_seq; Type: SEQUENCE; Schema: ecomm; Owner: garima
--

CREATE SEQUENCE ecomm.order_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ecomm.order_order_id_seq OWNER TO garima;

--
-- Name: order_order_id_seq; Type: SEQUENCE OWNED BY; Schema: ecomm; Owner: garima
--

ALTER SEQUENCE ecomm.order_order_id_seq OWNED BY ecomm.order.order_id;


--
-- Name: order_product; Type: TABLE; Schema: ecomm; Owner: garima
--

CREATE TABLE ecomm.order_product (
    order_id integer,
    item_id integer,
    quantity integer
);


ALTER TABLE ecomm.order_product OWNER TO garima;



--
-- Name: item_id; Type: DEFAULT; Schema: ecomm; Owner: garima
--

ALTER TABLE ONLY ecomm.item ALTER COLUMN item_id SET DEFAULT nextval('ecomm.item_item_id_seq'::regclass);


--
-- Name: order_id; Type: DEFAULT; Schema: ecomm; Owner: garima
--

ALTER TABLE ONLY ecomm."order" ALTER COLUMN order_id SET DEFAULT nextval('ecomm.order_order_id_seq'::regclass);


--
-- Data for Name: item; Type: TABLE DATA; Schema: ecomm; Owner: garima
--

COPY ecomm.item (item_id, item_name, item_price, quantity_left) FROM stdin;
1	item1	32	1
2	item2	32	3
3	item3	32	1
4	item4	32	1
5	item6	32	1
7	item51	32	4
6	item5	32	2
\.


--
-- Name: item_item_id_seq; Type: SEQUENCE SET; Schema: ecomm; Owner: garima
--

SELECT pg_catalog.setval('ecomm.item_item_id_seq', 7, true);


--
-- Data for Name: order; Type: TABLE DATA; Schema: ecomm; Owner: garima
--

COPY ecomm."order" (order_id, email_id, order_made_at) FROM stdin;
1	abc@abc.com	2019-05-26 22:09:34
2	abc@abc.com	2019-05-26 22:12:11
3	abc@abc.com	2019-05-26 22:14:18
4	abc@abc.com	2019-05-26 22:17:13
5	abc@abc.com	2019-05-26 22:17:48
6	abc@abc.com	2019-05-26 22:23:54
7	abc@abc.com	2019-05-26 23:30:35
\.


--
-- Name: order_order_id_seq; Type: SEQUENCE SET; Schema: ecomm; Owner: garima
--

SELECT pg_catalog.setval('ecomm.order_order_id_seq', 7, true);


--
-- Data for Name: order_product; Type: TABLE DATA; Schema: ecomm; Owner: garima
--

COPY ecomm.order_product (order_id, item_id, quantity) FROM stdin;
3	1	2
3	2	1
5	3	3
5	4	3
6	6	1
6	5	3
7	6	1
\.

--
-- Name: item_pkey; Type: CONSTRAINT; Schema: ecomm; Owner: garima
--

ALTER TABLE ONLY ecomm.item
    ADD CONSTRAINT item_pkey PRIMARY KEY (item_id);


--
-- Name: order_pkey; Type: CONSTRAINT; Schema: ecomm; Owner: garima
--

ALTER TABLE ONLY ecomm.order
    ADD CONSTRAINT order_pkey PRIMARY KEY (order_id);




--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

