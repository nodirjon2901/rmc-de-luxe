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

    String BLOG_FULL_FORM = """
            {
              "id": 0,
              "headOption": {
                "id": 0,
                "title": {
                  "uz": "string",
                  "ru": "string",
                  "en": "string"
                },
                "description": {
                  "uz": "string",
                  "ru": "string",
                  "en": "string"
                },
                "photo": {
                  "id": 0,
                  "url": "string"
                }
              },
              "options": [
                {
                  "id": 0,
                  "title": {
                    "uz": "string",
                    "ru": "string",
                    "en": "string"
                  },
                  "description": {
                    "uz": "string",
                    "ru": "string",
                    "en": "string"
                  },
                  "photo": {
                    "id": 0,
                    "url": "string"
                  }
                }
              ],
              "typeId": 0,
              "active": true,
              "main": true
            }
            """;
    String BLOG_CUSTOM_FIELD = """
            {
              "id": 0,
              "headOption": {
                "id": 0,
                "title": {
                  "uz": "edit_string"
                }
              }
            }
            """;
    String ADD_BLOG_OPTION_DESC = "Send 'option' without 'id' and it added, you can add a lot 'option' at the same time";
    String ADD_BLOG_OPTION_JSON = """
            {
              "id": 0,
              "headOption": {
                "id": 0,
                "title": {
                  "uz": "string",
                  "ru": "string",
                  "en": "string"
                },
                "description": {
                  "uz": "string",
                  "ru": "string",
                  "en": "string"
                },
                "photo": {
                  "id": 0,
                  "url": "string"
                }
              },
              "options": [
                {
                  "title": {
                    "uz": "string",
                    "ru": "string",
                    "en": "string"
                  },
                  "description": {
                    "uz": "string",
                    "ru": "string",
                    "en": "string"
                  },
                  "photo": {
                    "id": 0,
                    "url": "string"
                  }
                }
              ],
              "typeId": 0,
              "active": true,
              "main": true
            }
            """;
    String DELETE_BLOG_OPTION_DESC = "send only 'option' and 'id' and it deleted, you can delete a lot 'option' at the same time";
    String DELETE_BLOG_OPTION_JSON = """
            {
              "id": 0,
              "headOption": {
                "id": 0,
                "title": {
                  "uz": "string",
                  "ru": "string",
                  "en": "string"
                },
                "description": {
                  "uz": "string",
                  "ru": "string",
                  "en": "string"
                },
                "photo": {
                  "id": 0,
                  "url": "string"
                }
              },
              "options": [
                {
                  "id": 0
                }
              ],
              "typeId": 0,
              "active": true,
              "main": true
            }
            """;
    String BlOG_UPDATE_DESCRIPTION = """
            Send which field must edit.
             1) If you need add new 'option' Please send 'option' without 'id' filed and it added
             If you need remove 'option' Please send only 'id' filed and it deleted
                                        \s
            """;
}
