import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Runner {
	public static void main(String[] args) throws IOException {
		MSAccessConnect msAccessConnect = new MSAccessConnect();

		Properties properties = new Properties();

		properties.load(new FileInputStream("Query.properties"));

		msAccessConnect.connectDB(
			properties.getProperty("pathToDB"), properties.getProperty("databaseName"), properties.getProperty("table"), properties.getProperty("categoryCode"),
			properties.getProperty("sumCategory"));
	}

	private static void readCSV() {

	}
}
