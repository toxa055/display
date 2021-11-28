import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
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

/**
 * Class that sets up connection to message broker, receives messages from queue and
 * executes http requests to get data from the first application.
 */
@Singleton
@Startup
public class EventsReceiver {

    /**
     * Queue name that messages will be received from.
     */
    private static final String QUEUE = "events_queue";

    /**
     * Converts incoming data from JSON format.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * EventsBean bean.
     */
    @Inject
    private EventsBean eventsBean;

    /**
     * Interface that is used for connecting to message broker.
     */
    private Connection connection;

    /**
     * Channel that uses connection to receive data from message queue.
     */
    private Channel channel;

    /**
     * Empty constructor.
     */
    public EventsReceiver() {
    }

    /**
     * Method executes after current bean initialization.
     * At first, it executes http request to the first application to get list of events.
     * It converts it from JSON format to List of Event and sets the list to eventsBean.
     * Consumer receives messages from queue.
     * Every time when new message appears in queue method executes http request
     * to the first application to get list of events.
     *
     * @throws IOException      if error occurs
     * @throws TimeoutException if error occurs.
     */
    @PostConstruct
    public void init() throws IOException, TimeoutException {
        var config = new DefaultClientConfig();
        var client = Client.create(config);
        var service = client.resource(UriBuilder.fromUri("http://localhost:8060").build());
        getEvents(service);

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
                    getEvents(service);
                }
            }
        };
        channel.basicConsume(QUEUE, true, consumer);
    }

    /**
     * Releases resources before removing current instance.
     */
    @PreDestroy
    public void closeConnection() {
        try {
            connection.close();
            channel.close();
        } catch (IOException | TimeoutException e) {
        }
    }

    /**
     * Method executes http request to the first application to get list of events.
     * It converts it from JSON format to List of Event and sets the list to eventsBean.
     *
     * @param service resource where to send http request.
     */
    private void getEvents(WebResource service) {
        try {
            var stringEvents = service.path("rest").path("events")
                    .accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);
            eventsBean.setEvents(List.of(MAPPER.readValue(stringEvents, Event[].class)));
        } catch (Exception e) {
        }
    }
}
