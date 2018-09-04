import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class MSAccessConnect {
	public void connectDB(String pathToDB, String dbName, String table, String categoryCode, String sumCategory) throws IOException {

		if (dbName == null || table == null || categoryCode == null || sumCategory == null) {
			throw new IOException("One of the properties required is not set");
		}

		// variables
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		// Step 1: Loading or registering Oracle JDBC driver class
		try {

			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		}
		catch(ClassNotFoundException cnfex) {

			System.out.println("Problem in loading or "
				+ "registering MS Access JDBC driver");
			cnfex.printStackTrace();
		}

		// Step 2: Opening database connection
		try {

			String msAccDB = pathToDB + "/" + dbName;
			String dbURL = "jdbc:ucanaccess://" + msAccDB;

			// Step 2.A: Create and get connection using DriverManager class
			connection = DriverManager.getConnection(dbURL);

			// Step 2.B: Creating JDBC Statement
			statement = connection.createStatement();

			// Step 2.C: Executing SQL & retrieve data into ResultSet
//			resultSet = statement.executeQuery(
//				"SELECT TOP 10 productCode FROM a1 WHERE CategoryCode = 'A' AND CustCode = '1H2999' ORDER BY SumOfYear1_OrderAmt DESC");

//			resultSet = statement.executeQuery(
//				"SELECT CustCode, productCode, " + sumCategory +" FROM " + table + " WHERE CategoryCode = '" + categoryCode + "' AND " + sumCategory + " != 0 ORDER BY " + sumCategory + " DESC");

			resultSet = statement.executeQuery(
				"SELECT CustCode, productCode,CategoryCode, " + sumCategory +" FROM " + table + " WHERE " + sumCategory + " != 0 ORDER BY " + sumCategory + " DESC");

//			resultSet = statement.executeQuery(
//				"SELECT TOP 10 productCode, SumOfYear1_OrderAmt FROM " + table + " WHERE CategoryCode = '" + categoryCode + "' AND CustCode = '" + custCode + "' ORDER BY SumOfYear1_OrderAmt DESC");
				// processing returned data and printing into console

			Map<String, Map<Double, String>> map = new TreeMap<>();

			while(resultSet.next()) {
				String key = resultSet.getString(1);
				Map<Double, String> list = map.getOrDefault(key, new TreeMap<>(Collections.reverseOrder()));

				if (list.containsKey(resultSet.getDouble(4))) {
					list.put(resultSet.getDouble(4)+0.0001, resultSet.getString(2) + "," + resultSet.getString(3));
				}
				else {
					list.put(resultSet.getDouble(4), resultSet.getString(2) + "," + resultSet.getString(3));
				}

				map.put(key, list);
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter(table + "-" + categoryCode +".csv"));

			writer.write("CustCode,productCode,CategoryCode," + sumCategory + "\n");

			for (Map.Entry<String, Map<Double, String>> entry : map.entrySet()) {
				Map<Double, String> keymap = entry.getValue();

				int count = 0;
				for (Map.Entry<Double, String> entry2 : keymap.entrySet()) {
					if (count == 10) {
						break;
					}

					writer.write(entry.getKey() + "," + entry2.getValue() + "," + entry2.getKey());
					writer.write("\n");

					count++;
				}
			}
		}
		catch(SQLException sqlex){
			sqlex.printStackTrace();
		}
		finally {
			// Step 3: Closing database connection
			try {
				if(null != connection) {

					// cleanup resources, once after processing
					resultSet.close();
					statement.close();

					// and then finally close connection
					connection.close();
				}
			}
			catch (SQLException sqlex) {
				sqlex.printStackTrace();
			}
		}
	}
}
