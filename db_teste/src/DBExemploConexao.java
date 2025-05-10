// Essa classe representa uma conexão ativa com o banco de dados, e a partir dela você pode criar Statement,
// PreparedStatement e CallableStatement para executar comandos SQL. Tem como principais funções abrir e fechar
// conexões com o banco, Executar transções (commit, rollback) e criar comandos SQL
import java.sql.Connection;
// responsável por gerenciar um conjunto de drivers JDBC. Ela oferece métodos para registrar e remover drivers,
// estabelecer conexões e configurar parâmetros de logging e tempo limite.
import java.sql.DriverManager;
// é usada para executar instruções SQL estáticas (como SELECT, INSERT, UPDATE ou DELETE)
import java.sql.Statement;
// é uma forma mais segura e eficiente de executar comandos SQL em Java (previne SQL injection)
import java.sql.PreparedStatement;
// é utilizada para armazenar e manipular os dados retornados por uma consulta SQL. Ela atua como um cursor que aponta
// para o conjunto de resultados, permitindo iterar sobre as linhas retornadas e acessar os valores de cada coluna.
// Além disso, oferece métodos para atualizar os dados no banco de dados diretamente a partir do conjunto de resultados.
import java.sql.ResultSet;
// usado para fazer catch de exceções SQL
import java.sql.SQLException;


public class DBExemploConexao {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // carrega a class org.postgresql.Driver na memória
        Class.forName("org.postgresql.Driver");
        // abre uma conexão JDBC com o servidor local
        Connection c = DriverManager.getConnection(
                "jdbc:postgresql://localhost/livraria",
                "postgres",
                "4213");

        // busca e mostra todos os registros da tabela clientes
        Statement s = c.createStatement();
        System.out.println("Listando todos os clientes...");
        ResultSet rs = s.executeQuery("SELECT * FROM clientes");
        while (rs.next())
            System.out.println(rs.getInt("codigo") + " - " + rs.getString("nome"));

        // Apaga todos os clientes
        System.out.println("Apagando todos os clientes...");
        s.executeUpdate("DELETE FROM clientes");

        // Lista todos os clientes (não tem ninguém para listar)
        System.out.println("Listando todos os clientes novamente...");
        rs = s.executeQuery("SELECT * FROM clientes");
        while (rs.next())
            System.out.println(rs.getInt("codigo") + " - " + rs.getString("nome"));

        // Insere novos clientes na tabela
        System.out.println("Inserindo clientes ...");
        PreparedStatement p = c.prepareStatement("INSERT INTO clientes (codigo, nome) VALUES (?, ?)");
        p.setInt(1, 1);
        p.setString(2, "Fulano");
        p.execute();
        p.setInt(1, 2);
        p.setString(2, "Beltrano");
        p.execute();
        p.setInt(1, 3);
        p.setString(2, "Ciclano");
        p.execute();

        // lista os novos clientes inseridos
        System.out.println("Listando todos os clientes novamente...");
        rs = s.executeQuery("SELECT * FROM clientes");
        while (rs.next())
            System.out.println(rs.getInt("codigo") + " - " + rs.getString("nome"));

        // altera dados dos clientes
        System.out.println("Alterando o cliente 1...");
        p = c.prepareStatement(
                "UPDATE clientes SET nome = ? WHERE codigo = 1");
        p.setString(1, "Fulano de Tal");
        p.execute();

        // lista os clientes com os dados alterados
        System.out.println("Listando todos os clientes novamente...");
        rs = s.executeQuery("SELECT * FROM clientes");
        while (rs.next())
            System.out.println(rs.getInt("codigo") + " - " + rs.getString("nome"));

        rs.close();
        p.close();
        c.close();
    }
}
