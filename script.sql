-- Creating the 'library_items' table
CREATE TABLE library_items (
id SERIAL PRIMARY KEY,
title VARCHAR(255) NOT NULL,
author VARCHAR(255) NOT NULL,
publication_year INT NOT NULL,
item_type VARCHAR(50) NOT NULL CHECK (item_type IN ('Book', 'Magazine', 'DVD'))
);

-- Creating the 'books' table
CREATE TABLE books (
id INTEGER PRIMARY KEY,
genre VARCHAR(255) NOT NULL,
pages INTEGER NOT NULL,
isbn VARCHAR(13) UNIQUE NOT null,
FOREIGN KEY (id) REFERENCES library_items (id) ON DELETE CASCADE
);

-- Creating the 'magazines' table
CREATE TABLE magazines (
id INTEGER PRIMARY KEY,
issue_number INTEGER NOT NULL,
publisher VARCHAR(255) NOT NULL,
FOREIGN KEY (id) REFERENCES library_items (id) ON DELETE CASCADE
);

-- Creating the 'dvds' table
CREATE TABLE dvds (
id INTEGER PRIMARY KEY,
duration_minutes INTEGER NOT NULL,
director VARCHAR(255) NOT NULL,
rating VARCHAR(5) NOT NULL CHECK (rating IN ('G', 'PG', 'PG-13', 'R', 'NC-17')),
FOREIGN KEY (id) REFERENCES library_items (id) ON DELETE CASCADE
);
