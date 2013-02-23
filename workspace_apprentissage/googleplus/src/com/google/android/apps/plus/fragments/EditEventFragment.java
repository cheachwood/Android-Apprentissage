package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.style;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.CircleData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.TimeZoneSpinnerAdapter;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.EventDateUtils;
import com.google.android.apps.plus.util.StringUtils;
import com.google.android.apps.plus.util.TimeZoneHelper;
import com.google.android.apps.plus.util.TimeZoneHelper.TimeZoneInfo;
import com.google.android.apps.plus.views.EsImageView.OnImageLoadedListener;
import com.google.android.apps.plus.views.EventThemeView;
import com.google.android.apps.plus.views.TypeableAudienceView;
import com.google.api.services.plusi.model.EventOptions;
import com.google.api.services.plusi.model.EventTime;
import com.google.api.services.plusi.model.Place;
import com.google.api.services.plusi.model.PlaceJson;
import com.google.api.services.plusi.model.PlusEvent;
import com.google.api.services.plusi.model.PlusEventJson;
import com.google.api.services.plusi.model.ThemeSpecification;
import java.util.Calendar;
import java.util.TimeZone;

public class EditEventFragment extends EsFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener, AdapterView.OnItemSelectedListener, AlertFragmentDialog.AlertDialogListener, PeopleSearchAdapter.SearchListAdapterListener, EsImageView.OnImageLoadedListener
{
  private static final String[] EVENT_COLUMNS = { "event_data" };
  private static final String[] THEME_COLUMNS = { "theme_id", "image_url", "placeholder_path" };
  private PeopleSearchListAdapter mAudienceAdapter;
  private TypeableAudienceView mAudienceView;
  private String mAuthKey;
  private boolean mChanged;
  private int mCurrentSpinnerPosition;
  private EditText mDescriptionView;
  private Button mEndDateView;
  private Button mEndTimeView;
  private boolean mError;
  private PlusEvent mEvent;
  private TextWatcher mEventDescriptionTextWatcher = new TextWatcher()
  {
    public final void afterTextChanged(Editable paramAnonymousEditable)
    {
    }

    public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
    }

    public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      String str = EditEventFragment.this.mDescriptionView.getText().toString().trim();
      if (!TextUtils.equals(EditEventFragment.this.mEvent.description, str))
      {
        EditEventFragment.this.mEvent.description = str;
        EditEventFragment.access$1202(EditEventFragment.this, true);
        if (EditEventFragment.this.mListener == null);
      }
    }
  };
  private String mEventId;
  private boolean mEventLoaded;
  private TextWatcher mEventNameTextWatcher = new TextWatcher()
  {
    public final void afterTextChanged(Editable paramAnonymousEditable)
    {
    }

    public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
    }

    public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      String str = EditEventFragment.this.mEventNameView.getText().toString().trim();
      if (!TextUtils.equals(EditEventFragment.this.mEvent.name, str))
      {
        EditEventFragment.this.mEvent.name = str;
        EditEventFragment.access$1202(EditEventFragment.this, true);
        if (EditEventFragment.this.mListener == null);
      }
    }
  };
  private EditText mEventNameView;
  private int mEventThemeId;
  private EventThemeView mEventThemeView;
  private String mExternalId;
  private final Handler mHandler = new Handler(Looper.getMainLooper());
  private OnEditEventListener mListener;
  private TextView mLocationView;
  private boolean mNewEvent = true;
  private String mOwnerId;
  private Integer mPendingRequestId;
  private AudienceData mResultAudience;
  private final EsServiceListener mServiceListener = new EsServiceListener()
  {
    public final void onCreateEventComplete(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      EditEventFragment.this.handleServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }

    public final void onUpdateEventComplete(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      EditEventFragment.this.handleServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }
  };
  private Button mStartDateView;
  private Button mStartTimeView;
  private ProgressBar mThemeProgressBar;
  private View mThemeSelectionButton;
  private TextView mThemeSelectionTextView;
  private TimeZoneHelper mTimeZoneHelper;
  private Spinner mTimeZoneSpinner;
  private TimeZoneSpinnerAdapter mTimeZoneSpinnerAdapter;

  private void bindEndDate()
  {
    if (this.mEvent.endTime != null)
      this.mEndDateView.setText(EventDateUtils.getSingleDateDisplayLine(getActivity(), this.mEvent.endTime.timeMs.longValue(), getTimeZone(this.mEvent.endTime)));
    while (true)
    {
      return;
      this.mEndDateView.setText(null);
    }
  }

  private void bindEndTime()
  {
    if ((this.mEvent.endTime != null) && (getActivity() != null))
      this.mEndTimeView.setText(EventDateUtils.getDisplayTime(getActivity(), this.mEvent.endTime.timeMs.longValue(), getTimeZone(this.mEvent.endTime)));
    while (true)
    {
      return;
      this.mEndTimeView.setText(null);
    }
  }

  private void bindEvent()
  {
    if (this.mEvent == null)
      return;
    TypeableAudienceView localTypeableAudienceView = this.mAudienceView;
    if (this.mNewEvent);
    for (int i = 0; ; i = 8)
    {
      localTypeableAudienceView.setVisibility(i);
      this.mEventNameView.setText(this.mEvent.name);
      this.mDescriptionView.setText(this.mEvent.description);
      bindStartDate();
      bindEndDate();
      bindTimeZoneSpinner();
      bindStartTime();
      bindEndTime();
      bindLocation();
      break;
    }
  }

  private void bindLocation()
  {
    Place localPlace = this.mEvent.location;
    if (localPlace != null)
      this.mLocationView.setText(localPlace.name);
    while (true)
    {
      return;
      this.mLocationView.setText(null);
    }
  }

  private void bindStartDate()
  {
    this.mStartDateView.setText(EventDateUtils.getSingleDateDisplayLine(getActivity(), this.mEvent.startTime.timeMs.longValue(), getTimeZone(this.mEvent.startTime)));
  }

  private void bindStartTime()
  {
    if ((this.mEvent.startTime != null) && (getActivity() != null))
      this.mStartTimeView.setText(EventDateUtils.getDisplayTime(getActivity(), this.mEvent.startTime.timeMs.longValue(), getTimeZone(this.mEvent.startTime)));
  }

  private void bindTimeZoneSpinner()
  {
    if (this.mEvent.startTime != null)
    {
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTimeInMillis(this.mEvent.startTime.timeMs.longValue());
      TimeZoneHelper localTimeZoneHelper = this.mTimeZoneHelper;
      getActivity();
      localTimeZoneHelper.configure$622086bc(localCalendar);
      this.mTimeZoneSpinnerAdapter.setTimeZoneHelper(this.mTimeZoneHelper);
      this.mCurrentSpinnerPosition = this.mTimeZoneHelper.getTimeZonePos(this.mEvent.startTime.timezone, null);
      this.mTimeZoneSpinner.setSelection(this.mCurrentSpinnerPosition);
    }
  }

  private void clearEndTime()
  {
    this.mEvent.endTime = null;
  }

  private void enableEventPicker()
  {
    this.mHandler.post(new Runnable()
    {
      public final void run()
      {
        EditEventFragment.this.mThemeSelectionButton.setVisibility(0);
        EditEventFragment.this.mThemeSelectionTextView.setVisibility(0);
        EditEventFragment.this.mThemeProgressBar.setVisibility(8);
        EditEventFragment.this.mThemeSelectionButton.setLayoutParams(new FrameLayout.LayoutParams(EditEventFragment.this.mEventThemeView.getMeasuredWidth(), EditEventFragment.this.mEventThemeView.getMeasuredHeight()));
      }
    });
  }

  private EsAccount getAccount()
  {
    return (EsAccount)getActivity().getIntent().getExtras().get("account");
  }

  private static long getDefaultEventTime()
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(12, 90);
    localCalendar.set(12, 0);
    localCalendar.set(13, 0);
    localCalendar.set(14, 0);
    return localCalendar.getTimeInMillis();
  }

  private TimeZone getTimeZone(EventTime paramEventTime)
  {
    if (paramEventTime != null);
    for (TimeZone localTimeZone = this.mTimeZoneHelper.getTimeZone(paramEventTime.timezone, null); ; localTimeZone = this.mTimeZoneHelper.getCurrentTimeZoneInfo().getTimeZone())
      return localTimeZone;
  }

  private boolean isEmptyAudience()
  {
    AudienceData localAudienceData = this.mAudienceView.getAudience();
    if (localAudienceData.getCircleCount() + localAudienceData.getUserCount() == 0);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private void onAudienceChanged()
  {
    if (this.mListener != null);
  }

  private void recordUserAction(OzActions paramOzActions)
  {
    FragmentActivity localFragmentActivity = getActivity();
    EsAccount localEsAccount = getAccount();
    if (localEsAccount != null)
      EsAnalytics.recordActionEvent(localFragmentActivity, localEsAccount, paramOzActions, OzViews.getViewForLogging(localFragmentActivity));
  }

  private void setEndTime(Calendar paramCalendar)
  {
    long l = paramCalendar.getTimeInMillis();
    TimeZone localTimeZone = paramCalendar.getTimeZone();
    if (this.mEvent.endTime == null)
    {
      this.mEvent.endTime = new EventTime();
      this.mEvent.endTime.timeMs = Long.valueOf(getDefaultEventTime());
    }
    if (this.mEvent.endTime.timeMs.longValue() != l)
    {
      this.mEvent.endTime.timeMs = Long.valueOf(l);
      this.mEvent.endTime.timezone = localTimeZone.getID();
      this.mChanged = true;
    }
  }

  private void setEventTheme(int paramInt, String paramString, Uri paramUri, boolean paramBoolean)
  {
    if (this.mEvent == null);
    while (true)
    {
      return;
      if (this.mEvent.themeSpecification == null)
        this.mEvent.themeSpecification = new ThemeSpecification();
      if ((paramBoolean) || (this.mEvent.themeSpecification.themeId == null))
      {
        this.mEventThemeId = paramInt;
        this.mEvent.themeSpecification.themeId = Integer.valueOf(paramInt);
        if (paramUri != null)
        {
          this.mEventThemeView.setDefaultImageUri(paramUri);
          enableEventPicker();
        }
        this.mEventThemeView.setImageUrl(paramString);
      }
      else if (this.mEvent.themeSpecification.themeId.intValue() == paramInt)
      {
        this.mEventThemeId = paramInt;
        if (paramUri != null)
        {
          this.mEventThemeView.setDefaultImageUri(paramUri);
          enableEventPicker();
        }
        this.mEventThemeView.setImageUrl(paramString);
      }
    }
  }

  private void setStartTime(Calendar paramCalendar)
  {
    long l = paramCalendar.getTimeInMillis();
    TimeZone localTimeZone = paramCalendar.getTimeZone();
    if (this.mEvent.startTime.timezone != null);
    for (int i = 1; ; i = 0)
    {
      if ((this.mEvent.startTime.timeMs.longValue() != l) || (i == 0))
      {
        this.mEvent.startTime.timeMs = Long.valueOf(l);
        this.mEvent.startTime.timezone = localTimeZone.getID();
        bindTimeZoneSpinner();
        this.mChanged = true;
      }
      return;
    }
  }

  private void updateView(View paramView)
  {
    if ((paramView == null) || (this.mNewEvent));
    while (true)
    {
      return;
      TextView localTextView = (TextView)paramView.findViewById(R.id.server_error);
      View localView = paramView.findViewById(R.id.content);
      if (this.mEvent != null)
      {
        localTextView.setVisibility(8);
        localView.setVisibility(0);
        showContent(paramView);
      }
      else if (!this.mEventLoaded)
      {
        localView.setVisibility(8);
        localTextView.setVisibility(8);
        showEmptyViewProgress(paramView);
      }
      else if (this.mError)
      {
        localTextView.setVisibility(0);
        localTextView.setText(R.string.event_details_error);
        localView.setVisibility(8);
        showContent(paramView);
      }
      else
      {
        localTextView.setVisibility(0);
        localTextView.setText(R.string.event_does_not_exist);
        localView.setVisibility(8);
        showContent(paramView);
      }
    }
  }

  public final void createEvent()
  {
    if (this.mEvent == null)
    {
      this.mEvent = new PlusEvent();
      this.mEvent.eventOptions = new EventOptions();
      this.mEvent.eventOptions.openEventAcl = Boolean.valueOf(true);
      this.mEvent.eventOptions.openPhotoAcl = Boolean.valueOf(true);
      this.mEvent.startTime = new EventTime();
      this.mEvent.startTime.timeMs = Long.valueOf(getDefaultEventTime());
      TimeZoneHelper.TimeZoneInfo localTimeZoneInfo = this.mTimeZoneHelper.getCurrentTimeZoneInfo();
      TimeZone localTimeZone = localTimeZoneInfo.getTimeZone();
      this.mEvent.startTime.timezone = localTimeZone.getID();
      this.mExternalId = (System.currentTimeMillis() + "." + StringUtils.randomString(32));
      this.mEventThemeId = -1;
    }
  }

  public final void editEvent(String paramString1, String paramString2, String paramString3)
  {
    this.mEventId = paramString1;
    this.mOwnerId = paramString2;
    this.mAuthKey = paramString3;
    this.mEventThemeId = -1;
    this.mNewEvent = false;
  }

  protected final void handleServiceCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mPendingRequestId == null) || (paramInt != this.mPendingRequestId.intValue()));
    do
    {
      return;
      DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("req_pending");
      if (localDialogFragment != null)
        localDialogFragment.dismiss();
      this.mPendingRequestId = null;
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
      {
        FragmentActivity localFragmentActivity2 = getActivity();
        if (this.mNewEvent);
        for (int j = R.string.create_event_server_error; ; j = R.string.transient_server_error)
        {
          Toast.makeText(localFragmentActivity2, j, 0).show();
          break;
        }
      }
    }
    while (this.mListener == null);
    FragmentActivity localFragmentActivity1 = getActivity();
    if (this.mNewEvent);
    for (int i = R.string.event_create_successful; ; i = R.string.event_save_successful)
    {
      Toast.makeText(localFragmentActivity1, i, 0).show();
      this.mListener.onEventSaved();
      break;
    }
  }

  protected final boolean isEmpty()
  {
    if (this.mEvent != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt2 != -1) || (paramIntent == null));
    while (true)
    {
      return;
      switch (paramInt1)
      {
      default:
        break;
      case 0:
        byte[] arrayOfByte = paramIntent.getByteArrayExtra("location");
        if (arrayOfByte == null);
        for (this.mEvent.location = null; ; this.mEvent.location = ((Place)PlaceJson.getInstance().fromByteArray(arrayOfByte)))
        {
          bindLocation();
          break;
        }
      case 1:
        int i = paramIntent.getIntExtra("theme_id", -1);
        String str = paramIntent.getStringExtra("theme_url");
        if ((i != -1) && (str != null))
        {
          setEventTheme(i, str, null, true);
          getLoaderManager().restartLoader(0, null, this);
        }
        break;
      case 2:
        this.mResultAudience = ((AudienceData)paramIntent.getParcelableExtra("audience"));
      }
    }
  }

  public final void onAddPersonToCirclesAction(String paramString1, String paramString2, boolean paramBoolean)
  {
  }

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mTimeZoneHelper = new TimeZoneHelper(getActivity().getApplicationContext());
    TimeZoneHelper localTimeZoneHelper = this.mTimeZoneHelper;
    getActivity().getApplicationContext();
    localTimeZoneHelper.configure$622086bc(Calendar.getInstance());
  }

  public final void onChangeCirclesAction(String paramString1, String paramString2)
  {
  }

  public final void onCircleSelected(String paramString, CircleData paramCircleData)
  {
    this.mAudienceView.addCircle(paramCircleData);
    this.mAudienceView.clearText();
  }

  public void onClick(View paramView)
  {
    int i = paramView.getId();
    if (i == R.id.edit_audience)
    {
      recordUserAction(OzActions.COMPOSE_CHANGE_ACL);
      startActivityForResult(Intents.getEditAudienceActivityIntent(getActivity(), getAccount(), getString(R.string.event_invite_activity_title), this.mAudienceView.getAudience(), 11, false, false, true, false), 2);
    }
    while (true)
    {
      return;
      if (i == R.id.start_date)
      {
        DatePickerFragmentDialog localDatePickerFragmentDialog1 = new DatePickerFragmentDialog(1);
        localDatePickerFragmentDialog1.setTargetFragment(this, 0);
        Bundle localBundle1 = new Bundle();
        localBundle1.putLong("date_time", this.mEvent.startTime.timeMs.longValue());
        localBundle1.putString("time_zone", this.mEvent.startTime.timezone);
        localDatePickerFragmentDialog1.setArguments(localBundle1);
        localDatePickerFragmentDialog1.show(getFragmentManager(), "date");
      }
      else
      {
        if (i == R.id.end_date)
        {
          DatePickerFragmentDialog localDatePickerFragmentDialog2 = new DatePickerFragmentDialog(0);
          localDatePickerFragmentDialog2.setTargetFragment(this, 0);
          Bundle localBundle2 = new Bundle();
          if (this.mEvent.endTime != null)
            localBundle2.putLong("date_time", this.mEvent.endTime.timeMs.longValue());
          while (true)
          {
            localBundle2.putString("time_zone", this.mEvent.startTime.timezone);
            localDatePickerFragmentDialog2.setArguments(localBundle2);
            localDatePickerFragmentDialog2.show(getFragmentManager(), "date");
            break;
            localBundle2.putLong("date_time", this.mEvent.startTime.timeMs.longValue());
          }
        }
        if (i == R.id.start_time)
        {
          TimePickerFragmentDialog localTimePickerFragmentDialog1 = new TimePickerFragmentDialog(1);
          localTimePickerFragmentDialog1.setTargetFragment(this, 0);
          Bundle localBundle3 = new Bundle();
          localBundle3.putLong("date_time", this.mEvent.startTime.timeMs.longValue());
          localBundle3.putString("time_zone", this.mEvent.startTime.timezone);
          localTimePickerFragmentDialog1.setArguments(localBundle3);
          localTimePickerFragmentDialog1.show(getFragmentManager(), "time");
        }
        else
        {
          if (i == R.id.end_time)
          {
            TimePickerFragmentDialog localTimePickerFragmentDialog2 = new TimePickerFragmentDialog(0);
            localTimePickerFragmentDialog2.setTargetFragment(this, 0);
            Bundle localBundle4 = new Bundle();
            if (this.mEvent.endTime != null)
              localBundle4.putLong("date_time", this.mEvent.endTime.timeMs.longValue());
            while (true)
            {
              localBundle4.putString("time_zone", this.mEvent.startTime.timezone);
              localTimePickerFragmentDialog2.setArguments(localBundle4);
              localTimePickerFragmentDialog2.show(getFragmentManager(), "time");
              break;
              localBundle4.putLong("date_time", 7200000L + this.mEvent.startTime.timeMs.longValue());
            }
          }
          if (i == R.id.location_text)
          {
            recordUserAction(OzActions.COMPOSE_CHANGE_LOCATION);
            startActivityForResult(Intents.getEventLocationActivityIntent(getActivity(), getAccount(), this.mEvent.location), 0);
          }
          else if (i == R.id.select_theme_button)
          {
            startActivityForResult(Intents.getEventThemePickerIntent(getActivity(), getAccount()), 1);
          }
        }
      }
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.mNewEvent = paramBundle.getBoolean("new_event");
      this.mEventId = paramBundle.getString("event_id");
      this.mOwnerId = paramBundle.getString("owner_id");
      if (paramBundle.containsKey("event"))
      {
        byte[] arrayOfByte = paramBundle.getByteArray("event");
        this.mEvent = ((PlusEvent)PlusEventJson.getInstance().fromByteArray(arrayOfByte));
      }
      if (paramBundle.containsKey("request_id"))
        this.mPendingRequestId = Integer.valueOf(paramBundle.getInt("request_id"));
      this.mExternalId = paramBundle.getString("external_id");
      this.mChanged = paramBundle.getBoolean("changed");
    }
    getLoaderManager().initLoader(0, null, this);
    if ((!this.mNewEvent) && (this.mEvent == null))
      getLoaderManager().initLoader(1, null, this);
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    final FragmentActivity localFragmentActivity = getActivity();
    final EsAccount localEsAccount = getAccount();
    Object localObject;
    switch (paramInt)
    {
    default:
      localObject = null;
    case 0:
    case 1:
    }
    while (true)
    {
      return localObject;
      localObject = new EsCursorLoader(localFragmentActivity)
      {
        public final Cursor esLoadInBackground()
        {
          return EsEventData.getEventTheme(localFragmentActivity, localEsAccount, EditEventFragment.this.mEventThemeId, EditEventFragment.THEME_COLUMNS);
        }
      };
      continue;
      localObject = new EsCursorLoader(getActivity(), EsProvider.EVENTS_ALL_URI)
      {
        public final Cursor esLoadInBackground()
        {
          return EsEventData.retrieveEvent(localFragmentActivity, localEsAccount, EditEventFragment.this.mEventId, EditEventFragment.this.mAuthKey, EditEventFragment.EVENT_COLUMNS);
        }
      };
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.edit_event_fragment, paramViewGroup);
    this.mEventThemeView = ((EventThemeView)localView.findViewById(R.id.event_theme_image));
    this.mEventThemeView.setOnImageLoadedListener(this);
    this.mEventThemeView.setClickable(true);
    this.mEventThemeView.setOnClickListener(this);
    this.mThemeSelectionTextView = ((TextView)localView.findViewById(R.id.select_theme_text));
    this.mThemeSelectionTextView.setText(getString(R.string.event_change_theme).toUpperCase());
    this.mThemeProgressBar = ((ProgressBar)localView.findViewById(R.id.event_theme_progress_bar));
    this.mEventNameView = ((EditText)localView.findViewById(R.id.event_name));
    this.mEventNameView.addTextChangedListener(this.mEventNameTextWatcher);
    this.mStartDateView = ((Button)localView.findViewById(R.id.start_date));
    this.mStartDateView.setOnClickListener(this);
    this.mEndDateView = ((Button)localView.findViewById(R.id.end_date));
    this.mEndDateView.setOnClickListener(this);
    this.mStartTimeView = ((Button)localView.findViewById(R.id.start_time));
    this.mStartTimeView.setOnClickListener(this);
    this.mEndTimeView = ((Button)localView.findViewById(R.id.end_time));
    this.mEndTimeView.setOnClickListener(this);
    this.mLocationView = ((TextView)localView.findViewById(R.id.location_text));
    this.mLocationView.setOnClickListener(this);
    this.mAudienceView = ((TypeableAudienceView)localView.findViewById(R.id.audience_view));
    this.mAudienceView.setEmptyAudienceHint(R.string.event_invitees_hint);
    this.mAudienceView.setAudienceChangedCallback(new Runnable()
    {
      public final void run()
      {
        EditEventFragment.this.onAudienceChanged();
      }
    });
    this.mThemeSelectionButton = localView.findViewById(R.id.select_theme_button);
    this.mThemeSelectionButton.setOnClickListener(this);
    this.mDescriptionView = ((EditText)localView.findViewById(R.id.description));
    this.mDescriptionView.addTextChangedListener(this.mEventDescriptionTextWatcher);
    ContextThemeWrapper localContextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.CircleBrowserTheme);
    this.mAudienceAdapter = new PeopleSearchListAdapter(localContextThemeWrapper, getFragmentManager(), getLoaderManager(), getAccount());
    this.mAudienceAdapter.setCircleUsageType(11);
    this.mAudienceAdapter.setShowPersonNameDialog(false);
    this.mAudienceAdapter.setListener(this);
    this.mAudienceAdapter.onCreate(paramBundle);
    this.mAudienceView.setAutoCompleteAdapter(this.mAudienceAdapter);
    this.mAudienceView.setAccount(getAccount());
    localView.findViewById(R.id.edit_audience).setOnClickListener(this);
    this.mTimeZoneSpinnerAdapter = new TimeZoneSpinnerAdapter(localContextThemeWrapper);
    this.mTimeZoneSpinnerAdapter.setTimeZoneHelper(this.mTimeZoneHelper);
    this.mTimeZoneSpinner = ((Spinner)localView.findViewById(R.id.time_zone));
    this.mTimeZoneSpinner.setAdapter(this.mTimeZoneSpinnerAdapter);
    TimeZoneHelper.TimeZoneInfo localTimeZoneInfo = this.mTimeZoneHelper.getCurrentTimeZoneInfo();
    if (localTimeZoneInfo != null);
    for (int i = localTimeZoneInfo.getPosition(); ; i = -1)
    {
      this.mCurrentSpinnerPosition = i;
      this.mTimeZoneSpinner.setSelection(this.mCurrentSpinnerPosition);
      this.mTimeZoneSpinner.setOnItemSelectedListener(this);
      bindEvent();
      updateView(localView);
      return localView;
    }
  }

  public final void onDialogCanceled$20f9a4b7(String paramString)
  {
  }

  public final void onDialogListClick$12e92030(int paramInt, Bundle paramBundle)
  {
  }

  public final void onDialogNegativeClick$20f9a4b7(String paramString)
  {
  }

  public final void onDialogPositiveClick(Bundle paramBundle, String paramString)
  {
    if (("quit".equals(paramString)) && (this.mListener != null))
      this.mListener.onEventClosed();
  }

  public final void onDiscard()
  {
    int i;
    if (this.mNewEvent)
      if ((!TextUtils.isEmpty(this.mEvent.name)) || (!TextUtils.isEmpty(this.mEvent.description)) || (!isEmptyAudience()))
      {
        i = 1;
        if (i == 0)
          break label101;
        AlertFragmentDialog localAlertFragmentDialog2 = AlertFragmentDialog.newInstance(getString(R.string.new_event_quit_title), getString(R.string.new_event_quit_question), getString(R.string.yes), getString(R.string.no));
        localAlertFragmentDialog2.setTargetFragment(this, 0);
        localAlertFragmentDialog2.show(getFragmentManager(), "quit");
      }
    while (true)
    {
      return;
      i = 0;
      break;
      label101: if (this.mListener != null)
      {
        this.mListener.onEventClosed();
        continue;
        if (this.mChanged)
        {
          AlertFragmentDialog localAlertFragmentDialog1 = AlertFragmentDialog.newInstance(getString(R.string.edit_event_quit_title), getString(R.string.edit_event_quit_question), getString(R.string.yes), getString(R.string.no));
          localAlertFragmentDialog1.setTargetFragment(this, 0);
          localAlertFragmentDialog1.show(getFragmentManager(), "quit");
        }
        else if (this.mListener != null)
        {
          this.mListener.onEventClosed();
        }
      }
    }
  }

  public final void onDismissSuggestionAction(String paramString1, String paramString2)
  {
  }

  public final void onEndDateCleared()
  {
    clearEndTime();
    bindEndDate();
    bindEndTime();
  }

  public final void onEndDateSet(int paramInt1, int paramInt2, int paramInt3)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeZone(((TimeZoneHelper.TimeZoneInfo)this.mTimeZoneSpinner.getSelectedItem()).getTimeZone());
    if (this.mEvent.endTime != null)
      localCalendar.setTimeInMillis(this.mEvent.endTime.timeMs.longValue());
    while (true)
    {
      if ((this.mEvent.endTime == null) || (localCalendar.get(1) != paramInt1) || (localCalendar.get(2) != paramInt2) || (localCalendar.get(5) != paramInt3))
      {
        localCalendar.set(paramInt1, paramInt2, paramInt3);
        long l = localCalendar.getTimeInMillis();
        if (this.mEvent.startTime.timeMs.longValue() > l)
          this.mEvent.startTime.timeMs.longValue();
        setEndTime(localCalendar);
        bindEndDate();
        bindEndTime();
      }
      return;
      localCalendar.setTimeInMillis(7200000L + this.mEvent.startTime.timeMs.longValue());
    }
  }

  public final void onEndTimeCleared()
  {
    clearEndTime();
    bindEndTime();
    bindEndDate();
  }

  public final void onEndTimeSet(int paramInt1, int paramInt2)
  {
    Calendar localCalendar = Calendar.getInstance();
    if (this.mEvent.endTime != null)
      localCalendar.setTimeInMillis(this.mEvent.endTime.timeMs.longValue());
    while (true)
    {
      if ((this.mEvent.endTime == null) || (localCalendar.get(11) != paramInt1) || (localCalendar.get(12) != paramInt2))
      {
        localCalendar.set(11, paramInt1);
        localCalendar.set(12, paramInt2);
        localCalendar.setTimeZone(((TimeZoneHelper.TimeZoneInfo)this.mTimeZoneSpinner.getSelectedItem()).getTimeZone());
        long l = localCalendar.getTimeInMillis();
        if (this.mEvent.startTime.timeMs.longValue() > l)
          this.mEvent.startTime.timeMs.longValue();
        setEndTime(localCalendar);
        bindEndTime();
        bindEndDate();
      }
      return;
      localCalendar.setTimeInMillis(7200000L + this.mEvent.startTime.timeMs.longValue());
    }
  }

  public final void onImageLoaded$7ad36aad()
  {
    enableEventPicker();
  }

  public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (paramInt != this.mCurrentSpinnerPosition)
    {
      TimeZoneHelper.TimeZoneInfo localTimeZoneInfo = (TimeZoneHelper.TimeZoneInfo)this.mTimeZoneSpinner.getSelectedItem();
      long l1 = localTimeZoneInfo.getOffset();
      long l2 = this.mTimeZoneHelper.getCurrentTimeZoneInfo().getOffset();
      if (!TextUtils.isEmpty(this.mEvent.startTime.timezone))
      {
        TimeZone localTimeZone = TimeZoneHelper.getSystemTimeZone(this.mEvent.startTime.timezone);
        l2 = this.mTimeZoneHelper.getOffset(localTimeZone);
      }
      long l3 = l2 - l1;
      this.mEvent.startTime.timezone = localTimeZoneInfo.getTimeZone().getID();
      EventTime localEventTime1 = this.mEvent.startTime;
      localEventTime1.timeMs = Long.valueOf(l3 + localEventTime1.timeMs.longValue());
      if ((this.mEvent.endTime != null) && (this.mEvent.endTime.timeMs != null))
      {
        EventTime localEventTime2 = this.mEvent.endTime;
        localEventTime2.timeMs = Long.valueOf(l3 + localEventTime2.timeMs.longValue());
        this.mEvent.endTime.timezone = this.mEvent.startTime.timezone;
      }
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public void onNothingSelected(AdapterView<?> paramAdapterView)
  {
  }

  public final void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mServiceListener);
  }

  public final void onPersonSelected(String paramString1, String paramString2, PersonData paramPersonData)
  {
    this.mAudienceView.addPerson(paramPersonData);
    this.mAudienceView.clearText();
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mServiceListener);
    if ((this.mPendingRequestId != null) && (!EsService.isRequestPending(this.mPendingRequestId.intValue())))
    {
      ServiceResult localServiceResult = EsService.removeResult(this.mPendingRequestId.intValue());
      handleServiceCallback(this.mPendingRequestId.intValue(), localServiceResult);
      this.mPendingRequestId = null;
    }
    if (this.mResultAudience != null)
    {
      this.mAudienceView.replaceAudience(this.mResultAudience);
      this.mResultAudience = null;
      onAudienceChanged();
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    this.mAudienceAdapter.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("new_event", this.mNewEvent);
    paramBundle.putString("event_id", this.mEventId);
    paramBundle.putString("owner_id", this.mOwnerId);
    if (this.mEvent != null)
      paramBundle.putByteArray("event", PlusEventJson.getInstance().toByteArray(this.mEvent));
    if (this.mPendingRequestId != null)
      paramBundle.putInt("request_id", this.mPendingRequestId.intValue());
    paramBundle.putString("external_id", this.mExternalId);
    paramBundle.putBoolean("changed", this.mChanged);
  }

  public final void onSearchListAdapterStateChange(PeopleSearchAdapter paramPeopleSearchAdapter)
  {
  }

  public final void onStart()
  {
    super.onStart();
    if (this.mAudienceAdapter != null)
      this.mAudienceAdapter.onStart();
  }

  public final void onStartDateSet(int paramInt1, int paramInt2, int paramInt3)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeZone(((TimeZoneHelper.TimeZoneInfo)this.mTimeZoneSpinner.getSelectedItem()).getTimeZone());
    localCalendar.setTimeInMillis(this.mEvent.startTime.timeMs.longValue());
    if ((localCalendar.get(1) != paramInt1) || (localCalendar.get(2) != paramInt2) || (localCalendar.get(5) != paramInt3))
    {
      localCalendar.set(paramInt1, paramInt2, paramInt3);
      setStartTime(localCalendar);
      bindStartDate();
      bindStartTime();
      if ((this.mEvent.endTime != null) && (this.mEvent.endTime.timeMs.longValue() < localCalendar.getTimeInMillis()))
      {
        localCalendar.add(13, 7200);
        setEndTime(localCalendar);
        bindEndDate();
        bindEndTime();
      }
    }
  }

  public final void onStartTimeSet(int paramInt1, int paramInt2)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTimeZone(((TimeZoneHelper.TimeZoneInfo)this.mTimeZoneSpinner.getSelectedItem()).getTimeZone());
    localCalendar.setTimeInMillis(this.mEvent.startTime.timeMs.longValue());
    if ((localCalendar.get(11) != paramInt1) || (localCalendar.get(12) != paramInt2))
    {
      localCalendar.set(11, paramInt1);
      localCalendar.set(12, paramInt2);
      long l = localCalendar.getTimeInMillis();
      setStartTime(localCalendar);
      bindStartTime();
      if ((this.mEvent.endTime != null) && (this.mEvent.endTime.timeMs.longValue() < l))
      {
        localCalendar.add(13, 7200);
        setEndTime(localCalendar);
        bindEndDate();
        bindEndTime();
      }
    }
  }

  public final void onStop()
  {
    super.onStop();
    if (this.mAudienceAdapter != null)
      this.mAudienceAdapter.onStop();
  }

  public final void onUnblockPersonAction(String paramString, boolean paramBoolean)
  {
  }

  public final void save()
  {
    int i = 1;
    if (this.mEvent == null)
    {
      i = 0;
      if (i != 0)
      {
        ProgressFragmentDialog.newInstance(null, getString(R.string.event_update_operation_pending), false).show(getFragmentManager(), "req_pending");
        if (!this.mNewEvent)
          break label163;
      }
    }
    label163: for (this.mPendingRequestId = Integer.valueOf(EsService.createEvent(getActivity(), getAccount(), this.mEvent, this.mAudienceView.getAudience(), this.mExternalId)); ; this.mPendingRequestId = Integer.valueOf(EsService.updateEvent(getActivity(), getAccount(), this.mEvent)))
    {
      return;
      if (TextUtils.isEmpty(this.mEvent.name))
      {
        Toast.makeText(getActivity(), getResources().getString(R.string.event_no_title_hint), 0).show();
        i = 0;
        break;
      }
      if (!this.mNewEvent)
        break;
      boolean bool = isEmptyAudience();
      if (bool)
        Toast.makeText(getActivity(), getResources().getString(R.string.event_no_audience_hint), 0).show();
      if (!bool)
        break;
      i = 0;
      break;
    }
  }

  public final void setOnEventChangedListener(OnEditEventListener paramOnEditEventListener)
  {
    this.mListener = paramOnEditEventListener;
  }

  public static class DatePickerFragmentDialog extends DialogFragment
    implements DatePickerDialog.OnDateSetListener, DialogInterface.OnClickListener
  {
    private boolean mCancelled;
    private int mType = -1;

    public DatePickerFragmentDialog()
    {
    }

    public DatePickerFragmentDialog(int paramInt)
    {
      this.mType = paramInt;
    }

    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      EditEventFragment localEditEventFragment = (EditEventFragment)getTargetFragment();
      switch (paramInt)
      {
      default:
      case -2:
      }
      while (true)
      {
        return;
        localEditEventFragment.onEndDateCleared();
        this.mCancelled = true;
      }
    }

    public final Dialog onCreateDialog(Bundle paramBundle)
    {
      if (this.mType == -1)
      {
        this.mType = paramBundle.getInt("type");
        this.mCancelled = paramBundle.getBoolean("cancelled", this.mCancelled);
      }
      long l = getArguments().getLong("date_time");
      TimeZone localTimeZone = TimeZoneHelper.getSystemTimeZone(getArguments().getString("time_zone"));
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTimeZone(localTimeZone);
      localCalendar.setTimeInMillis(l);
      DatePickerDialog localDatePickerDialog = new DatePickerDialog(getActivity(), this, localCalendar.get(1), localCalendar.get(2), localCalendar.get(5));
      if (this.mType == 0)
        localDatePickerDialog.setButton(-2, getString(R.string.clear), this);
      return localDatePickerDialog;
    }

    public void onDateSet(DatePicker paramDatePicker, int paramInt1, int paramInt2, int paramInt3)
    {
      if (this.mCancelled);
      while (true)
      {
        return;
        if (this.mType == 1)
          ((EditEventFragment)getTargetFragment()).onStartDateSet(paramInt1, paramInt2, paramInt3);
        else
          ((EditEventFragment)getTargetFragment()).onEndDateSet(paramInt1, paramInt2, paramInt3);
      }
    }

    public final void onSaveInstanceState(Bundle paramBundle)
    {
      super.onSaveInstanceState(paramBundle);
      paramBundle.putInt("type", this.mType);
      paramBundle.putBoolean("cancelled", this.mCancelled);
    }
  }

  public static abstract interface OnEditEventListener
  {
    public abstract void onEventClosed();

    public abstract void onEventSaved();
  }

  public static class TimePickerFragmentDialog extends DialogFragment
    implements TimePickerDialog.OnTimeSetListener, DialogInterface.OnClickListener
  {
    private boolean mCancelled;
    private int mType = -1;

    public TimePickerFragmentDialog()
    {
    }

    public TimePickerFragmentDialog(int paramInt)
    {
      this.mType = paramInt;
    }

    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      EditEventFragment localEditEventFragment = (EditEventFragment)getTargetFragment();
      switch (paramInt)
      {
      default:
      case -2:
      }
      while (true)
      {
        return;
        localEditEventFragment.onEndTimeCleared();
        this.mCancelled = true;
      }
    }

    public final Dialog onCreateDialog(Bundle paramBundle)
    {
      if (this.mType == -1)
      {
        this.mType = paramBundle.getInt("type", -1);
        this.mCancelled = paramBundle.getBoolean("cancelled", this.mCancelled);
      }
      long l = getArguments().getLong("date_time");
      String str = getArguments().getString("time_zone");
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTimeZone(TimeZoneHelper.getSystemTimeZone(str));
      localCalendar.setTimeInMillis(l);
      TimePickerDialog localTimePickerDialog = new TimePickerDialog(getActivity(), this, localCalendar.get(11), localCalendar.get(12), DateFormat.is24HourFormat(getActivity()));
      if (this.mType == 0)
        localTimePickerDialog.setButton(-2, getString(R.string.clear), this);
      return localTimePickerDialog;
    }

    public final void onSaveInstanceState(Bundle paramBundle)
    {
      super.onSaveInstanceState(paramBundle);
      paramBundle.putInt("type", this.mType);
      paramBundle.putBoolean("cancelled", this.mCancelled);
    }

    public void onTimeSet(TimePicker paramTimePicker, int paramInt1, int paramInt2)
    {
      if (this.mCancelled);
      while (true)
      {
        return;
        EditEventFragment localEditEventFragment = (EditEventFragment)getTargetFragment();
        if (this.mType == 1)
          localEditEventFragment.onStartTimeSet(paramInt1, paramInt2);
        else
          localEditEventFragment.onEndTimeSet(paramInt1, paramInt2);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.EditEventFragment
 * JD-Core Version:    0.6.2
 */