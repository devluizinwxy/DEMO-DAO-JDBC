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


        sellerDao.deleteById(1);
        Seller seller2  = sellerDao.findById(1);
        System.out.println(seller2);

    }
}
