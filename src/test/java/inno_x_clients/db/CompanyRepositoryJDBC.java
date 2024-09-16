package inno_x_clients.db;

import inno_x_clients.x_clients.model.CompanyDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CompanyRepositoryJDBC implements CompanyRepository {

    private final Connection connection;

    public CompanyRepositoryJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int createCompany(String name, String descr) throws SQLException {
        String SQL = "insert into company(\"name\", description) values (?, ?)";
        PreparedStatement statement = connection.prepareStatement(SQL,
            Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, name);
        statement.setString(2, descr);
        statement.executeUpdate();

        ResultSet set = statement.getGeneratedKeys();
        set.next();
        return set.getInt(1);
    }

    @Override
    public CompanyDB selectById(int companyId) throws SQLException {
        String SQL = "SELECT * FROM company WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(SQL);
        statement.setInt(1, companyId);
        ResultSet result = statement.executeQuery();
        result.next();

        return new CompanyDB(
            result.getInt("id"),
            result.getString("name"),
            result.getString("description"),
            result.getBoolean("is_active")
        );
    }
}
