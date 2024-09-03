import ewm.client.StatClient;
import org.springframework.web.client.RestTemplate;

public class Main {
    public static void main(String[] args) {
        StatClient statClient = new StatClient(new RestTemplate(), "Url");
    }
}
