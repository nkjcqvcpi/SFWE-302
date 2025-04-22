import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeDTOTest {

    private Connection conn;
    private EmployeeDTO employeeDTO;

    @BeforeAll
    void setup() throws SQLException {
        conn = DriverManager.getConnection("jdbc:derby:memory:testdb;create=true");
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("CREATE TABLE Employee (" +
                "id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "email VARCHAR(255), " +
                "age INT, " +
                "gender VARCHAR(10), " +
                "salary BIGINT)");
        employeeDTO = new EmployeeDTO(conn);
    }

    @AfterEach
    void clearTable() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM Employee");
    }

    @AfterAll
    void teardown() throws SQLException {
        conn.close();
    }

    @Test
    void testSaveInsertAndRetrieve() throws SQLException {
        Employee emp = new Employee(null, "Alice", "alice@example.com", 30, "Female", 50000L);
        employeeDTO.save(emp);
        assertNotNull(emp.getId());
        Employee fetched = employeeDTO.findById(emp.getId());
        assertEquals("Alice", fetched.getName());
    }

    @Test
    void testSaveUpdate() throws SQLException {
        Employee emp = new Employee(null, "Bob", "bob@example.com", 28, "Male", 60000L);
        employeeDTO.save(emp);
        emp.setName("Bobby");
        employeeDTO.save(emp);
        Employee updated = employeeDTO.findById(emp.getId());
        assertEquals("Bobby", updated.getName());
    }

    @Test
    void testFindByIdNotFound() throws SQLException {
        Employee emp = employeeDTO.findById(9999L);
        assertNull(emp);
    }

    @Test
    void testDeleteSuccess() throws SQLException {
        Employee emp = new Employee(null, "Daisy", "daisy@example.com", 26, "Female", 45000L);
        employeeDTO.save(emp);
        employeeDTO.delete(emp.getId());
        assertNull(employeeDTO.findById(emp.getId()));
    }

    @Test
    void testFindAllReturnsAllEmployees() throws SQLException {
        employeeDTO.save(new Employee(null, "Eve", "eve@example.com", 22, "Female", 40000L));
        employeeDTO.save(new Employee(null, "Frank", "frank@example.com", 33, "Male", 75000L));
        List<Employee> all = employeeDTO.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void testCountMatchesNumberOfRecords() throws SQLException {
        assertEquals(0, employeeDTO.count());
        employeeDTO.save(new Employee(null, "Grace", "grace@example.com", 29, "Female", 48000L));
        assertEquals(1, employeeDTO.count());
    }

    @Test
    void testFindWithCondition() throws SQLException {
        employeeDTO.save(new Employee(null, "Henry", "henry@example.com", 40, "Male", 90000L));
        employeeDTO.save(new Employee(null, "Ivy", "ivy@example.com", 23, "Female", 42000L));
        List<Employee> result = employeeDTO.find("salary > 50000");
        assertEquals(1, result.size());
        assertEquals("Henry", result.get(0).getName());
    }

    @Test
    void testFindWithEmptyResult() throws SQLException {
        List<Employee> result = employeeDTO.find("salary > 1000000");
        assertTrue(result.isEmpty());
    }
}
