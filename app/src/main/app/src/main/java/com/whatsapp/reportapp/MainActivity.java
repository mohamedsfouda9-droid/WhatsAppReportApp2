package com.whatsapp.reportapp;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private final int navy = Color.rgb(13, 35, 90);

    private GradientDrawable rounded(int color, int radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(color);
        shape.setCornerRadius(radius);
        return shape;
    }

    private TextView text(String value, int size, int color) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER);
        t.setTypeface(null, Typeface.BOLD);
        return t;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(navy);

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setGravity(Gravity.CENTER_HORIZONTAL);
        page.setPadding(24, 36, 24, 32);
        scroll.addView(page);

        TextView title = text("حظر واتساب", 30, Color.WHITE);
        page.addView(title, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 70));

        TextView sponsor = text(
                "برعايه ✨💯¥ ℰ𝓁_ℳℴ𝓈𝒶𝓀ℯ𝓈_ℋ_𝒜_ℋ_ℳ€❌❗💯",
                14, Color.WHITE);
        sponsor.setPadding(0, 8, 0, 24);
        page.addView(sponsor);

        TextView info = text(
                "اكتب الرقم وسبب البلاغ، ثم راجع الرسالة في تطبيق البريد.",
                16, Color.WHITE);
        page.addView(info);

        EditText phone = new EditText(this);
        phone.setSingleLine(true);
        phone.setHint("رقم الهاتف مع مفتاح الدولة");
        phone.setInputType(3);
        phone.setTextColor(Color.BLACK);
        phone.setHintTextColor(Color.DKGRAY);
        phone.setPadding(16, 12, 16, 12);
        phone.setBackground(rounded(Color.WHITE, 18));
        LinearLayout.LayoutParams fieldParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        fieldParams.setMargins(0, 24, 0, 16);
        page.addView(phone, fieldParams);

        EditText reason = new EditText(this);
        reason.setHint("اكتب سبب البلاغ بالتفصيل");
        reason.setTextColor(Color.BLACK);
        reason.setHintTextColor(Color.DKGRAY);
        reason.setGravity(Gravity.TOP | Gravity.RIGHT);
        reason.setMinLines(4);
        reason.setPadding(16, 12, 16, 12);
        reason.setBackground(rounded(Color.WHITE, 18));
        page.addView(reason, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        Button send = new Button(this);
        send.setText("ارفعوا علي بتاعي ❌❗");
        send.setTextSize(17);
        send.setTextColor(Color.WHITE);
        send.setBackground(rounded(Color.rgb(35, 75, 155), 18));
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(0, 24, 0, 16);
        page.addView(send, buttonParams);

        TextView note = text(
                "لن يُرسل أي بلاغ تلقائيًا. ستراجع الرسالة وتقرر إرسالها بنفسك.",
                13, Color.LTGRAY);
        page.addView(note);

        send.setOnClickListener(v -> {
            String number = phone.getText().toString().trim();
            String details = reason.getText().toString().trim();

            if (number.isEmpty() || details.isEmpty()) {
                Toast.makeText(this,
                        "اكتب رقم الهاتف وسبب البلاغ أولًا",
                        Toast.LENGTH_LONG).show();
                return;
            }

            String subject = "WhatsApp support report";
            String body = "Phone number: " + number
                    + "\nReason for report: " + details
                    + "\n\nPlease review this report.";

            Uri email = Uri.parse("mailto:support@whatsapp.com")
                    .buildUpon()
                    .appendQueryParameter("subject", subject)
                    .appendQueryParameter("body", body)
                    .build();

            Intent intent = new Intent(Intent.ACTION_SENDTO, email);

            try {
                startActivity(Intent.createChooser(
                        intent, "اختر تطبيق البريد لمراجعة البلاغ"));
            } catch (Exception e) {
                Toast.makeText(this,
                        "لا يوجد تطبيق بريد مثبت على الجهاز",
                        Toast.LENGTH_LONG).show();
            }
        });

        setContentView(scroll);
    }
}
