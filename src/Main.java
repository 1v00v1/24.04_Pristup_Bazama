import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = createDataSource();

        try(Connection connection = dataSource.getConnection() ) {
            System.out.println("Uspješno ste spojeni na bazu podataka");

            Statement st = connection.createStatement();
            int rowAffected = st.executeUpdate("UPDATE Drzava SET Naziv ='Hrvatska' WHERE IDDrzava=1");
            System.out.println("Drzava je uspješno preimenovana");
            st.execute("INSERT INTO  Drzava VALUES ('test')") ;
            st.execute("SELECT * FROM Drzava WHERE Naziv ='test'");

            ResultSet rs = st.executeQuery("SELECT IDDrzava,Naziv FROM Drzava");
            while (rs.next()){
                System.out.printf("%d %s\n",rs.getInt("IDDrzava"), rs.getString("Naziv"));
            }

            rowAffected = st.executeUpdate("DELETE  FROM Drzava WHERE Naziv = 'test'");

            rs = st.executeQuery("SELECT IDDrzava,Naziv FROM Drzava");
            while (rs.next()){
                System.out.printf("%d %s\n",rs.getInt("IDDrzava"), rs.getString("Naziv"));
            }
            rs.close();
            st.close();
        }catch (SQLException e){
            System.err.println("Greška pri spajanju u bazu podataka");
            e.printStackTrace();
        }



    }

    private static DataSource createDataSource(){

        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("localhost");
        ds.setPortNumber(1433);
        ds.setDatabaseName("AdventureWorksOBP");
        ds.setUser("sa");
        ds.setPassword("SQL");
        ds.setEncrypt(false);

        return ds;
    }
}
