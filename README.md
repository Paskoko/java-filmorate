# java-filmorate

Repository for Filmorate project.

![ER diagram](/Scheme.png)

Basic commands for database:

```
SELECT * FROM films WHERE id = ?;

INSERT INTO film_genre (FILM_ID, GENRE_ID) VALUES (?, ?)

SELECT friend_id FROM friends WHERE user_id = ?;
