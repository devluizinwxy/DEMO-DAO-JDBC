package model.dao;

import model.entities.Departament;

import java.util.List;

public interface DepartamentDao {
    void insert (Departament departament);
    void update (Departament departament);
    void deleteById (Integer id);
    Departament findById (Integer id);
    List<Departament> findAll();

}
