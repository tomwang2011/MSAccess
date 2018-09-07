import java.io.*;
import java.util.*;

public class CSVScanner {
	public void scanCSV(String file, String category, int count, String outputName) throws IOException {

		Map<String, String> companyMap = new TreeMap<>();
		Map<String, Map<Double, String>> ordersMap = new TreeMap<>();

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String line = bufferedReader.readLine();

			System.out.println(line);


			while ((line = bufferedReader.readLine()) != null) {

				String[] split = line.split(",");

				if (split[6].equals(".00")) {
					continue;
				}

				if (category != "all") {
					if (!split[12].equals("\"" + category + "\"")) {
						continue;
					}
				}

				companyMap.putIfAbsent(split[1], split[2]);
				Map<Double, String> orderAmountMap = ordersMap.getOrDefault(
					split[1], new TreeMap<>(Collections.reverseOrder()));

				double amount = Double.valueOf(split[6]);
				int n = 1;
				while (orderAmountMap.containsKey(amount)) {
					amount += 0.001*n;
					n++;
				}
				orderAmountMap.put(amount, split[3] + "," + split[12]);
				ordersMap.put(split[1], orderAmountMap);
			}


			BufferedWriter writer = new BufferedWriter(new FileWriter(outputName + ".csv"));

			writer.write("CustCode,productCode,CategoryCode,OrderAmount\n");

			for (Map.Entry<String, Map<Double, String>> entry : ordersMap.entrySet()) {
				Map<Double, String> keymap = entry.getValue();

				int counter = 0;
				for (Map.Entry<Double, String> entry2 : keymap.entrySet()) {
					if (counter == count) {
						break;
					}

					writer.write(entry.getKey() + "," + entry2.getValue() + "," + entry2.getKey());
					writer.write("\n");

					counter++;
				}
			}
		}
	}
}
