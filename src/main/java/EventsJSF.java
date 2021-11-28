import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

/**
 * Class that bean is available from jsf page.
 * It's used for provide data for view (i.e. jsf page).
 */
@ManagedBean
@SessionScoped
public class EventsJSF {

    /**
     * Injected bean of EventsBean.
     */
    @Inject
    private EventsBean eventsBean;

    /**
     * Empty constructor.
     */
    public EventsJSF() {
    }

    public EventsBean getEventsBean() {
        return eventsBean;
    }

    public void setEventsBean(EventsBean eventsBean) {
        this.eventsBean = eventsBean;
    }
}
