package carsharing;

import java.util.List;

public interface CompanyDao {
    List<Company> get();
    void add(String company);
}