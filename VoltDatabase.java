import org.voltdb.*;
import org.voltdb.client.*;

public class VoltDatabase {
    // VoltDB client instance
    private Client client;

    // Constructor
    public VoltDatabase(String servers) throws Exception {
        client = ClientFactory.createClient();
        client.createConnection(servers);
    }

    // Method to get next package id
    public int getNextPackageId() throws Exception {
        VoltTable response = client.callProcedure("@AdHoc","SELECT PACKAGE_ID FROM PACKAGE ORDER BY PACKAGE_ID DESC LIMIT 1;").getResults()[0];
        if(response.advanceRow())
        return (int) response.getLong("PACKAGE_ID") + 1;
        
        throw new Exception("error while getting next package id");
    }
    
    // Method to get next customer id
    public int getNextCustomerId() throws Exception {
        VoltTable response = client.callProcedure("@AdHoc","SELECT CUST_ID FROM CUSTOMER ORDER BY CUST_ID DESC LIMIT 1;").getResults()[0];
        if(response.advanceRow())
            return (int) response.getLong("CUST_ID") + 1;
            
            throw new Exception("error while getting next customer id");
        }
        
    // Method to get next balance id
    public int getNextBalanceId() throws Exception {

        VoltTable response = client.callProcedure("@AdHoc","SELECT BALANCE_ID FROM BALANCE ORDER BY BALANCE_ID DESC LIMIT 1;").getResults()[0];
        if(response.advanceRow())
            return (int) response.getLong("BALANCE_ID") + 1;

        throw new Exception("error while getting next balance id");
    }

    // Method to insert data into PACKAGE table
    public void insertPackage(String packageName, double price, int minutes, int data, int sms, int period) throws Exception {
        int packageId = getNextPackageId();
        client.callProcedure("@AdHoc", "INSERT INTO PACKAGE VALUES (?, ?, ?, ?, ?, ?);", packageId, packageName, price, minutes, data, sms, period);
    }

    // Method to insert data into CUSTOMER table
    public void insertCustomer(String msisdn, String name, String surname, String email, String password, String status, String securityKey) throws Exception {
        int custId = getNextCustomerId();
        client.callProcedure("@AdHoc", "INSERT INTO CUSTOMER VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, ?);", custId, msisdn, name, surname, email, password, status, securityKey);
    }

    // Method to insert data into BALANCE table
    public void insertBalance(int packageId, int custId, int partitionId, int minutes, int sms, int data, double price, double moneyBalance) throws Exception {
        int balanceId = getNextBalanceId();
        client.callProcedure("@AdHoc", "INSERT INTO BALANCE VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, NULL, ?, ?);", balanceId, packageId, custId, partitionId, minutes, sms, data, price, moneyBalance);
    }

    // Method to select all rows from PACKAGE table
    public VoltTable selectAllPackages() throws Exception {
        return client.callProcedure("@AdHoc", "SELECT * FROM PACKAGE;").getResults()[0];
    }

    // Method to select all rows from CUSTOMER table
    public VoltTable selectAllCustomers() throws Exception {
        return client.callProcedure("@AdHoc", "SELECT * FROM CUSTOMER;").getResults()[0];
    }

    // Method to select all rows from BALANCE table
    public VoltTable selectAllBalances() throws Exception {
        return client.callProcedure("@AdHoc", "SELECT * FROM BALANCE;").getResults()[0];
    }

    // Method to select the balance of a customer by MSISDN
    public VoltTable selectBalanceByMSISDN(String msisdn) throws Exception {
        return client.callProcedure("@AdHoc", "SELECT b.* FROM BALANCE b JOIN CUSTOMER c ON b.CUST_ID = c.CUST_ID WHERE c.MSISDN = ?;", msisdn).getResults()[0];
    }

    public VoltTable selectCustomerByMSISDN(String msisdn) throws Exception {
        return client.callProcedure("@AdHoc", "SELECT * FROM CUSTOMER WHERE MSISDN = ?;", msisdn).getResults()[0];
    }

    // Close the VoltDB client connection
    public void close() throws Exception {
        client.close();
    }
}