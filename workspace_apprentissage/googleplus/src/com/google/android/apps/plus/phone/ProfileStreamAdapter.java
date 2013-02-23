package com.google.android.apps.plus.phone;

import android.animation.LayoutTransition;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsLocalPageData;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsPeopleData.ProfileAndContactData;
import com.google.android.apps.plus.fragments.CircleNameResolver;
import com.google.android.apps.plus.util.PrimitiveUtils;
import com.google.android.apps.plus.views.ColumnGridView;
import com.google.android.apps.plus.views.ColumnGridView.LayoutParams;
import com.google.android.apps.plus.views.ItemClickListener;
import com.google.android.apps.plus.views.ProfileAboutView;
import com.google.android.apps.plus.views.ProfileAboutView.DisplayPolicies;
import com.google.android.apps.plus.views.ProfileAboutView.OnClickListener;
import com.google.android.apps.plus.views.StreamCardView.StreamMediaClickListener;
import com.google.android.apps.plus.views.StreamCardView.StreamPlusBarClickListener;
import com.google.api.services.plusi.model.AggregatedReviewsProto;
import com.google.api.services.plusi.model.AuthorityPageProto;
import com.google.api.services.plusi.model.BirthdayField;
import com.google.api.services.plusi.model.CoarseDate;
import com.google.api.services.plusi.model.CommonConfig;
import com.google.api.services.plusi.model.CommonContent;
import com.google.api.services.plusi.model.Contacts;
import com.google.api.services.plusi.model.DataPlusOne;
import com.google.api.services.plusi.model.DateInfo;
import com.google.api.services.plusi.model.Description;
import com.google.api.services.plusi.model.Education;
import com.google.api.services.plusi.model.Educations;
import com.google.api.services.plusi.model.EducationsJson;
import com.google.api.services.plusi.model.Employment;
import com.google.api.services.plusi.model.Employments;
import com.google.api.services.plusi.model.EmploymentsJson;
import com.google.api.services.plusi.model.FrontendPaperProto;
import com.google.api.services.plusi.model.Gender;
import com.google.api.services.plusi.model.GoogleReviewProto;
import com.google.api.services.plusi.model.IntField;
import com.google.api.services.plusi.model.KnownForTermsProto;
import com.google.api.services.plusi.model.Links;
import com.google.api.services.plusi.model.LocalEntityInfo;
import com.google.api.services.plusi.model.Locations;
import com.google.api.services.plusi.model.LocationsJson;
import com.google.api.services.plusi.model.Name;
import com.google.api.services.plusi.model.Page;
import com.google.api.services.plusi.model.PhoneProto;
import com.google.api.services.plusi.model.PlacePageLink;
import com.google.api.services.plusi.model.PlacePagePhoneNumber;
import com.google.api.services.plusi.model.ProfilesLink;
import com.google.api.services.plusi.model.ReviewsHeadlineProto;
import com.google.api.services.plusi.model.ScrapBook;
import com.google.api.services.plusi.model.ScrapBookEntry;
import com.google.api.services.plusi.model.ScrapbookInfo;
import com.google.api.services.plusi.model.ScrapbookInfoFullBleedPhoto;
import com.google.api.services.plusi.model.ScrapbookInfoOffset;
import com.google.api.services.plusi.model.SharingRosterDataJson;
import com.google.api.services.plusi.model.SimpleProfile;
import com.google.api.services.plusi.model.SocialGraphData;
import com.google.api.services.plusi.model.StringField;
import com.google.api.services.plusi.model.TaggedAddress;
import com.google.api.services.plusi.model.TaggedEmail;
import com.google.api.services.plusi.model.TaggedPhone;
import com.google.api.services.plusi.model.User;
import com.google.api.services.plusi.model.ZagatAspectRatingsProto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class ProfileStreamAdapter extends StreamAdapter
{
  private boolean mBlockRequestPending;
  CircleNameResolver mCircleNameResolver;
  ArrayList<String> mCircleNames;
  private EsPeopleData.ProfileAndContactData mData;
  private String mErrorText;
  String mFamilyName;
  String mFullName;
  String mGender;
  String mGivenName;
  boolean mHasCoverPhotoUpgrade;
  boolean mHasProfile;
  boolean mIsBlocked;
  private boolean mIsEditEnabled;
  private boolean mIsLocalPlusPage;
  boolean mIsMuted;
  boolean mIsMyProfile;
  private boolean mIsPlusPage;
  boolean mIsSmsIntentRegistered;
  private boolean mIsUnclaimedLocalPlusPage;
  private String mPackedCircleIds;
  private String mPersonId;
  boolean mPlusOneByMe;
  int mPlusOnes = -1;
  private SimpleProfile mProfile;
  boolean mProfileLoadFailed;
  private ProfileAboutView.OnClickListener mProfileViewOnClickListener;
  String mScrapbookAlbumId;
  String mScrapbookCoverPhotoId;
  int mScrapbookCoverPhotoOffset;
  String mScrapbookCoverPhotoOwnerType;
  String mScrapbookCoverPhotoUrl;
  String mScrapbookLayout;
  boolean mShowAddToCircles;
  boolean mShowBlocked;
  boolean mShowCircles;
  boolean mShowProgress;
  private boolean mViewIsExpanded;
  private boolean mViewingAsPlusPage;

  public ProfileStreamAdapter(Context paramContext, ColumnGridView paramColumnGridView, EsAccount paramEsAccount, View.OnClickListener paramOnClickListener, ItemClickListener paramItemClickListener, StreamAdapter.ViewUseListener paramViewUseListener, StreamCardView.StreamPlusBarClickListener paramStreamPlusBarClickListener, StreamCardView.StreamMediaClickListener paramStreamMediaClickListener, ComposeBarController paramComposeBarController)
  {
    super(paramContext, paramColumnGridView, paramEsAccount, paramOnClickListener, paramItemClickListener, paramViewUseListener, paramStreamPlusBarClickListener, paramStreamMediaClickListener, null);
  }

  private void addDateInfo(DateInfo paramDateInfo, StringBuilder paramStringBuilder)
  {
    int i = 1;
    int j;
    int k;
    if (paramDateInfo != null)
    {
      j = i;
      if ((j == 0) || (paramDateInfo.start == null) || (PrimitiveUtils.safeInt(paramDateInfo.start.year) == 0))
        break label78;
      k = i;
      label37: if ((j == 0) || (paramDateInfo.end == null) || (PrimitiveUtils.safeInt(paramDateInfo.end.year) == 0))
        break label84;
      label62: if ((k != 0) || (i != 0))
        break label89;
    }
    while (true)
    {
      return;
      j = 0;
      break;
      label78: k = 0;
      break label37;
      label84: i = 0;
      break label62;
      label89: if (paramStringBuilder.length() > 0)
        paramStringBuilder.append(", ");
      boolean bool = PrimitiveUtils.safeBoolean(paramDateInfo.current);
      if ((k != 0) && ((i != 0) || (bool)))
      {
        paramStringBuilder.append(paramDateInfo.start.year);
        paramStringBuilder.append(" - ");
        if (bool)
          paramStringBuilder.append(getString(R.string.profile_end_date_for_current));
        else
          paramStringBuilder.append(paramDateInfo.end.year);
      }
      else if (bool)
      {
        paramStringBuilder.append(getString(R.string.profile_end_date_for_current));
      }
      else if (k != 0)
      {
        paramStringBuilder.append(paramDateInfo.start.year);
      }
      else if (i != 0)
      {
        paramStringBuilder.append(paramDateInfo.end.year);
      }
    }
  }

  private void bindProfileAboutView(ProfileAboutView paramProfileAboutView)
  {
    if (this.mData == null);
    while (true)
    {
      return;
      label58: String str2;
      label130: label153: label210: String str6;
      label248: boolean bool2;
      label262: label428: SimpleProfile localSimpleProfile5;
      Object localObject;
      label459: SimpleProfile localSimpleProfile1;
      String str16;
      label500: boolean bool12;
      label549: SimpleProfile localSimpleProfile3;
      String str20;
      label586: int i10;
      label672: SimpleProfile localSimpleProfile2;
      List localList3;
      label734: int i8;
      label771: boolean bool13;
      label797: AuthorityPageProto localAuthorityPageProto;
      PlacePageLink localPlacePageLink;
      label846: int i9;
      if (this.mHasCoverPhotoUpgrade)
      {
        paramProfileAboutView.setCoverPhotoUrl(this.mScrapbookCoverPhotoUrl, this.mScrapbookCoverPhotoOffset, this.mIsMyProfile);
        String str1 = this.mProfile.content.photoUrl;
        if (TextUtils.isEmpty(str1))
          break label1183;
        paramProfileAboutView.setAvatarUrl(str1, this.mIsMyProfile);
        paramProfileAboutView.setName(this.mFullName, this.mGivenName, this.mFamilyName);
        if ((this.mProfile.config == null) || (this.mProfile.config.incomingConnections == null) || (this.mProfile.config.incomingConnections.value == null))
          break label1194;
        paramProfileAboutView.setAddedByCount(this.mProfile.config.incomingConnections.value);
        if (!this.mIsLocalPlusPage)
          break label1202;
        str2 = EsLocalPageData.getFullAddress(this.mProfile);
        paramProfileAboutView.setLocation(str2, false);
        if ((!this.mIsPlusPage) || (this.mPlusOnes == -1))
          break label1441;
        int i12 = R.string.stream_plus_one_count_with_plus;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(Math.max(this.mPlusOnes, 1));
        paramProfileAboutView.setPlusOneData(getString(i12, arrayOfObject2), this.mPlusOneByMe);
        if ((this.mProfile.content == null) || (this.mProfile.content.tagLine == null))
          break label1450;
        str6 = this.mProfile.content.tagLine.value;
        if (TextUtils.isEmpty(str6))
          break label1456;
        paramProfileAboutView.setTagLine(str6);
        boolean bool1 = this.mHasProfile;
        String str7 = null;
        if (!bool1)
        {
          int i11 = R.string.profile_not_on_google_plus;
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = this.mFullName;
          str7 = getString(i11, arrayOfObject1);
        }
        if ((str7 == null) && (this.mProfile.content != null) && (this.mProfile.content.introduction != null))
          str7 = this.mProfile.content.introduction.value.trim();
        if (!TextUtils.isEmpty(str7))
          paramProfileAboutView.setIntroduction(str7);
        if ((this.mProfile.content == null) || (this.mProfile.content.links == null) || (this.mProfile.content.links.link == null) || (this.mProfile.content.links.link.isEmpty()))
          break label1464;
        bool2 = true;
        if (!this.mIsLocalPlusPage)
          break label1708;
        if (this.mIsUnclaimedLocalPlusPage)
        {
          localSimpleProfile5 = this.mProfile;
          if (localSimpleProfile5.content != null)
            break label1470;
          localObject = null;
          if (!TextUtils.isEmpty((CharSequence)localObject))
            paramProfileAboutView.setAvatarUrl((String)localObject, false);
        }
        localSimpleProfile1 = this.mProfile;
        if (localSimpleProfile1.page.localInfo.paper.phone != null)
          break label1483;
        str16 = null;
        String str17 = EsLocalPageData.getCid(this.mProfile);
        paramProfileAboutView.setLocalActions(this.mFullName, str16, str17, str2);
        ZagatAspectRatingsProto localZagatAspectRatingsProto = this.mProfile.page.localInfo.paper.zagatAspectRatings;
        if (localZagatAspectRatingsProto == null)
          break label1548;
        bool12 = true;
        paramProfileAboutView.enableLocalEditorialReviewsSection(bool12);
        if (localZagatAspectRatingsProto != null)
        {
          localSimpleProfile3 = this.mProfile;
          if (localSimpleProfile3.page.localInfo.paper.zagatEditorialReview != null)
            break label1554;
          str20 = null;
          String str21 = EsLocalPageData.getPriceLabel(this.mProfile);
          String str22 = EsLocalPageData.getPriceValue(this.mProfile);
          SimpleProfile localSimpleProfile4 = this.mProfile;
          if ((localSimpleProfile4.page.localInfo.paper.reviewsHeadline == null) || (localSimpleProfile4.page.localInfo.paper.reviewsHeadline.aggregatedReviews == null))
            break label1576;
          i10 = localSimpleProfile4.page.localInfo.paper.reviewsHeadline.aggregatedReviews.numReviews.intValue();
          paramProfileAboutView.setLocalEditorialReviews(localZagatAspectRatingsProto, str20, str21, str22, i10);
        }
        localSimpleProfile2 = this.mProfile;
        if ((localSimpleProfile2.page.localInfo.paper.knownForTerms != null) && (localSimpleProfile2.page.localInfo.paper.knownForTerms.term != null))
          break label1582;
        localList3 = Collections.emptyList();
        String str18 = EsLocalPageData.getOpeningHoursSummary(this.mProfile);
        String str19 = EsLocalPageData.getOpeningHoursFull(this.mProfile);
        if ((TextUtils.isEmpty(str18)) && (TextUtils.isEmpty(str19)))
          break label1604;
        i8 = 1;
        if ((TextUtils.isEmpty(str16)) && (localList3.size() <= 0) && (i8 == 0))
          break label1610;
        bool13 = true;
        paramProfileAboutView.enableLocalDetailsSection(bool13);
        if (bool13)
          paramProfileAboutView.setLocalDetails(localList3, str16, str18, str19);
        localAuthorityPageProto = this.mProfile.page.localInfo.paper.authorityPage;
        if (localAuthorityPageProto != null)
          break label1616;
        localPlacePageLink = null;
        if ((localPlacePageLink == null) || (TextUtils.isEmpty(localPlacePageLink.url)))
          break label1626;
        i9 = 1;
        label865: if ((i9 == 0) || (bool2))
          break label1632;
      }
      label1183: label1194: label1202: label1464: label1470: label1483: label1616: label1626: label1632: for (boolean bool14 = true; ; bool14 = false)
      {
        paramProfileAboutView.enableHompageSection(bool14);
        if (bool14)
          paramProfileAboutView.setHomepage(localPlacePageLink.url, localPlacePageLink.text, "https://www.google.com/s2/u/0/favicons?domain=" + Uri.parse(localPlacePageLink.url).getHost());
        boolean bool15 = EsLocalPageData.hasYourActivity(this.mProfile);
        paramProfileAboutView.clearAllReviews();
        paramProfileAboutView.enableLocalYourActivitySection(bool15);
        GoogleReviewProto localGoogleReviewProto = EsLocalPageData.getYourReview(this.mProfile);
        if (localGoogleReviewProto != null)
          paramProfileAboutView.addYourReview(localGoogleReviewProto);
        paramProfileAboutView.enableLocalYourCirclesActivitySection(EsLocalPageData.hasCircleActivity(this.mProfile));
        Iterator localIterator6 = EsLocalPageData.getCircleReviews(this.mProfile).iterator();
        while (localIterator6.hasNext())
          paramProfileAboutView.addCircleReview((GoogleReviewProto)localIterator6.next());
        if ((this.mProfile.content.scrapbook != null) && (this.mProfile.content.scrapbook.albumId != null) && (this.mProfile.content.scrapbook.plusiEntry != null))
        {
          int i13 = this.mProfile.content.scrapbook.plusiEntry.size();
          String[] arrayOfString = new String[i13];
          for (int i14 = 0; i14 < i13; i14++)
            arrayOfString[i14] = ((ScrapBookEntry)this.mProfile.content.scrapbook.plusiEntry.get(i14)).url;
          paramProfileAboutView.setScrapbookAlbumUrls(Long.valueOf(Long.parseLong(this.mProfile.content.scrapbook.albumId)), arrayOfString, this.mIsMyProfile);
          break;
        }
        paramProfileAboutView.setCoverPhotoToDefault(this.mIsMyProfile);
        break;
        paramProfileAboutView.setAvatarToDefault(this.mIsMyProfile);
        break label58;
        paramProfileAboutView.setAddedByCount(null);
        break label130;
        User localUser = this.mProfile.user;
        str2 = null;
        if (localUser == null)
          break label153;
        Employments localEmployments = this.mProfile.user.employments;
        String str3 = null;
        if (localEmployments != null)
        {
          List localList6 = this.mProfile.user.employments.employment;
          str3 = null;
          if (localList6 != null)
          {
            Employment localEmployment2 = (Employment)this.mProfile.user.employments.employment.get(0);
            str3 = null;
            if (localEmployment2 != null)
              str3 = localEmployment2.employer;
          }
        }
        paramProfileAboutView.setEmployer(str3);
        Locations localLocations = this.mProfile.user.locations;
        String str4 = null;
        if (localLocations != null)
          str4 = this.mProfile.user.locations.currentLocation;
        paramProfileAboutView.setLocation(str4, true);
        Educations localEducations = this.mProfile.user.educations;
        String str5 = null;
        if (localEducations != null)
        {
          List localList5 = this.mProfile.user.educations.education;
          str5 = null;
          if (localList5 != null)
          {
            Education localEducation2 = (Education)this.mProfile.user.educations.education.get(0);
            str5 = null;
            if (localEducation2 != null)
              str5 = localEducation2.school;
          }
        }
        paramProfileAboutView.setEducation(str5);
        str2 = null;
        break label153;
        paramProfileAboutView.setPlusOneData(null, false);
        break label210;
        str6 = null;
        break label248;
        paramProfileAboutView.setTagLine(null);
        break label262;
        bool2 = false;
        break label428;
        localObject = localSimpleProfile5.content.photoUrl;
        break label459;
        if (localSimpleProfile1.page.localInfo.paper.phone.phoneNumber.size() == 0)
        {
          str16 = null;
          break label500;
        }
        str16 = ((PlacePagePhoneNumber)localSimpleProfile1.page.localInfo.paper.phone.phoneNumber.get(0)).formattedPhone;
        break label500;
        bool12 = false;
        break label549;
        str20 = localSimpleProfile3.page.localInfo.paper.zagatEditorialReview.text;
        break label586;
        i10 = 0;
        break label672;
        localList3 = localSimpleProfile2.page.localInfo.paper.knownForTerms.term;
        break label734;
        i8 = 0;
        break label771;
        bool13 = false;
        break label797;
        localPlacePageLink = localAuthorityPageProto.authorityLink;
        break label846;
        i9 = 0;
        break label865;
      }
      label1441: label1450: label1456: label1604: label1610: List localList4 = EsLocalPageData.getReviews(this.mProfile);
      label1548: label1554: label1576: label1582: if (!localList4.isEmpty());
      for (boolean bool16 = true; ; bool16 = false)
      {
        paramProfileAboutView.enableLocalReviewsSection(bool16);
        Iterator localIterator7 = localList4.iterator();
        while (localIterator7.hasNext())
          paramProfileAboutView.addLocalReview((GoogleReviewProto)localIterator7.next());
      }
      label1708: Contacts localContacts;
      int i;
      int j;
      int k;
      if (this.mProfile.content == null)
      {
        localContacts = null;
        i = 0;
        j = 0;
        k = 0;
        if (localContacts != null)
        {
          if ((localContacts.email == null) || (localContacts.email.isEmpty()))
            break label1953;
          j = 1;
          label1759: if ((localContacts.phone == null) || (localContacts.phone.isEmpty()))
            break label1959;
          k = 1;
          label1783: if ((localContacts.address == null) || (localContacts.address.isEmpty()))
            break label1965;
          i = 1;
        }
        label1807: if ((j == 0) && (k == 0) && (i == 0))
          break label1971;
      }
      label1953: label1959: label1965: label1971: for (boolean bool3 = true; ; bool3 = false)
      {
        paramProfileAboutView.enableContactSection(bool3);
        paramProfileAboutView.clearEmails();
        paramProfileAboutView.clearPhoneNumbers();
        paramProfileAboutView.clearAddresses();
        if (j == 0)
          break label1977;
        Iterator localIterator5 = localContacts.email.iterator();
        while (localIterator5.hasNext())
        {
          TaggedEmail localTaggedEmail = (TaggedEmail)localIterator5.next();
          boolean bool11 = this.mIsPlusPage;
          String str15 = null;
          if (!bool11)
            str15 = EsPeopleData.getStringForEmailType(this.mContext, localTaggedEmail.tag);
          if (str15 == null)
            str15 = getString(R.string.profile_item_email);
          paramProfileAboutView.addEmail(localTaggedEmail.value, str15);
        }
        localContacts = this.mProfile.content.contacts;
        break;
        j = 0;
        break label1759;
        k = 0;
        break label1783;
        i = 0;
        break label1807;
      }
      label1977: if (k != 0)
      {
        Iterator localIterator4 = localContacts.phone.iterator();
        if (localIterator4.hasNext())
        {
          TaggedPhone localTaggedPhone = (TaggedPhone)localIterator4.next();
          if (this.mIsPlusPage);
          for (String str13 = EsPeopleData.getStringForPlusPagePhoneType(this.mContext, localTaggedPhone.tag); ; str13 = EsPeopleData.getStringForPhoneType(this.mContext, localTaggedPhone.tag))
          {
            if (str13 == null)
              str13 = getString(R.string.profile_item_phone);
            String str14 = localTaggedPhone.value;
            boolean bool10 = this.mIsSmsIntentRegistered;
            paramProfileAboutView.addPhoneNumber(str14, str13, bool10);
            break;
          }
        }
      }
      if (i != 0)
      {
        Iterator localIterator3 = localContacts.address.iterator();
        while (localIterator3.hasNext())
        {
          TaggedAddress localTaggedAddress = (TaggedAddress)localIterator3.next();
          boolean bool9 = this.mIsPlusPage;
          String str12 = null;
          if (!bool9)
            str12 = EsPeopleData.getStringForAddress(this.mContext, localTaggedAddress.tag);
          if (str12 == null)
            str12 = getString(R.string.profile_item_address);
          paramProfileAboutView.addAddress(localTaggedAddress.value, str12);
        }
      }
      paramProfileAboutView.updateContactSectionDividers();
      int i6;
      int i7;
      label2281: boolean bool8;
      label2294: String str11;
      if (!this.mIsPlusPage)
      {
        if ((this.mGender != null) && (!"UNKNOWN".equals(this.mGender)) && (!"OTHER".equals(this.mGender)))
        {
          i6 = 1;
          if ((this.mProfile.user == null) || (this.mProfile.user.birthday == null) || (TextUtils.isEmpty(this.mProfile.user.birthday.value)))
            break label2552;
          i7 = 1;
          if ((i6 == 0) && (i7 == 0))
            break label2558;
          bool8 = true;
          paramProfileAboutView.enablePersonalSection(bool8);
          if (i6 == 0)
            break label2589;
          str11 = "";
          if (!"MALE".equals(this.mGender))
            break label2564;
          str11 = getString(R.string.profile_item_gender_male);
          label2332: paramProfileAboutView.setGender(str11);
          label2338: if (i7 == 0)
            break label2597;
          paramProfileAboutView.setBirthday(this.mProfile.user.birthday.value);
          label2360: paramProfileAboutView.updatePersonalSectionDividers();
        }
      }
      else
        if ((this.mProfile.user == null) || (this.mProfile.user.employments == null) || (this.mProfile.user.employments.employment == null) || (this.mProfile.user.employments.employment.size() <= 0))
          break label2605;
      label2564: label2589: label2597: label2605: for (int m = 1; ; m = 0)
      {
        paramProfileAboutView.clearEmploymentLocations();
        if (m == 0)
          break label2611;
        List localList2 = this.mProfile.user.employments.employment;
        int i4 = localList2.size();
        for (int i5 = 0; i5 < i4; i5++)
        {
          Employment localEmployment1 = (Employment)localList2.get(i5);
          StringBuilder localStringBuilder2 = new StringBuilder();
          if (!TextUtils.isEmpty(localEmployment1.title))
            localStringBuilder2.append(localEmployment1.title);
          addDateInfo(localEmployment1.dateInfo, localStringBuilder2);
          paramProfileAboutView.addEmploymentLocation(localEmployment1.employer, localStringBuilder2.toString());
        }
        i6 = 0;
        break;
        label2552: i7 = 0;
        break label2281;
        label2558: bool8 = false;
        break label2294;
        if (!"FEMALE".equals(this.mGender))
          break label2332;
        str11 = getString(R.string.profile_item_gender_female);
        break label2332;
        paramProfileAboutView.setGender(null);
        break label2338;
        paramProfileAboutView.setBirthday(null);
        break label2360;
      }
      label2611: if (this.mIsMyProfile)
        paramProfileAboutView.setNoEmploymentLocations();
      boolean bool4;
      if ((m != 0) || (this.mIsEditEnabled))
      {
        bool4 = true;
        paramProfileAboutView.enableWorkSection(bool4);
        if ((this.mProfile.user == null) || (this.mProfile.user.educations == null) || (this.mProfile.user.educations.education == null) || (this.mProfile.user.educations.education.size() <= 0))
          break label2831;
      }
      label2831: for (int n = 1; ; n = 0)
      {
        paramProfileAboutView.clearEducationLocations();
        if (n == 0)
          break label2837;
        List localList1 = this.mProfile.user.educations.education;
        int i2 = localList1.size();
        for (int i3 = 0; i3 < i2; i3++)
        {
          Education localEducation1 = (Education)localList1.get(i3);
          StringBuilder localStringBuilder1 = new StringBuilder();
          if (!TextUtils.isEmpty(localEducation1.majorConcentration))
          {
            localStringBuilder1.append(localEducation1.majorConcentration);
            addDateInfo(localEducation1.dateInfo, localStringBuilder1);
          }
          paramProfileAboutView.addEducationLocation(localEducation1.school, localStringBuilder1.toString());
        }
        bool4 = false;
        break;
      }
      label2837: if (this.mIsMyProfile)
        paramProfileAboutView.setNoEducationLocations();
      boolean bool5;
      if ((n != 0) || (this.mIsEditEnabled))
      {
        bool5 = true;
        paramProfileAboutView.enableEducationSection(bool5);
        if ((this.mProfile.user == null) || (this.mProfile.user.locations == null) || ((TextUtils.isEmpty(this.mProfile.user.locations.currentLocation)) && ((this.mProfile.user.locations.otherLocation == null) || (this.mProfile.user.locations.otherLocation.isEmpty()))))
          break label3111;
      }
      label3111: for (int i1 = 1; ; i1 = 0)
      {
        if (i1 == 0)
          break label3117;
        paramProfileAboutView.setLocationUrl(this.mProfile.user.locations.locationMapUrl);
        paramProfileAboutView.clearLocations();
        if (this.mProfile.user.locations.currentLocation != null)
        {
          String str10 = this.mProfile.user.locations.currentLocation.trim();
          if (str10.length() != 0)
            paramProfileAboutView.addLocation(str10, true);
        }
        if (this.mProfile.user.locations.otherLocation == null)
          break label3133;
        Iterator localIterator2 = this.mProfile.user.locations.otherLocation.iterator();
        while (localIterator2.hasNext())
        {
          String str9 = ((String)localIterator2.next()).trim();
          if (str9.length() != 0)
            paramProfileAboutView.addLocation(str9, false);
        }
        bool5 = false;
        break;
      }
      label3117: if (this.mIsMyProfile)
      {
        paramProfileAboutView.setLocationUrl(null);
        paramProfileAboutView.setNoLocations();
      }
      label3133: if ((i1 != 0) || (this.mIsEditEnabled));
      for (boolean bool6 = true; ; bool6 = false)
      {
        paramProfileAboutView.enableLocationsSection(bool6);
        paramProfileAboutView.updateLocationsSectionDividers();
        paramProfileAboutView.enableLinksSection(bool2);
        paramProfileAboutView.clearLinks();
        if (!bool2)
          break;
        Iterator localIterator1 = this.mProfile.content.links.link.iterator();
        while (localIterator1.hasNext())
        {
          ProfilesLink localProfilesLink = (ProfilesLink)localIterator1.next();
          if (localProfilesLink.url != null)
          {
            boolean bool7 = this.mIsPlusPage;
            String str8 = null;
            if (bool7)
              str8 = getString(R.string.profile_item_website);
            paramProfileAboutView.addLink(localProfilesLink.url, localProfilesLink.label, localProfilesLink.faviconImgUrl, str8);
          }
        }
      }
      paramProfileAboutView.updateLinksSectionDividers();
      if (this.mShowCircles)
        paramProfileAboutView.setCircles(this.mCircleNames);
      else if (this.mShowAddToCircles)
        paramProfileAboutView.showAddToCircles(this.mIsPlusPage);
      else if (this.mShowBlocked)
        paramProfileAboutView.showBlocked();
      else if (this.mShowProgress)
        paramProfileAboutView.showProgress();
      else
        paramProfileAboutView.showNone();
    }
  }

  private String getString(int paramInt)
  {
    return this.mContext.getString(paramInt);
  }

  private String getString(int paramInt, Object[] paramArrayOfObject)
  {
    return this.mContext.getString(paramInt, paramArrayOfObject);
  }

  public final void beginBlockInProgress()
  {
    this.mBlockRequestPending = true;
    updateCircleList();
    notifyDataSetChanged();
  }

  public final void bindStreamView(View paramView, Cursor paramCursor)
  {
    ProfileAboutView.DisplayPolicies localDisplayPolicies;
    if (paramCursor.getPosition() == 0)
    {
      if (Log.isLoggable("ProfileAdapter", 3))
        Log.d("ProfileAdapter", "bindView(); " + paramView);
      ProfileAboutView localProfileAboutView = (ProfileAboutView)paramView;
      localProfileAboutView.init(this.mViewIsExpanded, this.mIsEditEnabled);
      localProfileAboutView.showError(this.mProfileLoadFailed, this.mErrorText);
      if (this.mProfile != null)
      {
        localDisplayPolicies = new ProfileAboutView.DisplayPolicies();
        switch (sScreenMetrics.screenDisplayType)
        {
        default:
          if (this.mLandscape)
          {
            localDisplayPolicies.showInfoIcons = true;
            localDisplayPolicies.showDetailsAlways = true;
            if ((this.mIsUnclaimedLocalPlusPage) && (!this.mLandscape))
              localDisplayPolicies.showDetailsAlways = true;
            if ((this.mIsMyProfile) && (this.mLandscape))
              localDisplayPolicies.hideButtons = true;
            localProfileAboutView.setDisplayPolicies(localDisplayPolicies);
            bindProfileAboutView(localProfileAboutView);
            localProfileAboutView.setOnClickListener(this.mProfileViewOnClickListener);
          }
          break;
        case 1:
        }
      }
    }
    while (true)
    {
      return;
      localDisplayPolicies.showInfoIcons = true;
      if (this.mLandscape)
      {
        localDisplayPolicies.showDetailsAlways = true;
        break;
      }
      localDisplayPolicies.showExpandButtonText = true;
      break;
      localDisplayPolicies.showInfoIcons = false;
      break;
      super.bindStreamView(paramView, paramCursor);
    }
  }

  public final void endBlockInProgress(boolean paramBoolean)
  {
    this.mBlockRequestPending = false;
    if (paramBoolean)
    {
      boolean bool1 = this.mIsBlocked;
      boolean bool2 = false;
      if (!bool1)
        bool2 = true;
      this.mIsBlocked = bool2;
    }
    updateCircleList();
    notifyDataSetChanged();
  }

  public final int getCount()
  {
    return super.getCount();
  }

  public final String getEducationList()
  {
    if ((this.mProfile == null) || (this.mProfile.user == null) || (this.mProfile.user.educations == null));
    for (String str = "{}"; ; str = EducationsJson.getInstance().toString(this.mProfile.user.educations))
      return str;
  }

  public final String getEmploymentList()
  {
    if ((this.mProfile == null) || (this.mProfile.user == null) || (this.mProfile.user.employments == null));
    for (String str = "{}"; ; str = EmploymentsJson.getInstance().toString(this.mProfile.user.employments))
      return str;
  }

  public final String getFullName()
  {
    return this.mFullName;
  }

  public final String getGender()
  {
    return this.mGender;
  }

  public final String getGivenName()
  {
    return this.mGivenName;
  }

  public final long getItemId(int paramInt)
  {
    if (paramInt == 0);
    for (long l = 0L; ; l = super.getItemId(paramInt))
      return l;
  }

  public final String getPlacesLivedList()
  {
    if ((this.mProfile == null) || (this.mProfile.user == null) || (this.mProfile.user.locations == null));
    for (String str = "{}"; ; str = LocationsJson.getInstance().toString(this.mProfile.user.locations))
      return str;
  }

  public final String getScrapbookAlbumId()
  {
    return this.mScrapbookAlbumId;
  }

  public final Long getScrapbookCoverPhotoId()
  {
    if (!TextUtils.isEmpty(this.mScrapbookCoverPhotoId));
    for (Long localLong = Long.valueOf(Long.parseLong(this.mScrapbookCoverPhotoId)); ; localLong = null)
      return localLong;
  }

  public final int getScrapbookCoverPhotoOffset()
  {
    return this.mScrapbookCoverPhotoOffset;
  }

  public final String getScrapbookCoverPhotoOwnerId()
  {
    if ("GALLERY".equals(this.mScrapbookCoverPhotoOwnerType));
    for (String str = "115239603441691718952"; ; str = this.mAccount.getGaiaId())
      return str;
  }

  public final String getScrapbookCoverPhotoUrl()
  {
    return this.mScrapbookCoverPhotoUrl;
  }

  public final String getScrapbookLayout()
  {
    return this.mScrapbookLayout;
  }

  public final Long getScrapbookPhotoId(int paramInt)
  {
    SimpleProfile localSimpleProfile = this.mProfile;
    String str = null;
    if (localSimpleProfile != null)
    {
      CommonContent localCommonContent = this.mProfile.content;
      str = null;
      if (localCommonContent != null)
      {
        ScrapBook localScrapBook1 = this.mProfile.content.scrapbook;
        str = null;
        if (localScrapBook1 != null)
        {
          ScrapBook localScrapBook2 = this.mProfile.content.scrapbook;
          if (paramInt != 0)
            break label87;
          str = localScrapBook2.coverPhotoEntry.photoId;
        }
      }
    }
    if (str != null);
    for (Long localLong = Long.valueOf(Long.parseLong(str)); ; localLong = null)
    {
      return localLong;
      label87: List localList = this.mProfile.content.scrapbook.plusiEntry;
      str = null;
      if (localList == null)
        break;
      int i = localList.size();
      int j = paramInt - 1;
      str = null;
      if (i <= j)
        break;
      str = ((ScrapBookEntry)localList.get(paramInt - 1)).photoId;
      break;
    }
  }

  public final String getSharingRosterData()
  {
    if ((this.mProfile == null) || (this.mProfile.rosterData == null));
    for (String str = "{}"; ; str = SharingRosterDataJson.getInstance().toString(this.mProfile.rosterData))
      return str;
  }

  public final int getStreamItemViewType(int paramInt)
  {
    int i;
    if (paramInt == 0)
      if (this.mLandscape)
        i = 11;
    while (true)
    {
      return i;
      i = 10;
      continue;
      i = super.getStreamItemViewType(paramInt);
    }
  }

  public final boolean getViewIsExpanded()
  {
    return this.mViewIsExpanded;
  }

  public final int getViewTypeCount()
  {
    return 2 + super.getViewTypeCount();
  }

  public final boolean hasCoverPhotoUpgrade()
  {
    return this.mHasCoverPhotoUpgrade;
  }

  public final void init(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, CircleNameResolver paramCircleNameResolver)
  {
    this.mPersonId = paramString;
    this.mIsMyProfile = paramBoolean1;
    this.mHasProfile = paramBoolean2;
    this.mIsSmsIntentRegistered = paramBoolean3;
    this.mCircleNameResolver = paramCircleNameResolver;
    this.mViewingAsPlusPage = this.mAccount.isPlusPage();
  }

  public final boolean isBlocked()
  {
    return this.mIsBlocked;
  }

  public final boolean isMuted()
  {
    return this.mIsMuted;
  }

  public final boolean isPlusOnedByMe()
  {
    return this.mPlusOneByMe;
  }

  public final boolean isPlusPage()
  {
    return this.mIsPlusPage;
  }

  public final View newStreamView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    int i = -3;
    int j = 2;
    int k = 1;
    Object localObject;
    int m;
    int n;
    if (paramCursor.getPosition() == 0)
    {
      localObject = (ProfileAboutView)((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(R.layout.profile_about_fragment, null);
      if (this.mLandscape)
      {
        m = k;
        switch (sScreenMetrics.screenDisplayType)
        {
        default:
          if (this.mLandscape)
          {
            n = (int)(0.7D * sScreenMetrics.longDimension);
            label97: i = n;
            j = k;
            label105: ColumnGridView.LayoutParams localLayoutParams = new ColumnGridView.LayoutParams(m, i, k, j);
            if (!this.mLandscape)
              localLayoutParams.height = -2;
            ((ProfileAboutView)localObject).setLayoutParams(localLayoutParams);
            if (Build.VERSION.SDK_INT >= 11)
              ((ProfileAboutView)localObject).setLayoutTransition(new LayoutTransition());
            if (Log.isLoggable("ProfileAdapter", 3))
              Log.d("ProfileAdapter", "newView() -> " + localObject);
          }
          break;
        case 1:
        }
      }
    }
    while (true)
    {
      return localObject;
      m = j;
      break;
      k = j;
      break label105;
      n = i;
      break label97;
      localObject = super.newStreamView(paramContext, paramCursor, paramViewGroup);
    }
  }

  public final void setOnClickListener(ProfileAboutView.OnClickListener paramOnClickListener)
  {
    this.mProfileViewOnClickListener = paramOnClickListener;
  }

  public final void setProfileData(EsPeopleData.ProfileAndContactData paramProfileAndContactData)
  {
    boolean bool1 = true;
    if (paramProfileAndContactData == null)
      return;
    this.mData = paramProfileAndContactData;
    this.mProfile = paramProfileAndContactData.profile;
    label155: label243: String str;
    label264: boolean bool2;
    label330: boolean bool3;
    if (this.mProfile == null)
    {
      this.mProfile = new SimpleProfile();
      this.mProfile.profileType = "USER";
      if (this.mPersonId.startsWith("e:"))
      {
        this.mProfile.content = new CommonContent();
        this.mProfile.content.contacts = new Contacts();
        this.mProfile.content.contacts.email = new ArrayList();
        TaggedEmail localTaggedEmail = new TaggedEmail();
        localTaggedEmail.value = this.mPersonId.substring(2);
        this.mProfile.content.contacts.email.add(localTaggedEmail);
      }
    }
    else
    {
      if (this.mProfile.profileType != null)
      {
        if (!"USER".equals(this.mProfile.profileType))
          break label768;
        if ((this.mProfile.user != null) && (this.mProfile.user.name != null))
        {
          this.mGivenName = this.mProfile.user.name.given;
          this.mFamilyName = this.mProfile.user.name.family;
        }
        this.mIsPlusPage = false;
      }
      if (!TextUtils.isEmpty(this.mProfile.displayName))
        break label972;
      str = getString(R.string.profile_unknown_name);
      this.mFullName = str;
      this.mIsBlocked = paramProfileAndContactData.blocked;
      this.mPackedCircleIds = paramProfileAndContactData.packedCircleIds;
      if ((this.mProfile.config == null) || (this.mProfile.config.socialGraphData == null) || (!PrimitiveUtils.safeBoolean(this.mProfile.config.socialGraphData.muted)))
        break label983;
      bool2 = bool1;
      this.mIsMuted = bool2;
      updateCircleList();
      if (this.mProfile.content != null)
      {
        ScrapBook localScrapBook = this.mProfile.content.scrapbook;
        if (localScrapBook != null)
        {
          if (localScrapBook.albumId != null)
            this.mScrapbookAlbumId = localScrapBook.albumId;
          if (localScrapBook.coverPhotoEntry != null)
          {
            this.mScrapbookCoverPhotoId = localScrapBook.coverPhotoEntry.photoId;
            this.mScrapbookCoverPhotoUrl = localScrapBook.coverPhotoEntry.cropUrl;
            if ((this.mScrapbookCoverPhotoUrl == null) && (localScrapBook.plusiEntry != null) && (localScrapBook.plusiEntry.size() > 0))
              this.mScrapbookCoverPhotoUrl = ((ScrapBookEntry)localScrapBook.plusiEntry.get(0)).cropUrl;
          }
        }
        if (this.mProfile.content.scrapbookInfo != null)
        {
          this.mScrapbookLayout = this.mProfile.content.scrapbookInfo.layout;
          if ((!"FULL_BLEED".equals(this.mScrapbookLayout)) && (!"COVER".equals(this.mScrapbookLayout)))
            break label989;
          bool3 = bool1;
          label524: this.mHasCoverPhotoUpgrade = bool3;
          if (this.mProfile.content.scrapbookInfo.fullBleedPhoto != null)
          {
            if (this.mProfile.content.scrapbookInfo.fullBleedPhoto.offset != null)
              this.mScrapbookCoverPhotoOffset = PrimitiveUtils.safeInt(this.mProfile.content.scrapbookInfo.fullBleedPhoto.offset.top);
            this.mScrapbookCoverPhotoOwnerType = this.mProfile.content.scrapbookInfo.fullBleedPhoto.photoOwnerType;
          }
        }
      }
      if (!this.mIsPlusPage)
        break label995;
      this.mGender = "OTHER";
      label625: if ((!this.mIsMyProfile) || (this.mIsPlusPage))
        break label1064;
    }
    while (true)
    {
      this.mIsEditEnabled = bool1;
      if (this.mData == null)
        break;
      notifyDataSetChanged();
      break;
      if (!this.mPersonId.startsWith("p:"))
        break label155;
      this.mProfile.content = new CommonContent();
      this.mProfile.content.contacts = new Contacts();
      this.mProfile.content.contacts.phone = new ArrayList();
      TaggedPhone localTaggedPhone = new TaggedPhone();
      localTaggedPhone.value = this.mPersonId.substring(2);
      this.mProfile.content.contacts.phone.add(localTaggedPhone);
      break label155;
      label768: if (!"PLUSPAGE".equals(this.mProfile.profileType))
        break label243;
      this.mIsPlusPage = bool1;
      SimpleProfile localSimpleProfile = this.mProfile;
      boolean bool4;
      if (localSimpleProfile == null)
        bool4 = false;
      while (true)
      {
        this.mIsLocalPlusPage = bool4;
        if (this.mIsLocalPlusPage)
          this.mIsUnclaimedLocalPlusPage = "UNCLAIMED".equals(this.mProfile.page.localInfo.type);
        DataPlusOne localDataPlusOne = paramProfileAndContactData.profile.page.plusone;
        this.mPlusOnes = localDataPlusOne.globalCount.intValue();
        this.mPlusOneByMe = localDataPlusOne.isPlusonedByViewer.booleanValue();
        break;
        if (!"PLUSPAGE".equals(localSimpleProfile.profileType))
          bool4 = false;
        else if (localSimpleProfile.page == null)
          bool4 = false;
        else if (!"LOCAL".equals(localSimpleProfile.page.type))
          bool4 = false;
        else if ((localSimpleProfile.page.localInfo == null) || (localSimpleProfile.page.localInfo.paper == null))
          bool4 = false;
        else
          bool4 = bool1;
      }
      label972: str = this.mProfile.displayName;
      break label264;
      label983: bool2 = false;
      break label330;
      label989: bool3 = false;
      break label524;
      label995: if ((this.mProfile.user != null) && (this.mProfile.user.gender != null) && (this.mProfile.user.gender.value != null))
      {
        this.mGender = this.mProfile.user.gender.value;
        break label625;
      }
      this.mGender = "UNKNOWN";
      break label625;
      label1064: bool1 = false;
    }
  }

  public final void setViewIsExpanded(boolean paramBoolean)
  {
    this.mViewIsExpanded = paramBoolean;
  }

  public final void showError(String paramString)
  {
    this.mProfileLoadFailed = true;
    this.mErrorText = paramString;
    notifyDataSetChanged();
  }

  public final void updateCircleList()
  {
    if ((this.mIsMyProfile) || (this.mIsUnclaimedLocalPlusPage) || (this.mProfile == null) || (!this.mCircleNameResolver.isLoaded()))
    {
      this.mShowProgress = false;
      this.mShowBlocked = false;
      this.mShowAddToCircles = false;
      this.mShowCircles = false;
    }
    while (true)
    {
      return;
      if (this.mBlockRequestPending)
      {
        this.mShowBlocked = false;
        this.mShowAddToCircles = false;
        this.mShowCircles = false;
        this.mShowProgress = true;
      }
      else if (this.mIsBlocked)
      {
        this.mShowProgress = false;
        this.mShowAddToCircles = false;
        this.mShowCircles = false;
        this.mShowBlocked = true;
      }
      else if (TextUtils.isEmpty(this.mPackedCircleIds))
      {
        this.mShowBlocked = false;
        this.mShowProgress = false;
        this.mShowCircles = false;
        boolean bool1;
        if (!this.mIsPlusPage)
        {
          boolean bool2 = this.mViewingAsPlusPage;
          bool1 = false;
          if (bool2);
        }
        else
        {
          bool1 = true;
        }
        this.mShowAddToCircles = bool1;
      }
      else
      {
        this.mShowBlocked = false;
        this.mShowProgress = false;
        this.mShowAddToCircles = false;
        this.mShowCircles = true;
        this.mCircleNames = this.mCircleNameResolver.getCircleNameListForPackedIds(this.mPackedCircleIds);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.ProfileStreamAdapter
 * JD-Core Version:    0.6.2
 */