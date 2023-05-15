package main.java.com.javabootcamptestadvance.repository;

import main.java.com.javabootcamptestadvance.models.Item;
import main.java.com.javabootcamptestadvance.models.Book;
import main.java.com.javabootcamptestadvance.models.Dvd;
import main.java.com.javabootcamptestadvance.models.Magazine;
import main.java.com.javabootcamptestadvance.utils.LocalizationUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RepositoryPostGreSQL<T extends Item> implements RepositoryBase<T> {
    private Connection connection;

    @Override
    public void connect() {
        String url = "jdbc:postgresql://localhost:5432/library";
        String username = "postgres";
        String password = "admin";
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println(LocalizationUtil.getInfoString("info.database.connection_success"));
        } catch (SQLException e) {
            System.out.println(LocalizationUtil.getErrorString("error.database.connect"));
        }
    }

    @Override
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println(LocalizationUtil.getInfoString("info.database.connection_closed"));
            } catch (SQLException e) {
                System.out.println(LocalizationUtil.getErrorString("error.database.close"));
            }
        }
    }

    @Override
    public boolean checkIsConnected() {
        if (connection == null) {
            return false;
        }
        try {
            if (connection.isClosed()) {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(LocalizationUtil.getErrorString("error.database.connection"));
            return false;
        }
        return true;
    }

    @Override
    public void addItem(T item) {
        String insertItemQueryString = "INSERT INTO library_items (title, author, publication_year, item_type) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertItemQueryString, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getTitle());
            statement.setString(2, item.getAuthor());
            statement.setInt(3, item.getPublicationYear());
            statement.setString(4, item.getItemType().getValue());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int itemId = generatedKeys.getInt(1);
                item.setId(itemId);
            }
        } catch (SQLException e) {
            System.out.println(LocalizationUtil.getErrorString("error.database.executing.insert.item"));
        }

        String insertSpecificItemQueryString;
        if (item instanceof Book book) {
            insertSpecificItemQueryString = "INSERT INTO books (id, genre, pages, isbn) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertSpecificItemQueryString)) {
                statement.setInt(1, book.getId());
                statement.setString(2, book.getGenre());
                statement.setInt(3, book.getPages());
                statement.setString(4, book.getIsbn());

                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(LocalizationUtil.getErrorString("error.database.executing.insert.item"));
            }
        } else if (item instanceof Magazine magazine) {
            insertSpecificItemQueryString = "INSERT INTO magazines (id, issue_number, publisher) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertSpecificItemQueryString)) {
                statement.setInt(1, magazine.getId());
                statement.setInt(2, magazine.getIssueNumber());
                statement.setString(3, magazine.getPublisher());

                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(LocalizationUtil.getErrorString("error.database.executing.insert.item"));
            }
        } else if (item instanceof Dvd dvd) {
            insertSpecificItemQueryString = "INSERT INTO dvds (id, duration_minutes, director, rating) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertSpecificItemQueryString)) {
                statement.setInt(1, dvd.getId());
                statement.setInt(2, dvd.getDurationMinutes());
                statement.setString(3, dvd.getDirector());
                statement.setString(4, dvd.getRating().getValue());

                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(LocalizationUtil.getErrorString("error.database.executing.insert.item"));
            }
        }

        System.out.println(LocalizationUtil.getInfoString("info.database.success.item_upload"));
    }

    @Override
    public void updateItem(T item) {
        String updateItemQueryString = "UPDATE library_items SET title = ?, author = ?, publication_year = ?, item_type = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(updateItemQueryString)) {
            statement.setString(1, item.getTitle());
            statement.setString(2, item.getAuthor());
            statement.setInt(3, item.getPublicationYear());
            statement.setString(4, item.getItemType().getValue());
            statement.setInt(5, item.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(LocalizationUtil.getErrorString("error.database.executing.update.item"));
        }

        String updateSpecificItemQueryString;
        if (item instanceof Book book) {
            updateSpecificItemQueryString = "UPDATE books SET genre = ?, pages = ?, isbn = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateSpecificItemQueryString)) {
                statement.setString(1, book.getGenre());
                statement.setInt(2, book.getPages());
                statement.setString(3, book.getIsbn());
                statement.setInt(4, book.getId());

                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(LocalizationUtil.getErrorString("error.database.executing.update.item"));
            }
        } else if (item instanceof Magazine magazine) {
            updateSpecificItemQueryString = "UPDATE magazines issue_number = ?, publisher = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateSpecificItemQueryString)) {
                statement.setInt(1, magazine.getIssueNumber());
                statement.setString(2, magazine.getPublisher());
                statement.setInt(3, magazine.getId());

                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(LocalizationUtil.getErrorString("error.database.executing.update.item"));
            }
        } else if (item instanceof Dvd dvd) {
            updateSpecificItemQueryString = "UPDATE dvds duration_minutes = ?, director = ?, rating = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateSpecificItemQueryString)) {
                statement.setInt(1, dvd.getDurationMinutes());
                statement.setString(2, dvd.getDirector());
                statement.setString(3, dvd.getRating().getValue());
                statement.setInt(4, dvd.getId());

                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(LocalizationUtil.getErrorString("error.database.executing.update.item"));
            }
        }
        System.out.println(LocalizationUtil.getInfoString("info.database.success.item_update"));
    }

    @Override
    public void deleteItem(T item) {
        deleteFromTable("library_items", item.getId());
    }

    private void deleteFromTable(String tableName, int itemId) {
        String itemType = tableName.substring(0, 1).toUpperCase() + tableName.substring(1).replace("s", "");
        String deleteItemQueryString = "DELETE FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteItemQueryString)) {
            statement.setInt(1, itemId);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                System.out.println(LocalizationUtil.getErrorString("error.list.item.not_found"));
            } else {
                System.out.println(LocalizationUtil.getInfoString("info.database.success.item_delete"));
            }
        } catch (SQLException e) {
            System.out.println(LocalizationUtil.getErrorString("error.database.executing.delete.item"));;
        }
    }

    @Override
    public List<T> getAllItems() {
        List<T> itemContainer = new ArrayList<>();
        String queryString = "SELECT * FROM library_items LEFT JOIN books ON library_items.id = books.id"
                + " LEFT JOIN magazines ON library_items.id = magazines.id"
                + " LEFT JOIN dvds ON library_items.id = dvds.id";
        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    T item = createItem(resultSet);
                    itemContainer.add(item);
                }
            }
        } catch (SQLException e) {
            System.out.println(LocalizationUtil.getErrorString("error.database.executing.select.all"));
        }
        return itemContainer;
    }

    @Override
    public List<T> getItemsByFilter(Map<String, String> filters) {
        List<T> itemContainer = new ArrayList<>();
        String selectString = "SELECT * FROM library_items LEFT JOIN books ON library_items.id = books.id"
                + " LEFT JOIN magazines ON library_items.id = magazines.id"
                + " LEFT JOIN dvds ON library_items.id = dvds.id";
        String queryString = createQuery(filters, selectString);

        try (PreparedStatement statement = connection.prepareStatement(queryString)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    T item = createItem(resultSet);
                    itemContainer.add(item);
                }
            }
        } catch (SQLException e) {
            System.out.println(LocalizationUtil.getErrorString("error.database.executing.select.all"));
        }
        return itemContainer;
    }

    private T createItem(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String itemTitle = resultSet.getString("title");
        String itemAuthor = resultSet.getString("author");
        int itemPublicationYear = resultSet.getInt("publication_year");
        Item.Type type = Item.Type.fromValue(resultSet.getString("item_type"));

        T item;
        if (Item.Type.BOOK.equals(type)) {
            String genre = resultSet.getString("genre");
            int pages = resultSet.getInt("pages");
            String isbn = resultSet.getString("isbn");
            item = (T) new Book(id, itemTitle, itemAuthor, itemPublicationYear, type, genre, pages, isbn);
        } else if (Item.Type.MAGAZINE.equals(type)) {
            int issueNumber = resultSet.getInt("issue_number");
            String publisher = resultSet.getString("publisher");
            item = (T) new Magazine(id, itemTitle, itemAuthor, itemPublicationYear, type, issueNumber, publisher);
        } else if (Item.Type.DVD.equals(type)) {
            int durationMinutes = resultSet.getInt("duration_minutes");
            String director = resultSet.getString("director");
            Dvd.Rating rating = Dvd.Rating.fromValue(resultSet.getString("rating"));
            item = (T) new Dvd(id, itemTitle, itemAuthor, itemPublicationYear, type, durationMinutes, director, rating);
        } else {
            System.out.println(LocalizationUtil.getErrorString("error.item.invalid_type"));
            return null;
        }
        return item;
    }

    private String createQuery(Map<String, String> filters, String query) {
        StringBuilder queryBuilder = new StringBuilder(query);

        if (filters.values().isEmpty()) {
            return query;
        }

        boolean needWhere = true;
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (!key.isEmpty()) {
                if (needWhere) {
                    queryBuilder.append(" WHERE ");
                    needWhere = false;
                } else {
                    queryBuilder.append(" AND ");
                }

                if (key.equals("id")) {
                    queryBuilder.append("library_items.");
                }

                queryBuilder.append(key);

                if (isNumber(value)) {
                    queryBuilder.append(" = ").append(value);
                } else {
                    queryBuilder.append(" LIKE ").append("'%").append(value).append("%'");
                }
            }
        }
        return queryBuilder.toString();
    }

    private boolean isNumber(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
