package aplication;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

import java.time.LocalDate;
import java.util.Date;

public class Program {
    public static void main(String[] args) {


        SellerDao  sellerDao = DaoFactory.createSellerDao();
        Seller seller1  = sellerDao.findById(1);
        System.out.println(seller1);
    }
}
