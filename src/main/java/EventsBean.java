import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that will store list of received events.
 */
@Singleton
public class EventsBean {

    /**
     * List that will store received events.
     */
    private List<Event> events = new ArrayList<>();

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
