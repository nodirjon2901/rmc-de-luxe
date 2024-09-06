package uz.result.rmcdeluxe.documentation;

public interface SwaggerConstants {

    String CATALOG_FULL_FORM = """
            {
               "id": 0,
               "name": "string",
               "photo": {
                 "id": 0,
                 "url": "string"
               },
               "districtId": 0,
               "price": 0,
               "typeId": 0,
               "numberOfRooms": {
                 "uz": "string",
                 "ru": "string",
                 "en": "string"
               },
               "completionDate": {
                 "uz": "string",
                 "ru": "string",
                 "en": "string"
               },
               "active": true
             }
            """;
    String CATALOG_CUSTOM_FIELD = """
            {
              "id": 0,
              "name":"edit_name"
            }
            """;

    String DISTRICT_FULL_FORM = """
            {
               "id": 0,
               "name": {
                 "uz": "string",
                 "ru": "string",
                 "en": "string"
               }
            }\s
            """;

    String DISTRICT_CUSTOM_FIELD = """
            {
               "id": 0,
               "name": {
                 "uz": "string"
               }
            }\s
            """;

    String CATALOG_UPDATE_DESCRIPTION = """
            UpdateDTO containing the updated information of the catalog
            \s""";

    String DISTRICT_UPDATE_DESCRIPTION = """
            UpdateDTO containing the updated information of the district
            \s""";

    String HOUSE_TYPE_UPDATE_DESCRIPTION = """
            UpdateDTO containing the updated information of the type
            \s""";

}
