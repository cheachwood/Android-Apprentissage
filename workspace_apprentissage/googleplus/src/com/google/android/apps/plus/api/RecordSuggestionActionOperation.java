package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.RecordSuggestionRequest;
import com.google.api.services.plusi.model.RecordSuggestionRequestJson;
import com.google.api.services.plusi.model.RecordSuggestionResponse;
import com.google.api.services.plusi.model.RecordSuggestionResponseJson;
import java.util.List;

public final class RecordSuggestionActionOperation extends PlusiOperation<RecordSuggestionRequest, RecordSuggestionResponse>
{
  private final String mActionType;
  private final List<String> mPersonIds;
  private final List<String> mSuggestionIds;
  private final String mSuggestionsUi;

  public RecordSuggestionActionOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, List<String> paramList1, List<String> paramList2, String paramString2, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "recordsuggestion", RecordSuggestionRequestJson.getInstance(), RecordSuggestionResponseJson.getInstance(), null, null);
    this.mSuggestionsUi = paramString1;
    this.mPersonIds = paramList1;
    this.mSuggestionIds = paramList2;
    this.mActionType = paramString2;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.RecordSuggestionActionOperation
 * JD-Core Version:    0.6.2
 */