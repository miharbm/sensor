import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.style.Styler;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class BarChart implements ExampleChart<CategoryChart> {
    public static void main(String[] args) {
        BarChart barChart = new BarChart();
        CategoryChart chart = barChart.getChart();
        new SwingWrapper<>( chart ).displayChart();
    }

    @Override
    public CategoryChart getChart() {
        // Create Chart
        CategoryChart chart = new CategoryChartBuilder()
                .width(800).height(600)
                .title("Score Histogram")
                .xAxisTitle("X").yAxisTitle("Number")
                .build();

        // Customize Chart
        chart.getStyler().setLegendPosition( Styler.LegendPosition.InsideNW);
        List<Double> list;
        try {
            list = BarChart.getMeasurements();
        } catch (JsonProcessingException e) {
            throw new RuntimeException( e );
        }
        int[] hist = new int[21];
        int[] xHist = new int[21];
        for (int i = - 100; i < 101; i += 10){
            xHist[i / 10 + 10] = i;
        }
        double start = -100.0;
        double finish = 100.0;
        double step = 10.0;
        double left;
        double right;
        left = start;
        right = start + step;
        int count = 0;
        while (right <= finish){
            System.out.println(right);
            for(Double i : list){
                if(i >= left && i <= right){
                    hist[count] ++;
                }
            }
            count++;
            left += step;
            right += step;
        }

        // Series
        chart.addSeries("temperatures", xHist, hist );

        return chart;
    }

    public static  List<Double> getMeasurements() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8081/measurements";
        String response = restTemplate.getForObject( url, String.class );

        ObjectMapper mapper = new ObjectMapper();
        JsonNode obj = mapper.readTree( response );

        List<Double> list = new ArrayList<>();
        for(JsonNode j : obj){
            list.add( j.get( "temperature" ).asDouble() );
        }

        return list;
    }

    @Override
    public String getExampleChartName() {
        return null;
    }
}
