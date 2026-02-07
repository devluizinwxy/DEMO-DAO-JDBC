package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {
    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }
    @Override
    public Seller findById(Integer id) {
          PreparedStatement statement = null;
           ResultSet resultSet = null;
        String sql = "SELECT seller.*, departament.Nome AS DepName "
                + "FROM seller "
                + "INNER JOIN departament ON seller.DepartamentId = departament.Id "
                + "WHERE seller.Id = ?";

        try{
          connection = DB.getConnection();

          statement = connection.prepareStatement(sql);

           statement.setInt(1,id);
          resultSet = statement.executeQuery();
          if(resultSet.next()){
              Departament departament = instantiateDepartment(resultSet);

              Seller obj = instantiateSeller(resultSet,departament);

              return obj;

          }

        } catch (SQLException e) {
            throw new DbException("Aconteceu um erro ao buscar pelo id"+ e.getMessage());
        }
        return null;
    }


    @Override
    public List<Seller> findAll() {
        return List.of();
    }
    private Seller instantiateSeller(ResultSet resultSet, Departament departament) throws SQLException {
        Seller obj = new Seller();
        obj.setId(resultSet.getInt("Id"));
        obj.setName(resultSet.getString("Name"));
        obj.setEmail(resultSet.getString("Email"));
        obj.setBaseSalary(resultSet.getDouble("BaseSalary"));
        obj.setBirthDate(resultSet.getDate("BirthDate"));
        obj.setDepartament(departament);
        return obj;
    }

    private Departament instantiateDepartment(ResultSet resultSet) throws SQLException {
        Departament dep = new Departament();
        dep.setId(resultSet.getInt("DepartmentId"));
        dep.setName(resultSet.getString("DepName"));
        return dep;
    }
}
