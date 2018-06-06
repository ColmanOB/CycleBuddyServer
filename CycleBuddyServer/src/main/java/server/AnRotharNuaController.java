package server;

import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data_models.ARNScheme;
import api_client.ARNClient;

@RestController
public class AnRotharNuaController {

	@RequestMapping("/an_rothar_nua/stations")
	public ARNScheme getStations(@RequestParam(value = "schemeID") String schemeID)
			throws IllegalStateException, IOException {
		return ARNClient.getSchemeByID(schemeID, Application.serverConfiguration.getProperty("APIKey_AnRotharNua"));
	}
}
