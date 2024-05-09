package org.example.dataAccess;

import org.example.connection.ConnectionFactory;
import org.example.model.Bill;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    public void insertBill(Bill bill) {
        String sql = "INSERT INTO Log (idClient, idProdus, Cantitate, PretTotal) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, bill.idClient());
            statement.setInt(2, bill.idProdus());
            statement.setInt(3, bill.cantitate());
            statement.setInt(4, bill.pretTotal());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Bill> readBills() {
        String sql = "SELECT id, idClient, idProdus, Cantitate, PretTotal FROM Log";
        Connection connection = null;
        PreparedStatement statement = null;
        List<Bill> bills = new ArrayList<>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                int idClient = resultSet.getInt("idClient");
                int idProdus = resultSet.getInt("idProdus");
                int cantitate = resultSet.getInt("Cantitate");
                int pretTotal = resultSet.getInt("PretTotal");

                Bill bill = new Bill(idClient, idProdus, cantitate, pretTotal);
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bills;
    }
}
