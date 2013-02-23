package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class BookJson extends EsJson<Book>
{
  static final BookJson INSTANCE = new BookJson();

  private BookJson()
  {
    super(Book.class, new Object[] { EmbedClientItemJson.class, "about", AggregateRatingJson.class, "aggregateRating", EmbedsPersonJson.class, "author", "buttonStyle", "description", "imageUrl", "logoHrefUrl", "logoImageUrl", "name", OfferJson.class, "offers", "text", "thumbnailUrl", "titleIconUrl", "url" });
  }

  public static BookJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.BookJson
 * JD-Core Version:    0.6.2
 */