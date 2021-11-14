import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

@ManagedBean
@SessionScoped
public class EventsJSF {

    @Inject
    private EventsBean eventsBean;

    public EventsJSF() {
    }

    public EventsBean getEventsBean() {
        return eventsBean;
    }

    public void setEventsBean(EventsBean eventsBean) {
        this.eventsBean = eventsBean;
    }
}
