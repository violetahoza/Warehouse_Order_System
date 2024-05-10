package dataAccess;

import java.beans.*;
import java.lang.reflect.*;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import connection.ConnectionFactory;

/**
 * A class that implements methods which allow CRUD operations on a given database.
 * It uses generics so the methods are available on any class.
 * @param <T> The type of object handled by this DAO.
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;
    /**
     * Constructs a new AbstractDAO with the specified type.
     * @param type The class type handled by this DAO.
     */
    public AbstractDAO(Class<T> type){
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Creates objects of type T from a ResultSet.
     * @param resultSet The ResultSet containing data for object initialization.
     * @return A list of objects of type T.
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (Constructor constructor : ctors) {
            ctor = constructor;
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                Objects.requireNonNull(ctor).setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException |
                 InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Retrieves all objects of type T from the database.
     * @return A list of all objects of type T.
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM ";
        query += type.getSimpleName();
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Retrieves the properties of the given object and constructs a string representation.
     * @param t The object for which properties are retrieved.
     * @return A string representation of the object's properties for use in SQL queries.
     */
    private String getProperties(T t) {
        StringBuilder query = new StringBuilder();
        for (Field field : t.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {
                if (String.class.isAssignableFrom(field.getType()))
                    query.append("\"");
                query.append(field.get(t).toString());
                if (String.class.isAssignableFrom(field.getType()))
                    query.append("\"");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (!field.equals(t.getClass().getDeclaredFields()[(t.getClass().getDeclaredFields().length)-1]))
                query.append(", ");
            else
                query.append(")");
        }
        return query.toString();
    }

    /**
     * @param t is an object of type T used to get the actual name of the class and the values of each field - in getProperties method
     * @return an SQL string that is used to insert a new row in the database
     */
    private String insertQuery(T t){
        StringBuilder query = new StringBuilder("INSERT INTO schooldb." + t.getClass().getSimpleName().toLowerCase(Locale.ROOT) + " (");
        for (Field field : t.getClass().getDeclaredFields()){
            String fieldName = field.getName();
            query.append(fieldName);
            if (!field.equals(t.getClass().getDeclaredFields()[t.getClass().getDeclaredFields().length - 1]))
                query.append(", ");
            else
                query.append(")");
        }
        query.append(" VALUES (");
        query.append(getProperties(t));
        return query.toString();
    }
    /**
     * Inserts a new object into the database.
     * @param t The object to be inserted.
     * @throws SQLException if a database access error occurs.
     */
    public void insert(T t) throws SQLException{
        String SQL = insertQuery(t);
        ConnectionFactory.connectAndExecute(SQL);
    }

    /**
     * Constructs an update query string for the given object.
     * @param t The object for which the update query is constructed.
     * @return An update query string for the object.
     */
    private String updateQuery(T t){
        StringBuilder query = new StringBuilder("UPDATE " + t.getClass().getSimpleName() + " SET ");
        String id = null;
        for(Field field : t.getClass().getDeclaredFields()){
            field.setAccessible(true);
            String fieldName = field.getName();
            try {
                query.append(fieldName).append(" = ");
                if (String.class.isAssignableFrom(field.getType()))
                    query.append("\"");
                query.append(field.get(t).toString());
                if (String.class.isAssignableFrom(field.getType()))
                    query.append("\"");

                if (fieldName.equals("id"))
                    id = field.get(t).toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (!field.equals(t.getClass().getDeclaredFields()[t.getClass().getDeclaredFields().length - 1]))
                query.append(", ");
            else {
                query.append(" WHERE id = ").append(id);
            }
        }
        return query.toString();
    }
    /**
     * Updates an existing object in the database.
     * @param t The object to be updated.
     * @throws SQLException if a database access error occurs.
     */
    public void update(T t) throws SQLException{
        String SQL = updateQuery(t);
        ConnectionFactory.connectAndExecute(SQL);
    }

    /**
     * Constructs a delete query string for the given object.
     * @param t The object for which the delete query is constructed.
     * @return A delete query string for the object.
     */
    private String deleteQuery(T t){
        StringBuilder query = new StringBuilder("DELETE FROM "+ t.getClass().getSimpleName()+ " WHERE id = ");
        try {
            Field field = t.getClass().getDeclaredFields()[0];
            field.setAccessible(true);
            query.append(field.get(t).toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return query.toString();
    }
    /**
     * Deletes an object from the database.
     * @param t The object to be deleted.
     * @throws SQLException if a database access error occurs.
     */
    public void delete(T t) throws SQLException{
        String SQL = deleteQuery(t);
        ConnectionFactory.connectAndExecute(SQL);
    }

    /**
     * Constructs a select query string for retrieving objects based on a field value.
     * @param field The field to filter the select query by.
     * @return A select query string for retrieving objects based on the specified field.
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }
    /**
     * Retrieves an object by its ID from the database.
     * @param id The ID of the object to retrieve.
     * @return The object with the specified ID, or null if not found.
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
}
