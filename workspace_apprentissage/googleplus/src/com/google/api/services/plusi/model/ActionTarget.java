package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.math.BigInteger;
import java.util.List;

public final class ActionTarget extends GenericJson
{
  public String actionSource;
  public ActivityDetails activityDetails;
  public String activityId;
  public LoggedAutoComplete autoComplete;
  public LoggedBillboardImpression billboardImpression;
  public LoggedBillboardPromoAction billboardPromoAction;
  public Integer categoryId;
  public List<LoggedCircle> circle;
  public List<LoggedCircleMember> circleMember;
  public String commentId;
  public Integer connectSiteId;
  public List<Long> deprecatedCircleId;
  public List<String> deprecatedSettingsNotificationType;
  public Integer entityTypeId;
  public String externalUrl;
  public String featureHintType;
  public LoggedFrame frame;
  public BigInteger gadgetId;
  public String gadgetPlayId;
  public List<BigInteger> gaiaId;
  public LoggedIntrCelebsClick intrCelebsClick;
  public Long iphFlowId;
  public String iphStepId;
  public Boolean isUnreadNotification;
  public BigInteger labelId;
  public LoggedLocalWriteReviewInfo localWriteReviewInfo;
  public String notificationId;
  public Integer notificationSlot;
  public List<NotificationTypes> notificationTypes;
  public String notificationWidgetPostReloadBuildLabel;
  public String notificationWidgetPreReloadBuildLabel;
  public Integer notificationWidgetUpTimeBeforeReload;
  public Integer numUnreadNotifications;
  public Integer page;
  public String photoAlbumId;
  public BigInteger photoAlbumIdDeprecated;
  public Integer photoAlbumType;
  public Integer photoCount;
  public BigInteger photoId;
  public Integer photoIndexInPost;
  public String plusEventId;
  public Integer previousNumUnreadNotifications;
  public String profileData;
  public Integer promoType;
  public PromotedYMLImpression promotedYmlInfo;
  public String questionsOneboxQuery;
  public String region;
  public String reviewId;
  public LoggedRhsComponent rhsComponent;
  public LoggedRibbonClick ribbonClick;
  public LoggedRibbonOrder ribbonOrder;
  public List<SettingsNotificationType> settingsNotificationType;
  public LoggedShareboxInfo shareboxInfo;
  public String shortcutTask;
  public SocialadsContext socialadsContext;
  public LoggedSquare square;
  public List<LoggedSuggestionInfo> suggestionInfo;
  public LoggedSuggestionSummaryInfo suggestionSummary;
  public Integer tab;
  public Integer updateStreamPosition;
  public VolumeChange volumeChange;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ActionTarget
 * JD-Core Version:    0.6.2
 */