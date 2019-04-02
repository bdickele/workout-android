package org.dickele.workout.activity.parameters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.tuple.Pair;
import org.dickele.workout.R;
import org.dickele.workout.parser.FromJavaToMd;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.util.SimpleTextWatcher;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;

public class ParametersActivity extends AppCompatActivity {

    private static final String PREF_EMAIL = "PREF_EMAIL";

    private TextView textEmail;

    private Button buttonExport;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_parameters);
        textEmail = findViewById(R.id.email);
        buttonExport = findViewById(R.id.button_export);

        ((Toolbar) findViewById(R.id.toolbar_main)).setTitle(R.string.parameters);
        configureParameterEmail();

        buttonExport.setOnClickListener(v -> exportWorkouts());
    }

    private void configureParameterEmail() {
        final SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        textEmail.setText(preferences.getString(PREF_EMAIL, null));
        checkEmailState();

        textEmail.addTextChangedListener(new SimpleTextWatcher() {

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                preferences.edit().putString(PREF_EMAIL, textEmail.getText().toString()).apply();
                checkEmailState();
            }
        });
    }

    private void checkEmailState() {
        final String email = textEmail.getText().toString();
        final boolean emailValid = isEmailValid(email);
        buttonExport.setEnabled(emailValid);
        buttonExport.setActivated(emailValid);
    }

    private static boolean isEmailValid(final String email) {
        final String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        final Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void exportWorkouts() {
        //TODO Mettre les libelles dans string.xml
        final ContentLoadingProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.show();

        final Pair<String, File> fileResult = FromJavaToMd.createFile(InMemoryDb.getInstance().getWorkouts(), this);
        if (fileResult.getLeft() != null) {
            Toast.makeText(this, "Erreur : " + fileResult.getLeft(), Toast.LENGTH_SHORT).show();
            return;
        }
        final File file = fileResult.getRight();
        Toast.makeText(this, "Fichier créé", Toast.LENGTH_SHORT).show();
        //TODO La creation de l'Uri plante
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{textEmail.getText().toString()});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Workouts backup");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        Toast.makeText(this, "Fichier envoyé par email", Toast.LENGTH_SHORT).show();
        progressBar.hide();
    }
}
