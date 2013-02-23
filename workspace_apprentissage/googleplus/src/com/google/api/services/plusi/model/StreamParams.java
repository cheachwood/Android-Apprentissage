package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class StreamParams extends GenericJson
{
  public Long activitiesOldestTimestampUsec;
  public String channelOrigin;
  public String collapserType;
  public String contentFormat;
  public Boolean disableAbuseFiltering;
  public Boolean expandSharebox;
  public String fieldInclusion;
  public FieldRequestOptions fieldRequestOptions;
  public List<StreamParamsFilter> filters;
  public String focusGroupId;
  public String gaiaGroupOid;
  public String initialOperation;
  public List<Interest> interest;
  public InterestParams interestParams;
  public Integer maxComments;
  public Integer maxNumImages;
  public Integer maxNumUpdates;
  public String openSocialDomain;
  public String perspectiveUserId;
  public ProductionStreamParams productionParams;
  public String productionStreamOid;
  public SearchQuery searchQuery;
  public Boolean skipCommentCollapsing;
  public String sort;
  public String squareStreamId;
  public StreamItemFilter streamItemFilter;
  public UpdateFilter updateFilter;
  public UpdateMixinFilter updateMixinFilter;
  public String viewType;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.StreamParams
 * JD-Core Version:    0.6.2
 */