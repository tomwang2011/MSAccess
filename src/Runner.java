import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Runner {
	public static void main(String[] args) throws IOException {
		Properties properties = new Properties();

		properties.load(new FileInputStream("Query.properties"));

		readCSV(
			properties.getProperty("csvFile"), properties.getProperty("categoryCode"),
			Integer.valueOf(properties.getProperty("outputCount")), properties.getProperty("outputName"));
	}

	private static void readCSV(String file, String category, int count, String outputName) throws IOException {
		CSVScanner csvScanner = new CSVScanner();

		csvScanner.scanCSV(file, category, count, outputName);
	}

	private static void connectMS() throws IOException {
		MSAccessConnect msAccessConnect = new MSAccessConnect();

		Properties properties = new Properties();

		properties.load(new FileInputStream("Query.properties"));

		msAccessConnect.connectDBAll(
			properties.getProperty("pathToDB"), properties.getProperty("databaseName"), properties.getProperty("table"), "all",
			properties.getProperty("sumCategory"), 50);

		msAccessConnect.connectDBAll(
			properties.getProperty("pathToDB"), properties.getProperty("databaseName"), properties.getProperty("table"), "A",
			properties.getProperty("sumCategory"), 25);

		msAccessConnect.connectDBAll(
			properties.getProperty("pathToDB"), properties.getProperty("databaseName"), properties.getProperty("table"), "B",
			properties.getProperty("sumCategory"), 25);

		msAccessConnect.connectDBAll(
			properties.getProperty("pathToDB"), properties.getProperty("databaseName"), properties.getProperty("table"), "G",
			properties.getProperty("sumCategory"), 25);

		msAccessConnect.connectDBAll(
			properties.getProperty("pathToDB"), properties.getProperty("databaseName"), properties.getProperty("table"), "H",
			properties.getProperty("sumCategory"), 25);

		msAccessConnect.connectDBAll(
			properties.getProperty("pathToDB"), properties.getProperty("databaseName"), properties.getProperty("table"), "K",
			properties.getProperty("sumCategory"), 25);

		msAccessConnect.connectDBAll(
			properties.getProperty("pathToDB"), properties.getProperty("databaseName"), properties.getProperty("table"), "L",
			properties.getProperty("sumCategory"), 25);

		msAccessConnect.connectDBAll(
			properties.getProperty("pathToDB"), properties.getProperty("databaseName"), properties.getProperty("table"), "O",
			properties.getProperty("sumCategory"), 25);

		msAccessConnect.connectDBAll(
			properties.getProperty("pathToDB"), properties.getProperty("databaseName"), properties.getProperty("table"), "S",
			properties.getProperty("sumCategory"), 25);

		msAccessConnect.connectDBAll(
			properties.getProperty("pathToDB"), properties.getProperty("databaseName"), properties.getProperty("table"), "W",
			properties.getProperty("sumCategory"), 25);

		msAccessConnect.connectDBAll(
			properties.getProperty("pathToDB"), properties.getProperty("databaseName"), properties.getProperty("table"), "X",
			properties.getProperty("sumCategory"), 25);
	}
}
