package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EmbedsCommentJson extends EsJson<EmbedsComment>
{
  static final EmbedsCommentJson INSTANCE = new EmbedsCommentJson();

  private EmbedsCommentJson()
  {
    super(EmbedsComment.class, new Object[] { EmbedClientItemJson.class, "about", EmbedClientItemJson.class, "author", "dateCreated", "dateModified", "description", "id", "imageUrl", "name", "text", "url" });
  }

  public static EmbedsCommentJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EmbedsCommentJson
 * JD-Core Version:    0.6.2
 */