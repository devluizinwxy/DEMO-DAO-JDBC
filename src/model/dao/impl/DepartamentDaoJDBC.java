package model.dao.impl;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.DepartamentDao;
import model.entities.Departament;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DepartamentDaoJDBC implements DepartamentDao {
    private Connection connection;

    public DepartamentDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Departament departament) {
        PreparedStatement statement = null;
        String sql="INSERT INTO departament "
                + "(Nome)"
                + "VALUES (?)";
        try{
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1 ,departament.getNome());
            int quantidade = statement.executeUpdate();
            if (quantidade > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()){
                    int id = resultSet.getInt(1);
                    departament.setId(id);
                }
                DB.closeResultSet(resultSet);
            } else {
                throw new DbException("Nenhuma linha foi criada: ");
            }
        } catch (SQLException e) {
            throw new DbException("Erro ao inserir: "+ e.getMessage());
        }
    }

    @Override
    public void update(Departament departament) {
        PreparedStatement statement = null;
        String sql="UPDATE departament "
                + "SET Nome = ?"
                + "WHERE Id = ?";

        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1 ,departament.getNome());
            statement.setInt(2, departament.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
        throw new DbException("Erro ao inserir: "+ e.getMessage());
    }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement statement = null;
        String sql = "DELETE FROM departament " +
                "WHERE Id = ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DbIntegrityException("Erro ao apagar: " + e.getMessage());
        }
    }

    @Override
    public List<Departament> findAll() {
        String sql = "SELECT departament.* FROM departament ORDER BY departament.Nome";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            List<Departament> departaments = new ArrayList<>();
            while (resultSet.next()) {
                departaments.add(instantiateDepartment(resultSet));
            }
            return departaments;

        } catch (SQLException e) {
            throw new DbException("Erro ao buscar todos os departamentos: " + e.getMessage());
        }
    }


    @Override
    public Departament findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "SELECT departament.*"
                + "FROM departament "
                + "WHERE departament.Id = ?";
        try{
            statement = connection.prepareStatement(sql);
            statement.setInt(1,id);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                Departament departament = instantiateDepartment(resultSet);
                return departament;

            }
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar por id" + e.getMessage());
        }
        return null;
    }
    private Departament instantiateDepartment(ResultSet resultSet) throws SQLException {

        int Id = resultSet.getInt("Id");
        String nome = resultSet.getNString("Nome");
        Departament dep = new Departament(Id,nome);
        return dep;
    }

}
