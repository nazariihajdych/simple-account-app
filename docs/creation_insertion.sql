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

insert into t_movies (title, director, year, length_min) values
('Life of Pi', 'Kelsy Allerton', 2003, 114),
('Blood Shot', 'Alanson Kerwen', 2009, 94),
('Imaginary Heroes', 'Harman MacCumeskey', 2003, 175),
('Go Get Some Rosemary (Daddy Longlegs)', 'Guendolen Scarce', 2009, 174),
('Wipers Times, The', 'Linette de Mendoza', 1966, 161),
('Daria: Is It College Yet?', 'Brandon Poinsett', 2007, 128),
('Age of Consent', 'Drusy Lorking', 2012, 172),
('Once in a Lifetime', 'Larry Roebottom', 1995, 142),
('Alice', 'Calv Ruseworth', 2009, 161),
('Third Man, The', 'Banky Troake', 2007, 165),
('A Cry in the Wild', 'Lazarus Wilderspoon', 2006, 116),
('My Little Business', 'Marietta Ferris', 2007, 112),
('Grey Fox, The', 'Stevena Abrams', 2009, 164),
('Glenn, the Flying Robot', 'Ulrike Klimsch', 2000, 126),
('Lion of the Desert', 'Gabby Brotherwood', 2004, 125),
('Official Story', 'Giovanni Storer', 2011, 148),
('Life of Aleksis Kivi)', 'Lu Riggeard', 1989, 170),
('Lot Like Love, A', 'Nicola Vink', 1992, 150),
('I Bought a Vampire Motorcycle', 'Meade Caddens', 1993, 180),
('I Got Next', 'Jaime Gudd', 2002, 143);

insert into t_boxoffice (movie_id, rating, domestic_sale, international_sale) values
(15, 1.9, 71590, 4791829),
(8, 3.2, 82808, 6325299),
(13, 1.5, 98492, 6777914),
(12, 3.6, 78044, 5036985),
(7, 1.8, 73801, 3063586),
(6, 2.6, 32021, 3718150),
(20, 3.0, 12025, 5353578),
(10, 2.1, 69099, 6723318),
(3, 0.7, 58201, 9569982),
(16, 3.1, 28900, 6178757),
(5, 0.5, 16380, 1766768),
(14, 3.3, 76256, 9955977),
(4, 3.4, 84958, 5548930),
(17, 2.4, 88533, 9209696),
(19, 1.7, 9276, 8732553),
(18, 1.3, 79153, 7687176),
(11, 1.4, 13860, 3848337),
(9, 0.3, 16706, 7028993),
(2, 1.2, 64483, 552957),
(1, 1.2, 51961, 4380983);



