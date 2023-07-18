package assignmet6;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Application {

    public static final String MODEL_3 = "model3.csv";
    public static final String MODEL_S = "modelS.csv";
    public static final String MODEL_X = "modelX.csv";

    public static void main(String[] args) throws IOException {
        FileService fs = new FileService();
        List<SalesData> model3SalesData = fs.loadSalesData(MODEL_3);
        List<SalesData> modelSSalesData = fs.loadSalesData(MODEL_S);
        List<SalesData> modelXSalesData = fs.loadSalesData(MODEL_X);

        generateSalesReport(model3SalesData, "Model 3");
        generateSalesReport(modelSSalesData, "Model S");
        generateSalesReport(modelXSalesData, "Model X");
    }

    private static void generateSalesReport(List<SalesData> carSalesData, String modelType) {
        System.out.println(modelType + " Yearly Sales Report");
        System.out.println("---------------------------");

        // Group sales data by year
        Map<Integer, List<SalesData>> salesGroupedByYear = carSalesData.stream()
                .collect(Collectors.groupingBy(d -> d.getDate().getYear()));

        // Calculate and print total yearly sales
        String totalYearlySales = salesGroupedByYear.entrySet().stream()
                .map(entry -> entry.getKey() + " -> " + entry.getValue().stream()
                        .collect(Collectors.summingInt(SalesData::getSales)))
                .collect(Collectors.joining("\n"));
        System.out.println(totalYearlySales + "\n");

        // Find the best and worst months for sales
        Optional<SalesData> maxSalesData = carSalesData.stream()
                .max((o1, o2) -> o1.getSales().compareTo(o2.getSales()));
        Optional<SalesData> minSalesData = carSalesData.stream()
                .min((o1, o2) -> o1.getSales().compareTo(o2.getSales()));

        System.out.println("The best month for " + modelType + " was: \n"
                + maxSalesData.orElse(new SalesData("Jan-00", "00")).getDate());
        System.out.println("The worst month for " + modelType + " was: \n"
                + minSalesData.orElse(new SalesData("Jan-00", "00")).getDate() + "\n");
    }

}
