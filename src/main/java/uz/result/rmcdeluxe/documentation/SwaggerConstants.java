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
    String ADD_PHOTO_IN_OPTION_DESC= """
             Send 'photo' without 'id' and it added
            """;
    String ADD_PHOTO_IN_OPTION_JSON= """
            {
              "id": 0,
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
                    "url": "string"
                  }
                }
              ]
            }
            """;
    String DELETE_BLOG_OPTION_DESC = "Send only 'option' and 'id' and it deleted, you can delete a lot 'option' at the same time";
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
             2) If you want to add a new option to the blog during the update process, and the added option should
             have an image, you should first upload the image and send only the url of the image in the option.
             3) If you want to delete the image of the option during the update process. Use delete API in photo
                                        \s
            """;
    String APARTMENT_UPDATE_DESCRIPTION = """
            UpdateDTO containing the updated information of the PlanOfApartment
            \s
            """;
    String APARTMENT_FULL_FORM = """
            {
              "id": 0,
              "title": {
                "uz": "string",
                "ru": "string",
                "en": "string"
              },
              "floorNum": 0,
              "buildingNum": 0,
              "entranceNum": 0,
              "roomCount": {
                "uz": "string",
                "ru": "string",
                "en": "string"
              },
              "price": 0,
              "photo": {
                "id": 0,
                "url": "string"
              },
              "active": true
            }\s
            """;

    String APARTMENT_CUSTOM_FIELD = """
            {
              "id": 0,
              "floorNum": 0
            }\s
            """;
    String BANNER_UPDATE_DESCRIPTION = """
            Send which field must edit.
             1) If you need add new 'slider' Please send 'slider' without 'id' filed and it added
             If you need remove 'slider' Please send only 'id' filed and it deleted
             2) If you want to add a new slider during the update process. 
             To add an image to the slider, you just send the url of the previously saved image
                                        \s
            """;

    String BANNER_FULL_FORM = """
            {
                "id": 0,
                "sliders": [
                  {
                    "id": 0,
                    "title": {
                      "uz": "string",
                      "ru": "string",
                      "en": "string"
                    },
                    "shortDescription": {
                      "uz": "string",
                      "ru": "string",
                      "en": "string"
                    },
                    "photo": {
                      "id": 0,
                      "url": "string"
                    },
                    "link": "string",
                    "orderNum": 0,
                    "active": true
                  }
                ]
              }
            """;

    String BANNER_CUSTOM_FIELD = """
            {
               "id": 0,
               "sliders": [
                 {
                   "id": 0,
                   "shortDescription": {
                     "uz": "string"
                   },
                   "link": "string",
                   "orderNum": 0
                 }
               ]
             }
            """;

    String ADD_SLIDER_DESC = "Send 'slider' without 'id' and it added. To add an image to the slider, you just send the url of the previously saved image You can add a lot 'slider' at the same time";

    String ADD_SLIDER_JSON = """
            {
              "id": 0,
              "sliders": [
                  {
                    "id": 0,
                    "title": {
                      "uz": "string",
                      "ru": "string",
                      "en": "string"
                    },
                    "shortDescription": {
                      "uz": "string",
                      "ru": "string",
                      "en": "string"
                    },
                    "photo": {
                      "url": "string"
                    },
                    "link": "string"
                  }
                ]
            }
            """;

    String DELETE_SLIDER_DESC = "Send only 'slider' and 'id' and it deleted, you can delete a lot 'slider' at the same time";

    String DELETE_SLIDER_JSON = """
            {
              "id": 0,
              "sliders": [
                {
                  "id": 0
                }
              ]
            }
            """;

}
