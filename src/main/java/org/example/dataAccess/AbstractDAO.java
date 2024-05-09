package org.example.dataAccess;

import org.example.connection.ConnectionFactory;

import javax.swing.table.DefaultTableModel;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The generic class that contains the methods for accesing the database
 * @param <T> it can be either Client, Product or Order
 */


public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    public AbstractDAO() {
        this.type = (Class<T>)  ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * The method for creating the tables
     * @param list It contains the elements for the table
     * @return A table with the items for the list
     * @throws IllegalAccessException
     */
    public static DefaultTableModel buildTable(List<?> list) throws IllegalAccessException {
        if (list.isEmpty()) {
            return new DefaultTableModel();
        }

        Vector<String> columnsNames = new Vector<>();
        Vector<Vector<Object>> dataVector = new Vector<>();

        Field[] fields = list.get(0).getClass().getDeclaredFields();

        for(Field field : fields) {
            columnsNames.add(field.getName());
            field.setAccessible(true);
        }

        for(Object obj : list) {
            Vector<Object> data = new Vector<>();
            for(Field field : fields) {
                data.add(field.get(obj));  //iau valorile pentru fiecare coloana
            }
            dataVector.add(data);
        }

        return new DefaultTableModel(dataVector, columnsNames);
    }

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
     *
     * @return all the elements from a table
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName();
        List<T> list = new ArrayList<>();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            list = createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "findAll: Error getting all records", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return list;
    }

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

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;

        for(int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if(ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while(resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for(Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        }catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            Field[] fields = type.getDeclaredFields();
            int index = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                statement.setObject(index++, field.get(t));
            }

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return t;
            }
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, "insert: Error inserting object", e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private String createInsertQuery() {
        StringBuilder fields = new StringBuilder();
        StringBuilder placeHolders = new StringBuilder();
        for(Field field: type.getDeclaredFields()) {
            fields.append(field.getName()).append(",");
            placeHolders.append("?,");
        }

        fields.setLength(fields.length() - 1);
        placeHolders.setLength(placeHolders.length() - 1);

        return "INSERT INTO " + type.getSimpleName() + " (" + fields.toString() + ") VALUES (" + placeHolders.toString() + ")";
    }

    public T update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            Field[] fields = type.getDeclaredFields();
            int index = 1;
            Object idValue = null;
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                if (field.getName().equals("id")) {
                    idValue = field.get(t);
                    continue;
                }
                statement.setObject(index++, field.get(t));
            }
            if (idValue != null) {
                statement.setObject(index, idValue);
            }

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return t;
            }
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, "Error updating object", e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private String createUpdateQuery() {
        StringBuilder setClause = new StringBuilder();
        for (Field field : type.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) || field.getName().equals("id")) {
                continue;
            }
            setClause.append(field.getName()).append("=?,");
        }
        setClause.setLength(setClause.length() - 1);

        return "UPDATE " + type.getSimpleName() + " SET " + setClause.toString() + " WHERE id=?";
    }

    private String createDeleteQuery() {
        return "DELETE FROM " + type.getSimpleName() + " WHERE id = ?";
    }

    public boolean delete(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery();
        boolean result = false;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            Field field = type.getDeclaredField("id");
            field.setAccessible(true);
            Object idValue = field.get(t);

            statement.setObject(1, idValue);
            int rowsAffected = statement.executeUpdate();
            result = rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting object: " + t, e);

        } catch (NoSuchFieldException e) {
            LOGGER.log(Level.SEVERE, "Field 'id' not found");
        }catch (IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, "Illegal acces to field 'id'", e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return result;
    }





}
