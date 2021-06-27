package sample;
import java.sql.*;
public class DatabaseConnection
{
    public Connection dblink;
    public Connection getConnection()throws Exception
    {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            dblink = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:","C##pratham","p3pratham");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return dblink;
        }
    }
}
