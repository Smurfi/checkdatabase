/*
 * Copyright (C) 2019 mkroll
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package checkdatabase.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Oracle connection class
 *
 * @author mkroll
 * @version 20160629
 */
public class OracleConnect {

    private static final String dbDriver = "oracle.jdbc.driver.OracleDriver";
    private static String dbUrl = "";
    private static String dbUser = "";
    private static String dbPwd = "";
    private static Connection dbConnection = null;
    private static Statement dbStatementRS = null;
    private static String sSqlQuery = null;
    private static ResultSet rs = null;

    /**
     * Make database connection
     *
     * @param _host
     * @param _port
     * @param _sid
     * @param _username
     * @param _password
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static void connectDB(String _host, String _port, String _sid, String _username, String _password) throws ClassNotFoundException, SQLException {
        dbUser = _username;
        dbPwd = _password;
        dbUrl = "jdbc:oracle:thin:@" + _host + ":" + _port + ":" + _sid;

        // Datenbankverbindung aufbauen

            Class.forName(dbDriver);
            dbConnection = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
    }

    /**
     * Close database connection
     */
    public static void closeDB() {
        // Datenbankverbindung aufbauen
        try {
            dbConnection.close();
        } catch (SQLException ex) {
            Logger.getLogger(OracleConnect.class.getName()).log(Level.WARNING, "connectDB.connectDB: {0}", ex);
        }
    }

    /**
     * Check database connection
     *
     * @return String
     * @throws java.sql.SQLException
     */
    public static String checkConnection() throws SQLException {
        boolean reachable;
            reachable = dbConnection.isValid(10); // 10 sec
        if (reachable) {
            return "Oracle Database connected!!!";
        } else {
            return "Can't connect to Database!!!";
        }
    }

    /**
     * Database connection
     *
     * @return Connection
     */
    public static Connection getConnection() {
        return dbConnection;
    }

    /**
     * ResultSet
     *
     * @param m_sSqlQuery Sql-Query String
     * @return ResultSet
     */
    public static ResultSet getResultSet(String m_sSqlQuery) {
        sSqlQuery = m_sSqlQuery;
        try {
            dbStatementRS = dbConnection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = dbStatementRS.executeQuery(sSqlQuery);
            rs.first();
        } catch (SQLException ex) {
            Logger.getLogger(OracleConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    /**
     * Database statement
     *
     * @return Statement
     */
    public static Statement getStatement() {
        return dbStatementRS;
    }
}
