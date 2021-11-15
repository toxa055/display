import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class EventsBean {

    private List<Event> events = new ArrayList<>();

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
