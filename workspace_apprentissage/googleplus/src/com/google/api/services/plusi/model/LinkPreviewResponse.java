package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class LinkPreviewResponse extends GenericJson
{
  public TraceRecords backendTrace;
  public List<String> blackboxPreviewData;
  public List<EmbedClientItem> embedItem;
  public List<String> image;
  public List<MediaLayout> mediaLayout;
  public List<String> selectedImage;
  public Boolean succeeded;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LinkPreviewResponse
 * JD-Core Version:    0.6.2
 */