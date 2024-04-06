package carsharing;

import java.util.List;

public interface CompanyDao {
    List<Company> getCompanies();

    void addCompany(String company);

    List<String> getCars(int id);

    void addCAr(String CAR_NAME, int id);
}