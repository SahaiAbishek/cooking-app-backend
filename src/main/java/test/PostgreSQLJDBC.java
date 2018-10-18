package test;
import java.sql.DriverManager;

public class PostgreSQLJDBC {
   public static void main(String args[]) {
//      Connection c = null;
      try {
         Class.forName("org.postgresql.Driver");
         DriverManager.getConnection("jdbc:postgresql://10.0.0.47:5432/cooking","abhi", "Abhi2207");
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         System.exit(0);
      }
      System.out.println("Opened database successfully");
   }
}