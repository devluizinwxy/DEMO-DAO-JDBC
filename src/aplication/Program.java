package aplication;

import model.dao.DaoFactory;
import model.dao.DepartamentDao;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {


        DepartamentDao departamentDao = DaoFactory.createDepartamentDao();
       List<Departament> departaments= departamentDao.findAll();
 departaments.forEach(System.out::println);
    }
}
