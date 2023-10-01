import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.Measurement;
import dto.Sensor;
import org.knowm.xchart.*;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

//        List<Double> list = getMeasurements();
//
//        // Create Chart
//        XYChart chart = new XYChart( 500, 500 );
//        chart.setTitle( "Temperatures" );
//        chart.setXAxisTitle( "X" );
//        chart.setYAxisTitle( "Y" );
//        XYSeries series = chart.addSeries( "y(x)", null, list );
//
//        // Show it
//        new SwingWrapper(chart).displayChart();


    }

    public static void test1000measures(){
        String url1 = "http://localhost:8081/sensors/registration";
        String url2 = "http://localhost:8081/measurements/add";


        RestTemplate restTemplate = new RestTemplate();

        Sensor sensor = new Sensor("test1000_sensor");

        HttpEntity<Sensor> request1 = new HttpEntity<>( sensor );

        String response1 = restTemplate.postForObject(url1, request1,String.class  );

        System.out.println(response1);

        for (int i = 0; i < 1000; i++){

            double temperature = ((int)(Math.random() * 2000) - 1000)/10.0;
            boolean isRain = (int) temperature % 2 == 0;

            Measurement measurement = new Measurement( sensor, temperature, isRain );
            HttpEntity<Measurement> request2 = new HttpEntity<>( measurement );

            String response2 = restTemplate.postForObject(url2, request2,String.class  );

            System.out.println(response2);

        }
    }

//    public static  List<Double> getMeasurements(){
//        RestTemplate restTemplate = new RestTemplate();
//
//        String url = "http://localhost:8081/measurements";
//        Measurement[] response = restTemplate.getForObject( url, Measurement[].class );
//        List<Double> list = new ArrayList<>();
//        assert response != null;
//        for(Measurement m : response){
//            list.add( m.getTemperature() );
//        }
//
//        return list;
//    }
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
}
