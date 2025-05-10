import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class meuTeste {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // carrega a classe org.postgresql.Driver na memória
        Class.forName("org.postgresql.Driver");
        // abre uma conexão JDBC com o servidor local
        Connection conexãoJDBC = DriverManager.getConnection(
                "jdbc:postgresql://localhost/livraria",
                "postgres",
                "4213"
        );

        // (READ) busca e mostra todos os registros da tabela de clientes
        Statement SQLstatement = conexãoJDBC.createStatement();
        ResultSet rs = SQLstatement.executeQuery("SELECT * FROM clientes");
        System.out.println("Listando todos os clientes... ");
        while (rs.next()) {
            System.out.println(rs.getInt("codigo") + " - " + rs.getString("nome"));
        }

        // (DELETE) apaga todos os clientes
        System.out.println("Apagando todos os clientes...");
        SQLstatement.executeUpdate("DELETE FROM clientes");

        // lista todos os clientes
        System.out.println("Listando todos os clientes novamente...");
        rs = SQLstatement.executeQuery("SELECT * FROM clientes");
        while (rs.next()) {
            System.out.println(rs.getInt("codigo") + " - " + rs.getString("nome"));
        }

        // (CREATE) insere novos clientes na tabela
        System.out.println("Inserindo novos clientes...");
        PreparedStatement SQLstatementSeguro = conexãoJDBC.prepareStatement("INSERT INTO clientes (codigo, nome) VALUES (?, ?)");
        SQLstatementSeguro.setInt(1, 1);
        SQLstatementSeguro.setString(2, "Fulano");
        SQLstatementSeguro.execute();
        SQLstatementSeguro.setInt(1, 2);
        SQLstatementSeguro.setString(2, "Beltrano");
        SQLstatementSeguro.execute();
        SQLstatementSeguro.setInt(1, 3);
        SQLstatementSeguro.setString(2, "Ciclano");
        SQLstatementSeguro.execute();

        // lista os novos clientes inserido
        System.out.println("Listando todos os clientes novamente...");
        rs = SQLstatement.executeQuery("SELECT * FROM clientes");
        while (rs.next()) {
            System.out.println(rs.getInt("codigo") + " - " + rs.getString("nome"));
        }

        // (UPDATE) altera os dados de um dos clientes
        System.out.println("Alterando o cliente 1...");
        SQLstatementSeguro = conexãoJDBC.prepareStatement("UPDATE clientes SET nome = ? WHERE codigo = 1");
        SQLstatementSeguro.setString(1, "Fulano de Tal");
        SQLstatementSeguro.execute();

        // lista os clientes com os dados alterados
        System.out.println("Listando todos os clientes novamente...");
        rs = SQLstatement.executeQuery("SELECT * FROM clientes");
        while (rs.next())
            System.out.println(rs.getInt("codigo") + " - " + rs.getString("nome"));

        rs.close();
        SQLstatementSeguro.close();
        conexãoJDBC.close();
    }
}
