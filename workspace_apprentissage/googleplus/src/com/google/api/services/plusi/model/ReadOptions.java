package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ReadOptions extends GenericJson
{
  public ReadOptionsCommentsOptions commentsOptions;
  public ReadOptionsUpdateOptions eventUpdateOptions;
  public ReadOptionsFramesOptions framesOptions;
  public Boolean includePhotoContributors;
  public Boolean includePlusEvent;
  public ReadOptionsInviteeOptions inviteeOptions;
  public String optionsCriteria;
  public List<ReadOptionsPhotosOptions> photosOptions;
  public Boolean resolvePersons;
  public String responseFormat;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReadOptions
 * JD-Core Version:    0.6.2
 */