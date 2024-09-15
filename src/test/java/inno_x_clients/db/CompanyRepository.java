package inno_x_clients.db;

import inno_x_clients.x_clients.model.CompanyDB;
import java.sql.SQLException;

public interface CompanyRepository {

    int createCompany(String name, String descr) throws SQLException;

    CompanyDB selectById(int companyId) throws SQLException;
}
