package com.google.android.apps.plus.phone;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.menu;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.InstrumentedActivity;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.content.EsAccountsData.ExperimentListener;
import com.google.android.apps.plus.content.EsNotificationData.NotificationQuery;
import com.google.android.apps.plus.content.EsPostsData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.fragments.AlertFragmentDialog;
import com.google.android.apps.plus.fragments.AlertFragmentDialog.AlertDialogListener;
import com.google.android.apps.plus.fragments.HostedAlbumsFragment;
import com.google.android.apps.plus.fragments.HostedEventListFragment;
import com.google.android.apps.plus.fragments.HostedHangoutFragment;
import com.google.android.apps.plus.fragments.HostedMessengerFragment;
import com.google.android.apps.plus.fragments.HostedPeopleFragment;
import com.google.android.apps.plus.fragments.HostedProfileFragment;
import com.google.android.apps.plus.fragments.HostedSquareListFragment;
import com.google.android.apps.plus.fragments.HostedStreamFragment;
import com.google.android.apps.plus.hangout.GCommApp;
import com.google.android.apps.plus.hangout.GCommNativeWrapper;
import com.google.android.apps.plus.hangout.GCommService;
import com.google.android.apps.plus.hangout.Log;
import com.google.android.apps.plus.service.AndroidContactsSync;
import com.google.android.apps.plus.service.AndroidNotification;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.Hangout;
import com.google.android.apps.plus.service.Hangout.SupportStatus;
import com.google.android.apps.plus.service.ImageResourceManager;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.HelpUrl;
import com.google.android.apps.plus.util.MapUtils;
import com.google.android.apps.plus.util.Property;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.android.apps.plus.views.HostActionBar.OnUpButtonClickListener;
import com.google.android.apps.plus.views.HostLayout;
import com.google.android.apps.plus.views.HostLayout.HostLayoutListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class HomeActivity extends InstrumentedActivity
  implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener, EsAccountsData.ExperimentListener, HostActionBar.OnUpButtonClickListener, HostLayout.HostLayoutListener
{
  private static final Uri REMOVE = Uri.parse("https://plus.google.com/downgrade/");
  private HostActionBar mActionBar;
  private Bundle mDestination;
  private Parcelable[] mDestinationState = new Parcelable[9];
  private boolean mDestinationsConfigured;
  private ArrayList<String> mDialogTags;
  protected boolean mFirstLoad = true;
  private HostLayout mHostLayout;
  private ListView mNavigationBar;
  private HostNavigationBarAdapter mNavigationBarAdapter;
  private int mNavigationBarScrollPosition = -1;
  private int mNotificationCount;
  private boolean mNotificationsLoaded;
  private Integer mRequestId;
  private EsServiceListener mServiceListener = new EsServiceListener()
  {
    public final void onSyncNotifications$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      HomeActivity.this.handleServiceCallback$b5e9bbb(paramAnonymousInt, paramAnonymousServiceResult);
    }
  };
  private ShakeDetector.ShakeEventListener mShakeListener;
  private SignOnManager mSignOnManager = new SignOnManager(this);

  private void buildDestinationBundleForIntent()
  {
    Intent localIntent = getIntent();
    Bundle localBundle = localIntent.getExtras();
    this.mDestination = new Bundle();
    if (localBundle == null)
      this.mDestination.putInt("destination", 0);
    while (true)
    {
      this.mDestination.putParcelable("account", this.mSignOnManager.getAccount());
      return;
      this.mDestination.putAll(localBundle);
      int i = localIntent.getIntExtra("destination", 0);
      this.mDestination.putInt("destination", i);
    }
  }

  private void configureDestinations()
  {
    this.mNavigationBarAdapter.removeAllDestinations();
    this.mNavigationBarAdapter.addDestination(0, R.drawable.ic_nav_home, R.string.home_stream_label);
    this.mNavigationBarAdapter.addDestination(5, R.drawable.ic_nav_circles, R.string.home_screen_people_label);
    EsAccount localEsAccount = this.mSignOnManager.getAccount();
    int i;
    if ((localEsAccount != null) && (localEsAccount.isPlusPage()))
    {
      i = 1;
      if (i == 0)
        break label261;
      this.mNavigationBarAdapter.addDestination(1, R.drawable.ic_nav_profile, localEsAccount.getDisplayName(), localEsAccount.getGaiaId());
    }
    while (true)
    {
      this.mNavigationBarAdapter.addDestination(7, R.drawable.ic_nav_myphotos, R.string.home_screen_photos_label);
      if (Property.ENABLE_SQUARES.getBoolean())
        this.mNavigationBarAdapter.addDestination(8, R.drawable.ic_nav_communities, R.string.home_screen_squares_label);
      if ((localEsAccount != null) && (i == 0) && (Hangout.isHangoutCreationSupported(getApplicationContext(), localEsAccount)))
        this.mNavigationBarAdapter.addDestination(3, R.drawable.ic_nav_hangouts, R.string.home_screen_hangout_label);
      this.mNavigationBarAdapter.addDestination(2, R.drawable.ic_nav_events, R.string.home_screen_events_label);
      if (i == 0)
        this.mNavigationBarAdapter.addDestination(4, R.drawable.ic_nav_messenger, R.string.home_screen_huddle_label);
      Intent localIntent = MapUtils.getPlacesActivityIntent$7ec49240();
      boolean bool = getPackageManager().queryIntentActivities(localIntent, 65536).isEmpty();
      int j = 0;
      if (!bool)
        j = 1;
      if ((j != 0) && (i == 0))
        this.mNavigationBarAdapter.addDestination(6, R.drawable.ic_nav_local, R.string.home_screen_local_label);
      this.mNavigationBarAdapter.showDestinations();
      this.mDestinationsConfigured = true;
      restoreNavigationBarScrollPosition();
      return;
      i = 0;
      break;
      label261: this.mNavigationBarAdapter.addDestination(1, R.drawable.ic_nav_profile, R.string.home_screen_profile_label);
    }
  }

  private void handleServiceCallback$b5e9bbb(int paramInt)
  {
    if ((this.mRequestId == null) || (this.mRequestId.intValue() != paramInt));
    while (true)
    {
      return;
      this.mRequestId = null;
      updateNotificationsSpinner();
    }
  }

  private static boolean isLauncherIntent(Intent paramIntent)
  {
    if (("android.intent.action.MAIN".equals(paramIntent.getAction())) && (paramIntent.getCategories() != null) && (paramIntent.getCategories().contains("android.intent.category.LAUNCHER")) && (paramIntent.getExtras() == null));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private void navigateToDestination(int paramInt, Bundle paramBundle, boolean paramBoolean, Fragment.SavedState paramSavedState)
  {
    EsAccount localEsAccount = this.mSignOnManager.getAccount();
    int i;
    if ((localEsAccount != null) && (localEsAccount.isPlusPage()))
    {
      i = 1;
      if ((paramInt == 4) && (i != 0))
        paramInt = 0;
      switch (paramInt)
      {
      case 6:
      default:
      case 0:
      case 1:
      case 7:
      case 2:
      case 3:
      case 5:
      case 4:
      case 8:
      }
    }
    while (true)
    {
      return;
      i = 0;
      break;
      HostedStreamFragment localHostedStreamFragment = new HostedStreamFragment();
      localHostedStreamFragment.setArguments(paramBundle);
      this.mHostLayout.showFragment(localHostedStreamFragment, paramBoolean, paramSavedState);
      continue;
      HostedProfileFragment localHostedProfileFragment = new HostedProfileFragment();
      if (!paramBundle.containsKey("person_id"))
        paramBundle.putString("person_id", this.mSignOnManager.getAccount().getPersonId());
      localHostedProfileFragment.setArguments(paramBundle);
      this.mHostLayout.showFragment(localHostedProfileFragment, paramBoolean, paramSavedState);
      continue;
      HostedAlbumsFragment localHostedAlbumsFragment = new HostedAlbumsFragment();
      if (!paramBundle.containsKey("person_id"))
        paramBundle.putString("person_id", this.mSignOnManager.getAccount().getPersonId());
      if (!paramBundle.containsKey("photos_home"))
        paramBundle.putBoolean("photos_home", true);
      localHostedAlbumsFragment.setArguments(paramBundle);
      this.mHostLayout.showFragment(localHostedAlbumsFragment, paramBoolean, paramSavedState);
      continue;
      HostedEventListFragment localHostedEventListFragment = new HostedEventListFragment();
      paramBundle.putBoolean("refresh", true);
      localHostedEventListFragment.setArguments(paramBundle);
      this.mHostLayout.showFragment(localHostedEventListFragment, paramBoolean, paramSavedState);
      continue;
      Context localContext = getApplicationContext();
      if (localEsAccount != null);
      try
      {
        if (Hangout.isHangoutCreationSupported(localContext, localEsAccount))
        {
          GCommApp.getInstance(localContext).getGCommNativeWrapper().getCurrentState();
          HostedHangoutFragment localHostedHangoutFragment = new HostedHangoutFragment();
          localHostedHangoutFragment.setArguments(paramBundle);
          this.mHostLayout.showFragment(localHostedHangoutFragment, paramBoolean, paramSavedState);
        }
        if ((localEsAccount == null) || (Hangout.getSupportedStatus(localContext, localEsAccount) != Hangout.SupportStatus.SUPPORTED) || (!GCommApp.getInstance(localContext).isInAHangout()))
          continue;
        Intent localIntent = GCommApp.getInstance(this).getGCommService().getNotificationIntent();
        if (localIntent == null)
          continue;
        startActivity(localIntent);
      }
      catch (LinkageError localLinkageError)
      {
        int j = R.string.hangout_native_lib_error;
        String str = getResources().getString(j);
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = str;
        arrayOfObject[1] = Boolean.valueOf(false);
        Log.debug("showError: message=%s finishOnOk=%s", arrayOfObject);
        AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(null, str, getResources().getString(R.string.ok), null, 17301543);
        localAlertFragmentDialog.setCancelable(false);
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
            if (this.val$finishOnOk)
              HomeActivity.this.finish();
            while (true)
            {
              return;
              if (HomeActivity.this.mHostLayout.isNavigationBarVisible())
                HomeActivity.this.mHostLayout.hideNavigationBar();
              HomeActivity.access$202(HomeActivity.this, null);
            }
          }
        });
        localAlertFragmentDialog.show(getSupportFragmentManager(), "error");
      }
      continue;
      HostedPeopleFragment localHostedPeopleFragment = new HostedPeopleFragment(true);
      localHostedPeopleFragment.setArguments(paramBundle);
      this.mHostLayout.showFragment(localHostedPeopleFragment, paramBoolean, paramSavedState);
      continue;
      HostedMessengerFragment localHostedMessengerFragment = new HostedMessengerFragment();
      localHostedMessengerFragment.setArguments(paramBundle);
      this.mHostLayout.showFragment(localHostedMessengerFragment, true, paramSavedState);
      continue;
      HostedSquareListFragment localHostedSquareListFragment = new HostedSquareListFragment();
      paramBundle.putBoolean("refresh", true);
      localHostedSquareListFragment.setArguments(paramBundle);
      this.mHostLayout.showFragment(localHostedSquareListFragment, paramBoolean, null);
    }
  }

  private void refreshNotifications()
  {
    this.mRequestId = EsService.syncNotifications(this, this.mSignOnManager.getAccount());
    updateNotificationsSpinner();
  }

  private void restoreNavigationBarScrollPosition()
  {
    if ((this.mNavigationBarScrollPosition != -1) && (this.mDestinationsConfigured) && (this.mNotificationsLoaded))
    {
      this.mNavigationBar.setSelection(this.mNavigationBarScrollPosition);
      this.mNavigationBarScrollPosition = -1;
    }
  }

  private void saveDestinationState()
  {
    if (this.mDestination != null)
    {
      int i = this.mDestination.getInt("destination", -1);
      if (i != -1)
        this.mDestinationState[i] = this.mHostLayout.saveHostedFragmentState();
    }
  }

  private void showCurrentDestination()
  {
    if (this.mActionBar != null)
      this.mActionBar.dismissPopupMenus();
    if ((this.mDialogTags == null) || (this.mDialogTags.isEmpty()));
    while (true)
    {
      navigateToDestination(this.mDestination.getInt("destination"), this.mDestination, false, null);
      return;
      FragmentManager localFragmentManager = getSupportFragmentManager();
      Iterator localIterator = this.mDialogTags.iterator();
      while (localIterator.hasNext())
      {
        DialogFragment localDialogFragment = (DialogFragment)localFragmentManager.findFragmentByTag((String)localIterator.next());
        if (localDialogFragment != null)
          localDialogFragment.dismissAllowingStateLoss();
      }
      this.mDialogTags = null;
    }
  }

  private void updateNotificationsSpinner()
  {
    if (this.mNavigationBarAdapter != null)
    {
      if (this.mRequestId == null)
        break label22;
      this.mNavigationBarAdapter.showProgressIndicator();
    }
    while (true)
    {
      return;
      label22: this.mNavigationBarAdapter.hideProgressIndicator();
    }
  }

  protected final EsAccount getAccount()
  {
    return this.mSignOnManager.getAccount();
  }

  public final OzViews getViewForLogging()
  {
    HostedFragment localHostedFragment;
    if (this.mHostLayout == null)
    {
      localHostedFragment = null;
      if (localHostedFragment != null)
        break label30;
    }
    label30: for (OzViews localOzViews = OzViews.HOME; ; localOzViews = localHostedFragment.getViewForLogging())
    {
      return localOzViews;
      localHostedFragment = this.mHostLayout.getCurrentHostedFragment();
      break;
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (this.mSignOnManager.onActivityResult$6eb84b56(paramInt1, paramInt2));
    while (true)
    {
      return;
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
    }
  }

  public final void onAttachFragment(Fragment paramFragment)
  {
    if ((this.mHostLayout != null) && ((paramFragment instanceof HostedFragment)))
      this.mHostLayout.onAttachFragment((HostedFragment)paramFragment);
    if ((paramFragment instanceof DialogFragment))
    {
      if (this.mDialogTags == null)
        this.mDialogTags = new ArrayList();
      this.mDialogTags.add(paramFragment.getTag());
    }
  }

  public void onBackPressed()
  {
    if (this.mHostLayout.isNavigationBarVisible())
      this.mHostLayout.hideNavigationBar();
    while (true)
    {
      return;
      if ((this.mHostLayout.getCurrentHostedFragment() == null) || (!this.mHostLayout.getCurrentHostedFragment().onBackPressed()))
        super.onBackPressed();
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    if (paramBundle != null)
      paramBundle.setClassLoader(getClass().getClassLoader());
    super.onCreate(paramBundle);
    if (!isTaskRoot())
    {
      int m = getIntent().getIntExtra("destination", 0);
      if ((isLauncherIntent(getIntent())) || (m == 4))
        finish();
    }
    do
    {
      return;
      this.mSignOnManager.onCreate(paramBundle, getIntent());
    }
    while (isFinishing());
    setContentView(R.layout.host_navigation_activity);
    this.mHostLayout = ((HostLayout)findViewById(R.id.host));
    this.mHostLayout.setListener(this);
    this.mActionBar = this.mHostLayout.getActionBar();
    this.mActionBar.setOnUpButtonClickListener(this);
    this.mActionBar.setUpButtonContentDescription(getString(R.string.main_menu_content_description));
    this.mNavigationBar = ((ListView)this.mHostLayout.getNavigationBar());
    this.mNavigationBarAdapter = new HostNavigationBarAdapter(this);
    this.mNavigationBarAdapter.setCollapsedMenuItemCount(this.mHostLayout.getCollapsedMenuItemCount());
    this.mNavigationBar.setAdapter(this.mNavigationBarAdapter);
    this.mNavigationBar.setOnItemClickListener(this);
    EsAccountsData.registerExperimentListener(this);
    configureDestinations();
    if (paramBundle != null)
    {
      if (paramBundle.containsKey("reqId"))
        this.mRequestId = Integer.valueOf(paramBundle.getInt("reqId"));
      this.mNavigationBarScrollPosition = paramBundle.getInt("scrollPos");
      this.mNavigationBarAdapter.setCollapsed(paramBundle.getBoolean("navBarCollapsed", true));
      this.mHostLayout.attachActionBar();
    }
    label532: 
    while (true)
    {
      if (this.mSignOnManager.getAccount() != null)
        getSupportLoaderManager().initLoader(0, null, this);
      this.mActionBar.setNotificationCount(this.mNotificationCount);
      ShakeDetector localShakeDetector = ShakeDetector.getInstance(getApplicationContext());
      if (localShakeDetector == null)
        break;
      this.mShakeListener = new ShakeDetector.ShakeEventListener()
      {
        public final void onShakeDetected()
        {
          EsAccount localEsAccount = HomeActivity.this.getAccount();
          Context localContext = HomeActivity.this.getApplicationContext();
          if (((localEsAccount != null) && (Hangout.isHangoutCreationSupported(localContext, localEsAccount))) || ((Hangout.getSupportedStatus(localContext, localEsAccount) == Hangout.SupportStatus.SUPPORTED) && (GCommApp.getInstance(localContext).isInAHangout())))
          {
            Intent localIntent = Intents.getHangoutActivityIntent(localContext, localEsAccount);
            localIntent.addFlags(67108864);
            localIntent.addFlags(268435456);
            HomeActivity.this.getApplicationContext().startActivity(localIntent);
          }
        }
      };
      localShakeDetector.addEventListener(this.mShakeListener);
      localShakeDetector.start();
      break;
      if (this.mSignOnManager.isSignedIn())
      {
        buildDestinationBundleForIntent();
        showCurrentDestination();
        if (getIntent().getBooleanExtra("show_notifications", false))
          this.mHostLayout.showNavigationBarDelayed();
        EsAccount localEsAccount = this.mSignOnManager.getAccount();
        int i;
        if ((localEsAccount != null) && (localEsAccount.isPlusPage()))
        {
          i = 1;
          label405: if (i != 0)
            break label469;
          if (EsAccountsData.isContactsStatsSyncPreferenceSet(this, this.mSignOnManager.getAccount()))
            break label463;
        }
        label463: for (int k = 1; ; k = 0)
        {
          if (k == 0)
            break label469;
          new NewFeaturesFragmentDialog(this.mSignOnManager.getAccount()).show(getSupportFragmentManager(), "new_features");
          break;
          i = 0;
          break label405;
        }
        label469: if (i == 0)
        {
          if ((Build.VERSION.SDK_INT >= 14) && (!EsAccountsData.isContactsSyncPreferenceSet(this, this.mSignOnManager.getAccount())) && (AndroidContactsSync.isAndroidSyncSupported(this)));
          for (int j = 1; ; j = 0)
          {
            if (j == 0)
              break label532;
            startActivity(Intents.getContactsSyncConfigActivityIntent(this, this.mSignOnManager.getAccount()));
            break;
          }
        }
      }
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Object localObject = null;
    switch (paramInt)
    {
    default:
    case 0:
    }
    while (true)
    {
      return localObject;
      EsAccount localEsAccount = this.mSignOnManager.getAccount();
      localObject = null;
      if (localEsAccount != null)
        localObject = new EsCursorLoader(this, EsProvider.appendAccountParameter(EsProvider.NOTIFICATIONS_URI, localEsAccount), EsNotificationData.NotificationQuery.PROJECTION, null, null, "timestamp DESC");
    }
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(R.menu.host_menu, paramMenu);
    return true;
  }

  protected void onDestroy()
  {
    super.onDestroy();
    ShakeDetector localShakeDetector = ShakeDetector.getInstance(getApplicationContext());
    if (localShakeDetector != null)
    {
      localShakeDetector.removeEventListener(this.mShakeListener);
      localShakeDetector.stop();
    }
    ImageResourceManager.getInstance(this).verifyEmpty();
    EsAccountsData.unregisterExperimentListener(this);
  }

  public final void onExperimentsChanged()
  {
    configureDestinations();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.mNavigationBarAdapter.isNotificationHeader(paramInt))
      refreshNotifications();
    while (true)
    {
      return;
      int i = this.mNavigationBarAdapter.getDestinationId(paramInt);
      if (i != -1)
      {
        if (i == -2)
        {
          this.mNavigationBarAdapter.setCollapsed(false);
        }
        else if (i == 6)
        {
          startActivity(MapUtils.getPlacesActivityIntent$7ec49240());
          this.mHostLayout.hideNavigationBar();
        }
        else if ((this.mDestination != null) && (this.mDestination.getInt("destination") == i))
        {
          this.mHostLayout.hideNavigationBar();
        }
        else
        {
          saveDestinationState();
          this.mDestination = new Bundle();
          this.mDestination.putParcelable("account", this.mSignOnManager.getAccount());
          this.mDestination.putInt("destination", i);
          navigateToDestination(i, this.mDestination, true, (Fragment.SavedState)this.mDestinationState[i]);
        }
      }
      else
      {
        Cursor localCursor = (Cursor)this.mNavigationBarAdapter.getItem(paramInt);
        if (localCursor != null)
        {
          Intent localIntent = AndroidNotification.newViewNotificationIntent(this, this.mSignOnManager.getAccount(), localCursor);
          if (localIntent != null)
          {
            String str = localCursor.getString(1);
            if (localCursor.getInt(11) != 1)
              EsService.markNotificationAsRead(this, this.mSignOnManager.getAccount(), str);
            localIntent.putExtra("com.google.plus.analytics.intent.extra.START_VIEW", OzViews.NOTIFICATIONS_WIDGET);
            localIntent.putExtra("com.google.plus.analytics.intent.extra.FROM_NOTIFICATION", true);
            startActivity(localIntent);
          }
        }
      }
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onNavigationBarVisibilityChange(boolean paramBoolean)
  {
    if ((!paramBoolean) && (this.mNavigationBarAdapter != null))
    {
      this.mNavigationBarAdapter.setCollapsed(true);
      if (this.mNavigationBarAdapter.getUnreadNotificationCount() > 0)
      {
        EsAccount localEsAccount = this.mSignOnManager.getAccount();
        if (localEsAccount != null)
          EsService.tellServerNotificationsWereRead(this, localEsAccount);
      }
    }
  }

  protected void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    if ((this.mSignOnManager.isSignedIn()) && (!isLauncherIntent(paramIntent)))
    {
      setIntent(paramIntent);
      buildDestinationBundleForIntent();
      showCurrentDestination();
      if (paramIntent.getBooleanExtra("show_notifications", false))
        this.mHostLayout.showNavigationBarDelayed();
    }
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    boolean bool = true;
    if (this.mHostLayout.onOptionsItemSelected(paramMenuItem));
    while (true)
    {
      return bool;
      int i = paramMenuItem.getItemId();
      if (i == R.id.search)
      {
        startActivity(Intents.getPostSearchActivityIntent(this, this.mSignOnManager.getAccount(), null));
      }
      else if (i == R.id.feedback)
      {
        recordUserAction(OzActions.SETTINGS_FEEDBACK);
        GoogleFeedback.launch(this);
      }
      else if (i == R.id.settings)
      {
        startActivity(Intents.getSettingsActivityIntent(this, this.mSignOnManager.getAccount()));
      }
      else if (i == R.id.help)
      {
        startExternalActivity(new Intent("android.intent.action.VIEW", HelpUrl.getHelpUrl(this, getResources().getString(R.string.url_param_help_stream))));
      }
      else if (i == R.id.sign_out)
      {
        this.mSignOnManager.signOut(false);
      }
      else
      {
        bool = super.onOptionsItemSelected(paramMenuItem);
      }
    }
  }

  protected void onPause()
  {
    super.onPause();
    this.mSignOnManager.onPause();
    EsService.unregisterListener(this.mServiceListener);
  }

  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    int i = paramMenu.size();
    int j = 0;
    if (j < i)
    {
      MenuItem localMenuItem = paramMenu.getItem(j);
      int k = localMenuItem.getItemId();
      if ((k == R.id.search) || (k == R.id.feedback) || (k == R.id.settings) || (k == R.id.help) || (k == R.id.sign_out))
        localMenuItem.setVisible(true);
      while (true)
      {
        j++;
        break;
        localMenuItem.setVisible(false);
      }
    }
    this.mHostLayout.onPrepareOptionsMenu(paramMenu);
    return true;
  }

  protected void onResume()
  {
    super.onResume();
    boolean bool = this.mSignOnManager.onResume();
    EsService.registerListener(this.mServiceListener);
    if (this.mRequestId != null)
      if (!EsService.isRequestPending(this.mRequestId.intValue()))
      {
        int i = this.mRequestId.intValue();
        EsService.removeResult(this.mRequestId.intValue());
        handleServiceCallback$b5e9bbb(i);
        if (!getIntent().getBooleanExtra("sign_out", false))
          break label121;
        this.mSignOnManager.signOut(true);
        startExternalActivity(new Intent("android.intent.action.VIEW", REMOVE));
        finish();
      }
    while (true)
    {
      return;
      updateNotificationsSpinner();
      break;
      updateNotificationsSpinner();
      break;
      label121: if (this.mSignOnManager.getAccount() == null)
        Arrays.fill(this.mDestinationState, null);
      if (bool)
      {
        getSupportLoaderManager().initLoader(0, null, this);
        configureDestinations();
        buildDestinationBundleForIntent();
        showCurrentDestination();
      }
    }
  }

  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mRequestId != null)
      paramBundle.putInt("reqId", this.mRequestId.intValue());
    if (this.mNavigationBar != null)
    {
      paramBundle.putInt("scrollPos", this.mNavigationBar.getFirstVisiblePosition());
      paramBundle.putBoolean("navBarCollapsed", this.mNavigationBarAdapter.isCollapsed());
    }
    saveDestinationState();
  }

  protected void onStart()
  {
    super.onStart();
    EsPostsData.setSyncEnabled(false);
  }

  protected void onStop()
  {
    super.onStop();
    EsPostsData.setSyncEnabled(true);
  }

  public final void onUpButtonClick()
  {
    if ((!this.mHostLayout.isNavigationBarVisible()) && (this.mHostLayout.getCurrentHostedFragment() != null) && (this.mHostLayout.getCurrentHostedFragment().onUpButtonClicked()));
    while (true)
    {
      return;
      this.mHostLayout.toggleNavigationBarVisibility();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.HomeActivity
 * JD-Core Version:    0.6.2
 */