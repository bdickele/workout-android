package org.dickele.workout.activity.parameters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.tuple.Pair;
import org.dickele.workout.R;
import org.dickele.workout.parser.FromJavaToMd;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.util.SimpleTextWatcher;

import java.io.File;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.widget.ContentLoadingProgressBar;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;

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

        setSupportActionBar(findViewById(R.id.toolbar_main));
        // Used to display back button in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Management of back button in toolbar
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    private void exportWorkouts() {
        final ContentLoadingProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.show();

        try {
            createDirAndFile();
        } catch (final Exception e) {
            Toast.makeText(this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
            Logger.getLogger(this.getClass().getSimpleName()).severe(e.getMessage());
        }

        progressBar.hide();
    }

    private void createDirAndFile() {
        final File directory = new File(getFilesDir(), "backups");

        if (!directory.exists()) {
            directory.mkdir();
        }

        final String path = directory.getPath();

        final Pair<String, File> fileResult = FromJavaToMd.createFile(path, InMemoryDb.getInstance().getWorkouts(), this);
        if (fileResult.getLeft() != null) {
            Toast.makeText(this, "Error : " + fileResult.getLeft(), Toast.LENGTH_LONG).show();
            return;
        }

        final File file = fileResult.getRight();
        final Uri contentUri = FileProvider.getUriForFile(this, "org.dickele.workout.fileprovider", file);

        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.setFlags(FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{textEmail.getText().toString()});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
        intent.putExtra(Intent.EXTRA_STREAM, contentUri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
