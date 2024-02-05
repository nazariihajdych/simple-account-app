--Select operations:
SELECT title FROM t_movies;

SELECT director FROM t_movies;

SELECT title, director FROM t_movies;

SELECT title, year FROM t_movies;

SELECT * FROM t_movies;

--Condition operation:
SELECT * FROM t_movies WHERE year BETWEEN 2000 AND 2010;

SELECT * FROM t_movies WHERE year < 2000 OR year > 2010;

SELECT title, year FROM t_movies WHERE year IN (2000, 2010, 2020);

SELECT * FROM t_movies WHERE title ILIKE 'b%' OR title ILIKE 's%' OR title ILIKE 'p%';

SELECT DISTINCT director FROM t_movies WHERE year > 2005 ORDER BY director;

--JOINs:
SELECT m.title, b.domestic_sale, b.international_sale
FROM t_movies m
JOIN t_boxoffice b ON m.id = b.movie_id;

SELECT m.title, m.director, b.rating
FROM t_movies m
JOIN t_boxoffice b ON m.id = b.movie_id
WHERE b.international_sale > b.domestic_sale;

SELECT m.title, b.rating
FROM t_movies m
JOIN t_boxoffice b ON m.id = b.movie_id
ORDER BY b.rating DESC;

SELECT m.title, (b.domestic_sale + b.international_sale) AS combined_sales
FROM t_movies m
JOIN t_boxoffice b ON m.id = b.movie_id;

SELECT * FROM t_movies WHERE year % 2 = 0;

SELECT m.director, COUNT(m.id) AS num_movies, AVG(b.rating) AS avg_rating
FROM t_movies m
JOIN t_boxoffice b ON m.id = b.movie_id
GROUP BY m.director;

SELECT m.director, SUM(b.domestic_sale) AS total_domestic_sales, SUM(b.international_sale) AS total_international_sales
FROM t_movies m
JOIN t_boxoffice b ON m.id = b.movie_id
GROUP BY m.director;

