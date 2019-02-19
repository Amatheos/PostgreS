package PostgreS.repositories.test;

import PostgreS.dtos.DTOBase;
import PostgreS.exceptions.MyCustomException;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import oracle.jdbc.pool.OraclePooledConnection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;

import PostgreS.repositories.IRepository;
import org.junit.BeforeClass;

import javax.sql.PooledConnection;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class RepositoryTestBase<TDTO extends DTOBase, TRepository extends IRepository<TDTO>> {

	protected TRepository _repository;
	protected static PooledConnection pooledConnection;

	@Before
	public void before() {
		_repository = Create();
		if (_repository != null) {
			_repository.beginTransaction();
		}
	}

	@After
	public void after() {
		if (_repository != null) {
			_repository.rollbackTransaction();
		}
	}

    @BeforeClass
    public static void createPooledConnection() {
        try {
            OracleConnectionPoolDataSource poolDataSource = new OracleConnectionPoolDataSource();
            poolDataSource.setURL("jdbc:oracle:thin:@localhost:1521/XE");
            poolDataSource.setUser("homeuser");
            poolDataSource.setPassword("123");
            pooledConnection = poolDataSource.getPooledConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @AfterClass
    public static void closePooledConnection() {
        try {
            pooledConnection.close();
        } catch (SQLException e) {
            throw new MyCustomException(e);
        }
    }

    public Connection getRepoConnection() {
        Class<? extends IRepository> _repoClass = _repository.getClass();
        try {
            Field connectionField = _repoClass.getSuperclass().getDeclaredField("connection");
            connectionField.setAccessible(true);
            return  (Connection) connectionField.get(_repository);
        } catch (Exception e) {
            throw new MyCustomException(e);
        }
    }

    protected abstract TRepository Create();
}