import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Singleton
@Startup
public class MQReceiver {

    private static final String QUEUE = "events_queue";

    @Inject
    private EventsBean eventsBean;

    public MQReceiver() {
    }

    @PostConstruct
    public void init() throws IOException, TimeoutException {
        var connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        var connection = connectionFactory.newConnection();
        var channel = connection.createChannel();
        channel.queueDeclare(QUEUE, true, false, false, null);
        var consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                var mapper = new ObjectMapper();
                eventsBean.setEvents(List.of(mapper.readValue(body, Event[].class)));
            }
        };
        channel.basicConsume(QUEUE, true, consumer);
    }
}
