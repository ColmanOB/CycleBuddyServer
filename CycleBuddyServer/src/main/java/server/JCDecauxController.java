package server;

import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data_models.JCDContract;
import data_models.JCDStation;
import api_client.JCDClient;

@RestController
public class JCDecauxController {

	@RequestMapping("/jcdecaux/station")
	public JCDStation getStation(@RequestParam(value = "contractName") String contractName,
			@RequestParam(value = "stationId") String id) throws IllegalStateException, IOException {
		return JCDClient.getStationByID(contractName, id,
				Application.serverConfiguration.getProperty("APIKey_JCDecaux"));
	}

	@RequestMapping("/jcdecaux/stations_in_contract")
	public JCDStation[] getStationsInContract(@RequestParam(value = "contractName") String contractName)
			throws IllegalStateException, IOException {
		return JCDClient.getStationListByContract(contractName,
				Application.serverConfiguration.getProperty("APIKey_JCDecaux"));
	}

	@RequestMapping("/jcdecaux/stations_all")
	public JCDStation[] getStationsAll() throws IllegalStateException, IOException {
		return JCDClient.getStationListAll(Application.serverConfiguration.getProperty("APIKey_JCDecaux"));
	}

	@RequestMapping("/jcdecaux/contract_list")
	public JCDContract[] getContracts() throws IllegalStateException, IOException {
		return JCDClient.getContractList(Application.serverConfiguration.getProperty("APIKey_JCDecaux"));
	}
}
