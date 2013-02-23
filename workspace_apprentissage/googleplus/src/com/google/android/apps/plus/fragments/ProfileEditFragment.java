package com.google.android.apps.plus.fragments;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.CircleData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.PrimitiveUtils;
import com.google.android.apps.plus.util.SoftInput;
import com.google.android.apps.plus.views.ImageTextButton;
import com.google.android.apps.plus.views.TextOnlyAudienceView;
import com.google.android.apps.plus.views.TextOnlyAudienceView.ChevronDirection;
import com.google.api.services.plusi.model.CoarseDate;
import com.google.api.services.plusi.model.DataCircleMemberId;
import com.google.api.services.plusi.model.DateInfo;
import com.google.api.services.plusi.model.Education;
import com.google.api.services.plusi.model.Educations;
import com.google.api.services.plusi.model.EducationsJson;
import com.google.api.services.plusi.model.Employment;
import com.google.api.services.plusi.model.Employments;
import com.google.api.services.plusi.model.EmploymentsJson;
import com.google.api.services.plusi.model.Locations;
import com.google.api.services.plusi.model.LocationsJson;
import com.google.api.services.plusi.model.Metadata;
import com.google.api.services.plusi.model.SharingRoster;
import com.google.api.services.plusi.model.SharingRosterData;
import com.google.api.services.plusi.model.SharingRosterDataJson;
import com.google.api.services.plusi.model.SharingTarget;
import com.google.api.services.plusi.model.SharingTargetId;
import com.google.api.services.plusi.model.SharingTargetIdJson;
import com.google.api.services.plusi.model.SimpleProfile;
import com.google.api.services.plusi.model.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ProfileEditFragment extends Fragment
  implements View.OnClickListener
{
  private EsAccount mAccount;
  TextView mAddItemView;
  private AudienceData mAudience;
  TextOnlyAudienceView mAudienceView;
  View mDeletedFieldView;
  private String mDomainId;
  private String mDomainName;
  private int mEditMode;
  View mFocusOverrideView;
  private boolean mHasPublicCircle;
  private ArrayList<ItemViewIds> mItemViewIdsList;
  LinearLayout mItemViews;
  private String mItemsJson;
  private HashSet<View> mModifiedViews = new HashSet();
  private AudienceData mOriginalAudience;
  private int mOriginalCount;
  private Educations mOriginalEducations;
  private Employments mOriginalEmployments;
  private String mOriginalItemsJson;
  private Locations mOriginalLocations;
  private String mOriginalRequiredScopeId;
  private final EsServiceListener mProfileEditServiceListener = new EsServiceListener()
  {
    public final void onMutateProfileComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      if ((ProfileEditFragment.this.mProfilePendingRequestId == null) || (ProfileEditFragment.this.mProfilePendingRequestId.intValue() != paramAnonymousInt));
      while (true)
      {
        return;
        ProfileEditFragment.this.dismissProgressDialog();
        boolean bool = ProfileEditFragment.this.showErrorToast(paramAnonymousServiceResult);
        ProfileEditFragment.access$002(ProfileEditFragment.this, null);
        if (!bool)
          ProfileEditFragment.this.finishActivity(-1);
      }
    }
  };
  private Integer mProfilePendingRequestId;
  ImageTextButton mSaveButton;
  ScrollView mScollView;
  private SharingRosterData mSharingRosterData;
  private String mSharingRosterDataJson;
  private int mViewIdNextCurrent = 5000;
  private int mViewIdNextEndDate = 4000;
  private int mViewIdNextName = 1000;
  private int mViewIdNextStartDate = 3000;
  private int mViewIdNextTitleOrMajor = 2000;

  private void addChangedField(View paramView)
  {
    this.mModifiedViews.add(paramView);
    int i;
    int j;
    if ((this.mOriginalCount == 0) && (this.mItemViews.getChildCount() == 0))
    {
      i = 1;
      if ((this.mModifiedViews.size() != 1) || (paramView != this.mAudienceView))
        break label65;
      j = 1;
      label50: if ((j == 0) || (i == 0))
        break label71;
    }
    while (true)
    {
      return;
      i = 0;
      break;
      label65: j = 0;
      break label50;
      label71: this.mSaveButton.setEnabled(true);
    }
  }

  private View addItem()
  {
    int i = this.mEditMode;
    View localView = null;
    switch (i)
    {
    default:
      return localView;
    case 1:
      localView = getView(null, null);
    case 2:
    case 3:
    }
    while (true)
    {
      this.mItemViews.addView(localView);
      updateViewsWithOriginalValues();
      break;
      localView = getView(null, null);
      continue;
      localView = getView(null, null, null);
    }
  }

  private void configureDataInfo(View paramView, ItemViewIds paramItemViewIds, DateInfo paramDateInfo)
  {
    EditText localEditText1 = (EditText)paramView.findViewById(R.id.start);
    localEditText1.setId(paramItemViewIds.startDate);
    if ((paramDateInfo != null) && (paramDateInfo.start != null) && (paramDateInfo.start.year != null))
      localEditText1.setText(Integer.toString(paramDateInfo.start.year.intValue()));
    EditText localEditText2 = (EditText)paramView.findViewById(R.id.end);
    localEditText2.setId(paramItemViewIds.endDate);
    if ((paramDateInfo != null) && (paramDateInfo.end != null) && (paramDateInfo.end.year != null))
      localEditText2.setText(Integer.toString(paramDateInfo.end.year.intValue()));
    CheckBox localCheckBox = (CheckBox)paramView.findViewById(R.id.current);
    localCheckBox.setId(paramItemViewIds.current);
    localCheckBox.setTag(localEditText2);
    if (paramDateInfo != null)
      localCheckBox.setChecked(PrimitiveUtils.safeBoolean(paramDateInfo.current));
    ImageView localImageView = (ImageView)paramView.findViewById(R.id.delete_item);
    localImageView.setOnClickListener(this);
    localImageView.setTag(paramView);
  }

  private Educations createEducations()
  {
    Educations localEducations = new Educations();
    int i = this.mItemViews.getChildCount();
    localEducations.education = new ArrayList(i);
    for (int j = 0; j < i; j++)
    {
      View localView = this.mItemViews.getChildAt(j);
      ItemViewIds localItemViewIds = (ItemViewIds)localView.getTag();
      Education localEducation = new Education();
      localEducation.school = getEditedString(localView, localItemViewIds.name);
      localEducation.majorConcentration = getEditedString(localView, localItemViewIds.titleOrMajor);
      localEducation.dateInfo = new DateInfo();
      String str1 = getEditedString(localView, localItemViewIds.startDate);
      if (!TextUtils.isEmpty(str1))
      {
        localEducation.dateInfo.start = new CoarseDate();
        localEducation.dateInfo.start.year = Integer.valueOf(Integer.parseInt(str1));
      }
      String str2 = getEditedString(localView, localItemViewIds.endDate);
      if (!TextUtils.isEmpty(str2))
      {
        localEducation.dateInfo.end = new CoarseDate();
        localEducation.dateInfo.end.year = Integer.valueOf(Integer.parseInt(str2));
      }
      localEducation.dateInfo.current = Boolean.valueOf(getCurrent(localView, localItemViewIds.current));
      localEducations.education.add(localEducation);
    }
    localEducations.metadata = createMetadata();
    return localEducations;
  }

  private Employments createEmployments()
  {
    Employments localEmployments = new Employments();
    int i = this.mItemViews.getChildCount();
    localEmployments.employment = new ArrayList(i);
    for (int j = 0; j < i; j++)
    {
      View localView = this.mItemViews.getChildAt(j);
      ItemViewIds localItemViewIds = (ItemViewIds)localView.getTag();
      Employment localEmployment = new Employment();
      localEmployment.employer = getEditedString(localView, localItemViewIds.name);
      localEmployment.title = getEditedString(localView, localItemViewIds.titleOrMajor);
      localEmployment.dateInfo = new DateInfo();
      String str1 = getEditedString(localView, localItemViewIds.startDate);
      if (!TextUtils.isEmpty(str1))
      {
        localEmployment.dateInfo.start = new CoarseDate();
        localEmployment.dateInfo.start.year = Integer.valueOf(Integer.parseInt(str1));
      }
      String str2 = getEditedString(localView, localItemViewIds.endDate);
      if (!TextUtils.isEmpty(str2))
      {
        localEmployment.dateInfo.end = new CoarseDate();
        localEmployment.dateInfo.end.year = Integer.valueOf(Integer.parseInt(str2));
      }
      localEmployment.dateInfo.current = Boolean.valueOf(getCurrent(localView, localItemViewIds.current));
      localEmployments.employment.add(localEmployment);
    }
    localEmployments.metadata = createMetadata();
    return localEmployments;
  }

  private Locations createLocations(boolean paramBoolean)
  {
    Locations localLocations = new Locations();
    String str = null;
    int i = this.mItemViews.getChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView = this.mItemViews.getChildAt(j);
      ItemViewIds localItemViewIds = (ItemViewIds)localView.getTag();
      if (getCurrent(localView, localItemViewIds.current))
      {
        str = getEditedString(localView, localItemViewIds.name);
        if (paramBoolean)
          str = "~~Internal~CurrentLocation." + str;
      }
      else
      {
        if (localLocations.otherLocation == null)
          localLocations.otherLocation = new ArrayList();
        localLocations.otherLocation.add(getEditedString(localView, localItemViewIds.name));
      }
    }
    if (str != null)
      localLocations.currentLocation = str;
    localLocations.metadata = createMetadata();
    return localLocations;
  }

  private Metadata createMetadata()
  {
    AudienceData localAudienceData = this.mAudienceView.getAudience();
    Metadata localMetadata = new Metadata();
    int i = localAudienceData.getCircleCount();
    int j = localAudienceData.getUserCount();
    int n;
    if ((i == 1) && (j == 0))
    {
      n = localAudienceData.getCircle(0).getType();
      if (n != 1)
        if (n == 9)
          localMetadata.scope = "PUBLIC";
    }
    while (true)
    {
      return localMetadata;
      if (n == 8)
      {
        localMetadata.scope = "DOMAIN";
      }
      else if (n == 7)
      {
        localMetadata.scope = "EXTENDED_CIRCLES";
      }
      else if (n == 5)
      {
        localMetadata.scope = "MY_CIRCLES";
      }
      else if (n == 101)
      {
        localMetadata.scope = "PRIVATE";
        continue;
        localMetadata.scope = "CUSTOM_CHIPS";
        localMetadata.sharingRoster = new SharingRoster();
        if (this.mOriginalRequiredScopeId != null)
          localMetadata.sharingRoster.requiredScopeId = ((SharingTargetId)SharingTargetIdJson.getInstance().fromString(this.mOriginalRequiredScopeId));
        localMetadata.sharingRoster.sharingTargetId = new ArrayList();
        int k = 0;
        if (k < i)
        {
          CircleData localCircleData = localAudienceData.getCircle(k);
          SharingTargetId localSharingTargetId2 = new SharingTargetId();
          switch (localCircleData.getType())
          {
          case 2:
          case 3:
          case 4:
          case 6:
          default:
          case 9:
          case 8:
          case 7:
          case 5:
          case 1:
          }
          while (true)
          {
            localMetadata.sharingRoster.sharingTargetId.add(localSharingTargetId2);
            k++;
            break;
            localSharingTargetId2.groupType = "PUBLIC";
            continue;
            localSharingTargetId2.groupType = "DASHER_DOMAIN";
            continue;
            localSharingTargetId2.groupType = "EXTENDED_CIRCLES";
            continue;
            localSharingTargetId2.groupType = "YOUR_CIRCLES";
            continue;
            localSharingTargetId2.circleId = EsPeopleData.getFocusCircleId(localCircleData.getId());
          }
        }
        for (int m = 0; m < j; m++)
        {
          PersonData localPersonData = localAudienceData.getUser(m);
          SharingTargetId localSharingTargetId1 = new SharingTargetId();
          localSharingTargetId1.personId = new DataCircleMemberId();
          localSharingTargetId1.personId.obfuscatedGaiaId = localPersonData.getObfuscatedId();
          localMetadata.sharingRoster.sharingTargetId.add(localSharingTargetId1);
        }
      }
    }
  }

  private void dismissProgressDialog()
  {
    DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("req_pending");
    if (localDialogFragment != null)
      localDialogFragment.dismiss();
  }

  private void finishActivity(int paramInt)
  {
    getActivity().setResult(paramInt, null);
    getActivity().finish();
  }

  private AudienceData getAudience(Metadata paramMetadata)
  {
    CircleData localCircleData;
    label33: AudienceData localAudienceData;
    if (paramMetadata == null)
      if (this.mHasPublicCircle)
      {
        localCircleData = new CircleData("0", 9, getString(R.string.acl_public), 0);
        localAudienceData = new AudienceData(localCircleData);
      }
    while (true)
    {
      return localAudienceData;
      if (this.mDomainName != null)
      {
        localCircleData = new CircleData("v.domain", 8, this.mDomainName, 0);
        break label33;
      }
      localCircleData = new CircleData("1c", 5, getString(R.string.acl_your_circles), 0);
      break label33;
      if ((paramMetadata.sharingRoster != null) && (paramMetadata.sharingRoster.requiredScopeId != null))
        this.mOriginalRequiredScopeId = SharingTargetIdJson.getInstance().toString(paramMetadata.sharingRoster.requiredScopeId);
      if (paramMetadata.scope == null)
      {
        paramMetadata = null;
        break;
      }
      if ("PRIVATE".equals(paramMetadata.scope))
      {
        localAudienceData = new AudienceData(new CircleData("v.private", 101, getString(R.string.acl_private), 1));
      }
      else
      {
        if ((paramMetadata.sharingRoster == null) || (paramMetadata.sharingRoster.sharingTargetId == null))
        {
          paramMetadata = null;
          break;
        }
        ArrayList localArrayList1 = new ArrayList();
        ArrayList localArrayList2 = new ArrayList();
        List localList = paramMetadata.sharingRoster.sharingTargetId;
        int i = localList.size();
        int j = 0;
        if (j < i)
        {
          SharingTargetId localSharingTargetId = (SharingTargetId)localList.get(j);
          if (localSharingTargetId.groupType != null)
            if ("PUBLIC".equals(localSharingTargetId.groupType))
              localArrayList2.add(new CircleData("0", 9, getString(R.string.acl_public), 0));
          while (true)
          {
            j++;
            break;
            if ("DASHER_DOMAIN".equals(localSharingTargetId.groupType))
            {
              localArrayList2.add(new CircleData(this.mDomainId, 8, this.mDomainName, 1));
            }
            else if ("EXTENDED_CIRCLES".equals(localSharingTargetId.groupType))
            {
              localArrayList2.add(new CircleData("1f", 7, getString(R.string.acl_extended_network), 0));
            }
            else if ("YOUR_CIRCLES".equals(localSharingTargetId.groupType))
            {
              localArrayList2.add(new CircleData("1c", 5, getString(R.string.acl_your_circles), 0));
              continue;
              if (localSharingTargetId.circleId != null)
              {
                String str2 = getCircleNameFromSharingRoster(localSharingTargetId.circleId);
                localArrayList2.add(new CircleData(EsPeopleData.getCircleId(localSharingTargetId.circleId), 1, str2, 1));
              }
              else if ((localSharingTargetId.personId != null) && (localSharingTargetId.personId != null) && (localSharingTargetId.personId.obfuscatedGaiaId != null))
              {
                String str1 = getPersonNameFromSharingRoster(localSharingTargetId.personId.obfuscatedGaiaId);
                localArrayList1.add(new PersonData(localSharingTargetId.personId.obfuscatedGaiaId, str1, null));
              }
            }
          }
        }
        if ((localArrayList1.isEmpty()) && (localArrayList2.isEmpty()))
          localArrayList2.add(new CircleData("v.private", 101, getString(R.string.acl_private), 1));
        localAudienceData = new AudienceData(localArrayList1, localArrayList2);
      }
    }
  }

  private String getCircleNameFromSharingRoster(String paramString)
  {
    return getNameFromSharingRoster(1, paramString);
  }

  private static boolean getCurrent(View paramView, int paramInt)
  {
    CheckBox localCheckBox = (CheckBox)paramView.findViewById(paramInt);
    if ((localCheckBox != null) && (localCheckBox.isChecked()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private static String getEditedString(View paramView, int paramInt)
  {
    EditText localEditText = (EditText)paramView.findViewById(paramInt);
    if (localEditText != null);
    for (String str = localEditText.getText().toString(); ; str = null)
      return str;
  }

  private String getNameFromSharingRoster(int paramInt, String paramString)
  {
    if ((this.mSharingRosterData == null) && (!TextUtils.isEmpty(this.mSharingRosterDataJson)))
      this.mSharingRosterData = ((SharingRosterData)SharingRosterDataJson.getInstance().fromString(this.mSharingRosterDataJson));
    SharingTarget localSharingTarget;
    String str;
    if ((this.mSharingRosterData != null) && (this.mSharingRosterData.targets != null))
    {
      int i = this.mSharingRosterData.targets.size();
      int j = 0;
      if (j < i)
      {
        localSharingTarget = (SharingTarget)this.mSharingRosterData.targets.get(j);
        if (localSharingTarget.id != null);
        switch (paramInt)
        {
        default:
        case 1:
          do
          {
            j++;
            break;
          }
          while (!TextUtils.equals(localSharingTarget.id.circleId, paramString));
          str = localSharingTarget.displayName;
        case 2:
        case 3:
        }
      }
    }
    while (true)
    {
      return str;
      if ((localSharingTarget.id.personId == null) || (!TextUtils.equals(localSharingTarget.id.personId.obfuscatedGaiaId, paramString)))
        break;
      str = localSharingTarget.displayName;
      continue;
      if (!"DASHER_DOMAIN".equals(localSharingTarget.id.groupType))
        break;
      str = localSharingTarget.displayName;
      continue;
      str = null;
    }
  }

  private String getPersonNameFromSharingRoster(String paramString)
  {
    return getNameFromSharingRoster(2, paramString);
  }

  private View getView(Education paramEducation, ItemViewIds paramItemViewIds)
  {
    if (paramItemViewIds == null)
    {
      int i = this.mViewIdNextName;
      this.mViewIdNextName = (i + 1);
      int j = this.mViewIdNextTitleOrMajor;
      this.mViewIdNextTitleOrMajor = (j + 1);
      int k = this.mViewIdNextStartDate;
      this.mViewIdNextStartDate = (k + 1);
      int m = this.mViewIdNextEndDate;
      this.mViewIdNextEndDate = (m + 1);
      int n = this.mViewIdNextCurrent;
      this.mViewIdNextCurrent = (n + 1);
      paramItemViewIds = new ItemViewIds(i, j, k, m, n);
    }
    View localView = getActivity().getLayoutInflater().inflate(R.layout.profile_edit_item_education, null);
    localView.setTag(paramItemViewIds);
    EditText localEditText1 = (EditText)localView.findViewById(R.id.name);
    localEditText1.setId(paramItemViewIds.name);
    String str1;
    EditText localEditText2;
    if (paramEducation != null)
    {
      str1 = paramEducation.school;
      localEditText1.setText(str1);
      localEditText2 = (EditText)localView.findViewById(R.id.title);
      localEditText2.setId(paramItemViewIds.titleOrMajor);
      if (paramEducation == null)
        break label219;
    }
    label219: for (String str2 = paramEducation.majorConcentration; ; str2 = "")
    {
      localEditText2.setText(str2);
      DateInfo localDateInfo = null;
      if (paramEducation != null)
        localDateInfo = paramEducation.dateInfo;
      configureDataInfo(localView, paramItemViewIds, localDateInfo);
      return localView;
      str1 = "";
      break;
    }
  }

  private View getView(Employment paramEmployment, ItemViewIds paramItemViewIds)
  {
    if (paramItemViewIds == null)
    {
      int i = this.mViewIdNextName;
      this.mViewIdNextName = (i + 1);
      int j = this.mViewIdNextTitleOrMajor;
      this.mViewIdNextTitleOrMajor = (j + 1);
      int k = this.mViewIdNextStartDate;
      this.mViewIdNextStartDate = (k + 1);
      int m = this.mViewIdNextEndDate;
      this.mViewIdNextEndDate = (m + 1);
      int n = this.mViewIdNextCurrent;
      this.mViewIdNextCurrent = (n + 1);
      paramItemViewIds = new ItemViewIds(i, j, k, m, n);
    }
    View localView = getActivity().getLayoutInflater().inflate(R.layout.profile_edit_item_employment, null);
    localView.setTag(paramItemViewIds);
    EditText localEditText1 = (EditText)localView.findViewById(R.id.name);
    localEditText1.setId(paramItemViewIds.name);
    String str1;
    EditText localEditText2;
    if (paramEmployment != null)
    {
      str1 = paramEmployment.employer;
      localEditText1.setText(str1);
      localEditText2 = (EditText)localView.findViewById(R.id.title);
      localEditText2.setId(paramItemViewIds.titleOrMajor);
      if (paramEmployment == null)
        break label219;
    }
    label219: for (String str2 = paramEmployment.title; ; str2 = "")
    {
      localEditText2.setText(str2);
      DateInfo localDateInfo = null;
      if (paramEmployment != null)
        localDateInfo = paramEmployment.dateInfo;
      configureDataInfo(localView, paramItemViewIds, localDateInfo);
      return localView;
      str1 = "";
      break;
    }
  }

  private View getView(String paramString1, String paramString2, ItemViewIds paramItemViewIds)
  {
    if (paramItemViewIds == null)
    {
      int i = this.mViewIdNextName;
      this.mViewIdNextName = (i + 1);
      int j = this.mViewIdNextTitleOrMajor;
      this.mViewIdNextTitleOrMajor = (j + 1);
      int k = this.mViewIdNextStartDate;
      this.mViewIdNextStartDate = (k + 1);
      int m = this.mViewIdNextEndDate;
      this.mViewIdNextEndDate = (m + 1);
      int n = this.mViewIdNextCurrent;
      this.mViewIdNextCurrent = (n + 1);
      paramItemViewIds = new ItemViewIds(i, j, k, m, n);
    }
    View localView = getActivity().getLayoutInflater().inflate(R.layout.profile_edit_item_location, null);
    localView.setTag(paramItemViewIds);
    EditText localEditText = (EditText)localView.findViewById(R.id.name);
    localEditText.setId(paramItemViewIds.name);
    localEditText.setText(paramString1);
    CheckBox localCheckBox = (CheckBox)localView.findViewById(R.id.current);
    localCheckBox.setId(paramItemViewIds.current);
    localCheckBox.setTag(localView);
    if ((paramString2 != null) && (paramString2.equals(paramString1)));
    for (boolean bool = true; ; bool = false)
    {
      localCheckBox.setChecked(bool);
      ImageView localImageView = (ImageView)localView.findViewById(R.id.delete_item);
      localImageView.setOnClickListener(this);
      localImageView.setTag(localView);
      return localView;
    }
  }

  private AudienceData normalizeAudience(AudienceData paramAudienceData)
  {
    Arrays.sort(paramAudienceData.getUsers(), new Comparator()
    {
    });
    Arrays.sort(paramAudienceData.getCircles(), new Comparator()
    {
    });
    return paramAudienceData;
  }

  private void onCancel()
  {
    if (this.mSaveButton.isEnabled())
    {
      SoftInput.hide(this.mFocusOverrideView);
      AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(getString(R.string.app_name), getString(R.string.profile_edit_items_exit_unsaved), getString(R.string.yes), getString(R.string.no));
      localAlertFragmentDialog.setListener(new AlertFragmentDialog.AlertDialogListener()
      {
        public final void onDialogCanceled$20f9a4b7(String paramAnonymousString)
        {
        }

        public final void onDialogListClick$12e92030(int paramAnonymousInt, Bundle paramAnonymousBundle)
        {
        }

        public final void onDialogNegativeClick$20f9a4b7(String paramAnonymousString)
        {
        }

        public final void onDialogPositiveClick(Bundle paramAnonymousBundle, String paramAnonymousString)
        {
          ProfileEditFragment.this.finishActivity(0);
        }
      });
      localAlertFragmentDialog.show(getFragmentManager(), "quit");
    }
    while (true)
    {
      return;
      finishActivity(0);
    }
  }

  private void removeChangedField(View paramView)
  {
    this.mModifiedViews.remove(paramView);
    int i;
    if ((this.mOriginalCount == 0) && (this.mItemViews.getChildCount() == 0))
    {
      i = 1;
      if ((this.mModifiedViews.size() != 1) || (!this.mModifiedViews.contains(this.mAudienceView)))
        break label89;
    }
    label89: for (int j = 1; ; j = 0)
    {
      if ((this.mModifiedViews.size() == 0) || ((j != 0) && (i != 0)))
        this.mSaveButton.setEnabled(false);
      return;
      i = 0;
      break;
    }
  }

  private void removeFocus()
  {
    if (this.mFocusOverrideView != null)
      this.mFocusOverrideView.requestFocus();
    SoftInput.hide(getView());
  }

  private boolean showErrorToast(ServiceResult paramServiceResult)
  {
    boolean bool;
    String str2;
    if ((paramServiceResult != null) && (!paramServiceResult.hasError()))
    {
      Exception localException = paramServiceResult.getException();
      bool = false;
      if (localException == null);
    }
    else
    {
      if ((paramServiceResult == null) || (paramServiceResult.getException() == null))
        break label77;
      str2 = paramServiceResult.getException().getMessage();
    }
    label77: for (String str1 = getString(R.string.profile_edit_update_error, new Object[] { str2 }); ; str1 = getString(R.string.transient_server_error))
    {
      Toast.makeText(getActivity(), str1, 1).show();
      bool = true;
      return bool;
    }
  }

  private void updateViewsWithDateInfoValues(View paramView, ItemViewIds paramItemViewIds, DateInfo paramDateInfo)
  {
    boolean bool1 = true;
    boolean bool2;
    String str1;
    label48: boolean bool3;
    label120: String str2;
    label140: EditText localEditText2;
    EditTextWatcher localEditTextWatcher2;
    if ((paramDateInfo != null) && (paramDateInfo.start != null) && (paramDateInfo.start.year != null))
    {
      bool2 = bool1;
      if (!bool2)
        break label236;
      str1 = Integer.toString(paramDateInfo.start.year.intValue());
      EditText localEditText1 = (EditText)paramView.findViewById(paramItemViewIds.startDate);
      EditTextWatcher localEditTextWatcher1 = new EditTextWatcher(localEditText1, str1);
      localEditTextWatcher1.onTextChanged(localEditText1.getText(), 0, 0, 0);
      localEditText1.addTextChangedListener(localEditTextWatcher1);
      if ((paramDateInfo == null) || (paramDateInfo.end == null) || (paramDateInfo.end.year == null))
        break label244;
      bool3 = bool1;
      if (!bool3)
        break label250;
      str2 = Integer.toString(paramDateInfo.end.year.intValue());
      localEditText2 = (EditText)paramView.findViewById(paramItemViewIds.endDate);
      localEditTextWatcher2 = new EditTextWatcher(localEditText2, str2);
      if ((paramDateInfo == null) || (!PrimitiveUtils.safeBoolean(paramDateInfo.current)))
        break label258;
    }
    while (true)
    {
      CheckBox localCheckBox = (CheckBox)paramView.findViewById(paramItemViewIds.current);
      CheckboxWatcher localCheckboxWatcher = new CheckboxWatcher(localEditText2, localEditTextWatcher2, bool1);
      localCheckboxWatcher.onCheckedChanged(localCheckBox, localCheckBox.isChecked());
      localCheckBox.setOnCheckedChangeListener(localCheckboxWatcher);
      return;
      bool2 = false;
      break;
      label236: str1 = "";
      break label48;
      label244: bool3 = false;
      break label120;
      label250: str2 = "";
      break label140;
      label258: bool1 = false;
    }
  }

  private void updateViewsWithOriginalValues()
  {
    this.mModifiedViews.clear();
    this.mSaveButton.setEnabled(false);
    switch (this.mEditMode)
    {
    default:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      return;
      if (this.mOriginalEmployments.employment != null);
      int i5;
      for (int i4 = this.mOriginalEmployments.employment.size(); ; i4 = 0)
      {
        i5 = this.mItemViews.getChildCount();
        if (i5 != 0)
          break label105;
        if (i4 == 0)
          break;
        this.mSaveButton.setEnabled(true);
        break;
      }
      label105: int i6 = 0;
      if (i6 < i5)
      {
        Employment localEmployment;
        label141: View localView3;
        ItemViewIds localItemViewIds3;
        String str4;
        label174: String str5;
        if (i6 < i4)
        {
          localEmployment = (Employment)this.mOriginalEmployments.employment.get(i6);
          localView3 = this.mItemViews.getChildAt(i6);
          localItemViewIds3 = (ItemViewIds)localView3.getTag();
          if (localEmployment == null)
            break label318;
          str4 = localEmployment.employer;
          EditText localEditText4 = (EditText)localView3.findViewById(localItemViewIds3.name);
          EditTextWatcher localEditTextWatcher4 = new EditTextWatcher(localEditText4, str4);
          localEditTextWatcher4.onTextChanged(localEditText4.getText(), 0, 0, 0);
          localEditText4.addTextChangedListener(localEditTextWatcher4);
          if (localEmployment == null)
            break label326;
          str5 = localEmployment.title;
          label235: EditText localEditText5 = (EditText)localView3.findViewById(localItemViewIds3.titleOrMajor);
          EditTextWatcher localEditTextWatcher5 = new EditTextWatcher(localEditText5, str5);
          localEditTextWatcher5.onTextChanged(localEditText5.getText(), 0, 0, 0);
          localEditText5.addTextChangedListener(localEditTextWatcher5);
          if (localEmployment == null)
            break label334;
        }
        label318: label326: label334: for (DateInfo localDateInfo2 = localEmployment.dateInfo; ; localDateInfo2 = null)
        {
          updateViewsWithDateInfoValues(localView3, localItemViewIds3, localDateInfo2);
          i6++;
          break;
          localEmployment = null;
          break label141;
          str4 = "";
          break label174;
          str5 = "";
          break label235;
        }
      }
      if (i4 > i5)
      {
        addChangedField(this.mDeletedFieldView);
        continue;
        if (this.mOriginalEducations.education != null);
        int i2;
        for (int i1 = this.mOriginalEducations.education.size(); ; i1 = 0)
        {
          i2 = this.mItemViews.getChildCount();
          if (i2 != 0)
            break label418;
          if (i1 == 0)
            break;
          this.mSaveButton.setEnabled(true);
          break;
        }
        label418: int i3 = 0;
        if (i3 < i2)
        {
          Education localEducation;
          label454: View localView2;
          ItemViewIds localItemViewIds2;
          String str2;
          label487: String str3;
          if (i3 < i1)
          {
            localEducation = (Education)this.mOriginalEducations.education.get(i3);
            localView2 = this.mItemViews.getChildAt(i3);
            localItemViewIds2 = (ItemViewIds)localView2.getTag();
            if (localEducation == null)
              break label631;
            str2 = localEducation.school;
            EditText localEditText2 = (EditText)localView2.findViewById(localItemViewIds2.name);
            EditTextWatcher localEditTextWatcher2 = new EditTextWatcher(localEditText2, str2);
            localEditTextWatcher2.onTextChanged(localEditText2.getText(), 0, 0, 0);
            localEditText2.addTextChangedListener(localEditTextWatcher2);
            if (localEducation == null)
              break label639;
            str3 = localEducation.majorConcentration;
            label548: EditText localEditText3 = (EditText)localView2.findViewById(localItemViewIds2.titleOrMajor);
            EditTextWatcher localEditTextWatcher3 = new EditTextWatcher(localEditText3, str3);
            localEditTextWatcher3.onTextChanged(localEditText3.getText(), 0, 0, 0);
            localEditText3.addTextChangedListener(localEditTextWatcher3);
            if (localEducation == null)
              break label647;
          }
          label647: for (DateInfo localDateInfo1 = localEducation.dateInfo; ; localDateInfo1 = null)
          {
            updateViewsWithDateInfoValues(localView2, localItemViewIds2, localDateInfo1);
            i3++;
            break;
            localEducation = null;
            break label454;
            label631: str2 = "";
            break label487;
            label639: str3 = "";
            break label548;
          }
        }
        if (i1 > i2)
        {
          addChangedField(this.mDeletedFieldView);
          continue;
          if (this.mOriginalLocations.otherLocation != null);
          int j;
          int k;
          for (int i = this.mOriginalLocations.otherLocation.size(); ; i = 0)
          {
            j = this.mItemViews.getChildCount();
            k = 0;
            if (j != 0)
              break label759;
            if ((this.mOriginalLocations.currentLocation == null) && ((this.mOriginalLocations.otherLocation == null) || (this.mOriginalLocations.otherLocation.size() <= 0)))
              break;
            this.mSaveButton.setEnabled(true);
            break;
          }
          label759: int m = 0;
          while (m < j)
          {
            String str1;
            boolean bool;
            if ((m == 0) && (!TextUtils.isEmpty(this.mOriginalLocations.currentLocation)))
            {
              str1 = this.mOriginalLocations.currentLocation;
              bool = true;
              k = 1;
              View localView1 = this.mItemViews.getChildAt(m);
              ItemViewIds localItemViewIds1 = (ItemViewIds)localView1.getTag();
              EditText localEditText1 = (EditText)localView1.findViewById(localItemViewIds1.name);
              EditTextWatcher localEditTextWatcher1 = new EditTextWatcher(localEditText1, str1);
              localEditTextWatcher1.onTextChanged(localEditText1.getText(), 0, 0, 0);
              localEditText1.addTextChangedListener(localEditTextWatcher1);
              CheckBox localCheckBox = (CheckBox)localView1.findViewById(localItemViewIds1.current);
              LocationCheckboxWatcher localLocationCheckboxWatcher = new LocationCheckboxWatcher(bool);
              localLocationCheckboxWatcher.onCheckedChanged(localCheckBox, localCheckBox.isChecked());
              localCheckBox.setOnCheckedChangeListener(localLocationCheckboxWatcher);
              m++;
            }
            else
            {
              int n = m - k;
              if (n < i);
              for (str1 = (String)this.mOriginalLocations.otherLocation.get(n); ; str1 = "")
              {
                bool = false;
                break;
              }
            }
          }
          if (i > j - k)
            addChangedField(this.mDeletedFieldView);
        }
      }
    }
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 != -1);
    while (true)
    {
      return;
      switch (paramInt1)
      {
      default:
        break;
      case 1:
        if (paramIntent != null)
        {
          AudienceData localAudienceData = (AudienceData)paramIntent.getParcelableExtra("audience");
          if (localAudienceData != null)
          {
            this.mAudienceView.replaceAudience(localAudienceData);
            if (this.mOriginalAudience.equals(normalizeAudience(localAudienceData.clone())))
              removeChangedField(this.mAudienceView);
            else
              addChangedField(this.mAudienceView);
          }
        }
        break;
      }
    }
  }

  public void onClick(View paramView)
  {
    int i = paramView.getId();
    if (i == R.id.cancel)
      onCancel();
    while (true)
    {
      return;
      if (i == R.id.save)
      {
        removeFocus();
        SoftInput.hide(getView());
        SimpleProfile localSimpleProfile;
        switch (this.mEditMode)
        {
        default:
          break;
        case 1:
          Employments localEmployments = createEmployments();
          localSimpleProfile = new SimpleProfile();
          localSimpleProfile.user = new User();
          localSimpleProfile.user.employments = localEmployments;
          localSimpleProfile.user.employments.metadata = createMetadata();
        case 2:
        case 3:
          while (true)
          {
            this.mProfilePendingRequestId = EsService.mutateProfile(getActivity(), this.mAccount, localSimpleProfile);
            ProgressFragmentDialog.newInstance(null, getString(R.string.profile_edit_updating), false).show(getFragmentManager(), "req_pending");
            break;
            Educations localEducations = createEducations();
            localSimpleProfile = new SimpleProfile();
            localSimpleProfile.user = new User();
            localSimpleProfile.user.educations = localEducations;
            localSimpleProfile.user.educations.metadata = createMetadata();
            continue;
            Locations localLocations = createLocations(false);
            localSimpleProfile = new SimpleProfile();
            localSimpleProfile.user = new User();
            localSimpleProfile.user.locations = localLocations;
            localSimpleProfile.user.locations.metadata = createMetadata();
          }
        }
      }
      else if (i == R.id.add_item)
      {
        View localView2 = addItem();
        if (localView2 != null)
        {
          View localView3 = localView2.findViewById(((ItemViewIds)localView2.getTag()).name);
          localView3.requestFocus();
          SoftInput.show(localView3);
        }
      }
      else if (i == R.id.delete_item)
      {
        final View localView1 = (View)paramView.getTag();
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
        localBuilder.setMessage(R.string.profile_edit_item_remove_confirm);
        localBuilder.setPositiveButton(R.string.profile_edit_item_remove, new DialogInterface.OnClickListener()
        {
          public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            ProfileEditFragment.this.mItemViews.removeView(localView1);
            ProfileEditFragment.this.updateViewsWithOriginalValues();
          }
        });
        localBuilder.setNegativeButton(17039360, new DialogInterface.OnClickListener()
        {
          public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            paramAnonymousDialogInterface.dismiss();
          }
        });
        localBuilder.show();
      }
      else if (i == R.id.audience)
      {
        removeFocus();
        SimpleAudiencePickerDialog localSimpleAudiencePickerDialog = SimpleAudiencePickerDialog.newInstance(this.mDomainName, this.mDomainId, this.mHasPublicCircle);
        localSimpleAudiencePickerDialog.setTargetFragment(this, 0);
        localSimpleAudiencePickerDialog.show(getFragmentManager(), "simple_audience");
      }
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.mDomainName = paramBundle.getString("domain_name");
      this.mDomainId = paramBundle.getString("domain_id");
      this.mHasPublicCircle = paramBundle.getBoolean("has_public_circle");
      this.mItemsJson = paramBundle.getString("items_json");
      this.mAudience = ((AudienceData)paramBundle.getParcelable("audience"));
      this.mOriginalRequiredScopeId = paramBundle.getString("required_scope_id");
      this.mItemViewIdsList = ((ArrayList)paramBundle.getSerializable("items"));
      this.mViewIdNextName = paramBundle.getInt("next_name");
      this.mViewIdNextTitleOrMajor = paramBundle.getInt("next_title");
      this.mViewIdNextStartDate = paramBundle.getInt("next_start");
      this.mViewIdNextEndDate = paramBundle.getInt("next_end");
      this.mViewIdNextCurrent = paramBundle.getInt("next_current");
    }
    this.mEditMode = getArguments().getInt("profile_edit_mode");
    this.mAccount = ((EsAccount)getArguments().getParcelable("account"));
    this.mOriginalItemsJson = getArguments().getString("profile_edit_items_json");
    if (this.mItemsJson == null)
      this.mItemsJson = this.mOriginalItemsJson;
    this.mSharingRosterDataJson = getArguments().getString("profile_edit_roster_json");
    switch (this.mEditMode)
    {
    default:
    case 1:
    case 2:
      while (true)
      {
        return;
        this.mOriginalEmployments = ((Employments)EmploymentsJson.getInstance().fromString(this.mOriginalItemsJson));
        this.mOriginalAudience = normalizeAudience(getAudience(this.mOriginalEmployments.metadata));
        if (this.mOriginalEmployments.employment != null);
        for (int m = this.mOriginalEmployments.employment.size(); ; m = 0)
        {
          this.mOriginalCount = m;
          break;
        }
        this.mOriginalEducations = ((Educations)EducationsJson.getInstance().fromString(this.mOriginalItemsJson));
        this.mOriginalAudience = normalizeAudience(getAudience(this.mOriginalEducations.metadata));
        List localList2 = this.mOriginalEducations.education;
        int k = 0;
        if (localList2 != null)
          k = this.mOriginalEducations.education.size();
        this.mOriginalCount = k;
      }
    case 3:
    }
    this.mOriginalLocations = ((Locations)LocationsJson.getInstance().fromString(this.mOriginalItemsJson));
    this.mOriginalAudience = normalizeAudience(getAudience(this.mOriginalLocations.metadata));
    if (!TextUtils.isEmpty(this.mOriginalLocations.currentLocation));
    for (int i = 1; ; i = 0)
    {
      List localList1 = this.mOriginalLocations.otherLocation;
      int j = 0;
      if (localList1 != null)
        j = this.mOriginalLocations.otherLocation.size();
      this.mOriginalCount = (i + j);
      break;
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView1 = paramLayoutInflater.inflate(R.layout.profile_edit_items, null);
    this.mFocusOverrideView = localView1.findViewById(R.id.focus_override);
    this.mDeletedFieldView = new View(getActivity());
    ((ImageTextButton)localView1.findViewById(R.id.cancel)).setOnClickListener(this);
    this.mSaveButton = ((ImageTextButton)localView1.findViewById(R.id.save));
    this.mSaveButton.setOnClickListener(this);
    this.mScollView = ((ScrollView)localView1.findViewById(R.id.scroller));
    this.mItemViews = ((LinearLayout)localView1.findViewById(R.id.items));
    CircleData localCircleData = EsPeopleData.getCircleData(getActivity(), this.mAccount, 8);
    if (localCircleData != null)
    {
      this.mDomainName = localCircleData.getName();
      this.mDomainId = localCircleData.getId();
    }
    this.mHasPublicCircle = EsPeopleData.hasPublicCircle(getActivity(), this.mAccount);
    int i = this.mEditMode;
    Metadata localMetadata = null;
    int j = 0;
    int m;
    switch (i)
    {
    default:
      if (j == 0)
        addItem();
      switch (this.mEditMode)
      {
      default:
        m = 0;
      case 1:
      case 2:
      case 3:
      }
      break;
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      this.mAddItemView = ((TextView)localView1.findViewById(R.id.add_item));
      this.mAddItemView.setText(m);
      this.mAddItemView.setOnClickListener(this);
      if (this.mAudience == null)
        this.mAudience = getAudience(localMetadata);
      this.mAudienceView = ((TextOnlyAudienceView)localView1.findViewById(R.id.audience));
      this.mAudienceView.setAccount(this.mAccount);
      this.mAudienceView.setOnClickListener(this);
      this.mAudienceView.setChevronDirection(TextOnlyAudienceView.ChevronDirection.POINT_RIGHT);
      this.mAudienceView.replaceAudience(this.mAudience);
      if (paramBundle == null)
        this.mFocusOverrideView.requestFocus();
      updateViewsWithOriginalValues();
      return localView1;
      boolean bool4 = TextUtils.isEmpty(this.mItemsJson);
      localMetadata = null;
      j = 0;
      if (bool4)
        break;
      Employments localEmployments = (Employments)EmploymentsJson.getInstance().fromString(this.mItemsJson);
      localMetadata = null;
      j = 0;
      if (localEmployments == null)
        break;
      List localList2 = localEmployments.employment;
      localMetadata = null;
      j = 0;
      if (localList2 == null)
        break;
      j = localEmployments.employment.size();
      int i3 = 0;
      if (i3 < j)
      {
        Employment localEmployment = (Employment)localEmployments.employment.get(i3);
        if ((this.mItemViewIdsList != null) && (this.mItemViewIdsList.size() > i3));
        for (ItemViewIds localItemViewIds3 = (ItemViewIds)this.mItemViewIdsList.get(i3); ; localItemViewIds3 = null)
        {
          View localView5 = getView(localEmployment, localItemViewIds3);
          this.mItemViews.addView(localView5);
          i3++;
          break;
        }
      }
      localMetadata = localEmployments.metadata;
      break;
      boolean bool3 = TextUtils.isEmpty(this.mItemsJson);
      localMetadata = null;
      j = 0;
      if (bool3)
        break;
      Educations localEducations = (Educations)EducationsJson.getInstance().fromString(this.mItemsJson);
      localMetadata = null;
      j = 0;
      if (localEducations == null)
        break;
      List localList1 = localEducations.education;
      localMetadata = null;
      j = 0;
      if (localList1 == null)
        break;
      j = localEducations.education.size();
      int i2 = 0;
      if (i2 < j)
      {
        Education localEducation = (Education)localEducations.education.get(i2);
        if ((this.mItemViewIdsList != null) && (this.mItemViewIdsList.size() > i2));
        for (ItemViewIds localItemViewIds2 = (ItemViewIds)this.mItemViewIdsList.get(i2); ; localItemViewIds2 = null)
        {
          View localView4 = getView(localEducation, localItemViewIds2);
          this.mItemViews.addView(localView4);
          i2++;
          break;
        }
      }
      localMetadata = localEducations.metadata;
      break;
      boolean bool1 = TextUtils.isEmpty(this.mItemsJson);
      localMetadata = null;
      j = 0;
      if (bool1)
        break;
      Locations localLocations = (Locations)LocationsJson.getInstance().fromString(this.mItemsJson);
      localMetadata = null;
      j = 0;
      if (localLocations == null)
        break;
      String str = localLocations.currentLocation;
      boolean bool2 = TextUtils.isEmpty(str);
      int k = 0;
      int i1;
      if (!bool2)
      {
        if (str.startsWith("~~Internal~CurrentLocation."))
          str = localLocations.currentLocation.substring(27);
      }
      else
      {
        if (localLocations.otherLocation == null)
          break label953;
        int n = localLocations.otherLocation.size();
        k += n;
        i1 = 0;
        label841: if (i1 >= n)
          break label953;
        if ((this.mItemViewIdsList == null) || (this.mItemViewIdsList.size() <= i1))
          break label947;
      }
      label947: for (ItemViewIds localItemViewIds1 = (ItemViewIds)this.mItemViewIdsList.get(i1); ; localItemViewIds1 = null)
      {
        View localView2 = getView((String)localLocations.otherLocation.get(i1), str, localItemViewIds1);
        this.mItemViews.addView(localView2);
        i1++;
        break label841;
        View localView3 = getView(str, str, null);
        this.mItemViews.addView(localView3);
        k = 1;
        break;
      }
      label953: localMetadata = localLocations.metadata;
      j = k;
      break;
      m = R.string.profile_add_a_job;
      continue;
      m = R.string.profile_add_a_school;
      continue;
      m = R.string.profile_add_a_place;
    }
  }

  public final void onDiscard()
  {
    onCancel();
  }

  public final void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mProfileEditServiceListener);
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mProfileEditServiceListener);
    if ((this.mProfilePendingRequestId != null) && (!EsService.isRequestPending(this.mProfilePendingRequestId.intValue())))
    {
      ServiceResult localServiceResult = EsService.removeResult(this.mProfilePendingRequestId.intValue());
      this.mProfilePendingRequestId = null;
      dismissProgressDialog();
      if (!showErrorToast(localServiceResult))
        finishActivity(-1);
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putParcelable("audience", this.mAudience);
    String str;
    switch (this.mEditMode)
    {
    default:
      str = null;
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      paramBundle.putString("items_json", str);
      if (this.mDomainName != null)
        paramBundle.putString("domain_name", this.mDomainName);
      if (this.mDomainId != null)
        paramBundle.putString("domain_id", this.mDomainId);
      paramBundle.putBoolean("has_public_circle", this.mHasPublicCircle);
      paramBundle.putString("required_scope_id", this.mOriginalRequiredScopeId);
      int i = this.mItemViews.getChildCount();
      if (i <= 0)
        break label254;
      if (this.mItemViewIdsList == null)
        this.mItemViewIdsList = new ArrayList();
      this.mItemViewIdsList.clear();
      for (int j = 0; j < i; j++)
      {
        View localView = this.mItemViews.getChildAt(j);
        this.mItemViewIdsList.add((ItemViewIds)localView.getTag());
      }
      Employments localEmployments = createEmployments();
      str = EmploymentsJson.getInstance().toString(localEmployments);
      continue;
      Educations localEducations = createEducations();
      str = EducationsJson.getInstance().toString(localEducations);
      continue;
      Locations localLocations = createLocations(true);
      str = LocationsJson.getInstance().toString(localLocations);
    }
    paramBundle.putSerializable("items", this.mItemViewIdsList);
    label254: paramBundle.putInt("next_name", this.mViewIdNextName);
    paramBundle.putInt("next_title", this.mViewIdNextTitleOrMajor);
    paramBundle.putInt("next_start", this.mViewIdNextStartDate);
    paramBundle.putInt("next_end", this.mViewIdNextEndDate);
    paramBundle.putInt("next_current", this.mViewIdNextCurrent);
    super.onSaveInstanceState(paramBundle);
  }

  public final void onSetSimpleAudience(String paramString1, int paramInt, String paramString2)
  {
    if (paramInt > 0)
    {
      AudienceData localAudienceData1 = new AudienceData(new CircleData(paramString1, paramInt, paramString2, 1));
      this.mAudienceView.replaceAudience(localAudienceData1);
      this.mOriginalAudience.equals(localAudienceData1);
      addChangedField(this.mAudienceView);
    }
    while (true)
    {
      return;
      AudienceData localAudienceData2 = this.mAudienceView.getAudience();
      if ((localAudienceData2.getCircleCount() == 1) && ("v.private".equals(localAudienceData2.getCircle(0).getId())))
        localAudienceData2 = null;
      Intent localIntent = Intents.getEditAudienceActivityIntent(getActivity(), this.mAccount, getString(R.string.profile_edit_item_acl_picker), localAudienceData2, 5, false, true, true, false);
      SoftInput.hide(getView());
      startActivityForResult(localIntent, 1);
    }
  }

  private final class CheckboxWatcher
    implements CompoundButton.OnCheckedChangeListener
  {
    private final EditText mLinkedEditText;
    private String mLinkedEditTextPreviousValue;
    private final ProfileEditFragment.EditTextWatcher mLinkedEditTextWatcher;
    private final boolean mOriginalCurrent;

    public CheckboxWatcher(EditText paramEditTextWatcher, ProfileEditFragment.EditTextWatcher paramBoolean, boolean arg4)
    {
      this.mLinkedEditText = paramEditTextWatcher;
      this.mLinkedEditTextWatcher = paramBoolean;
      boolean bool;
      this.mOriginalCurrent = bool;
    }

    public final void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
    {
      int i = 1;
      if (paramBoolean)
      {
        this.mLinkedEditTextPreviousValue = this.mLinkedEditText.getText().toString();
        ProfileEditFragment.this.removeChangedField(this.mLinkedEditText);
        this.mLinkedEditText.removeTextChangedListener(this.mLinkedEditTextWatcher);
        Calendar localCalendar = Calendar.getInstance();
        this.mLinkedEditText.setText(Integer.toString(localCalendar.get(i)));
        EditText localEditText = this.mLinkedEditText;
        if (paramBoolean)
          break label142;
        label73: localEditText.setEnabled(i);
        if (this.mOriginalCurrent != paramBoolean)
          break label147;
        ProfileEditFragment.this.removeChangedField(paramCompoundButton);
      }
      while (true)
      {
        return;
        if (this.mLinkedEditTextPreviousValue == null)
          this.mLinkedEditTextPreviousValue = this.mLinkedEditText.getText().toString();
        this.mLinkedEditText.addTextChangedListener(this.mLinkedEditTextWatcher);
        this.mLinkedEditText.setText(this.mLinkedEditTextPreviousValue);
        break;
        label142: int j = 0;
        break label73;
        label147: ProfileEditFragment.this.addChangedField(paramCompoundButton);
      }
    }
  }

  private final class EditTextWatcher
    implements TextWatcher
  {
    private final String mOriginalValue;
    private final View mView;

    public EditTextWatcher(View paramString, String arg3)
    {
      this.mView = paramString;
      Object localObject;
      this.mOriginalValue = localObject;
    }

    public final void afterTextChanged(Editable paramEditable)
    {
    }

    public final void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
    }

    public final void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
      if (paramCharSequence.toString().equals(this.mOriginalValue))
        ProfileEditFragment.this.removeChangedField(this.mView);
      while (true)
      {
        return;
        ProfileEditFragment.this.addChangedField(this.mView);
      }
    }
  }

  public static final class ItemViewIds
    implements Serializable
  {
    public int current;
    public int endDate;
    public int name;
    public int startDate;
    public int titleOrMajor;

    public ItemViewIds(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      this.name = paramInt1;
      this.titleOrMajor = paramInt2;
      this.startDate = paramInt3;
      this.endDate = paramInt4;
      this.current = paramInt5;
    }
  }

  private final class LocationCheckboxWatcher
    implements CompoundButton.OnCheckedChangeListener
  {
    private final boolean mOriginalValue;

    public LocationCheckboxWatcher(boolean arg2)
    {
      boolean bool;
      this.mOriginalValue = bool;
    }

    public final void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
    {
      if (paramBoolean)
      {
        View localView1 = (View)paramCompoundButton.getTag();
        LinearLayout localLinearLayout = (LinearLayout)localView1.getParent();
        int i = localLinearLayout.getChildCount();
        for (int j = 0; j < i; j++)
        {
          View localView2 = localLinearLayout.getChildAt(j);
          if (localView2 != localView1)
            ((CheckBox)localView2.findViewById(((ProfileEditFragment.ItemViewIds)localView2.getTag()).current)).setChecked(false);
        }
      }
      if (this.mOriginalValue == paramBoolean)
        ProfileEditFragment.this.removeChangedField(paramCompoundButton);
      while (true)
      {
        return;
        ProfileEditFragment.this.addChangedField(paramCompoundButton);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.ProfileEditFragment
 * JD-Core Version:    0.6.2
 */