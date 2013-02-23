package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class EditActivityRequest extends GenericJson
{
  public String annotation;
  public ApiaryFields commonFields;
  public String contentFormat;
  public Boolean deleteLocation;
  public EditSegments editSegments;
  public List<EmbedClientItem> embed;
  public Boolean enableTracing;
  public String externalId;
  public Boolean isReshare;
  public String mediaJson;
  public Boolean preserveExistingAttachment;
  public String renderContextLocation;
  public String updateText;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EditActivityRequest
 * JD-Core Version:    0.6.2
 */