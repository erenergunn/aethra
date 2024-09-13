package com.eren.aethra.api.store;

import com.eren.aethra.constants.AethraCoreConstants;
import com.eren.aethra.dtos.requests.StoreRequest;
import com.eren.aethra.services.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("")
public class CmsController {

    @Resource
    private StoreService storeService;

    @GetMapping("/homepage")
    public ResponseEntity<String> getHomepage(){
        return new ResponseEntity<>(
                "{\n" +
                        "    \"glossary\": {\n" +
                        "        \"title\": \"öçşi glossary\",\n" +
                        "\t\t\"GlossDiv\": {\n" +
                        "            \"title\": \"S\",\n" +
                        "\t\t\t\"GlossList\": {\n" +
                        "                \"GlossEntry\": {\n" +
                        "                    \"ID\": \"SGML\",\n" +
                        "\t\t\t\t\t\"SortAs\": \"SGML\",\n" +
                        "\t\t\t\t\t\"GlossTerm\": \"Standard Generalized Markup Language\",\n" +
                        "\t\t\t\t\t\"Acronym\": \"SGML\",\n" +
                        "\t\t\t\t\t\"Abbrev\": \"ISO 8879:1986\",\n" +
                        "\t\t\t\t\t\"GlossDef\": {\n" +
                        "                        \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\n" +
                        "\t\t\t\t\t\t\"GlossSeeAlso\": [\"GML\", \"XML\"]\n" +
                        "                    },\n" +
                        "\t\t\t\t\t\"GlossSee\": \"markup\"\n" +
                        "                }\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}",
                HttpStatus.OK);
    }

    @GetMapping("/store")
    public ResponseEntity getStore() {
        try {
            return new ResponseEntity<>(storeService.getStore(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/store/update")
    public ResponseEntity updateStore(@RequestBody StoreRequest storeRequest) {
        try {
            storeService.updateStore(storeRequest);
            return new ResponseEntity<>(AethraCoreConstants.STORE_UPDATED_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
