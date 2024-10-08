package uz.result.rmcdeluxe.documentation;

public interface SwaggerConstants {

    String CATALOG_FULL_FORM = """
            {
               "id": 0,
               "name": "string",
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

    String ABOUT_US_BANNER_UPDATE_DESCRIPTION = """
            UpdateDTO containing the updated information of the about us banner
            \s""";

    String ABOUT_US_BODY_UPDATE_DESCRIPTION = """
            UpdateDTO containing the updated information of the about us body
            \s""";

    String BUILD_UPDATE_DESCRIPTION = """
            1) If you want to add a new video or picture to the building during the update process. 
            First save and then send only the url of this image or video in json format.
                                                                     
            2) If you want to delete a picture or video from the building. You use the delete API released for Video/Image
            \s""";

    String INF_AREA_UPDATE_DESCRIPTION = """
            UpdateDTO containing the updated information of the InfArea
            \s""";

    String DISTRICT_UPDATE_DESCRIPTION = """
            UpdateDTO containing the updated information of the district
            \s""";

    String REVIEW_UPDATE_DESCRIPTION = """
            UpdateDTO containing the updated information of the review
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
    String ADD_PHOTO_IN_OPTION_DESC = """
             Send 'photo' without 'id' and it added
            """;
    String ADD_PHOTO_IN_OPTION_JSON = """
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
               "buildingId": 0,
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
             \s
            """;

    String BANNER_FULL_FORM = """
            {
               "id": 1,
               "title": {
                 "uz": "title",
                 "ru": "string",
                 "en": "string"
               },
               "shortDescription": {
                 "uz": "description",
                 "ru": "string",
                 "en": "string"
               },
               "link": "string_url",
               "orderNum": 1,
               "active": true
             }
            """;

    String BANNER_CUSTOM_FIELD = """
            {
               "id": 1,
               "title": {
                 "uz": "title"
               }
             }
            """;

//    String ADD_SLIDER_DESC = "Send 'slider' without 'id' and it added. To add an image to the slider, you just send the url of the previously saved image You can add a lot 'slider' at the same time";
//
//    String ADD_SLIDER_JSON = """
//            {
//              "id": 0,
//              "sliders": [
//                  {
//                    "id": 0,
//                    "title": {
//                      "uz": "string",
//                      "ru": "string",
//                      "en": "string"
//                    },
//                    "shortDescription": {
//                      "uz": "string",
//                      "ru": "string",
//                      "en": "string"
//                    },
//                    "photo": {
//                      "url": "string"
//                    },
//                    "link": "string"
//                  }
//                ]
//            }
//            """;
//
//    String DELETE_SLIDER_DESC = "Send only 'slider' and 'id' and it deleted, you can delete a lot 'slider' at the same time";
//
//    String DELETE_SLIDER_JSON = """
//            {
//              "id": 0,
//              "sliders": [
//                {
//                  "id": 0
//                }
//              ]
//            }
//            """;

    String INF_AREA_FULL_FORM = """
            {
               "id": 0,
               "buildingId": 0,
               "sections": [
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
                   "sectionItems": [
                     {
                       "id": 0,
                       "name": {
                         "uz": "string",
                         "ru": "string",
                         "en": "string"
                       },
                       "timeOrDistance": {
                         "uz": "string",
                         "ru": "string",
                         "en": "string"
                       }
                     }
                   ]
                 }
               ],
               "photo": {
                 "id": 0,
                 "url": "string"
               }
            }           \s
            """;

    String INF_AREA_CUSTOM_FIELD = """
            {
              "id": 0,
              "sections": [
                {
                  "id": 0,
                  "sectionItems": [
                    {
                      "id": 0,
                      "name": {
                        "uz": "string"
                      }
                    }
                  ]
                }
              ]
            }           \s
            """;
    String BUILD_FULL_FORM = """
            {
              "id": 0,
              "catalogId":0,
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
              "gallery": [
                {
                  "id": 0,
                  "url": "string"
                }
              ],
              "videoList": [
                {
                  "id": 0,
                  "videoUrl": "string"
                }
              ],
              "active": true
            }
            """;
    String BUILD_CUSTOM_FIELD = """
            {
              "id": 0,
              "title": {
                "uz": "string"
              }
            }
            """;

    String ADD_PHOTO_OR_VIDEO_IN_BUILDING = """
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
              "gallery": [
                {
                  "url": "photo_url_1"
                },
                {
                  "url": "photo_url_2"
                }
              ],
              "videoList": [
                {
                  "videoUrl": "video_url_1"
                },
                {
                  "videoUrl": "video_url_2"
                }
              ],
              "active": true
            }
            """;
    String REVIEW_FULL_FORM = """
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
              "active": true
            }           \s
            """;

    String REVIEW_CUSTOM_FIELD = """
            {
              "id": 0,
              "title": {
                "uz": "string"
              }
            }           \s
            """;
    String ABOUT_US_BANNER_FULL_FORM = """
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
               }
             }
            """;
    String ABOUT_US_BANNER_CUSTOM_FIELD = """
            {
               "id": 0,
               "title": {
                 "uz": "string"
               }
             }
            """;
    String ABOUT_US_BODY_FULL_FORM = """
            {
              "id": 0,
              "title": {
                "uz": "string",
                "ru": "string",
                "en": "string"
              },
              "subtitle": {
                "uz": "string",
                "ru": "string",
                "en": "string"
              },
              "description": {
                "uz": "string",
                "ru": "string",
                "en": "string"
              }
            }
            """;
    String ABOUT_US_BODY_CUSTOM_FIELD = """
            {
              "id": 0,
              "title": {
                "uz": "string"
              }
            }
            """;
}
