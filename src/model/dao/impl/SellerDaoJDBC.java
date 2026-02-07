package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {
    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {
       PreparedStatement statement = null;
       ResultSet resultSet = null;
       String sql = "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartamentId) VALUES (?),(?),(?),(?),(?) ";
       try{
           connection.setAutoCommit(false);
           statement = connection.prepareStatement(sql);
           statement.setString(1,seller.getName());
           statement.setString(2,seller.getEmail());
           statement.setDate(3, (Date) seller.getBirthDate());
           statement.setDouble(4,seller.getBaseSalary());
           statement.setInt(5,seller.getDepartament().getId());
           connection.commit();
       } catch (SQLException e) {
           try{
               connection.rollback();
           } catch (SQLException ex) {
               throw new DbException("Erro ao fazer rollback"+ex.getMessage());
           }
           throw new DbException("Erro ao inserir o dado: "+ e.getMessage());
       }
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
        }finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
        return null;
    }


    @Override
    public List<Seller> findAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "SELECT seller.*, departament.Nome AS DepName "
                + "FROM seller "
                + "INNER JOIN departament "
                + "ON seller.DepartamentId = departament.Id "
                + "ORDER BY seller.Name";


        try{
            connection = DB.getConnection();

            statement = connection.prepareStatement(sql);

            resultSet = statement.executeQuery();
            Map<Integer, Departament> departamentMap = new HashMap<>();
            List<Seller> sellers = new ArrayList<>();
            while (resultSet.next()){
                Departament dep = departamentMap.get(resultSet.getInt("DepartamentId"));
                if (dep == null){
                    dep = instantiateDepartment(resultSet);
                    departamentMap.put(resultSet.getInt("DepartamentID"),dep);
                }

                Seller obj = instantiateSeller(resultSet,dep);
                sellers.add(obj);


            }
            return sellers;

        } catch (SQLException e) {
            throw new DbException("Aconteceu um erro ao buscar pelo id"+ e.getMessage());

        }finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }

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

    private Departament instantiateDepartment(ResultSet rs) throws SQLException {
        Departament dep = new Departament();
        dep.setId(rs.getInt("DepartamentId")); // vem de seller.*
        dep.setName(rs.getString("DepName"));  // alias do SQL
        return dep;
    }

    @Override
    public List<Seller> findByDepartament(Departament departament) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "SELECT seller.*, departament.Nome AS DepName "
                + "FROM seller "
                + "INNER JOIN departament "
                + "ON seller.DepartamentId = departament.Id "
                + "WHERE seller.DepartamentId = ? "
                + "ORDER BY seller.Name";


        try{
            connection = DB.getConnection();

            statement = connection.prepareStatement(sql);

            statement.setInt(1,departament.getId());
            resultSet = statement.executeQuery();
            Map<Integer, Departament> departamentMap = new HashMap<>();
            List<Seller> sellers = new ArrayList<>();
            while (resultSet.next()){
                Departament dep = departamentMap.get(resultSet.getInt("DepartamentId"));
                if (dep == null){
                    dep = instantiateDepartment(resultSet);
                    departamentMap.put(resultSet.getInt("DepartamentID"),dep);
                }

                Seller obj = instantiateSeller(resultSet,dep);
                sellers.add(obj);


            }
            return sellers;

        } catch (SQLException e) {
            throw new DbException("Aconteceu um erro ao buscar pelo id"+ e.getMessage());

        }finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }

    }
}
