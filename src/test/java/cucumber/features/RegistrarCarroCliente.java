package cucumber.features;

import cucumber.http.Sprint1.RegistrarCarroClienteHttp;
import cucumber.resource.interactions.VehicleResource;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegistrarCarroCliente {


    private RegistrarCarroClienteHttp registrarCarroClienteHttp;
    private List<Integer> numbers;
    @Before
    public void init() {
        this.registrarCarroClienteHttp = new RegistrarCarroClienteHttp();
    }

    @Given("que ingreso al API {string} para agregar - Carro_User")
    public void que_ingreso_al_API_para_agregar(String string) {
        this.registrarCarroClienteHttp.setUrl(string);
    }

    @Given("se ubico en la ruta {string} para agregar - Carro_User")
    public void se_ubico_en_la_ruta_para_agregar(String string) {
        this.registrarCarroClienteHttp.setPath(string);
    }

    @Given("mediante el metodo {string} para agregar - Carro_User")
    public void mediante_el_metodo_para_agregar(String string) {
        this.registrarCarroClienteHttp.setMethod(string);
    }

    @Given("tengo el siguiente Carro para agregar")
    public void tengo_el_siguiente_carro_para_agregar(DataTable dataTable) throws IOException {
        List<Map<String,String>> list = dataTable.asMaps(String.class, String.class);

        DataTable a= dataTable.subTable(0,3);
        List<Map<String,Double>> list1 = a.asMaps(String.class,double.class);

        DataTable b= dataTable.subTable(0,4);
        List<Map<String,Double>> list2 = b.asMaps(String.class,double.class);

        for (int i = 0; i < list.size(); i++) {
            VehicleResource vehicleResource = new VehicleResource();
            vehicleResource.setModel(list.get(i).get("Model"));
            vehicleResource.setBrand(list.get(i).get("Brand"));
            vehicleResource.setRegistration_plate(list.get(i).get("Registration_Plate"));
            vehicleResource.setLocation(list1.get(i).get("Location"));
            vehicleResource.setUser_Id(list2.get(i).get("User"));
            this.registrarCarroClienteHttp.createCarro(vehicleResource);
            System.out.println( vehicleResource.getRegistration_plate());
        }
    }

    @When("envio su peticion para agregar - Carro_User")
    public void envio_su_peticion_para_agregar() throws IOException {
        this.registrarCarroClienteHttp.make();
    }

    @Then("se registro Carro")
    public void se_insertaron_los_Carros() {
        List<Integer> codes = this.registrarCarroClienteHttp.getResponseStatusCodes();
        System.out.println(codes);
        for (int i = 0; i < codes.size(); i++) {
            System.out.println(codes.get(0)==200);
            assertEquals(400, (int) codes.get(0));
        }
    }
}
