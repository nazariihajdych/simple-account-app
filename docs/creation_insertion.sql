CREATE TABLE t_movies (
    id SERIAL NOT NULL PRIMARY KEY,
    title VARCHAR NOT NULL,
    director VARCHAR NOT NULL,
    year INT NOT NULL,
    length_min INT NOT NULL
);

CREATE TABLE t_boxoffice (
    id SERIAL NOT NULL PRIMARY KEY,
    movie_id INT UNIQUE REFERENCES t_movies(id),
    rating DOUBLE PRECISION,
    domestic_sale BIGINT,
    international_sale BIGINT
);

insert into t_movies (title, director, year, length_min) values ('Life of Pi', 'Kelsy Allerton', 2003, 114);
insert into t_movies (title, director, year, length_min) values ('Blood Shot', 'Alanson Kerwen', 2009, 94);
insert into t_movies (title, director, year, length_min) values ('Imaginary Heroes', 'Harman MacCumeskey', 2003, 175);
insert into t_movies (title, director, year, length_min) values ('Go Get Some Rosemary (Daddy Longlegs)', 'Guendolen Scarce', 2009, 174);
insert into t_movies (title, director, year, length_min) values ('Wipers Times, The', 'Linette de Mendoza', 1966, 161);
insert into t_movies (title, director, year, length_min) values ('Daria: Is It College Yet?', 'Brandon Poinsett', 2007, 128);
insert into t_movies (title, director, year, length_min) values ('Age of Consent', 'Drusy Lorking', 2012, 172);
insert into t_movies (title, director, year, length_min) values ('Once in a Lifetime', 'Larry Roebottom', 1995, 142);
insert into t_movies (title, director, year, length_min) values ('Alice', 'Calv Ruseworth', 2009, 161);
insert into t_movies (title, director, year, length_min) values ('Third Man, The', 'Banky Troake', 2007, 165);
insert into t_movies (title, director, year, length_min) values ('A Cry in the Wild', 'Lazarus Wilderspoon', 2006, 116);
insert into t_movies (title, director, year, length_min) values ('My Little Business', 'Marietta Ferris', 2007, 112);
insert into t_movies (title, director, year, length_min) values ('Grey Fox, The', 'Stevena Abrams', 2009, 164);
insert into t_movies (title, director, year, length_min) values ('Glenn, the Flying Robot', 'Ulrike Klimsch', 2000, 126);
insert into t_movies (title, director, year, length_min) values ('Lion of the Desert', 'Gabby Brotherwood', 2004, 125);
insert into t_movies (title, director, year, length_min) values ('Official Story', 'Giovanni Storer', 2011, 148);
insert into t_movies (title, director, year, length_min) values ('Life of Aleksis Kivi)', 'Lu Riggeard', 1989, 170);
insert into t_movies (title, director, year, length_min) values ('Lot Like Love, A', 'Nicola Vink', 1992, 150);
insert into t_movies (title, director, year, length_min) values ('I Bought a Vampire Motorcycle', 'Meade Caddens', 1993, 180);
insert into t_movies (title, director, year, length_min) values ('I Got Next', 'Jaime Gudd', 2002, 143); 

insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (15, 1.9, 71590, 4791829);
insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (8, 3.2, 82808, 6325299);
insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (13, 1.5, 98492, 6777914);
insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (12, 3.6, 78044, 5036985);
insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (7, 1.8, 73801, 3063586);
insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (6, 2.6, 32021, 3718150);
insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (20, 3.0, 12025, 5353578);
insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (10, 2.1, 69099, 6723318);
insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (3, 0.7, 58201, 9569982);
insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (16, 3.1, 28900, 6178757);
insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (5, 0.5, 16380, 1766768);
insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (14, 3.3, 76256, 9955977);
insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (4, 3.4, 84958, 5548930);
insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (17, 2.4, 88533, 9209696);
insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (19, 1.7, 9276, 8732553);
insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (18, 1.3, 79153, 7687176);
insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (11, 1.4, 13860, 3848337);
insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (9, 0.3, 16706, 7028993);
insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (2, 1.2, 64483, 552957);
insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values (1, 1.2, 51961, 4380983);



