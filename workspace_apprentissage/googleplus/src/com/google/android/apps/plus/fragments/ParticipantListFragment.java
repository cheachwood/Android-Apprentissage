package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.RecyclerListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.content.SimpleParticipantsLoader;
import com.google.android.apps.plus.phone.EsCursorAdapter;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.ParticipantParcelable;
import com.google.android.apps.plus.util.StringUtils;
import com.google.android.apps.plus.views.ParticipantListItemView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ParticipantListFragment extends EsListFragment<ListView, ParticipantCursorAdapter>
  implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener, AdapterView.OnItemClickListener
{
  private static Bitmap sDefaultUserImage;
  private EsAccount mAccount;
  private long mConversationRowId;
  private ParticipantParcelable[] mParticipantArray;
  private Uri mParticipantsUri;
  private Integer mPendingRequestId;
  private ArrayList<String> mSelectedCircleIdsResult;
  private final EsServiceListener mServiceListener = new ServiceListener((byte)0);

  private void addParticipantsToCircle()
  {
    OzActions localOzActions = OzActions.GROUP_CONVERSATION_MAKE_CIRCLE;
    if (this.mAccount != null)
    {
      FragmentActivity localFragmentActivity = getActivity();
      EsAnalytics.recordActionEvent(localFragmentActivity, this.mAccount, localOzActions, OzViews.getViewForLogging(localFragmentActivity));
    }
    ProgressFragmentDialog.newInstance(null, getString(R.string.add_to_circle_operation_pending), false).show(getFragmentManager(), "req_pending");
    this.mPendingRequestId = EsService.addPeopleToCircles(getActivity(), this.mAccount, this.mParticipantArray, (String[])this.mSelectedCircleIdsResult.toArray(new String[0]));
    this.mSelectedCircleIdsResult = null;
  }

  protected final void handleServiceCallback$b5e9bbb(int paramInt)
  {
    if ((this.mPendingRequestId == null) || (paramInt != this.mPendingRequestId.intValue()));
    while (true)
    {
      return;
      DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("req_pending");
      if (localDialogFragment != null)
        localDialogFragment.dismiss();
      this.mPendingRequestId = null;
    }
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 != -1);
    while (true)
    {
      return;
      this.mSelectedCircleIdsResult = paramIntent.getExtras().getStringArrayList("selected_circle_ids");
    }
  }

  public void onClick(View paramView)
  {
    if (paramView.getId() == R.id.add_to_circle_button)
      startActivityForResult(Intents.getCircleMembershipActivityIntent(getActivity(), this.mAccount, null, null, true), 0);
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if ((paramBundle != null) && (paramBundle.containsKey("request_id")))
      this.mPendingRequestId = Integer.valueOf(paramBundle.getInt("request_id"));
    if (sDefaultUserImage == null)
      sDefaultUserImage = EsAvatarData.getSmallDefaultAvatar(getActivity());
    this.mAccount = ((EsAccount)getActivity().getIntent().getParcelableExtra("account"));
    this.mConversationRowId = getActivity().getIntent().getLongExtra("conversation_row_id", -1L);
    this.mParticipantsUri = EsProvider.buildParticipantsUri(this.mAccount, this.mConversationRowId);
    if (this.mConversationRowId == -1L)
      getLoaderManager().initLoader(2, null, this);
    while (true)
    {
      return;
      getLoaderManager().initLoader(1, null, this);
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Object localObject;
    if (paramInt == 2)
    {
      Collection localCollection = (Collection)getActivity().getIntent().getSerializableExtra("hangout_participants");
      localObject = new SimpleParticipantsLoader(getActivity(), localCollection, ParticipantQuery.PROJECTION);
    }
    while (true)
    {
      return localObject;
      if (paramInt == 1)
      {
        FragmentActivity localFragmentActivity = getActivity();
        Uri localUri = this.mParticipantsUri;
        String[] arrayOfString1 = ParticipantQuery.PROJECTION;
        String[] arrayOfString2 = new String[1];
        arrayOfString2[0] = this.mAccount.getRealTimeChatParticipantId();
        localObject = new EsCursorLoader(localFragmentActivity, localUri, arrayOfString1, "participant_id!=? AND active=1", arrayOfString2, "first_name ASC", this.mParticipantsUri);
      }
      else
      {
        localObject = null;
      }
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.participant_list_fragment, paramViewGroup, false);
    this.mListView = ((ListView)localView.findViewById(16908298));
    this.mAdapter = new ParticipantCursorAdapter(getActivity(), null, this.mListView);
    ((ListView)this.mListView).setAdapter(this.mAdapter);
    ((ListView)this.mListView).setOnItemClickListener(this);
    localView.findViewById(R.id.add_to_circle_button).setOnClickListener(this);
    return localView;
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    String str = ((ParticipantListItemView)paramView).getPersonId();
    Intent localIntent = Intents.getProfileActivityIntent(getActivity(), this.mAccount, str, null);
    getActivity().startActivity(localIntent);
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mServiceListener);
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mServiceListener);
    if ((this.mPendingRequestId != null) && (!EsService.isRequestPending(this.mPendingRequestId.intValue())))
    {
      EsService.removeResult(this.mPendingRequestId.intValue());
      handleServiceCallback$b5e9bbb(this.mPendingRequestId.intValue());
      this.mPendingRequestId = null;
    }
    if (((ParticipantCursorAdapter)this.mAdapter).getCursor() == null)
      showEmptyViewProgress(getView());
    if ((this.mSelectedCircleIdsResult != null) && (this.mParticipantArray != null))
      addParticipantsToCircle();
    ((ParticipantCursorAdapter)this.mAdapter).onResume();
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mPendingRequestId != null)
      paramBundle.putInt("request_id", this.mPendingRequestId.intValue());
  }

  static final class ParticipantCursorAdapter extends EsCursorAdapter
  {
    boolean mShowLetterSections;
    final List<ParticipantListItemView> mViews = new ArrayList();

    public ParticipantCursorAdapter(Context paramContext, Cursor paramCursor, AbsListView paramAbsListView)
    {
      super(null);
      paramAbsListView.setRecyclerListener(new AbsListView.RecyclerListener()
      {
        public final void onMovedToScrapHeap(View paramAnonymousView)
        {
          ((ParticipantListItemView)paramAnonymousView).clear();
          ParticipantListFragment.ParticipantCursorAdapter.this.mViews.remove(paramAnonymousView);
        }
      });
    }

    public final void bindView(View paramView, Context paramContext, Cursor paramCursor)
    {
      ParticipantListItemView localParticipantListItemView = (ParticipantListItemView)paramView;
      if (!this.mViews.contains(paramView))
        this.mViews.add(localParticipantListItemView);
      localParticipantListItemView.clear();
      String str = paramCursor.getString(2);
      localParticipantListItemView.setParticipantName(str);
      localParticipantListItemView.setPersonId(paramCursor.getString(1));
      char c = StringUtils.firstLetter(str);
      if (this.mShowLetterSections)
        if (!paramCursor.moveToPrevious())
        {
          localParticipantListItemView.showSectionHeader(c);
          paramCursor.moveToFirst();
        }
      while (true)
      {
        localParticipantListItemView.setContentDescription(str);
        return;
        if (StringUtils.firstLetter(paramCursor.getString(2)) != c)
          localParticipantListItemView.showSectionHeader(c);
        while (true)
        {
          paramCursor.moveToNext();
          break;
          localParticipantListItemView.hideSectionHeader();
        }
        localParticipantListItemView.hideSectionHeader();
      }
    }

    public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
    {
      return (ParticipantListItemView)((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(R.layout.participant_list_item_view, null);
    }

    public final void onResume()
    {
      this.mViews.clear();
    }

    public final Cursor swapCursor(Cursor paramCursor)
    {
      boolean bool;
      if (paramCursor != null)
      {
        int i = paramCursor.getCount();
        bool = false;
        if (i > 20)
          bool = true;
      }
      for (this.mShowLetterSections = bool; ; this.mShowLetterSections = false)
        return super.swapCursor(paramCursor);
    }
  }

  public static abstract interface ParticipantQuery
  {
    public static final String[] PROJECTION = { "_id", "participant_id", "full_name", "first_name" };
  }

  private final class ServiceListener extends EsServiceListener
  {
    private ServiceListener()
    {
    }

    public final void onAddPeopleToCirclesComplete$6a63df5(int paramInt, ServiceResult paramServiceResult)
    {
      ParticipantListFragment.this.handleServiceCallback$b5e9bbb(paramInt);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.ParticipantListFragment
 * JD-Core Version:    0.6.2
 */