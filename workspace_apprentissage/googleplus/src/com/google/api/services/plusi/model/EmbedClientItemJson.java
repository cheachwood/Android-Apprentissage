package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EmbedClientItemJson extends EsJson<EmbedClientItem>
{
  static final EmbedClientItemJson INSTANCE = new EmbedClientItemJson();

  private EmbedClientItemJson()
  {
    super(EmbedClientItem.class, new Object[] { AggregateRatingJson.class, "aggregateRating", AppInviteJson.class, "appInvite", AudioObjectJson.class, "audioObject", BlogJson.class, "blog", BlogPostingJson.class, "blogPosting", BookJson.class, "book", "canonicalId", CheckinJson.class, "checkin", EmbedsCommentJson.class, "comment", CreativeWorkJson.class, "creativeWork", DeepLinkDataJson.class, "deepLinkData", DocumentObjectJson.class, "documentObject", DrawingObjectJson.class, "drawingObject", EmotishareJson.class, "emotishare", ExampleObjectJson.class, "exampleObject", FileObjectJson.class, "fileObject", FinancialQuoteJson.class, "financialQuote", FormObjectJson.class, "formObject", GeoCoordinatesJson.class, "geoCoordinates", GoogleChartJson.class, "googleChart", HangoutBroadcastJson.class, "hangoutBroadcast", HangoutConsumerJson.class, "hangoutConsumer", "id", ImageGalleryJson.class, "imageGallery", ImageObjectJson.class, "imageObject", LocalBusinessJson.class, "localBusiness", LocalPlusPhotoAlbumJson.class, "localPlusPhotoAlbum", MagazineJson.class, "magazine", MobileApplicationJson.class, "mobileApplication", MovieJson.class, "movie", MusicAlbumJson.class, "musicAlbum", MusicGroupJson.class, "musicGroup", MusicRecordingJson.class, "musicRecording", OfferJson.class, "offer", OrganizationJson.class, "organization", EmbedsPersonJson.class, "person", PlaceJson.class, "place", PlaceReviewJson.class, "placeReview", PlayMusicAlbumJson.class, "playMusicAlbum", PlayMusicTrackJson.class, "playMusicTrack", PlusEventJson.class, "plusEvent", PlusPageJson.class, "plusPage", PlusPhotoJson.class, "plusPhoto", PlusPhotoAlbumJson.class, "plusPhotoAlbum", PlusPhotosAddedToCollectionJson.class, "plusPhotosAddedToCollection", PlusPostJson.class, "plusPost", EmbedsPostalAddressJson.class, "postalAddress", PresentationObjectJson.class, "presentationObject", ProductReviewJson.class, "productReview", RatingJson.class, "rating", RecommendedPeopleJson.class, "recommendedPeople", ReservationJson.class, "reservation", ReviewJson.class, "review", SpreadsheetObjectJson.class, "spreadsheetObject", EmbedsSquareJson.class, "square", SquareInviteJson.class, "squareInvite", ThingJson.class, "thing", TourObjectJson.class, "tourObject", TransientDataJson.class, "transientData", TravelEventJson.class, "travelEvent", TvEpisodeJson.class, "tvEpisode", TvSeriesJson.class, "tvSeries", "type", VideoObjectJson.class, "videoObject", VideoObjectDummyV2Json.class, "videoObjectDummyV2", WebPageJson.class, "webPage" });
  }

  public static EmbedClientItemJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EmbedClientItemJson
 * JD-Core Version:    0.6.2
 */