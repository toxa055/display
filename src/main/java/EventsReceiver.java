import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Singleton
@Startup
public class EventsReceiver {

    private static final String QUEUE = "events_queue";

    @Inject
    private EventsBean eventsBean;

    private Connection connection;
    private Channel channel;

    public EventsReceiver() {
    }

    @PostConstruct
    public void init() throws IOException, TimeoutException {
        var mapper = new ObjectMapper();

        var config = new DefaultClientConfig();
        var client = Client.create(config);
        var service = client.resource(UriBuilder.fromUri("http://localhost:8060").build());

        var connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE, true, false, false, null);

        var consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                if ("\"updated\"".equals(new String(body))) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                    var stringEvents = service.path("rest").path("events")
                            .accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);
                    eventsBean.setEvents(List.of(mapper.readValue(stringEvents, Event[].class)));
                }
            }
        };
        channel.basicConsume(QUEUE, true, consumer);
    }

    @PreDestroy
    public void closeConnection() {
        try {
            connection.close();
            channel.close();
        } catch (IOException | TimeoutException e) {
        }
    }
}
