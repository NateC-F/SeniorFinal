package com.example.seniorfinal.Model.DAO;

import com.example.seniorfinal.Core.Account;
import com.example.seniorfinal.Core.UserAccount;
import com.example.seniorfinal.Core.UserSession;
import com.example.seniorfinal.Utilities.JDBC;
import com.example.seniorfinal.Utilities.PasswordHash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class AccountDAO
{
    private static String name;
    private static int id;
    private String sqlCode;
    private PreparedStatement statement;
    private static Account activeAccount;
    private static Account listingAccount;

    public AccountDAO(){
        activeAccount = new UserAccount();
    }
//=============================================================================================================
    public boolean createAccount(String accountName, String accountPass)
    {
        sqlCode = "INSERT INTO user_profile (user_name, user_pass) VALUES (?,?)";
        String hashedPass = PasswordHash.hashString(accountPass);

        try(Connection connection = JDBC.getConnection())
        {
            statement = connection.prepareStatement(sqlCode);
            statement.setString(1,accountName);
            statement.setString(2,hashedPass);
            int rows = statement.executeUpdate();
            return rows > 0;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
    }
//=============================================================================================================
    public String login(String accountName, String pass, boolean adminLogin)
    {
        if (!adminLogin)
            sqlCode = "SELECT * FROM user_profile WHERE user_name = ?";
        else
            sqlCode = "SELECT * FROM user_profile WHERE user_name = ? AND user_role = 'ADMIN'";

        if (accountName.isEmpty())
            return "NULL NAME";
        if (pass.isEmpty())
            return "NULL PASS";
        try (Connection connection = JDBC.getConnection())
            {
                statement = connection.prepareStatement(sqlCode);
                statement.setString(1, accountName);

                ResultSet resultSet = statement.executeQuery();

                if (!resultSet.next())
                    return "USERNAME ERROR";
                String hashedPass = resultSet.getString("user_pass");

                if (!PasswordHash.verifyHash(pass, hashedPass))
                    return "PASSWORD ERROR";

                activeAccount.setAccountID(resultSet.getInt("user_id"));
                activeAccount.setAccountName(resultSet.getString("user_name"));
                UserSession.getSession().setActiveUser(activeAccount);
                return "SUCCESS";

            } catch (Exception e)
            {
                System.out.println(e);
                return "OTHER ERROR";
            }
    }
//=============================================================================================================
    public ArrayList<Account> getUserAccounts()
    {
        sqlCode = "Select * From user_profile WHERE user_role = 'USER'";
        ArrayList<Account> accountList = new ArrayList<>();

        try(Connection connection = JDBC.getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sqlCode);
            ResultSet rs = statement.executeQuery();

            while(rs.next())
            {
                int userId = rs.getInt("user_id");
                String userName = rs.getString("user_name");
                LocalDate createDate = rs.getDate("create_date").toLocalDate();

                UserAccount tempAccount = new UserAccount();
                tempAccount.setAccountName(userName);
                tempAccount.setAccountID(userId);
                tempAccount.setCreationDate(createDate);

                accountList.add(tempAccount);

            }
        }
        catch (Exception e)
        {

        }

        return accountList;
    }

}
