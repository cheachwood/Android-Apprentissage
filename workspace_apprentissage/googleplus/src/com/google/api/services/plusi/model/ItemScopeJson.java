package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ItemScopeJson extends EsJson<ItemScope>
{
  static final ItemScopeJson INSTANCE = new ItemScopeJson();

  private ItemScopeJson()
  {
    super(ItemScope.class, new Object[] { ItemScopeJson.class, "about", ItemScopeJson.class, "actor", "additionalName", ItemScopeJson.class, "address", "addressCountry", "addressLocality", "addressRegion", ItemScopeJson.class, "aggregateRating", ItemScopeJson.class, "albums", ItemScopeJson.class, "associatedMedia", "attendeeCount", ItemScopeJson.class, "attendees", ItemScopeJson.class, "audio", ItemScopeJson.class, "author", "bestRating", "birthDate", ItemScopeJson.class, "byArtist", "canonicalFountainStream", "caption", ItemScopeJson.class, "contentLocation", "contentSize", "contentUrl", ItemScopeJson.class, "contributor", "dateCreated", "dateModified", "datePublished", "description", ItemScopeJson.class, "director", "duration", "email", EmbedClientItemJson.class, "embedClientItem", "embedUrl", "encodedCustomProto", "endDate", ItemScopeExtensionJson.class, "extension", "familyName", "faviconUrl", "gender", ItemScopeJson.class, "geo", "givenName", "height", "id", "imageUrl", ItemScopeJson.class, "inAlbum", "inboxFountainStream", "isFamilyFriendly", ItemScopeJson.class, "itemReviewed", "itemtype", "latitude", ItemScopeJson.class, "location", "longitude", "mapUrl", "name", "numTracks", ItemScopeJson.class, "offers", "ownerObfuscatedId", ItemScopeJson.class, "partOfTvSeries", ItemScopeJson.class, "performers", "playerType", "postOfficeBoxNumber", "postalCode", "postmodFountainStream", "premodFountainStream", "price", "priceCurrency", ItemScopeJson.class, "producer", "ratingCount", "ratingValue", "reviewBody", ItemScopeJson.class, "reviewRating", "startDate", "streetAddress", "telephone", "text", ItemScopeJson.class, "thumbnail", "thumbnailUrl", "tickerSymbol", ItemScopeJson.class, "tracks", "type", "unfilteredFountainStream", "url", ItemScopeJson.class, "video", "width", "worstRating" });
  }

  public static ItemScopeJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ItemScopeJson
 * JD-Core Version:    0.6.2
 */