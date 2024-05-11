public class ExampleInsertPackage {
    public static void main(String[] args) {
        try {
            // Connect to VoltDB database
            VoltDatabase db = new VoltDatabase("ServerIP, put the actual server ip here");

            // Example usage: Insert data into PACKAGE table
            db.insertPackage(1, "Package A", 10.99, 600, 1024, 100, 30);

            // Close the database connection
            db.close();
            
            System.out.println("Package inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}