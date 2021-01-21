package org.orgname.app.database.manager;

import org.orgname.app.database.entity.ServiceEntity;
import org.orgname.app.util.MysqlDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEntityManager
{
    private MysqlDatabase database;

    public ServiceEntityManager(MysqlDatabase database) {
        this.database = database;
    }

    public void add(ServiceEntity service) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "INSERT INTO Service(Title, Cost, DurationInSeconds, Discount, MainImagePath) values(?,?,?,?,?)";
            PreparedStatement s = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            s.setString(1, service.getTitle());
            s.setDouble(2, service.getCost());
            s.setInt(3, service.getDuration());
            s.setDouble(4, service.getDiscount());
            s.setString(5, service.getImgPath());

            s.executeUpdate();

            ResultSet keys = s.getGeneratedKeys();
            if (keys.next()) {
                service.setId(keys.getInt(1));
                return;
            }

            throw new SQLException("User not added");
        }
    }

    public ServiceEntity getById(int id) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM Service WHERE id=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, id);

            ResultSet resultSet = s.executeQuery();
            if(resultSet.next()) {
                return new ServiceEntity(
                        resultSet.getInt("ID"),
                        resultSet.getString("Title"),
                        resultSet.getDouble("Cost"),
                        resultSet.getInt("DurationInSeconds"),
                        resultSet.getDouble("Discount"),
                        resultSet.getString("MainImagePath")
                );
            }

            return null;
        }
    }

    public List<ServiceEntity> getAll() throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM Service";
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);

            List<ServiceEntity> services = new ArrayList<>();
            while(resultSet.next()) {
                services.add(new ServiceEntity(
                        resultSet.getInt("ID"),
                        resultSet.getString("Title"),
                        resultSet.getDouble("Cost"),
                        resultSet.getInt("DurationInSeconds"),
                        resultSet.getDouble("Discount"),
                        resultSet.getString("MainImagePath")
                ));
            }
            return services;
        }
    }

    public int update(ServiceEntity service) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "UPDATE Service SET Title=?, Cost=?, DurationInSeconds=?, Discount=?, MainImagePath=? WHERE id=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1, service.getTitle());
            s.setDouble(2, service.getCost());
            s.setInt(3, service.getDuration());
            s.setDouble(4, service.getDiscount());
            s.setString(5, service.getImgPath());
            s.setInt(6, service.getId());

            return s.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "DELETE FROM Service WHERE id=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, id);

            return s.executeUpdate();
        }
    }

    public int delete(ServiceEntity service) throws SQLException
    {
        return deleteById(service.getId());
    }
}
