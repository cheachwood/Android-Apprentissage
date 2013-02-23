package com.google.protos.embed;

import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.Internal.EnumLiteMap;

public final class ItemTypes
{
  public static enum ItemType
    implements Internal.EnumLite
  {
    private static Internal.EnumLiteMap<ItemType> internalValueMap = new Internal.EnumLiteMap()
    {
    };
    private final int value;

    static
    {
      THING = new ItemType("THING", 1, 0);
      CREATIVE_WORK = new ItemType("CREATIVE_WORK", 2, 1);
      EVENT = new ItemType("EVENT", 3, 2);
      INTANGIBLE = new ItemType("INTANGIBLE", 4, 3);
      ORGANIZATION = new ItemType("ORGANIZATION", 5, 4);
      PERSON = new ItemType("PERSON", 6, 5);
      PLACE = new ItemType("PLACE", 7, 6);
      PRODUCT = new ItemType("PRODUCT", 8, 7);
      ARTICLE = new ItemType("ARTICLE", 9, 11);
      BLOG_POSTING = new ItemType("BLOG_POSTING", 10, 12);
      NEWS_ARTICLE = new ItemType("NEWS_ARTICLE", 11, 13);
      SCHOLARLY_ARTICLE = new ItemType("SCHOLARLY_ARTICLE", 12, 219);
      BLOG = new ItemType("BLOG", 13, 14);
      BOOK = new ItemType("BOOK", 14, 15);
      COMMENT = new ItemType("COMMENT", 15, 264);
      ITEM_LIST = new ItemType("ITEM_LIST", 16, 16);
      MAP = new ItemType("MAP", 17, 17);
      MEDIA_OBJECT = new ItemType("MEDIA_OBJECT", 18, 18);
      AUDIO_OBJECT = new ItemType("AUDIO_OBJECT", 19, 19);
      IMAGE_OBJECT = new ItemType("IMAGE_OBJECT", 20, 20);
      MUSIC_VIDEO_OBJECT = new ItemType("MUSIC_VIDEO_OBJECT", 21, 21);
      VIDEO_OBJECT = new ItemType("VIDEO_OBJECT", 22, 22);
      MOVIE = new ItemType("MOVIE", 23, 23);
      MUSIC_PLAYLIST = new ItemType("MUSIC_PLAYLIST", 24, 24);
      MUSIC_ALBUM = new ItemType("MUSIC_ALBUM", 25, 25);
      MUSIC_RECORDING = new ItemType("MUSIC_RECORDING", 26, 26);
      PAINTING = new ItemType("PAINTING", 27, 27);
      PHOTOGRAPH = new ItemType("PHOTOGRAPH", 28, 28);
      RECIPE = new ItemType("RECIPE", 29, 29);
      REVIEW = new ItemType("REVIEW", 30, 30);
      SCULPTURE = new ItemType("SCULPTURE", 31, 31);
      SOFTWARE_APPLICATION = new ItemType("SOFTWARE_APPLICATION", 32, 302);
      MOBILE_APPLICATION = new ItemType("MOBILE_APPLICATION", 33, 303);
      WEB_APPLICATION = new ItemType("WEB_APPLICATION", 34, 304);
      TV_EPISODE = new ItemType("TV_EPISODE", 35, 32);
      TV_SEASON = new ItemType("TV_SEASON", 36, 33);
      TV_SERIES = new ItemType("TV_SERIES", 37, 34);
      WEB_PAGE = new ItemType("WEB_PAGE", 38, 35);
      ABOUT_PAGE = new ItemType("ABOUT_PAGE", 39, 36);
      CHECKOUT_PAGE = new ItemType("CHECKOUT_PAGE", 40, 37);
      COLLECTION_PAGE = new ItemType("COLLECTION_PAGE", 41, 38);
      IMAGE_GALLERY = new ItemType("IMAGE_GALLERY", 42, 39);
      VIDEO_GALLERY = new ItemType("VIDEO_GALLERY", 43, 40);
      CONTACT_PAGE = new ItemType("CONTACT_PAGE", 44, 41);
      ITEM_PAGE = new ItemType("ITEM_PAGE", 45, 42);
      PROFILE_PAGE = new ItemType("PROFILE_PAGE", 46, 43);
      SEARCH_RESULTS_PAGE = new ItemType("SEARCH_RESULTS_PAGE", 47, 44);
      WEB_PAGE_ELEMENT = new ItemType("WEB_PAGE_ELEMENT", 48, 45);
      SITE_NAVIGATION_ELEMENT = new ItemType("SITE_NAVIGATION_ELEMENT", 49, 46);
      TABLE = new ItemType("TABLE", 50, 47);
      WP_AD_BLOCK = new ItemType("WP_AD_BLOCK", 51, 48);
      WP_FOOTER = new ItemType("WP_FOOTER", 52, 49);
      WP_HEADER = new ItemType("WP_HEADER", 53, 50);
      WP_SIDEBAR = new ItemType("WP_SIDEBAR", 54, 51);
      BUSINESS_EVENT = new ItemType("BUSINESS_EVENT", 55, 52);
      CHILDRENS_EVENT = new ItemType("CHILDRENS_EVENT", 56, 53);
      COMEDY_EVENT = new ItemType("COMEDY_EVENT", 57, 54);
      DANCE_EVENT = new ItemType("DANCE_EVENT", 58, 55);
      EDUCATION_EVENT = new ItemType("EDUCATION_EVENT", 59, 56);
      FESTIVAL = new ItemType("FESTIVAL", 60, 57);
      FOOD_EVENT = new ItemType("FOOD_EVENT", 61, 58);
      LITERARY_EVENT = new ItemType("LITERARY_EVENT", 62, 59);
      MUSIC_EVENT = new ItemType("MUSIC_EVENT", 63, 60);
      SALE_EVENT = new ItemType("SALE_EVENT", 64, 61);
      SOCIAL_EVENT = new ItemType("SOCIAL_EVENT", 65, 62);
      SPORTS_EVENT = new ItemType("SPORTS_EVENT", 66, 63);
      THEATER_EVENT = new ItemType("THEATER_EVENT", 67, 64);
      VISUAL_ARTS_EVENT = new ItemType("VISUAL_ARTS_EVENT", 68, 65);
      RESERVATION = new ItemType("RESERVATION", 69, 267);
      TRAVEL_EVENT = new ItemType("TRAVEL_EVENT", 70, 325);
      CORPORATION = new ItemType("CORPORATION", 71, 66);
      EDUCATIONAL_ORGANIZATION = new ItemType("EDUCATIONAL_ORGANIZATION", 72, 67);
      COLLEGE_OR_UNIVERSITY = new ItemType("COLLEGE_OR_UNIVERSITY", 73, 68);
      ELEMENTARY_SCHOOL = new ItemType("ELEMENTARY_SCHOOL", 74, 69);
      HIGH_SCHOOL = new ItemType("HIGH_SCHOOL", 75, 70);
      MIDDLE_SCHOOL = new ItemType("MIDDLE_SCHOOL", 76, 71);
      PRESCHOOL = new ItemType("PRESCHOOL", 77, 72);
      SCHOOL = new ItemType("SCHOOL", 78, 73);
      GOVERNMENT_ORGANIZATION = new ItemType("GOVERNMENT_ORGANIZATION", 79, 74);
      LOCAL_BUSINESS = new ItemType("LOCAL_BUSINESS", 80, 75);
      ANIMAL_SHELTER = new ItemType("ANIMAL_SHELTER", 81, 76);
      AUTOMOTIVE_BUSINESS = new ItemType("AUTOMOTIVE_BUSINESS", 82, 77);
      AUTO_BODY_SHOP = new ItemType("AUTO_BODY_SHOP", 83, 78);
      AUTO_DEALER = new ItemType("AUTO_DEALER", 84, 79);
      AUTO_PARTS_STORE = new ItemType("AUTO_PARTS_STORE", 85, 80);
      AUTO_RENTAL = new ItemType("AUTO_RENTAL", 86, 81);
      AUTO_REPAIR = new ItemType("AUTO_REPAIR", 87, 82);
      AUTO_WASH = new ItemType("AUTO_WASH", 88, 83);
      GAS_STATION = new ItemType("GAS_STATION", 89, 84);
      MOTORCYCLE_DEALER = new ItemType("MOTORCYCLE_DEALER", 90, 85);
      MOTORCYCLE_REPAIR = new ItemType("MOTORCYCLE_REPAIR", 91, 86);
      CHILD_CARE = new ItemType("CHILD_CARE", 92, 87);
      DRY_CLEANING_OR_LAUNDRY = new ItemType("DRY_CLEANING_OR_LAUNDRY", 93, 88);
      EMERGENCY_SERVICE = new ItemType("EMERGENCY_SERVICE", 94, 89);
      FIRE_STATION = new ItemType("FIRE_STATION", 95, 90);
      HOSPITAL = new ItemType("HOSPITAL", 96, 91);
      POLICE_STATION = new ItemType("POLICE_STATION", 97, 92);
      EMPLOYMENT_AGENGY = new ItemType("EMPLOYMENT_AGENGY", 98, 93);
      ENTERTAINMENT_BUSINESS = new ItemType("ENTERTAINMENT_BUSINESS", 99, 94);
      ADULT_ENTERTAINMENT = new ItemType("ADULT_ENTERTAINMENT", 100, 95);
      AMUSEMENT_PARK = new ItemType("AMUSEMENT_PARK", 101, 96);
      ART_GALLERY = new ItemType("ART_GALLERY", 102, 97);
      CASINO = new ItemType("CASINO", 103, 98);
      COMEDY_CLUB = new ItemType("COMEDY_CLUB", 104, 99);
      MOVIE_THEATER = new ItemType("MOVIE_THEATER", 105, 100);
      NIGHT_CLUB = new ItemType("NIGHT_CLUB", 106, 101);
      FINANCIAL_SERVICE = new ItemType("FINANCIAL_SERVICE", 107, 102);
      ACCOUNTING_SERVICE = new ItemType("ACCOUNTING_SERVICE", 108, 103);
      AUTOMATED_TELLER = new ItemType("AUTOMATED_TELLER", 109, 104);
      BANK_OR_CREDIT_UNION = new ItemType("BANK_OR_CREDIT_UNION", 110, 105);
      INSURANCE_AGENCY = new ItemType("INSURANCE_AGENCY", 111, 106);
      FOOD_ESTABLISHMENT = new ItemType("FOOD_ESTABLISHMENT", 112, 107);
      BAKERY = new ItemType("BAKERY", 113, 108);
      BAR_OR_PUB = new ItemType("BAR_OR_PUB", 114, 109);
      BREWERY = new ItemType("BREWERY", 115, 110);
      CAFE_OR_COFFEE_SHOP = new ItemType("CAFE_OR_COFFEE_SHOP", 116, 111);
      FAST_FOOD_RESTAURANT = new ItemType("FAST_FOOD_RESTAURANT", 117, 112);
      ICE_CREAM_SHOP = new ItemType("ICE_CREAM_SHOP", 118, 113);
      RESTAURANT = new ItemType("RESTAURANT", 119, 114);
      WINERY = new ItemType("WINERY", 120, 115);
      GOVERNMENT_OFFICE = new ItemType("GOVERNMENT_OFFICE", 121, 116);
      POST_OFFICE = new ItemType("POST_OFFICE", 122, 117);
      HEALTH_AND_BEAUTY_BUSINESS = new ItemType("HEALTH_AND_BEAUTY_BUSINESS", 123, 118);
      BEAUTY_SALON = new ItemType("BEAUTY_SALON", 124, 119);
      DAY_SPA = new ItemType("DAY_SPA", 125, 120);
      HAIR_SALON = new ItemType("HAIR_SALON", 126, 121);
      HEALTH_CLUB = new ItemType("HEALTH_CLUB", 127, 122);
      NAIL_SALON = new ItemType("NAIL_SALON", 128, 123);
      TATTOO_PARLOR = new ItemType("TATTOO_PARLOR", 129, 124);
      HOME_AND_CONSTRUCTION_BUSINESS = new ItemType("HOME_AND_CONSTRUCTION_BUSINESS", 130, 125);
      ELECTRICIAN = new ItemType("ELECTRICIAN", 131, 126);
      GENERAL_CONTRACTOR = new ItemType("GENERAL_CONTRACTOR", 132, 127);
      HVAC_BUSINESS = new ItemType("HVAC_BUSINESS", 133, 128);
      HOUSE_PAINTER = new ItemType("HOUSE_PAINTER", 134, 129);
      LOCKSMITH = new ItemType("LOCKSMITH", 135, 130);
      MOVING_COMPANY = new ItemType("MOVING_COMPANY", 136, 131);
      PLUMBER = new ItemType("PLUMBER", 137, 132);
      ROOFING_CONTRACTOR = new ItemType("ROOFING_CONTRACTOR", 138, 133);
      INTERNET_CAFE = new ItemType("INTERNET_CAFE", 139, 134);
      LIBRARY = new ItemType("LIBRARY", 140, 135);
      LODGING_BUSINESS = new ItemType("LODGING_BUSINESS", 141, 136);
      BED_AND_BREAKFAST = new ItemType("BED_AND_BREAKFAST", 142, 137);
      HOSTEL = new ItemType("HOSTEL", 143, 138);
      HOTEL = new ItemType("HOTEL", 144, 139);
      MOTEL = new ItemType("MOTEL", 145, 140);
      MEDICAL_ORGANIZATION = new ItemType("MEDICAL_ORGANIZATION", 146, 141);
      DENTIST = new ItemType("DENTIST", 147, 142);
      MEDICAL_CLINIC = new ItemType("MEDICAL_CLINIC", 148, 144);
      OPTICIAN = new ItemType("OPTICIAN", 149, 145);
      PHARMACY = new ItemType("PHARMACY", 150, 146);
      PHYSICIAN = new ItemType("PHYSICIAN", 151, 147);
      VETERINARY_CARE = new ItemType("VETERINARY_CARE", 152, 148);
      PROFESSIONAL_SERVICE = new ItemType("PROFESSIONAL_SERVICE", 153, 149);
      ATTORNEY = new ItemType("ATTORNEY", 154, 151);
      NOTARY = new ItemType("NOTARY", 155, 157);
      RADIO_STATION = new ItemType("RADIO_STATION", 156, 160);
      REAL_ESTATE_AGENT = new ItemType("REAL_ESTATE_AGENT", 157, 161);
      RECYCLING_CENTER = new ItemType("RECYCLING_CENTER", 158, 162);
      SELF_STORAGE = new ItemType("SELF_STORAGE", 159, 163);
      SHOPPING_CENTER = new ItemType("SHOPPING_CENTER", 160, 164);
      SPORTS_ACTIVITY_LOCATION = new ItemType("SPORTS_ACTIVITY_LOCATION", 161, 165);
      BOWLING_ALLEY = new ItemType("BOWLING_ALLEY", 162, 166);
      EXERCISE_GYM = new ItemType("EXERCISE_GYM", 163, 167);
      GOLF_COURSE = new ItemType("GOLF_COURSE", 164, 168);
      PUBLIC_SWIMMING_POOL = new ItemType("PUBLIC_SWIMMING_POOL", 165, 170);
      SKI_RESORT = new ItemType("SKI_RESORT", 166, 171);
      SPORTS_CLUB = new ItemType("SPORTS_CLUB", 167, 172);
      STADIUM_OR_ARENA = new ItemType("STADIUM_OR_ARENA", 168, 173);
      TENNIS_COMPLEX = new ItemType("TENNIS_COMPLEX", 169, 174);
      STORE = new ItemType("STORE", 170, 175);
      BIKE_STORE = new ItemType("BIKE_STORE", 171, 177);
      BOOK_STORE = new ItemType("BOOK_STORE", 172, 178);
      CLOTHING_STORE = new ItemType("CLOTHING_STORE", 173, 179);
      COMPUTER_STORE = new ItemType("COMPUTER_STORE", 174, 180);
      CONVENIENCE_STORE = new ItemType("CONVENIENCE_STORE", 175, 181);
      DEPARTMENT_STORE = new ItemType("DEPARTMENT_STORE", 176, 182);
      ELECTRONICS_STORE = new ItemType("ELECTRONICS_STORE", 177, 183);
      FLORIST = new ItemType("FLORIST", 178, 184);
      FURNITURE_STORE = new ItemType("FURNITURE_STORE", 179, 185);
      GARDEN_STORE = new ItemType("GARDEN_STORE", 180, 186);
      GROCERY_STORE = new ItemType("GROCERY_STORE", 181, 187);
      HARDWARE_STORE = new ItemType("HARDWARE_STORE", 182, 188);
      HOBBY_SHOP = new ItemType("HOBBY_SHOP", 183, 189);
      HOME_GOODS_STORE = new ItemType("HOME_GOODS_STORE", 184, 190);
      JEWELRY_STORE = new ItemType("JEWELRY_STORE", 185, 191);
      LIQUOR_STORE = new ItemType("LIQUOR_STORE", 186, 192);
      MENS_CLOTHING_STORE = new ItemType("MENS_CLOTHING_STORE", 187, 193);
      MOBILE_PHONE_STORE = new ItemType("MOBILE_PHONE_STORE", 188, 194);
      MOVIE_RENTAL_STORE = new ItemType("MOVIE_RENTAL_STORE", 189, 195);
      MUSIC_STORE = new ItemType("MUSIC_STORE", 190, 196);
      OFFICE_EQUIPMENT_STORE = new ItemType("OFFICE_EQUIPMENT_STORE", 191, 197);
      OUTLET_STORE = new ItemType("OUTLET_STORE", 192, 198);
      PAWN_SHOP = new ItemType("PAWN_SHOP", 193, 199);
      PET_STORE = new ItemType("PET_STORE", 194, 200);
      SHOE_STORE = new ItemType("SHOE_STORE", 195, 201);
      SPORTING_GOODS_STORE = new ItemType("SPORTING_GOODS_STORE", 196, 202);
      TIRE_SHOP = new ItemType("TIRE_SHOP", 197, 203);
      TOY_STORE = new ItemType("TOY_STORE", 198, 204);
      WHOLESALE_STORE = new ItemType("WHOLESALE_STORE", 199, 205);
      TELEVISION_STATION = new ItemType("TELEVISION_STATION", 200, 206);
      TOURIST_INFORMATION_CENTER = new ItemType("TOURIST_INFORMATION_CENTER", 201, 207);
      TRAVEL_AGENCY = new ItemType("TRAVEL_AGENCY", 202, 208);
      PERFORMING_GROUP = new ItemType("PERFORMING_GROUP", 203, 259);
      MUSIC_GROUP = new ItemType("MUSIC_GROUP", 204, 260);
      ADMINISTRATIVE_AREA = new ItemType("ADMINISTRATIVE_AREA", 205, 268);
      CITY = new ItemType("CITY", 206, 269);
      COUNTRY = new ItemType("COUNTRY", 207, 270);
      STATE = new ItemType("STATE", 208, 271);
      CIVIC_STRUCTURE = new ItemType("CIVIC_STRUCTURE", 209, 272);
      AIRPORT = new ItemType("AIRPORT", 210, 273);
      AQUARIUM = new ItemType("AQUARIUM", 211, 274);
      BEACH = new ItemType("BEACH", 212, 275);
      BUS_STATION = new ItemType("BUS_STATION", 213, 276);
      BUS_STOP = new ItemType("BUS_STOP", 214, 277);
      CAMPGROUND = new ItemType("CAMPGROUND", 215, 278);
      CEMETERY = new ItemType("CEMETERY", 216, 279);
      CREMATORIUM = new ItemType("CREMATORIUM", 217, 280);
      EVENT_VENUE = new ItemType("EVENT_VENUE", 218, 281);
      GOVERNMENT_BUILDING = new ItemType("GOVERNMENT_BUILDING", 219, 282);
      CITY_HALL = new ItemType("CITY_HALL", 220, 283);
      COURTHOUSE = new ItemType("COURTHOUSE", 221, 284);
      DEFENCE_ESTABLISHMENT = new ItemType("DEFENCE_ESTABLISHMENT", 222, 285);
      EMBASSY = new ItemType("EMBASSY", 223, 286);
      LEGISLATIVE_BUILDING = new ItemType("LEGISLATIVE_BUILDING", 224, 287);
      MUSEUM = new ItemType("MUSEUM", 225, 288);
      MUSIC_VENUE = new ItemType("MUSIC_VENUE", 226, 289);
      PARK = new ItemType("PARK", 227, 290);
      PARKING_FACILITY = new ItemType("PARKING_FACILITY", 228, 291);
      PERFORMING_ARTS_THEATER = new ItemType("PERFORMING_ARTS_THEATER", 229, 292);
      PLACE_OF_WORSHIP = new ItemType("PLACE_OF_WORSHIP", 230, 293);
      BUDDHIST_TEMPLE = new ItemType("BUDDHIST_TEMPLE", 231, 294);
      CATHOLIC_CHURCH = new ItemType("CATHOLIC_CHURCH", 232, 295);
      CHURCH = new ItemType("CHURCH", 233, 296);
      HINDU_TEMPLE = new ItemType("HINDU_TEMPLE", 234, 297);
      MOSQUE = new ItemType("MOSQUE", 235, 298);
      SYNAGOGUE = new ItemType("SYNAGOGUE", 236, 299);
      PLAYGROUND = new ItemType("PLAYGROUND", 237, 300);
      R_V_PARK = new ItemType("R_V_PARK", 238, 301);
      RESIDENCE = new ItemType("RESIDENCE", 239, 209);
      APARTMENT_COMPLEX = new ItemType("APARTMENT_COMPLEX", 240, 210);
      GATED_RESIDENCE_COMMUNITY = new ItemType("GATED_RESIDENCE_COMMUNITY", 241, 211);
      SINGLE_FAMILY_RESIDENCE = new ItemType("SINGLE_FAMILY_RESIDENCE", 242, 212);
      TOURIST_ATTRACTION = new ItemType("TOURIST_ATTRACTION", 243, 213);
      SUBWAY_STATION = new ItemType("SUBWAY_STATION", 244, 305);
      TAXI_STAND = new ItemType("TAXI_STAND", 245, 306);
      TRAIN_STATION = new ItemType("TRAIN_STATION", 246, 307);
      ZOO = new ItemType("ZOO", 247, 308);
      LANDFORM = new ItemType("LANDFORM", 248, 309);
      BODY_OF_WATER = new ItemType("BODY_OF_WATER", 249, 310);
      CANAL = new ItemType("CANAL", 250, 311);
      LAKE_BODY_OF_WATER = new ItemType("LAKE_BODY_OF_WATER", 251, 312);
      OCEAN_BODY_OF_WATER = new ItemType("OCEAN_BODY_OF_WATER", 252, 313);
      POND = new ItemType("POND", 253, 314);
      RESERVOIR = new ItemType("RESERVOIR", 254, 315);
      RIVER_BODY_OF_WATER = new ItemType("RIVER_BODY_OF_WATER", 255, 316);
      SEA_BODY_OF_WATER = new ItemType("SEA_BODY_OF_WATER", 256, 317);
      WATERFALL = new ItemType("WATERFALL", 257, 318);
      CONTINENT = new ItemType("CONTINENT", 258, 319);
      MOUNTAIN = new ItemType("MOUNTAIN", 259, 320);
      VOLCANO = new ItemType("VOLCANO", 260, 321);
      LANDMARKS_OR_HISTORICAL_BUILDINGS = new ItemType("LANDMARKS_OR_HISTORICAL_BUILDINGS", 261, 322);
      USER_INTERACTION = new ItemType("USER_INTERACTION", 262, 220);
      USER_PLUS_ONES = new ItemType("USER_PLUS_ONES", 263, 215);
      ENUMERATION = new ItemType("ENUMERATION", 264, 222);
      BOOK_FORMAT_TYPE = new ItemType("BOOK_FORMAT_TYPE", 265, 223);
      ITEM_AVAILABILITY = new ItemType("ITEM_AVAILABILITY", 266, 224);
      OFFER_ITEM_CONDITION = new ItemType("OFFER_ITEM_CONDITION", 267, 225);
      JOB_POSTING = new ItemType("JOB_POSTING", 268, 226);
      LANGUAGE = new ItemType("LANGUAGE", 269, 227);
      OFFER = new ItemType("OFFER", 270, 228);
      QUANTITY = new ItemType("QUANTITY", 271, 229);
      DISTANCE = new ItemType("DISTANCE", 272, 230);
      DURATION = new ItemType("DURATION", 273, 231);
      ENERGY = new ItemType("ENERGY", 274, 232);
      MASS = new ItemType("MASS", 275, 233);
      RATING = new ItemType("RATING", 276, 234);
      AGGREGATE_RATING = new ItemType("AGGREGATE_RATING", 277, 235);
      STRUCTURED_VALUE = new ItemType("STRUCTURED_VALUE", 278, 236);
      CONTACT_POINT = new ItemType("CONTACT_POINT", 279, 237);
      POSTAL_ADDRESS = new ItemType("POSTAL_ADDRESS", 280, 238);
      GEO_COORDINATES = new ItemType("GEO_COORDINATES", 281, 239);
      GEO_SHAPE = new ItemType("GEO_SHAPE", 282, 240);
      NUTRITION_INFORMATION = new ItemType("NUTRITION_INFORMATION", 283, 241);
      PRESENTATION_OBJECT = new ItemType("PRESENTATION_OBJECT", 284, 216);
      DOCUMENT_OBJECT = new ItemType("DOCUMENT_OBJECT", 285, 217);
      SPREADSHEET_OBJECT = new ItemType("SPREADSHEET_OBJECT", 286, 218);
      FORM_OBJECT = new ItemType("FORM_OBJECT", 287, 242);
      DRAWING_OBJECT = new ItemType("DRAWING_OBJECT", 288, 262);
      PLACE_REVIEW = new ItemType("PLACE_REVIEW", 289, 263);
      FILE_OBJECT = new ItemType("FILE_OBJECT", 290, 265);
      PLAY_MUSIC_TRACK = new ItemType("PLAY_MUSIC_TRACK", 291, 323);
      PLAY_MUSIC_ALBUM = new ItemType("PLAY_MUSIC_ALBUM", 292, 324);
      MAGAZINE = new ItemType("MAGAZINE", 293, 328);
      CAROUSEL_FRAME = new ItemType("CAROUSEL_FRAME", 294, 243);
      PLUS_EVENT = new ItemType("PLUS_EVENT", 295, 244);
      HANGOUT = new ItemType("HANGOUT", 296, 254);
      HANGOUT_BROADCAST = new ItemType("HANGOUT_BROADCAST", 297, 255);
      HANGOUT_CONSUMER = new ItemType("HANGOUT_CONSUMER", 298, 256);
      CHECKIN = new ItemType("CHECKIN", 299, 266);
      EXAMPLE_OBJECT = new ItemType("EXAMPLE_OBJECT", 300, 245);
      SQUARE = new ItemType("SQUARE", 301, 246);
      PLUS_PHOTO = new ItemType("PLUS_PHOTO", 302, 249);
      PLUS_PHOTO_ALBUM = new ItemType("PLUS_PHOTO_ALBUM", 303, 250);
      PRODUCT_REVIEW = new ItemType("PRODUCT_REVIEW", 304, 251);
      FINANCIAL_QUOTE = new ItemType("FINANCIAL_QUOTE", 305, 252);
      TOUR_OBJECT = new ItemType("TOUR_OBJECT", 306, 253);
      PLUS_PAGE = new ItemType("PLUS_PAGE", 307, 327);
      GOOGLE_CHART = new ItemType("GOOGLE_CHART", 308, 258);
      PLUS_PHOTOS_ADDED_TO_COLLECTION = new ItemType("PLUS_PHOTOS_ADDED_TO_COLLECTION", 309, 261);
      RECOMMENDED_PEOPLE = new ItemType("RECOMMENDED_PEOPLE", 310, 326);
      ItemType[] arrayOfItemType = new ItemType[311];
      arrayOfItemType[0] = UNKNOWN;
      arrayOfItemType[1] = THING;
      arrayOfItemType[2] = CREATIVE_WORK;
      arrayOfItemType[3] = EVENT;
      arrayOfItemType[4] = INTANGIBLE;
      arrayOfItemType[5] = ORGANIZATION;
      arrayOfItemType[6] = PERSON;
      arrayOfItemType[7] = PLACE;
      arrayOfItemType[8] = PRODUCT;
      arrayOfItemType[9] = ARTICLE;
      arrayOfItemType[10] = BLOG_POSTING;
      arrayOfItemType[11] = NEWS_ARTICLE;
      arrayOfItemType[12] = SCHOLARLY_ARTICLE;
      arrayOfItemType[13] = BLOG;
      arrayOfItemType[14] = BOOK;
      arrayOfItemType[15] = COMMENT;
      arrayOfItemType[16] = ITEM_LIST;
      arrayOfItemType[17] = MAP;
      arrayOfItemType[18] = MEDIA_OBJECT;
      arrayOfItemType[19] = AUDIO_OBJECT;
      arrayOfItemType[20] = IMAGE_OBJECT;
      arrayOfItemType[21] = MUSIC_VIDEO_OBJECT;
      arrayOfItemType[22] = VIDEO_OBJECT;
      arrayOfItemType[23] = MOVIE;
      arrayOfItemType[24] = MUSIC_PLAYLIST;
      arrayOfItemType[25] = MUSIC_ALBUM;
      arrayOfItemType[26] = MUSIC_RECORDING;
      arrayOfItemType[27] = PAINTING;
      arrayOfItemType[28] = PHOTOGRAPH;
      arrayOfItemType[29] = RECIPE;
      arrayOfItemType[30] = REVIEW;
      arrayOfItemType[31] = SCULPTURE;
      arrayOfItemType[32] = SOFTWARE_APPLICATION;
      arrayOfItemType[33] = MOBILE_APPLICATION;
      arrayOfItemType[34] = WEB_APPLICATION;
      arrayOfItemType[35] = TV_EPISODE;
      arrayOfItemType[36] = TV_SEASON;
      arrayOfItemType[37] = TV_SERIES;
      arrayOfItemType[38] = WEB_PAGE;
      arrayOfItemType[39] = ABOUT_PAGE;
      arrayOfItemType[40] = CHECKOUT_PAGE;
      arrayOfItemType[41] = COLLECTION_PAGE;
      arrayOfItemType[42] = IMAGE_GALLERY;
      arrayOfItemType[43] = VIDEO_GALLERY;
      arrayOfItemType[44] = CONTACT_PAGE;
      arrayOfItemType[45] = ITEM_PAGE;
      arrayOfItemType[46] = PROFILE_PAGE;
      arrayOfItemType[47] = SEARCH_RESULTS_PAGE;
      arrayOfItemType[48] = WEB_PAGE_ELEMENT;
      arrayOfItemType[49] = SITE_NAVIGATION_ELEMENT;
      arrayOfItemType[50] = TABLE;
      arrayOfItemType[51] = WP_AD_BLOCK;
      arrayOfItemType[52] = WP_FOOTER;
      arrayOfItemType[53] = WP_HEADER;
      arrayOfItemType[54] = WP_SIDEBAR;
      arrayOfItemType[55] = BUSINESS_EVENT;
      arrayOfItemType[56] = CHILDRENS_EVENT;
      arrayOfItemType[57] = COMEDY_EVENT;
      arrayOfItemType[58] = DANCE_EVENT;
      arrayOfItemType[59] = EDUCATION_EVENT;
      arrayOfItemType[60] = FESTIVAL;
      arrayOfItemType[61] = FOOD_EVENT;
      arrayOfItemType[62] = LITERARY_EVENT;
      arrayOfItemType[63] = MUSIC_EVENT;
      arrayOfItemType[64] = SALE_EVENT;
      arrayOfItemType[65] = SOCIAL_EVENT;
      arrayOfItemType[66] = SPORTS_EVENT;
      arrayOfItemType[67] = THEATER_EVENT;
      arrayOfItemType[68] = VISUAL_ARTS_EVENT;
      arrayOfItemType[69] = RESERVATION;
      arrayOfItemType[70] = TRAVEL_EVENT;
      arrayOfItemType[71] = CORPORATION;
      arrayOfItemType[72] = EDUCATIONAL_ORGANIZATION;
      arrayOfItemType[73] = COLLEGE_OR_UNIVERSITY;
      arrayOfItemType[74] = ELEMENTARY_SCHOOL;
      arrayOfItemType[75] = HIGH_SCHOOL;
      arrayOfItemType[76] = MIDDLE_SCHOOL;
      arrayOfItemType[77] = PRESCHOOL;
      arrayOfItemType[78] = SCHOOL;
      arrayOfItemType[79] = GOVERNMENT_ORGANIZATION;
      arrayOfItemType[80] = LOCAL_BUSINESS;
      arrayOfItemType[81] = ANIMAL_SHELTER;
      arrayOfItemType[82] = AUTOMOTIVE_BUSINESS;
      arrayOfItemType[83] = AUTO_BODY_SHOP;
      arrayOfItemType[84] = AUTO_DEALER;
      arrayOfItemType[85] = AUTO_PARTS_STORE;
      arrayOfItemType[86] = AUTO_RENTAL;
      arrayOfItemType[87] = AUTO_REPAIR;
      arrayOfItemType[88] = AUTO_WASH;
      arrayOfItemType[89] = GAS_STATION;
      arrayOfItemType[90] = MOTORCYCLE_DEALER;
      arrayOfItemType[91] = MOTORCYCLE_REPAIR;
      arrayOfItemType[92] = CHILD_CARE;
      arrayOfItemType[93] = DRY_CLEANING_OR_LAUNDRY;
      arrayOfItemType[94] = EMERGENCY_SERVICE;
      arrayOfItemType[95] = FIRE_STATION;
      arrayOfItemType[96] = HOSPITAL;
      arrayOfItemType[97] = POLICE_STATION;
      arrayOfItemType[98] = EMPLOYMENT_AGENGY;
      arrayOfItemType[99] = ENTERTAINMENT_BUSINESS;
      arrayOfItemType[100] = ADULT_ENTERTAINMENT;
      arrayOfItemType[101] = AMUSEMENT_PARK;
      arrayOfItemType[102] = ART_GALLERY;
      arrayOfItemType[103] = CASINO;
      arrayOfItemType[104] = COMEDY_CLUB;
      arrayOfItemType[105] = MOVIE_THEATER;
      arrayOfItemType[106] = NIGHT_CLUB;
      arrayOfItemType[107] = FINANCIAL_SERVICE;
      arrayOfItemType[108] = ACCOUNTING_SERVICE;
      arrayOfItemType[109] = AUTOMATED_TELLER;
      arrayOfItemType[110] = BANK_OR_CREDIT_UNION;
      arrayOfItemType[111] = INSURANCE_AGENCY;
      arrayOfItemType[112] = FOOD_ESTABLISHMENT;
      arrayOfItemType[113] = BAKERY;
      arrayOfItemType[114] = BAR_OR_PUB;
      arrayOfItemType[115] = BREWERY;
      arrayOfItemType[116] = CAFE_OR_COFFEE_SHOP;
      arrayOfItemType[117] = FAST_FOOD_RESTAURANT;
      arrayOfItemType[118] = ICE_CREAM_SHOP;
      arrayOfItemType[119] = RESTAURANT;
      arrayOfItemType[120] = WINERY;
      arrayOfItemType[121] = GOVERNMENT_OFFICE;
      arrayOfItemType[122] = POST_OFFICE;
      arrayOfItemType[123] = HEALTH_AND_BEAUTY_BUSINESS;
      arrayOfItemType[124] = BEAUTY_SALON;
      arrayOfItemType[125] = DAY_SPA;
      arrayOfItemType[126] = HAIR_SALON;
      arrayOfItemType[127] = HEALTH_CLUB;
      arrayOfItemType[''] = NAIL_SALON;
      arrayOfItemType[''] = TATTOO_PARLOR;
      arrayOfItemType[''] = HOME_AND_CONSTRUCTION_BUSINESS;
      arrayOfItemType[''] = ELECTRICIAN;
      arrayOfItemType[''] = GENERAL_CONTRACTOR;
      arrayOfItemType[''] = HVAC_BUSINESS;
      arrayOfItemType[''] = HOUSE_PAINTER;
      arrayOfItemType[''] = LOCKSMITH;
      arrayOfItemType[''] = MOVING_COMPANY;
      arrayOfItemType[''] = PLUMBER;
      arrayOfItemType[''] = ROOFING_CONTRACTOR;
      arrayOfItemType[''] = INTERNET_CAFE;
      arrayOfItemType[''] = LIBRARY;
      arrayOfItemType[''] = LODGING_BUSINESS;
      arrayOfItemType[''] = BED_AND_BREAKFAST;
      arrayOfItemType[''] = HOSTEL;
      arrayOfItemType[''] = HOTEL;
      arrayOfItemType[''] = MOTEL;
      arrayOfItemType[''] = MEDICAL_ORGANIZATION;
      arrayOfItemType[''] = DENTIST;
      arrayOfItemType[''] = MEDICAL_CLINIC;
      arrayOfItemType[''] = OPTICIAN;
      arrayOfItemType[''] = PHARMACY;
      arrayOfItemType[''] = PHYSICIAN;
      arrayOfItemType[''] = VETERINARY_CARE;
      arrayOfItemType[''] = PROFESSIONAL_SERVICE;
      arrayOfItemType[''] = ATTORNEY;
      arrayOfItemType[''] = NOTARY;
      arrayOfItemType[''] = RADIO_STATION;
      arrayOfItemType[''] = REAL_ESTATE_AGENT;
      arrayOfItemType[''] = RECYCLING_CENTER;
      arrayOfItemType[''] = SELF_STORAGE;
      arrayOfItemType[' '] = SHOPPING_CENTER;
      arrayOfItemType['¡'] = SPORTS_ACTIVITY_LOCATION;
      arrayOfItemType['¢'] = BOWLING_ALLEY;
      arrayOfItemType['£'] = EXERCISE_GYM;
      arrayOfItemType['¤'] = GOLF_COURSE;
      arrayOfItemType['¥'] = PUBLIC_SWIMMING_POOL;
      arrayOfItemType['¦'] = SKI_RESORT;
      arrayOfItemType['§'] = SPORTS_CLUB;
      arrayOfItemType['¨'] = STADIUM_OR_ARENA;
      arrayOfItemType['©'] = TENNIS_COMPLEX;
      arrayOfItemType['ª'] = STORE;
      arrayOfItemType['«'] = BIKE_STORE;
      arrayOfItemType['¬'] = BOOK_STORE;
      arrayOfItemType['­'] = CLOTHING_STORE;
      arrayOfItemType['®'] = COMPUTER_STORE;
      arrayOfItemType['¯'] = CONVENIENCE_STORE;
      arrayOfItemType['°'] = DEPARTMENT_STORE;
      arrayOfItemType['±'] = ELECTRONICS_STORE;
      arrayOfItemType['²'] = FLORIST;
      arrayOfItemType['³'] = FURNITURE_STORE;
      arrayOfItemType['´'] = GARDEN_STORE;
      arrayOfItemType['µ'] = GROCERY_STORE;
      arrayOfItemType['¶'] = HARDWARE_STORE;
      arrayOfItemType['·'] = HOBBY_SHOP;
      arrayOfItemType['¸'] = HOME_GOODS_STORE;
      arrayOfItemType['¹'] = JEWELRY_STORE;
      arrayOfItemType['º'] = LIQUOR_STORE;
      arrayOfItemType['»'] = MENS_CLOTHING_STORE;
      arrayOfItemType['¼'] = MOBILE_PHONE_STORE;
      arrayOfItemType['½'] = MOVIE_RENTAL_STORE;
      arrayOfItemType['¾'] = MUSIC_STORE;
      arrayOfItemType['¿'] = OFFICE_EQUIPMENT_STORE;
      arrayOfItemType['À'] = OUTLET_STORE;
      arrayOfItemType['Á'] = PAWN_SHOP;
      arrayOfItemType['Â'] = PET_STORE;
      arrayOfItemType['Ã'] = SHOE_STORE;
      arrayOfItemType['Ä'] = SPORTING_GOODS_STORE;
      arrayOfItemType['Å'] = TIRE_SHOP;
      arrayOfItemType['Æ'] = TOY_STORE;
      arrayOfItemType['Ç'] = WHOLESALE_STORE;
      arrayOfItemType['È'] = TELEVISION_STATION;
      arrayOfItemType['É'] = TOURIST_INFORMATION_CENTER;
      arrayOfItemType['Ê'] = TRAVEL_AGENCY;
      arrayOfItemType['Ë'] = PERFORMING_GROUP;
      arrayOfItemType['Ì'] = MUSIC_GROUP;
      arrayOfItemType['Í'] = ADMINISTRATIVE_AREA;
      arrayOfItemType['Î'] = CITY;
      arrayOfItemType['Ï'] = COUNTRY;
      arrayOfItemType['Ð'] = STATE;
      arrayOfItemType['Ñ'] = CIVIC_STRUCTURE;
      arrayOfItemType['Ò'] = AIRPORT;
      arrayOfItemType['Ó'] = AQUARIUM;
      arrayOfItemType['Ô'] = BEACH;
      arrayOfItemType['Õ'] = BUS_STATION;
      arrayOfItemType['Ö'] = BUS_STOP;
      arrayOfItemType['×'] = CAMPGROUND;
      arrayOfItemType['Ø'] = CEMETERY;
      arrayOfItemType['Ù'] = CREMATORIUM;
      arrayOfItemType['Ú'] = EVENT_VENUE;
      arrayOfItemType['Û'] = GOVERNMENT_BUILDING;
      arrayOfItemType['Ü'] = CITY_HALL;
      arrayOfItemType['Ý'] = COURTHOUSE;
      arrayOfItemType['Þ'] = DEFENCE_ESTABLISHMENT;
      arrayOfItemType['ß'] = EMBASSY;
      arrayOfItemType['à'] = LEGISLATIVE_BUILDING;
      arrayOfItemType['á'] = MUSEUM;
      arrayOfItemType['â'] = MUSIC_VENUE;
      arrayOfItemType['ã'] = PARK;
      arrayOfItemType['ä'] = PARKING_FACILITY;
      arrayOfItemType['å'] = PERFORMING_ARTS_THEATER;
      arrayOfItemType['æ'] = PLACE_OF_WORSHIP;
      arrayOfItemType['ç'] = BUDDHIST_TEMPLE;
      arrayOfItemType['è'] = CATHOLIC_CHURCH;
      arrayOfItemType['é'] = CHURCH;
      arrayOfItemType['ê'] = HINDU_TEMPLE;
      arrayOfItemType['ë'] = MOSQUE;
      arrayOfItemType['ì'] = SYNAGOGUE;
      arrayOfItemType['í'] = PLAYGROUND;
      arrayOfItemType['î'] = R_V_PARK;
      arrayOfItemType['ï'] = RESIDENCE;
      arrayOfItemType['ð'] = APARTMENT_COMPLEX;
      arrayOfItemType['ñ'] = GATED_RESIDENCE_COMMUNITY;
      arrayOfItemType['ò'] = SINGLE_FAMILY_RESIDENCE;
      arrayOfItemType['ó'] = TOURIST_ATTRACTION;
      arrayOfItemType['ô'] = SUBWAY_STATION;
      arrayOfItemType['õ'] = TAXI_STAND;
      arrayOfItemType['ö'] = TRAIN_STATION;
      arrayOfItemType['÷'] = ZOO;
      arrayOfItemType['ø'] = LANDFORM;
      arrayOfItemType['ù'] = BODY_OF_WATER;
      arrayOfItemType['ú'] = CANAL;
      arrayOfItemType['û'] = LAKE_BODY_OF_WATER;
      arrayOfItemType['ü'] = OCEAN_BODY_OF_WATER;
      arrayOfItemType['ý'] = POND;
      arrayOfItemType['þ'] = RESERVOIR;
      arrayOfItemType['ÿ'] = RIVER_BODY_OF_WATER;
      arrayOfItemType[256] = SEA_BODY_OF_WATER;
      arrayOfItemType[257] = WATERFALL;
      arrayOfItemType[258] = CONTINENT;
      arrayOfItemType[259] = MOUNTAIN;
      arrayOfItemType[260] = VOLCANO;
      arrayOfItemType[261] = LANDMARKS_OR_HISTORICAL_BUILDINGS;
      arrayOfItemType[262] = USER_INTERACTION;
      arrayOfItemType[263] = USER_PLUS_ONES;
      arrayOfItemType[264] = ENUMERATION;
      arrayOfItemType[265] = BOOK_FORMAT_TYPE;
      arrayOfItemType[266] = ITEM_AVAILABILITY;
      arrayOfItemType[267] = OFFER_ITEM_CONDITION;
      arrayOfItemType[268] = JOB_POSTING;
      arrayOfItemType[269] = LANGUAGE;
      arrayOfItemType[270] = OFFER;
      arrayOfItemType[271] = QUANTITY;
      arrayOfItemType[272] = DISTANCE;
      arrayOfItemType[273] = DURATION;
      arrayOfItemType[274] = ENERGY;
      arrayOfItemType[275] = MASS;
      arrayOfItemType[276] = RATING;
      arrayOfItemType[277] = AGGREGATE_RATING;
      arrayOfItemType[278] = STRUCTURED_VALUE;
      arrayOfItemType[279] = CONTACT_POINT;
      arrayOfItemType[280] = POSTAL_ADDRESS;
      arrayOfItemType[281] = GEO_COORDINATES;
      arrayOfItemType[282] = GEO_SHAPE;
      arrayOfItemType[283] = NUTRITION_INFORMATION;
      arrayOfItemType[284] = PRESENTATION_OBJECT;
      arrayOfItemType[285] = DOCUMENT_OBJECT;
      arrayOfItemType[286] = SPREADSHEET_OBJECT;
      arrayOfItemType[287] = FORM_OBJECT;
      arrayOfItemType[288] = DRAWING_OBJECT;
      arrayOfItemType[289] = PLACE_REVIEW;
      arrayOfItemType[290] = FILE_OBJECT;
      arrayOfItemType[291] = PLAY_MUSIC_TRACK;
      arrayOfItemType[292] = PLAY_MUSIC_ALBUM;
      arrayOfItemType[293] = MAGAZINE;
      arrayOfItemType[294] = CAROUSEL_FRAME;
      arrayOfItemType[295] = PLUS_EVENT;
      arrayOfItemType[296] = HANGOUT;
      arrayOfItemType[297] = HANGOUT_BROADCAST;
      arrayOfItemType[298] = HANGOUT_CONSUMER;
      arrayOfItemType[299] = CHECKIN;
      arrayOfItemType[300] = EXAMPLE_OBJECT;
      arrayOfItemType[301] = SQUARE;
      arrayOfItemType[302] = PLUS_PHOTO;
      arrayOfItemType[303] = PLUS_PHOTO_ALBUM;
      arrayOfItemType[304] = PRODUCT_REVIEW;
      arrayOfItemType[305] = FINANCIAL_QUOTE;
      arrayOfItemType[306] = TOUR_OBJECT;
      arrayOfItemType[307] = PLUS_PAGE;
      arrayOfItemType[308] = GOOGLE_CHART;
      arrayOfItemType[309] = PLUS_PHOTOS_ADDED_TO_COLLECTION;
      arrayOfItemType[310] = RECOMMENDED_PEOPLE;
    }

    private ItemType(int paramInt1, int paramInt2)
    {
      this.value = paramInt1;
    }

    public static ItemType valueOf(int paramInt)
    {
      ItemType localItemType;
      switch (paramInt)
      {
      case 8:
      case 9:
      case 10:
      case 143:
      case 150:
      case 152:
      case 153:
      case 154:
      case 155:
      case 156:
      case 158:
      case 159:
      case 169:
      case 176:
      case 214:
      case 221:
      case 247:
      case 248:
      default:
        localItemType = null;
      case 257:
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 11:
      case 12:
      case 13:
      case 219:
      case 14:
      case 15:
      case 264:
      case 16:
      case 17:
      case 18:
      case 19:
      case 20:
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
      case 29:
      case 30:
      case 31:
      case 302:
      case 303:
      case 304:
      case 32:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      case 46:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
      case 54:
      case 55:
      case 56:
      case 57:
      case 58:
      case 59:
      case 60:
      case 61:
      case 62:
      case 63:
      case 64:
      case 65:
      case 267:
      case 325:
      case 66:
      case 67:
      case 68:
      case 69:
      case 70:
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
      case 76:
      case 77:
      case 78:
      case 79:
      case 80:
      case 81:
      case 82:
      case 83:
      case 84:
      case 85:
      case 86:
      case 87:
      case 88:
      case 89:
      case 90:
      case 91:
      case 92:
      case 93:
      case 94:
      case 95:
      case 96:
      case 97:
      case 98:
      case 99:
      case 100:
      case 101:
      case 102:
      case 103:
      case 104:
      case 105:
      case 106:
      case 107:
      case 108:
      case 109:
      case 110:
      case 111:
      case 112:
      case 113:
      case 114:
      case 115:
      case 116:
      case 117:
      case 118:
      case 119:
      case 120:
      case 121:
      case 122:
      case 123:
      case 124:
      case 125:
      case 126:
      case 127:
      case 128:
      case 129:
      case 130:
      case 131:
      case 132:
      case 133:
      case 134:
      case 135:
      case 136:
      case 137:
      case 138:
      case 139:
      case 140:
      case 141:
      case 142:
      case 144:
      case 145:
      case 146:
      case 147:
      case 148:
      case 149:
      case 151:
      case 157:
      case 160:
      case 161:
      case 162:
      case 163:
      case 164:
      case 165:
      case 166:
      case 167:
      case 168:
      case 170:
      case 171:
      case 172:
      case 173:
      case 174:
      case 175:
      case 177:
      case 178:
      case 179:
      case 180:
      case 181:
      case 182:
      case 183:
      case 184:
      case 185:
      case 186:
      case 187:
      case 188:
      case 189:
      case 190:
      case 191:
      case 192:
      case 193:
      case 194:
      case 195:
      case 196:
      case 197:
      case 198:
      case 199:
      case 200:
      case 201:
      case 202:
      case 203:
      case 204:
      case 205:
      case 206:
      case 207:
      case 208:
      case 259:
      case 260:
      case 268:
      case 269:
      case 270:
      case 271:
      case 272:
      case 273:
      case 274:
      case 275:
      case 276:
      case 277:
      case 278:
      case 279:
      case 280:
      case 281:
      case 282:
      case 283:
      case 284:
      case 285:
      case 286:
      case 287:
      case 288:
      case 289:
      case 290:
      case 291:
      case 292:
      case 293:
      case 294:
      case 295:
      case 296:
      case 297:
      case 298:
      case 299:
      case 300:
      case 301:
      case 209:
      case 210:
      case 211:
      case 212:
      case 213:
      case 305:
      case 306:
      case 307:
      case 308:
      case 309:
      case 310:
      case 311:
      case 312:
      case 313:
      case 314:
      case 315:
      case 316:
      case 317:
      case 318:
      case 319:
      case 320:
      case 321:
      case 322:
      case 220:
      case 215:
      case 222:
      case 223:
      case 224:
      case 225:
      case 226:
      case 227:
      case 228:
      case 229:
      case 230:
      case 231:
      case 232:
      case 233:
      case 234:
      case 235:
      case 236:
      case 237:
      case 238:
      case 239:
      case 240:
      case 241:
      case 216:
      case 217:
      case 218:
      case 242:
      case 262:
      case 263:
      case 265:
      case 323:
      case 324:
      case 328:
      case 243:
      case 244:
      case 254:
      case 255:
      case 256:
      case 266:
      case 245:
      case 246:
      case 249:
      case 250:
      case 251:
      case 252:
      case 253:
      case 327:
      case 258:
      case 261:
      case 326:
      }
      while (true)
      {
        return localItemType;
        localItemType = UNKNOWN;
        continue;
        localItemType = THING;
        continue;
        localItemType = CREATIVE_WORK;
        continue;
        localItemType = EVENT;
        continue;
        localItemType = INTANGIBLE;
        continue;
        localItemType = ORGANIZATION;
        continue;
        localItemType = PERSON;
        continue;
        localItemType = PLACE;
        continue;
        localItemType = PRODUCT;
        continue;
        localItemType = ARTICLE;
        continue;
        localItemType = BLOG_POSTING;
        continue;
        localItemType = NEWS_ARTICLE;
        continue;
        localItemType = SCHOLARLY_ARTICLE;
        continue;
        localItemType = BLOG;
        continue;
        localItemType = BOOK;
        continue;
        localItemType = COMMENT;
        continue;
        localItemType = ITEM_LIST;
        continue;
        localItemType = MAP;
        continue;
        localItemType = MEDIA_OBJECT;
        continue;
        localItemType = AUDIO_OBJECT;
        continue;
        localItemType = IMAGE_OBJECT;
        continue;
        localItemType = MUSIC_VIDEO_OBJECT;
        continue;
        localItemType = VIDEO_OBJECT;
        continue;
        localItemType = MOVIE;
        continue;
        localItemType = MUSIC_PLAYLIST;
        continue;
        localItemType = MUSIC_ALBUM;
        continue;
        localItemType = MUSIC_RECORDING;
        continue;
        localItemType = PAINTING;
        continue;
        localItemType = PHOTOGRAPH;
        continue;
        localItemType = RECIPE;
        continue;
        localItemType = REVIEW;
        continue;
        localItemType = SCULPTURE;
        continue;
        localItemType = SOFTWARE_APPLICATION;
        continue;
        localItemType = MOBILE_APPLICATION;
        continue;
        localItemType = WEB_APPLICATION;
        continue;
        localItemType = TV_EPISODE;
        continue;
        localItemType = TV_SEASON;
        continue;
        localItemType = TV_SERIES;
        continue;
        localItemType = WEB_PAGE;
        continue;
        localItemType = ABOUT_PAGE;
        continue;
        localItemType = CHECKOUT_PAGE;
        continue;
        localItemType = COLLECTION_PAGE;
        continue;
        localItemType = IMAGE_GALLERY;
        continue;
        localItemType = VIDEO_GALLERY;
        continue;
        localItemType = CONTACT_PAGE;
        continue;
        localItemType = ITEM_PAGE;
        continue;
        localItemType = PROFILE_PAGE;
        continue;
        localItemType = SEARCH_RESULTS_PAGE;
        continue;
        localItemType = WEB_PAGE_ELEMENT;
        continue;
        localItemType = SITE_NAVIGATION_ELEMENT;
        continue;
        localItemType = TABLE;
        continue;
        localItemType = WP_AD_BLOCK;
        continue;
        localItemType = WP_FOOTER;
        continue;
        localItemType = WP_HEADER;
        continue;
        localItemType = WP_SIDEBAR;
        continue;
        localItemType = BUSINESS_EVENT;
        continue;
        localItemType = CHILDRENS_EVENT;
        continue;
        localItemType = COMEDY_EVENT;
        continue;
        localItemType = DANCE_EVENT;
        continue;
        localItemType = EDUCATION_EVENT;
        continue;
        localItemType = FESTIVAL;
        continue;
        localItemType = FOOD_EVENT;
        continue;
        localItemType = LITERARY_EVENT;
        continue;
        localItemType = MUSIC_EVENT;
        continue;
        localItemType = SALE_EVENT;
        continue;
        localItemType = SOCIAL_EVENT;
        continue;
        localItemType = SPORTS_EVENT;
        continue;
        localItemType = THEATER_EVENT;
        continue;
        localItemType = VISUAL_ARTS_EVENT;
        continue;
        localItemType = RESERVATION;
        continue;
        localItemType = TRAVEL_EVENT;
        continue;
        localItemType = CORPORATION;
        continue;
        localItemType = EDUCATIONAL_ORGANIZATION;
        continue;
        localItemType = COLLEGE_OR_UNIVERSITY;
        continue;
        localItemType = ELEMENTARY_SCHOOL;
        continue;
        localItemType = HIGH_SCHOOL;
        continue;
        localItemType = MIDDLE_SCHOOL;
        continue;
        localItemType = PRESCHOOL;
        continue;
        localItemType = SCHOOL;
        continue;
        localItemType = GOVERNMENT_ORGANIZATION;
        continue;
        localItemType = LOCAL_BUSINESS;
        continue;
        localItemType = ANIMAL_SHELTER;
        continue;
        localItemType = AUTOMOTIVE_BUSINESS;
        continue;
        localItemType = AUTO_BODY_SHOP;
        continue;
        localItemType = AUTO_DEALER;
        continue;
        localItemType = AUTO_PARTS_STORE;
        continue;
        localItemType = AUTO_RENTAL;
        continue;
        localItemType = AUTO_REPAIR;
        continue;
        localItemType = AUTO_WASH;
        continue;
        localItemType = GAS_STATION;
        continue;
        localItemType = MOTORCYCLE_DEALER;
        continue;
        localItemType = MOTORCYCLE_REPAIR;
        continue;
        localItemType = CHILD_CARE;
        continue;
        localItemType = DRY_CLEANING_OR_LAUNDRY;
        continue;
        localItemType = EMERGENCY_SERVICE;
        continue;
        localItemType = FIRE_STATION;
        continue;
        localItemType = HOSPITAL;
        continue;
        localItemType = POLICE_STATION;
        continue;
        localItemType = EMPLOYMENT_AGENGY;
        continue;
        localItemType = ENTERTAINMENT_BUSINESS;
        continue;
        localItemType = ADULT_ENTERTAINMENT;
        continue;
        localItemType = AMUSEMENT_PARK;
        continue;
        localItemType = ART_GALLERY;
        continue;
        localItemType = CASINO;
        continue;
        localItemType = COMEDY_CLUB;
        continue;
        localItemType = MOVIE_THEATER;
        continue;
        localItemType = NIGHT_CLUB;
        continue;
        localItemType = FINANCIAL_SERVICE;
        continue;
        localItemType = ACCOUNTING_SERVICE;
        continue;
        localItemType = AUTOMATED_TELLER;
        continue;
        localItemType = BANK_OR_CREDIT_UNION;
        continue;
        localItemType = INSURANCE_AGENCY;
        continue;
        localItemType = FOOD_ESTABLISHMENT;
        continue;
        localItemType = BAKERY;
        continue;
        localItemType = BAR_OR_PUB;
        continue;
        localItemType = BREWERY;
        continue;
        localItemType = CAFE_OR_COFFEE_SHOP;
        continue;
        localItemType = FAST_FOOD_RESTAURANT;
        continue;
        localItemType = ICE_CREAM_SHOP;
        continue;
        localItemType = RESTAURANT;
        continue;
        localItemType = WINERY;
        continue;
        localItemType = GOVERNMENT_OFFICE;
        continue;
        localItemType = POST_OFFICE;
        continue;
        localItemType = HEALTH_AND_BEAUTY_BUSINESS;
        continue;
        localItemType = BEAUTY_SALON;
        continue;
        localItemType = DAY_SPA;
        continue;
        localItemType = HAIR_SALON;
        continue;
        localItemType = HEALTH_CLUB;
        continue;
        localItemType = NAIL_SALON;
        continue;
        localItemType = TATTOO_PARLOR;
        continue;
        localItemType = HOME_AND_CONSTRUCTION_BUSINESS;
        continue;
        localItemType = ELECTRICIAN;
        continue;
        localItemType = GENERAL_CONTRACTOR;
        continue;
        localItemType = HVAC_BUSINESS;
        continue;
        localItemType = HOUSE_PAINTER;
        continue;
        localItemType = LOCKSMITH;
        continue;
        localItemType = MOVING_COMPANY;
        continue;
        localItemType = PLUMBER;
        continue;
        localItemType = ROOFING_CONTRACTOR;
        continue;
        localItemType = INTERNET_CAFE;
        continue;
        localItemType = LIBRARY;
        continue;
        localItemType = LODGING_BUSINESS;
        continue;
        localItemType = BED_AND_BREAKFAST;
        continue;
        localItemType = HOSTEL;
        continue;
        localItemType = HOTEL;
        continue;
        localItemType = MOTEL;
        continue;
        localItemType = MEDICAL_ORGANIZATION;
        continue;
        localItemType = DENTIST;
        continue;
        localItemType = MEDICAL_CLINIC;
        continue;
        localItemType = OPTICIAN;
        continue;
        localItemType = PHARMACY;
        continue;
        localItemType = PHYSICIAN;
        continue;
        localItemType = VETERINARY_CARE;
        continue;
        localItemType = PROFESSIONAL_SERVICE;
        continue;
        localItemType = ATTORNEY;
        continue;
        localItemType = NOTARY;
        continue;
        localItemType = RADIO_STATION;
        continue;
        localItemType = REAL_ESTATE_AGENT;
        continue;
        localItemType = RECYCLING_CENTER;
        continue;
        localItemType = SELF_STORAGE;
        continue;
        localItemType = SHOPPING_CENTER;
        continue;
        localItemType = SPORTS_ACTIVITY_LOCATION;
        continue;
        localItemType = BOWLING_ALLEY;
        continue;
        localItemType = EXERCISE_GYM;
        continue;
        localItemType = GOLF_COURSE;
        continue;
        localItemType = PUBLIC_SWIMMING_POOL;
        continue;
        localItemType = SKI_RESORT;
        continue;
        localItemType = SPORTS_CLUB;
        continue;
        localItemType = STADIUM_OR_ARENA;
        continue;
        localItemType = TENNIS_COMPLEX;
        continue;
        localItemType = STORE;
        continue;
        localItemType = BIKE_STORE;
        continue;
        localItemType = BOOK_STORE;
        continue;
        localItemType = CLOTHING_STORE;
        continue;
        localItemType = COMPUTER_STORE;
        continue;
        localItemType = CONVENIENCE_STORE;
        continue;
        localItemType = DEPARTMENT_STORE;
        continue;
        localItemType = ELECTRONICS_STORE;
        continue;
        localItemType = FLORIST;
        continue;
        localItemType = FURNITURE_STORE;
        continue;
        localItemType = GARDEN_STORE;
        continue;
        localItemType = GROCERY_STORE;
        continue;
        localItemType = HARDWARE_STORE;
        continue;
        localItemType = HOBBY_SHOP;
        continue;
        localItemType = HOME_GOODS_STORE;
        continue;
        localItemType = JEWELRY_STORE;
        continue;
        localItemType = LIQUOR_STORE;
        continue;
        localItemType = MENS_CLOTHING_STORE;
        continue;
        localItemType = MOBILE_PHONE_STORE;
        continue;
        localItemType = MOVIE_RENTAL_STORE;
        continue;
        localItemType = MUSIC_STORE;
        continue;
        localItemType = OFFICE_EQUIPMENT_STORE;
        continue;
        localItemType = OUTLET_STORE;
        continue;
        localItemType = PAWN_SHOP;
        continue;
        localItemType = PET_STORE;
        continue;
        localItemType = SHOE_STORE;
        continue;
        localItemType = SPORTING_GOODS_STORE;
        continue;
        localItemType = TIRE_SHOP;
        continue;
        localItemType = TOY_STORE;
        continue;
        localItemType = WHOLESALE_STORE;
        continue;
        localItemType = TELEVISION_STATION;
        continue;
        localItemType = TOURIST_INFORMATION_CENTER;
        continue;
        localItemType = TRAVEL_AGENCY;
        continue;
        localItemType = PERFORMING_GROUP;
        continue;
        localItemType = MUSIC_GROUP;
        continue;
        localItemType = ADMINISTRATIVE_AREA;
        continue;
        localItemType = CITY;
        continue;
        localItemType = COUNTRY;
        continue;
        localItemType = STATE;
        continue;
        localItemType = CIVIC_STRUCTURE;
        continue;
        localItemType = AIRPORT;
        continue;
        localItemType = AQUARIUM;
        continue;
        localItemType = BEACH;
        continue;
        localItemType = BUS_STATION;
        continue;
        localItemType = BUS_STOP;
        continue;
        localItemType = CAMPGROUND;
        continue;
        localItemType = CEMETERY;
        continue;
        localItemType = CREMATORIUM;
        continue;
        localItemType = EVENT_VENUE;
        continue;
        localItemType = GOVERNMENT_BUILDING;
        continue;
        localItemType = CITY_HALL;
        continue;
        localItemType = COURTHOUSE;
        continue;
        localItemType = DEFENCE_ESTABLISHMENT;
        continue;
        localItemType = EMBASSY;
        continue;
        localItemType = LEGISLATIVE_BUILDING;
        continue;
        localItemType = MUSEUM;
        continue;
        localItemType = MUSIC_VENUE;
        continue;
        localItemType = PARK;
        continue;
        localItemType = PARKING_FACILITY;
        continue;
        localItemType = PERFORMING_ARTS_THEATER;
        continue;
        localItemType = PLACE_OF_WORSHIP;
        continue;
        localItemType = BUDDHIST_TEMPLE;
        continue;
        localItemType = CATHOLIC_CHURCH;
        continue;
        localItemType = CHURCH;
        continue;
        localItemType = HINDU_TEMPLE;
        continue;
        localItemType = MOSQUE;
        continue;
        localItemType = SYNAGOGUE;
        continue;
        localItemType = PLAYGROUND;
        continue;
        localItemType = R_V_PARK;
        continue;
        localItemType = RESIDENCE;
        continue;
        localItemType = APARTMENT_COMPLEX;
        continue;
        localItemType = GATED_RESIDENCE_COMMUNITY;
        continue;
        localItemType = SINGLE_FAMILY_RESIDENCE;
        continue;
        localItemType = TOURIST_ATTRACTION;
        continue;
        localItemType = SUBWAY_STATION;
        continue;
        localItemType = TAXI_STAND;
        continue;
        localItemType = TRAIN_STATION;
        continue;
        localItemType = ZOO;
        continue;
        localItemType = LANDFORM;
        continue;
        localItemType = BODY_OF_WATER;
        continue;
        localItemType = CANAL;
        continue;
        localItemType = LAKE_BODY_OF_WATER;
        continue;
        localItemType = OCEAN_BODY_OF_WATER;
        continue;
        localItemType = POND;
        continue;
        localItemType = RESERVOIR;
        continue;
        localItemType = RIVER_BODY_OF_WATER;
        continue;
        localItemType = SEA_BODY_OF_WATER;
        continue;
        localItemType = WATERFALL;
        continue;
        localItemType = CONTINENT;
        continue;
        localItemType = MOUNTAIN;
        continue;
        localItemType = VOLCANO;
        continue;
        localItemType = LANDMARKS_OR_HISTORICAL_BUILDINGS;
        continue;
        localItemType = USER_INTERACTION;
        continue;
        localItemType = USER_PLUS_ONES;
        continue;
        localItemType = ENUMERATION;
        continue;
        localItemType = BOOK_FORMAT_TYPE;
        continue;
        localItemType = ITEM_AVAILABILITY;
        continue;
        localItemType = OFFER_ITEM_CONDITION;
        continue;
        localItemType = JOB_POSTING;
        continue;
        localItemType = LANGUAGE;
        continue;
        localItemType = OFFER;
        continue;
        localItemType = QUANTITY;
        continue;
        localItemType = DISTANCE;
        continue;
        localItemType = DURATION;
        continue;
        localItemType = ENERGY;
        continue;
        localItemType = MASS;
        continue;
        localItemType = RATING;
        continue;
        localItemType = AGGREGATE_RATING;
        continue;
        localItemType = STRUCTURED_VALUE;
        continue;
        localItemType = CONTACT_POINT;
        continue;
        localItemType = POSTAL_ADDRESS;
        continue;
        localItemType = GEO_COORDINATES;
        continue;
        localItemType = GEO_SHAPE;
        continue;
        localItemType = NUTRITION_INFORMATION;
        continue;
        localItemType = PRESENTATION_OBJECT;
        continue;
        localItemType = DOCUMENT_OBJECT;
        continue;
        localItemType = SPREADSHEET_OBJECT;
        continue;
        localItemType = FORM_OBJECT;
        continue;
        localItemType = DRAWING_OBJECT;
        continue;
        localItemType = PLACE_REVIEW;
        continue;
        localItemType = FILE_OBJECT;
        continue;
        localItemType = PLAY_MUSIC_TRACK;
        continue;
        localItemType = PLAY_MUSIC_ALBUM;
        continue;
        localItemType = MAGAZINE;
        continue;
        localItemType = CAROUSEL_FRAME;
        continue;
        localItemType = PLUS_EVENT;
        continue;
        localItemType = HANGOUT;
        continue;
        localItemType = HANGOUT_BROADCAST;
        continue;
        localItemType = HANGOUT_CONSUMER;
        continue;
        localItemType = CHECKIN;
        continue;
        localItemType = EXAMPLE_OBJECT;
        continue;
        localItemType = SQUARE;
        continue;
        localItemType = PLUS_PHOTO;
        continue;
        localItemType = PLUS_PHOTO_ALBUM;
        continue;
        localItemType = PRODUCT_REVIEW;
        continue;
        localItemType = FINANCIAL_QUOTE;
        continue;
        localItemType = TOUR_OBJECT;
        continue;
        localItemType = PLUS_PAGE;
        continue;
        localItemType = GOOGLE_CHART;
        continue;
        localItemType = PLUS_PHOTOS_ADDED_TO_COLLECTION;
        continue;
        localItemType = RECOMMENDED_PEOPLE;
      }
    }

    public final int getNumber()
    {
      return this.value;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.protos.embed.ItemTypes
 * JD-Core Version:    0.6.2
 */