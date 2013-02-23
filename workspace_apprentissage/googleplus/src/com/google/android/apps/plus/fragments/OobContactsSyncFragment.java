package com.google.android.apps.plus.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.util.AndroidUtils;
import com.google.android.apps.plus.util.HelpUrl;
import java.util.regex.Pattern;

public class OobContactsSyncFragment extends Fragment
{
  private ImageView mArrowContactsStatsSync;
  private ImageView mArrowContactsSync;
  private ImageView mCiclesImage;
  private ImageView mContactsImage;
  private CheckBox mContactsStatsSyncChoice;
  private CheckBox mContactsSyncChoice;
  private boolean statsSyncOnly;

  public final boolean commit()
  {
    FragmentActivity localFragmentActivity = getActivity();
    EsAccount localEsAccount = (EsAccount)localFragmentActivity.getIntent().getParcelableExtra("account");
    if (!this.statsSyncOnly)
      EsAccountsData.saveContactsSyncPreference(localFragmentActivity, localEsAccount, this.mContactsSyncChoice.isChecked());
    boolean bool = this.mContactsStatsSyncChoice.isChecked();
    EsAccountsData.saveContactsStatsSyncPreference(localFragmentActivity, localEsAccount, bool);
    EsAnalytics.recordImproveSuggestionsPreferenceChange(localFragmentActivity, localEsAccount, bool, OzViews.OOB_IMPROVE_CONTACTS_VIEW);
    if (bool)
      EsService.disableWipeoutStats(localFragmentActivity, localEsAccount);
    while (true)
    {
      return true;
      EsService.enableAndPerformWipeoutStats(localFragmentActivity, localEsAccount);
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    boolean bool;
    View localView;
    label148: View.OnClickListener local4;
    Resources localResources;
    TextView localTextView1;
    if (Build.VERSION.SDK_INT < 14)
    {
      bool = true;
      this.statsSyncOnly = bool;
      localView = paramLayoutInflater.inflate(R.layout.oob_contacts_sync_fragment, paramViewGroup, false);
      this.mCiclesImage = ((ImageView)localView.findViewById(R.id.circles));
      this.mCiclesImage.setImageResource(R.drawable.home_screen_people_icon_default);
      this.mContactsImage = ((ImageView)localView.findViewById(R.id.contacts));
      this.mContactsImage.setImageResource(R.drawable.oob_contact_sync_icon_contacts);
      this.mArrowContactsStatsSync = ((ImageView)localView.findViewById(R.id.arrow_contacts_stats_sync));
      this.mContactsStatsSyncChoice = ((CheckBox)localView.findViewById(R.id.contacts_stats_sync_checkbox));
      this.mContactsStatsSyncChoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
      {
        public final void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
        {
          ImageView localImageView = OobContactsSyncFragment.this.mArrowContactsStatsSync;
          if (paramAnonymousBoolean);
          for (int i = 0; ; i = 4)
          {
            localImageView.setVisibility(i);
            return;
          }
        }
      });
      this.mContactsStatsSyncChoice.setChecked(true);
      if (!this.statsSyncOnly)
        break label308;
      this.mArrowContactsStatsSync.setImageResource(R.drawable.oob_contact_sync_icon_arrow);
      local4 = new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          OobContactsSyncFragment.this.mContactsStatsSyncChoice.toggle();
        }
      };
      localResources = getResources();
      localTextView1 = (TextView)localView.findViewById(R.id.contacts_stats_sync_checkbox_title);
      if (!AndroidUtils.hasTelephony(getActivity()))
        break label407;
    }
    label407: for (int i = R.string.contacts_stats_sync_preference_enabled_phone_summary; ; i = R.string.contacts_stats_sync_preference_enabled_tablet_summary)
    {
      localTextView1.setText(localResources.getString(i));
      localTextView1.setOnClickListener(local4);
      TextView localTextView2 = (TextView)localView.findViewById(R.id.contacts_stats_sync_checkbox_link);
      String str1 = localResources.getString(R.string.contacts_stats_sync_preference_enabled_learn_more);
      SpannableString localSpannableString = new SpannableString(str1);
      String str2 = getResources().getString(R.string.url_param_help_stats_sync);
      Uri localUri = HelpUrl.getHelpUrl(getActivity(), str2);
      Linkify.addLinks(localSpannableString, Pattern.compile(str1), localUri.toString());
      localTextView2.setText(localSpannableString);
      localTextView2.setMovementMethod(LinkMovementMethod.getInstance());
      return localView;
      bool = false;
      break;
      label308: this.mArrowContactsSync = ((ImageView)localView.findViewById(R.id.arrow_contacts_sync));
      this.mArrowContactsSync.setImageResource(R.drawable.oob_contact_sync_icon_arrow);
      this.mArrowContactsStatsSync.setImageResource(R.drawable.oob_contact_stats_sync_icon_arrow);
      this.mContactsSyncChoice = ((CheckBox)localView.findViewById(R.id.contacts_sync_checkbox));
      this.mContactsSyncChoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
      {
        public final void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
        {
          ImageView localImageView = OobContactsSyncFragment.this.mArrowContactsSync;
          if (paramAnonymousBoolean);
          for (int i = 0; ; i = 4)
          {
            localImageView.setVisibility(i);
            return;
          }
        }
      });
      this.mContactsSyncChoice.setChecked(true);
      View.OnClickListener local3 = new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          OobContactsSyncFragment.this.mContactsSyncChoice.toggle();
        }
      };
      localView.findViewById(R.id.contacts_sync_checkbox_title).setOnClickListener(local3);
      break label148;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.OobContactsSyncFragment
 * JD-Core Version:    0.6.2
 */