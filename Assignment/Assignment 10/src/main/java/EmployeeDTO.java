import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDTO {

    static {
        System.setProperty("derby.language.sequence.preallocator", "1");
    }

    private final Connection connection;

    public EmployeeDTO(Connection connection) {
        this.connection = connection;
    }

    public void save(Employee emp) throws SQLException {
        if (emp.getId() != null) {
            String update = "UPDATE Employee SET name=?, email=?, age=?, gender=?, salary=? WHERE id=?";
            try (PreparedStatement ps = connection.prepareStatement(update)) {
                ps.setString(1, emp.getName());
                ps.setString(2, emp.getEmail());
                ps.setInt(3, emp.getAge());
                ps.setString(4, emp.getGender());
                ps.setLong(5, emp.getSalary());
                ps.setLong(6, emp.getId());
                ps.executeUpdate();
            }
        } else {
            String insert = "INSERT INTO Employee (name, email, age, gender, salary) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(insert)) {
                ps.setString(1, emp.getName());
                ps.setString(2, emp.getEmail());
                ps.setInt(3, emp.getAge());
                ps.setString(4, emp.getGender());
                ps.setLong(5, emp.getSalary());
                ps.executeUpdate();

                try (Statement lastIdStmt = connection.createStatement()) {
                    lastIdStmt.setMaxRows(1);
                    ResultSet rs = lastIdStmt.executeQuery("SELECT id FROM Employee ORDER BY id DESC");
                    if (rs.next()) {
                        emp.setId(rs.getLong(1));
                    }
                }
            }
        }
    }

    public Employee findById(Long id) throws SQLException {
        String query = "SELECT * FROM Employee WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
            return null;
        }
    }

    public void delete(Long id) throws SQLException {
        String delete = "DELETE FROM Employee WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(delete)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public List<Employee> findAll() throws SQLException {
        String query = "SELECT * FROM Employee";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            List<Employee> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        }
    }

    public long count() throws SQLException {
        String query = "SELECT COUNT(*) FROM Employee";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.next() ? rs.getLong(1) : 0;
        }
    }

    public List<Employee> find(String condition) throws SQLException {
        String query = "SELECT * FROM Employee WHERE " + condition;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            List<Employee> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        }
    }

    private Employee mapRow(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("age"),
                rs.getString("gender"),
                rs.getLong("salary")
        );
    }
}
