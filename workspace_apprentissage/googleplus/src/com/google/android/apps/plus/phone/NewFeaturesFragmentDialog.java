package com.google.android.apps.plus.phone;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;
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

public class NewFeaturesFragmentDialog extends DialogFragment
{
  private static final boolean CONTACTS_SYNC_ENABLED;
  private CheckBox mContactsStatsSyncChoice;
  private CheckBox mContactsSyncChoice;
  private View mContactsSyncView;

  static
  {
    if (Build.VERSION.SDK_INT >= 14);
    for (boolean bool = true; ; bool = false)
    {
      CONTACTS_SYNC_ENABLED = bool;
      return;
    }
  }

  public NewFeaturesFragmentDialog()
  {
  }

  public NewFeaturesFragmentDialog(EsAccount paramEsAccount)
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("account", paramEsAccount);
    setArguments(localBundle);
  }

  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    FragmentActivity localFragmentActivity = getActivity();
    final EsAccount localEsAccount = (EsAccount)getArguments().getParcelable("account");
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(localFragmentActivity);
    View localView1 = LayoutInflater.from(localFragmentActivity).inflate(R.layout.whats_new_dialog, null);
    this.mContactsSyncView = localView1.findViewById(R.id.contacts_sync_view);
    View localView2 = this.mContactsSyncView;
    int i;
    TextView localTextView1;
    if (CONTACTS_SYNC_ENABLED)
    {
      i = 0;
      localView2.setVisibility(i);
      this.mContactsSyncChoice = ((CheckBox)localView1.findViewById(R.id.contacts_sync_checkbox));
      this.mContactsSyncChoice.setChecked(true);
      this.mContactsStatsSyncChoice = ((CheckBox)localView1.findViewById(R.id.contacts_stats_sync_checkbox));
      this.mContactsStatsSyncChoice.setChecked(true);
      ((TextView)localView1.findViewById(R.id.contacts_sync_checkbox_title)).setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          NewFeaturesFragmentDialog.this.mContactsSyncChoice.toggle();
        }
      });
      localTextView1 = (TextView)localView1.findViewById(R.id.contacts_stats_sync_checkbox_title);
      if (!AndroidUtils.hasTelephony(localFragmentActivity))
        break label325;
    }
    label325: for (int j = R.string.contacts_stats_sync_preference_enabled_phone_summary; ; j = R.string.contacts_stats_sync_preference_enabled_tablet_summary)
    {
      localTextView1.setText(getString(j));
      localTextView1.setOnClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          NewFeaturesFragmentDialog.this.mContactsStatsSyncChoice.toggle();
        }
      });
      TextView localTextView2 = (TextView)localView1.findViewById(R.id.contacts_stats_sync_checkbox_link);
      String str1 = getString(R.string.contacts_stats_sync_preference_enabled_learn_more);
      SpannableString localSpannableString = new SpannableString(str1);
      String str2 = HelpUrl.getHelpUrl(localFragmentActivity, getResources().getString(R.string.url_param_help_stats_sync)).toString();
      Linkify.addLinks(localSpannableString, Pattern.compile(str1), str2);
      localTextView2.setText(localSpannableString);
      localTextView2.setMovementMethod(LinkMovementMethod.getInstance());
      localBuilder.setView(localView1);
      DialogInterface.OnClickListener local3 = new DialogInterface.OnClickListener()
      {
        public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          FragmentActivity localFragmentActivity = NewFeaturesFragmentDialog.this.getActivity();
          if (NewFeaturesFragmentDialog.CONTACTS_SYNC_ENABLED)
          {
            boolean bool2 = NewFeaturesFragmentDialog.this.mContactsSyncChoice.isChecked();
            EsAccountsData.saveContactsSyncPreference(localFragmentActivity, localEsAccount, bool2);
          }
          boolean bool1 = NewFeaturesFragmentDialog.this.mContactsStatsSyncChoice.isChecked();
          EsAccountsData.saveContactsStatsSyncPreference(localFragmentActivity, localEsAccount, bool1);
          EsAnalytics.recordImproveSuggestionsPreferenceChange(localFragmentActivity, localEsAccount, bool1, OzViews.HOME);
          if (bool1)
            EsService.disableWipeoutStats(localFragmentActivity, localEsAccount);
          while (true)
          {
            return;
            EsService.enableAndPerformWipeoutStats(localFragmentActivity, localEsAccount);
          }
        }
      };
      localBuilder.setPositiveButton(getString(17039370), local3);
      localBuilder.setCancelable(false);
      return localBuilder.create();
      i = 8;
      break;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.NewFeaturesFragmentDialog
 * JD-Core Version:    0.6.2
 */