package aplication;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {


        SellerDao  sellerDao = DaoFactory.createSellerDao();
        System.out.println();
        Seller seller1  = sellerDao.findById(1);
        System.out.println("a");
        System.out.println(seller1);
        System.out.println();
        Departament departament = new Departament(1,null);
        List<Seller> sellers = sellerDao.findByDepartament(departament);
        sellers.forEach(System.out::println);
        System.out.println("Findyall");
        List<Seller> sellers1 = sellerDao.findAll();
        sellers1.forEach(System.out::println);
    }
}
