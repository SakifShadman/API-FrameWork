package Resources;

import Pojo.AddPlace;
import Pojo.Location;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuild {

    public AddPlace addPlacePayload(String name, String language, String address) {
        AddPlace addPlaceObject = new AddPlace();

        addPlaceObject.setAccuracy(50);
        addPlaceObject.setName(name);
        addPlaceObject.setPhone_number("(+91) 983 893 3937");
        addPlaceObject.setAddress(address);
        addPlaceObject.setWebsite("http://google.com");
        addPlaceObject.setLanguage(language);

        List<String> typesList = new ArrayList<>();
        typesList.add("shoe park");
        typesList.add("shop");
        addPlaceObject.setTypes(typesList);

        Location locationObject = new Location();
        locationObject.setLat(-38.383494);
        locationObject.setLng(33.427362);
        addPlaceObject.setLocation(locationObject);

        return addPlaceObject;
    }

    public String deletePlacePayload(String placeId) {
        return "{\n" +
                "    \"place_id\":\"" + placeId + "\"\n" +
                "}";
    }
}
