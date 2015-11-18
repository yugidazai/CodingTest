package duc.assessment.weatherforecast.utilities;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

/**
 * Created by ducnguyen on 18/11/2015.
 * This helper class is to create hyperlink for a TextView and others functions (if any)
 */
public class TextViewHelper {
    public static void createHyperLink(TextView textView, String link, String displayedText) {
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(Html.fromHtml("<a href='" + link + "'>" + displayedText + "</a>"));
    }
}
