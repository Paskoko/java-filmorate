# java-filmorate
Template repository for Filmorate project.

![ER diagram](/Scheme.png)

Basic commands for database:
```
SELECT *
FROM users AS u
LEFT OUTER JOIN friends AS f ON f.user_id = u.id; 

SELECT *
FROM films AS f
LEFT OUTER JOIN film_genre AS fg ON fg.film_id = f.id
LEFT OUTER JOIN genres AS g ON g.genre_id = fd.genre_id
LEFT OUTER JOIN likes AS l ON l.film_id = f.id
LEFT OUTER JOIN film_MPA_rating AS fmr ON fmr.film_id = f.id
LEFT OUTER JOIN MPA_rating AS mr ON mr.rating_id = fmr.rating_id;