package aplication;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

import java.time.LocalDate;
import java.util.Date;

public class Program {
    public static void main(String[] args) {
        Departament departament = new Departament(20, "Departamento");
        Seller seller = new Seller(21,"Luis","Luis@gmail.com", LocalDate.now(), 1000.2,departament);
        System.out.println(departament);
        System.out.println();
        System.out.println(seller);
        SellerDao  sellerDao = DaoFactory.createSellerDao();
   
    }
}
