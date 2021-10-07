package profesorp.oauth2.resourceServer.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestServicesController {
	  @RequestMapping("/publica")
	  public String publico() {
	    return "Pagina Publica";
	  }
	  @RequestMapping("/privada")
	  public String privada() {
	    return "Pagina Privada";
	  }
	  @RequestMapping("/admin")
	  public String admin() {
	    return "Pagina Administrador";
	  }
}
