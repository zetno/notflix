import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("resources")
public class ResourceLoader extends ResourceConfig {
	public ResourceLoader() {
		packages("resource");
		getClasses();
	}
}